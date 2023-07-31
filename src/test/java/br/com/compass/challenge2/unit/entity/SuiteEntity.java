package br.com.compass.challenge2.unit.entity;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        AssessmentTest.class,
        StudentTest.class,
        GroupTest.class,
        SquadTest.class,
        OrganizerTest.class
})
public class SuiteEntity {
}
