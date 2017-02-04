package logique;

public class Sortie extends IO {
	IO entree;
	
	@Override
	public boolean getValeur() {
		return entree.getValeur();
	}

	public Sortie(IO entree) {
		super();
		this.entree = entree;
	}

	@Override
	public void calculer() {
		// TODO Auto-generated method stub
		
	}
}
