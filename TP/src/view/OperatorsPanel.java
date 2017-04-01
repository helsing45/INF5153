package view;

import controler.BaseController;
import model.BaseDTO;
import model.BaseModel;
import utils.ErrorUtils;
import utils.NameUtils;

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
public class OperatorsPanel<T extends BaseDTO, genericModel extends BaseModel<T>> extends JPanel implements OperatorLabel.Listener {

    //private Template template;
    private OperatorLabel first, second;
    private BaseController<T, genericModel> controller;

    DropTarget dropTarget = new DropTarget() {
        public void drop(DropTargetDropEvent dtde) {
            try {
                addComponent((T) dtde.getTransferable().getTransferData(OperatorItemTransferable.LIST_ITEM_DATA_FLAVOR), dtde.getLocation());
                validate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public OperatorsPanel(BaseController<T, genericModel> controller) {
        super(null);
        this.controller = controller;
        setDropTarget(dropTarget);
    }

    private String[] getEntriesName(ArrayList<OperatorLabel> operators) {
        String[] names = new String[operators.size()];
        for (int index = 0; index < operators.size(); index++) {
            names[index] = operators.get(index).getText();
        }
        return names;
    }

    public void addComponent(T component, Point position) {
        T copyOfComponent = (T) component.clone();
        copyOfComponent.setBound(getBounds());
        if (!controller.addComponent(copyOfComponent, position)) {
            ErrorUtils.showError(OperatorsPanel.this, "Impossible d'ajouter");
        }
    }

    @Override
    public void onLink(OperatorLabel operatorLabel) {
        if (first == null) {
            first = operatorLabel;
        } else {
            second = operatorLabel;
            //TODO link with controller
            //template.addLink(first, second);
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

    public boolean canDelete(OperatorLabel label) {
        return true; //TODO refaire la validation dans le controleur
        //return label.getOperator() instanceof Entree && entriesCount > 1 || label.getOperator() instanceof Sortie && exitsCount > 1;
    }

    @Override
    public void delete(OperatorLabel label) {
        if (canDelete(label)) {
            //TODO refaire le decompte
            //entriesCount += label.getOperator() instanceof Entree ? -1 : 0;
            //exitsCount += label.getOperator() instanceof Sortie ? -1 : 0;
            // template.remove(label);
            NameUtils.removeReservation(label.getText());
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
        for (BaseController.Line line : controller.getLines()) {
            g.drawLine(line.getX1(), line.getY1(), line.getX2(), line.getX2());
        }
    }


    public void refreshTemplate() {
        removeAll();
        updateUI();
        validate();
        HashMap<T, Point> componentsPosition = controller.getComponentsPosition();
        for (T t : componentsPosition.keySet()) {
            add(new OperatorLabel<>(t, componentsPosition.get(t), this));
        }

        repaint();
    }

    public static class OperatorItemTransferable implements Transferable {

        public static final DataFlavor LIST_ITEM_DATA_FLAVOR = new DataFlavor(BaseDTO.class, "java/BaseDTO");

        private BaseDTO operator;

        public OperatorItemTransferable(BaseDTO operator) {
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
