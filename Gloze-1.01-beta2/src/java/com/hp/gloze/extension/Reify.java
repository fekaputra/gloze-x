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

package com.hp.gloze.extension;

import com.hp.hpl.jena.graph.Node;
import com.hp.hpl.jena.graph.Reifier;
import com.hp.hpl.jena.graph.Triple;
import com.hp.hpl.jena.reasoner.InfGraph;
import com.hp.hpl.jena.reasoner.rulesys.BindingEnvironment;
import com.hp.hpl.jena.reasoner.rulesys.RuleContext;
import com.hp.hpl.jena.reasoner.rulesys.builtins.BaseBuiltin;
/**
 * @author steven.a.battle@googlemail.com
 */
public class Reify extends BaseBuiltin {

	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.reasoner.rulesys.Builtin#getName()
	 */
	public String getName() {
		return "reify";
	}
	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.reasoner.rulesys.Builtin#getArgLength()
	 */
	public int getArgLength() {
		return 4;
	}
	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.reasoner.rulesys.Builtin#getURI()
	 */
	public String getURI() {
		return "http://www.hp.com/gloze/extensions/reify";
	}
	/* (non-Javadoc)
	 * @see com.hp.hpl.jena.reasoner.rulesys.Builtin#bodyCall(com.hp.hpl.jena.graph.Node[], int, com.hp.hpl.jena.reasoner.rulesys.RuleContext)
	 */
	public boolean bodyCall(Node[] args, int length, RuleContext context) {
		BindingEnvironment env = context.getEnv();
		InfGraph graph = context.getGraph();
		Node s = env.getGroundVersion(args[0]);
		Node p = env.getGroundVersion(args[1]);
		Node o = env.getGroundVersion(args[2]);
		try {
			Triple t = Triple.create(s,p,o);
			Reifier r = graph.getReifier();
			Node a = Node.createAnon();
			r.reifyAs(a,t);
			env.bind(args[3],a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void headAction(Node[] args, int length, RuleContext context) {
		BindingEnvironment env = context.getEnv();
		InfGraph graph = context.getGraph();
		Node s = env.getGroundVersion(args[0]);
		Node p = env.getGroundVersion(args[1]);
		Node o = env.getGroundVersion(args[2]);
		try {
			Triple t = Triple.create(s,p,o);
			context.add(t);
			Node a = Node.createAnon();
			Reifier r = graph.getReifier();
			r.reifyAs(a,t);
			env.bind(args[3],a);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}		
	}
}
