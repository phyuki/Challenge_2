package br.com.compass.challenge2.integrate;
import br.com.compass.challenge2.integrate.controller.SuiteController;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        SuiteController.class
})
public class SuiteIntegrateTest {
}
