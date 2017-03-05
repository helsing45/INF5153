package logique;

public class Entree extends Operator {

    private String variable;

    public Entree() {
        super();
        addExit(null);
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    @Override
    public String getName() {
        return "entry";
    }

    @Override
    public String getBooleanExpression() {
        return getVariable();
    }

    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }

    @Override
    public void calculer() {
        // TODO Auto-generated method stub

    }

}
