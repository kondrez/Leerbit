package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Arrays;

public class loginScreen<Private> {
    /* hier worden de gui onderdelen gedeclareerd */
    private JPanel panel_Login;
    private JTextField textField_username;
    private JPasswordField textField_password;
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
                /* de code hierin is wat er gebeurt als de knop "button_submit" word ingedrukt
                 er word gekeken of het wachtwoord klopt. zo ja, doorsturen naar volgende scherm. zo nee message laten zien met "Try again!' */

                String ingevuldeUsername = textField_username.getText();
                int wachtwoordHash = String.valueOf(textField_password.getPassword()).hashCode();
                String query = "SELECT password FROM user WHERE username = '" + ingevuldeUsername + "';";
                //int wachtwoordHash = ingevuldPassword.hashCode();;
                int opgeslagenHash = 0;
                ResultSet rs;

                // de query uitvoeren
                try {
                    rs = dataBase.executeQuery(query);

                    // het rs omzetten in een String
                    assert rs != null;
                    rs.next();
                    opgeslagenHash = rs.getInt("password");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // checken of het wachwoord klopt
                checkPassword(opgeslagenHash, wachtwoordHash, login);
            }
        });
    }

    private void checkPassword(int wachtwoord, int ingevuldWachtwoord, JFrame login) {
        /* als de wachtwoord en gebruikersnaam combinatie klopt, stuurt deze code je door naar het volgende scherm: */

        if (ingevuldWachtwoord == wachtwoord) {
            JFrame optie = new JFrame("optie scherm");                       // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
            try {
                optie.setContentPane(new optionScreen(optie).panel_optieScherm);      // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                 // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
            optie.pack();                                                         // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
            optie.setVisible(true);                                               // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
            panel_Login.setVisible(false);
            login.dispose();                                                      // hier word het scherm weggehaald zodra het goede wachtwoord ingevoerd word
        } else {
            /* als het niet klopt, vraagt dit je om het opnieuw te proberen */

            JOptionPane.showMessageDialog(panel_Login, "Wrong combination, please try again!");
        }
    }
}
