package model;

import view.OperatorLabel;

import java.awt.*;

public class Link {
        OperatorLabel label1;
        OperatorLabel label2;

        private Link() {
        }

        public Link(OperatorLabel label1, OperatorLabel label2) {
            this.label1 = label1;
            this.label1.linkTo(label2);
            this.label2 = label2;
            this.label2.linkTo(label1);
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