package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginScreen {
    /* hier worden de gui onderdelen gedeclareerd */
    private JPanel panel_Login;
    private JTextField textField_username;
    private JTextField textField_password;
    private JLabel label_Message;
    private JLabel label_username;
    private JLabel label_password;
    private JButton button_submit;

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        /* this is where the program starts running */
        /* code hieronder bouwt een scherm op, en laat dit zien */

        JFrame login = new JFrame("loginscherm");
        login.setContentPane(new loginScreen(login).panel_Login);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.pack();
        login.setVisible(true);


    }

    private loginScreen(JFrame login) throws SQLException {
        button_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 de code hierin is wat er gebeurt als de knop "button_submit" word ingedrukt
                 er word gekeken of het wachtwoord klopt. zo ja, doorsturen naar volgende scherm. zo nee message laten zien met "Try again!'
                */

                /* making a connection to the database, url is het adres van de database, met daarachter wat opties die nodig waren */
                String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
                String username = "admin";
                String password = "admin";

                Connection conn = null;
                try {
                    conn = DriverManager.getConnection(url, username, password);        // hier word daarwerkelijk de connectie gemaakt
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                /* nu moeten we het wachtwoord uitlezen */
                String ingevuldeUsername = textField_username.getText();
                String query = "SELECT password FROM user WHERE username = '" + ingevuldeUsername + "';";

                // eerst maak je een statement
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
                } catch (SQLException ex) {ex.printStackTrace();}

                // je moet het resultset omzetten in een string:
                String wachtwoord = null;
                while (true) {
                    try {
                        assert rs != null;
                        if (!rs.next()) break;
                    } catch (SQLException ex) {ex.printStackTrace();}

                    try {
                        wachtwoord = rs.getString("password");
                    } catch (SQLException ex) {ex.printStackTrace();}

                    System.out.println("password retrieval succesfull");
                }

                // nu je het wachtwoord in een string heb staan, kan je de connectie sluiten
                try {
                    st.close();
                    rs.close();
                    conn.close();
                } catch (SQLException ex) {ex.printStackTrace();}

                /* nu gaan we kijken of het wachtwoord klopt */
                if (textField_password.getText().equals(wachtwoord)) {
                    /* als de wachtwoord en gebruikersnaam combinatie klopt, stuurt deze code je door naar het volgende scherm: */

                    JFrame optie = new JFrame("optie scherm");                       // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                    try {
                        optie.setContentPane(new optionScreen(optie).panel_optieScherm);      // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                    } catch (SQLException ex) {ex.printStackTrace();}

                    optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                 // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                    optie.pack();                                                         // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                    optie.setVisible(true);                                               // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                    panel_Login.setVisible(false);
                    login.dispose();                                                      // hier word het scherm weggehaald zodra het goede wachtwoord ingevoerd word
                } else {
                    /* als het niet klopt, vraagt dit je om het opnieuw te proberen */

                    JOptionPane.showMessageDialog(null, "Wrong combination, please try again!");
                }

            }

        });
    }
}
