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

    public static void main(String[] args) throws SQLException {
        /* this is where the program starts running */
        /* code hieronder bouwt een scherm op, en laat dit zien */

        JFrame login = new JFrame("loginscherm");
        login.setContentPane(new loginScreen(login).panel_Login);
        login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login.pack();
        login.setVisible(true);
    }

    private loginScreen(JFrame login) {
        button_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* de code hierin is wat er gebeurt als de knop "button_submit" word ingedrukt
                 er word gekeken of het wachtwoord klopt. zo ja, doorsturen naar volgende scherm.
                 zo nee message laten zien met "Try again!' */

                String ingevuldeUsername = textField_username.getText();
                int wachtwoordHash = String.valueOf(textField_password.getPassword()).hashCode();
                String query = "SELECT password FROM user WHERE username = '" + ingevuldeUsername + "';";
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
