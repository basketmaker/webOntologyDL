package com.pierreyves.tool.model;

import java.util.Collection;

/*
 * We have in this class the complexity and also the explanations 
 * for the results of this problems
 */

public interface ComplexityQueryResult {
	Complexity getComplexity();
	Explanation getExplanation();
	Collection<AxiomType> getAxiomsType();
	Collection<Constructor> getConstructors();
}
