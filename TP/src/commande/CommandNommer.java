package commande;
import logic.AbstractDoor;


/**
 * Created by Mat on 2017-04-14.
 */
public class CommandNommer implements Command{
    private AbstractDoor domainObjectX1; // TODO remplacer `domainObjectX1` par une classe du domaine
    private Object state;
    private String previousDoorName;
    private Memento memento;

    public CommandNommer(AbstractDoor domainObjectX){
        this.domainObjectX1 = domainObjectX;
        this.previousDoorName = domainObjectX.getName();
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
        String newDoorName = "TODO in " +
                this.getClass().getName(); // TODO get new name from UI.
        this.domainObjectX1.setName(newDoorName);
    }

    @Override
    public void undo() {
        this.state = this.memento.getState();
        this.previousDoorName = ((CommandNommer) this.state).previousDoorName;
    }
}
