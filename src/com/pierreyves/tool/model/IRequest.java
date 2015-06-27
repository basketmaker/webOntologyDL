package com.pierreyves.tool.model;

import java.util.Collection;


public interface IRequest {
	public ComplexityQueryResult getComplexity(DecisionProblem pdecisionProblem, Collection<AxiomType> axioms, Collection<Constructor> constructors);
	public Collection<AxiomType> getAllRoleAxioms();
	public Collection<AxiomType> getAllConceptAxioms();
	public Collection<Constructor> getAllRoleConstructors();
	public Collection<Constructor> getAllConceptConstructors();
	public Collection<DecisionProblem> getAllDecisionProblems();
}

