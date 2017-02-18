package view;

import javax.swing.table.DefaultTableModel;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorTableModel extends DefaultTableModel {
    public OperatorTableModel(int entriesCount) {
        super(generateTruthTable(entriesCount),generateEntries(entriesCount));
    }

    private static Object[][] generateTruthTable(int entryCount) {
        int combinationCount = 1<<entryCount;

        // array to store combinations
        Object truthTable[][] = new Object[combinationCount][entryCount];
        for(int nn=0; nn<combinationCount; ++nn) {
            for( int n=0; n<entryCount; ++n) {
                truthTable[nn][entryCount-n-1] = (((nn>>n) & 1));
            }
        }
        return truthTable;
    }

    private static String[] generateEntries(int entryCount) {
        String[] entries = new String[entryCount];
        for (int i = 0; i < entryCount; i++) {
            entries[i] = "Entrée " + i;
        }
        return entries;
    }
}
