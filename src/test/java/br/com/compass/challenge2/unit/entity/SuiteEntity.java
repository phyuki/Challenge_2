package br.com.compass.challenge2.unit.entity;

import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        StudentTest.class
})
public class SuiteEntity {
}
