package logique;

//Ne sert qu'a des fins de tests
public class LogiqueMain {

	public static void main(String[] args) {
		//Simulation d'une porte XOR
		
		Entree entreeA = new Entree();
		
		Entree entreeB = new Entree();
		
		And and1 = new And(entreeA, entreeB);
		
		Or or1 = new Or(entreeA, entreeB);
		
		entreeA.ajouterSortie(and1);
		entreeA.ajouterSortie(or1);
		
		entreeB.ajouterSortie(and1);
		entreeB.ajouterSortie(or1);
		
		Not not1 = new Not(and1);
		
		And and2 = new And(not1, or1);
		
		Sortie sortie = new Sortie(and2);
		
		//Valeurs des deux entrees de la porte XOR
		entreeA.setValeur(true);
		entreeB.setValeur(false);
		
		and1.calculer();
		or1.calculer();
		not1.calculer();
		and2.calculer();
		
		System.out.println(sortie.getValeur());
	}

}
