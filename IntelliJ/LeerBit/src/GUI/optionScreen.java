package GUI;

import com.mysql.cj.protocol.Resultset;
import com.sun.java.swing.plaf.motif.MotifDesktopIconUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class optionScreen {
    public static String bestandLocatie = "C:\\test\\";
    private static java.util.ArrayList list;
    private JButton button_persoonsGegevens;
    private JButton button_opdrachten;
    private JButton button_scores;
    public JPanel panel_optieScherm;
    private JButton button_export;
    private JButton button_import;
    private JButton button_addStudent;
    private JButton button_addCourse;

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
                String query = "Select * from vraag";
                String message = "Your subjects: ";
                displayTable(query, message);

            }
        });

        button_scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /* deze code word aangeroepen als er op de knop "button_scores" gedrukt word */
                String query = "Select l.leerling_nummer, l.voor_naam, l.achter_naam, v.vak_naam, s.aantal_goed, v.hoeveelheid_vragen as max" +
                        " from score s join leerling l on s.leerling_nummer = l.leerling_nummer" +
                        " join vak v on s.vak_nummer = v.vak_nummer" +
                        " order by l.leerling_nummer;";
                String message = "Your Students:";

                System.out.println(query);

                displayTable(query, message);
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
            JTable table = new JTable(buildTableModel(rs));

            // display the table in a message dialog
            JOptionPane.showMessageDialog(panel_optieScherm, new JScrollPane(table), message,
                    JOptionPane.INFORMATION_MESSAGE, new ImageIcon(bestandLocatie + "nothing.png"));

            // closing the resultset
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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

        // defineren van de variabelen
        java.util.List<String> data_leerling = new ArrayList<String>();
        java.util.List<String> data_vragen = new ArrayList<String>();
        java.util.List<String> data_score = new ArrayList<String>();
        ResultSet rs;

        /* De tabel leerling uitlezen, en dan opzetten in het bestandje leerling.csv */

        rs = dataBase.executeQuery("Select voor_naam from leerling");

        while (rs.next()) {
            String voor_naam = rs.getString("voor_naam");

            data_leerling.add(voor_naam);
        }

        deleteFile(bestandLocatie + "leerling.csv");

        assert false;
        writeToFile(listToArray(data_leerling), bestandLocatie + "leerling.csv");

        // De tabel score uitlezen
        rs = dataBase.executeQuery("Select * from score");

        while (rs.next()) {
            String id = rs.getString("leerling_nummer");
            String vak_nummer = rs.getString("vak_nummer");
            String aantal_goed = rs.getString("aantal_goed");

            data_score.add(id + "," + vak_nummer + "," + aantal_goed);
        }

        deleteFile(bestandLocatie + "score.csv");

        assert false;
        writeToFile(listToArray(data_score), bestandLocatie + "score.csv");

        //vragen toevoegen
        rs = dataBase.executeQuery("Select opdracht, antwoord1, antwoord2, antwoord3," +
                " antwoord4, juiste_antwoord from vraag" +
                " order by vak_nummer ASC, vraag_nummer ASC;");
        int teller = 0;
        int vakTeller = 1;

        // groote van de resultset vinden
        int size =0;
        if (rs != null)
        {
            rs.last();    // moves cursor to the last row
            size = rs.getRow(); // get row id
        }
        rs.first();

        while (teller <= size + 1) {
            if (teller % 16 == 0) {
                // voor 15 vragen de naam van het vak afdrukken
                ResultSet rsVak = dataBase.executeQuery("Select vak_naam from vak where vak_nummer = " + vakTeller);
                rsVak.next();
                data_vragen.add(rsVak.getString(1));
                rsVak.close();
                vakTeller++;
            }
            else {
                // als het geen vaknaam regel is, data uitlezen en toevoegen aan de lijst
                String opdracht = rs.getString("opdracht");
                String antwoord1 = rs.getString("antwoord1");
                String antwoord2 = rs.getString("antwoord2");
                String antwoord3 = rs.getString("antwoord3");
                String antwoord4 = rs.getString("antwoord4");
                String juiste_antwoord = rs.getString("juiste_antwoord");


                data_vragen.add(opdracht + "," + antwoord1 + "," + antwoord2 + "," + antwoord3
                        + "," + antwoord4 + "," + juiste_antwoord);

                // naar de volgende regel van rs
                rs.next();
            }

            teller++;
        }

        deleteFile(bestandLocatie + "vraag.csv");

        assert false;
        writeToFile(listToArray(data_vragen), bestandLocatie + "vraag.csv");


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