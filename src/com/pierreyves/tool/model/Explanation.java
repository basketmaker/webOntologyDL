package com.pierreyves.tool.model;

import java.util.Set;

public interface Explanation {
	/*
	 * The explanations of the query result 
	 * this is a set of axioms who are needed to demonstrate the result of complexity
	 */
	public Set<Axiom> getExplanation();
}
