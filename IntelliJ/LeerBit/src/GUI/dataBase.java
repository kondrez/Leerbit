package GUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

class dataBase {

    static void closeConnection(ResultSet rs) throws SQLException {
        rs.close();
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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return rs;
    }

    static void executeUpdate(String query) throws SQLException, FileNotFoundException {
        // connectie maken met de database

        Connection conn = makeConnection();

        Statement st = conn.createStatement();

        // de oude data uit te database verweideren
        st.executeUpdate(query);
    }
}
