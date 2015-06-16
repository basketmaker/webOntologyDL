package com.pierreyves.ontology;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
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


public class ModeleOfConstructor extends Observable{ 

	  private HashMap<String , Boolean > conceptConstructor = new HashMap<>();
	  private HashMap<String , Boolean > roleConstructors = new HashMap<>();
	  private HashMap<String , Boolean > roleAxioms = new HashMap<>();
	  private HashMap<String , Boolean > conceptAxioms = new HashMap<>();
	  
	  private AdapterIdLabel adapter = new AdapterIdLabel("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
	  private OWLOntology ont;
	  private OWLDataFactory df;
	  private OWLReasoner reasoner;
	  private String prefix = "http://www.semanticweb.org/sanae/ontologies/2015/4/desc-logic.owl";
	  
	  private String complexitySat;
	  private String complexityAbox;
	  /*
	  private String hardnessSat;
	  private String hardnessABox;
	  */
	  
	  public ModeleOfConstructor()
	  {
		  ViewConstructor tmp = new ViewConstructor("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
		  
		  for(String c : tmp.getNameOfBox(adapter.getId("ConceptConstructor")).keySet())
		  {
			  conceptConstructor.put(c, false);
		  }
		  for(String c : tmp.getNameOfBox(adapter.getId("RoleAxiom")).keySet())
		  {
			  roleAxioms.put(c, false);
		  }
		  for(String c : tmp.getNameOfBox(adapter.getId("ConceptAxiom")).keySet())
		  {
			  conceptAxioms.put(c, false);
		  }
		  for(String c : tmp.getNameOfBox(adapter.getId("RoleConstructor")).keySet())
		  {
			  roleConstructors.put(c, false);
		  }
		  
		  try
		  {		
			  df = OWLManager.getOWLDataFactory();
			  OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			  ont = manager.loadOntologyFromOntologyDocument(new File("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl"));
			  reasoner = new Reasoner.ReasonerFactory().createReasoner(ont);
		  }
		  catch(Exception e)
		  {
			  System.out.println("Error in loading ontology");
		  }
	  }
	  
	  
	  public void setRoleAxiom(String name, boolean value)
	  {
		  roleAxioms.put(name, value);
		  setChanged();
		  notifyObservers();
	  }
	  public void setConceptConstructor(String name, boolean value)
	  {
		  conceptConstructor.put(name, value);
		  setChanged();
		  notifyObservers();
	  }
	  public void setRoleConstructors(String name, boolean value)
	  {
		  roleConstructors.put(name, value);
		  setChanged();
		  notifyObservers();
	  }
	  public void setConceptAxioms(String name, boolean value)
	  {
		  conceptAxioms.put(name, value);
		  setChanged();
		  notifyObservers();
	  }
	  public void requestLogique(String pdecisionProblem)
	  {
		  /*
		   * get all the class and property we need in the request
		   */
		  
		  OWLClass decisionProblem = df.getOWLClass(IRI.create(prefix+"#"+adapter.getId(pdecisionProblem)));
		  OWLObjectProperty hasDesclogic = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasDescriptionLogic")));
		  OWLClass descriptionLogic = df.getOWLClass(IRI.create(prefix+"#"+adapter.getId("DescriptionLogic")));
		  OWLObjectProperty hasConstructorProperty = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasConstructor")));
		  OWLObjectProperty hasAxiomProperty = df.getOWLObjectProperty(IRI.create(prefix+"#"+adapter.getId("hasAxiom")));
		  
		  
		   // construct the set of axioms
		  Set< OWLClass > axioms = new HashSet<>();
		  Set< OWLClass > constructors = new HashSet<>();
		  feelSetOf(roleAxioms,axioms);
		  feelSetOf(conceptAxioms, axioms);
		  feelSetOf(roleConstructors,constructors);
		  feelSetOf(conceptConstructor,constructors);
		  
		  /*
		   * Now we can construct the request
		   */
		  
		  OWLClassExpression axiomsDisjonction = df.getOWLObjectUnionOf(axioms);
		  OWLClassExpression constructorsDisjonction = df.getOWLObjectUnionOf(constructors);
		  
		  
		  
		  OWLClassExpression hasAxiomsOnly = df.getOWLObjectAllValuesFrom(hasAxiomProperty, axiomsDisjonction);
		  OWLClassExpression hasConstructorsOnly = df.getOWLObjectAllValuesFrom(hasConstructorProperty, constructorsDisjonction);
		  
		  OWLClassExpression hasAxiomsOnlyOrHasConstructorsOnly = df.getOWLObjectUnionOf(hasConstructorsOnly,hasAxiomsOnly);
		  OWLClassExpression descLogicandUnionOfConstructorsAxioms = df.getOWLObjectIntersectionOf(descriptionLogic ,hasAxiomsOnlyOrHasConstructorsOnly);
		  
		  OWLClassExpression hasDesclogicSome = df.getOWLObjectSomeValuesFrom(hasDesclogic, descLogicandUnionOfConstructorsAxioms);
		  OWLClassExpression request = df.getOWLObjectIntersectionOf(decisionProblem,hasDesclogicSome);
		  
		   
		  VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		  request.accept(visitor);
		  System.out.println("expression : "+visitor.getExpression());
		  
		  NodeSet<OWLClass> subClasses = reasoner.getSuperClasses(request, true);
		  
		  
		  
		  for(OWLClass c : subClasses.getFlattened())
		  {
			  
			  System.out.println(adapter.getLabel(c.getIRI().getFragment()));
		  }
		  
		  
	  }
	  private void feelSetOf(HashMap<String, Boolean> setbool, Set<OWLClass> setclass)
	  {
		  for(String key : setbool.keySet())
		  {
			  if(setbool.get(key))
			  {
				  setclass.add(df.getOWLClass(IRI.create(prefix+"#"+adapter.getId(key))));
			  }
		  }
	  }
	  
	  
	  
	  
}
