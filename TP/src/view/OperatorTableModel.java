package view;

import model.Calculable;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorTableModel extends DefaultTableModel {
    public OperatorTableModel(List<Calculable> exitsInterface, String... entries) {
        super(getCompleteTable(exitsInterface, entries), entries);
    }

    public OperatorTableModel(String[][] values, String[] entries) {
        super(values, entries);
    }

    public static Object[][] getCompleteTable(List<Calculable> exitsInterface, String... entries) {
        int entryCount = entries.length - exitsInterface.size();
        Object[][] valueTable = getValueTable(entryCount);
        if (entryCount == entries.length)
            return valueTable;

        for (int i = 0; i < valueTable.length; i++) {
            Object[] line = new Object[entries.length];
            String value = "";
            for (int i1 = 0; i1 < entries.length; i1++) {
                if (i1 < entryCount) {
                    value += valueTable[i][i1];
                    line[i1] = valueTable[i][i1];
                } else {
                    line[i1] = exitsInterface.get(i1 - entryCount).calculate(value);
                }
            }
            valueTable[i] = line;
        }
        return valueTable;
    }

    private static Object[][] getValueTable(int entryCount) {
        int combinationCount = 1 << entryCount;

        // array to store combinations
        Object truthTable[][] = new Object[combinationCount][entryCount];
        for (int nn = 0; nn < combinationCount; ++nn) {
            for (int n = 0; n < entryCount; ++n) {
                truthTable[nn][entryCount - n - 1] = (((nn >> n) & 1));
            }
        }
        return truthTable;
    }
}
