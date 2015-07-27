package com.pierreyves.tool.model;

import java.util.Collection;

public interface QueryManager {
	/*
	 * Get all the role axioms 
	 * return a list of role axioms
	 * */
	public Collection<AxiomType> getAllRoleAxioms();
	/*
	 * Get all the concept axioms 
	 * return a list of concept axioms
	 * */
	public Collection<AxiomType> getAllConceptAxioms();
	/*
	 * Get all the role Constructors 
	 * return a list of role constructors 
	 * */
	public Collection<Constructor> getAllRoleConstructors();
	/*
	 * Get all the concept constructors 
	 * return a list of concept constructors 
	 * */
	public Collection<Constructor> getAllConceptConstructors();
	/*
	 * Get all the decision problems  
	 * return a list of decision problem 
	 * */
	public Collection<DecisionProblem> getAllDecisionProblems();
	/*
	 * Return the results of the complexity 
	 * we send it axioms and constructors that are checked and it return the result of complexity hardness and membership 
	 * and an explanation of the results
	 */
	public ComplexityQueryResult getComplexity(
			DecisionProblem pdecisionProblem, 
			Collection<AxiomType> axioms,
			Collection<Constructor> constructors);
	
}
