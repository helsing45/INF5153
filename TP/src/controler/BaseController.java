package controler;

import model.BaseModel;
import model.Link;
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

    public abstract ArrayList<Link> getLinks();

    public abstract void undo();
    public abstract void redo();
    public abstract void open();
    public abstract void reset();
    public abstract void onLocationChange(component component, Point point);
    public abstract void onNameChange(component component, String name);

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

    public abstract HashMap<component, Point> getComponentsPosition();

    public abstract void addLink(OperatorLabel ol1, OperatorLabel ol2);

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

    protected abstract boolean canDelete(component t);

    public abstract boolean removeComponent(component component);


}
