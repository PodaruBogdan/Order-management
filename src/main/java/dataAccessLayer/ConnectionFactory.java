package dataAccessLayer;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "java.sql.Driver";
    private static final String dbHost = "127.0.0.1";
    private static final String dbPort = "3306";
    private static final String database = "order_management"; //
    private static final String dbUser = "root";
    private static final String dbPassword = "kia";
    private static Connection conn;

    private static ConnectionFactory singleInstance = new ConnectionFactory();

    private ConnectionFactory(){
        try {
            Class.forName("java.sql.Driver"); // load driver
        } catch (ClassNotFoundException e) { //
            System.out.println("Driver not found " + e);
        }
        conn=createConnection();
    }

    /**
     * <p>private Connection createConnection</p>
     * Mehod used to create a connection to a mysql database scheme.
     * @return Connection(the connection established)
     */
    private Connection createConnection(){
        conn=null;
        try {
             conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + database + "?" + "user=" + dbUser + "&" + "password=" + dbPassword+"&useSSL=false");
        }catch(SQLException e){
            System.out.println("Connect not possible" + e);

        }
        return conn;
    }
    public static Connection getConnection(){return conn;}
    public static void close(Connection connection){}
    public static void close(Statement statement){}
    public static void close(ResultSet resultSet){}
}
