package com.pierreyves.tool.implementation;

import java.util.Collection;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.Complexity;
import com.pierreyves.tool.model.ComplexityQueryResult;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.Explanation;

public class ComplexityQueryResultImpl implements ComplexityQueryResult {
	
	private final Complexity complexity;
	private final Explanation explanation;
	private final Collection<AxiomType> axiomsType;
	private final Collection<Constructor> constructors;
	
	
	ComplexityQueryResultImpl(Complexity pcomplexity, 
								Explanation pexplanation,
								Collection<AxiomType> paxiomsType,
								Collection<Constructor> pconstructors)
	{
		complexity = pcomplexity;
		explanation = pexplanation;
		constructors = pconstructors;
		axiomsType = paxiomsType;
	}
	
	
	@Override
	public Complexity getComplexity() {
		// TODO Auto-generated method stub
		return complexity;
	}

	@Override
	public Explanation getExplanation() {
		// TODO Auto-generated method stub
		return explanation;
	}


	@Override
	public Collection<AxiomType> getAxiomsType() {
		// TODO Auto-generated method stub
		return axiomsType;
	}


	@Override
	public Collection<Constructor> getConstructors() {
		// TODO Auto-generated method stub
		return constructors;
	}
	
}
