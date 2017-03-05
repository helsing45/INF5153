package logique;

import utils.BooleanExpressionUtils;

public class Not extends Porte {

    public Not() {
        super();
        addEntry(null);
        addExit(null);
    }

    public Not(Operator entree) {
        super();
        addEntry(entree);
    }

    @Override
    public void calculer() {
        this.valeur = !getEntry(0).getValeur();
    }

    @Override
    public String getName() {
        return "NOT";
    }

    @Override
    public String getBooleanExpression() {
        return getEntry(0).getBooleanExpression() + BooleanExpressionUtils.NOT;
    }

}
