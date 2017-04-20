package logic;


public class Logic3Main {

	public static void main(String[] args) {
		
		//XOR
		Entry entreeA = new Entry();
		Entry entreeB = new Entry();
		
		Not not1 = new Not();
		Not not2 = new Not();
		
		not1.setEntry(0, 0, entreeA);
		not2.setEntry(0, 0, entreeB);
		
		And and1 = new And();
		and1.setEntry(0, 0, not1);
		and1.setEntry(1, 0, entreeB);
		
		And and2 = new And();
		and2.setEntry(0, 0, entreeA);
		and2.setEntry(1, 0, not2);
		
		Or or1 = new Or();
		or1.setEntry(0, 0, and1);
		or1.setEntry(1, 0, and2);
		
		Exit exit = new Exit();
		exit.setEntry(0, 0, or1);
		
		
		Circuit circuit = new Circuit("Luigi");
		circuit.addDoor(entreeA);
		circuit.addDoor(entreeB);
		circuit.addDoor(not1);
		circuit.addDoor(not2);
		circuit.addDoor(and1);
		circuit.addDoor(and2);
		circuit.addDoor(or1);
		circuit.addDoor(exit);
		
		circuit.setBehavior();
		
		Door xor = circuit.createCutomDoor("xor");
		
		Entry entree1 = new Entry();
		Entry entree2 = new Entry();
		Exit sortie1 = new Exit();
		
		xor.setEntry(0, 0, entree1);
		xor.setEntry(1, 0, entree2);
		sortie1.setEntry(0, 0, xor);
		
		Circuit circuit2 = new Circuit("Mario");
		
		circuit2.addDoor(xor);
		circuit2.addDoor(entree1);
		circuit2.addDoor(entree2);
		circuit2.addDoor(sortie1);
		
		circuit2.setBehavior();
		circuit2.printThruthTable(circuit2.getTruthTable());
	/*
		Entry e1 = new Entry();
		Entry e2 = new Entry();
		Entry e3 = new Entry();
		
		And a1 = new And();
		Or o1 = new Or();
		Not n1 = new Not();
		And a2 = new And();
		
		Exit s1 = new Exit();
		
		a1.setEntry(0, 0, e1);
		a1.setEntry(1, 0, e2);
		o1.setEntry(0, 0, e2);
		o1.setEntry(1, 0, e3);
		n1.setEntry(0, 0, a1);
		a2.setEntry(0, 0, n1);
		a2.setEntry(1, 0, o1);
		s1.setEntry(0, 0, a2);
		
		Circuit circuit = new Circuit("Luigi");
		circuit.addDoor(e1);
		circuit.addDoor(e2);
		circuit.addDoor(e3);
		circuit.addDoor(a1);
		circuit.addDoor(o1);
		circuit.addDoor(n1);
		circuit.addDoor(a2);
		circuit.addDoor(s1);
		
		circuit.setBehavior();
		//circuit.printThruthTable(circuit.getTruthTable());
		Door xor = circuit.createCutomDoor("xor");
		
		Entry entree1 = new Entry();
		Entry entree2 = new Entry();
		Exit sortie1 = new Exit();
		
		xor.setEntry(0, 0, entree1);
		xor.setEntry(1, 0, entree2);
		sortie1.setEntry(0, 0, xor);
		
		Circuit circuit2 = new Circuit("Mario");
		
		circuit2.addDoor(xor);
		circuit2.addDoor(entree1);
		circuit2.addDoor(entree2);
		circuit2.addDoor(sortie1);
		
		circuit2.setBehavior();
		//circuit2.printThruthTable(circuit2.getTruthTable());
		*/
	}

}
