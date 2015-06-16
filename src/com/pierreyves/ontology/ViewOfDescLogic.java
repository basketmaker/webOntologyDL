package com.pierreyves.ontology;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observer;
import java.util.Observable;
	
public class ViewOfDescLogic extends JFrame implements Observer {


	  /**
	 * this class is the view of the java complexity calculus
	 */
	
	
	private static final long serialVersionUID = -1892287360468678740L;
	  
	
	  private HashMap<String,JCheckBox> conceptConstructor;
	  private HashMap<String,JCheckBox> roleConstructors;
	  private HashMap<String,JCheckBox> roleAxioms;
	  private HashMap<String,JRadioButton> conceptAxioms;
	  
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
	    
	    
	    AdapterIdLabel adapter = new AdapterIdLabel("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
		ViewConstructor constructCheckBox = new ViewConstructor("/home/basketmaker/workspace/Desc-Logic-Onto/DescriptionLogicsOntology.owl");
	    
		createHashMapOfCheckBox(constructCheckBox.getNameOfBox(adapter.getId("ConceptConstructor")),conceptConstructor);
		createHashMapOfCheckBox(constructCheckBox.getNameOfBox(adapter.getId("RoleConstructor")),roleConstructors);
		createHashMapOfCheckBox(constructCheckBox.getNameOfBox(adapter.getId("RoleAxiom")),roleAxioms);
		createHashMapOfRadioButton(constructCheckBox.getNameOfBox(adapter.getId("ConceptAxiom")),conceptAxioms);
		
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
	    createMenuRadioButton(conceptAxiomsPanel, conceptAxioms, "Concept of axioms");
	    
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
		      this.complexitySatText.setText("coucou");
		      this.complexityAConsText.setText("coucou");
		      complexity.requestLogique("ABoxConsistency");
		    } 
	    }
	    
	  
	  
	  
	  
	  public HashMap<String,JCheckBox> getConceptConstructor()
	  {
		  return conceptConstructor;
	  }
	  public HashMap<String,JCheckBox> getRoleConstructors()
	  {
		  return roleConstructors;
	  }
	  public HashMap<String,JCheckBox> getRoleAxioms()
	  {
		  return roleAxioms;
	  }
	  public HashMap<String,JRadioButton> getConceptAxioms()
	  {
		  return conceptAxioms;
	  }
	  
	  
	   private void createHashMapOfCheckBox( HashMap<String,String> nameOfConcept, Map<String,JCheckBox> checkBoxes)
	   {
		    Iterator it = nameOfConcept.entrySet().iterator();
			while(it.hasNext())
			{
		        Map.Entry pair = (Map.Entry)it.next();
		        checkBoxes.put(pair.getKey().toString(),new JCheckBox(pair.getValue().toString()));
			}
	   }
	   private void createHashMapOfRadioButton( HashMap<String,String> nameOfConcept, Map<String,JRadioButton> checkBoxes)
	   {
		    Iterator it = nameOfConcept.entrySet().iterator();
			while(it.hasNext())
			{
		        Map.Entry pair = (Map.Entry)it.next();
		        checkBoxes.put(pair.getKey().toString(),new JRadioButton(pair.getValue().toString()));
			}
	   }
	   
	   private void createMenuCheckbox(JPanel panel, HashMap<String, ? extends AbstractButton> mapOfButton, String name)
	   {
		   panel.setLayout(new GridLayout(mapOfButton.size()+1,0));
		   panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		   panel.add(new JLabel(name));
		   for(AbstractButton it : mapOfButton.values())
		   {
			   panel.add(it);
		   }
	   }
	   private void createMenuRadioButton(JPanel panel, HashMap<String, ? extends AbstractButton> mapOfButton, String name)
	   {
		   ButtonGroup group = new ButtonGroup();
		   panel.setLayout(new GridLayout(mapOfButton.size()+1,0));
		   panel.setBorder(new BevelBorder(BevelBorder.RAISED));
		   panel.add(new JLabel(name));
		   for(AbstractButton it : mapOfButton.values())
		   {
			   panel.add(it);
			   group.add(it);
		   }
	   }
	   

}
