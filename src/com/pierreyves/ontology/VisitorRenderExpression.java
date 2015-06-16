package com.pierreyves.ontology;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLClassExpressionVisitor;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectHasSelf;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectOneOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;

public class VisitorRenderExpression implements OWLClassExpressionVisitor {
	
	String render_expression = "";
	private OWLOntology ont;
	
	public VisitorRenderExpression(OWLOntology pont)
	{
		ont = pont;
	}
	public String getExpression()
	{
		return render_expression;
	}
	
	private String getLabel(OWLClass c)
	{
		String label = "Il y a un probleme";
		for(OWLAnnotation d : c.getAnnotations(ont))
		{
			if(d.getProperty().isLabel()){
				label = ((OWLLiteral)d.getValue()).getLiteral();
			}
		}
		return label;
	}
	
	@Override
	public void visit(OWLClass ce) {
		// TODO Auto-generated method stub
		render_expression = getLabel(ce);
	}

	@Override
	public void visit(OWLObjectIntersectionOf ce) {
		// TODO Auto-generated method stub
		render_expression = "(";
		for(OWLClassExpression c : ce.getOperands())
		{
			VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
			c.accept(visitor);
			render_expression += visitor.getExpression()+" and "; 
		}
		if(render_expression.length() != 1)
			render_expression = render_expression.substring(0,render_expression.length()-4);
		render_expression+=")";
	}

	@Override
	public void visit(OWLObjectUnionOf ce) {
		// TODO Auto-generated method stub
		render_expression = "(";
		for(OWLClassExpression c : ce.getOperands())
		{
			VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
			c.accept(visitor);
			render_expression += visitor.getExpression()+" or "; 
		}
		if(render_expression.length() != 1)
			render_expression = render_expression.substring(0,render_expression.length()-4);
		render_expression+=")";
	}

	@Override
	public void visit(OWLObjectComplementOf ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		String retour ="";
		render_expression = "(";
		for(OWLObjectProperty  c : ce.getProperty().getObjectPropertiesInSignature())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel()){
					retour += ((OWLLiteral)d.getValue()).getLiteral();
				}
			}
		}
		VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		ce.getFiller().accept(visitor);
		render_expression += retour+" some ";
		render_expression += visitor.getExpression();
		render_expression += ")";
	}

	@Override
	public void visit(OWLObjectAllValuesFrom ce) {
		// TODO Auto-generated method stub
		String retour ="";
		render_expression = "(";
		for(OWLObjectProperty  c : ce.getProperty().getObjectPropertiesInSignature())
		{
			for(OWLAnnotation d : c.getAnnotations(ont))
			{
				if(d.getProperty().isLabel()){
					retour += ((OWLLiteral)d.getValue()).getLiteral();
				}
			}
		}
		VisitorRenderExpression visitor = new VisitorRenderExpression(ont);
		ce.getFiller().accept(visitor);
		render_expression += retour+" only ";
		render_expression += visitor.getExpression();
		render_expression += ")";
		
	}

	@Override
	public void visit(OWLObjectHasValue ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectMinCardinality ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectExactCardinality ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectMaxCardinality ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectHasSelf ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLObjectOneOf ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataSomeValuesFrom ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataAllValuesFrom ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataHasValue ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataMinCardinality ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataExactCardinality ce) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(OWLDataMaxCardinality ce) {
		// TODO Auto-generated method stub
		
	}

}
