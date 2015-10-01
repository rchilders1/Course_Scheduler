/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sched_App_GUI;

import java.util.ArrayList;

/**
 *
 * @author Calvin
 */
public class Course {
    private int ID;
    private int credithrs;
    private String name;
    private String department;
    private String offered;
    private ArrayList<String> prerequisites;
    
    //Mock constructor for testing purposes
    public Course(){
        this("Computer Science I");
    }
    public Course( String named ){
        this(1470, 3, named, "CSCI", "Always");
    }
    
    public Course(int id, int credits, String named, String dpt, String offrd, String prereq_1, String prereq_2){
        ID = id;
        credithrs = credits;
        name = named;
        department = dpt;
        offered = offrd;
        prerequisites = new ArrayList();
        
        prerequisites.add(prereq_1);
        prerequisites.add(prereq_2);
    }
    
    public Course ( int id, int credits, String named, String dpt, String offrd, String prereq_1){
        ID= id;
        credithrs = credits;
        name = named;
        department = dpt;
        offered = offrd;
        prerequisites = new ArrayList();
        
        prerequisites.add(prereq_1);
        prerequisites.add("");
    }
    
    public Course ( int id, int credits, String named, String dpt, String offrd){
        ID= id;
        credithrs = credits;
        name = named;
        department = dpt;
        offered = offrd;
        prerequisites = new ArrayList();
        prerequisites.add("");
        prerequisites.add("");
        
    }
    
    public int getID(){
        return ID;
    }

    public int getCredithrs(){
        return credithrs;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDepartment(){
        return department;
    }
    
    public String getOffered(){
        return offered;
    }
    
    public ArrayList<String> getPrerequisites(){
        return prerequisites;
    }
    
    public void setID(int ident){
        ID = ident;
    }
    
    public void setCredithrs(int crdthrs){
        credithrs = crdthrs;
    }
    
    public void setName(String nombre){
        name = nombre;
    }
    
    public void setDepartment(String dprt){
        department = dprt;
    }
    
    public void setOffered(String offer){
        offered = offer;
    }
    
    public void setPrerequisites(ArrayList prereqs){
        prerequisites = prereqs;
    }
}
