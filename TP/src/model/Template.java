package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import view.OperatorLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@XStreamAlias("Template")
public class Template extends BaseModel<OperatorDTO> {
    private HashMap<OperatorDTO, Point> operators;
    private List<Link> links;

    public Template() {
        operators = new HashMap<>();
        links = new ArrayList<>();
    }

    public Template(SaveableTemplate template){
        this.operators = template.operators;
        this.links = template.links;
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
        return (ArrayList<Link>) links;
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
            if (link.getId1().equals(component.getId()) || link.getId2().equals(component.getId())) {
                //link.unlink();
                linkToRemove.add(link);
            }
        }
        getLinks().removeAll(linkToRemove);
        notifyObserver();
    }

    public void addLink(OperatorLabel first, OperatorLabel second) {
        links.add(new Link(first, second));
    }

    /**
     * La classe saveableTemplate, ne sert qu a faire la sauvegarde puis le loading, on ne peut pas utiliser la classe template car on ne veux pas sauvegarder les observables
     */
    public static class SaveableTemplate {
        @XStreamImplicit
        private HashMap<OperatorDTO, Point> operators;
        @XStreamImplicit
        private List<Link> links;

        public SaveableTemplate(Template template) {
            this.operators = template.operators;
            this.links = template.links;
        }
    }
}
