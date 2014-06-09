package domain.useraccounts;

import domain.common.implementation.BaseObject;
import domain.securityprofiles.SecurityProfile;

import javax.persistence.*;

/**
 * Created by Krzysiu on 2014-06-09.
 */
@Entity
@Table(name = "UserGroups")
public class UserGroup extends BaseObject {

    private SecurityProfile securityProfile;
    private String name;
    private String description;

    public UserGroup() {
    }

    @Override
    @Id
    @GeneratedValue(generator = "PK_Sequence_UserGroups", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "PK_Sequence_UserGroups", sequenceName = "PK_Sequence_UserGroups", initialValue = 1, allocationSize = 1)
    public Long getId() {
        return super.getId();
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "securityProfileId", nullable = false)
    public SecurityProfile getSecurityProfile() {
        return securityProfile;
    }
    public void setSecurityProfile(SecurityProfile securityProfile) {
        this.securityProfile = securityProfile;
    }

    @Basic
    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
