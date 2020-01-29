package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class questionsScreen {
    public JPanel JPanel_VragenToevoegen;
    private JTextField textField_c;
    private JTextField textField_vraag;
    private JTextField textField_b;
    private JTextField textField_a;
    private JTextField textField_d;
    private JRadioButton radioButton_a;
    private JRadioButton radioButton_b;
    private JRadioButton radioButton_c;
    private JRadioButton radioButton_d;
    private JButton maakOpdrachtButton;
    private JButton home_button;
    int vraagNummer = 1;


    public questionsScreen(JFrame questions, int currentVakNr) throws SQLException{
        maakOpdrachtButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vraag = textField_vraag.getText();
                String antwoordA = textField_a.getText();
                String antwoordB = textField_b.getText();
                String antwoordC = textField_c.getText();
                String antwoordD = textField_d.getText();
                String goedeAntwoord = "a";
                if (radioButton_a.isSelected()){
                    goedeAntwoord = "a";
                }
                else if (radioButton_b.isSelected()){
                    goedeAntwoord = "b";
                }
                else if (radioButton_c.isSelected()){
                    goedeAntwoord = "c";
                }
                else if (radioButton_d.isSelected()){
                    goedeAntwoord = "d";
                }


                String query = "insert into vraag values ('" + vraagNummer + "','" +
                        currentVakNr + "','" + vraag + "','" + antwoordA + "','" + antwoordB + "','" + antwoordC + "','" + antwoordD + "','" + goedeAntwoord + "');";
                try {
                    dataBase.executeUpdate(query);
                } catch (SQLException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }

                vraagNummer++;

                textField_vraag.setText("");
                textField_a.setText("");
                textField_b.setText("");
                textField_c.setText("");
                textField_d.setText("");
            }
        });

        home_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame assignemnt = new JFrame("assignemnt scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                assignemnt.setContentPane(new assignmentScreen(assignemnt).Jpanel_vragen);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                assignemnt.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                assignemnt.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                assignemnt.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                questions.dispose();
            }
        });

    }
}
