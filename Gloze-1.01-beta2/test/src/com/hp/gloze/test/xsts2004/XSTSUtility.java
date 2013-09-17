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

package com.hp.gloze.test.xsts2004;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import org.w3c.dom.Document;

import com.hp.gloze.Gloze;
import com.hp.gloze.XMLUtility;
import com.hp.gloze.test.Utility;
import com.hp.hpl.jena.rdf.model.Model;

public abstract class XSTSUtility {
	static final boolean SILENT = true;
	private static final String EXCLUSIONS = "exclusions.txt";
	protected File tests;
	
	public static Set<String> exclusions() {
		Set<String> exclusions = new HashSet<String>();
		BufferedReader in = new BufferedReader(new InputStreamReader(XSTSUtility.class.getResourceAsStream(EXCLUSIONS)));
		String line;
		try {
			while ((line=in.readLine())!=null) 
				exclusions.add(line);
		} catch (Exception e) {} // non-fatal
		return exclusions;
	}

	public static void runTest(String source, Set<String> exclusions) {
		File tests = new File(source);		
		File[] list = tests.listFiles();
		for (int i = 0; i < list.length; i++) {
			String name = list[i].getName();
			// skip sub-folders and non-xml files
			if (list[i].isDirectory() || !name.endsWith(".xml")) continue;
			
			// is this test specifically excluded?
			if (exclusions!=null && exclusions.contains(name)) continue;

			boolean positive = true;
			try {
				Gloze.clearCache();
				Gloze.logger.info(name);
				
				// is this intended as a positive or negative test?
				String l;
				BufferedReader r = new BufferedReader(new FileReader(list[i]));
				while ((l=r.readLine())!=null) {
					if (l.contains("This instance is intended to be invalid")) {
						Gloze.logger.info(l.trim()+"...");
						positive = false;
						break;
					}
				}			
				// load the target XML and initialise gloze with its schema
				Document xml = XMLUtility.read(new FileInputStream(list[i]));
				
				// lift and drop
				Gloze gloze = new Gloze(SILENT);
				
				// list[i] is the base, provide URL for additional imports/includes
				Model m = gloze.lift(xml, list[i].toURL(), list[i].toURI());
				Document doc = gloze.drop(m, list[i].toURI());
				if (positive) assertTrue(Utility.equalXML(xml,doc, true));
				else assertFalse(Utility.equalXML(xml,doc, true));
			} catch (Exception error) {
				fail(error.getMessage());
			}
		}
	}

//	/**
//	 * @param tests The tests to set.
//	 */
//	protected void setTests(File tests) {
//		this.tests = tests;
//	}

}
