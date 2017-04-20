package controler;

import model.Link;
import model.OperatorDTO;
import model.Template;
import model.Template.SaveableTemplate;
import utils.ConfirmationUtils;
import utils.ErrorUtils;
import utils.NameUtils;
import utils.XmlUtils;
import view.OperatorLabel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static model.BaseDTO.ENTRY;
import static model.BaseDTO.EXIT;

public class TemplateController extends BaseController<OperatorDTO, Template> {
    protected int entriesCount, exitsCount;

    public TemplateController(Template model) {
        super(model);
    }

    @Override
    public ArrayList<Link> getLinks() {
        return getModel().getLinks();
    }

    @Override
    public void undo() {
        //TODO do something useful
    }

    @Override
    public void redo() {
        //TODO do something useful
    }

    @Override
    public void open() {
        askForConfirmationThenExecute(new Runnable() {
            @Override
            public void run() {
                // TODO only ask the confirmation if change as been made
                // TODO put this in a command class
                JFileChooser chooser = new JFileChooser();
                chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    getModel().update(new Template((SaveableTemplate) XmlUtils.getXmlUtils().fromXML(chooser.getSelectedFile())));
                }
            }
        });
    }

    @Override
    public void reset() {
        askForConfirmationThenExecute(new Runnable() {
            @Override
            public void run() {
                // TODO only ask the confirmation if change as been made
                // TODO put this in a command class
                getModel().reset();
            }
        });
    }

    @Override
    public void onLocationChange(OperatorDTO operatorDTO, Point point) {
        isDirty = true;
        getModel().setComponentPosition(operatorDTO, point);
    }

    @Override
    public void onNameChange(OperatorDTO operatorDTO, String name) {
        isDirty = true;
        if (name.length() > 5) {
            showError("Le nom de l'input doit contenir au plus 5 lettres.");
        } else {
            if (NameUtils.isNameAvailable(name)) {
                getModel().updateComponentName(operatorDTO, name);
            } else {
                showError("Le nom: " + name + " est deja utiliser.");
            }
        }
    }

    @Override
    public void closes() {
        askForConfirmationThenExecute(new Runnable() {
            @Override
            public void run() {
                System.exit(0);
            }
        });
    }

    @Override
    public void saveTemplate() {
        //TODO do a command instead
        isDirty = false;
        JFileChooser chooser = new JFileChooser();
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            save(chooser.getSelectedFile());
        }
    }

    public void save(File filePath) {
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            fw = new FileWriter(filePath.getCanonicalPath() + ".xml");
            bw = new BufferedWriter(fw);
            bw.write(XmlUtils.getXmlUtils().toXML(new SaveableTemplate(getModel())));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void saveCustomDoor() {
        //TODO do something useful
    }

    @Override
    public OperatorDTO[] getAllComponent() {
        //TODO ajouter les custom doors
    	//TODO centraliser les valeurs des portes
        return new OperatorDTO[]{
                OperatorDTO.getEntryDTO(),
                OperatorDTO.getExitDTO(),
                new OperatorDTO("And").setFileName("and.png").setEntryCount(2).setExitsCount(1),
                new OperatorDTO("Or").setFileName("or.png").setEntryCount(2).setExitsCount(1),
                new OperatorDTO("Not").setFileName("not.png").setEntryCount(1).setExitsCount(1)
        };
    }

    @Override
    public HashMap<OperatorDTO, Point> getComponentsPosition() {
        return getModel().getOperators();
    }

    private String getName(OperatorDTO dto) {
        int count = (dto.getValue().equals(EXIT) ? exitsCount : entriesCount) + 1;
        for (int i = 0; i < count; i++) {
            String name = (dto.getValue().equals(EXIT) ? "S" : "E") + i;
            if (NameUtils.isNameAvailable(name)) {
                return name;
            }
        }
        return "ERR";
    }

    @Override
    public void addComponent(OperatorDTO baseDTO, Point position, boolean manual) {
        if (canAdd(baseDTO)) {
            if (manual)
                isDirty = true;
            if (baseDTO.getValue().equals(EXIT) || baseDTO.getValue().equals(ENTRY))
                baseDTO.setName(getName(baseDTO));
            getModel().addComponent(baseDTO, position);
            inputCount++;
            entriesCount += baseDTO.getValue().equals(ENTRY) ? 1 : 0;
            exitsCount += baseDTO.getValue().equals(EXIT) ? 1 : 0;
            return;
        }
        showError("Impossible d'ajouter");
    }

    public void addLink(OperatorLabel ol1, OperatorLabel ol2) {
        isDirty = true;
        getModel().addLink(ol1, ol2);
    }

    protected boolean canAdd(OperatorDTO baseDTO) {
        return inputCount < getInputMax() && (!(baseDTO.getValue().equals(EXIT) || baseDTO.getValue().equals(ENTRY)) || (baseDTO.getValue().equals(EXIT) ? exitsCount : entriesCount) < 5);
    }

    @Override
    protected boolean canDelete(OperatorDTO baseDTO) {
        return !(baseDTO.getValue().equals(EXIT) && exitsCount == 1 || baseDTO.getValue().equals(ENTRY) && entriesCount == 1);
    }

    @Override
    public void removeComponent(OperatorDTO operatorDTO) {
        if (canDelete(operatorDTO)) {
            isDirty = true;
            NameUtils.removeReservation(operatorDTO.getName());
            getModel().removeComponent(operatorDTO);
            inputCount--;
            entriesCount -= operatorDTO.getValue().equals(ENTRY) ? 1 : 0;
            exitsCount -= operatorDTO.getValue().equals(EXIT) ? 1 : 0;
            return;
        }
        showError("Impossible de supprimer cet input");
    }

    @Override
    public void calculate() {
    	getModel().generateCircuit().printThruthTable();
        //getModel().calculate();
    }

    private void showError(String error) {
        ErrorUtils.showError(null, error);
    }

    private void askForConfirmationThenExecute(Runnable runnable) {
        if (isDirty) {
            ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
                @Override
                public void onChoiceMade(boolean asConfirm) {
                    if (asConfirm) {
                        isDirty = false;
                        runnable.run();
                    }
                }
            });
        } else {
            runnable.run();
        }
    }
}
