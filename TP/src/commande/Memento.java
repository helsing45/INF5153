package commande;

/**
 * Created by Mat on 2017-04-14.
 */
public class Memento {
    private final Object state; //TODO quel est le type qui doit etre utilise pour le memento?

    public Memento(Object state){
        this.state = state;
    }

    public Object getState() {
        return state;
    }
}
