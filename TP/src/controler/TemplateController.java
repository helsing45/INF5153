package controler;

import model.BaseDTO;
import model.Link;
import model.Template;
import utils.ConfirmationUtils;
import utils.XmlUtils;

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

public class TemplateController extends BaseController<BaseDTO, Template> {
    protected int entriesCount, exitsCount;

    public TemplateController(Template model) {
        super(model);
    }

    @Override
    public ArrayList<Line> getLines() {
        ArrayList<Line> lines = new ArrayList<>();
        for (Link pair : getModel().getLinks()) {
            JLabel label1 = pair.getLabel1();
            JLabel label2 = pair.getLabel2();
            Point point1 = label1.getLocation();
            Point point2 = label2.getLocation();
            int i = pair.howToDraw();
            if (i == 1) {
                lines.add(new Line(point1.x, point1.y + label1.getHeight() / 2, point2.x + label2.getWidth(), point2.y + label2.getHeight() / 2));
            } else if (i == 2) {
                lines.add(new Line(point2.x, point2.y + label2.getHeight() / 2, point1.x + label1.getWidth(), point1.y + label1.getHeight() / 2));
            } else if (i == 3) {
                lines.add(new Line(point1.x + label1.getWidth() / 2, point1.y, point2.x + label2.getWidth() / 2, point2.y + label2.getHeight()));
            } else if (i == 4) {
                lines.add(new Line(point2.x + label2.getWidth() / 2, point2.y, point1.x + label1.getWidth() / 2, point1.y + label1.getHeight()));
            }
        }
        return lines;
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
    public BaseDTO[] getAllComponent() {
        //TODO ajouter les custom doors
        return new BaseDTO[]{
                BaseDTO.getEntryDTO(),
                BaseDTO.getExitDTO(),
                new BaseDTO("And").setFileName("and.png").setEntryCount(2).setExitsCount(1),
                new BaseDTO("Or").setFileName("or.png").setEntryCount(2).setExitsCount(1),
                new BaseDTO("Not").setFileName("not.png").setEntryCount(1).setExitsCount(1)
        };
    }

    @Override
    public HashMap<BaseDTO, Point> getComponentsPosition() {
        return getModel().getOperators();
    }

    @Override
    public boolean addComponent(BaseDTO baseDTO, Point position) {
        if (canAdd(baseDTO)) {

            getModel().addComponent(baseDTO, position);
            inputCount++;
            entriesCount += baseDTO.getValue().equals(ENTRY) ? 1 : 0;
            exitsCount += baseDTO.getValue().equals(EXIT) ? 1 : 0;
            return true;
        }
        return false;
    }

    public void link() {

    }

    protected boolean canAdd(BaseDTO baseDTO) {
        return inputCount < getInputMax() && (!(baseDTO.getValue().equals(EXIT) || baseDTO.getValue().equals(ENTRY)) || (baseDTO.getValue().equals(EXIT) ? exitsCount : entriesCount) < 5);
    }
}
