package logique;

public class Entree extends Operator {

    public Entree() {
        super();
        addExit(null);
    }

    @Override
    public String getName() {
        return "entry";
    }

    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }

    @Override
    public void calculer() {
        // TODO Auto-generated method stub

    }

}
