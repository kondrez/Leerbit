package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Vector;

public class personalDataScreen {
    private JTable table_PData;
    public JPanel panel_PData;
    private JScrollPane schrollPane_omTable;
    private JLabel label_message;
    private JButton button_submit;
    private JButton button_home;

    personalDataScreen(JFrame PData) throws SQLException {


        /* making a connection to the database, url is het adres van de database, met daarachter wat opties die nodig waren */
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "admin";                   // gebruikersnaam van je mySQL server
        String password = "admin";              // wachtwoord van je mySQL server

        Connection conn = null;                     // de variabel conn aanmaken, van het type connectie
        try {
            conn = DriverManager.getConnection(url, username, password);        // hier word daarwerkelijk de connectie gemaakt
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        /* als het goed is is er nu een connectie met de databse, nu moeten we de tabel uitlezen */

        // maak  de sql query als een string
        String query = "SELECT * FROM leerling;";

        // eerst moet je een Statement maken
        Statement st = null;
        try {
            assert conn != null;
            st = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // daarna een resultset, dit is een lijst met alle resultaten van de query
        ResultSet rs = null;
        try {
            assert st != null;
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // de tabel vullen, dmv de methode "buildTableModel"
        JTable table = new JTable(buildTableModel(rs));

        // Closes the Connection

        st.close();
        rs.close();
        conn.close();

        JOptionPane.showMessageDialog(null, new JScrollPane(table));

        button_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame optie = new JFrame("optie scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                optie.setContentPane(new optionScreen(optie).panel_optieScherm);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                optie.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                optie.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                PData.dispose();
            }
        });


    }

    public static DefaultTableModel buildTableModel(ResultSet rs)
            throws SQLException {

        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}
