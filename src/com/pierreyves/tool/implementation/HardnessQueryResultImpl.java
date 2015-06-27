package com.pierreyves.tool.implementation;

import com.pierreyves.tool.model.Complexity;
import com.pierreyves.tool.model.Explanation;
import com.pierreyves.tool.model.Hardness;
import com.pierreyves.tool.model.HardnessQueryResult;

public class HardnessQueryResultImpl implements HardnessQueryResult {
	
	Hardness hardness;
	Explanation explanation;
	HardnessQueryResultImpl(Hardness phardness, Explanation pexplanation)
	{
		hardness = phardness;
		explanation = pexplanation;
	}
	@Override
	public Complexity getHardness() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Explanation getExplanation() {
		// TODO Auto-generated method stub
		return null;
	}
}
