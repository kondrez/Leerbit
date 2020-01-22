package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class assignmentScreen {
    private JButton button_home;
    private JTextField textField_assignmentScreen;
    private JButton button_ok;
    public JPanel Jpanel_vragen;
    private JLabel JLabel_course;
    public int currentVakNr = 0;

    public assignmentScreen(JFrame assignment){
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
                assignment.dispose();
            }
        });


                button_ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String vak_naam = textField_assignmentScreen.getText();
                        // get the maximum vak_nummer, than add 1 to get the new;
                        ResultSet rs = null;
                        try {
                            rs = dataBase.executeQuery("select vak_nummer from vak where vak_naam = \"" + vak_naam + "\";");
                            rs.next();
                            currentVakNr = (rs.getInt(1));
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        JFrame questions = new JFrame("vragen scherm");
                        questions.setContentPane(new questionsScreen(questions, currentVakNr).JPanel_VragenToevoegen);
                        questions.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        questions.pack();
                        questions.setVisible(true);
                        assignment.dispose();


                    }
                });
    }
}
