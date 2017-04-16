package commande;

import java.util.Stack;

/**
 * Created by Mat on 2017-04-14.
 */
public class Caretaker {
    private Stack<Memento> mementos;

    public void addMemento(Memento m){
        mementos.add(m);
    }

    public Memento getMemento(){
        return mementos.pop();
    }
}
