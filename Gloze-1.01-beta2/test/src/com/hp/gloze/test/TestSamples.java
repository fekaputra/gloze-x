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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;

import com.hp.gloze.Gloze;
import com.hp.gloze.XMLUtility;
import com.hp.hpl.jena.rdf.model.Model;

public class TestSamples {
	static final boolean SILENT = true;
	Gloze gloze;

	@BeforeClass public static void setUp() {
		System.setProperty("gloze.xmlns","http://example.org/unqualified");
		System.setProperty("gloze.order","seq");
		System.setProperty("gloze.space","default"); // replace and collapse whitespace
		System.setProperty("gloze.fixed","true"); // add fixed attriubutes
	}
	
	@Test public void testLibrary() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/library/library.xml");
		Gloze.initSchema(
				new URI("http://example.org/library/schema"), 
				new File(src.getParent()+"/library.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, new URI("http://example.org/library/instance"));
	}

	@Test public void testPO() throws Exception {
		gloze = new Gloze(SILENT);
		gloze.fixed = "false";
		File src = new File("samples/po/po.xml");
		Gloze.initSchema(
				new URI("http://tempuri.org/po.xsd"), 
				new File(src.getParent()+"/po.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testNamespaces() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/namespaces/po1.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/PO1"), 
				new File(src.getParent()+"/po1.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testIPO() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/ipo/ipo.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/IPO"), 
				new File(src.getParent()+"/ipo.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testRedefine() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/redefine/ipo.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/IPO"), 
				new File(src.getParent()+"/ipo.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testSubstitution() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/substitution/substitution.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/IPO"), 
				new File(src.getParent()+"/ipo.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testReport() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/report/4Q99.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/IPO"), 
				new File(src.getParent()+"/ipo.xsd").toURL(),
				gloze.schemaMap);
		Gloze.initSchema(
				new URI("http://www.example.com/Report"), 
				new File(src.getParent()+"/report.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void test6States() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/lists/6States.xml");
		Gloze.initSchema(
				new URI("http://example.org"), 
				new File(src.getParent()+"/lists.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testZips() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/lists/zips.xml");
		Gloze.initSchema(
				new URI("http://example.org"), 
				new File(src.getParent()+"/lists.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testAttributeGroups() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/attributeGroups/po.xml");
		Gloze.initSchema(
				new URI("http://tempuri.org/po.xsd"), 
				new File(src.getParent()+"/po.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}
	
	@Test public void testEmpty() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/empty/empty.xml");
		Gloze.initSchema(null, new File(src.getParent()+"/empty.xsd").toURL(),gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testChoiceSeqGroups() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/groups/po1.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/PO1"), 
				new File(src.getParent()+"/po1.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}
	
	@Test public void testAllGroups() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/groups/po1.xml");
		Gloze.initSchema(
				new URI("http://www.example.com/PO1"), 
				new File(src.getParent()+"/po2.xsd").toURL(),
				gloze.schemaMap);
		runTest(src, src.toURI());
	}
	
	@Test public void testNil() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/nil/nil.xml");
		Gloze.initSchema(null, new File(src.getParent()+"/nil.xsd").toURL(),gloze.schemaMap);
		runTest(src, src.toURI());
	}

	@Test public void testMixed() throws Exception {
		gloze = new Gloze(SILENT);
		File src = new File("samples/mixed/mix.xml");
		Gloze.initSchema(null, new File(src.getParent()+"/mix.xsd").toURL(),gloze.schemaMap);
		runTest(src, src.toURI());
	}

	private void runTest(File src, URI uri) {
		try {
			// load the target XML and initialise gloze with its schema
			Document xml = XMLUtility.read(new FileInputStream(src));
			// lift and drop
			Model m = gloze.lift(xml, src.toURL(), uri);
			Document doc = gloze.drop(m, uri);
			assertTrue(Utility.equalXML(xml,doc));
		} catch (Exception e) {
			fail(e.getMessage());			
		}
	}

}
