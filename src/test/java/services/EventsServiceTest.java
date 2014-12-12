package services;

import common.BaseTestObject;
import domain.eventshistory.LogEvent;
import domain.useraccounts.Individual;
import domain.useraccounts.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.IEventsService;
import services.interfaces.IUserManagementService;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Krzysztof Kicinger on 2014-12-11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class EventsServiceTest extends BaseTestObject {

    @Autowired
    public IEventsService eventsService;

    @Autowired
    public IUserManagementService userManagementService;

    @Test
    @Transactional
    public void testSavingEvents() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "Token", "jankowalski@gmail.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);
        eventsService.saveSuccessfulLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveSuccessfulLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveFailedLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveLogoutEvent(account, "192.168.0.0", "1234567890");

        List<LogEvent> eventLogs = eventsService.getAllLogEvents();
        assertNotNull(eventLogs);
        assertCollectionSize(eventLogs, 4);
    }

    @Test
    @Transactional
    public void testSavingLogEventsByUserAccount() throws Exception {
        Individual individual = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual);
        UserAccount account = createUserAccount("Login", "Password", "Token", "jankowalski@gmail.com", UserAccount.Status.ACTIVE, individual);
        userManagementService.saveUserAccount(account);

        Individual individual2 = createIndividual("Jan", "Kowalski");
        userManagementService.saveIndividual(individual2);
        UserAccount account2 = createUserAccount("Login2", "Password", "Token", "jankowalski2@gmail.com", UserAccount.Status.ACTIVE, individual2);
        userManagementService.saveUserAccount(account2);

        eventsService.saveSuccessfulLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveSuccessfulLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveFailedLoginEvent(account, "192.168.0.0", "1234567890");
        eventsService.saveLogoutEvent(account, "192.168.0.0", "1234567890");

        eventsService.saveSuccessfulLoginEvent(account2, "192.168.0.0", "1234567890");
        eventsService.saveLogoutEvent(account2, "192.168.0.0", "1234567890");

        List<LogEvent> userOneLogEvents = eventsService.getLogEventForUserAccount(account);
        assertNotNull(userOneLogEvents);
        assertCollectionSize(userOneLogEvents, 4);

        List<LogEvent> userTwoLogEvents = eventsService.getLogEventForUserAccount(account2);
        assertNotNull(userTwoLogEvents);
        assertCollectionSize(userTwoLogEvents, 2);
    }

}
