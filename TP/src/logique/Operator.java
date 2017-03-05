package logique;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public abstract class Operator implements Transferable {
	Boolean valeur;
	private ImageIcon image;
	DataFlavor dataFlavor = new DataFlavor(Operator.class,
			Operator.class.getSimpleName());
	
	public abstract void calculer();
	
	public Operator() {
		super();
		this.valeur = null;
	}

	public ImageIcon getImage() {
		if (image == null) {
			image = new ImageIcon(getClass().getResource("/images/"+getName()+".png"));
		}
		return image;
	}

	public abstract String getName();

	public abstract int getExitCount();
	public abstract int getEntryCount();
	public abstract Operator getEntry(int index);
	public abstract Operator getExit(int index);

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
