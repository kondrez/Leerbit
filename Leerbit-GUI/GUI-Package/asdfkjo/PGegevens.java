package asdfkjo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PGegevens {
    public JPanel panel_PGegeven;
    private JButton button_TerugKeren;
    private JTable table_persoonsGegevens;


    public PGegevens() {
        button_TerugKeren.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame sScherm = new JFrame();
                sScherm.setContentPane(new optieScherm().panel_sScherm);
                sScherm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                sScherm.pack();
                sScherm.setVisible(true);
                panel_PGegeven.setVisible(false);
            }
        });
    }

    public static void main(String[] args) {
        /*JFrame PGegevens = new JFrame( "persoonsGegevens");
        PGegevens.setContentPane(new PGegevens().PGegeven);
        PGegevens.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        PGegevens.pack();
        PGegevens.setVisible(true);*/
    }

    private void createUIComponents() {

    }
}
