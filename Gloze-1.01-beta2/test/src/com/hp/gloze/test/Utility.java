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
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**
 * @author steven.a.battle@googlemail.com
 */

package com.hp.gloze.test;

import java.util.StringTokenizer;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.hp.gloze.XMLBean;
import com.hp.gloze.www_w3_org_2001_XMLSchema.schema;

public class Utility {
	
	static boolean checkpoint(boolean b) {
		return b;
	}
		
	public static boolean equalXML(Node n1, Node n2) throws Exception {
		return checkpoint(equalXML(n1, n2, true));
	}

	public static boolean equalXML(Node n1, Node n2, boolean ignoreWhiteSpace)
			throws Exception {
		if (n1==null || n2==null) return checkpoint(false);
		switch (n1.getNodeType()) {
		case Node.DOCUMENT_NODE:
			boolean eq = n2.getNodeType() == Node.DOCUMENT_NODE 
					&& equal((Document) n1, (Document) n2, ignoreWhiteSpace);
//			if (!eq) XMLUtility.write((Document)n1,new PrintWriter(System.err));
			return eq;
		case Node.ATTRIBUTE_NODE:
			return checkpoint(n2.getNodeType() == Node.ATTRIBUTE_NODE && equal((Attr) n1,
					(Attr) n2));
		case Node.ELEMENT_NODE:
			return checkpoint(n2.getNodeType() == Node.ELEMENT_NODE && equal(
					(Element) n1, (Element) n2, ignoreWhiteSpace));
		case Node.ENTITY_REFERENCE_NODE:
			return checkpoint(n2.getNodeType() == Node.ENTITY_REFERENCE_NODE && equal(
					(EntityReference) n1, (EntityReference) n2));
		case Node.CDATA_SECTION_NODE:
			return checkpoint(n2.getNodeType() == Node.CDATA_SECTION_NODE && equal(
					(CDATASection) n1, (CDATASection) n2));
		case Node.TEXT_NODE:
			return checkpoint(n2.getNodeType() == Node.TEXT_NODE && equal((Text) n1,
					(Text) n2));
		case Node.PROCESSING_INSTRUCTION_NODE:
			return checkpoint(n2.getNodeType() == Node.PROCESSING_INSTRUCTION_NODE);
		default:
			return checkpoint(false);
		}
	}

	private static boolean equal(Text t1, Text t2) {
		boolean eq = t1.getNodeValue().trim().equals(t2.getNodeValue().trim());
		if (eq) return true;
		// treat as a list
		StringTokenizer tok1 = new StringTokenizer(t1.getTextContent());
		StringTokenizer tok2 = new StringTokenizer(t2.getTextContent());
		while (tok1.hasMoreTokens() && tok2.hasMoreTokens()) {
			String s1 = tok1.nextToken(), s2 = tok2.nextToken();
			// might be a qname or URI
			if (s1.indexOf(":")>=0 || s2.indexOf(":")>=0) {
				s1 = expand(s1,t1.getParentNode());
				s2 = expand(s2,t2.getParentNode());
			}
			if (!s1.equals(s2)) return checkpoint(false);
		}
		return checkpoint(true);
	}
	
	static String expand(String name, Node node) {
		if (schema.isValidURI(name)) return name;
		String prefix = "";
		int i = name.indexOf(':');
		if (i>=0) {
			prefix = name.substring(0, i);
			name = name.substring(i + 1);
		}
		return XMLBean.concatName(expandPrefix(prefix, node), name);
	}

	public static String expandPrefix(String prefix, Node node) {
		if (node!=null && prefix.equals(node.getPrefix()))
			return node.getNamespaceURI();

		// if this node has a namespace and no prefix, it must be the default
		if (prefix.equals("") && node.getNamespaceURI()!=null && node.getPrefix()==null)
			return node.getNamespaceURI();
		
		if (node.getNodeType()==Document.ELEMENT_NODE) {
			Element e = (Element) node;
			String a = prefix.equals("")?"xmlns":"xmlns:"+prefix;
			if (e.hasAttribute(a))
				return e.getAttribute(a);
		}
		// is it declared in the parent?
		if (node.getNodeType()!=Document.DOCUMENT_NODE) {
			Node parent = node instanceof Attr?parent =((Attr) node).getOwnerElement():node.getParentNode();
			return expandPrefix(prefix, parent);
		}	
		return null;
	
	}

	private static boolean equal(CDATASection cd1, CDATASection cd2) {
		return checkpoint(cd1.getNodeValue().equals(cd2.getNodeValue()));
	}

	private static boolean equal(EntityReference er1, EntityReference er2) {
		return checkpoint(er1.getNodeValue().equals(er2.getNodeValue()));
	}

	private static boolean equal(Element e1, Element e2, boolean ignoreWhiteSpace) throws Exception {
		if (e1==null || e2==null) return checkpoint(false);
		return checkpoint(
				equalElementTagNames(e1,e2)
				&& equalElementAttributes(e1, e2)
				&& equalElementAttributes(e2, e1)
				&& equalElementChildren(e1, e2, ignoreWhiteSpace));
	}
	
	private static boolean equalElementTagNames(Element e1, Element e2) {
		//boolean eq = e1.getTagName().equals(e2.getTagName());
		String name1 = e1.getNamespaceURI();
		name1 = name1==null ? name1 = e1.getTagName(): e1.getLocalName();

		String name2 = e2.getNamespaceURI();
		name2 = name2==null ? name2 = e2.getTagName(): e2.getLocalName();

		return checkpoint(name1.equals(name2));
	}

	private static boolean equalElementAttributes(Element e1, Element e2) throws Exception {
		boolean eq = true;
		NamedNodeMap attrs = e1.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++) {
			Attr a = (Attr) attrs.item(i);
			if (a.getName().startsWith("xmlns")) {
				// ignore namespace declaration
			} 
			// xml:lang has no local name to get
			else if (a.getName().equals("xml:lang")) {
				if (!a.getValue().equals(e2.getAttributeNode(a.getName()).getValue())) {
				checkpoint(eq = false);
				break;
				}
			} 
			else if (a.getName().equals("xsi:type")) {
				eq = true;
				break;
			}
			else if (a.getName().equals("xml:base")) {
				eq = true;
				break;
			}
			else if (a.getName().equals("xml:id")) {
				eq = a.getValue().equals(e2.getAttribute("xml:id"));
				break;
			}
			else if (a.getName().indexOf(":") > 0) {
				// qualified
				// ignore schemaLocation hints
				if ((a.getLocalName().equals("schemaLocation")
						|| a.getLocalName().equals("noNamespaceSchemaLocation"))
						&& a.getNamespaceURI().equals(schema.XSI)) {
					continue;
				}
				if (!equal(a, e2.getAttributeNodeNS(a.getNamespaceURI(), a.getLocalName()))) {
					checkpoint(eq = false);
					break;
				}
			} else if (!equal(a, e2.getAttributeNode(a.getName()))
				&& !equal(a, e2.getAttributeNodeNS(a.getNamespaceURI(),a.getName()))) {
				checkpoint(eq = false);
				break;
			}
		}
		return checkpoint(eq);
	}

	private static boolean equalElementChildren(Element e1, Element e2,
			boolean ignoreWhiteSpace) throws Exception {
		boolean eq = true;
		consolidateText(e1);
		consolidateText(e2);
		NodeList nl1 = e1.getChildNodes(), nl2 = e2.getChildNodes();
		int i1, i2;
		for (i1 = 0, i2 = 0; eq && i1 < nl1.getLength() && i2 < nl2.getLength(); i1++, i2++) {
			i1 = consume(nl1, i1, ignoreWhiteSpace);
			i2 = consume(nl2, i2, ignoreWhiteSpace);
			checkpoint(eq = equalXML(nl1.item(i1), nl2.item(i2), ignoreWhiteSpace));
		}
		i1 = consume(nl1, i1, ignoreWhiteSpace);
		i2 = consume(nl2, i2, ignoreWhiteSpace);
		eq &= i1==nl1.getLength() && i2==nl2.getLength();
		return checkpoint(eq);
	}
	
	static void consolidateText(Element e) {
		NodeList l = e.getChildNodes();
		Text agg = null;
		for (int i=0; i<l.getLength(); i++) {
			if (l.item(i).getNodeType()==Node.TEXT_NODE) {
				Text txt = (Text) l.item(i);
				if (agg!=null) {
					agg.setTextContent(agg.getTextContent()+txt.getTextContent());
					txt.setTextContent("");
				}
				else agg = txt;
			}
			else agg = null;
		}
	}

	/** consume whitespace and comments */

	private static int consume(NodeList nl1, int i1, boolean enabled) {
		while (enabled && i1 < nl1.getLength() 
		&& ((nl1.item(i1).getNodeType() == Node.TEXT_NODE
			&& (nl1.item(i1).getNodeValue()==null || nl1.item(i1).getNodeValue().trim().equals("")))
				|| nl1.item(i1).getNodeType() == Node.COMMENT_NODE))
			i1++;
		return i1;
	}

	private static boolean equal(Document d1, Document d2, boolean ignoreWhiteSpace) throws Exception {
		return checkpoint(equal(d1.getDocumentElement(), d2.getDocumentElement(),ignoreWhiteSpace));
	}

	private static boolean equal(Attr a1, Attr a2) {
		if (a1==null || a2==null) return false;
		String v1 = a1.getNodeValue(), v2 = a2.getNodeValue();
		// URIs may include an expanded base
		if (v1.startsWith("http://") || v2.startsWith("http://") 
				|| v1.endsWith(".xml") || v2.endsWith(".xml")) {
			return (v1.endsWith(v2) || v2.endsWith(v1));
		}
		return checkpoint(v1.equals(v2));
	}
	
	public static String getValue(Element element) {
		NodeList l = element.getChildNodes();
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < l.getLength(); i++) {
			switch (l.item(i).getNodeType()) {
			case Node.TEXT_NODE:
			case Node.CDATA_SECTION_NODE:
				s.append(l.item(i).getNodeValue());
			}
		}
		return s.toString();
	}

}
