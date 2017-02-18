package view;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorTransferHandler extends TransferHandler {

    @Override
    protected Transferable createTransferable(JComponent c) {
        JList list = (JList) c;
        StringBuffer images = new StringBuffer();
        Object[] selectedValues = list.getSelectedValues();
        for (int i = 0; i < selectedValues.length; i++) {
            images.append(selectedValues[i] + ";");
        }
        return new StringSelection(images.toString());
    }

    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY;
    }
}
