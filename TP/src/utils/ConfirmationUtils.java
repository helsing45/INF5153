package utils;

import javax.swing.*;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class ConfirmationUtils {

    public static void askForConfirmation(ConfirmationListener listener){
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, "Vous allez perdre votre circuit en cours. Etre-vous sur de vouloir continuer ?", "Confirmation", dialogButton);
        listener.onChoiceMade(dialogResult == 0);
    }

    public interface ConfirmationListener{
        void onChoiceMade(boolean asConfirm);
    }
}
