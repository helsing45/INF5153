package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.OperatorDTO;
import utils.Pair;

public abstract class AbstractDoor {
	public String name;
	protected int nbEntries;
	protected int nbExits;
	protected boolean isCalculated;
	protected List<Pair<AbstractDoor, Integer>> entries;
	protected HashMap<String, String> behavior = null;
	protected String entryValues = "";
	protected String exitValues = "";
	
	public AbstractDoor(String name, HashMap<String, String> behavior, int nbEntries, int nbExits) {
		super();
		this.nbEntries = nbEntries;
		this.nbExits = nbExits;
		this.name = name;
		this.behavior = behavior;
		entries = new ArrayList<>(nbEntries);
		for (int i = 0; i <nbEntries; ++i){
			entries.add(i,  null);
		}
	}
	
	public AbstractDoor(String name, int nbEntries, int nbExits) {
		super();
		this.name = name;
		this.nbEntries = nbEntries;
		this.nbExits = nbExits;
		entries = new ArrayList<>(nbEntries);
		
		for (int i = 0; i < nbEntries; ++i){
			entries.add(i,  null);
		}
	}
	
	public AbstractDoor(String name) {
		super();
		this.name = name;
		entries = new ArrayList<>();
	}

	public int getNbEntries(){
		return entries.size();
	}
	
	public int getNbExits(){
		return nbExits;
	}
	
	public void setEntry(int localIndex, int EntryIndex, AbstractDoor porte){
		Pair<AbstractDoor, Integer> pair = new Pair<AbstractDoor, Integer>(porte, EntryIndex);
		entries.set(localIndex, pair);
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
	
	public static AbstractDoor getDoor(OperatorDTO dto){
		if(dto.getValue().equals("And")){
			return new And();
		}else if(dto.getValue().equals("Or")){
			return new Or();
		}else if(dto.getValue().equals("Not")){
			return new Not();
		}else if(dto.getValue().equals("entry")){
			return new Entry();
		}else if(dto.getValue().equals("end")){
			return new Exit();
		}
		return null;
		//TODO si c'est aucun des cas au dessus c'est une porte custom.
	}
}
