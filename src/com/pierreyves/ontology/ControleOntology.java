package com.pierreyves.ontology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JCheckBox;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.Constructor;

public class ControleOntology {

	private ModeleOfConstructor modelConstructor;
	

	  public ControleOntology(ModeleOfConstructor pmodelConstructor, ViewOfDescLogic vue)
	  {
		
	    this.modelConstructor = pmodelConstructor;
	    HashMap<AxiomType, JCheckBox> axiomCheckBox = vue.getRoleAxioms();
	    for(final AxiomType name : axiomCheckBox.keySet())
	    {
	    	axiomCheckBox.get(name).addActionListener(new ActionListener() {
				boolean ischecked = false;
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(!ischecked)
					{
						ControleOntology.this.modelConstructor.setRoleAxiom(name, true);
						ischecked = true;
					}
					else
					{
						ControleOntology.this.modelConstructor.setRoleAxiom(name, false);
						ischecked = false;
					}
				}
			});
	    }
	    
	    axiomCheckBox = vue.getConceptAxioms();
	    for(final AxiomType name : axiomCheckBox.keySet())
	    {
	    	axiomCheckBox.get(name).addActionListener(new ActionListener() {
				boolean ischecked = false;
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(!ischecked)
					{
						ControleOntology.this.modelConstructor.setConceptAxioms(name, true);
						ischecked = true;
					}
					else
					{
						ControleOntology.this.modelConstructor.setConceptAxioms(name, false);
						ischecked = false;
					}
				}
			});
	    }
	    
	    
	    HashMap<Constructor,JCheckBox> constructorCheckBox = vue.getConceptConstructor();
	    for(final Constructor name : constructorCheckBox.keySet())
	    {
	    	constructorCheckBox.get(name).addActionListener(new ActionListener() {
				boolean ischecked = false;
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(!ischecked)
					{
						ControleOntology.this.modelConstructor.setConceptConstructor(name, true);
						ischecked = true;
					}
					else
					{
						ControleOntology.this.modelConstructor.setConceptConstructor(name, false);
						ischecked = false;
					}
				}
			});
	    }
	    constructorCheckBox = vue.getRoleConstructors();
	    for(final Constructor name : constructorCheckBox.keySet())
	    {
	    	constructorCheckBox.get(name).addActionListener(new ActionListener() 
	    	{
				boolean ischecked = false;
				@Override
				public void actionPerformed(ActionEvent ae) {
					if(!ischecked)
					{
						ControleOntology.this.modelConstructor.setRoleConstructors(name, true);
						ischecked = true;
					}
					else
					{
						ControleOntology.this.modelConstructor.setRoleConstructors(name, false);
						ischecked = false;
					}
				}
			});
	    }
	    

	  }
}
