package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class loginScreen {
    /* hier worden de gui onderdelen gedeclareerd */
    private JPanel panel_Login;                                             // Dit is de panel, het "scherm" of de achtergrond
    private JTextField textField_username;                                  // Dit is het textfield waar de gebruikersnaam ingevuld word
    private JTextField textField_password;                                  // het password field waar het wachtwoord ingevuld word
    private JLabel label_Message;                                           // Een label als onaanpasbaar tekstblokje om de gebruiker instrcutie te geven
    private JLabel label_username;                                          // Een label als onaanpasbaar tekstblokje om de gebruiker instrcutie te geven
    private JLabel label_password;                                          // Een label als onaanpasbaar tekstblokje om de gebruiker instrcutie te geven
    private JButton button_submit;                                          // een knop, die, wanneer ingedrukt, controleert of het wachtwoord klopt en je doorstuurt naar de volgende pagina

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        JFrame login = new JFrame("loginscherm");               // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
        login.setContentPane(new loginScreen(login).panel_Login);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
        login.pack();                                                // hier worden alle gui elementen op dezelfde JFrame gezet
        login.setVisible(true);                                      // spreekt wel voor zich, hier word de JFrame "login" zichtbaar gemaakt


    }

    private loginScreen(JFrame login) {
        button_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /*
                 de code hierin is wat er gebeurt als de knop "button_submit" word ingedrukt
                 er word gekeken of het wachtwoord klopt. zo ja, doorsturen naar volgende scherm. zo nee message laten zien met "Try again!'
                */

                /* making a connection to the database, url is het adres van de database, met daarachter wat opties die nodig waren */
                String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
                String username = "root";                   // gebruikersnaam van je mySQL server
                String password = "3Janine5!";                       // wachtwoord van je mySQL server

                Connection conn = null;                     // de variabel conn aanmaken, van het type connectie
                try {
                    conn = DriverManager.getConnection(url, username, password);        // hier word daarwerkelijk de connectie gemaakt
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                /* als het goed is is er nu een connectie met de databse, nu moeten we het wachtwoord uitlezen */
                String ingevuldeUsername = textField_username.getText();
                String query = "SELECT password FROM user WHERE username = '" + ingevuldeUsername + "';";

                Statement st = null;
                try {
                    st = conn.createStatement();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                ResultSet rs = null;
                try {
                    rs = st.executeQuery(query);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                String wachtwoord = null;
                while (true) {
                    try {
                        if (!rs.next()) break;
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    try {
                        wachtwoord = rs.getString("password");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    // print the results
                    System.out.format("%s\n", wachtwoord);
                }
                try {
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                boolean ingelogd = false;
                if (textField_password.getText().equals(wachtwoord)) {
                    ingelogd = true;
                }

                if (ingelogd) {
                    /* als de wachtwoord en gebruikersnaam combinatie klopt, stuurt deze code je door naar het volgende scherm: */

                    JFrame optie = new JFrame("optie scherm");                       // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                    optie.setContentPane(new optionScreen(optie).panel_optieScherm);      // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                    optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                 // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                    optie.pack();                                                         // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                    optie.setVisible(true);                                               // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                    panel_Login.setVisible(false);
                    login.dispose();                                                      // hier word het scherm weggehaald zodra het goede wachtwoord ingevoerd word
                } else {
                    /* als het niet klopt, geeft deze code een foutmelding: */
                    JOptionPane.showMessageDialog(null, "Wrong combination, please try again!");
                }

            }
        });
    }
}
