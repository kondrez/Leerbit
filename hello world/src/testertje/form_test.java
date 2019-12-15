package testertje;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class form_test {
    private JButton button_test;
    private JTable table_test;
    private JTextField textField_test;
    private JLabel label_test;
    private JScrollPane scrollpane_test;
    private JPanel panel_test;


    private Connection DBConnection;

    public form_test() {
        button_test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    connect();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void connect() throws SQLException {

    }


    public static void main(String[] args) throws SQLException {
        JFrame test = new JFrame("loginscherm");               // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
        test.setContentPane(new form_test().panel_test);            // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel
        test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
        test.pack();                                                // hier worden alle gui elementen op dezelfde JFrame gezet
        test.setVisible(true);                                      // spreekt wel voor zich, hier word de JFrame "login" zichtbaar gemaakt

        /* making a connection to the database */
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "root";
        String password = "";
        DriverManager.getConnection(url, username, password);
    }
}
