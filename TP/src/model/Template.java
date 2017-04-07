package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import view.OperatorLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by j-c9 on 2017-03-04.
 */
@XStreamAlias("template")
public class Template extends BaseModel<OperatorDTO> {

    private HashMap<OperatorDTO, Point> operators;
    private ArrayList<Link> links;

    public Template() {
        operators = new HashMap<>();
        links = new ArrayList<>();
    }

    public HashMap<OperatorDTO, Point> getOperators() {
        return operators;
    }

    public void remove(OperatorLabel operatorLabel) {
        operators.remove(operatorLabel);
    }

    public Point getLocationOf(BaseDTO operatorLabel) {
        return operators.get(operatorLabel);
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    @Override
    public void update(BaseModel newModel) {
        if (newModel instanceof Template) {
            this.operators = ((Template) newModel).operators;
            this.links = ((Template) newModel).links;
            notifyObserver();
        }
    }

    @Override
    public void reset() {
        //update(getDefaultTemplate());
    }

    @Override
    public void addComponent(OperatorDTO label, Point position) {
        operators.put(label, position);
        notifyObserver();
    }

    @Override
    public void setComponentPosition(OperatorDTO dto, Point position) {
        operators.put(dto, position);
    }

    @Override
    public void removeComponent(OperatorDTO component) {
        operators.remove(component);
        ArrayList<Link> linkToRemove = new ArrayList<>();
        for (Link link : getLinks()) {
            if (link.getLabel1().getOperator().equals(component) || link.getLabel2().getOperator().equals(component)) {
                link.unlink();
                linkToRemove.add(link);
            }
        }
        getLinks().removeAll(linkToRemove);
        notifyObserver();
    }

    public void addLink(OperatorLabel first, OperatorLabel second) {
        links.add(new Link(first, second));
    }
}
