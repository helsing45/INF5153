package logique;

public class And extends Porte {
	IO entree1;
	IO entree2;
	
	@Override
	public void calculer() {
		this.valeur = entree1.getValeur() && entree2.getValeur();

	}

	public And(IO entree1, IO entree2) {
		super();
		this.entree1 = entree1;
		this.entree2 = entree2;
	}

}
