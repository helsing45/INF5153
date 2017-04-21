package model;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by j-c9 on 2017-03-27.
 */
public abstract class BaseModel<T extends BaseDTO> {
    private ArrayList<Observer> listObserver = new ArrayList<>();

    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    public void notifyTableCalculate(String[][] table){
        for (Observer observer : listObserver) {
            observer.truthTableCalculate(table);
        }
    }

    public void notifyObserverEntriesHasChange(int entryCount, String... entries){
        for (Observer observer : listObserver) {
            observer.truthTableEntriesHasChange(entryCount, entries);
        }
    }

    public void notifyObserver() {
        for (Observer observer : listObserver) {
            observer.refreshTemplate();
        }
    }

    public void notifyReset(){
        for (Observer observer : listObserver) {
            observer.reset();
        }
    }

    public void removeObserver() {
        listObserver = new ArrayList<>();
    }

    public abstract ArrayList<Link> getLinks();

    public abstract void update(BaseModel newModel);

    public abstract void reset();

    public abstract void addComponent(T lbl, Point position);

    public abstract void setComponentPosition(T dto, Point position);

    public abstract void updateComponentName(T dto, String name);

    public abstract void calculate();

    public void addComponent(T lbl, int X, int Y) {
        addComponent(lbl, new Point(X, Y));
    }

    public abstract void removeComponent(T component);
}
