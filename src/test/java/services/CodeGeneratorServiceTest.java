package services;

import common.BaseTestObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import services.interfaces.ICodeGeneratorService;

import static org.junit.Assert.*;

/**
 * Created by Krzysztof Kicinger on 2014-11-16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
@ActiveProfiles("test")
public class CodeGeneratorServiceTest extends BaseTestObject {

    @Autowired
    private ICodeGeneratorService codeGeneratorService;

    @Test
    public void codeGeneratorTest() {
        try {
            String code = codeGeneratorService.generateSessionToken();
            assertNotNull(code);
            assertEquals(code.length(), 20);
        } catch (Exception ex) {
            fail("Failed codeGeneratorTest.");
        }
    }

}
