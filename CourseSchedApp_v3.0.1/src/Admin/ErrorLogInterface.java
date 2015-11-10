/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Admin;

import CourseSchedulerMain;
import java.io.*;
import java.util.*;

import javax.swing.*;

import java.awt.Container;
import java.awt.event.*;

public class ErrorLogInterface{

	private boolean running = true;
	private int nextViewID = 2;
    /**
     * Creates new form ErrorLogInterface
     */
    public ErrorLogInterface(CourseSchedulerMain ja) {
    	this.mainApplet = CourseSchedulerMain;
    	this.mainPanel = mainApplet.getMainPanel();
    }
    
    private void createTable(){
        //TODO replace with real database info
        Scanner testIn = null;
        
        //TODO database connection here
        try{
            File testfile = new File("C:\\Users\\Justin\\Desktop\\Course Scheduler\\Course_Scheduler\\CourseSchedApp_v3.0.1\\errorTest.txt");
            testIn = new Scanner(testfile);
        }
        catch(FileNotFoundException e){
            System.out.print("File Not Found...");
        }
        
        //initialize column counter
        int rows = 0;
        
        //initial table data
        Object tableData[][] = new Object[3][4];
        
        while(testIn.hasNext()){
            //store data
            String testLine[] = testIn.nextLine().split(",");
            //populate the row with error data
            tableData[rows][0] = testLine[0];
            tableData[rows][1] = testLine[1];
            tableData[rows][2] = testLine[2];
            tableData[rows][3] = testLine[3];
            //set a status combobox in every row
            rows++;
        }
        
        //create table
        ErrorLog = new JTable(rows, 4);/*{
            @Override
             public String getToolTipText( MouseEvent e )
            {
                int row = rowAtPoint( e.getPoint() );
                int column = columnAtPoint( e.getPoint() );

                Object value = getValueAt(row, column);
                return value == null ? null : value.toString();
            }
            
        };*/
        //set Table font
        ErrorLog.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        
        //set Table Model
        ErrorLog.setModel(new javax.swing.table.DefaultTableModel(
            tableData,
            new String [] {
                "ID", "Description", "Date", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        //setRowHeight
        ErrorLog.setRowHeight(50);
        mainPanel.add(ErrorLog);
    }
    
    private void getErrorData(int max_rows){
        createTable();
        
        //set each row's editor to the ErrorLogStatus ComboBox
        ErrorLog.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(errorLogStatus));
        
    }
    
                        
    public void initComponents(JPanel mainPanel) {
    	
        //initialize status selection categories
        errorLogStatus.addItem("New");
        errorLogStatus.addItem("Updated");
        errorLogStatus.addItem("Reviewing");
        errorLogStatus.addItem("Solved");

        this.mainPanel = mainPanel;
    	getErrorData(20); 
    	mainPanel.repaint();
        
    }          
             

    public int checkCurrentViewID() {
    	return nextViewID;
    }
    
    private JButton returnButton = new JButton("Return");
    private JPanel mainPanel;
    private CourseSchedulerMain mainApplet;
    private javax.swing.JTable ErrorLog;
    private JComboBox errorLogStatus = new JComboBox();
    /* 
       // Variables declaration - do not modify                     
    private javax.swing.JScrollPane ErrorLog_ScrollPanel;
    private javax.swing.JToggleButton ErrorLog_SubmitButton;
    private javax.swing.JLabel ErrorLog_Title;
    private javax.swing.JPanel ScrollContainer_Panel;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}*/
}