package logique;

import java.util.ArrayList;
import java.util.List;

public abstract class Porte extends Operator {
    private List<Operator> entries, exits;

    public abstract void calculer();



    public void addEntry(int index, Operator operator) {
        entries.add(index, operator);
    }

    public void addEntry(Operator operator) {
        entries.add(operator);
    }

    public void addExit(int index, Operator operator) {
        exits.add(index, operator);
    }

    public void addExit(Operator operator) {
        exits.add(operator);
    }

    public int getExitCount() {
        return entries.size();
    }

    public int getEntryCount() {
        return exits.size();
    }

    @Override
    public Operator getEntry(int index) {
        if (index > getEntryCount()) return null;
        return entries.get(index);
    }

    @Override
    public Operator getExit(int index) {
        if (index > getExitCount()) return null;
        return exits.get(index);
    }

    public Porte() {
        super();
        entries = exits = new ArrayList<>();
    }

}
