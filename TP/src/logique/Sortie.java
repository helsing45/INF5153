package logique;

public class Sortie extends Operator {
	
	@Override
	public boolean getValeur() {
		return getEntry(0).getValeur();
	}

	@Override
	public String getBooleanExpression() {
		if(getEntryCount() > 0){
			return getEntry(0).getBooleanExpression();
		}
		return "N/D";
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
		if(getEntry(0) != null){

		}
		// TODO Auto-generated method stub
	}

	@Override
	public String getName() {
		return "end";
	}
}
