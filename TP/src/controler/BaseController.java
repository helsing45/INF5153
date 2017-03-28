package controler;

import model.BaseModel;

/**
 * Created by j-c9 on 2017-03-27.
 */

public abstract class BaseController {

    private BaseModel model;

    protected BaseModel getModel(){
        return model;
    }

    public BaseController(BaseModel model) {
        this.model = model;
    }
}
