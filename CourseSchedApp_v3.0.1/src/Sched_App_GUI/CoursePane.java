/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sched_App_GUI;

import static Sched_App_GUI.SchedAppGUI.changeFont;
import Sched_App_GUI.SchedTreeManager.checkTaken;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Calvin
 */
public class CoursePane extends JTabbedPane{
    /* Global Constants */
    final Color COLOR_PANE = new Color(150,150,150);
    final Color COLOR_OVER = new Color(200,200,200);
    final Color COLOR_PRER = new Color(150,150,150);
    final Color COLOR_BLUE = new Color(0,0,150);
    
    /* Global Variable Declarations and Initializations */
    /* Overview Tab */
    JPanel OverviewPanel = new JPanel(new GridBagLayout());
    GridBagConstraints cons = new GridBagConstraints();
    JPanel ColorPanel = new JPanel();                                //Color bar at the top of a course (RED = can't take, BLUE = can take, GREEN = have taken and passed)
    JLabel ClassNameLabel;
    JCheckBox TakenCheckBox = new JCheckBox("Taken");                //Check box for student to choose which courses they have taken
    JLabel CreditHoursLabel = new JLabel("Credit Hours:");
    JTextField CreditHourTextField;
    JLabel OfferedLabel = new JLabel("Offered:");
    JTextField OfferedTextField;
    Font CourseFont = new Font("Serif",0,14);
    
    /* Prerequisites Tab */
    JPanel PrerequisitesPanel = new JPanel(new GridBagLayout());
    JLabel Prerequisite_1Label = new JLabel("Prerequisite 1:");
    JLabel Prerequisite_2Label = new JLabel("Prerequisite 2:");
    JTextField Prerequisite_1TextField = new JTextField();
    JTextField Prerequisite_2TextField = new JTextField();
    JCheckBox Prerequisite_1CheckBox = new JCheckBox("Passed");
    JCheckBox Prerequisite_2CheckBox = new JCheckBox("Passed");
    
    /**
     * Constructor: 0 Arguments
     */
    public CoursePane(){
        /* Field Initializations */
        ClassNameLabel = new JLabel("NONE 0000 Empty Class");
        CreditHourTextField = new JTextField("0");
        OfferedTextField = new JTextField("NEVER");
        Prerequisite_1TextField.setText("NONE");
        Prerequisite_2TextField.setText("NONE");
        
        /* Miscellaneous Component Edits */
        CreditHourTextField.setEditable(false);
        OfferedTextField.setEditable(false);
        this.setBackground(COLOR_PANE);
        OverviewPanel.setBackground(COLOR_OVER);
        ColorPanel.setBackground(COLOR_BLUE);
        
        this.assemblePane();
    }
    
    /**
     * Creates a course pain based on course information passed in to it in @c
     * @param c 
     */
    public CoursePane(Course c, checkTaken Lstnr){
        /* Field Initializations */
        ClassNameLabel = new JLabel(c.getDepartment() + " " + c.getID() + " " + c.getName());
        CreditHourTextField = new JTextField(String.valueOf(c.getCredithrs()));
        OfferedTextField = new JTextField(c.getOffered());
        Prerequisite_1TextField.setText(c.getPrerequisites().get(0));
        Prerequisite_2TextField.setText(c.getPrerequisites().get(1));
        
        /* Miscellaneous Component Edits */
        CreditHourTextField.setEditable(false);
        OfferedTextField.setEditable(false);
        this.setBackground(COLOR_PANE);
        OverviewPanel.setBackground(COLOR_OVER);
        OfferedTextField.setBackground(COLOR_OVER);
        TakenCheckBox.setBackground(COLOR_OVER);
        CreditHourTextField.setBackground(COLOR_OVER);
        ColorPanel.setBackground(COLOR_BLUE);
        TakenCheckBox.addActionListener(Lstnr);
        Prerequisite_1TextField.setEditable(false);
        Prerequisite_2TextField.setEditable(false);
        Prerequisite_1CheckBox.setEnabled(false);
        Prerequisite_2CheckBox.setEnabled(false);
        
        assemblePane();
    }
    
    private void assemblePane(){
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
        add("Overview", OverviewPanel);
        
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
        
        add("Prerequisites", PrerequisitesPanel);
        
        //Safeguard against font-growth infinite loop
        OverviewPanel.setMaximumSize(new Dimension(400,400));
        PrerequisitesPanel.setMaximumSize(new Dimension(400,400));
        
        setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        setFont(CourseFont);
    }
    
    private void setColorConstraints(GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.gridheight = 1;
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.EAST;
        c.weightx = 1.0;
        c.weighty = 1.0;
    }
    
    private void setInfoConstraints(GridBagConstraints c){
        c.gridwidth = 1;
        c.insets = new Insets(5,5,5,5);
    }
    
    private static void changeFont ( Component component, Font font ){
        component.setFont ( font );
        if ( component instanceof Container ){
            for ( Component child : ( ( Container ) component ).getComponents () ){
                changeFont ( child, font );
            }
        }
    }
}
