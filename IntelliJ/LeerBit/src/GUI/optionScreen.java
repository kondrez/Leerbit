package GUI;

import javax.swing.*;
import java.awt.*;
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

    public optionScreen(JFrame optie) {
        /* dit is een methode met daarin alle code voor de knoppen. */

        button_persoonsGegevens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "button_persoonsGegevens" gedrukt word, hij zal de pagina "Peronal Data" openen */

                JFrame PData = new JFrame("PersoonsDataScherm");
                PData.setContentPane(new personalDataScreen(PData).panel_PData);
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

                writeSD();
            }
        });

        button_import.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            /* deze methode importeert de .txt bestanden op de sd kaart en schrijft het naar de database */

            readTXTFile();
            updateDB();
            }
        });
    }

    private static void writeSD() {
        /* deze methode leest een database uit, en zet dit daarna in een .txt bestand */

        java.util.List<String> data_leerling = new ArrayList<String>();
        java.util.List<String> data_vragen = new ArrayList<String>();
        java.util.List<String> data_score = new ArrayList<String>();
        java.util.List<String> data_vak = new ArrayList<String>();

        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "root";
        String password = "3Janine5!";
        Connection conn = null;

        try {conn = DriverManager.getConnection(url, username, password);}
        catch (SQLException ex) {ex.printStackTrace();}

        if (conn != null) {
            System.out.println("connection succesfull!");
        }

        /* De tabel leerling uitlezen, en dan opzetten in het bestandje leerling.txt */

        try {
            assert conn != null;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select voor_naam from leerling");

            while (rs.next()) {
                String voor_naam = rs.getString("voor_naam");

                data_leerling.add(voor_naam);
            }

            assert false;
            writeToFile(listToArray(data_leerling), "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\leerling.txt");
            rs.close();
            st.close();

        }
        catch (Exception e) {System.out.println(e);}

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

                data_vragen.add(id + "," + vak_naam + "," + opdracht + "," + antwoord1 + "," + antwoord2 + "," + antwoord3 + "," + antwoord4 + "," + juisteAntwoord);
            }

            assert false;
            writeToFile(listToArray(data_vragen), "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vragen.txt");
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

                data_score.add(id + "," + vak_naam + "," + aantal_goed);
            }

            assert false;
            writeToFile(listToArray(data_score), "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\score.txt");
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

                data_vak.add(vak_naam + "," + aantal_vragen);
            }

            assert false;
            writeToFile(listToArray(data_vak), "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vak.txt");
            st.close();
            rs.close();

        } catch (Exception e) {System.out.println(e);}
    }

    private static void writeToFile(String[] array, String path) throws IOException {
        File file = new File(path);
        BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
        System.out.println(array[1]);

        for (Object s : array) {
            out.write((String) s);
            out.newLine();
        }
        out.close();
    }

    private static String[] listToArray(java.util.List<String> list) {
        String[] strData_Vak= new String[list.size()];
        strData_Vak = list.toArray(strData_Vak);

        return strData_Vak;
    }

    private static void readTXTFile() {
        /* this method reads a .txt file */

        // The name of the file to open.
        String fileName = "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vak.txt";

        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '"  + fileName + "'");
        }
    }

    private static void updateDB() {
        /* making a connection to the database, url is het adres van de database, met daarachter wat opties die nodig waren */
        String url = "jdbc:mysql://localhost:3306/leerbit?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&&serverTimezone=UTC";
        String username = "root";
        String password = "3Janine5!";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        String values = "'7','bart','de kale','5'";
        String table = "leerling";
        String query = "insert into" + table + "values (" + values + ");";

        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        ResultSet rs = null;
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
