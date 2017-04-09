package view;

import model.Calculable;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class TruthTablePanel extends JScrollPane {

    public TruthTablePanel() {
        super();
        setBorder(new EmptyBorder(0, 0, 69, 13));
        setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        setBackground(Color.WHITE);
    }

    public void load(ArrayList<Calculable> calculables, String... entries){
        JTable table = getGenericTable();
        table.setModel(new OperatorTableModel(calculables, entries));
        setViewportView(table);
        validate();
    }

    public void load(int entryCount, String... entries) {
        JTable table = getGenericTable();
        ArrayList<Calculable> calculables = new ArrayList<>();
        for (int i = 0; i < entries.length - entryCount; i++) {
            calculables.add(new Calculable() {
                @Override
                public String calculate(String values) {
                    return "ND";
                }
            });
        }
        table.setModel(new OperatorTableModel(calculables, entries));
        setViewportView(table);
        validate();
    }

    private static JTable getGenericTable() {
        JTable table = new JTable();
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //table.setModel();
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
                Component tableCellRendererComponent = super.getTableCellRendererComponent(arg0, arg1, arg2, arg3, arg4, arg5);
                ((DefaultTableCellRenderer) tableCellRendererComponent).setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
                return tableCellRendererComponent;
            }
        };
        for (int column = 0; column < table.getColumnCount(); column++) {
            table.getColumnModel().getColumn(column).setCellRenderer(renderer);
        }
        return table;
    }
}
