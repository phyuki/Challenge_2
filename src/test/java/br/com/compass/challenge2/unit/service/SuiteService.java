package br.com.compass.challenge2.unit.service;
import br.com.compass.challenge2.unit.entity.StudentTest;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        GroupServiceTest.class,
        AssessmentServiceTest.class,
        StudentServiceTest.class
})
public class SuiteService {
}
