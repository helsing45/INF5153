package logic;

import java.util.HashMap;

public class Door extends AbstractDoor {
	
	public Door(String name, int nbEntries, int nbExits) {
		super(name, nbEntries, nbExits);
	}
	
	public Door(String name, HashMap<String, String> behavior, int nbEntries, int nbExits) {
		super(name, behavior, nbEntries, nbExits);
	}
	
	public void calculate(){
		if (behavior != null){
			entryValues = "";
			for (int i = 0; i < entries.size(); i++){
				entryValues += entries.get(i).getDoor().getExitValue(entries.get(i).getIndexExit());
			}
			setExitValues(entryValues);
			isCalculated = true;
		} else {
			System.out.println("La porte ne possede pas de behavior!");
		}
	}
}
