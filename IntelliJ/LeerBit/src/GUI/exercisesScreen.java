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
    private JButton button_home;
    private JTextField textField_courseName;
    private JButton button_addCourse;

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

        button_addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String vak_naam = textField_courseName.getText();
                int vak_nummer = 0;
                int hoeveelheid_vragen = 15;
                // get the maximum vak_nummer, than add 1 to get the new;
                ResultSet rs = null;
                try {
                    rs = dataBase.executeQuery("select max(vak_nummer) from vak;");
                    rs.next();
                    vak_nummer = (rs.getInt(1)) + 1;
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                String query = "insert into vak values ('" +
                        vak_nummer + "','" + vak_naam + "','" + hoeveelheid_vragen + "');";
                try {
                    dataBase.executeUpdate(query);
                } catch (SQLException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println("course add sucessfull");

                JOptionPane.showMessageDialog(panel_opdrachten, "course added sucessfully");
            }
        });
    }
}
