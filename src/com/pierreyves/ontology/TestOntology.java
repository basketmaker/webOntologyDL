package com.pierreyves.ontology;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.HermiT.Reasoner;

import com.pierreyves.tool.implementation.QueryDL;



 
 
public class TestOntology{
	
	public static void main(String[] args) throws Exception {
		
		
		ModeleOfConstructor modele = new ModeleOfConstructor(new QueryDL());
		
		ViewOfDescLogic view = new ViewOfDescLogic(modele);
		new ControleOntology(modele, view);	
	}
}


