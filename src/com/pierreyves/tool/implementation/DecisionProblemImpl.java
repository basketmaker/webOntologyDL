package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.DecisionProblem;

public class DecisionProblemImpl implements DecisionProblem {
	private final String name;
	
	DecisionProblemImpl(String pname)
	{
		name = pname;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
