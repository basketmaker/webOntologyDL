package com.pierreyves.tool.implementation;

import java.util.Set;

import com.pierreyves.tool.model.Explanation;

public class ExplanationImpl implements Explanation{
	
	private final Set<String> explanation;
	
	ExplanationImpl(Set<String> pexplanation)
	{
		explanation = pexplanation;
	}

	@Override
	public Set<String> getExplanation() {
		// TODO Auto-generated method stub
		return explanation;
	}
}
