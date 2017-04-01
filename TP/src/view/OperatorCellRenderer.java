package view;

import model.BaseDTO;

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
		BaseDTO entry = (BaseDTO) value;
		setText(entry.getValue());
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