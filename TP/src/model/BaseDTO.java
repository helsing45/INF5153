package model;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by j-c9 on 2017-03-25.
 */
public class BaseDTO implements Transferable, Cloneable {

    public static final String ENTRY = "entry", EXIT = "end";
    public static final String ICON_PATH = "/images/operator_icon/";

    private ImageIcon image;
    private String name, value;
    private String fileName;
    private String id;
    private int entryCount, exitsCount;
    private boolean canBeName;
    private Rectangle bound;

    DataFlavor dataFlavor = new DataFlavor(BaseDTO.class,
            BaseDTO.class.getSimpleName());

    public BaseDTO(String value) {
        this.value = value;

        id = UUID.randomUUID().toString();
    }

    public static BaseDTO getEntryDTO() {
        return new BaseDTO("entry")
                .setExitsCount(1)
                .setCanBeName(true);

    }

    public static BaseDTO getExitDTO() {
        return new BaseDTO("end")
                .setEntryCount(1)
                .setCanBeName(true);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public Rectangle getBound() {
        return bound;
    }

    public void setBound(Rectangle bound) {
        this.bound = bound;
    }

    private String getPath() {
        return ICON_PATH + getFileName();
    }

    private String getFileName() {
        return fileName == null ? value + ".png" : fileName;
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

    public BaseDTO setImage(ImageIcon image) {
        this.image = image;
        return this;
    }

    public BaseDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public BaseDTO setEntryCount(int entryCount) {
        this.entryCount = entryCount;
        return this;
    }

    public BaseDTO setExitsCount(int exitsCount) {
        this.exitsCount = exitsCount;
        return this;
    }

    public BaseDTO setCanBeName(boolean canBeName) {
        this.canBeName = canBeName;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BaseDTO && ((BaseDTO) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public BaseDTO clone() {
        BaseDTO copy = new BaseDTO(value);
        id = UUID.randomUUID().toString();
        copy.name = name;
        copy.value = value;
        copy.fileName = fileName;
        copy.entryCount = entryCount;
        copy.exitsCount = exitsCount;
        copy.canBeName = canBeName;
        return copy;
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{dataFlavor};
    }

    /**
     * Returns whether or not the specified data flavor is supported for
     * this object.
     *
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
