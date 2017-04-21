package model;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import logic.AbstractDoor;
import logic.Circuit;
import utils.NameUtils;
import view.OperatorLabel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@XStreamAlias("Template")
public class Template extends BaseModel<OperatorDTO> {
	private HashMap<OperatorDTO, Point> operators;
	private List<Link> links;

	public Template() {
		operators = new HashMap<>();
		links = new ArrayList<>();
	}

	public Template(SaveableTemplate template) {
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
		NameUtils.reset();
		operators = new HashMap<>();
		links = new ArrayList<>();
		notifyReset();
	}

	public void notifyTruthTableEntries() {
		ArrayList<OperatorDTO> entries = new ArrayList<>();
		ArrayList<OperatorDTO> exits = new ArrayList<>();
		for (OperatorDTO operatorDTO : operators.keySet()) {
			if (operatorDTO.getValue().equals(OperatorDTO.ENTRY)) {
				entries.add(operatorDTO);
			}
			if (operatorDTO.getValue().equals(OperatorDTO.EXIT)) {
				exits.add(operatorDTO);
			}
		}
		int entryCount = entries.size();
		entries.addAll(exits);
		String[] tableEntries = new String[entries.size()];
		for (int i = 0; i < entries.size(); i++) {
			tableEntries[i] = entries.get(i).getName();
		}
		notifyObserverEntriesHasChange(entryCount, tableEntries);
	}

	@Override
	public void addComponent(OperatorDTO label, Point position) {
		operators.put(label, position);
		notifyObserver();
		if (label.getValue().equals(OperatorDTO.ENTRY) || label.getValue().equals(OperatorDTO.EXIT)) {
			notifyTruthTableEntries();
		}
	}

	@Override
	public void setComponentPosition(OperatorDTO dto, Point position) {
		operators.put(dto, position);
	}

	@Override
	public void updateComponentName(OperatorDTO dto, String name) {
		for (OperatorDTO operatorDTO : operators.keySet()) {
			if (operatorDTO.getId().equals(dto.getId())) {
				operatorDTO.setName(name);
				break;
			}
		}
		notifyObserver();
		notifyTruthTableEntries();
	}

	@Override
	public void calculate() {
		Circuit circuit = generateCircuit();
		circuit.setBehavior();
		notifyTableCalculate(circuit.getTruthTable());
	}

	@Override
	public void removeComponent(OperatorDTO component) {
		operators.remove(component);
		ArrayList<Link> linkToRemove = new ArrayList<>();
		for (Link link : getLinks()) {
			if (link.getId1().equals(component.getId()) || link.getId2().equals(component.getId())) {
				linkToRemove.add(link);
			}
		}
		getLinks().removeAll(linkToRemove);
		notifyObserver();

		if (component.getValue().equals(OperatorDTO.ENTRY) || component.getValue().equals(OperatorDTO.EXIT)) {
			notifyTruthTableEntries();
		}
	}

	public void addLink(OperatorLabel first, OperatorLabel second) {
		links.add(new Link(first, second));
	}

	/**
	 * La classe saveableTemplate, ne sert qu a faire la sauvegarde puis le
	 * loading, on ne peut pas utiliser la classe template car on ne veux pas
	 * sauvegarder les observables
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

	public Circuit generateCircuit() {
		Circuit circuit = new Circuit("MOCK");
		HashMap<String, AbstractDoor> map = new HashMap<>();
		for (OperatorDTO dto : operators.keySet()) {
			map.put(dto.getId(), AbstractDoor.getDoor(dto));
		}

		for (Link link : links) {
			if(link.getLbl1SelectedPortType().equals(OperatorLabel.PORT_ENTRY)) {
				map.get(link.getId1()).setEntry(link.getLbl1SelectedPortIndex(),
						link.getLbl2SelectedPortIndex(),
						map.get(link.getId2()));
			}else {
				map.get(link.getId2()).setEntry(link.getLbl2SelectedPortIndex(),
						link.getLbl1SelectedPortIndex(),
						map.get(link.getId1()));
			}
		}
		for (String key : map.keySet()) {
			circuit.addDoor(map.get(key));
		}

		return circuit;
	}
}
