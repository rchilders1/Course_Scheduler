/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sched_App_GUI;

import static Sched_App_GUI.SchedAppGUI.changeFont;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Calvin
 */
public class SchedTreeManager {
    /* Global Variable Declarations and Initializations */
    JCheckBox TakenCheckBox;                //Check box for student to choose which courses they have taken
    JPanel ColorPanel;                      //Color bar at the top of a course (RED = can't take, BLUE = can take, GREEN = have taken and passed)
    JPanel SchedTreePanel = new JPanel();   //Grid Panel that holds individual classes that are apart of the indicated program
    JPanel ElectivesPanel = new JPanel();   //Grid Panel that holds Electives courses.
    
    /**
     * Constructor with zero arguments
     */
    public void PanelCreator(){
        
    }
    
    /**
     * Creates full track tree from course list
     * 
     * @param CL 
     * @param tree 
     * @param electives 
     */
    public void TreeCreation(Course[] CL, JPanel tree, JPanel electives){    
        /* Local Variable Declarations and Initializations */
        JTabbedPane CoursePane;         //Holds the finished, next course to add to the Schedule Tree
        JTabbedPane EmptyPane;          //Empty place holder panels for the vacant grid spots
        SchedTreePanel = tree;          //
        ElectivesPanel = electives;
        
        SchedTreePanel.setLayout(new GridLayout(4,6,5,5));

        //This loops through the array and if the class pane has been initialized
        //with a name inserts it otherwise inserts the empty panels
        for (Course CL1 : CL) {
            if (CL1 != null) {
                CoursePane = PaneCreation(CL1, SchedTreePanel, ElectivesPanel);
                SchedTreePanel.add(CoursePane);
            } else {
                EmptyPane = new JTabbedPane();
                EmptyPane.setOpaque(false);
                EmptyPane.setVisible(false);
                SchedTreePanel.add(EmptyPane);
            }
        }

        SchedTreePanel.repaint();
        SchedTreePanel.revalidate();
        
        evaluatePrerequisites(SchedTreePanel);
    }
    
    /**
     * Checks for passed prerequisites then paints the course color appropriately
     * 
     * @param tree 
     */
    public void evaluatePrerequisites(JPanel tree){
        for(int i = 0; i< 24; i++){
                JTabbedPane NextPane = (JTabbedPane) tree.getComponent(i);
            
                if(NextPane.getTabCount() >= 2){
                    JPanel PrereqPanel = (JPanel) NextPane.getComponent(1);
                    JPanel OverPanel = (JPanel) NextPane.getComponent(0);
                    JCheckBox passedBox_1 = (JCheckBox) PrereqPanel.getComponent(2);
                    JCheckBox passedBox_2 = (JCheckBox) PrereqPanel.getComponent(5);
                    JCheckBox takenBox = (JCheckBox) OverPanel.getComponent(2);
                    JPanel color = (JPanel) OverPanel.getComponent(0);
                    boolean passed_1 = passedBox_1.isSelected();
                    boolean passed_2 = passedBox_2.isSelected();
                    boolean text_1 = (!"".equals(((JTextField) PrereqPanel.getComponent(1)).getText()));
                    boolean text_2 = (!"".equals(((JTextField) PrereqPanel.getComponent(4)).getText()));
                    boolean taken = (takenBox.isSelected());
                    
                    if(!taken){
                        if((text_1 && text_2 && passed_1 && passed_2) || (text_1 && passed_1 && !text_2) || (!text_1 && !text_2)){
                            color.setBackground(new java.awt.Color(0,0,150));
                            color.repaint();
                            color.revalidate();
                        }
                        else{
                            color.setBackground(new java.awt.Color(150,0,0));
                            color.repaint();
                            color.revalidate();
                        }
                    }
                }
        }
    }
    
    /**
     * Marks Prerequisites as passed or not
     * 
     * @param tree
     * @param name
     * @param passed 
     */
    public void checkOffPrerequisites(JPanel tree, String name, boolean passed){
        for(int i = 0; i< 24; i++){
            JTabbedPane NextPane = (JTabbedPane) tree.getComponent(i);

            if(NextPane.getTabCount() >= 2){
                JPanel prereqsPanel = (JPanel) NextPane.getComponent(1);
                JCheckBox prereq_1CheckBox = (JCheckBox) prereqsPanel.getComponent(2);
                JCheckBox prereq_2CheckBox = (JCheckBox) prereqsPanel.getComponent(5);
                String prereq_1 = ((JTextField) prereqsPanel.getComponent(1)).getText();
                String prereq_2 = ((JTextField) prereqsPanel.getComponent(4)).getText();

                if(prereq_1.equals(name)){
                    if(passed){
                        prereq_1CheckBox.setSelected(true);
                    }
                    else{
                        prereq_1CheckBox.setSelected(false);
                    }
                }
                else if(prereq_2.equals(name)){
                    if(passed){
                        prereq_2CheckBox.setSelected(true);
                    }
                    else{
                        prereq_2CheckBox.setSelected(false);
                    }
                }
                else{}
            }
        }    
    }
    
    /**
     * Creates the course representation in a tabbed pane with basic info on each class as well as the prereqs in another tab
     * 
     * @param c
     * @param row
     * @param i
     * @return 
     */
    public JTabbedPane PaneCreation(Course c, JPanel tree, JPanel ElectivesPanel){
        JTabbedPane NextClassPane = new JTabbedPane();
        NextClassPane.setBackground(new java.awt.Color(200,200,200));
        NextClassPane.setOpaque(true);
        NextClassPane.setVisible(true);
        courseResizeAdapter resizer = new courseResizeAdapter();
        tree.addComponentListener(resizer);
        //SelectMajorComboBox.addComponentListener(new SchedAppGUI.selectBoxResizeAdapter());
        
        //Overview Panel Component Declarations and Initializations
        JPanel OverviewPanel = new JPanel(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        JLabel ClassNameLabel = new JLabel(c.getDepartment() + " " + c.getID() + " " + c.getName());
        JLabel CreditHoursLabel = new JLabel("Credit Hours:");
        JLabel OfferedLabel = new JLabel("Offered:");
        JTextField OfferedTextField = new JTextField(c.getOffered());
        OfferedTextField.setEditable(false);
        JTextField CreditHourTextField = new JTextField(c.getCredithrs());
        TakenCheckBox = new JCheckBox("Taken");
        ColorPanel = new JPanel();
        
        //Prerequisites Panel Component Declarations and Initializations
        JPanel PrerequisitesPanel = new JPanel(new GridBagLayout());
        JLabel Prerequisite_1Label = new JLabel("Prerequisite 1:");
        JLabel Prerequisite_2Label = new JLabel("Prerequisite 2:");
        JTextField Prerequisite_1TextField = new JTextField();
        JTextField Prerequisite_2TextField = new JTextField();
        JCheckBox Prerequisite_1CheckBox = new JCheckBox("Passed");
        JCheckBox Prerequisite_2CheckBox = new JCheckBox("Passed");
        
        if(!c.getPrerequisites().isEmpty()){
            Prerequisite_1TextField.setText(c.getPrerequisites().get(0));
            Prerequisite_2TextField.setText(c.getPrerequisites().get(1));
        }
        
        Font CourseFont = new Font("Serif",0,14);
        
        //Panel Component Font Set
        OverviewPanel.setFont(CourseFont);

        
        //Miscellaneous Component settings
        ColorPanel.setBackground(new java.awt.Color(0,0,150));
        NextClassPane.setBackground(new java.awt.Color(100,0,100));
        OverviewPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Prerequisite_1TextField.setEditable(false);
        Prerequisite_2TextField.setEditable(false);
        Prerequisite_2CheckBox.setEnabled(false);
        Prerequisite_1CheckBox.setEnabled(false);
        OfferedTextField.setEditable(false);
        CreditHourTextField.setEditable(false);
        //OverviewPanel.setBackground(new java.awt.Color(150,150,150));
        //PrerequisitesPanel.setBackground(new java.awt.Color(150,150,150));
        TakenCheckBox.addActionListener(new checkTaken());

        
        //Overview Panel Components Added
        setColorConstraints(cons);
        OverviewPanel.add(ColorPanel,cons);
        cons.gridy++;
        setInfoConstraints(cons);
        cons.ipadx = 5;
        OverviewPanel.add(ClassNameLabel,cons);
        cons.gridx++;
        OverviewPanel.add(TakenCheckBox, cons);
        cons.gridy++;
        cons.gridx--;
        OverviewPanel.add(CreditHoursLabel, cons);
        cons.gridx++;
        OverviewPanel.add(CreditHourTextField, cons);
        cons.gridy++;
        cons.gridx--;
        OverviewPanel.add(OfferedLabel, cons);
        cons.gridx++;
        OverviewPanel.add(OfferedTextField, cons);
        cons.gridy++;
        cons.gridx--;
        
        changeFont(OverviewPanel, CourseFont);
        
        //Prerequisites Panel Components Added
        cons.gridx = 0;
        cons.gridy = 0;
        cons.gridwidth = 3;
        PrerequisitesPanel.add(Prerequisite_1Label, cons);
        cons.gridy++;
        cons.gridwidth = 2;
        PrerequisitesPanel.add(Prerequisite_1TextField, cons);
        cons.gridx += 2;
        cons.gridwidth = 1;
        PrerequisitesPanel.add(Prerequisite_1CheckBox, cons);
        cons.gridx = 0;
        cons.gridy++;
        cons.gridwidth = 3;
        PrerequisitesPanel.add(Prerequisite_2Label, cons);
        cons.gridy++;
        cons.gridwidth = 2;
        PrerequisitesPanel.add(Prerequisite_2TextField, cons);
        cons.gridx += 2;
        cons.gridwidth = 1;
        PrerequisitesPanel.add(Prerequisite_2CheckBox, cons);
        
        
        OverviewPanel.setMaximumSize(new Dimension(400,400));
        PrerequisitesPanel.setMaximumSize(new Dimension(400,400));
        
        NextClassPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        NextClassPane.setFont(CourseFont);
        NextClassPane.add(OverviewPanel, "Overview");
        NextClassPane.add(PrerequisitesPanel,"Prerequisites");
        
        return NextClassPane;
    }
    
     public void setColorConstraints(GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 1.0;
        c.weighty = 1.0;
    }
    
    public void setInfoConstraints(GridBagConstraints c){
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
    }
    
    class courseResizeAdapter extends ComponentAdapter{
        @Override public void componentResized(ComponentEvent e){
            Font NewFont = new Font("serif",0,(SchedTreePanel.getWidth()/115));
            changeFont(SchedTreePanel, NewFont);
            ElectivesPanel.setSize(SchedTreePanel.getWidth(), ElectivesPanel.getHeight());
        }
    }
    
    /*class selectBoxResizeAdapter extends ComponentAdapter{
        @Override public void componentResized(ComponentEvent e){
            Font NewFont = new Font("san-serif",1,SelectMajorComboBox.getWidth()/10);
            SelectMajorComboBox.setFont(NewFont);
            CourseTypeComboBox.setFont(NewFont);
        }
    }*/
    
    class checkTaken implements ActionListener{
        @Override public void actionPerformed(ActionEvent e){
            boolean passed = true;
            JCheckBox box = (JCheckBox) e.getSource();
            JPanel overPanel = (JPanel) box.getParent();
            JPanel color = (JPanel)overPanel.getComponent(0);
            JTabbedPane pane = (JTabbedPane) overPanel.getParent();
            JPanel tree = (JPanel) pane.getParent();
            JLabel labelName = (JLabel) overPanel.getComponent(1);
            String name = labelName.getText();
                
            if(box.isSelected()){
                color.setBackground(new java.awt.Color(0,150,0));
                color.repaint();
                color.revalidate();
            }
                
            else{
               color.setBackground(new java.awt.Color(0,0,150));
               color.repaint();
               color.revalidate();
               passed = false;
            }
            
            checkOffPrerequisites(tree,name,passed);
            evaluatePrerequisites(tree);
        }
    }
}
