package com.pierreyves.ontology;

import java.io.File;
import java.util.HashMap;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/*
 * Developed by Pierre-Yves MAES 
 * Prerequisite the ontology must have different Labels name for classes axioms and Properties
 * This code make two HashMap<String,String> 
 * idToLabel key id value label
 * labelToId key label value id
 * Have fun
 */


public class AdapterIdLabel {
	private OWLOntology ont;
	private OWLReasoner reasoner;
	private HashMap<String,String> idToLabel;
	private HashMap<String,String> labelToId;

	AdapterIdLabel(String pontology)
	{
		idToLabel = new HashMap<String,String>();
		labelToId = new HashMap<String,String>();
		String label;
		String id;
		try
		{		
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File(pontology));
			reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		}
		catch(Exception e)
		{
			System.out.println("Error in loading ontology");
		}
		Node<OWLClass> classes = reasoner.getTopClassNode();
		for(OWLClass f : classes.getEntities() )
			for(OWLClass c : reasoner.getSubClasses(f, false).getFlattened())
			{
				for(OWLAnnotation d : c.getAnnotations(ont))
				{
					if(d.getProperty().isLabel()){
						label = ((OWLLiteral)d.getValue()).getLiteral();
						id = c.getIRI().getFragment();
						labelToId.put(label,id);
						idToLabel.put(id, label);
					}
				}
			}
		Node<OWLObjectPropertyExpression> properties = reasoner.getTopObjectPropertyNode();
		for(OWLObjectPropertyExpression c : properties.getEntities())
			for( OWLObjectPropertyExpression d : reasoner.getSubObjectProperties(c, true).getFlattened())
			{
				
				for(OWLAnnotation e : d.getNamedProperty().getAnnotations(ont))
				{
					if(e.getProperty().isLabel())
					{
						id = d.getNamedProperty().getIRI().getFragment();
						label = ((OWLLiteral)e.getValue()).getLiteral();
						labelToId.put(label,id);
						idToLabel.put(id, label);
					}
				}
			}
		
	}
	HashMap<String,String> getIdToLabel()
	{
		return idToLabel;
	}
	HashMap<String, String> getLabelToId()
	{
		return labelToId;
	}
	String getId(String plabel)
	{
		return labelToId.get(plabel);
	}
	String getLabel(String pid)
	{
		return idToLabel.get(pid);
	}
	
}
