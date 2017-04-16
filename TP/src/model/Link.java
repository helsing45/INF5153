package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import view.OperatorLabel;

@XStreamAlias("link")
public class Link {

    String id1,id2;
    String lbl1SelectedPortType, lbl2SelectedPortType;
    int lbl1SelectedPortIndex, lbl2SelectedPortIndex;

    private Link() {
    }

    public Link(OperatorLabel label1, OperatorLabel label2) {
        //this.label1 = label1;
        this.id1 = label1.getId();
        this.lbl1SelectedPortType = label1.getSelectedPortType();
        this.lbl1SelectedPortIndex = label1.getSelectedPortIndex();
        //this.label1.link(lbl1SelectedPortType, lbl1SelectedPortIndex, label2);
        //this.label2 = label2;
        this.id2 = label2.getId();
        this.lbl2SelectedPortType = label2.getSelectedPortType();
        this.lbl2SelectedPortIndex = label2.getSelectedPortIndex();
        //this.label2.link(lbl2SelectedPortType, lbl2SelectedPortIndex, label1);
    }

    public String getId1() {
        return id1;
    }

    public String getId2() {
        return id2;
    }

    public String getLbl1SelectedPortType() {
        return lbl1SelectedPortType;
    }

    public String getLbl2SelectedPortType() {
        return lbl2SelectedPortType;
    }

    public int getLbl1SelectedPortIndex() {
        return lbl1SelectedPortIndex;
    }

    public int getLbl2SelectedPortIndex() {
        return lbl2SelectedPortIndex;
    }

    public void unlink() {
        /*label1.unlink(lbl1SelectedPortType, lbl1SelectedPortIndex);
        label2.unlink(lbl2SelectedPortType, lbl2SelectedPortIndex);*/
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