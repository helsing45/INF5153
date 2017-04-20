package model;

import utils.NameUtils;

import java.util.UUID;

/**
 * Created by j-c9 on 2017-04-01.
 */
public class OperatorDTO extends BaseDTO {

	public OperatorDTO(String value) {
        super(value);
    }

    public static OperatorDTO getEntryDTO() {
        return new OperatorDTO("entry")
                .setExitsCount(1)
                .setCanBeName(true);

    }

    public static OperatorDTO getExitDTO() {
        return new OperatorDTO("end")
                .setEntryCount(1)
                .setCanBeName(true);

    }

    @Override
    public void setName(String name) {
        if(canBeName() && NameUtils.isNameAvailable(name)){
            NameUtils.reserveName(name);
            super.setName(name);
        }
    }

   /* @Override
    public OperatorDTO setImage(ImageIcon image) {
        this.image = image;
        return this;
    }*/

    @Override
    public OperatorDTO setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public OperatorDTO setEntryCount(int entryCount) {
        this.entryCount = entryCount;
        return this;
    }

    @Override
    public OperatorDTO setExitsCount(int exitsCount) {
        this.exitsCount = exitsCount;
        return this;
    }

    @Override
    public OperatorDTO setCanBeName(boolean canBeName) {
        this.canBeName = canBeName;
        return this;
    }

    @Override
    public OperatorDTO clone() {
        OperatorDTO copy = new OperatorDTO(value);
        id = UUID.randomUUID().toString();
        copy.name = name;
        copy.value = value;
        copy.fileName = fileName;
        copy.entryCount = entryCount;
        copy.exitsCount = exitsCount;
        copy.canBeName = canBeName;
        return copy;
    }
}
