package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.AxiomType;

public class AxiomTypeImpl implements AxiomType {
	private final String name;
	
	AxiomTypeImpl(String pname)
	{
		name = pname;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
