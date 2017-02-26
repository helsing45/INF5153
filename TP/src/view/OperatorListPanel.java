package view;

import logique.Operator;

import javax.swing.*;
import java.awt.*;

public class OperatorListPanel extends JPanel {

    private JList operatorList;

    public OperatorListPanel(Operator... Operators) {
        setLayout(new BorderLayout());

        operatorList = new JList(Operators);
        operatorList.setDropMode(DropMode.INSERT);
        operatorList.setDragEnabled(true);
        operatorList.setCellRenderer(new OperatorCellRenderer());
        JScrollPane pane = new JScrollPane(operatorList);

        add(pane, BorderLayout.CENTER);
    }
}
