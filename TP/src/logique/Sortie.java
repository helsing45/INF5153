package logique;

public class Sortie extends Operator {
	
	@Override
	public boolean getValeur() {
		return getEntry(0).getValeur();
	}

	public Sortie() {
		super();
		addEntry(null);
	}

	public Sortie(Operator entree) {
		super();
		addEntry(entree);
	}

	@Override
	public void calculer() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "end";
	}
}
