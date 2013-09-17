/*
 *  (c) Copyright Hewlett-Packard Company 2001 - 2009
 *  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @author steven.a.battle@googlemail.com
 */

package com.hp.gloze.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;
import java.util.StringTokenizer;

import junit.framework.TestCase;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.hp.gloze.Gloze;
import com.hp.gloze.XMLBean;
import com.hp.gloze.XMLUtility;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

/**
 * defines the environment to run lift tests
 */
public class TestLift {

	/* the properties file and the properties it defines */
	private static final String CONFIG = "testLift.props";
	private static String uri, ns, xml, rdf;
	static final boolean SILENT = true;
		
	@BeforeClass public static void setUp() throws IOException {
		Properties props = new Properties();
		props.load(TestLift.class.getResourceAsStream(CONFIG));
		xml = props.getProperty("xml");
		rdf = props.getProperty("rdf");
		System.setProperty("gloze.xmlns", ns = props.getProperty("xmlns"));
		System.setProperty("gloze.verbose","false");
		System.setProperty("gloze.uri", uri = props.getProperty("uri"));
		System.setProperty("gloze.space","default");
		System.setProperty("gloze.order","seq");
	}

	@Test public void testLift() {
		File xmlDir = new File(xml);
		File rdfDir = new File(rdf);
		
		if (!rdfDir.exists()) try {
			rdfDir.mkdir();
		} catch (Exception e) {
			e.printStackTrace();
		}

		File[] list = xmlDir.listFiles();
		for (int i = 0; i < list.length; i++) {
			// skip over eg. CVS subdirectories
			if (list[i].isDirectory()) continue;

			Gloze gloze = new Gloze(SILENT);
			try {
				Gloze.logger.info("lift " + list[i].getName());
				String targetName = changeSuffix(list[i].getName(), "rdf");
				File targetRDF = new File(rdfDir, targetName);
				Model m = ModelFactory.createDefaultModel();
				// generate target RDF the first time
				Model output = ModelFactory.createDefaultModel();
				File target = targetRDF.exists()?null:targetRDF;
				URI base = XMLBean.resolveBase(uri,list[i]);
				gloze.xml_to_rdf(list[i], target, base, output);
				m.read(new FileInputStream(targetRDF), base.toString());
				assertTrue(m.isIsomorphicWith(output));
			} catch (Exception e) {
				Gloze.logger.error(e.getMessage());
			}
		}
	}

	String changeSuffix(String name, String suffix) {
		return name.substring(0, name.lastIndexOf('.') + 1) + suffix;
	}
	
	@Test public void testDrop() {
		StringTokenizer tx = new StringTokenizer(xml), tr = new StringTokenizer(rdf);
		while (tx.hasMoreTokens() && tr.hasMoreTokens()) {
			File xmlDir = new File(tx.nextToken());
			File rdfDir = new File(tr.nextToken());
			dropDir(xmlDir, rdfDir);
		}
	}

	private void dropDir(File xmlDir, File rdfDir) {
		File[] list = rdfDir.listFiles();
		for (int i = 0; i < list.length; i++) {
			// skip over eg. CVS subdirectories
			if (list[i].isDirectory() || !list[i].getName().endsWith("rdf")) continue;
			
			Gloze gloze = new Gloze(SILENT);

			try {
				Model m = ModelFactory.createDefaultModel();
				Gloze.logger.info("drop " + list[i].getName());
				String name = list[i].getName();
				// load the target XML and initialise gloze with its schema
				String targetName = name.substring(0,name.lastIndexOf('.') + 1) + "xml";
				File targetXML = new File(xmlDir, targetName);
				Document targetDoc = XMLUtility.read(new FileInputStream(targetXML));
				Gloze.initSchemaXSI(targetDoc.getDocumentElement(),xmlDir.toURL(),ns,gloze.schemaMap);
				Document target = XMLUtility.read(new FileInputStream(targetXML));
				// drop the rdf and compare with the target
				Document output = gloze.rdf_to_xml(list[i], null, XMLBean.resolveBase(uri,targetXML), m);
				assertTrue(Utility.equalXML(output,target, true));
			}  catch (Exception error) {
				fail(list[i].getName()+" : "+error.getMessage());
			}
		}
	}


}
