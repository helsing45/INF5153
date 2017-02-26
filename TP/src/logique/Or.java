package logique;

public class Or extends Porte {

	public Or() {
		super();
		addEntry(null);
		addEntry(null);
		addExit(null);
	}

	public Or(Operator entree1, Operator entree2) {
		super();
		addEntry(0,entree1);
		addEntry(1,entree2);
	}
	
	@Override
	public void calculer() {
		for (int index = 0; index < getExitCount(); index++) {
			this.valeur |= getEntry(index).getValeur();
		}
	}

	@Override
	public String getName() {
		return "OR";
	}

}
