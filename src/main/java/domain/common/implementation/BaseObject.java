package domain.common.implementation;

import domain.common.interfaces.IBaseObject;

/**
 * Created by Krzysiu on 2014-05-30.
 */
public class BaseObject implements IBaseObject {

    protected Long id;

    public BaseObject() {
    }

    public BaseObject(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
}
