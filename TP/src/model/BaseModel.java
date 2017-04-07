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

    public void notifyObserver(){
        for (Observer obs : listObserver)
            obs.refreshTemplate();
    }

    public void removeObserver() {
        listObserver = new ArrayList<>();
    }

    public abstract ArrayList<Link> getLinks();
    public abstract void update(BaseModel newModel);
    public abstract void reset();
    public abstract void addComponent(T lbl, Point position);
    public abstract void setComponentPosition(T dto,Point position);
    public void addComponent(T lbl, int X, int Y){
        addComponent(lbl,new Point(X,Y));
    }
    public abstract void removeComponent(T component);
}
