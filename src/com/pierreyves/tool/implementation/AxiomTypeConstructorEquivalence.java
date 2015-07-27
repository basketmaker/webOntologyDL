package com.pierreyves.tool.implementation;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.QueryManager;

public class AxiomTypeConstructorEquivalence {
	private OWLDataFactory df;
	private Map<Set<AxiomType>, Set<AxiomType>> equivalentAxiom = new HashMap<>();
	private Map<Set<Constructor>, Set<Constructor>> equivalentConstructor = new HashMap<>();
	private QueryManager query;
	
	AxiomTypeConstructorEquivalence(String pontology, AdapterIdLabel padapter, QueryManager pquery)
	{
		query = pquery;
		OWLOntology ont = null;
		try {
			df = OWLManager.getOWLDataFactory();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File(pontology));
		} catch (Exception e) {
			System.out.println("Error in loading ontology in AxiomAndConstructor");
			System.exit(1);
		}
		Set<OWLClassAxiom> entities = ont.getGeneralClassAxioms();
		String expression;
		for(OWLClassAxiom c : entities)
		{
			for(OWLAnnotation d : c.getAnnotations())
			{
				if(d.getProperty().equals(df.getOWLAnnotationProperty(padapter.getId("AxiomAndConstructorEquivalence"))))
				{
					expression = ((OWLLiteral)d.getValue()).getLiteral();
					String[] equivalence = expression.split(":");
					String[] individualsLeft = equivalence[0].split("/");
					boolean isInConstructors = isInConstructor(individualsLeft[0]);
					String[] individualsRight = equivalence[1].split("/");
					
					/*
					 * individuals left should have less member than right one
					 * 
					 */
					if(individualsLeft.length > individualsRight.length)
					{
						String tmp[] = individualsLeft;
						individualsLeft = individualsRight;
						individualsRight = tmp;
					}
					if(isInConstructors)
					{

						Set<Constructor> constructorsLeft = new HashSet<>();
						for(String individual : individualsLeft)
						{
							constructorsLeft.add(getConstructor(individual));
						}
						
						HashSet<Constructor> constructorsRight = new HashSet<>();
						for(String individual : individualsRight)
						{
							constructorsRight.add(getConstructor(individual));
						}
						equivalentConstructor.put(constructorsLeft, constructorsRight);
					}
					else
					{
						HashSet<AxiomType> axiomLeft = new HashSet<>();
						for(String individual : individualsLeft)
						{
							axiomLeft.add(getAxiomType(individual));
						}
						HashSet<AxiomType> axiomRight = new HashSet<>();
						for(String individual : individualsRight)
						{
							axiomRight.add(getAxiomType(individual));
						}

						System.out.println("left");
						for(AxiomType a : axiomLeft)
							System.out.println(a.getName());

						System.out.println("right");
						for(AxiomType b : axiomRight)
							System.out.println(b.getName());
						equivalentAxiom.put(axiomLeft, axiomRight);
					}
				}
			}
		}

	}	
	
	
	
	public Collection<Constructor> equivalentConstructors(Collection<Constructor> pliste)
	{
		HashSet<Constructor> listeReturn = new HashSet<>();
		for(Set<Constructor> expression : equivalentConstructor.keySet())
			if(isSequenceConstructorInList(expression,pliste))
				for(Constructor c : equivalentConstructor.get(expression))
					listeReturn.add(c);
		return listeReturn;
	}
	
	public Collection<AxiomType> equivalentAxioms(Collection<AxiomType> pliste)
	{
		HashSet<AxiomType> listeReturn = new HashSet<>();
		for(Set<AxiomType> expression : equivalentAxiom.keySet())
			if(isSequenceAxiomInList(expression,pliste))
				for(AxiomType c : equivalentAxiom.get(expression))
					listeReturn.add(c);
		return listeReturn;
	}
	
	
	
	
	
	
	
	private boolean isSequenceAxiomInList(Collection<AxiomType> A, Collection<AxiomType> B)
	{
		for(AxiomType a : A)
			if(!B.contains(a))
				return false;
		return true;
	}
	private boolean isSequenceConstructorInList(Collection<Constructor> A, Collection<Constructor> B)
	{
		for(Constructor a : A)
			if(!B.contains(a))
				return false;
		return true;
	}
	
	
	private AxiomType getAxiomType(String individual)
	{
		for(AxiomType c : query.getAllConceptAxioms())
		{
			if(individual.equals(c.getName()))
			{
				return c;
			}
		}
		for(AxiomType c : query.getAllRoleAxioms())
		{
			if(individual.equals(c.getName()))
			{
				return c;
			}
		}
		return null;
	}
	private Constructor getConstructor(String individual)
	{
		for(Constructor c  : query.getAllConceptConstructors())
		{
			if(individual.equals(c.getName()))
			{
				return c;
			}
		}
		for(Constructor c  : query.getAllRoleConstructors())
		{
			if(individual.equals(c.getName()))
			{
				return c;
			}
		}
		return null;
	}
	private boolean isInConstructor(String individual)
	{
		for(Constructor c  : query.getAllConceptConstructors())
		{
			if(individual.equals(c.getName()))
			{
				return true;
			}
		}
		for(Constructor c  : query.getAllRoleConstructors())
		{
			if(individual.equals(c.getName()))
			{
				return true;
			}
		}
		return false;
	}
}
