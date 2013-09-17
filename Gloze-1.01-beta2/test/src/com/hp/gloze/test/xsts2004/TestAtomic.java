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

public class TestAtomic {
	
	public static String ATOMIC = "test/xsts/XML/xml-schema-test-suite/2004-01-14/XMLSchemaTests/Tests/Datatypes/atomic";
	static Set<String> exclusions ;
	
	@BeforeClass public static void setUp() {
		// perform full restriction checking
		System.setProperty("gloze.validate","full");
		// don't trim whitespace automatically
		System.setProperty("gloze.trim","false");

		exclusions = XSTSUtility.exclusions();
	}
	
	@Test public void test_anyURI() {
		XSTSUtility.runTest(ATOMIC+"/anyURI/Schema+Instance", exclusions);
	}
	
	@Test public void test_base64Binary() {
		XSTSUtility.runTest(ATOMIC+"/base64Binary/Schema+Instance", exclusions);
	}

	@Test public void test_boolean() {
		XSTSUtility.runTest(ATOMIC+"/boolean/Schema+Instance", exclusions);
	}

	@Test public void test_byte() {
		XSTSUtility.runTest(ATOMIC+"/byte/Schema+Instance", exclusions);
	}

	@Test public void test_date() {
		XSTSUtility.runTest(ATOMIC+"/date/Schema+Instance", exclusions);
	}

	@Test public void test_dateTime() {
		XSTSUtility.runTest(ATOMIC+"/dateTime/Schema+Instance", exclusions);
	}

	@Test public void test_decimal() {
		XSTSUtility.runTest(ATOMIC+"/decimal/Schema+Instance", exclusions);
	}

	@Test public void test_double() {
		XSTSUtility.runTest(ATOMIC+"/double/Schema+Instance", exclusions);
	}

	@Test public void test_duration() {
		XSTSUtility.runTest(ATOMIC+"/duration/Schema+Instance", exclusions);
	}

	@Test public void test_float() {
		XSTSUtility.runTest(ATOMIC+"/float/Schema+Instance", exclusions);
	}

	@Test public void test_gDay() {
		XSTSUtility.runTest(ATOMIC+"/gDay/Schema+Instance", exclusions);
	}

	@Test public void test_gMonth() {
		XSTSUtility.runTest(ATOMIC+"/gMonth/Schema+Instance", exclusions);
	}

	@Test public void test_gMonthDay() {
		XSTSUtility.runTest(ATOMIC+"/gMonthDay/Schema+Instance", exclusions);
	}

	@Test public void test_gYear() {
		XSTSUtility.runTest(ATOMIC+"/gYear/Schema+Instance", exclusions);
	}

	@Test public void test_gYearMonth() {
		XSTSUtility.runTest(ATOMIC+"/gYearMonth/Schema+Instance", exclusions);
	}

	@Test public void test_hexBinary() {
		XSTSUtility.runTest(ATOMIC+"/hexBinary/Schema+Instance", exclusions);
	}

	@Test public void test_ID() {
		XSTSUtility.runTest(ATOMIC+"/ID/Schema+Instance", exclusions);
	}

	@Test public void test_int() {
		XSTSUtility.runTest(ATOMIC+"/int/Schema+Instance", exclusions);
	}

	@Test public void test_integer() {
		XSTSUtility.runTest(ATOMIC+"/integer/Schema+Instance", exclusions);
	}

	@Test public void test_language() {
		XSTSUtility.runTest(ATOMIC+"/language/Schema+Instance", exclusions);
	}

	@Test public void test_long() {
		XSTSUtility.runTest(ATOMIC+"/long/Schema+Instance", exclusions);
	}

	@Test public void test_Name() {
		XSTSUtility.runTest(ATOMIC+"/Name/Schema+Instance", exclusions);
	}

	@Test public void test_NCName() {
		XSTSUtility.runTest(ATOMIC+"/NCName/Schema+Instance", exclusions);
	}

	@Test public void test_negativeInteger() {
		XSTSUtility.runTest(ATOMIC+"/negativeInteger/Schema+Instance", exclusions);
	}

	@Test public void test_NMTOKEN() {
		XSTSUtility.runTest(ATOMIC+"/NMTOKEN/Schema+Instance", exclusions);
	}

	@Test public void test_nonNegativeInteger() {
		XSTSUtility.runTest(ATOMIC+"/nonNegativeInteger/Schema+Instance", exclusions);
	}

	@Test public void test_nonPositiveInteger() {
		XSTSUtility.runTest(ATOMIC+"/nonPositiveInteger/Schema+Instance", exclusions);
	}

	@Test public void test_normalizedString() {
		XSTSUtility.runTest(ATOMIC+"/normalizedString/Schema+Instance", exclusions);
	}

	@Test public void test_positiveInteger() {
		XSTSUtility.runTest(ATOMIC+"/positiveInteger/Schema+Instance", exclusions);
	}

	@Test public void test_QName() {
		XSTSUtility.runTest(ATOMIC+"/QName/Schema+Instance", exclusions);
	}

	@Test public void test_short() {
		XSTSUtility.runTest(ATOMIC+"/short/Schema+Instance", exclusions);
	}

	@Test public void test_string() {
		XSTSUtility.runTest(ATOMIC+"/string/Schema+Instance", exclusions);
	}

	@Test public void test_time() {
		XSTSUtility.runTest(ATOMIC+"/time/Schema+Instance", exclusions);
	}

	@Test public void test_token() {
		XSTSUtility.runTest(ATOMIC+"/token/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedByte() {
		XSTSUtility.runTest(ATOMIC+"/unsignedByte/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedInt() {
		XSTSUtility.runTest(ATOMIC+"/unsignedInt/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedLong() {
		XSTSUtility.runTest(ATOMIC+"/unsignedLong/Schema+Instance", exclusions);
	}

	@Test public void test_unsignedShort() {
		XSTSUtility.runTest(ATOMIC+"/unsignedShort/Schema+Instance", exclusions);
	}
	
}
