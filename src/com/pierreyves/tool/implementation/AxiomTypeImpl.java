package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.AxiomType;

public class AxiomTypeImpl implements AxiomType {
	private final String name;
	private final String symbol;
	
	AxiomTypeImpl(String pname)
	{
		name = pname;
		symbol = "";
	}
	AxiomTypeImpl(String pname, String psymbol)
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

