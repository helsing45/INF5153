package model;

import java.util.ArrayList;

/**
 * Created by j-c9 on 2017-03-27.
 */
public abstract class BaseModel {
    private ArrayList<Observer> listObserver = new ArrayList<>();

    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    public void notifyObserver(String str) {
        if (str.matches("^0[0-9]+"))
            str = str.substring(1, str.length());

        for (Observer obs : listObserver)
            obs.update(str);
    }

    public void removeObserver() {
        listObserver = new ArrayList<Observer>();
    }
}
