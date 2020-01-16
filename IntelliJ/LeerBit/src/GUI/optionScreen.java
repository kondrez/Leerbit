package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class optionScreen {
    private static java.util.ArrayList list;
    private JButton button_persoonsGegevens;
    private JButton button_opdrachten;
    private JButton button_scores;
    public JPanel panel_optieScherm;
    private JButton button_export;
    private JButton button_import;
    private JButton button_addStudent;
    private JButton button_addCourse;
    private JButton button3;
    private JTable table_Leerlingen = null;
    private JTable table_opdrachten = null;
    public static String bestandLocatie = "C:\\test\\";

    optionScreen(JFrame optie) throws SQLException {
        /* dit is een methode met daarin alle code voor de knoppen. */

        button_persoonsGegevens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "button_persoonsGegevens" gedrukt word, hij zal de pagina "Peronal Data" openen */
                String query = "Select * from leerling";
                String message = "Your Students:";

                displayTable(query, message);
            }
        });

        button_opdrachten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "opdrachten" gedrukt word */
                String query = "Select * from vragen";
                String message = "Your subjects: ";
                displayTable(query, message);

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

        button_addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame PData = new JFrame("PersoonsDataScherm");
                PData.setContentPane(new personalDataScreen(PData).panel_PData);
                PData.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                PData.pack();
                PData.setVisible(true);

                optie.dispose();
            }
        });

        button_addCourse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame exercises = new JFrame("opdrachten scerm");
                exercises.setContentPane(new exercisesScreen(exercises).panel_opdrachten);
                exercises.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                exercises.pack();
                exercises.setVisible(true);
                optie.dispose();
            }
        });
    }

    private void displayTable(String query, String message) {
        ResultSet rs = null;

        // execute the query
        try {
            rs = dataBase.executeQuery(query);

            // construct a table model
            assert rs != null;
            table_opdrachten = new JTable(buildTableModel(rs));

            // closing the resultset
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // show the table in a message dialog
        JOptionPane.showMessageDialog(panel_optieScherm, table_opdrachten, message,
                JOptionPane.INFORMATION_MESSAGE, new ImageIcon(bestandLocatie + "leerbit2.png"));

    }

    private static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<>();

        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    private static void writeSD() throws SQLException, IOException {
        /* deze methode leest een database uit, en zet dit daarna in een .txt bestand */

        // defineren van de list variabelen
        java.util.List<String> data_leerling = new ArrayList<String>();
        java.util.List<String> data_vragen = new ArrayList<String>();
        java.util.List<String> data_score = new ArrayList<String>();
        java.util.List<String> data_vak = new ArrayList<String>();

        /* De tabel leerling uitlezen, en dan opzetten in het bestandje leerling.csv */


        ResultSet rs = dataBase.executeQuery("Select voor_naam from leerling");

        while (rs.next()) {
            String voor_naam = rs.getString("voor_naam");

            data_leerling.add(voor_naam);
        }

        deleteFile(bestandLocatie + "leerling.csv");

        assert false;
        writeToFile(listToArray(data_leerling), bestandLocatie + "leerling.csv");


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
        deleteFile(bestandLocatie + "vragen.csv");

        // en dan schijven naar het bestand
        assert false;
        writeToFile(listToArray(data_vragen), bestandLocatie + "vragen.csv");


        // De tabel score uitlezen
        rs = dataBase.executeQuery("Select * from score");

        while (rs.next()) {
            String id = rs.getString("leerling_nummer");
            String vak_naam = rs.getString("vak_naam");
            String aantal_goed = rs.getString("aantal_goed");

            data_score.add(id + "," + vak_naam + "," + aantal_goed);
        }

        deleteFile(bestandLocatie + "score.csv");

        assert false;
        writeToFile(listToArray(data_score), bestandLocatie + "score.csv");

        // De tabel vak uitlezen
        rs = dataBase.executeQuery("Select * from vak");

        while (rs.next()) {
            String vak_naam = rs.getString("vak_naam");
            String aantal_vragen = rs.getString("hoeveelheid_vragen");

            data_vak.add(vak_naam + "," + aantal_vragen);
        }

        deleteFile(bestandLocatie + "vak.csv");

        assert false;
        writeToFile(listToArray(data_vak), bestandLocatie + "vak.csv");

        //vragen toevoegen
        rs = dataBase.executeQuery("Select * from vragen");

        while (rs.next()) {
            String vraag_nummer = rs.getString("vraag_nummer");
            String vak_naam = rs.getString("vak_naam");
            String opdracht = rs.getString("opdracht");
            String antwoord1= rs.getString("antwoord1");
            String antwoord2= rs.getString("antwoord2");
            String antwoord3= rs.getString("antwoord3");
            String antwoord4= rs.getString("antwoord4");
            String juiste_antwoord= rs.getString("juiste_antwoord");



            data_vragen.add(vraag_nummer + "," + vak_naam +","+opdracht+","+antwoord1+","+ antwoord2+","+antwoord3
                    +","+antwoord4+","+ juiste_antwoord);
        }

        deleteFile(bestandLocatie + "vragen.csv");

        assert false;
        writeToFile(listToArray(data_vragen), bestandLocatie + "vragen.csv");


        System.out.println("export successful");
    }

    private static void deleteFile(String path) {
        // deletes a file with an absolute patch

        File file = new File(path);

        if (file.delete()) {
            System.out.println("Delete Successful");
        } else {
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
        String deleteQuery = "DELETE FROM score WHERE leerling_nummer <= 1000000";
        String updateQuery;

        // de oude tuples verweideren
        dataBase.executeUpdate(deleteQuery);

        // een buffer reader aanmaken, die altijd genest moeten worden met een file reader, die een bestand pad nodig heeft
        BufferedReader input;
        input = new BufferedReader(new FileReader(new File(bestandLocatie + "score.csv")));

        // nieuwe data in de database zetten
        try {
            String regel;
            String[] elementen;

            // regel voor regel het bestand afgaan, en elke regel in een query omzetten en uitvoeren
            while ((regel = input.readLine()) != null) {
                // velden is een Array, en met line.split vul je deze met de waardes, gesplit bij de ","
                elementen = regel.split(",");

                updateQuery = "INSERT INTO score VALUES\n"
                        + "('" + elementen[0] + "','" + elementen[1] + "',' " + elementen[2] + "');\n";

                dataBase.executeUpdate(updateQuery);
            }
        } finally {
            input.close();
            System.out.println("file import successfull");
        }
    }
}