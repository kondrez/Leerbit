package asdfkjo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class optieScherm {
    //Hier worden alle onderdelen van de GUI naam gegeven
    private JButton button_PGegevens;
    private JLabel whatDoYouWantLabel;
    private JLabel PGegevens;
    private JLabel scores;
    private JButton button_Scores;
    private JButton button_Beoordeling;
    private JLabel beoordeling;
    private JLabel opdrachten;
    private JButton botton_Opdrachten;
    public JPanel panel_sScherm;

    public optieScherm() {
        button_PGegevens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hier word het scherm "persoonsgegevens" geopend

                JFrame PGegeven = new JFrame();                             // hier word de frame "PGegeven" gedefinieerd dit staat
                PGegeven.setContentPane(new PGegevens().panel_PGegeven);
                PGegeven.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                PGegeven.pack();
                PGegeven.setVisible(true);
                // het oude scherm "sScherm" moet nog afgesloten worden, maar dit lukt nog niet helemaal
                //sScherm.dispose();
            }
        });
        button_Scores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hier word het scherm "Scores" geopend
            }
        });
        button_Beoordeling.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hier word het scherm "Beoordeling" geopend
            }
        });

        botton_Opdrachten.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // hier word het scherm "Opdrachten bewerken en bekijken" geopend


            }
        });
    }

    public static void main(String[] args) {
        JFrame sScherm = new JFrame( "startscherm");        //hier word het frame "sSherm" gedefinieerd
        sScherm.setContentPane(new optieScherm().panel_sScherm); //hier stel je in dat het scherm gaat bestaan uit het de panel "panel_sScherm"
        sScherm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //hier word ingesteld wat er gebeurt als je de pagina sluit, in dit geval word hij gewoon gesloten
        sScherm.pack();                                          //dit zegt dat alle gui elementen samen op een schermpje moeten komen te staan
        sScherm.setVisible(true);                                //hier word het scherm "sScherm" als zichtbaar gezet

    }
}

