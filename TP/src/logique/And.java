package logique;

public class And extends Porte {

	public And() {
		super();
		addEntry(null);
		addEntry(null);
		addExit(null);
	}

	@Override
	public void calculer() {
		for (int index = 0; index < getExitCount(); index++) {
			this.valeur &= getEntry(index).getValeur();
		}
	}

	@Override
	public String getName() {
		return "AND";
	}

	public And(Operator entree1, Operator entree2) {
		super();
		addEntry(0,entree1);
		addEntry(1,entree2);
	}

}
