package model;

import view.OperatorLabel;

import java.awt.*;

public class Link {
    OperatorLabel label1;
    String lbl1SelectedPortType, lbl2SelectedPortType;
    int lbl1SelectedPortIndex, lbl2SelectedPortIndex;
    OperatorLabel label2;

    private Link() {
    }

    public Link(OperatorLabel label1, OperatorLabel label2) {
        this.label1 = label1;
        this.lbl1SelectedPortType = label1.getSelectedPortType();
        this.lbl1SelectedPortIndex = label1.getSelectedPortIndex();
        this.label1.link(lbl1SelectedPortType, lbl1SelectedPortIndex, label2);
        this.label2 = label2;
        this.lbl2SelectedPortType = label2.getSelectedPortType();
        this.lbl2SelectedPortIndex = label2.getSelectedPortIndex();
        this.label2.link(lbl2SelectedPortType, lbl2SelectedPortIndex, label1);
    }

    public void replace(OperatorLabel newLabel) {
        if (newLabel.getOperator().getId().equals(label1.getOperator().getId())) {
            label1.transferData(newLabel);
            label1 = newLabel;
            return;
        }
        label2.transferData(newLabel);
        label2 = newLabel;
    }

    @Override
    public String toString() {
        return "{" + label1.getLocation() + "," + label2.getLocation() + "}";
    }

    public int howToDraw() {
        Point point1 = label1.getLocation();
        Point point2 = label2.getLocation();
        if (point1.x > point2.x) {
            return 1;
        } else if (point1.x < point2.x) {
            return 2;
        } else if (point1.y > point2.y) {
            return 3;
        } else if (point1.y < point2.y) {
            return 4;
        } else
            return 5;
    }

    public void unlink() {
        label1.unlink(lbl1SelectedPortType, lbl1SelectedPortIndex);
        label2.unlink(lbl2SelectedPortType, lbl2SelectedPortIndex);
    }

    public OperatorLabel getLabel1() {
        return label1;
    }

    public OperatorLabel getLabel2() {
        return label2;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Link) {
            Link temp = (Link) obj;
            if ((temp.toString()).equalsIgnoreCase(this.toString())) {
                return true;
            }
        }
        return false;
    }
}