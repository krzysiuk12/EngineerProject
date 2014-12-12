package services.implementation;

import domain.locations.Address;
import domain.locations.Location;
import domain.securityprofiles.AccountSecurityProfile;
import domain.securityprofiles.PasswordSecurityProfile;
import domain.securityprofiles.SecurityProfile;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import domain.useraccounts.UserGroup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import services.interfaces.IDataGeneratorService;
import services.interfaces.ILocationManagementService;
import services.interfaces.IUserManagementService;
import tools.ConfigurationTools;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Created by Krzysztof Kicinger on 2014-11-18.
 */
@Service
@Profile("default")
public class SystemInitializationService {

    private IDataGeneratorService dataGeneratorService;
    private IUserManagementService userManagementService;
    private ILocationManagementService locationManagementService;

    @Autowired
    public SystemInitializationService(IDataGeneratorService dataGeneratorService, IUserManagementService userManagementService, ILocationManagementService locationManagementService) {
        this.dataGeneratorService = dataGeneratorService;
        this.userManagementService = userManagementService;
        this.locationManagementService = locationManagementService;
    }

    @PostConstruct
    public void init() throws Exception {
        if (userManagementService.getUserAccountByLogin(ConfigurationTools.ADMINISTRATOR_LOGIN) == null) {
            PasswordSecurityProfile passwordSecurityProfile = dataGeneratorService.createAndSavePasswordSecurityProfile(4, 32, false, 0, 0, false, false, false, false);
            AccountSecurityProfile accountSecurityProfile = dataGeneratorService.createAndSaveAccountSecurityProfile(4, 32, 3, 30, 3);
            SecurityProfile securityProfile = dataGeneratorService.createAndSaveSecurityProfile(ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, ConfigurationTools.DEFAULT_SECURITY_PROFILE_NAME, true, accountSecurityProfile, passwordSecurityProfile);
            UserGroup administratorsUserGroup = dataGeneratorService.createAndSaveUserGroup(ConfigurationTools.ADMINISTRATORS_USER_GROUP, ConfigurationTools.ADMINISTRATORS_USER_GROUP, securityProfile);
            UserGroup simpleUsersUserGroup = dataGeneratorService.createAndSaveUserGroup(ConfigurationTools.SIMPLE_USERS_USER_GROUP, ConfigurationTools.SIMPLE_USERS_USER_GROUP, securityProfile);
            Individual systemIndividual = dataGeneratorService.createIndividual(ConfigurationTools.SYSTEM_INDIVIDUAL, ConfigurationTools.SYSTEM_INDIVIDUAL, "System Individual", null, null);
            UserAccount systemUserAccount = dataGeneratorService.createAndSaveUserAccount(ConfigurationTools.SYSTEM_ACCOUNT_LOGIN, ConfigurationTools.SYSTEM_ACCOUNT_PASSWORD, ConfigurationTools.SYSTEM_ACCOUNT_EMAIL, systemIndividual, administratorsUserGroup);
            Individual administratorIndividual = dataGeneratorService.createIndividual(ConfigurationTools.ADMINISTRATOR_INDIVIDUAL, ConfigurationTools.ADMINISTRATOR_INDIVIDUAL, "Administrator Individual", null, null);
            UserAccount administratorUserAccount = dataGeneratorService.createAndSaveUserAccount(ConfigurationTools.ADMINISTRATOR_LOGIN, ConfigurationTools.ADMINISTRATOR_PASSWORD, ConfigurationTools.ADMINISTRATOR_EMAIL, administratorIndividual, administratorsUserGroup);
            initializeLocations(systemUserAccount);
        }
    }

    private void initializeLocations(UserAccount createdBy) throws Exception {
        File locationsFile = new ClassPathResource("data/locations.csv").getFile();
        CSVParser parser = CSVParser.parse(locationsFile, StandardCharsets.UTF_8, CSVFormat.RFC4180.withHeader());
        for (CSVRecord csvRecord : parser) {
            Address address = dataGeneratorService.createAddress(readValue(csvRecord.get("STREET")), readValue(csvRecord.get("CITY")), readValue(csvRecord.get("POSTAL CODE")), readValue(csvRecord.get("COUNTRY")));
            Location location = dataGeneratorService.createLocation(readValue(csvRecord.get("NAME")), readValue(csvRecord.get("DESCRIPTION")), readValue(csvRecord.get("URL")), Double.valueOf(csvRecord.get("LATITUDE")), Double.valueOf(csvRecord.get("LONGITUDE")), false, address, createdBy);
            locationManagementService.saveLocation(location, createdBy);
        }
    }

    private String readValue(String csvValue) {
        if(csvValue.isEmpty() || csvValue.equals("NULL")) {
            return null;
        }
        return csvValue;
    }

}
