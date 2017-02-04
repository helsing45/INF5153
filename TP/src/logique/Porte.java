package logique;

public abstract class Porte extends IO {
	IO sortie;
	
	public abstract void calculer();

	public Porte() {
		super();
		this.sortie = null;
	}

	public void setSortie(IO sortie) {
		this.sortie = sortie;
	}

}
