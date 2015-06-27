package com.pierreyves.tool.implementation;

import java.io.File;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.ComplexityQueryResult;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.DecisionProblem;
import com.pierreyves.tool.model.HardnessQueryResult;
import com.pierreyves.tool.model.IRequest;

public class RequestDL implements IRequest {

	private AdapterIdLabel adapter = new AdapterIdLabel("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
	private OWLOntology ont;
	private OWLDataFactory df;
	private OWLReasoner reasoner;

	public RequestDL() {
		try {
			df = OWLManager.getOWLDataFactory();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			ont = manager.loadOntologyFromOntologyDocument(new File("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl"));
			reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		} catch (Exception e) {
			System.out.println("Error in loading ontology");
		}
	}

	@Override
	public ComplexityQueryResult getComplexity(DecisionProblem pdecisionProblem, 
			Collection<AxiomType> paxioms, Collection<Constructor> pconstructors) {
		/*
		 * get all the class and property we need in the request
		 */

		OWLClass decisionProblem = df.getOWLClass(adapter.getId(pdecisionProblem.getName()));
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

		OWLClassExpression axiomsDisjonction = df.getOWLObjectUnionOf(axioms);
		OWLClassExpression constructorsDisjonction = df
				.getOWLObjectUnionOf(constructors);

		OWLClassExpression hasAxiomsOnly = df.getOWLObjectAllValuesFrom(
				hasAxiomProperty, axiomsDisjonction);
		OWLClassExpression hasConstructorsOnly = df.getOWLObjectAllValuesFrom(
				hasConstructorProperty, constructorsDisjonction);

		OWLClassExpression hasAxiomsOnlyAndHasConstructorsOnly = df
				.getOWLObjectIntersectionOf(hasConstructorsOnly, hasAxiomsOnly);
		OWLClassExpression descLogicandUnionOfConstructorsAxioms = df
				.getOWLObjectIntersectionOf(descriptionLogic,
						hasAxiomsOnlyAndHasConstructorsOnly);

		OWLClassExpression hasDesclogicSome = df.getOWLObjectSomeValuesFrom(
				hasDesclogic, descLogicandUnionOfConstructorsAxioms);
		OWLClassExpression request = df.getOWLObjectIntersectionOf(
				decisionProblem, hasDesclogicSome);

		VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		request.accept(visitor);
		System.out.println("expression : " + visitor.getExpression());

		NodeSet<OWLClass> subClasses = reasoner.getSuperClasses(request, true);

		String complexity = "";
		for (OWLClass c : subClasses.getFlattened()) {

			if (!adapter.getLabel(c.getIRI().getFragment()).toString()
					.equals(pdecisionProblem)) {
				complexity = adapter.getLabel(c.getIRI().getFragment());
			}
		}
		return new ComplexityQueryResultImpl(new ComplexityImpl(complexity),null);

	}

	@Override
	public HardnessQueryResult getHardness(DecisionProblem pdecisionProblem, 
					Collection<AxiomType> paxioms, Collection<Constructor> pconstructors) {
		/*
		 * get all the class and property we need in the request
		 */

		OWLClass decisionProblem = df.getOWLClass(adapter.getId(pdecisionProblem.getName()));
		OWLObjectProperty hasDesclogic = df.getOWLObjectProperty(adapter.getId("hasDescriptionLogic"));
		OWLClass descriptionLogic = df.getOWLClass(adapter.getId("DescriptionLogic"));
		OWLObjectProperty hasConstructorProperty = df.getOWLObjectProperty(adapter.getId("hasConstructor"));
		OWLObjectProperty hasAxiomProperty = df.getOWLObjectProperty(adapter.getId("hasAxiom"));

		// construct the set of axioms
		Set<OWLClass> axioms = new HashSet<>();
		Set<OWLClass> constructors = new HashSet<>();
		for(AxiomType c : paxioms)
		{
			c.toString();
		}
		for( Constructor c : pconstructors)
		{
			c.toString();
		}

		/*
		 * Now we can construct the request
		 */
			

		Set<OWLClassExpression> hasSome = new HashSet<>();
		
		for(OWLClass c : axioms)
		{
			hasSome.add(df.getOWLObjectSomeValuesFrom(hasAxiomProperty, c));
		}
		for(OWLClass c : constructors)
		{
			hasSome.add(df.getOWLObjectSomeValuesFrom(hasConstructorProperty, c));
		}
		
		
		OWLClassExpression request = df.getOWLObjectIntersectionOf(hasSome);
		request = df.getOWLObjectIntersectionOf(descriptionLogic,request);
		request = df.getOWLObjectSomeValuesFrom(hasDesclogic,request);
		request = df.getOWLObjectIntersectionOf(decisionProblem,request);
		
		VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		request.accept(visitor);
		System.out.println("expression : " + visitor.getExpression());

		NodeSet<OWLClass> subClasses = reasoner.getSuperClasses(request, true);

		String hardness = "";
		for (OWLClass c : subClasses.getFlattened()) {

			if (!adapter.getLabel(c.getIRI().getFragment()).toString()
					.equals(pdecisionProblem)) {
				hardness = adapter.getLabel(c.getIRI().getFragment());
			}
		}
		return new HardnessQueryResultImpl(new HardnessImpl(hardness),null);
	}

	@Override
	public Collection<AxiomType> getAllRoleAxioms() {
		Set<AxiomType> subCategoryReturn = new HashSet<>();
		OWLClass category = df.getOWLClass(adapter.getId("ConceptConstructor"));
		NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
		for(OWLClass c : subCategory.getFlattened())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel())
				{
					String label = ((OWLLiteral)d.getValue()).getLiteral();
					subCategoryReturn.add(new AxiomTypeImpl(label));
				}
			}
		}
		return subCategoryReturn;
	}

	@Override
	public Collection<AxiomType> getAllConceptAxioms() {
		Set<AxiomType> subCategoryReturn = new HashSet<>();
		OWLClass category = df.getOWLClass(adapter.getId("ConceptConstructor"));
		NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
		for(OWLClass c : subCategory.getFlattened())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel())
				{
					String label = ((OWLLiteral)d.getValue()).getLiteral();
					subCategoryReturn.add(new AxiomTypeImpl(label));
				}
			}
		}
		return subCategoryReturn;
	}

	@Override
	public Collection<Constructor> getAllRoleConstructors() {
		Set<Constructor> subCategoryReturn = new HashSet<>();
		OWLClass category = df.getOWLClass(adapter.getId("RoleConstructor"));
		NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
		for(OWLClass c : subCategory.getFlattened())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel())
				{
					String label = ((OWLLiteral)d.getValue()).getLiteral();
					subCategoryReturn.add(new ConstructorImpl(label));
				}
			}
		}
		return subCategoryReturn;
	}

	@Override
	public Collection<Constructor> getAllConceptConstructors() {
		Set<Constructor> subCategoryReturn = new HashSet<>();
		OWLClass category = df.getOWLClass(adapter.getId("ConceptConstructor"));
		NodeSet<OWLClass> subCategory = reasoner.getSubClasses(category, true);
		for(OWLClass c : subCategory.getFlattened())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel())
				{
					String label = ((OWLLiteral)d.getValue()).getLiteral();
					subCategoryReturn.add(new ConstructorImpl(label));
				}
			}
		}
		return subCategoryReturn;
	}
	
	
}
