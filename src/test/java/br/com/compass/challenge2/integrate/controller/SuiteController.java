package br.com.compass.challenge2.integrate.controller;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        AssessmentControllerTest.class,
})
public class SuiteController {
}
