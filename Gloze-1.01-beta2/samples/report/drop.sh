#!/bin/bash

CP=$JAVA_HOME/jre/lib/rt.jar:../../lib/gloze.jar:$JENA_HOME/lib/xercesImpl.jar:$JENA_HOME/lib/jena.jar:$JENA_HOME/lib/commons-logging-1.1.jar:$JENA_HOME/lib/icu4j_3_4.jar:$JENA_HOME/lib/log4j-1.2.12.jar:$JENA_HOME/lib/iri.jar

java -cp $CP -Dgloze.base=http://example.org/4Q99 com.hp.gloze.Gloze 4Q99.rdf http://www.example.com/IPO ipo.xsd http://www.example.com/Report report.xsd 
