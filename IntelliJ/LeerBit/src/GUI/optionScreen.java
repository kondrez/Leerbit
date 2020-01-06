package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class optionScreen {
    private static java.util.ArrayList list;
    private JButton button_persoonsGegevens;
    private JButton button_opdrachten;
    private JButton button_scores;
    public JPanel panel_optieScherm;
    private JLabel label_message;
    private JButton button_export;
    private JButton button_import;

    optionScreen(JFrame optie) {
        /* dit is een methode met daarin alle code voor de knoppen. */

        button_persoonsGegevens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "button_persoonsGegevens" gedrukt word, hij zal de pagina "Peronal Data" openen */

                JFrame PData = new JFrame("PersoonsDataScherm");
                try {
                    PData.setContentPane(new personalDataScreen(PData).panel_PData);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                PData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                PData.pack();
                PData.setVisible(true);

                optie.dispose();
            }
        });

        button_opdrachten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "opdrachten" gedrukt word */

                JFrame exercises = new JFrame("opdrachten scerm");
                exercises.setContentPane(new exercisesScreen(exercises).panel_opdrachten);
                exercises.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                exercises.pack();
                exercises.setVisible(true);
                optie.dispose();
            }
        });

        button_scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "button_scores" gedrukt word */

                JFrame scores = new JFrame("scores scherm");
                scores.setContentPane(new scoresScreen(scores).panel_scoresScreen);
                scores.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                scores.pack();
                scores.setVisible(true);
                optie.dispose();
            }
        });

        button_export.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* Deze methode exporteert de database naar .txt op een microSD, dmv de writeSD methode */

                try {
                    writeSD();
                } catch (SQLException | IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        button_import.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze methode importeert de .txt bestanden op de sd kaart en schrijft het naar de database */

                try {
                    readCSVFile();
                } catch (IOException | SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private static void writeSD() throws SQLException, IOException {
        /* deze methode leest een database uit, en zet dit daarna in een .txt bestand */

        String path = "C:\\test\\";

        // defineren van de list variabelen
        java.util.List<String> data_leerling = new ArrayList<String>();
        java.util.List<String> data_vragen = new ArrayList<String>();
        java.util.List<String> data_score = new ArrayList<String>();
        java.util.List<String> data_vak = new ArrayList<String>();

        // connectie maken met de database
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "admin";
        String password = "admin";
        Connection conn;

        conn = DriverManager.getConnection(url, username, password);

        /* De tabel leerling uitlezen, en dan opzetten in het bestandje leerling.csv */


        assert conn != null;
        Statement st = conn.createStatement();
        ResultSet rs;
        rs = st.executeQuery("Select voor_naam from leerling");

        while (rs.next()) {
            String voor_naam = rs.getString("voor_naam");

            data_leerling.add(voor_naam);
        }

        deleteFile(path + "leerling.csv");

        assert false;
        writeToFile(listToArray(data_leerling), path + "leerling.csv");


        /* De tabel vragen uitlezen, en dan omzetten in het .txt bestand vragen.csv */
        while (rs.next()) {
            // eerst moet je de lijn van het resultset opdelen in strings, per kolom
            String id = rs.getString("vraag_nummer");
            String vak_naam = rs.getString("vak_naam");
            String opdracht = rs.getString("opdracht");
            String antwoord1 = rs.getString("antwoord1");
            String antwoord2 = rs.getString("antwoord2");
            String antwoord3 = rs.getString("antwoord3");
            String antwoord4 = rs.getString("antwoord4");
            String juisteAntwoord = rs.getString("juiste_antwoord");

            //daarna moet je het toevoegen aan de list data_vragen
            data_vragen.add(id + "," + vak_naam + "," + opdracht + "," + antwoord1 + "," + antwoord2 + "," + antwoord3 + "," + antwoord4 + "," + juisteAntwoord);
        }
        // eerst het oude bestand verweideren
        deleteFile(path + "vragen.csv");

        // en dan schijven naar het bestand
        assert false;
        writeToFile(listToArray(data_vragen), path + "vragen.csv");

        // De tabel score uitlezen
        rs = st.executeQuery("Select * from score");

        while (rs.next()) {
            String id = rs.getString("leerling_nummer");
            String vak_naam = rs.getString("vak_naam");
            String aantal_goed = rs.getString("aantal_goed");

            data_score.add(id + "," + vak_naam + "," + aantal_goed);
        }

        deleteFile(path + "score.csv");

        assert false;
        writeToFile(listToArray(data_score), path + "score.csv");

        // De tabel vak uitlezen
        rs = st.executeQuery("Select * from vak");

        while (rs.next()) {
            String vak_naam = rs.getString("vak_naam");
            String aantal_vragen = rs.getString("hoeveelheid_vragen");

            data_vak.add(vak_naam + "," + aantal_vragen);
        }

        deleteFile(path + "vak.csv");

        assert false;
        writeToFile(listToArray(data_vak), path + "vak.csv");

        // netjes alles sluiten
        st.close();
        rs.close();
        conn.close();
        System.out.println("export successful");
    }

    private static void deleteFile(String path) {
        // deletes a file with an absolute patch

        System.out.println(path);

        File file = new File(path);

        if (file.delete()) {
            System.out.println("Delete Successful");
        }
        else {
            System.out.println("Delete failed");
        }


    }

    private static void writeToFile(String[] array, String path) throws IOException {

        // creating the file
        File file = new File(path);

        BufferedWriter out = new BufferedWriter(new FileWriter(file, true));

        for (Object s : array) {
            out.write((String) s);
            out.newLine();
        }
        out.close();
    }

    private static String[] listToArray(java.util.List<String> list) {
        String[] strData_Vak = new String[list.size()];
        strData_Vak = list.toArray(strData_Vak);

        return strData_Vak;
    }

    private static void readCSVFile() throws IOException, SQLException {
        /* this method reads a .csv file */
        String query;

        // connectie maken met de database
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "admin";
        String password = "admin";

        Connection conn = DriverManager.getConnection(url, username, password);

        Statement st = conn.createStatement();

        // een buffer reader aanmaken, die altijd genest moeten worden met een file reader, die een bestand pad nodig heeft
        BufferedReader input = null;
        input = new BufferedReader(new FileReader(new File("C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\score.csv")));

        // de oude data uit te database verweideren
        String deleteQuery = "DELETE FROM score WHERE leerling_nummer <= 1000000;";
        st.executeUpdate(deleteQuery);

        // nieuwe data in de database zetten
        try {
            String line = null;
            String[] velden = null;

            // regel voor regel het bestand afgaan, en elke regel in een query omzetten en uitvoeren
            while ((line = input.readLine()) != null) {

                // velden is een Array, en met line.replace vul je deze met de waardes, gesplit bij de ","
                velden = line.split(",");

                query = "INSERT INTO score VALUES\n"
                        + "('" + velden[0] + "','" + velden[1] + "',' " + velden[2] + "');\n";
                st.executeUpdate(query);
            }
        } finally {
            input.close();
            st.close();
            conn.close();
            System.out.println("file import successfull");
        }
    }
}
