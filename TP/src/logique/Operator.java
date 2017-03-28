package logique;

import java.util.ArrayList;
import java.util.List;

public abstract class Operator {

	private List<Operator> entries, exits;
	Boolean valeur;
	
	public abstract void calculer();
	
	public Operator() {
		super();
		this.valeur = null;
		entries = new ArrayList<>();
		exits = new ArrayList<>();
	}

	public void addEntry(int index, Operator operator) {
		if(index <= getEntryCount()){
			entries.set(index,operator);
			return;
		}
		entries.add(index, operator);
	}

	public void addEntry(Operator operator) {
		entries.add(operator);
	}

	public void addExit(int index, Operator operator) {
		if(index <= getExitCount()){
			exits.set(index,operator);
			return;
		}
		exits.add(index, operator);
	}

	public void addExit(Operator operator) {
		exits.add(operator);
	}

	public int getExitCount() {
		return exits.size();
	}

	public int getEntryCount() {
		return entries.size();
	}

	public Operator getEntry(int index) {
		if (index > getEntryCount()) return null;
		return entries.get(index);
	}

	public Operator getExit(int index) {
		if (index > getExitCount()) return null;
		return exits.get(index);
	}

	public boolean getValeur() {
		return valeur;
	}

	public abstract String getBooleanExpression();





}
