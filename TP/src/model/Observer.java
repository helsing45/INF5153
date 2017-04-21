package model;

import java.util.ArrayList;

public interface Observer {
    void refreshTemplate();
    void reset();
    void truthTableEntriesHasChange(int entryCount, String... entries);
    void truthTableCalculate(String[][] table);
    void refreshTruthTable();
}