package commande;

/**
 * Created by Mat on 2017-04-14.
 */
public class CommandRedo implements Command{
    private Object domainObjectX; // TODO remplacer `domainObjectX` par une classe du domaine

    public CommandRedo(Object domainObjectX){
        this.domainObjectX = domainObjectX;
    }
    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }
}
