package logique;

import java.util.ArrayList;

public class Entree extends Operator {

	ArrayList<Operator> sorties;
	
	public Entree() {
		super();
		sorties = new ArrayList<Operator>();
	}

	@Override
	public String getName() {
		return "entry";
	}

	@Override
	public int getExitCount() {
		return sorties.size();
	}

	@Override
	public int getEntryCount() {
		return 0;
	}

	@Override
	public Operator getEntry(int index) {
		return null;
	}

	@Override
	public Operator getExit(int index) {
		if(index > getEntryCount())return null;
		return sorties.get(index);
	}

	public Entree(ArrayList<Operator> sorties) {
		super();
		this.sorties = sorties;
	}
	
	public void ajouterSortie(Operator sortie) {
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
