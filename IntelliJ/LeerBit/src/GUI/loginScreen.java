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
                /* kijken of het wachtwoord klopt. zo ja, doorsturen naar volgende scherm.
                zo nee message laten zien met "Try again!' */

                String ingevuldeUsername = textField_username.getText();
                String ingevuldPassword = String.valueOf(textField_password.getPassword());
                String query = "SELECT password FROM user WHERE username = '" + ingevuldeUsername + "';";
                int wachtwoordHash;
                int opgeslagenHash = 0;
                ResultSet rs;

                try {
                    // de query uitvoeren
                    rs = dataBase.executeQuery(query);

                    // het rs uitlezen en dan hashen
                    assert rs != null;
                    rs.next();
                    opgeslagenHash = rs.getString("password").hashCode();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                // het ingevulde wachtwoord hashen
                wachtwoordHash = ingevuldPassword.hashCode();

                // checken of het wachwoord klopt
                checkPassword(opgeslagenHash, wachtwoordHash, login);
            }
        });
    }

    private void checkPassword(int opgeslagenHash, int wachwoordHash, JFrame login) {
        /* als de wachtwoord en gebruikersnaam combinatie klopt, stuurt deze code je door naar het volgende scherm: */

        // de hash uit de database en de ingevuklde hash vergelijken
        if (opgeslagenHash == wachwoordHash) {
            // als het ww klopt, doorzenden naar volgende scherm
            JFrame optie = new JFrame("optie scherm");
            try {
                optie.setContentPane(new optionScreen(optie).panel_optieScherm);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            optie.pack();
            optie.setVisible(true);
            panel_Login.setVisible(false);
            login.dispose();
        } else {
            /* als het niet klopt, vraagt dit je om het opnieuw te proberen */
            JOptionPane.showMessageDialog(panel_Login, "Wrong combination, please try again!");
        }
    }
}
