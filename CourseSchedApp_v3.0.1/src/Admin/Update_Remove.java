package Admin;
import java.sql.*;

import javax.swing.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

public class Update_Remove extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Update_Remove frame = new Update_Remove();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	Connection connect= null;
	public Update_Remove() {
		getContentPane().setBackground(new Color(128, 0, 128));
		connect=DBConnection.dbConnector();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 200, 863, 777);
		getContentPane().setLayout(null);
		//Add Scroll pane
		JScrollPane scroll= new JScrollPane();
		scroll.setBounds(12,0,821,534);
		getContentPane().add(scroll);
		//Table
		final JTable table = new JTable(){
		public Component prepareRenderer(TableCellRenderer renderer, int row, int column){
	        Component returnComp = super.prepareRenderer(renderer, row, column);
	        Color alternateColor = new Color(252,242,206);
	        Color whiteColor = Color.WHITE;
	        if (!returnComp.getBackground().equals(getSelectionBackground())){
	            Color bg = (row % 2 == 0 ? alternateColor : whiteColor);
	            returnComp .setBackground(bg);
	            bg = null;
	        }
			return returnComp;
		}
		};
		scroll.setViewportView(table);
		table.setBackground(Color.PINK);
        table.setForeground(Color.DARK_GRAY);
        table.setRowHeight(24);
        table.setFont(new Font("Arial", Font.BOLD, 16));
		//Model of Table
		DefaultTableModel model = new DefaultTableModel()
		{
			public Class<?> getColumnClass(int column)
			{
				switch(column)
				{
				case 0:
				return String.class;
				case 1:
					return String.class;
				case 2:
					return String.class;
				case 3:
					return String.class;
				case 4:
					return String.class;
				case 5:
					return Boolean.class;
					default :
						return String.class;
						
					}
				}
		};
		//Assign Model to table
		table.setModel(model);
		model.addColumn("Name/CRN");
		model.addColumn("Description");
		model.addColumn("Credit Hours");
		model.addColumn("Pre-Requisite");
		model.addColumn("Department");
		model.addColumn("Selected");
		table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
		
			try
			{
			String Name;
			String Desc;
			String Credits;
			String Prereqs;
			String Dept;
		String query1="select * from coursescheduler.courseinfo";
		PreparedStatement pst=connect.prepareStatement(query1);
		//PreparedStatement pst2=connect.prepareStatement(query2);
		ResultSet rs= pst.executeQuery();
		//ResultSet rs1=pst2.executeQuery();
		//int count=rs1.getInt(1);
		//JOptionPane.showMessageDialog(null, "dataconnection is good so far");
		
		//JCheckBox chk = new JCheckBox("New check box");
		while (rs.next()) {
            Name = rs.getString("course");
            Desc = rs.getString("description");
            Credits = rs.getString("credit");
            Prereqs = rs.getString("prereq1");
            Dept=rs.getString("title");
            model.addRow(new Object[]{Name, Desc, Credits, Prereqs,Dept,false});
        }
	}
	catch(Exception e)
	{
		JOptionPane.showMessageDialog(null, e);
	}
			
	//JOptionPane.showMessageDialog(null, table.getRowCount());
	
	//Obtain selected row
	JButton btnNewButton = new JButton("Update");
	btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnNewButton.setBounds(81, 547, 97, 41);
	getContentPane().add(btnNewButton);
		JButton btnNewButton_1 = new JButton("Remove");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					//JOptionPane.showMessageDialog(null, "I'm here");
					String col=null;
					boolean checked=false;
					 try
					 {
						 for(int i=0;i<table.getRowCount();i++)
							{
						   checked=Boolean.valueOf(table.getValueAt(i,5).toString());
						   col=table.getValueAt(i,0).toString();
						   if(checked)
							 {
								 JOptionPane.showMessageDialog(null, col+" will be removed");
								 PreparedStatement pst=connect.prepareStatement("delete from courseinfo where course=?");
								 pst.setString(1, col);
								pst.executeUpdate();
								if (table.getSelectedRow() != -1) {
						            // remove selected row from the model
						            model.removeRow(table.getSelectedRow());
						        }
							
								
							 }
							
							}
						 
						 
						}
				catch(Exception ex){
					JOptionPane.showMessageDialog(null, ex);
				}
				
				
				
			}
		});
		table.addNotify();
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_1.setBounds(614, 559, 110, 41);
		getContentPane().add(btnNewButton_1);
		
		
		
	}
}
