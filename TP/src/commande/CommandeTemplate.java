package commande;

/**
 * Created by Mat on 2017-04-14.
 */
public class CommandeTemplate implements Command{
    private Object domainObjectX; // TODO remplacer `domainObjectX` par une classe du domaine

    public CommandeTemplate(Object domainObjectX){
        this.domainObjectX = domainObjectX;
    }
    @Override
    public void execute() {

    }
}
