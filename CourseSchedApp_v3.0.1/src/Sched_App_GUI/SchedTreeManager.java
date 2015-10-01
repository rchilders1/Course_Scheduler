/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sched_App_GUI;

import static Sched_App_GUI.SchedAppGUI.changeFont;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
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
    checkTaken takenLstnr = new checkTaken();
    courseResizeAdapter resizer = new courseResizeAdapter();
    
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
        //JTabbedPane CoursePane;         //Holds the finished, next course to add to the Schedule Tree
        JTabbedPane EmptyPane;          //Empty place holder panels for the vacant grid spots
        SchedTreePanel = tree;          //
        ElectivesPanel = electives;
        
        SchedTreePanel.setLayout(new GridLayout(4,6,5,5));
        tree.addComponentListener(resizer);

        //This loops through the array and if the class pane has been initialized
        //with a name inserts it otherwise inserts the empty panels
        for (Course CL1 : CL) {
            if (CL1 != null) {
                //CoursePane = PaneCreation(CL1, SchedTreePanel, ElectivesPanel);
                CoursePane NextPane = new CoursePane(CL1,takenLstnr);
                SchedTreePanel.add(NextPane);
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
    
    class courseResizeAdapter extends ComponentAdapter{
        @Override public void componentResized(ComponentEvent e){
            Font NewFont = new Font("serif",0,(SchedTreePanel.getWidth()/115));
            changeFont(SchedTreePanel, NewFont);
            ElectivesPanel.setSize(SchedTreePanel.getWidth(), ElectivesPanel.getHeight());
        }
    }
    
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
