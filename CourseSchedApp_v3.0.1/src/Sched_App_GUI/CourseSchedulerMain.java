package Sched_App_GUI;

import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class CourseSchedulerMain extends JApplet {
	
	/**
	 * Create the applet.
	 */
	private int viewID = 0;
	
	public int getViewID(){
		return this.viewID;
	}
	public void setView(int viewID){
		
		//clean mainPanel and remove components
		this.viewID = viewID;
		this.mainPanel.removeAll();
        this.mainPanel.repaint();
        
        int nextViewID = viewID;
        if(viewID == 2){
			Admin.ErrorLogInterface eli = new Admin.ErrorLogInterface(this);
			eli.initComponents(this.mainPanel);
			/*
			while(true){
				if(eli.checkCurrentViewID() != viewID){
					nextViewID = eli.checkCurrentViewID();
					break;
				}
			}
			*/
			//switchView(nextViewID);
		}
        else if(viewID == 0){
        	initComponents();
        }

	}
	
	public JPanel getMainPanel(){
		return mainPanel;
	}
	
	public void switchView(int nextViewID){
		setView(nextViewID);
	}
	
	public CourseSchedulerMain() {
		
		this.mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().setSize(1000,800);
		getContentPane().setVisible(true);
		
		initComponents();
		
		eventLoop();
	}
	
	public void eventLoop(){
		//
	}
	
	public void initComponents(){
		//TODO delete and replace with mainInterface run method
				btnAdminLogin = new JButton("Admin Login");
				
				//switch view when button is pressed
				btnAdminLogin.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						setView(2);
					}
					
				});
				
				//add button to panel
				mainPanel.add(btnAdminLogin);
	}
	
	private JPanel mainPanel;
	private JButton btnAdminLogin;
}
