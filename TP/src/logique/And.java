package logique;

public class And extends Porte {
	IO entree1;
	IO entree2;

	public And() {
		super();
	}

	@Override
	public void calculer() {
		this.valeur = entree1.getValeur() && entree2.getValeur();

	}

	@Override
	public String getName() {
		return "AND";
	}

	public And(IO entree1, IO entree2) {
		super();
		this.entree1 = entree1;
		this.entree2 = entree2;
	}

}
