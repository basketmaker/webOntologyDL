package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Complexity;

public class ComplexityImpl implements Complexity {
	private final String name;
	public ComplexityImpl(String pname)
	{
		name = pname;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
