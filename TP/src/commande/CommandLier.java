package commande;
import logic.AbstractDoor;
import java.util.List;
/**
 * Created by Mat on 2017-04-14.
 */
public class CommandLier implements Command, Cloneable{
    private AbstractDoor domainObjectX1; // TODO remplacer `domainObjectX1` par une classe du domaine
    private Object state;
    private List<Object> previousEntries;
    private List<Object> previousExits;

    private Memento memento;

    public CommandLier(AbstractDoor domainObjectX1){
        this.domainObjectX1 = domainObjectX1;
        //this.previousEntries = domainObjectX1.getEntries(); //TODO need method.
        this.setState();
    }

    private void setState(){
        try {
            this.state = this.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.out.println("Error cloning " +
                    this.getClass().getSimpleName());
        }
    }



    @Override
    public void execute() {
        this.memento = new Memento(this.state);
        //this.domainObjectX1.[setEntry()|setExit()]; //TODO comment on recupere les arguements pour la commande?;
    }

    @Override
    public void undo() {
        this.state = this.memento.getState();
        //this.domainObjectX1.[setEntry()|setExit()]; //TODO comment on recupere les arguements pour la commande?
    }
}
