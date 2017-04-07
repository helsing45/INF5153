package controler;

import model.Link;
import model.OperatorDTO;
import model.Template;
import utils.ConfirmationUtils;
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
        // TODO only ask the confirmation if change as been made
        // TODO put this in a command class
        ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
            @Override
            public void onChoiceMade(boolean asConfirm) {
                if (asConfirm) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.addChoosableFileFilter(new FileNameExtensionFilter("XML", "xml"));

                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                        getModel().update((Template) XmlUtils.getXmlUtils().fromXML(chooser.getSelectedFile()));
                    }
                }
            }
        });
    }

    @Override
    public void reset() {
        // TODO only ask the confirmation if change as been made
        // TODO put this in a command class
        ConfirmationUtils.askForConfirmation(new ConfirmationUtils.ConfirmationListener() {
            @Override
            public void onChoiceMade(boolean asConfirm) {
                if (asConfirm) {
                    getModel().reset();
                }
            }
        });
    }

    @Override
    public void onLocationChange(OperatorDTO operatorDTO, Point point) {
        getModel().setComponentPosition(operatorDTO, point);
    }

    @Override
    public void saveTemplate() {
        //TODO do a command instead
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
            bw.write(XmlUtils.getXmlUtils().toXML(getModel()));

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

    @Override
    public boolean addComponent(OperatorDTO baseDTO, Point position) {
        if (canAdd(baseDTO)) {
            if (baseDTO.getValue().equals(EXIT) || baseDTO.getValue().equals(ENTRY))
                baseDTO.setName((baseDTO.getValue().equals(EXIT) ? "S" : "E") + (baseDTO.getValue().equals(EXIT) ? exitsCount : entriesCount));
            getModel().addComponent(baseDTO, position);
            inputCount++;
            entriesCount += baseDTO.getValue().equals(ENTRY) ? 1 : 0;
            exitsCount += baseDTO.getValue().equals(EXIT) ? 1 : 0;
            return true;
        }
        return false;
    }

    public void addLink(OperatorLabel ol1, OperatorLabel ol2) {
        getModel().addLink(ol1, ol2);
    }

    protected boolean canAdd(OperatorDTO baseDTO) {
        return inputCount < getInputMax() && (!(baseDTO.getValue().equals(EXIT) || baseDTO.getValue().equals(ENTRY)) || (baseDTO.getValue().equals(EXIT) ? exitsCount : entriesCount) < 5);
    }
}
