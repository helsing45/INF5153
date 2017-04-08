package model;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.UUID;

public abstract class BaseDTO implements Transferable, Cloneable {

    public static final String ENTRY = "entry", EXIT = "end";
    public static final String ICON_PATH = "/images/operator_icon/";

    protected String name, value;
    protected String fileName;
    protected String id;
    protected int entryCount, exitsCount;
    protected boolean canBeName;
    protected Rectangle bound;

    DataFlavor dataFlavor = new DataFlavor(BaseDTO.class,
            BaseDTO.class.getSimpleName());

    public BaseDTO(String value) {
        this.value = value;

        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
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
        return new ImageIcon(getClass().getResource(getPath()));
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

    public abstract BaseDTO setFileName(String fileName);

    public abstract BaseDTO setEntryCount(int entryCount);

    public abstract BaseDTO setExitsCount(int exitsCount);

    public abstract BaseDTO setCanBeName(boolean canBeName);

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BaseDTO && ((BaseDTO) obj).id.equals(id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public abstract BaseDTO clone();

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
