package br.com.compass.challenge2;

import br.com.compass.challenge2.integrate.SuiteIntegrateTest;
import br.com.compass.challenge2.unit.SuiteUnitTest;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
@Suite
@Testable
@SelectClasses({
        SuiteIntegrateTest.class,
        SuiteUnitTest.class,
})
public class AllSuiteTest {
}
