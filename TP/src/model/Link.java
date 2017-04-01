package model;

import view.OperatorLabel;

import java.awt.*;

public class Link {
    String componentId1, componentId2;
    Point point1, point2;
    int height1, height2;
    int width1, width2;

    private Link() {
    }

    public Link(OperatorLabel label1, OperatorLabel label2) {
        componentId1 = label1.getOperator().getId();
        point1 = label1.getLocation();
        height1 = label1.getHeight();
        width1 = label1.getWidth();
        point2 = label2.getLocation();
        componentId2 = label2.getOperator().getId();
        height2 = label2.getHeight();
        width2 = label2.getWidth();

    }

    public String getComponentId1() {
        return componentId1;
    }

    public String getComponentId2() {
        return componentId2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public int getHeight1() {
        return height1;
    }

    public int getHeight2() {
        return height2;
    }

    public int getWidth1() {
        return width1;
    }

    public int getWidth2() {
        return width2;
    }

    public Line getLine() {

        if (point1.x > point2.x) {
            return new Line(point1.x, point1.y + height1 / 2, point2.x + width2, point2.y + height1 / 2);
        } else if (point1.x < point2.x) {
            return new Line(point2.x, point2.y + height2 / 2, point1.x + width1, point1.y + height1 / 2);
        } else if (point1.y > point2.y) {
            return new Line(point1.x + width1 / 2, point1.y, point2.x + width2 / 2, point2.y + height2);
        } else if (point1.y < point2.y) {
            return new Line(point2.x + width2 / 2, point2.y, point1.x + width1 / 2, point1.y + height1);
        }
        return null;
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

    @Override
    public String toString() {
        return "{" + point1 + "," + point2 + "}";
    }

    public class Line {
        int x1, y1, x2, y2;

        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }

        public int getX1() {
            return x1;
        }

        public int getY1() {
            return y1;
        }

        public int getX2() {
            return x2;
        }

        public int getY2() {
            return y2;
        }
    }
}