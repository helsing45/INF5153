package logic;


public class Logic3Main {

	public static void main(String[] args) {
		
		/* XOR
		
		Entry entreeA = new Entry();
		Entry entreeB = new Entry();
		
		System.out.println(entreeB.getName());
		
		//entreeA.setExitValues("0");
		//entreeB.setExitValues("0");
		
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
		/*
		not1.calculate();
		not2.calculate();
		and1.calculate();
		and2.calculate();
		or1.calculate();
		exit.calculate();
		
		
		Circuit circuit = new Circuit("Luigi");
		circuit.addDoor(entreeA);
		circuit.addDoor(entreeB);
		circuit.addDoor(not1);
		circuit.addDoor(not2);
		circuit.addDoor(and1);
		circuit.addDoor(and2);
		circuit.addDoor(or1);
		circuit.addDoor(exit);
		//System.out.println(circuit.validate());
		//circuit.calculate();
		circuit.setBehavior();
//		circuit.calculateLine("11");
		//circuit.get01Permutations(3);
//		circuit.getExitValues();
		circuit.printThruthTable(circuit.getTruthTable());

		System.out.println(circuit.behavior.get("10"));
	*/
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
		circuit.printThruthTable(circuit.getTruthTable());
		
		
	}

}
