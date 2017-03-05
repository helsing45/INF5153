package view;

import logique.Entree;
import logique.Operator;
import logique.Sortie;
import model.Link;
import model.Template;
import utils.ErrorUtils;
import utils.NameUtils;
import utils.XmlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by Jean-Christophe D on 2017-02-26.
 */
public class OperatorsPanel extends JPanel implements OperatorLabel.Listener {

    private Template template;
    private int entriesCount, exitsCount, inputCount;
    private OperatorLabel first, second;
    private TemplateChangeListener listener;

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

    private String[] getEntriesName(ArrayList<OperatorLabel> operators) {
        String[] names = new String[operators.size()];
        for (int index = 0; index < operators.size(); index++) {
            names[index] = operators.get(index).getText();
        }
        return names;
    }

    private void addOperatorLabel(OperatorLabel operatorLabel, Point position) {
        if (canAdd(operatorLabel)) {
            add(operatorLabel);
            template.addOperator(operatorLabel, position);
            inputCount++;
            entriesCount += operatorLabel.getOperator() instanceof Entree ? 1 : 0;
            exitsCount += operatorLabel.getOperator() instanceof Sortie ? 1 : 0;
            if (operatorLabel.getOperator() instanceof Entree && listener != null) {
                listener.entriesChanged(getEntriesName(template.getEntries()));
            }
            operatorLabel.initialize(position);
            operatorLabel.setText((operatorLabel.getOperator() instanceof Sortie ? "S" : "E") + (operatorLabel.getOperator() instanceof Sortie ? exitsCount : entriesCount));
            operatorLabel.setLeftOffset((int) getBounds().getX());
            operatorLabel.setTopOffset((int) getBounds().getY());
            operatorLabel.setListener(this);
        } else {
            ErrorUtils.showError(OperatorsPanel.this, "Impossible d'ajouter");
        }
    }

    public OperatorsPanel() {
        super(null);
        this.template = new Template();
        setDropTarget(dropTarget);
    }

    public void setListener(TemplateChangeListener listener) {
        this.listener = listener;
    }

    public void save(File filePath) {

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath.getCanonicalPath() + ".xml");
            bw = new BufferedWriter(fw);
            bw.write(XmlUtils.getXmlUtils().toXML(template));

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    public void load(File filePath) {
        load((Template) XmlUtils.getXmlUtils().fromXML(filePath));
    }

    public void load(Template template) {
        removeAll();
        updateUI();
        this.template = template;
        for (OperatorLabel operatorLabel : template.getOperators().keySet()) {
            addOperatorLabel(operatorLabel, template.getLocationOf(operatorLabel));
        }
        validate();
    }

    public String[] getBooleanExpressions(){
        ArrayList<OperatorLabel> exits = template.getExits();
        String[] expressions = new String[exits.size()];
        for (int index = 0; index < exits.size(); index++) {
            expressions[index] = exits.get(index).getOperator().getBooleanExpression();
        }
        return expressions;
    }

    @Override
    public void onLink(OperatorLabel operatorLabel) {
        if (first == null) {
            first = operatorLabel;
        } else {
            second = operatorLabel;
            template.addLink(first, second);
            first = null;
            second = null;
            repaint();
        }
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

    public boolean canDelete(OperatorLabel label) {
        return label.getOperator() instanceof Entree && entriesCount > 1 || label.getOperator() instanceof Sortie && exitsCount > 1;
    }

    @Override
    public void delete(OperatorLabel label) {
        if (canDelete(label)) {
            entriesCount += label.getOperator() instanceof Entree ? -1 : 0;
            exitsCount += label.getOperator() instanceof Sortie ? -1 : 0;
            template.remove(label);
            NameUtils.removeReservation(label.getText());
            if(listener != null){
                listener.entriesChanged(getEntriesName(template.getEntries()));
            }
            remove(label);
            validate();
            repaint();
        } else {
            ErrorUtils.showError(this, "Impossible de supprimer cet input");
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Link pair : template.getLinks()) {
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

    public interface TemplateChangeListener {
        void entriesChanged(String... entries);
    }
}
