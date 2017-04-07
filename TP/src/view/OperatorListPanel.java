package view;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import static view.OperatorsPanel.OperatorItemTransferable.LIST_ITEM_DATA_FLAVOR;

public class OperatorListPanel<T extends Transferable> extends JPanel {

    private JList operatorList;

    public OperatorListPanel(BaseDTO... operators) {
        setLayout(new BorderLayout());

        operatorList = new JList(operators);
        operatorList.setTransferHandler(new ListTransferHandler());
        operatorList.setDropMode(DropMode.INSERT);
        operatorList.setDragEnabled(true);
        operatorList.setCellRenderer(new OperatorCellRenderer());
        JScrollPane pane = new JScrollPane(operatorList);

        add(pane, BorderLayout.CENTER);
    }

    public class ListTransferHandler extends TransferHandler {

        @Override
        public boolean canImport(TransferSupport support) {
            return (support.getComponent() instanceof JLabel) && support.isDataFlavorSupported(LIST_ITEM_DATA_FLAVOR);
        }

        @Override
        public boolean importData(TransferSupport support) {
            boolean accept = false;
            if (canImport(support)) {
                try {
                    Transferable t = support.getTransferable();
                    Object value = t.getTransferData(LIST_ITEM_DATA_FLAVOR);
                    if (value instanceof BaseDTO) {
                        Component component = support.getComponent();
                        if (component instanceof JLabel) {
                            ((JLabel) component).setText(((BaseDTO) value).getValue());
                            accept = true;
                        }
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
            return accept;
        }
        @Override
        public int getSourceActions(JComponent c) {
            return DnDConstants.ACTION_COPY_OR_MOVE;
        }

        @Override
        protected Transferable createTransferable(JComponent c) {
            Transferable t = null;
            if (c instanceof JList) {
                JList list = (JList) c;
                Object value = list.getSelectedValue();
                if (value instanceof BaseDTO) {
                    BaseDTO li = (BaseDTO) value;
                    t = new OperatorsPanel.OperatorItemTransferable(li);
                }
            }
            return t;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            // Here you need to decide how to handle the completion of the transfer,
            // should you remove the item from the list or not...
        }
    }
}
