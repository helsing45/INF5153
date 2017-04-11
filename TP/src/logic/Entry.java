package logic;

import java.util.HashMap;

import utils.Pair;

public class Entry extends AbstractDoor  {
	
	private static int EntryIndex = 1;

	public Entry() {
		super("Entry "+ EntryIndex++);
		 hashInit();
		 entries = null;
		 isCalculated = true;
	}
	
	private void hashInit(){
		behavior = new HashMap<>();
		behavior.put("1", "1");
		behavior.put("0", "0");
	}

	public void setExitValues(String value) {
		if (value.length() == 1) {
			this.exitValues = value;
		} else {
			System.out.println("Une entree ne peut avoir qu'une seule valeur!");
		}
	}

	@Override
	public void setEntry(int localIndex, int EntryIndex, AbstractDoor porte){
		Pair<AbstractDoor, Integer> pair = new Pair<AbstractDoor, Integer>(porte, EntryIndex);
		entries.add(localIndex, pair);
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		
	}	
	
}
