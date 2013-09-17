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

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;


public class TestUnion extends XSTSUtility {
	
	public static String UNION = "test/xsts/XML/xml-schema-test-suite/2004-01-14/XMLSchemaTests/Tests/Datatypes/union";
	static Set<String> exclusions ;

	@BeforeClass public static void setUp() {
		// perform full restriction checking
		System.setProperty("gloze.validate","full");
		// don't trim whitespace automatically
		System.setProperty("gloze.trim","false");
	}
	
	@Test public void test_anyURI_float() {
		XSTSUtility.runTest(UNION+"/anyURI-float/Schema+Instance", exclusions);
	}
	
	@Test public void test_duration_decimal() {
		XSTSUtility.runTest(UNION+"/duration-decimal/Schema+Instance", exclusions);
	}
	
	@Test public void test_gMonthDay_gYearMonth() {
		XSTSUtility.runTest(UNION+"/gMonthDay-gYearMonth/Schema+Instance", exclusions);
	}
	
	@Test public void test_short_gYear() {
		XSTSUtility.runTest(UNION+"/short-gYear/Schema+Instance", exclusions);
	}
	
}
