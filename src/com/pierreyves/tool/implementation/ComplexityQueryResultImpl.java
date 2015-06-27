package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Complexity;
import com.pierreyves.tool.model.ComplexityQueryResult;
import com.pierreyves.tool.model.Explanation;

public class ComplexityQueryResultImpl implements ComplexityQueryResult {
	
	private final Complexity complexity;
	private final Explanation explanation;
	
	ComplexityQueryResultImpl(Complexity pcomplexity, Explanation pexplanation)
	{
		complexity = pcomplexity;
		explanation = pexplanation;
	}
	
	
	@Override
	public Complexity getComplexity() {
		// TODO Auto-generated method stub
		return complexity;
	}

	@Override
	public Explanation getExplanation() {
		// TODO Auto-generated method stub
		return explanation;
	}
	
}
