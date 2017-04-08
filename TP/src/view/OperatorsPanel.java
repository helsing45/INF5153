package view;

import controler.BaseController;
import model.BaseDTO;
import model.BaseModel;
import model.Link;

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
                addComponent((T) dtde.getTransferable().getTransferData(OperatorItemTransferable.LIST_ITEM_DATA_FLAVOR), dtde.getLocation(), true);
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

    public void addComponent(T component, Point position, boolean manual) {
        T copyOfComponent = (T) component.clone();
        copyOfComponent.setBound(getBounds());
        controller.addComponent(copyOfComponent, position, manual);
    }

    @Override
    public void onNameChange(OperatorLabel operatorLabel, String name) {
        controller.onNameChange((T) operatorLabel.getOperator(), name);
    }

    @Override
    public void onLink(OperatorLabel operatorLabel) {
        if (first == null) {
            first = operatorLabel;
        } else {
            second = operatorLabel;
            controller.addLink(first, second);

            first.deselect();
            second.deselect();
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
        controller.onLocationChange((T) operatorLabel.getOperator(), operatorLabel.getLocation());
        repaint();
    }

    @Override
    public void delete(OperatorLabel label) {
        controller.removeComponent((T) label.getOperator());
    }

    public OperatorLabel find(String id) {
        for (Component component : getComponents()) {
            if (component instanceof OperatorLabel && ((OperatorLabel) component).getId().equals(id)) {
                return (OperatorLabel) component;
            }
        }
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Link link : controller.getLinks()) {
            OperatorLabel label1 = find(link.getId1());
            OperatorLabel label2 = find(link.getId2());
            label1.link(link.getLbl1SelectedPortType(), link.getLbl1SelectedPortIndex(), label2);
            label2.link(link.getLbl2SelectedPortType(), link.getLbl2SelectedPortIndex(), label1);
            Point point1 = label1.getLocation();
            Point point2 = label2.getLocation();
            g.drawLine(point1.x + label1.getWidth() / 2, point1.y + label1.getHeight() / 2, point2.x + label2.getWidth() / 2, point2.y + label2.getHeight() / 2);
        }
    }


    public void refreshTemplate() {
        removeAll();
        updateUI();
        validate();
        HashMap<T, Point> componentsPosition = controller.getComponentsPosition();
        ArrayList<OperatorLabel> list = new ArrayList<>();
        for (T t : componentsPosition.keySet()) {
            OperatorLabel label = new OperatorLabel<>(t, componentsPosition.get(t), this);
            list.add(label);
            add(label);
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
