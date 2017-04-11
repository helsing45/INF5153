package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import utils.Pair;

public abstract class AbstractDoor {
	public String name;
	protected boolean isCalculated;
	protected List<Pair<AbstractDoor, Integer>> entries;
	protected List<Pair<AbstractDoor, Integer>> exits;
	protected HashMap<String, String> behavior = null;
	protected String entryValues = "";
	protected String exitValues = "";
	
	public AbstractDoor(String name, HashMap<String, String> behavior, int nbEntries, int nbExits) {
		super();
		this.name = name;
		this.behavior = behavior;
		entries = new ArrayList<>(nbEntries);
		exits = new ArrayList<>(nbExits);
		for (int i = 0; i <nbEntries; ++i){
			entries.add(i,  null);
		}
		
		for (int i = 0; i < nbExits; ++i){
			exits.add(i,  null);
		}
	}
	
	public AbstractDoor(String name, int nbEntries, int nbExits) {
		super();
		this.name = name;
		entries = new ArrayList<>(nbEntries);
		exits = new ArrayList<>(nbExits);
		
		for (int i = 0; i < nbEntries; ++i){
			entries.add(i,  null);
		}
		
		for (int i = 0; i < nbExits; ++i){
			exits.add(i,  null);
		}
	}
	
	public AbstractDoor(String name) {
		super();
		this.name = name;
		entries = new ArrayList<>();
		exits = new ArrayList<>();
	}

	public int getNbEntries(){
		return entries.size();
	}
	
	public int getNbExits(){
		return exits.size();
	}
	
	public void setEntry(int localIndex, int EntryIndex, AbstractDoor porte){
		Pair<AbstractDoor, Integer> pair = new Pair<AbstractDoor, Integer>(porte, EntryIndex);
		entries.set(localIndex, pair);
	}
	
	//Pas utile?
	public void setExit(int i, AbstractDoor porte){
		Pair<AbstractDoor, Integer> pair = new Pair<AbstractDoor, Integer>(porte, i);
		exits.set(i, pair);
	}
	
	public abstract void calculate();
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setExitValues(String input) {
		exitValues = behavior.get(input);
	}

	public char getExitValue(int i){
		if (exitValues != null){
			return exitValues.charAt(i);
		} else {
			System.out.println("La porte n'a pas encore ete calculee!");
			System.out.println(this.getClass());
			return 'x';
		}
	}
	
	//All entries of a door must have been calculated before a door can be calculated
	public boolean isCalculable(){
		if (entries != null){
			for (int i = 0; i < entries.size(); i++){
				if (!entries.get(i).getDoor().isCalculated){
					return false;
				}
			}
		}
		return true;
	}
}
