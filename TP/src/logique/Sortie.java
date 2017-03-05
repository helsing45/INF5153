package logique;

public class Sortie extends Operator {
	Operator entree;
	
	@Override
	public boolean getValeur() {
		return entree.getValeur();
	}

	public Sortie() {
		super();
	}

	public Sortie(Operator entree) {
		super();
		this.entree = entree;
	}

	@Override
	public void calculer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName() {
		return "end";
	}

	@Override
	public int getExitCount() {
		return 0;
	}

	@Override
	public int getEntryCount() {
		return 1;
	}

	@Override
	public Operator getEntry(int index) {
		if(index != 0)return null;
		return entree;
	}

	@Override
	public Operator getExit(int index) {
		return null;
	}
}
