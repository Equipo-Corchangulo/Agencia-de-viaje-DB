package jdbc;

import java.sql.*;

public class ConnectionProvider {
    private static String url = "jdbc:sqlite:C:\\Users\\blion\\Documents\\proyectosJosy\\Agencia-de-viaje-DB\\tierramedia2.db";
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
}
