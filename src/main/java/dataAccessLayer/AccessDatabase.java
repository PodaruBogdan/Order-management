package dataAccessLayer;
import java.sql.*;

public class AccessDatabase {
    /**
     * <p>public static ResultSet executeQeury(String sql)</p>
     * Static method used to return a ResultSet of an executed query
     * given by a connection. Method is useful for SELECT statement.
     * @param sql - string used for query execution
     * @return ResultSet - the ResultSet return from executeQuery() method
     */
    public static ResultSet executeQuery(String sql) {
        ResultSet rs=null;
        try {
            Connection dbConnection = ConnectionFactory.getConnection();
            Statement s = dbConnection.createStatement();
            rs = s.executeQuery(sql);
            ConnectionFactory.close(rs);
            ConnectionFactory.close(s);
            ConnectionFactory.close(dbConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    /**
     * <p>public static ResultSet executeUpdate(String sql)</p>
     * Static method used to return a ResultSet of an executed query
     * given by a connection. Method is useful for UPDATE,DELETE and INSERT statements.
     * @param sql - string used for query execution
     * @return ResultSet - the ResultSet return from executeQuery() method
     */
    public static int executeUpdate(String sql) {
        try {
            Connection dbConnection = ConnectionFactory.getConnection();
            Statement s = dbConnection.createStatement();
            s.executeUpdate(sql);
            ConnectionFactory.close(s);
            ConnectionFactory.close(dbConnection);
            return 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;

        }
    }
    /**
     * <p>public static ResultSet executeQueryParameterized(String sql,int index,Object value)</p>
     * Static parameterized method used to return a ResultSet of an executed query
     * given by a connection. Method is useful for SELECT statements where a field is a variable.
     * @param sql - string used for query execution
     * @param index - index of the variable to be bound
     * @param value - the value to bound on the variable with index index
     * @return ResultSet - the ResultSet return from executeQuery() method
     */
    public static ResultSet executeQueryParameterized(String sql,int index,Object value){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement stmt= null;
        try {
            stmt = dbConnection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ResultSet rs=null;
        try {
            stmt.setObject(index,value);
            rs=stmt.executeQuery();
            ConnectionFactory.close(rs);
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(dbConnection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * <p>public static int executeUpdateParameterized(String sql,int index,Object value)</p>
     * Parameterized method for prepared statements .The variables to be bound are given in the
     * list of arguments.
     * @param sql - the string used as SQL statement
     * @param index - index of the variable to be bound ( evaluated from left to right in sql string)
     * @param value - the value of the variable to be bound
     * @return int (&#62;=0 , success and -1 in case of sql error)
     */
    public static int executeUpdateParameterized(String sql,int index,Object value){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement stmt= null;
        try {
            stmt = dbConnection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int rs=0;
        try {
            stmt.setObject(index,value);
            rs=stmt.executeUpdate();
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(dbConnection);
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;
        }
        return rs;
    }

    /**
     * <p>public static int executeUpdateParameterized(String sql,Object[] values,Object selvalue)</p>
     * Parameterized method for prepared statements .The variables to be bound are given in the
     * list of arguments.
     * @param sql - the sql string to be executed
     * @param values - the variables to be bound ( useful on UPDATE statement where there are more fields
     *               to be bounded)
     * @param selvalue - this the projection value of the sql statement
     * @return int(&#62;=0 in case of success, -1 in case of sql error)
     */
    public static int executeUpdateParameterized(String sql,Object[] values,Object selvalue){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement stmt= null;
        try {
            stmt = dbConnection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int rs=0;
        try {
            int i;
            for(i=0;i<values.length;i++)
                stmt.setObject(i+1,values[i]);
            stmt.setObject(i+1,selvalue);
            rs=stmt.executeUpdate();
            ConnectionFactory.close(stmt);
            ConnectionFactory.close(dbConnection);
        } catch (SQLException e) {
            //e.printStackTrace();
            return -1;
        }
        return rs;
    }

}
