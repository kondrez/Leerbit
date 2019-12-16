package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class optionScreen {
    private JButton button_persoonsGegevens;
    private JButton button_opdrachten;
    private JButton button_scores;
    private JButton button_beoordelingen;
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
        button_beoordelingen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // deze code word aangeroepen als er op de knop "button_beoordelingen" gedrukt word

                JFrame rating = new JFrame("optie scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                rating.setContentPane(new ratingsScreen(rating).panel_ratingScherm);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                rating.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                rating.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                rating.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                optie.dispose();
            }
        });
    }
}
