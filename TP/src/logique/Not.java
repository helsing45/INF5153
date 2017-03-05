package logique;

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

}
