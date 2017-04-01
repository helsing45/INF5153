package view;

import model.BaseDTO;
import utils.ErrorUtils;
import utils.NameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorLabel<T extends BaseDTO> extends JLabel {
    public static final String PORT_ENTRY = "E", PORT_EXITS = "S";

    private String id;
    private int xPressed = 0;
    private int yPressed = 0;
    private JPopupMenu menu;
    private Listener listener;
    private String selectedPort;
    private OperatorLabel[] entries, exits;

    private BaseDTO operator;
    private int leftOffset, rightOffset, topOffset, bottomOffset;

    @Override
    public void setText(String text) {
        if (operator != null && operator.canBeName()) {
            if (NameUtils.isNameAvailable(text)) {
                NameUtils.reserveName(text);
                super.setText(text);
            } else {
                showError("Nom déjà prit.");
            }
        }
    }

    public OperatorLabel(T operator) {
        super(operator.getImage());
        this.operator = operator;
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);
        setLeftOffset((int) operator.getBound().getX());
        setTopOffset((int) operator.getBound().getY());
        setBorder(BorderFactory.createLineBorder(Color.black));
        entries = new OperatorLabel[operator.getEntryCount()];
        exits = new OperatorLabel[operator.getExitCount()];
    }

    public OperatorLabel(T operator, Point position, Listener listener) {
        this(operator);
        initialize(position);
        setListener(listener);
    }

    public void initialize(Point location) {
        setLocation(location);
        setSize(80, 70);
        addMouseListener(clickListener);
        addMouseMotionListener(motionListener);
    }

    public void deselect() {
        selectedPort = null;
        setSelected(false);
    }

    private void setSelected(boolean isSelected) {
        setBorder(BorderFactory.createLineBorder(isSelected ? Color.BLUE : Color.black));
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

    private JMenuItem getPortMenuItem(String port) {
        JMenuItem entry = new JMenuItem(port);
        entry.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectedPort = port;
                setSelected(true);
                if (listener != null) listener.onLink(OperatorLabel.this);
            }
        });
        return entry;
    }

    private JMenuItem getLinkMenu() {
        JMenu linkMenu = new JMenu("Lier");
        if (entries.length > 0 || exits.length > 0) {
            for (int i = 0; i < entries.length; i++) {
                linkMenu.add(getPortMenuItem(PORT_ENTRY + i));
            }
            for (int i = 0; i < exits.length; i++) {
                linkMenu.add(getPortMenuItem(PORT_EXITS + i));
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
                String name = JOptionPane.showInputDialog(OperatorLabel.this, "Input name");
                if (name.length() > 5) {
                    showError("Le nom de l'input doit contenir au plus 5 lettres.");
                } else {
                    setText(name);
                }
            }
        });
        return item;
    }

    private JPopupMenu getPopupMenu() {
        JPopupMenu menu = new JPopupMenu("Popup");
        if (operator.canBeName()) {
            menu.add(getNameMenuItem());
        }
        if (selectedPort == null) {
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

    private void showError(String error) {
        ErrorUtils.showError(OperatorLabel.this, error);
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
        void onLink(OperatorLabel operatorLabel);

        void onLinkCanceled(OperatorLabel operatorLabel);

        void onLocationChange(OperatorLabel operatorLabel);

        void delete(OperatorLabel label);
    }
}
