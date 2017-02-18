package view;

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

    private int leftOffset,rightOffset,topOffset,bottomOffset;

    private OperatorLabel() {} //Pour empecher d implementer autrement qu avec le Builder()

    private OperatorLabel(int leftOffset, int topOffset, Point location, String img) {
        super(new ImageIcon(OperatorLabel.class.getResource("/images/" + img + ".png")));
        this.leftOffset = leftOffset;
        this.topOffset = topOffset;
        addMouseListener(clickListener);
        addMouseMotionListener(motionListener);
    }

    private JPopupMenu getPopupMenu(){
        if(menu == null){
             menu = new JPopupMenu("Popup");

            JMenuItem item = new JMenuItem("Lier");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Menu item Test1");
                }
            });
            menu.add(item);

            item = new JMenuItem("Supprimer");
            item.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    Container parent = getParent();
                    parent.remove(OperatorLabel.this);
                    parent.validate();
                    parent.repaint();
                }
            });
            menu.add(item);
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
        }
    };



    static class Builder{
        private int leftOffset,rightOffset,topOffset,bottomOffset;
        private Point location;
        private String img;

        public Builder setLeftOffset(int leftOffset) {
            this.leftOffset = leftOffset;
            return this;
        }

        public Builder setRightOffset(int rightOffset) {
            this.rightOffset = rightOffset;
            return this;
        }

        public Builder setTopOffset(int topOffset) {
            this.topOffset = topOffset;
            return this;
        }

        public Builder setBottomOffset(int bottomOffset) {
            this.bottomOffset = bottomOffset;
            return this;
        }

        public Builder setLocation(Point location) {
            this.location = location;
            return this;
        }

        public Builder setImg(String img) {
            this.img = img;
            return this;
        }

        public OperatorLabel build(){
            return new OperatorLabel(leftOffset,topOffset,location,img);
        }
    }
}
