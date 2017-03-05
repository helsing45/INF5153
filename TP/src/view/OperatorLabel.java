package view;

import logique.Entree;
import logique.Operator;
import logique.Sortie;
import utils.ErrorUtils;
import utils.NameUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.UUID;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorLabel extends JLabel {
    public static final String PORT_ENTRY = "E", PORT_EXITS = "S";

    private String id;
    private int xPressed = 0;
    private int yPressed = 0;
    private JPopupMenu menu;
    private Listener listener;
    private String selectedPort;
    private Operator operator;
    private OperatorLabel[] entries, exits;

    private int leftOffset, rightOffset, topOffset, bottomOffset;

    @Override
    public void setText(String text) {
        if (operator instanceof Sortie || operator instanceof Entree) {
            if (NameUtils.isNameAvailable(text)) {
                if (operator instanceof Entree) {
                    ((Entree) operator).setVariable(text);
                }
                NameUtils.reserveName(text);
                super.setText(text);
            } else {
                showError("Nom déjà prit.");
            }
        }

    }

    public OperatorLabel(Operator operator) {
        super(new ImageIcon(ApplicationFrame.class.getResource("/images/" + operator.getName() + ".png")));
        id = UUID.randomUUID().toString();
        setHorizontalTextPosition(CENTER);
        setVerticalTextPosition(BOTTOM);
        this.operator = operator;
        setBorder(BorderFactory.createLineBorder(Color.black));
        entries = new OperatorLabel[operator.getEntryCount()];
        exits = new OperatorLabel[operator.getExitCount()];
    }

    public Operator getOperator() {
        return operator;
    }

    private boolean canBeName() {
        return operator != null && (operator instanceof Entree || operator instanceof Sortie);
    }

    public void initialize(Point location) {
        setLocation(location);
        setSize(80, 70);
        addMouseListener(clickListener);
        addMouseMotionListener(motionListener);
    }

    public void linkTo(OperatorLabel operator) {
        if (selectedPort.startsWith(PORT_ENTRY)) {
            getOperator().addEntry(Integer.parseInt(selectedPort.substring(1)), operator.getOperator());
            entries[Integer.parseInt(selectedPort.substring(1))] = operator;
        }
        if (selectedPort.startsWith(PORT_EXITS)) {
            getOperator().addExit(Integer.parseInt(selectedPort.substring(1)), operator.getOperator());
            exits[Integer.parseInt(selectedPort.substring(1))] = operator;
        }
        deselect();
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
        if (canBeName()) {
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
