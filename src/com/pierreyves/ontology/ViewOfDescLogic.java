package com.pierreyves.ontology;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import com.pierreyves.tool.model.AxiomType;
import com.pierreyves.tool.model.Constructor;
import com.pierreyves.tool.model.DecisionProblem;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
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
	  
	private HashMap<DecisionProblem,JLabel> complexityAndHardnessResult;
	  
	  
	  
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
	    JPanel resultpanel = new JPanel();
	    JPanel textpanel = new JPanel();
	    
	    
	    
	    
	    createMenuCheckbox(conceptConstructorPanel, conceptConstructor, "Concept constructor");
	    createMenuCheckbox(roleConstructorsPanel, roleConstructors, "Role Constructors");
	    createMenuCheckbox(roleAxiomsPanel, roleAxioms, "Role Axiom");
	    createMenuCheckbox(conceptAxiomsPanel, conceptAxioms, "Concept of axioms");
	    
	    
	    
	    /*
	     * The results are here
	     * */
	    
	    
	    resultpanel.setLayout(new GridLayout(2,0));
	    textpanel.setLayout(new GridLayout(2,0));
	    
	    
	    complexityAndHardnessResult = new HashMap<>();
	    JPanel tmp;
	    for(DecisionProblem dec : modele.getAllDecisionProblems())
	    {
	    	complexityAndHardnessResult.put(dec,new JLabel());
	    	tmp = new JPanel();
	    	tmp.setLayout(new GridLayout(1,1));
	    	tmp.setBorder(new BevelBorder(BevelBorder.RAISED));
	    	tmp.add(new JLabel(dec.getName()));
	    	textpanel.add(tmp);
	    	tmp = new JPanel();
	    	tmp.setLayout(new GridLayout(1,1));
	    	tmp.setBorder(new BevelBorder(BevelBorder.RAISED));
	    	tmp.add(complexityAndHardnessResult.get(dec));
	    	resultpanel.add(tmp);
	    }
	    
	    
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
		  
		    if(obs instanceof ModeleOfConstructor)
		    {
		    	ModeleOfConstructor complexity = (ModeleOfConstructor)obs;
		    	for(DecisionProblem c : complexityAndHardnessResult.keySet())
		    	{
		    		complexityAndHardnessResult.get(c).setText(complexity.getComplexityResult(c).getComplexity().getName());
		    	}
		    } 
	    }
	    
	  private HashMap<Constructor, JCheckBox> createHashMapOfConstructorCheckBox(Collection<Constructor> pconstructors)
	  {
		  HashMap<Constructor, JCheckBox> constructorWithCheckbox = new HashMap<>();
		  for(Constructor constructor : pconstructors)
		  {
			  constructorWithCheckbox.put(constructor, 
					  					  new JCheckBox("<html>"+constructor.getName()+" <font color='red' size='4'>"+constructor.getSymbol()+"</font></html>"));
		  }
		  return constructorWithCheckbox;
	  }
	  
	  private HashMap<AxiomType, JCheckBox> createHashMapOfAxiomCheckBox(Collection<AxiomType> paxioms)
	  {
		  HashMap<AxiomType, JCheckBox> constructorWithCheckbox = new HashMap<>();
		  for(AxiomType axiom : paxioms)
		  {
			  constructorWithCheckbox.put(axiom, 
					  					  new JCheckBox("<html>"+axiom.getName()+" <font color='red' size='4'>"+axiom.getSymbol()+"</font></html>"));
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
