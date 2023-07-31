package br.com.compass.challenge2.unit.service;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        GroupServiceTest.class,
        AssessmentServiceTest.class,
        StudentServiceTest.class,
        SquadServiceTest.class,
        OrganizerServiceTest.class
})
public class SuiteService {
}
