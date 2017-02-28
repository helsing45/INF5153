package view;

import logique.Operator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by j-c9 on 2017-02-18.
 */
public class OperatorLabel extends JLabel {
    private int xPressed = 0;
    private int yPressed = 0;
    private JPopupMenu menu;
    private Listener listener;
    private boolean isSelected;
    private Operator operator;
    private OperatorLabel[] entries, exits;

    private int leftOffset, rightOffset, topOffset, bottomOffset;

    public OperatorLabel(Operator operator) {
        super(new ImageIcon(ApplicationFrame.class.getResource("/images/" + operator.getName() + ".png")));
        this.operator = operator;
        setBorder(BorderFactory.createLineBorder(Color.black));
        entries = new OperatorLabel[operator.getEntryCount()];
        exits = new OperatorLabel[operator.getExitCount()];
    }

    public Operator getOperator() {
        return operator;
    }

    public void initialize(Point location) {
        setLocation(location);
        setSize(65, 65);
        addMouseListener(clickListener);
        addMouseMotionListener(motionListener);
    }

    public void setSelected(boolean isSelected) {
        //this.isSelected = isSelected;
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

    private JPopupMenu getPopupMenu() {
        if (menu == null) {
            menu = new JPopupMenu("Popup");
            JMenu lierMenu = new JMenu("Lier");
            if (entries.length > 0 || exits.length > 0) {
                for (int i = 0; i < entries.length; i++) {
                    String port = "E" + i;
                    JMenuItem entry = new JMenuItem(port);
                    entry.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (listener != null) listener.onLinkMenuClicked(OperatorLabel.this, port);
                        }
                    });
                    lierMenu.add(entry);
                }
                for (int i = 0; i < exits.length; i++) {
                    String port = "S" + i;
                    JMenuItem exit = new JMenuItem(port);
                    exit.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            if (listener != null) listener.onLinkMenuClicked(OperatorLabel.this, port);
                        }
                    });
                    lierMenu.add(exit);
                }
            }

            menu.add(lierMenu);

            JMenuItem deleteMenu = new JMenuItem("Supprimer");
            deleteMenu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Container parent = getParent();
                    parent.remove(OperatorLabel.this);
                    parent.validate();
                    parent.repaint();
                }
            });
            menu.add(deleteMenu);
        }
        return menu;
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
        void onLinkMenuClicked(OperatorLabel operatorLabel, String port);

        void onLocationChange(OperatorLabel operatorLabel);
    }
}
