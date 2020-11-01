package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SwiftAccessor {

	private static final String URL = "jdbc:mysql://mysql3000.mochahost.com/roadlink_roadlink";
        //private static final String URL = "jdbc:mysql://localhost:3306/roadlink";
	//private static final String USNAME = "root";
        //private static final String PWORD = "";
	private static final String PWORD = "java@1986";
        private static final String USNAME = "roadlink_root";
	
	
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver"); 
		return DriverManager.getConnection(URL, USNAME, PWORD);
	}
	
	public static void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
	
}
