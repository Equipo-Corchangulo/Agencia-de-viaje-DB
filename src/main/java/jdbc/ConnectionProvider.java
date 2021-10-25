package jdbc;

import java.sql.*;

public class ConnectionProvider {
    private static String url = "jdbc:sqlite:"+ System.getProperty("user.dir")+"\\db\\tierramedia.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Connection conn = getConnection();

        PreparedStatement statement = conn.prepareStatement(query);
        return statement.executeQuery();
    }
    
    public static void executeUpdate(String query) throws SQLException {
    	Connection conn = getConnection();
    	PreparedStatement statement = conn.prepareStatement(query);
    	statement.executeUpdate();
    }
}
