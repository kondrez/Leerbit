package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class exercisesScreen {
    public JPanel panel_opdrachten;
    private JLabel label_messages;
    private JButton button_submit;
    private JButton button_home;
    private JTextField textField_courseName;
    private JTextField textField_questionAmount;

    public exercisesScreen(JFrame exercises) {
        button_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame optie = new JFrame("optie scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                try {
                    optie.setContentPane(new optionScreen(optie).panel_optieScherm);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                optie.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                optie.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                exercises.dispose();
            }
        });

        button_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String courseName = textField_courseName.getText();
                String questionAmount = textField_questionAmount.getText();

                // get the maximum leerling_nummer, than add 1 to get the new;
                ResultSet rs = null;
                String query = "insert into vak values " +
                        "('" +
                        courseName + "','" + questionAmount  + "');";
                try {
                    dataBase.executeUpdate(query);
                } catch (SQLException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println("course add sucessfull");
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
