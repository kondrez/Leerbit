package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class optionScreen {
    private JButton button_persoonsGegevens;
    private JButton button_opdrachten;
    private JButton button_scores;
    public JPanel panel_optieScherm;
    private JLabel label_message;

    public optionScreen(JFrame optie) {         // dit is een methode met daarin alle code voor de knoppen. met de JFrame optie als parameter zodat deze gebruikt kan worden door de methode
        button_persoonsGegevens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deze code word aangeroepen als er op de knop "button_persoonsGegevens" gedrukt word, hij zal de pagina "Peronal Data" openen

                JFrame PData = new JFrame("PersoonsDataScherm");          // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                PData.setContentPane(new personalDataScreen(PData).panel_PData);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                PData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                PData.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                PData.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt

                optie.dispose();                                                // hier word het scherm wat je verlaat afgesloten
            }
        });
        button_opdrachten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deze code word aangeroepen als er op de knop "opdrachten" gedrukt word

                JFrame exercises = new JFrame("opdrachten scerm");          // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                exercises.setContentPane(new exercisesScreen(exercises).panel_opdrachten);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                exercises.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                exercises.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                exercises.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                optie.dispose();                                                   // hier word het scherm wat je verlaat afgesloten
            }
        });
        button_scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deze code word aangeroepen als er op de knop "button_scores" gedrukt word
                JFrame scores = new JFrame("scores scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                scores.setContentPane(new scoresScreen(scores).panel_scoresScreen);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                scores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                scores.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                scores.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                optie.dispose();                                                // hier word het scherm wat je verlaat afgesloten
            }
        });

    }

    public static void writeFile() {

        List data_leerling = new List();
        List data_vragen = new List();
        List data_score = new List();
        List data_vak = new List();

        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "root";
        String password = "";
        Connection conn = null;

        try {
            //Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        /* De tabel leerling uitlezen, en dan opzetten in het bestandje leerling.txt */

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select voor_naam from leerling");

            while (rs.next()) {
                //String id = rs.getString("leerling_nummer");
                String voor_naam = rs.getString("voor_naam");
                //String achter_naam = rs.getString("achter_naam");
                //String leeftijd = rs.getString("leeftijd");
                System.out.println(voor_naam);
                data_leerling.add(voor_naam);

            }
            writeToFile((java.util.List) data_leerling, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\leerling.txt");

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        /* De tabel vragen uitlezen, en dan omzetten in het .txt bestand vragen.txt */

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from vragen");

            while (rs.next()) {
                String id = rs.getString("vraag_nummer");
                String vak_naam = rs.getString("vak_naam");
                String opdracht = rs.getString("opdracht");
                String antwoord1 = rs.getString("antwoord1");
                String antwoord2 = rs.getString("antwoord2");
                String antwoord3 = rs.getString("antwoord3");
                String antwoord4 = rs.getString("antwoord4");
                String juisteAntwoord = rs.getString("juiste_antwoord");
                System.out.println(id + vak_naam + opdracht + antwoord1 + antwoord2 + antwoord3 + antwoord4 + juisteAntwoord);
                data_vragen.add(id + "," + vak_naam + "," + opdracht + "," + antwoord1 + "," + antwoord2 + "," + antwoord3 + "," + antwoord4 + "," + juisteAntwoord);

            }
            writeToFile((java.util.List) data_vragen, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vragen.txt");

            rs.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from score");

            while (rs.next()) {
                String id = rs.getString("leerling_nummer");
                String vak_naam = rs.getString("vak_naam");
                String aantal_goed = rs.getString("aantal_goed");
                System.out.println(id + vak_naam + aantal_goed);
                data_score.add(id + "," + vak_naam + "," + aantal_goed);
            }
            writeToFile((java.util.List) data_score, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\score.txt");

            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }


        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select * from vak");

            while (rs.next()) {
                String vak_naam = rs.getString("vak_naam");
                String aantal_vragen = rs.getString("hoeveelheid_vragen");
                System.out.println(vak_naam + aantal_vragen);
                data_vak.add(vak_naam + "," + aantal_vragen);
            }
            writeToFile((java.util.List) data_vak, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vak.txt");

            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    private static void writeToFile(java.util.List list, String path) {
        BufferedWriter out = null;
        try {
            File leerbit = new File(path);
            out = new BufferedWriter(new FileWriter(leerbit, true));
            for (Object s : list) {
                out.write((String) s);
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
        }
    }
    public static void enter(String bestand) {
        List enter = new List();
        enter.add("\n");
        String url = "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\" + bestand + ".txt";
        writeToFile((java.util.List) enter, url);
    }

}
