package domain.useraccounts;

import domain.common.implementation.BaseObject;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-06-12.
 */
@Entity
@Table(name = "preferences")
public class Preferences extends BaseObject {

    private enum MapType {

    }

    private enum Language {
        PL,
        EN
    }

    private UserAccount userAccount;
    private boolean wizardDone;
    private MapType mapType;
    private Language language;


    public Preferences() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_Preferences", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_Preferences", sequenceName = "PK_Sequence_Preferences", initialValue = 2, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "useraccountid", unique = true, nullable = false)
    public UserAccount getUserAccount() {
        return userAccount;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @Basic
    @Column(name = "wizarddone")
    public boolean isWizardDone() {
        return wizardDone;
    }
    public void setWizardDone(boolean wizardDone) {
        this.wizardDone = wizardDone;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "maptype")
    public MapType getMapType() {
        return mapType;
    }
    public void setMapType(MapType mapType) {
        this.mapType = mapType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
}
