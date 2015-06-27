package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.DecisionProblem;

public class DecisionProblemImpl implements DecisionProblem {
	private final String name;
	private final String id;
	
	DecisionProblemImpl(String pname,String pid)
	{
		name = pname;
		id = pid;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}
	

}
