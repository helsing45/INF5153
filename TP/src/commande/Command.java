package commande;
import logic.AbstractDoor;

/**
 * Created by Mat on 2017-04-14.
 */
public interface Command{

    void execute();
    void undo();
}
