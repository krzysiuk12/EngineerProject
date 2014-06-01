package domain.notes;

import domain.locations.Location;
import domain.pictures.Image;
import domain.trips.Trip;
import domain.useraccounts.UserAccount;

/**
 * Created by Krzysiu on 2014-05-31.
 */
public class ImageNote extends Note {

    private Image image;

    public ImageNote() {
    }
    public ImageNote(Long id, String title, String message, UserAccount userAccount, Trip trip, Location location, Image image) {
        super(id, title, message, userAccount, trip, location);
        this.image = image;
    }

    public Image getImage() {
        return image;
    }
    public void setImage(Image image) {
        this.image = image;
    }
}
