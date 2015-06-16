package com.pierreyves.ontology;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

public class ControleOntology {

	private ModeleOfConstructor modelConstructor;
	

	  public ControleOntology(ModeleOfConstructor pmodelConstructor, ViewOfDescLogic vue)
	  {
		
	    this.modelConstructor = pmodelConstructor;
	    HashMap<String, JCheckBox> tmp = vue.getRoleAxioms();
	    for(final String name : tmp.keySet())
	    {
	    	tmp.get(name).addActionListener(new ActionListener() {
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
	    
	    tmp = vue.getConceptConstructor();
	    for(final String name : tmp.keySet())
	    {
	    	tmp.get(name).addActionListener(new ActionListener() {
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
	    tmp = vue.getRoleConstructors();
	    for(final String name : tmp.keySet())
	    {
	    	tmp.get(name).addActionListener(new ActionListener() {
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
	    HashMap<String, JRadioButton> tmp2 = vue.getConceptAxioms();
	    for(final String name : tmp2.keySet())
	    {
	    	tmp2.get(name).addActionListener(new ActionListener() {
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

	  }
}
