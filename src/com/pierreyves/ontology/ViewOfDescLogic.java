package com.pierreyves.ontology;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.pierreyves.tool.implementation.AdapterIdLabel;
import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.Constructor;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;
	
public class ViewOfDescLogic extends JFrame implements Observer {


	  /**
	 * this class is the view of the java complexity calculus
	 */
	
	
	private static final long serialVersionUID = -1892287360468678740L;
	  
	
	  private HashMap<Constructor,JCheckBox> conceptConstructor;
	  private HashMap<Constructor,JCheckBox> roleConstructors;
	  private HashMap<AxiomType,JCheckBox> roleAxioms;
	  private HashMap<AxiomType,JCheckBox> conceptAxioms;
	  
	  private JLabel complexitySatText;
	  private JLabel complexityAConsText;
	  
	  
	  
	  public ViewOfDescLogic(ModeleOfConstructor modele) {
	    super("Ontology Test");
	    modele.addObserver(this);
	    Container content = getContentPane();
	    content.setLayout(new GridLayout(3,2)); //0,0 par défaut
	    
	    conceptConstructor = new HashMap<>();
		roleConstructors = new HashMap<>();
		roleAxioms = new HashMap<>();
		conceptAxioms = new HashMap<>();
	    
	    
		conceptConstructor = createHashMapOfConstructorCheckBox(modele.getAllConceptConstructors());
		roleConstructors = createHashMapOfConstructorCheckBox(modele.getAllRoleConstructors());
		roleAxioms = createHashMapOfAxiomCheckBox(modele.getAllRoleAxioms());
		conceptAxioms = createHashMapOfAxiomCheckBox(modele.getAllConceptAxioms());
		
		/*
		 * Now we can construct the view
		 */
		
	    JPanel conceptConstructorPanel = new JPanel();
	    JPanel roleConstructorsPanel = new JPanel();
	    JPanel roleAxiomsPanel = new JPanel();
	    JPanel conceptAxiomsPanel = new JPanel();
	    JPanel resultsConceptSatisfiabilitypanel = new JPanel();
	    JPanel resultsAboxConsistencypanel = new JPanel();
	    JPanel textAboxConsistencypanel = new JPanel();
	    JPanel textConceptSatisfiabilitypanel = new JPanel();
	    JPanel resultpanel = new JPanel();
	    JPanel textpanel = new JPanel();
	    
	    
	    
	    
	    createMenuCheckbox(conceptConstructorPanel, conceptConstructor, "Concept constructor");
	    createMenuCheckbox(roleConstructorsPanel, roleConstructors, "Role Constructors");
	    createMenuCheckbox(roleAxiomsPanel, roleAxioms, "Role Axiom");
	    createMenuCheckbox(conceptAxiomsPanel, conceptAxioms, "Concept of axioms");
	    
	    /*
	     * The results are here
	     * */
	    
	    
	    
	    resultsConceptSatisfiabilitypanel.setLayout(new GridLayout(1,1));
	    resultsConceptSatisfiabilitypanel.setBorder(new BevelBorder(BevelBorder.RAISED));
	    resultsAboxConsistencypanel.setLayout(new GridLayout(1,1));
	    resultsAboxConsistencypanel.setBorder(new BevelBorder(BevelBorder.RAISED));
	    textConceptSatisfiabilitypanel.setLayout(new GridLayout(1,1));
	    textConceptSatisfiabilitypanel.setBorder(new BevelBorder(BevelBorder.RAISED));
	    textAboxConsistencypanel.setLayout(new GridLayout(1,1));
	    textAboxConsistencypanel.setBorder(new BevelBorder(BevelBorder.RAISED));
	    
	    resultpanel.setLayout(new GridLayout(2,0));
	    textpanel.setLayout(new GridLayout(2,0));
	    
	    
	    textConceptSatisfiabilitypanel.add(new JLabel("Concept \n satisfiability "));
	    textAboxConsistencypanel.add(new JLabel("ABox \n Consistency"));
	    
	    complexitySatText = new JLabel("complexity concept sat");
	    complexityAConsText = new JLabel("complexity ABox consistency");
	    resultsConceptSatisfiabilitypanel.add(complexitySatText);
	    resultsAboxConsistencypanel.add(complexityAConsText);
	    
	    
	    
	    textpanel.add(textConceptSatisfiabilitypanel);
	    textpanel.add(textAboxConsistencypanel);
	    resultpanel.add(resultsConceptSatisfiabilitypanel);
	    resultpanel.add(resultsAboxConsistencypanel);
	    
	    
	    
	    content.add(conceptConstructorPanel);
	    content.add(roleConstructorsPanel);
	    content.add(roleAxiomsPanel);
	    content.add(conceptAxiomsPanel);
	    content.add(textpanel);
	    content.add(resultpanel);
	    
	    pack();
	    setVisible(true);
	  }
	  
	  
	  
	  
	  @Override
	  public void update(Observable obs, Object arg){
		  
		    if(obs instanceof ModeleOfConstructor){
		      ModeleOfConstructor complexity = (ModeleOfConstructor)obs;
		      this.complexityAConsText.setText(complexity.requestComplexity("ABoxConsistency")+" "+complexity.requestHardness("ABoxConsistency"));
		      this.complexitySatText.setText(complexity.requestComplexity("ConceptSatisfiability")+" "+complexity.requestHardness("ConceptSatisfiability"));
		    } 
	    }
	    
	  private HashMap<Constructor, JCheckBox> createHashMapOfConstructorCheckBox(Collection<Constructor> pconstructors)
	  {
		  HashMap<Constructor, JCheckBox> constructorWithCheckbox = new HashMap<>();
		  for(Constructor constructor : pconstructors)
		  {
			  constructorWithCheckbox.put(constructor, 
					  					  new JCheckBox(constructor.getName()));
		  }
		  return constructorWithCheckbox;
	  }
	  
	  private HashMap<AxiomType, JCheckBox> createHashMapOfAxiomCheckBox(Collection<AxiomType> paxioms)
	  {
		  HashMap<AxiomType, JCheckBox> constructorWithCheckbox = new HashMap<>();
		  for(AxiomType constructor : paxioms)
		  {
			  constructorWithCheckbox.put(constructor, 
					  					  new JCheckBox(constructor.getName()));
		  }
		  return constructorWithCheckbox;
	  }
	  
	  
	  
	  public HashMap<Constructor,JCheckBox> getConceptConstructor()
	  {
		  return conceptConstructor;
	  }
	  public HashMap<Constructor,JCheckBox> getRoleConstructors()
	  {
		  return roleConstructors;
	  }
	  public HashMap<AxiomType,JCheckBox> getRoleAxioms()
	  {
		  return roleAxioms;
	  }
	  public HashMap<AxiomType,JCheckBox> getConceptAxioms()
	  {
		  return conceptAxioms;
	  }
	  
	  
	   
	   
	   
	   
	   private void createMenuCheckbox(JPanel panel, HashMap<? extends Object, ? extends AbstractButton> mapOfButton, String name)
	   {
		   panel.setLayout(new GridLayout(mapOfButton.size()+1,0));
		   panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		   panel.add(new JLabel(name));
		   for(AbstractButton it : mapOfButton.values())
		   {
			   panel.add(it);
		   }
	   }
	   
	 

}
