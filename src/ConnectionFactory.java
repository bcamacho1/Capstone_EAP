

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class ConnectionFactory {
    //static reference to itself
    private static ConnectionFactory instance = new ConnectionFactory();
    public static final String URL = "jdbc:mysql://localhost/capstoneeap";
    public static final String USER = "root";
    public static final String PASSWORD = "1234";
    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";
 
    //private constructor
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            System.out.println("There is a problem, try again later.");
        }
    }
 
    private Connection createConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("ERROR: Unable to Connect to Database.");
        }
        return connection;
    }  
 
    public static Connection getConnection() {
        return instance.createConnection();
    }
}
