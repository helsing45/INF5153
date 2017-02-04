package logique;

public class Not extends Porte {
	IO entree;
	
	public Not(IO entree) {
		super();
		this.entree = entree;
	}
	
	@Override
	public void calculer() {
		this.valeur = !entree.getValeur();
	}

}
