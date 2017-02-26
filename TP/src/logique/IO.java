package logique;

import javax.swing.*;

public abstract class IO {
	Boolean valeur;
	private ImageIcon image;
	
	public abstract void calculer();
	
	public IO() {
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

	public boolean getValeur() {
		return valeur;
	}

	@Override
	public String toString() {
		return getName();
	}
}
