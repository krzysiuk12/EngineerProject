package services.implementation;

import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.interfaces.ISecurityProfilesManagementRepository;
import services.interfaces.ISecurityProfileManagementService;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
public class SecurityProfilesManagementService implements ISecurityProfileManagementService {

    private ISecurityProfilesManagementRepository securityProfilesManagementRepository;

    @Autowired
    public SecurityProfilesManagementService(ISecurityProfilesManagementRepository securityProfilesManagementRepository) {
        this.securityProfilesManagementRepository = securityProfilesManagementRepository;
    }

    @Override
    @Transactional
    public void saveSecurityProfile(SecurityProfile securityProfile) throws Exception {
        securityProfilesManagementRepository.saveOrUpdate(securityProfile);
    }

    @Override
    @Transactional
    public void saveAccountSecurityProfile(AccountSecurityProfile accountSecurityProfile) throws Exception {
        securityProfilesManagementRepository.saveOrUpdate(accountSecurityProfile);
    }

    @Override
    @Transactional
    public void savePasswordSecurityProfile(PasswordSecurityProfile passwordSecurityProfile) throws Exception {
        securityProfilesManagementRepository.saveOrUpdate(passwordSecurityProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public SecurityProfile getDefaultSecurityProfile() {
        return securityProfilesManagementRepository.getDefaultSecurityProfile();
    }
}
