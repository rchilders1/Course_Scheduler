/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Kendall the best programmer this side of the Mississippi
 */
public class CourseNode extends CourseNodeManager {
    private OverviewPanel panel;
    private PrereqTab prereq;
    private Font CourseFont;
    private Font PrereqFont;
    private final Color COLOR_PANE;
    private final Color COLOR_OVER;
    private final Color COLOR_PRER;
    private boolean dragging;
    private Dimension location;
    private JCheckBox steven;
    
    CourseNode()
    {
        panel = new OverviewPanel();
        prereq = new PrereqTab();
        COLOR_PANE = new Color(150,150,150);
        COLOR_OVER = new Color(200,200,200);
        COLOR_PRER = new Color(150,150,150);
    }
    
    CourseNode(Course course, boolean bool)
    {
        panel = new OverviewPanel(course, bool);
        prereq = new PrereqTab();
        COLOR_PANE = new Color(150,150,150);
        COLOR_OVER = new Color(200,200,200);
        COLOR_PRER = new Color(150,150,150);
    }
    
    private static void changeFont ( Component component, Font font )
    {
        component.setFont ( font );
            if ( component instanceof Container )
            {
                for ( Component child : ( ( Container ) component ).getComponents () )
                {
                changeFont ( child, font );
                }
            }   
    }
    
    public Dimension getDropLocation()
    {
        if(dragging == true)
        {
            createMiniCourseNode();
            return location;
        }
        else
            return null;
    }
    
    public Dimension getLocation()
    {
        return location;
    }
    
    public void setLocation(Dimension location)
    {
        this.location = location;
    }
    
    public void createMiniCourseNode()
    {
        CourseNode node = new CourseNode();
    }
}
