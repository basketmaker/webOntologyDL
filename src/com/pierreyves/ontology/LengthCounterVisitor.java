package com.pierreyves.ontology;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;


public class LengthCounterVisitor implements OWLClassExpressionVisitor {

	private int length = 0;
	
	public int getLength()
	{
		return length;
	}
	
	@Override
	public void visit(OWLClass ce) {
		// TODO Auto-generated method stub
		length = 1;
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		// TODO Auto-generated method stub
		for(OWLClassExpression c : ce.getOperands())
		{
			LengthCounterVisitor visitor = new LengthCounterVisitor();
			c.accept(visitor);
			length+=visitor.getLength();
		}
	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		// TODO Auto-generated method stub
		for(OWLClassExpression c : ce.getOperands())
		{
			LengthCounterVisitor visitor = new LengthCounterVisitor();
			c.accept(visitor);
			length+=visitor.getLength();
		}
		
	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		// TODO Auto-generated method stub
		LengthCounterVisitor visitor = new LengthCounterVisitor();
		ce.getObjectComplementOf().accept(visitor);
		length+=visitor.getLength();
	}

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		LengthCounterVisitor visitor = new LengthCounterVisitor();
		
		this.length+=visitor.getLength();
		System.out.println("not yet implement ");
		
	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataHasValue ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		// TODO Auto-generated method stub
		System.out.println("I visited where i am not suppose to visit ");
		
	}
	
	

}
