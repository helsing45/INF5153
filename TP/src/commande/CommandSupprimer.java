package commande;
import logic.AbstractDoor;

/**
 * Created by Mat on 2017-04-14.
 */
public class CommandSupprimer implements Command{
    private AbstractDoor domainObjectX1; // TODO remplacer `domainObjectX1` par une classe du domaine

    public CommandSupprimer(AbstractDoor domainObjectX){
        this.domainObjectX1 = domainObjectX;
    }
    @Override
    public void execute() {
        //this.domainObjectX1.DELETE();  //TODO quel methode supprime la porte?
    }

    @Override
    public void undo() {

    }
}
