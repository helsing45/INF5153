package view;

import java.awt.Component;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

public class OperatorListRenderer extends DefaultListCellRenderer {
	private Map<String, ImageIcon> imageMap;
	String[] nameList = {"AND", "NOT", "OR"};
	
	@Override
    public Component getListCellRendererComponent(JList list, Object value, int index,boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(imageMap.get((String) value));
        label.setHorizontalTextPosition(JLabel.RIGHT);
        return label;
    }
	
	protected Map<String, ImageIcon> getImageMap(){
		if(imageMap == null) createImageMap(nameList);
		return imageMap;
	}
	
	private Map<String, ImageIcon> createImageMap(String[] list) {
        Map<String, ImageIcon> map = new HashMap<>();
        for (String s : list) {
            map.put(s, new ImageIcon(
                    getClass().getResource( s + ".png")));
        }
        return map;
    }

}
