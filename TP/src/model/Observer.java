package model;

public interface Observer {
    void refreshTemplate();
    void reset();
    void truthTableEntriesHasChange(int entryCount, String... entries);
    void refreshTruthTable();
}