package commande;

/**
 * Created by Mat on 2017-04-14.
 */
public class CommandOuvrir implements Command{
    private Object domainObjectX; // TODO remplacer `domainObjectX` par une classe du domaine

    public CommandOuvrir(Object domainObjectX){
        this.domainObjectX = domainObjectX;
    }
    @Override
    public void execute() {

    }
}
