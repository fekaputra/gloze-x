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

public class TestList extends XSTSUtility {
	
	public static String LIST = "test/xsts/XML/xml-schema-test-suite/2004-01-14/XMLSchemaTests/Tests/Datatypes/list";
	static Set<String> exclusions ;

	@BeforeClass public static void setUp() {
		// perform full restriction checking
		System.setProperty("gloze.validate","full");
		// don't trim whitespace automatically
		System.setProperty("gloze.trim","false");
	}
		
	@Test public void test_anyURI() {
		XSTSUtility.runTest(LIST+"/anyURI/Schema+Instance", exclusions);
	}
	
	@Test public void test_base64Binary() {
		XSTSUtility.runTest(LIST+"/base64Binary/Schema+Instance", exclusions);
	}

	@Test public void test_boolean() {
		XSTSUtility.runTest(LIST+"/boolean/Schema+Instance", exclusions);
	}

	@Test public void test_byte() {
		XSTSUtility.runTest(LIST+"/byte/Schema+Instance", exclusions);
	}

	@Test public void test_date() {
		XSTSUtility.runTest(LIST+"/date/Schema+Instance", exclusions);
	}

	@Test public void test_dateTime() {
		XSTSUtility.runTest(LIST+"/dateTime/Schema+Instance", exclusions);
	}

	@Test public void test_decimal() {
		XSTSUtility.runTest(LIST+"/decimal/Schema+Instance", exclusions);
	}

	@Test public void test_double() {
		XSTSUtility.runTest(LIST+"/double/Schema+Instance", exclusions);
	}

	@Test public void test_duration() {
		XSTSUtility.runTest(LIST+"/duration/Schema+Instance", exclusions);
	}

	@Test public void test_float() {
		XSTSUtility.runTest(LIST+"/float/Schema+Instance", exclusions);
	}

	@Test public void test_gDay() {
		XSTSUtility.runTest(LIST+"/gDay/Schema+Instance", exclusions);
	}

	@Test public void test_gMonth() {
		XSTSUtility.runTest(LIST+"/gMonth/Schema+Instance", exclusions);
	}

	@Test public void test_gMonthDay() {
		XSTSUtility.runTest(LIST+"/gMonthDay/Schema+Instance", exclusions);
	}

	@Test public void test_gYear() {
		XSTSUtility.runTest(LIST+"/gYear/Schema+Instance", exclusions);
	}

	@Test public void test_gYearMonth() {
		XSTSUtility.runTest(LIST+"/gYearMonth/Schema+Instance", exclusions);
	}

	@Test public void test_hexBinary() {
		XSTSUtility.runTest(LIST+"/hexBinary/Schema+Instance", exclusions);
	}

	@Test public void test_ID() {
		XSTSUtility.runTest(LIST+"/ID/Schema+Instance", exclusions);
	}

	@Test public void test_int() {
		XSTSUtility.runTest(LIST+"/int/Schema+Instance", exclusions);
	}

	@Test public void test_integer() {
		XSTSUtility.runTest(LIST+"/integer/Schema+Instance", exclusions);
	}

	@Test public void test_language() {
		XSTSUtility.runTest(LIST+"/language/Schema+Instance", exclusions);
	}

	@Test public void test_long() {
		XSTSUtility.runTest(LIST+"/long/Schema+Instance", exclusions);
	}

	@Test public void test_Name() {
		XSTSUtility.runTest(LIST+"/Name/Schema+Instance", exclusions);
	}

	@Test public void test_NCName() {
		XSTSUtility.runTest(LIST+"/NCName/Schema+Instance", exclusions);
	}

	@Test public void test_negativeInteger() {
		XSTSUtility.runTest(LIST+"/negativeInteger/Schema+Instance", exclusions);
	}

	@Test public void test_NMTOKEN() {
		XSTSUtility.runTest(LIST+"/NMTOKEN/Schema+Instance", exclusions);
	}

	@Test public void test_nonNegativeInteger() {
		XSTSUtility.runTest(LIST+"/nonNegativeInteger/Schema+Instance", exclusions);
	}

	@Test public void test_nonPositiveInteger() {
		XSTSUtility.runTest(LIST+"/nonPositiveInteger/Schema+Instance", exclusions);
	}

	@Test public void test_normalizedString() {
		XSTSUtility.runTest(LIST+"/normalizedString/Schema+Instance", exclusions);
	}

	@Test public void test_positiveInteger() {
		XSTSUtility.runTest(LIST+"/positiveInteger/Schema+Instance", exclusions);
	}

	@Test public void test_QName() {
		XSTSUtility.runTest(LIST+"/QName/Schema+Instance", exclusions);
	}

	@Test public void test_short() {
		XSTSUtility.runTest(LIST+"/short/Schema+Instance", exclusions);
	}

	@Test public void test_string() {
		XSTSUtility.runTest(LIST+"/string/Schema+Instance", exclusions);
	}

	@Test public void test_time() {
		XSTSUtility.runTest(LIST+"/time/Schema+Instance", exclusions);
	}

	@Test public void test_token() {
		XSTSUtility.runTest(LIST+"/token/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedByte() {
		XSTSUtility.runTest(LIST+"/unsignedByte/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedInt() {
		XSTSUtility.runTest(LIST+"/unsignedInt/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedLong() {
		XSTSUtility.runTest(LIST+"/unsignedLong/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedShort() {
		XSTSUtility.runTest(LIST+"/unsignedShort/Schema+Instance", exclusions);
	}
	

}
