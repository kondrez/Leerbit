/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mjnde
 */
    
package javaapplication5;
import java.io.*;
import java.sql.*;
import java.util.*;


    
public class WriteIntoFileFromDatabase {
    public static void main(String[] args) {
          
        List data_leerling = new ArrayList();
        List data_vragen = new ArrayList();
        List data_score = new ArrayList();
        List data_vak = new ArrayList();
        
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
            writeToFile(data_leerling, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\leerling.txt");
            
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
            writeToFile(data_vragen, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vragen.txt");
            
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
            writeToFile(data_score, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\score.txt");
            
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
            writeToFile(data_vak, "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\vak.txt");
            
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
        List enter = new ArrayList();
        enter.add("\n");
        String url = "C:\\Users\\mjnde\\OneDrive\\Documenten\\leerbit\\" + bestand + ".txt";
        writeToFile(enter, url);
    }
}

