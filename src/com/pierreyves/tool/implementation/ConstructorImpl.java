package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Constructor;

public class ConstructorImpl implements Constructor {
	private final String name;
	ConstructorImpl(String pname)
	{
		name = pname;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
