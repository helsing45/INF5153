package view;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Created by Jean-Christophe D on 2017-02-26.
 */
public class OperatorsPanel extends JPanel implements OperatorLabel.Listener {

    ArrayList<Pair> listOfPairs;
    private OperatorLabel first, second;

    public OperatorsPanel() {
        super(null);
        listOfPairs = new ArrayList<>();
        setDropTarget(new DropTarget() {
            public void drop(DropTargetDropEvent dtde) {
                try {
                    OperatorLabel operatorLabel = new OperatorLabel((String) dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
                    add(operatorLabel);

                    operatorLabel.initialize(dtde.getLocation());
                    operatorLabel.setLeftOffset((int) getBounds().getX());
                    operatorLabel.setTopOffset((int) getBounds().getY());
                    operatorLabel.setListener(OperatorsPanel.this);
                    validate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onLinkMenuClicked(OperatorLabel operatorLabel) {
        operatorLabel.setSelected(true);
        if (first == null) {
            first = operatorLabel;
        } else {
            second = operatorLabel;
            first.setSelected(false);
            second.setSelected(false);
            listOfPairs.add(new Pair(first,second));
            first = second = null;
            repaint();
        }
    }

    @Override
    public void onLocationChange(OperatorLabel operatorLabel) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Pair pair : listOfPairs) {
            JLabel label1 = pair.getLabel1();
            JLabel label2 = pair.getLabel2();
            Point point1 = label1.getLocation();
            Point point2 = label2.getLocation();
            int i = pair.howToDraw();
            if (i == 1) {
                g.drawLine(point1.x, point1.y + label1.getHeight() / 2, point2.x + label2.getWidth(), point2.y + label2.getHeight() / 2);
            } else if (i == 2) {
                g.drawLine(point2.x, point2.y + label2.getHeight() / 2, point1.x + label1.getWidth(), point1.y + label1.getHeight() / 2);
            } else if (i == 3) {
                g.drawLine(point1.x + label1.getWidth() / 2, point1.y, point2.x + label2.getWidth() / 2, point2.y + label2.getHeight());
            } else if (i == 4) {
                g.drawLine(point2.x + label2.getWidth() / 2, point2.y, point1.x + label1.getWidth() / 2, point1.y + label1.getHeight());
            }
        }
    }

    class Pair {
        JLabel label1;
        JLabel label2;

        private Pair() {
        }

        public Pair(JLabel label1, JLabel label2) {
            this.label1 = label1;
            this.label2 = label2;
        }

        @Override
        public String toString() {
            return "{" + label1.getLocation() + "," + label2.getLocation() + "}";
        }

        public int howToDraw() {
            Point point1 = label1.getLocation();
            Point point2 = label2.getLocation();
            if (point1.x > point2.x) {
                return 1;
            } else if (point1.x < point2.x) {
                return 2;
            } else if (point1.y > point2.y) {
                return 3;
            } else if (point1.y < point2.y) {
                return 4;
            } else
                return 5;
        }

        public JLabel getLabel1() {
            return label1;
        }

        public JLabel getLabel2() {
            return label2;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Pair) {
                Pair temp = (Pair) obj;
                if ((temp.toString()).equalsIgnoreCase(this.toString())) {
                    return true;
                }
            }
            return false;
        }
    }

}
