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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.gloze.Gloze;
import com.hp.gloze.extension.CountValues;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFWriter;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.rulesys.BuiltinRegistry;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;

public class TestOWL {	
	private static final String CONFIG = "testOWL.props";
	private static final String RULES = "test.rules";
	static final String LANG = "RDF/XML-ABBREV";
	static final boolean SILENT = true;
	static String xsd, rdf, owl, base;
	static Reasoner reasoner;
	String currentBase;
	
	Gloze gloze;
	
	@BeforeClass public static void setUp() throws IOException {
		Properties props = new Properties();
		props.load(TestOWL.class.getResourceAsStream(CONFIG));
		xsd = props.getProperty("xsd");
		rdf = props.getProperty("rdf");
		owl = props.getProperty("owl");

		System.setProperty("gloze.xmlns",props.getProperty("xmlns"));
		System.setProperty("gloze.verbose","false");
		System.setProperty("gloze.lang",LANG);
		System.setProperty("gloze.base", base = props.getProperty("base"));
		System.setProperty("gloze.class","intersectionOf");
		
		// register rule extensions
		BuiltinRegistry.theRegistry.register(new CountValues());

		InputStream in = TestOWL.class.getResourceAsStream(RULES);
		BufferedReader b = new BufferedReader(new InputStreamReader(in));
		List rules = Rule.parseRules(Rule.rulesParserFromReader(b));
		reasoner = new GenericRuleReasoner(rules);			
	}

	@Test public void testAll() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/all.xsd"));
		checkConsistency(m,new File(rdf+"/allElement.rdf"));
	}

	@Test public void testAnnotation() throws Exception {
		gloze = new Gloze(SILENT);
		runTest(new File(xsd+"/annotation.xsd"));
	}

	@Test public void testAttribute() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/attribute.xsd"));
		checkConsistency(m,new File(rdf+"/attributeDatatype.rdf"));
		checkConsistency(m,new File(rdf+"/attributeForm.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAnyAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAttributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/attributeID.rdf"));
		checkConsistency(m,new File(rdf+"/attributeLang.rdf"));
		checkConsistency(m,new File(rdf+"/attributeQName.rdf"));
		checkConsistency(m,new File(rdf+"/attributeSimpleType.rdf"));
	}

	@Test public void testAttributeGroup() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/attributeGroup.xsd"));
		checkConsistency(m,new File(rdf+"/attributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAnyAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/attributeGroupAttributeGroup.rdf"));
	}

	@Test public void testChoice() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/choice.xsd"));
		checkConsistency(m,new File(rdf+"/choiceAny.rdf"));
		checkConsistency(m,new File(rdf+"/choiceChoice.rdf"));
		checkConsistency(m,new File(rdf+"/choiceElement.rdf"));
		checkConsistency(m,new File(rdf+"/choiceElementMax.rdf"));
		checkConsistency(m,new File(rdf+"/choiceGroup.rdf"));
		checkConsistency(m,new File(rdf+"/choiceSequence.rdf"));
	}

	@Test public void testComplexContent() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/complexContent.xsd"));
		checkConsistency(m,new File(rdf+"/complexContentExtension.rdf"));
		checkConsistency(m,new File(rdf+"/complexContentRestriction.rdf"));
	}

	@Test public void testComplexType() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/complexType.xsd"));
		checkConsistency(m,new File(rdf+"/complexType.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeAll.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeAnyAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeAttributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeChoice.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeComplexContent.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeSequence.rdf"));
		checkConsistency(m,new File(rdf+"/complexTypeSimpleContent.rdf"));
	}

	@Test public void testElement() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/element.xsd"));
		checkConsistency(m,new File(rdf+"/element.rdf"));
		checkConsistency(m,new File(rdf+"/elementComplexType.rdf"));
		checkConsistency(m,new File(rdf+"/elementDatatype.rdf"));
		checkConsistency(m,new File(rdf+"/elementGlobalComplexType.rdf"));
		checkConsistency(m,new File(rdf+"/elementGlobalSimpleType.rdf"));
		checkConsistency(m,new File(rdf+"/elementIdentity.rdf"));
		checkConsistency(m,new File(rdf+"/elementID1.rdf"));
		checkConsistency(m,new File(rdf+"/elementID2.rdf"));
		checkConsistency(m,new File(rdf+"/elementID3.rdf"));
		checkConsistency(m,new File(rdf+"/elementID4.rdf"));
		checkConsistency(m,new File(rdf+"/elementNil.rdf"));
		checkConsistency(m,new File(rdf+"/elementNillable.rdf"));
		checkConsistency(m,new File(rdf+"/elementQName.rdf"));
		checkConsistency(m,new File(rdf+"/elementSimpleType.rdf"));
		checkConsistency(m,new File(rdf+"/elementSubstitution.rdf"));
	}

	@Test public void testExtension() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/extension.xsd"));
		checkConsistency(m,new File(rdf+"/extensionAnyAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/extensionAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/extensionAttributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/extensionSequence.rdf"));
	}
	
	@Test public void testGroup() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/group.xsd"));
		checkConsistency(m,new File(rdf+"/group.rdf"));
		checkConsistency(m,new File(rdf+"/groupAll.rdf"));
		checkConsistency(m,new File(rdf+"/groupChoice.rdf"));
		checkConsistency(m,new File(rdf+"/groupSequence.rdf"));
	}

	@Test public void testImport() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/import.xsd"));
		checkConsistency(m,new File(rdf+"/import.rdf"));
	}

	@Test public void testInclude() throws Exception {
		gloze = new Gloze(SILENT);
		runTest(new File(xsd+"/include.xsd"));
//		checkConsistency(m,new File(rdf+"/include.rdf"));
	}

	@Test public void testLink() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/link.xsd"));
		checkConsistency(m,new File(rdf+"/baseAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/baseAttribute1.rdf"));
		checkConsistency(m,new File(rdf+"/baseAttribute2.rdf"));
		checkConsistency(m,new File(rdf+"/baseElement.rdf"));
		checkConsistency(m,new File(rdf+"/baseElements.rdf"));
		checkConsistency(m,new File(rdf+"/baseOverride.rdf"));
		checkConsistency(m,new File(rdf+"/baseRoot.rdf"));
		checkConsistency(m,new File(rdf+"/baseUndefined.rdf"));
	}

	@Test public void testList() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/list.xsd"));
		checkConsistency(m,new File(rdf+"/listSimpleType.rdf"));
	}

	@Test public void testRedefine() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/redefine.xsd"));
		checkConsistency(m,new File(rdf+"/redefine.rdf"));
		checkConsistency(m,new File(rdf+"/redefineComplexType.rdf"));
		checkConsistency(m,new File(rdf+"/redefineGroup.rdf"));
		checkConsistency(m,new File(rdf+"/redefineSimpleType.rdf"));
	}

	@Test public void testRestriction() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/restriction.xsd"));
		checkConsistency(m,new File(rdf+"/restrictionAll.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionAnyAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionAttribute.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionAttributeGroup.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionChoice.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionEnumeration.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionFractionDigits.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionGroup.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionLength.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMaxExclusive.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMaxInclusive.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMaxLength.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMinExclusive.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMinInclusive.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionMinLength.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionPattern.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionSequence.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionSimpleType.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionTotalDigits.rdf"));
		checkConsistency(m,new File(rdf+"/restrictionWhiteSpace.rdf"));
	}

	@Test public void testSequence() throws Exception {
		gloze = new Gloze(SILENT);
		gloze.closed="false";
		OntModel m = runTest(new File(xsd+"/sequence.xsd"));
		checkConsistency(m,new File(rdf+"/sequenceAny.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceAnyLocal.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceAnyOther.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceAnyTNS.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceAnyURI.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceChoice.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceElement.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceElement1.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceElementMax.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceElementMax1.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceEmpty.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceGroup.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceSequence.rdf"));
		checkConsistency(m,new File(rdf+"/sequenceUnambiguous.rdf"));
	}

	@Test public void testSimpleContent() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/simpleContent.xsd"));
		checkConsistency(m,new File(rdf+"/simpleContentExtension.rdf"));
		checkConsistency(m,new File(rdf+"/simpleContentRestriction.rdf"));
	}

	@Test public void testSimpleType() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/simpleType.xsd"));
		checkConsistency(m,new File(rdf+"/simpleType.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeList1.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeList2.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeList2Empty.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeList3.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeList4.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeRestriction.rdf"));
		checkConsistency(m,new File(rdf+"/simpleTypeUnion.rdf"));
	}

	@Test public void testSubstitution() throws Exception {
		gloze = new Gloze(SILENT);
		runTest(new File(xsd+"/substitution.xsd"));
	}

	@Test public void testUnion() throws Exception {
		gloze = new Gloze(SILENT);
		OntModel m = runTest(new File(xsd+"/union.xsd"));
		checkConsistency(m,new File(rdf+"/unionSimpleType.rdf"));
	}

	@Test public void testEcore() throws Exception {
		gloze = new Gloze(SILENT);
		runTest(new File(xsd+"/ecore.xsd"));
	}

	private OntModel runTest(File src) {
		try {
			// cached schema don't get added to the inference model
			Gloze.clearCache();
//			setName(src.getName());
			Gloze.logger.info(src.getName());
			
			currentBase = base;
			if (currentBase.endsWith("/")) currentBase += changeSuffix(src.getName(), "owl");
			OntModel ont = gloze.xsd_to_owl(src, currentBase);

			OntModel ont1 = ModelFactory.createOntologyModel();
			// ignore all ontologies imported during testing
			ont1.getDocumentManager().setProcessImports(false);
			
			File owlDir = new File(owl);
			if (!owlDir.exists()) try {
				owlDir.mkdir();
			} catch (Exception e) {
				e.printStackTrace();
			}

			File target = new File(owl, changeSuffix(src.getName(), "owl"));
			if (target.exists()) {
				ont1.read(new FileInputStream(target),currentBase,LANG);
				assertTrue(ont.isIsomorphicWith(ont1));
			}
			// write it the first time
			else {
				// set base
				RDFWriter writer = ont.getWriter(LANG);	
				writer.setProperty("xmlbase", currentBase);
				writer.write(ont.getBaseModel(), new FileWriter(target), currentBase);
			}
			return ont;
		} 
		catch (Exception e) {
			fail(e.getMessage());
			return null;
		}
	}
	
	private void checkConsistency(OntModel ont, File rdf) {
		try {
			Model m = ModelFactory.createDefaultModel();
			m.add(ont);
			m.read(new FileInputStream(rdf),currentBase);
			InfModel inf = ModelFactory.createInfModel(reasoner,m);
			assertTrue(inf.validate().isValid());
		} 
		catch (Exception e) {
			fail(e.getMessage());			
		}
	}
	
	String changeSuffix(String name, String suffix) {
		return name.substring(0, name.lastIndexOf('.') + 1) + suffix;
	}


}
