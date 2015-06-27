package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Hardness;

public class HardnessImpl implements Hardness{
	private final String name;
	
	public HardnessImpl(String pname) {
		name = pname;
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

}
