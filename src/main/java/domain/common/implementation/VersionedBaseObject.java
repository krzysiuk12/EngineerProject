package domain.common.implementation;

import domain.common.interfaces.IVersionedBaseObject;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@MappedSuperclass
@JsonIgnoreProperties(value = { "versionNumber", "lastModificationDate", "removalDate"})
public abstract class VersionedBaseObject extends BaseObject implements IVersionedBaseObject {

    protected int versionNumber;
    protected Date creationDate;
    protected Date lastModificationDate;
    protected Date removalDate;

    public VersionedBaseObject() {
    }

    public VersionedBaseObject(Long id, int versionNumber, Date creationDate, Date lastModificationDate, Date removalDate) {
        super(id);
        this.versionNumber = versionNumber;
        this.creationDate = creationDate;
        this.lastModificationDate = lastModificationDate;
        this.removalDate = removalDate;
    }

    @Override
    @Version
    @Column(name = "versionnumber")
    public int getVersionNumber() {
        return versionNumber;
    }
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creationdate")
    public Date getCreationDate() {
        return creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastmodificationdate")
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
    @Override
    public void setLastModificationDate(Date lastModificationDate) {
        this.lastModificationDate = lastModificationDate;
    }

    @Override
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "removaldate")
    public Date getRemovalDate() {
        return removalDate;
    }
    @Override
    public void setRemovalDate(Date removalDate) {
        this.removalDate = removalDate;
    }
}
