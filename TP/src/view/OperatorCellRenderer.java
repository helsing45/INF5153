package view;

import logique.IO;

import javax.swing.*;
import java.awt.*;

class OperatorCellRenderer extends JLabel implements ListCellRenderer {
    private static final Color HIGHLIGHT_COLOR = new Color(0, 0, 128);

	public OperatorCellRenderer() {
		setOpaque(true);
		setIconTextGap(12);
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,boolean cellHasFocus) {
		IO entry = (IO) value;
		setText(entry.getName());
		setIcon(entry.getImage());
		if (isSelected) {
			setBackground(HIGHLIGHT_COLOR);
			setForeground(Color.white);
		} else {
			setBackground(Color.white);
			setForeground(Color.black);
		}
		return this;
	}
}