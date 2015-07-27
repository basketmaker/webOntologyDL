package com.pierreyves.tool.implementation;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.clarkparsia.owlapi.explanation.BlackBoxExplanation;
import com.clarkparsia.owlapi.explanation.HSTExplanationGenerator;
import com.pierreyves.tool.model.Axiom;
import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.ComplexityQueryResult;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.DecisionProblem;
import com.pierreyves.tool.model.QueryManager;

public class QueryDL implements QueryManager {
	private  String path_ontology = "/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl";

	private AdapterIdLabel adapter = new AdapterIdLabel(path_ontology);
	private AxiomTypeConstructorEquivalence equivalenceAxiomConstructors= null;
	private OWLOntology ont;
	private OWLDataFactory df;
	private OWLReasoner reasoner;

	private Collection<AxiomType> roleAxioms = null;
	private Collection<AxiomType> conceptAxioms = null;
	private Collection<Constructor> roleConstructors = null;
	private Collection<Constructor> conceptConstructors = null;
	

	public QueryDL() {
		try {
			df = OWLManager.getOWLDataFactory();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File(path_ontology));
			reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
			
		} catch (Exception e) {
			System.out.println("Error loading ontology in QueryDL");
		}

		equivalenceAxiomConstructors = new AxiomTypeConstructorEquivalence(path_ontology,adapter,this);
	}

	@Override
	public ComplexityQueryResult getComplexity(DecisionProblem pdecisionProblem, 
			Collection<AxiomType> paxioms, Collection<Constructor> pconstructors) {
		/*
		 * get all the class and property we need in the request
		 */
		
		paxioms = equivalenceAxiomConstructors.equivalentAxioms(paxioms);
		pconstructors = equivalenceAxiomConstructors.equivalentConstructors(pconstructors);
		
		

		OWLClass decisionProblem = df.getOWLClass(adapter.getId(pdecisionProblem.getId()));
		OWLObjectProperty hasDesclogic = df.getOWLObjectProperty(adapter.getId("hasDescriptionLogic"));
		OWLClass descriptionLogic = df.getOWLClass(adapter.getId("DescriptionLogic"));
		OWLObjectProperty hasConstructorProperty = df.getOWLObjectProperty(adapter.getId("hasConstructor"));
		OWLObjectProperty hasAxiomProperty = df.getOWLObjectProperty(adapter.getId("hasAxiom"));

		// construct the set of axioms
		Set<OWLClass> axioms = new HashSet<>();
		Set<OWLClass> constructors = new HashSet<>();
		for(AxiomType c : paxioms)
		{
			axioms.add(df.getOWLClass(adapter.getId(c.getName())));
		}
		
		for(Constructor c : pconstructors)
		{
			constructors.add(df.getOWLClass(adapter.getId(c.getName())));
		}
		/*
		 * Now we can construct the request
		 */
		
		
		// MemberShip
		
		OWLClassExpression axiomsDisjonction = df.getOWLObjectUnionOf(axioms);
		OWLClassExpression constructorsDisjonction = df
				.getOWLObjectUnionOf(constructors);

		OWLClassExpression hasAxiomsOnly = df.getOWLObjectAllValuesFrom(
				hasAxiomProperty, axiomsDisjonction);
		OWLClassExpression hasConstructorsOnly = df.getOWLObjectAllValuesFrom(
				hasConstructorProperty, constructorsDisjonction);

		OWLClassExpression hasAxiomsOnlyAndHasConstructorsOnly = df
				.getOWLObjectIntersectionOf(hasConstructorsOnly, hasAxiomsOnly);
		
		
		//Hardness
		
		
		
		Set<OWLClassExpression> hasSome = new HashSet<>();
		
		for(OWLClass c : axioms)
		{
			hasSome.add(df.getOWLObjectSomeValuesFrom(hasAxiomProperty, c));
		}
		for(OWLClass c : constructors)
		{
			hasSome.add(df.getOWLObjectSomeValuesFrom(hasConstructorProperty, c));
		}
		
		OWLClassExpression hardnessPart = df.getOWLObjectIntersectionOf(hasSome);
		
		
		
		OWLClassExpression descLogicandUnionOfConstructorsAxioms = df
				.getOWLObjectIntersectionOf(descriptionLogic,
						hasAxiomsOnlyAndHasConstructorsOnly,hardnessPart);

		OWLClassExpression hasDesclogicSome = df.getOWLObjectSomeValuesFrom(
				hasDesclogic, descLogicandUnionOfConstructorsAxioms);
		OWLClassExpression request = df.getOWLObjectIntersectionOf(
				decisionProblem, hasDesclogicSome);
		
		/*
		VisitorRenderExpression visitor = new VisitorRenderExpression(ont,0);
		request.accept(visitor);
		System.out.println("expression : " + visitor.getExpression());
		*/
		
		NodeSet<OWLClass> subClasses = reasoner.getSuperClasses(request, true);

		String complexity = "";
		OWLClassExpression complexityOWL = null;
		for (OWLClass c : subClasses.getFlattened()) {

			if (!adapter.getLabel(c.getIRI()).toString().equals(pdecisionProblem.getId())) 
			{
				complexity += adapter.getLabel(c.getIRI())+" ";
				complexityOWL = c;
			}
		}
		
		
		

		Set<Axiom> axiomExplain = new HashSet<>();
		
		
		
		if(complexityOWL != null)
		{
			BlackBoxExplanation complexityResultExplanation = new BlackBoxExplanation(ont,new Reasoner.ReasonerFactory(), reasoner);
			HSTExplanationGenerator complexityResultExplanationGenerate = new HSTExplanationGenerator(complexityResultExplanation);
			
			OWLClassExpression unsatRequest = df.getOWLObjectIntersectionOf(request,
																			df.getOWLObjectComplementOf(complexityOWL));
			VisitorRenderExpression visitor = new VisitorRenderExpression(ont,0);
			unsatRequest.accept(visitor);
			System.out.println("expression : " + visitor.getExpression());
			
			
			Set<Set<OWLAxiom>> explanations = complexityResultExplanationGenerate.getExplanations(unsatRequest,1);
			for(Set<OWLAxiom> c : explanations)
			{
				System.out.println(" explications :");
				for(OWLAxiom d : c)
				{
					String comment ="";
					String label = "";
					for(OWLAnnotation e : d.getAnnotations())
					{
						if(e.getProperty().isComment())
							comment = ((OWLLiteral)e.getValue()).getLiteral();
						if(e.getProperty().isLabel())
							label = ((OWLLiteral)e.getValue()).getLiteral();
					}
					if(comment.length() != 0)
					{
						axiomExplain.add(new AxiomImpl(label,comment));
					}
				}
			}
		}
		
		
		return new ComplexityQueryResultImpl(new ComplexityImpl(complexity),
											 new ExplanationImpl(axiomExplain),
											 paxioms,
											 pconstructors);
	}

	@Override
	public Collection<AxiomType> getAllRoleAxioms() {
		if(roleAxioms == null)
		{
			Set<AxiomType> subCategoryReturn = new HashSet<>();
			OWLClass category = df.getOWLClass(adapter.getId("RoleAxiom"));
			NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
			for(OWLClass c : subCategory.getFlattened())
			{
				String label = "";
				String symbol = "";
				for(OWLAnnotation d : c.getAnnotations(ont))
				{
					if(d.getProperty().isLabel())
						label = ((OWLLiteral)d.getValue()).getLiteral();
					if(d.getProperty().equals(df.getOWLAnnotationProperty(adapter.getId("symbol"))))
						symbol = ((OWLLiteral)d.getValue()).getLiteral();
				}
				subCategoryReturn.add(new AxiomTypeImpl(label,symbol));
			}
			roleAxioms = subCategoryReturn;
		}
		return roleAxioms;
	}

	@Override
	public Collection<AxiomType> getAllConceptAxioms() {
		if(conceptAxioms == null)
		{
			Set<AxiomType> subCategoryReturn = new HashSet<>();
			OWLClass category = df.getOWLClass(adapter.getId("ConceptAxiom"));
			NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
			for(OWLClass c : subCategory.getFlattened())
			{
				String label = "";
				String symbol = "";
				for(OWLAnnotation d : c.getAnnotations(ont))
				{
					
					if(d.getProperty().isLabel())
						label = ((OWLLiteral)d.getValue()).getLiteral();
					if(d.getProperty().equals(df.getOWLAnnotationProperty(adapter.getId("symbol"))))
						symbol = ((OWLLiteral)d.getValue()).getLiteral();
				}
				subCategoryReturn.add(new AxiomTypeImpl(label,symbol));
			}
			return subCategoryReturn;
		}
		return conceptAxioms;
	}

	@Override
	public Collection<Constructor> getAllRoleConstructors() {
		if(roleConstructors == null)
		{
			Set<Constructor> subCategoryReturn = new HashSet<>();
			OWLClass category = df.getOWLClass(adapter.getId("RoleConstructor"));
			NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
			for(OWLClass c : subCategory.getFlattened())
			{
				String label = "";
				String symbol = "";
				for(OWLAnnotation d : c.getAnnotations(ont))
				{
					
					if(d.getProperty().isLabel())
						label = ((OWLLiteral)d.getValue()).getLiteral();
					if(d.getProperty().equals(df.getOWLAnnotationProperty(adapter.getId("symbol"))))
						symbol = ((OWLLiteral)d.getValue()).getLiteral();
				}
				subCategoryReturn.add(new ConstructorImpl(label,symbol));
			}
			roleConstructors = subCategoryReturn;
		}
		return roleConstructors;
	}

	@Override
	public Collection<Constructor> getAllConceptConstructors() {
		if(conceptConstructors == null)
		{
			Set<Constructor> subCategoryReturn = new HashSet<>();
			OWLClass category = df.getOWLClass(adapter.getId("ConceptConstructor"));
			NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
			for(OWLClass c : subCategory.getFlattened())
			{
				String label = "";
				String symbol = "";
				for(OWLAnnotation d : c.getAnnotations(ont))
				{
					if(d.getProperty().isLabel())
						label = ((OWLLiteral)d.getValue()).getLiteral();
					if(d.getProperty().equals(df.getOWLAnnotationProperty(adapter.getId("symbol"))))
						symbol = ((OWLLiteral)d.getValue()).getLiteral();
				}
				subCategoryReturn.add(new ConstructorImpl(label,symbol));
			}
			conceptConstructors = subCategoryReturn;
		}
		return conceptConstructors;
	}

	@Override
	public Collection<DecisionProblem> getAllDecisionProblems() {
		// TODO Auto-generated method stub
		Set<DecisionProblem> decisionProblems = new HashSet<>();
		decisionProblems.add(new DecisionProblemImpl("ABox \n consistency","ABoxConsistency"));
		decisionProblems.add(new DecisionProblemImpl("Concept \n Satisfiability","ConceptSatisfiability"));
		return decisionProblems;
	}
	
	
	
}
