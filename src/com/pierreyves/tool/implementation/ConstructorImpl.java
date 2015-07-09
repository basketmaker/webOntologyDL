package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Constructor;

public class ConstructorImpl implements Constructor {
	private final String name;
	private final String symbol;
	
	ConstructorImpl(String pname)
	{
		name = pname;
		symbol = "";
	}
	ConstructorImpl(String pname, String psymbol)
	{
		name = pname;
		symbol = psymbol;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public String getSymbol() {
		// TODO Auto-generated method stub
		return symbol;
	}

}
