package controler;

import model.BaseModel;
import view.OperatorLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class BaseController<component extends model.BaseDTO, genericModel extends BaseModel<component>> {
    protected int inputCount;
    private genericModel model;

    protected genericModel getModel() {
        return model;
    }

    public BaseController(genericModel model) {
        this.model = model;
    }

    protected int getInputMax() {
        return 50;
    }

    public abstract ArrayList<Line> getLines();

    public abstract void undo();
    public abstract void redo();
    public abstract void open();
    public abstract void reset();

    /**
     * Save the current template as a XML
     */
    public abstract void saveTemplate();

    /**
     * Save the current template as a custom door
     */
    public abstract void saveCustomDoor();

    /**
     * @return the list of all the component that can be use.
     */
    public abstract component[] getAllComponent();

    public abstract HashMap<component,Point> getComponentsPosition();

    /**
     * Method to add a component to the model.
     *
     * @param component the component to add to the model.
     * @param position  the position that the component will be added.
     * @param bounds    the bounds of the view, useful if your view as offset
     * @return return true if the component is add
     */
    public abstract boolean addComponent(component component, Point position);

    /**
     * Check if the component can be add to the model
     *
     * @param t the component to add
     * @return return true if the component can be add
     */
    protected abstract boolean canAdd(component t);

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
