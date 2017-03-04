package view;

import logique.Entree;
import logique.Operator;
import logique.Sortie;
import utils.ErrorUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Jean-Christophe D on 2017-02-26.
 */
public class OperatorsPanel extends JPanel implements OperatorLabel.Listener {

    private ArrayList<Pair> listOfPairs;
    private int entriesCount, exitsCount, inputCount;
    private OperatorLabel first, second;

    DropTarget dropTarget = new DropTarget() {
        public void drop(DropTargetDropEvent dtde) {
            try {
                addOperatorLabel(new OperatorLabel((Operator) dtde.getTransferable().getTransferData(OperatorItemTransferable.LIST_ITEM_DATA_FLAVOR)), dtde.getLocation());
                validate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void addOperatorLabel(OperatorLabel operatorLabel, Point position) {
        if (canAdd(operatorLabel)) {
            add(operatorLabel);
            inputCount++;
            entriesCount += operatorLabel.getOperator() instanceof Entree ? 1 : 0;
            exitsCount += operatorLabel.getOperator() instanceof Sortie ? 1 : 0;
            operatorLabel.initialize(position);
            operatorLabel.setText((operatorLabel.getOperator() instanceof Sortie ? "S" :"E") + (operatorLabel.getOperator() instanceof Sortie ? exitsCount : entriesCount));
            operatorLabel.setLeftOffset((int) getBounds().getX());
            operatorLabel.setTopOffset((int) getBounds().getY());
            operatorLabel.setListener(this);
        }else {
            ErrorUtils.showError(OperatorsPanel.this, "Impossible d'ajouter");
        }
    }

    public OperatorsPanel(HashMap<OperatorLabel, Point> template) {
        this();
        for (OperatorLabel operatorLabel : template.keySet()) {
            addOperatorLabel(operatorLabel, template.get(operatorLabel));
        }
        validate();
    }

    public OperatorsPanel() {
        super(null);
        listOfPairs = new ArrayList<>();
        setDropTarget(dropTarget);
    }

    @Override
    public void onLink(OperatorLabel operatorLabel) {
        if (first == null) {
            first = operatorLabel;
        } else {
            second = operatorLabel;
            listOfPairs.add(new Pair(first, second));
            first = null;
            second = null;
            repaint();
        }
    }

    public void save() {
    }

    @Override
    public void onLinkCanceled(OperatorLabel operatorLabel) {
        if (first.equals(operatorLabel)) {
            first = null;
        } else {
            second = null;
        }
    }

    @Override
    public void onLocationChange(OperatorLabel operatorLabel) {
        repaint();
    }

    public boolean canAdd(OperatorLabel operatorLabel) {
        return inputCount < 50 && (!(operatorLabel.getOperator() instanceof Sortie || operatorLabel.getOperator() instanceof Entree) || (operatorLabel.getOperator() instanceof Sortie ? exitsCount : entriesCount) < 5);
    }

    @Override
    public boolean canDelete(OperatorLabel label) {
        return !(label.getOperator() instanceof Sortie || label.getOperator() instanceof Entree) || (label.getOperator() instanceof Sortie ? exitsCount : entriesCount) > 1;
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
        OperatorLabel label1;
        OperatorLabel label2;

        private Pair() {
        }

        public Pair(OperatorLabel label1, OperatorLabel label2) {
            this.label1 = label1;
            this.label1.linkTo(label2);
            this.label2 = label2;
            this.label2.linkTo(label1);
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

    public static class OperatorItemTransferable implements Transferable {

        public static final DataFlavor LIST_ITEM_DATA_FLAVOR = new DataFlavor(Operator.class, "java/Operator");

        private Operator operator;

        public OperatorItemTransferable(Operator operator) {
            this.operator = operator;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{LIST_ITEM_DATA_FLAVOR};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(LIST_ITEM_DATA_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            return operator;
        }
    }
}
