package domain.common.interfaces;

import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
public interface IVersionedBaseObject extends IBaseObject {

    public int getVersionNumber();

    public Date getCreationDate();
    public void setCreationDate(Date creationDate);

    public Date getLastModificationDate();
    public void setLastModificationDate(Date lastModificationDate);

    public Date getRemovalDate();
    public void setRemovalDate(Date removalDate);
}
