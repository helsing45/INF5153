package logique;

public abstract class IO {
	Boolean valeur;
	
	public abstract void calculer();
	
	public IO() {
		super();
		this.valeur = null;
	}



	public boolean getValeur() {
		return valeur;
	}
	
}
