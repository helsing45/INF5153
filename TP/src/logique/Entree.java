package logique;

import java.util.ArrayList;

public class Entree extends IO {

	ArrayList<IO> sorties;
	
	public Entree() {
		super();
		sorties = new ArrayList<IO>();
	}
	
	public Entree(ArrayList<IO> sorties) {
		super();
		this.sorties = sorties;
	}
	
	public void ajouterSortie(IO sortie) {
		sorties.add(sortie);
	}

	public void setValeur(boolean valeur) {
		this.valeur = valeur;
	}

	@Override
	public void calculer() {
		// TODO Auto-generated method stub
		
	}

}
