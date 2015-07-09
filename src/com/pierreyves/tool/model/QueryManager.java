package com.pierreyves.tool.model;

import java.util.Collection;

public interface QueryManager {
	public Collection<AxiomType> getAllRoleAxioms();

	public Collection<AxiomType> getAllConceptAxioms();

	public Collection<Constructor> getAllRoleConstructors();

	public Collection<Constructor> getAllConceptConstructors();

	public Collection<DecisionProblem> getAllDecisionProblems();
	
	public ComplexityQueryResult getComplexity(
			DecisionProblem pdecisionProblem, 
			Collection<AxiomType> axioms,
			Collection<Constructor> constructors);
	
}
