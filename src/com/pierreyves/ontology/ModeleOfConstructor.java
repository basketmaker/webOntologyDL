package com.pierreyves.ontology;


import java.util.Collection;
import java.util.Set;
import java.util.HashSet;
import java.util.Observable;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.ComplexityQueryResult;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.DecisionProblem;
import com.pierreyves.tool.model.QueryManager;



public class ModeleOfConstructor extends Observable{ 

	  QueryManager request;
	  Set<AxiomType> axiomsSelect;
	  Set<Constructor> constructorsSelect;
	  
	  
	  
	  public ModeleOfConstructor(QueryManager prequest)
	  {
		  axiomsSelect = new HashSet<>();
		  constructorsSelect = new HashSet<>();
		  request = prequest;
	  }
	  
	  public void setRoleAxiom(AxiomType axiomtype, boolean check)
	  {
		  if(check)
			  if(!axiomsSelect.contains(axiomtype))
			  {
				  axiomsSelect.add(axiomtype);
				  setChanged();
			  }
		  if(!check)
			  if(axiomsSelect.contains(axiomtype))
			  {
				  axiomsSelect.remove(axiomtype);
				  setChanged();
			  }
		  notifyObservers();
	  }
	  public void setConceptConstructor(Constructor constructor, boolean check)
	  {
		  if(check)
			  if(!constructorsSelect.contains(constructor))
			  {
				  constructorsSelect.add(constructor);
				  setChanged();
			  }
		  if(!check)
			  if(constructorsSelect.contains(constructor))
			  {
				  constructorsSelect.remove(constructor);
				  setChanged();
			  }
		  notifyObservers();
	  }
	  public void setRoleConstructors(Constructor constructor, boolean check)
	  {
		  if(check)
			  if(!constructorsSelect.contains(constructor))
			  {
				  constructorsSelect.add(constructor);
				  setChanged();
			  }
		  if(!check)
			  if(constructorsSelect.contains(constructor))
			  {
				  constructorsSelect.remove(constructor);
				  setChanged();
			  }
		  notifyObservers();
	  }
	  public void setConceptAxioms(AxiomType axiomtype, boolean check)
	  {
		  if(check)
			  if(!axiomsSelect.contains(axiomtype))
			  {
				  axiomsSelect.add(axiomtype);
				  setChanged();
			  }
		  if(!check)
			  if(axiomsSelect.contains(axiomtype))
			  {
				  axiomsSelect.remove(axiomtype);
				  setChanged();
			  }
		  notifyObservers();
	  }
	  
	  public ComplexityQueryResult getComplexityResult(DecisionProblem pdecisionProblem)
	  {
		  return request.getComplexity(pdecisionProblem, axiomsSelect, constructorsSelect);
	  }
	  
	  
		public Collection<AxiomType> getAllRoleAxioms() {
			return request.getAllRoleAxioms();
		}

		
		public Collection<AxiomType> getAllConceptAxioms() {
			return request.getAllConceptAxioms();
		}

		
		public Collection<Constructor> getAllRoleConstructors() {
			return request.getAllRoleConstructors();
		}

		
		public Collection<Constructor> getAllConceptConstructors() {
			return request.getAllConceptConstructors();
		}
		
		public Collection<DecisionProblem> getAllDecisionProblems()
		{
			return request.getAllDecisionProblems();
		}
	  
}
