package view;

import model.BaseDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorLabel<T extends BaseDTO> extends JLabel {
    public static final String PORT_ENTRY = "Entree", PORT_EXITS = "Sortie";

    private int xPressed = 0;
    private int yPressed = 0;
    private Listener listener;
    private int selectedPortIndex = -1;
    private String selectedPortType;
    private OperatorLabel[] entries, exits;

    private BaseDTO operator;
    private int leftOffset, rightOffset, topOffset, bottomOffset;

    public OperatorLabel(T operator, Point position, Listener listener) {
        super(operator.getImage());
        this.operator = operator;
        if (operator.getName() != null && !operator.getValue().isEmpty())
            setText(operator.getName());
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);
        setLeftOffset((int) operator.getBound().getX());
        setTopOffset((int) operator.getBound().getY());
        setBorder(BorderFactory.createLineBorder(Color.black));
        entries = new OperatorLabel[operator.getEntryCount()];
        exits = new OperatorLabel[operator.getExitCount()];
        setOpaque(true);
        initialize(position);
        setListener(listener);
    }

    public String getId(){
        return getOperator() == null ? "" : getOperator().getId();
    }

    public void initialize(Point location) {
        setLocation(location);
        setSize(80, 70);
        addMouseListener(clickListener);
        addMouseMotionListener(motionListener);
    }

    public void deselect() {
        selectedPortType = null;
        selectedPortIndex = -1;
        setSelected(false);
    }

    private void setSelected(boolean isSelected) {
        setBorder(BorderFactory.createLineBorder(isSelected ? Color.BLUE : Color.black));
        setForeground(isSelected ? Color.BLUE : Color.black);
    }

    public void setLeftOffset(int leftOffset) {
        this.leftOffset = leftOffset;
    }

    public void setRightOffset(int rightOffset) {
        this.rightOffset = rightOffset;
    }

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public int getSelectedPortIndex() {
        return selectedPortIndex;
    }

    public String getSelectedPortType() {
        return selectedPortType;
    }

    public void link(String portType, int portIndex, OperatorLabel label) {
        if (portType.equals(PORT_ENTRY)) {
            entries[portIndex] = label;
        } else {
            exits[portIndex] = label;
        }
    }

    public void unlink(String portType, int portIndex){
        if (portType.equals(PORT_ENTRY)) {
            entries[portIndex] = null;
        } else {
            exits[portIndex] = null;
        }
    }

    public void transferData(OperatorLabel operatorLabel){
        operatorLabel.entries = entries;
        operatorLabel.exits = exits;
    }

    private boolean portAvailable(String portType, int portIndex) {
        return portType.equals(PORT_ENTRY) ? entries[portIndex] == null : exits[portIndex] == null;
    }

    private JMenuItem getPortMenuItem(String portType, int portNumber, int portIndex) {
        JMenuItem entry = new JMenuItem(String.format("Port #%1$d (%2$s %3$d)", portNumber, portType, portIndex));
        entry.setEnabled(portAvailable(portType, portIndex));
        entry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPortIndex = portIndex;
                selectedPortType = portType;
                setSelected(true);
                if (listener != null) listener.onLink(OperatorLabel.this);
            }
        });
        return entry;
    }

    private JMenuItem getLinkMenu() {
        JMenu linkMenu = new JMenu("Lier");
        int portNumber = 0;
        if (entries.length > 0 || exits.length > 0) {
            for (int i = 0; i < entries.length; i++) {
                linkMenu.add(getPortMenuItem(PORT_ENTRY, portNumber, i));
                portNumber++;
            }
            for (int i = 0; i < exits.length; i++) {
                linkMenu.add(getPortMenuItem(PORT_EXITS, portNumber, i));
                portNumber++;
            }
        }
        return linkMenu;
    }

    private JMenuItem getDeselectItem() {
        JMenuItem item = new JMenuItem("Cancel");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deselect();
                if (listener != null) listener.onLinkCanceled(OperatorLabel.this);
            }
        });
        return item;
    }

    private JMenuItem getNameMenuItem() {
        JMenuItem item = new JMenuItem("Nommer");
        item.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listener.onNameChange(OperatorLabel.this,JOptionPane.showInputDialog(OperatorLabel.this, "Input name"));

            }
        });
        return item;
    }

    private JPopupMenu getPopupMenu() {
        JPopupMenu menu = new JPopupMenu("Popup");
        if (operator.canBeName()) {
            menu.add(getNameMenuItem());
        }
        if (selectedPortType == null) {
            menu.add(getLinkMenu());
        } else {
            menu.add(getDeselectItem());
        }


        JMenuItem deleteMenu = new JMenuItem("Supprimer");
        deleteMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                listener.delete(OperatorLabel.this);
            }
        });
        menu.add(deleteMenu);

        return menu;
    }

    public BaseDTO getOperator() {
        return operator;
    }

    private MouseAdapter clickListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.isPopupTrigger()) {
                getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
            //catching the current values for x,y coordinates on screen
            xPressed = e.getX() + leftOffset;
            yPressed = e.getY() + topOffset;
        }

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
            }
        }
    };

    private MouseMotionAdapter motionListener = new MouseMotionAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            //and when the Jlabel is dragged
            setLocation(e.getXOnScreen() - xPressed, e.getYOnScreen() - yPressed);
            if (listener != null) listener.onLocationChange(OperatorLabel.this);
        }
    };

    public interface Listener {
        void onNameChange(OperatorLabel operatorLabel, String name);

        void onLink(OperatorLabel operatorLabel);

        void onLinkCanceled(OperatorLabel operatorLabel);

        void onLocationChange(OperatorLabel operatorLabel);

        void delete(OperatorLabel label);
    }
}
