package model;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * Created by j-c9 on 2017-03-25.
 */
public class OperatorDTO  implements Transferable {

    public static final String ENTRY = "entry",EXIT = "end";
    public static final String ICON_PATH = "/images/operator_icon/";
    private ImageIcon image;
    private String name;
    private String fileName;
    private int entryCount, exitsCount;
    private boolean canBeName;

    DataFlavor dataFlavor = new DataFlavor(OperatorDTO.class,
            OperatorDTO.class.getSimpleName());

    public OperatorDTO(String name) {
        this.name = name;
    }

    public static OperatorDTO getEntryDTO(){
        return new OperatorDTO("entry")
                .setExitsCount(1)
                .setCanBeName(true);

    }

    public static OperatorDTO getExitDTO(){
        return new OperatorDTO("end")
                .setEntryCount(1)
                .setCanBeName(true);

    }

    public String getName() {
        return name;
    }

    private String getPath() {
        return ICON_PATH + getFileName();
    }

    private String getFileName() {
        return fileName == null ? name + ".png" : fileName;
    }

    public ImageIcon getImage() {
        if (image == null) {
            image = new ImageIcon(getClass().getResource(getPath()));
        }
        return image;
    }

    public int getEntryCount() {
        return entryCount;
    }

    public int getExitCount() {
        return exitsCount;
    }

    public boolean canBeName() {
        return canBeName;
    }

    public OperatorDTO setImage(ImageIcon image) {
        this.image = image;
        return this;
    }

    public OperatorDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public OperatorDTO setEntryCount(int entryCount) {
        this.entryCount = entryCount;
        return this;
    }

    public OperatorDTO setExitsCount(int exitsCount) {
        this.exitsCount = exitsCount;
        return this;
    }

    public OperatorDTO setCanBeName(boolean canBeName) {
        this.canBeName = canBeName;
        return this;
    }

    public DataFlavor[] getTransferDataFlavors(){
        return new DataFlavor[] { dataFlavor };
    }

    /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     * @param flavor the requested flavor for the data
     * @return boolean indicating whether or not the data flavor is supported
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        //return flavor.equals(dataFlavor);
        return true;
    }

    @Override
    public Object getTransferData(DataFlavor flavor)
            throws UnsupportedFlavorException, IOException {

        if (flavor.equals(dataFlavor))
            return this;
        else
            throw new UnsupportedFlavorException(flavor);
    }

}
