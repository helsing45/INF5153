package utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by j-c9 on 2017-03-04.
 */
public class ErrorUtils {

    public static void showError(Component parent, String error){
        JOptionPane.showMessageDialog(parent, error, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
