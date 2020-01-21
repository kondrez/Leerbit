package GUI;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

public class personalDataScreen {
    public JPanel panel_PData;
    private JLabel label_message;
    private JButton button_submit;
    private JButton button_home;
    private JTextField textField_voornaam;
    private JTextField textField_achternaam;
    private JTextField textField_leeftijd;
    private JLabel label_voornaam;
    private JLabel label_achternaam;
    private JLabel label_leeftijd;

    personalDataScreen(JFrame PData) {
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
                PData.dispose();
            }
        });
        button_submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String voornaam = textField_voornaam.getText();
                String achternaam = textField_achternaam.getText();
                String leeftijd = textField_leeftijd.getText();
                int idNummer = 0;

                String query = "insert into leerling values ('" +
                        idNummer + "','" + voornaam + "','" + achternaam + "','" + leeftijd + "');";

                // get the maximum leerling_nummer, than add 1 to get the new;
                ResultSet rs = null;
                try {
                    rs = dataBase.executeQuery("select max(leerling_nummer) from leerling;");
                    rs.next();
                    idNummer = (rs.getInt(1)) + 1;
                    dataBase.executeUpdate(query);
                } catch (SQLException | FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                System.out.println("student add sucessfull");
            }
        });
    }
}