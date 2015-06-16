package com.pierreyves.ontology;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.semanticweb.elk.owlapi.ElkReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.HermiT.Reasoner;

import com.clarkparsia.owlapi.explanation.BlackBoxExplanation;
import com.clarkparsia.owlapi.explanation.HSTExplanationGenerator;
 
 
public class TestOntology{
	private static String prefix = "http://www.semanticweb.org/sanae/ontologies/2015/4/desc-logic.owl";
	
	public static void main(String[] args) throws Exception {
		
		
		
		ModeleOfConstructor modele = new ModeleOfConstructor();
		
		ViewOfDescLogic view = new ViewOfDescLogic(modele);
		ControleOntology controle = new ControleOntology(modele, view);
		
		
		
		
		
		/*
		 * This code display the direct subclasses of a class
		 */
		/*
		AdapterIdLabel adapter = new AdapterIdLabel("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
		ViewConstructor py = new ViewConstructor("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
		*/
		/*
		HashMap<String,String>	retour = py.getNameOfBox(adapter.getId("ConceptConstructor"));
		Iterator it = retour.entrySet().iterator();
		while(it.hasNext())
		{
	        Map.Entry pair = (Map.Entry)it.next();
	        System.out.println(pair.getKey() + " = " + pair.getValue());
	        it.remove(); // avoids a ConcurrentModificationException
		}
		*/
		
		
		//testOWLApiOntology();
	}
	
	
	
	public static void testOWLApiOntology() throws Exception
	{
		OWLDataFactory df = OWLManager.getOWLDataFactory();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ont = manager.loadOntologyFromOntologyDocument(new File("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl"));
		OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		AdapterIdLabel adapter = new AdapterIdLabel("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
		
		
		  OWLObjectProperty hasDesclogic = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasDescriptionLogic")));
		  OWLClass descriptionLogic = df.getOWLClass(IRI.create(prefix+"#"+adapter.getId("DescriptionLogic")));
		  OWLObjectProperty hasConstructorProperty = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasConstructor")));
		  OWLObjectProperty hasAxiomProperty = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasAxiom")));
		  
		  
		  OWLClassExpression hasDesclogicOnly = df.getOWLObjectAllValuesFrom(hasDesclogic, descriptionLogic);
		  
		  VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		  hasDesclogicOnly.accept(visitor);
		  System.out.println("expression : "+visitor.getExpression());
	}
	
	
	
	public static void testOWLAPIPizza() throws Exception
	{
		/*
		 * This code laod the ontology 
		 */
		OWLDataFactory df = OWLManager.getOWLDataFactory();
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		OWLOntology ont = manager.loadOntologyFromOntologyDocument(new File("/home/basketmaker/workspace/WEBServer/pizza.owl"));
		OWLReasoner reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		/*
		for(OWLAxiom c : ont.getAxioms())
			System.out.println(c);
		*/
		
		
		/*
		 * this code get all the proof to demonstrate something
		 * 
		 */
		
		/*
		BlackBoxExplanation pizzaExplanation = new BlackBoxExplanation(ont,new Reasoner.ReasonerFactory(), reasoner);
		HSTExplanationGenerator pizzaExplanationGenerate = new HSTExplanationGenerator(pizzaExplanation);
		
		
		OWLObjectProperty hasTopping = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasTopping"));
		OWLClass meat_topping =  df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#MeatTopping"));
		OWLClass pizza = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
		OWLClass laReine = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#LaReine"));
		OWLClassExpression hasTopping_meat = df.getOWLObjectSomeValuesFrom(hasTopping, meat_topping);
		OWLClassExpression pizzaHasMeat = df.getOWLObjectIntersectionOf(laReine,pizza.getObjectComplementOf());
		
		Set<Set<OWLAxiom>> PizzaResponse = pizzaExplanationGenerate.getExplanations(pizzaHasMeat);
		for(Set<OWLAxiom> it : PizzaResponse)
		{
			for(OWLAxiom axioms : it)
			{
				System.out.println(axioms.getAnnotations());
			}
			System.out.println("Other Explanation");
		}
		*/
		
		
		/*
		 * THis code show how to make queries with owl api
		 */
		
		
		
		if(reasoner.isConsistent())
			System.out.println("this ontology is consistant");
		//reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
		OWLObjectProperty hasTopping = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#hasTopping"));
		OWLClass meat_topping =  df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#MeatTopping"));
		OWLClass pizza = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl#Pizza"));
		//OWLObjectPropertyExpression pizza_hasTopping = df.getOWLSubClassOfAxiom(pizza,hasTopping);
		//OWLObjectPropertyExpression 
		//df.getOWLObjectSomeValuesFrom(,meatTopping);
		
		//OWLObjectPropertyExpression hello = df.getOWLDataUnionOf(dataRanges));
		
		OWLClassExpression hasTopping_meat = df.getOWLObjectSomeValuesFrom(hasTopping, meat_topping);
		OWLClassExpression pizzaHasMeat = df.getOWLObjectIntersectionOf(hasTopping_meat,pizza);
		
		//OWLSubClassOfAxiom axiom = df.getOWLSubClassOfAxiom(namedpizza,pizza);
		//System.out.println(reasoner.isEntailed(axiom));
		
		NodeSet<OWLClass> subClasses = reasoner.getSubClasses(pizza, true);
		
		for(OWLClass subClasse : subClasses.getFlattened())
		{
			System.out.println(subClasse);
			System.out.println(subClasse.getIRI());
			System.out.println(subClasse.getIRI().getFragment());
			System.out.println((subClasse));
		}
		
		reasoner.getClass();
	}
	
	


 
}


