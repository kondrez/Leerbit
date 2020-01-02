package GUI;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Enumeration;
import java.util.Vector;

public class personalDataScreen {
    public JTable table_PData;
    public JPanel panel_PData;
    private JScrollPane schrollPane_omTable;
    private JLabel label_message;
    private JButton button_submit;
    private JButton button_home;

    personalDataScreen(JFrame PData) throws SQLException {
        TableColumn[] test = new TableColumn[3];
        test[0] = new TableColumn(24, 5);
        test[1] = new TableColumn(14, 5);
        test[2] = new TableColumn(16, 5);
        TableColumnModel TModel = new TableColumnModel() {
            @Override
            public void addColumn(TableColumn aColumn) {
                for (TableColumn tableColumn : test) {
                    addColumn(tableColumn);
                }
            }

            @Override
            public void removeColumn(TableColumn column) {

            }

            @Override
            public void moveColumn(int columnIndex, int newIndex) {

            }

            @Override
            public void setColumnMargin(int newMargin) {

            }

            @Override
            public int getColumnCount() {
                return 0;
            }

            @Override
            public Enumeration<TableColumn> getColumns() {
                return null;
            }

            @Override
            public int getColumnIndex(Object columnIdentifier) {
                return 0;
            }

            @Override
            public TableColumn getColumn(int columnIndex) {
                return null;
            }

            @Override
            public int getColumnMargin() {
                return 0;
            }

            @Override
            public int getColumnIndexAtX(int xPosition) {
                return 0;
            }

            @Override
            public int getTotalColumnWidth() {
                return 0;
            }

            @Override
            public void setColumnSelectionAllowed(boolean flag) {

            }

            @Override
            public boolean getColumnSelectionAllowed() {
                return false;
            }

            @Override
            public int[] getSelectedColumns() {
                return new int[0];
            }

            @Override
            public int getSelectedColumnCount() {
                return 0;
            }

            @Override
            public void setSelectionModel(ListSelectionModel newModel) {

            }

            @Override
            public ListSelectionModel getSelectionModel() {
                return null;
            }

            @Override
            public void addColumnModelListener(TableColumnModelListener x) {

            }

            @Override
            public void removeColumnModelListener(TableColumnModelListener x) {

            }
        };
        table_PData.setColumnModel(TModel);

        button_home.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame optie = new JFrame("optie scherm");                 // dit zorgt ervoor dat de programatuur weet dat dit een scherm, of "JFrame" is.
                optie.setContentPane(new optionScreen(optie).panel_optieScherm);    // hier zeg je waaruit die JFrame gaat bestaan, dus je kiest welk panel. let op dat je de panel public maakt in je code.
                optie.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          // hier word gezegd wat er gebeurt als er op sluiten gedrukt word, in dit geval word het programma afgesloten
                optie.pack();                                                  // hier worden alle gui elementen uit het bestant "optie" op dezelfde JFrame gezet
                optie.setVisible(true);                                        // spreekt wel voor zich, hier word de JFrame "optie" zichtbaar gemaakt
                PData.dispose();
            }
        });


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

    public void setData(personalDataScreen[][] data) {
        String column[]={"ID","NAME","SALARY"};
    }

    public void getData(personalDataScreen data) {


    }

    public boolean isModified(personalDataScreen data) {


        return false;
    }
}
