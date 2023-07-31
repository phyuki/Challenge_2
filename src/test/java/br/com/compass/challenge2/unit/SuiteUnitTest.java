package br.com.compass.challenge2.unit;

import br.com.compass.challenge2.unit.entity.SuiteEntity;
import br.com.compass.challenge2.unit.handler.GlobalExceptionHandlerTest;
import br.com.compass.challenge2.unit.service.SuiteService;
import org.junit.platform.commons.annotation.Testable;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@Testable
@SelectClasses({
        SuiteService.class,
        SuiteEntity.class,
        GlobalExceptionHandlerTest.class
})
public class SuiteUnitTest {
}
