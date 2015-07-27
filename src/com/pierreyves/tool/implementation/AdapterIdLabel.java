package com.pierreyves.tool.implementation;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

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
	private HashMap<IRI,String> idToLabel;
	private HashMap<String,IRI> labelToId;

	AdapterIdLabel(String pontology)
	{
		idToLabel = new HashMap<>();
		labelToId = new HashMap<>();
		String label;
		IRI id;
		try
		{		
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File(pontology));
		}
		catch(Exception e)
		{
			System.out.println("Error in loading ontology in adapterIdLabel");
			System.exit(1);
		}
		
		
		Set<OWLEntity> entities = ont.getSignature();
		
		for(OWLEntity c : entities)
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel()){
					
					
					label = ((OWLLiteral)d.getValue()).getLiteral();
					id = c.getIRI();
					labelToId.put(label,id);
					idToLabel.put(id, label);
				}
			}
		}
		
		
	}
	HashMap<IRI,String> getIdToLabel()
	{
		return idToLabel;
	}
	HashMap<String, IRI> getLabelToId()
	{
		return labelToId;
	}
	IRI getId(String plabel)
	{
		return labelToId.get(plabel);
	}
	String getLabel(IRI pid)
	{
		return idToLabel.get(pid);
	}
	
}
