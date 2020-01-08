package GUI;

import java.sql.*;

class dataBase {

    static void closeResultSet(ResultSet rs)  {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static Connection makeConnection() {
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "admin";
        String password = "admin";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);        // hier word daarwerkelijk de connectie gemaakt
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return conn;
    }

    static ResultSet executeQuery(String query) throws SQLException {
        /* deze methode voert een query uit en slaat het resultaat op in een resultset */

        // connection to database
        Connection conn = makeConnection();

        Statement st;
        ResultSet rs = null;

        // eerst een resultset maken
        try {
            assert conn != null;
            st = conn.createStatement();

            // daarna een resultset, dit is een lijst met alle resultaten van de query
            assert st != null;
            rs = st.executeQuery(query);
        } catch (SQLException ex) {ex.printStackTrace();}
        conn.close();

        return rs;
    }
}
