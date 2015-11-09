package Admin;
import java.sql.*;
import javax.swing.*;
public class DBConnection {
	 Connection conn=null;

	public static Connection dbConnector()
	{
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection("jdbc:mysql://coursescheduler.ddns.net:3306/coursescheduler","JamesBond", "!H@t3@l3c");
			//JOptionPane.showMessageDialog(null, "Connection successful");
			return conn;
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e);
			return null;
		}

}
}
