package view;

import logique.IO;

import javax.swing.*;
import java.awt.*;

public class OperatorListPanel extends JPanel {



    private JList operatorList;

    public OperatorListPanel(IO... IOs) {
        setLayout(new BorderLayout());

        operatorList = new JList(IOs);
        operatorList.setDropMode(DropMode.INSERT);
        operatorList.setDragEnabled(true);
        operatorList.setCellRenderer(new OperatorCellRenderer());
        JScrollPane pane = new JScrollPane(operatorList);

        add(pane, BorderLayout.CENTER);
    }

    public static void main(String s[]) {
        JFrame frame = new JFrame("List Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new OperatorListPanel());
        frame.pack();
        frame.setVisible(true);
    }
}

class OperatorDTO {
    private final String title;
    private final String imagePath;
    private ImageIcon image;

    public OperatorDTO(String title, String imagePath) {
        this.title = title;
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public ImageIcon getImage() {
        if (image == null) {
            image = new ImageIcon(getClass().getResource(imagePath));
        }
        return image;
    }

    // Override standard toString method to give a useful result
    public String toString() {
        return title;
    }
}
