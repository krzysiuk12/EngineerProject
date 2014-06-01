package domain.pictures;

import domain.common.implementation.UserVersionedBaseObject;

/**
 * Created by Krzysiu on 2014-05-31.
 */
public class Image extends UserVersionedBaseObject {

    private String name;
    private String description;
    private String path;
    private boolean defaultImage;

    public Image() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDefaultImage() {
        return defaultImage;
    }
    public void setDefaultImage(boolean defaultImage) {
        this.defaultImage = defaultImage;
    }
}