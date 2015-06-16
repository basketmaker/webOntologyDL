package com.pierreyves.ontology;

import java.io.File;
import java.util.HashMap;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

public class ViewConstructor {

	/**
	 * @param args
	 */
	private OWLOntology ont;
	private OWLDataFactory df;
	private OWLReasoner reasoner;
	private String prefix;
	
	public ViewConstructor(String pontology)
	{
		prefix = "http://www.semanticweb.org/sanae/ontologies/2015/4/desc-logic.owl";
		try
		{
			df = OWLManager.getOWLDataFactory();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File(pontology));
			reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		}
		catch(Exception e)
		{
			System.out.println("Error in loading ontology");
		}
	}
	
	public HashMap<String,String> getNameOfBox(String subclass)
	{
		OWLClass category = df.getOWLClass(IRI.create(prefix+"#"+subclass));
		NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
		HashMap<String,String> subCategoryReturn = new HashMap<String,String>();
		for(OWLClass c : subCategory.getFlattened())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel())
				{
					subCategoryReturn.put(((OWLLiteral)d.getValue()).getLiteral(), ((OWLLiteral)d.getValue()).getLiteral());
				}
			}
		}
		return subCategoryReturn;
	}
}
