package com.pierreyves.tool.implementation;

import java.util.Set;

import com.pierreyves.tool.model.Axiom;
import com.pierreyves.tool.model.Explanation;

public class ExplanationImpl implements Explanation {

	private final Set<Axiom> explanation;

	ExplanationImpl(Set<Axiom> pexplanation) {
		explanation = pexplanation;
	}

	@Override
	public Set<Axiom> getExplanation() {
		// TODO Auto-generated method stub
		return explanation;
	}
}
