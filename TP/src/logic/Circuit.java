package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Circuit extends AbstractDoor {
	
	private List<Entry> entries;
	private List<Exit> exits;
	private List<AbstractDoor> contents;
	private String[][] truthTable;

	public Circuit(String name) {
		super(name);
		contents = new ArrayList<>();
		entries = new ArrayList<>();
		exits = new ArrayList<>();
	}
	
	public void addDoor(AbstractDoor door){
		if (door instanceof Entry) {
			entries.add((Entry)door);
		} else if (door instanceof Exit) {
			exits.add((Exit)door);
		} else {
			contents.add(door);
		}
	}
	
	public boolean validate() {
		for (int i = 0; i < contents.size(); i++){
			if (contents.get(i).entries == null) {continue;}
			for (int j = 0; j < contents.get(i).entries.size(); j++){
				if (contents.get(i).entries.get(j) == null){
					return false;
				}
			}
		}
		return true;
	}
	
	private List<String> get01Permutations(int n){
		List<String> ret = new ArrayList<>();
		
		double lenght = Math.pow(2, (n));
		for (int i = 0; i < lenght; i++) {
			String binaryString = Integer.toBinaryString(i);
			
			while (binaryString.length() < n) {
				binaryString = "0" + binaryString;
			}
			
			ret.add(binaryString);
		}
		
		return ret;
	}
	
	void setBehavior() {
		List<String> inputs = get01Permutations(entries.size());
		behavior = new HashMap<>();
		
		for (int i = 0; i < inputs.size(); i++) {
			calculateLine(inputs.get(i));
			//System.out.println(inputs.get(i));
			//System.out.println(getExitValues());
			behavior.put(inputs.get(i), getExitValues());
		}
	}
	
	public void calculateLine(String input) {
		if (input.length() == entries.size()) {
			for (int i = 0; i < entries.size(); i++) {
				entries.get(i).setExitValues(input.charAt(i)+"");
			}
			calculate();
		} else {
			System.out.println("Input invalide avec le nombre d'entrees du circuit");
		}
	}

	@Override
	public void calculate() {
		for (int i = 0; i < contents.size(); i++){
			if (contents.get(i).isCalculable() && !contents.get(i).isCalculated){
				contents.get(i).calculate();
			}
		}
		for (int i = 0; i < exits.size(); i++){
			exits.get(i).calculate();
		}
		resetIsCalculated();
	}
	
	public String getExitValues() {
		String ret = "";
		for (int i = 0; i < exits.size(); i ++) {
			ret = ret + exits.get(i).getExitValue(0);
		}
		return ret;
	}
	
	public String[][] getTruthTable(){
		String truthTable[][] = new String[(int)Math.pow(2, entries.size())+1][entries.size()+exits.size()];
		
		//Premiere ligne avec les noms des entrees/sorties
		for (int i = 0; i < entries.size(); i++) {
			truthTable[0][i] = entries.get(i).getName();
		}
		int j = 0;
		for (int i = entries.size(); i < entries.size()+exits.size(); i++) {
			truthTable[0][i] = exits.get(j++).getName();
		}
		
		List<String> inputs = get01Permutations(entries.size());
		int l = 1;
		for(int i = 0; i < inputs.size(); i++) {
			String input = inputs.get(i);
			String output = behavior.get(input);
			for (int c = 0; c < input.length(); c++) {
				truthTable[l][c] = (input.charAt(c)+"");
			}
			int p = 0;
			for (int c =  input.length(); c < input.length()+output.length(); c++) {
				truthTable[l][c] = (output.charAt(p++)+"");
			}
			l++;
		}
		
		return truthTable;
	}
	
	public void printThruthTable(String[][] truthTable) {
		System.out.println(Arrays.deepToString(truthTable));
	}

	private void resetIsCalculated() {
		for (int i = 0; i < contents.size(); i++) {
			contents.get(i).isCalculated = false;
		}
		for (int i = 0; i < exits.size(); i++) {
			exits.get(i).isCalculated = false;
		}
	}
	
}
