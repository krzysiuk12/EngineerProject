package domain.useraccounts;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-05-30.
 */
@Embeddable
public class Phones {

    public enum PreferredPhone {
        WORK,
        PRIVATE
    }

    private String privateMobile;
    private String workMobile;
    private PreferredPhone preferredPhone;

    public Phones() {
    }
    public Phones(String privateMobile, String workMobile, PreferredPhone preferredPhone) {
        this.privateMobile = privateMobile;
        this.workMobile = workMobile;
        this.preferredPhone = preferredPhone;
    }

    @Basic
    @Column(name = "privateMobile")
    public String getPrivateMobile() {
        return privateMobile;
    }
    public void setPrivateMobile(String privateMobile) {
        this.privateMobile = privateMobile;
    }

    @Basic
    @Column(name = "workMobile")
    public String getWorkMobile() {
        return workMobile;
    }
    public void setWorkMobile(String workMobile) {
        this.workMobile = workMobile;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "preferredPhone")
    public PreferredPhone getPreferredPhone() {
        return preferredPhone;
    }
    public void setPreferredPhone(PreferredPhone preferredPhone) {
        this.preferredPhone = preferredPhone;
    }

    public String getDefaultPhone() {
        switch(preferredPhone) {
            case WORK: return getWorkMobile();
            case PRIVATE: return getPrivateMobile();
        }
        return null;
    }
}
