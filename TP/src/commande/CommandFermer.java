package commande;

/**
 * Created by Mat on 2017-04-14.
 */
public class CommandFermer implements Command{
    private Object domainObjectX; // TODO remplacer `domainObjectX` par une classe du domaine

    public CommandFermer(Object domainObjectX){
        this.domainObjectX = domainObjectX;
    }
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
