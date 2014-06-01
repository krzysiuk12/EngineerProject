package domain.useraccounts;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Embeddable
public class Emails {

    public enum PreferredEmail {
        PRIVATE,
        WORK
    }

    private String privateEmail;
    private String workEmail;
    private PreferredEmail preferredEmail;

    public Emails() {
    }
    public Emails(String privateEmail, String workEmail, PreferredEmail preferredEmail) {
        this.privateEmail = privateEmail;
        this.workEmail = workEmail;
        this.preferredEmail = preferredEmail;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "preferredEmail")
    public PreferredEmail getPreferredEmail() {
        return preferredEmail;
    }
    public void setPreferredEmail(PreferredEmail preferredEmail) {
        this.preferredEmail = preferredEmail;
    }

    @Basic
    @Column(name = "workEmail")
    public String getWorkEmail() {
        return workEmail;
    }
    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    @Basic
    @Column(name = "privateEmail")
    public String getPrivateEmail() {
        return privateEmail;
    }
    public void setPrivateEmail(String privateEmail) {
        this.privateEmail = privateEmail;
    }

    public String getDefaultEmail() {
        switch(preferredEmail) {
            case PRIVATE: return getPrivateEmail();
            case WORK: return getWorkEmail();
        }
        return null;
    }
}
