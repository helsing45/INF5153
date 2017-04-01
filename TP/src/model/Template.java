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
public class Template extends BaseModel<BaseDTO> {

    private HashMap<BaseDTO, Point> operators;
    private ArrayList<Link> links;

    public static Template getDefaultTemplate() {
        Template template = new Template();
        template.addComponent(BaseDTO.getEntryDTO(), 50, 50);
        template.addComponent(BaseDTO.getEntryDTO(), 50, 250);
        template.addComponent(BaseDTO.getExitDTO(), 450, 150);
        return template;
    }

    public Template() {
        operators = new HashMap<>();
        links = new ArrayList<>();
    }

    public HashMap<BaseDTO, Point> getOperators() {
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
        update(getDefaultTemplate());
    }

    @Override
    public void addComponent(BaseDTO label, Point position) {
        operators.put(label, position);
        notifyObserver();
    }

    public void addLink(OperatorLabel first, OperatorLabel second) {
        links.add(new Link(first, second));
    }
}
