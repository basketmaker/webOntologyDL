package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Axiom;

public class AxiomImpl implements Axiom{
	private final String name;
	private final String description;
	
	AxiomImpl(String pname, String pdescription)
	{
		name = pname;
		description = pdescription;
	}
	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return name;
	}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return description;
	}

}
