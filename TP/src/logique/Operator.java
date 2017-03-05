package logique;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Operator implements Transferable {

	private List<Operator> entries, exits;
	Boolean valeur;
	private ImageIcon image;
	DataFlavor dataFlavor = new DataFlavor(Operator.class,
			Operator.class.getSimpleName());
	
	public abstract void calculer();
	
	public Operator() {
		super();
		this.valeur = null;
		entries = new ArrayList<>();
		exits = new ArrayList<>();
	}

	public ImageIcon getImage() {
		if (image == null) {
			image = new ImageIcon(getClass().getResource("/images/"+getName()+".png"));
		}
		return image;
	}

	public abstract String getName();

	public void addEntry(int index, Operator operator) {
		entries.add(index, operator);
	}

	public void addEntry(Operator operator) {
		entries.add(operator);
	}

	public void addExit(int index, Operator operator) {
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

	@Override
	public String toString() {
		return getName();
	}

	public DataFlavor[] getTransferDataFlavors(){
		return new DataFlavor[] { dataFlavor };
	}

	/**
	 * Returns whether or not the specified data flavor is supported for
	 * this object.
	 * @param flavor the requested flavor for the data
	 * @return boolean indicating whether or not the data flavor is supported
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		//return flavor.equals(dataFlavor);
		return true;
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {

		if (flavor.equals(dataFlavor))
			return this;
		else
			throw new UnsupportedFlavorException(flavor);
	}



}
