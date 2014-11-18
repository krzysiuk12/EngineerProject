import org.junit.Test;
import tools.ValidationTools;

import static junit.framework.TestCase.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-17.
 */
public class ValidationToolsTest {

    @Test
    public void validateEmailTest() {
        String emailAddress = "email@email.com";
        assertTrue(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email@email@email.com";
        assertEquals(false, ValidationTools.validateEmail(emailAddress));
        emailAddress = "email.email.com";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email@.com";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email@com";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email kom@com";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email_om@com pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email_om@com.pl.";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email_om@com..pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "_email_om@com.pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "0email_om@com.pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = "email_92_om@com.pl";
        assertTrue(ValidationTools.validateEmail(emailAddress));
        emailAddress = "j.kowalski@text.com.pl";
        assertTrue(ValidationTools.validateEmail(emailAddress));
        emailAddress = "j.kowal@firma-bala.com.pl";
        assertTrue(ValidationTools.validateEmail(emailAddress));
        emailAddress = "j.@firma.pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
        emailAddress = ".jan@firma.pl";
        assertFalse(ValidationTools.validateEmail(emailAddress));
    }

    @Test
    public void validateLoginTest() {
        String login = "123triptour@.+-!?#$%^&*=_~'`{}/|a";
        assertTrue(ValidationTools.validateLogin(login));
    }

    @Test
    public void validateFirstNameTest() {
        String firstName = "Krzysiu";
        assertTrue(ValidationTools.validateFirstName(firstName));
        firstName = "KrzysiuJan";
        assertFalse(ValidationTools.validateFirstName(firstName));
        firstName = "Kowal";
        assertTrue(ValidationTools.validateFirstName(firstName));
        firstName = "kowal";
        assertFalse(ValidationTools.validateFirstName(firstName));
        firstName = "Krzysiu-";
        assertFalse(ValidationTools.validateFirstName(firstName));
        firstName = "Krzysiu123";
        assertFalse(ValidationTools.validateFirstName(firstName));
        firstName = "Krzysiu----";
        assertFalse(ValidationTools.validateFirstName(firstName));
    }

    @Test
    public void validateLastNameTest() {
        String lastName = "Kowalski";
        assertTrue(ValidationTools.validateLastName(lastName));
        lastName = "KowalskiJan";
        assertFalse(ValidationTools.validateLastName(lastName));
        lastName = "Kowal";
        assertTrue(ValidationTools.validateLastName(lastName));
        lastName = "kowal";
        assertFalse(ValidationTools.validateLastName(lastName));
        lastName = "Kowalski-";
        assertFalse(ValidationTools.validateLastName(lastName));
        lastName = "Kowalski123";
        assertFalse(ValidationTools.validateLastName(lastName));
        lastName = "Kowalski----";
        assertFalse(ValidationTools.validateLastName(lastName));
        lastName = "Kowalski-Rower";
        assertTrue(ValidationTools.validateLastName(lastName));
        lastName = "Kowalski--Rower";
        assertFalse(ValidationTools.validateLastName(lastName));
    }

    @Test
    public void validateLongitudeTest() {
        double longitude = 0.0;
        assertTrue(ValidationTools.validateLongitude(longitude));
        longitude = 180.0;
        assertTrue(ValidationTools.validateLongitude(longitude));
        longitude = 180.000010;
        assertFalse(ValidationTools.validateLongitude(longitude));
        longitude = -180.0;
        assertTrue(ValidationTools.validateLongitude(longitude));
        longitude = -180.000010;
        assertFalse(ValidationTools.validateLongitude(longitude));
    }

    @Test
    public void validateLatitudeTest() {
        double longitude = 0.0;
        assertTrue(ValidationTools.validateLatitude(longitude));
        longitude = 90.0;
        assertTrue(ValidationTools.validateLatitude(longitude));
        longitude = 90.000010;
        assertFalse(ValidationTools.validateLatitude(longitude));
        longitude = -90.0;
        assertTrue(ValidationTools.validateLatitude(longitude));
        longitude = -90.000010;
        assertFalse(ValidationTools.validateLatitude(longitude));
    }
}
