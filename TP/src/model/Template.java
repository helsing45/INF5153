package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import logique.Entree;
import logique.Operator;
import logique.Sortie;
import view.OperatorLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by j-c9 on 2017-03-04.
 */
@XStreamAlias("template")
public class Template {

    private HashMap<OperatorLabel, Point> operators;
    private ArrayList<Link> links;

    public static Template getDefaultTemplate() {
        Template template = new Template();
        template.addOperator(new Entree(), 50, 50);
        template.addOperator(new Entree(), 50, 250);
        template.addOperator(new Sortie(), 450, 150);
        return template;
    }

    public Template() {
        operators = new HashMap<>();
        links = new ArrayList<>();
    }

    public HashMap<OperatorLabel, Point> getOperators() {
        return operators;
    }

    public void remove(OperatorLabel operatorLabel){
        operators.remove(operatorLabel);
    }

    public void addOperator(OperatorLabel operatorLabel, Point location) {
        operators.put(operatorLabel, location);
    }

    public void addOperator(Operator operator, Point location) {
        addOperator(new OperatorLabel(operator), location);
    }

    public void addOperator(Operator operator, int X, int Y) {
        addOperator(new OperatorLabel(operator), new Point(X, Y));
    }

    public ArrayList<OperatorLabel> getEntries() {
        ArrayList<OperatorLabel> entries = new ArrayList<>();
        for (OperatorLabel operatorLabel : operators.keySet()) {
            if (operatorLabel.getOperator() instanceof Entree) {
                entries.add(operatorLabel);
            }
        }
        return entries;
    }

    public ArrayList<OperatorLabel> getExits() {
        ArrayList<OperatorLabel> exits = new ArrayList<>();
        for (OperatorLabel operatorLabel : operators.keySet()) {
            if (operatorLabel.getOperator() instanceof Sortie) {
                exits.add(operatorLabel);
            }
        }
        return exits;
    }

    public Point getLocationOf(OperatorLabel operatorLabel) {
        return operators.get(operatorLabel);
    }

    public ArrayList<Link> getLinks() {
        return links;
    }

    public void addLink(OperatorLabel first, OperatorLabel second) {
        links.add(new Link(first, second));
    }
}
