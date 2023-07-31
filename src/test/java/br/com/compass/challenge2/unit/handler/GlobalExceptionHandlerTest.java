package br.com.compass.challenge2.unit.handler;
import br.com.compass.challenge2.handler.GlobalExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
public class GlobalExceptionHandlerTest {

    @Test
    void handleEntityNotFoundException() {
        EntityNotFoundException e = new EntityNotFoundException("Entity not found");
        ProblemDetail problemDetail = new GlobalExceptionHandler().handleEntityNotFoundException(e);

        Assertions.assertEquals(404, problemDetail.getStatus());
        Assertions.assertEquals("Entity not found", problemDetail.getDetail());
    }

    @Test
    void handleIllegalArgumentException() {
        IllegalArgumentException e = new IllegalArgumentException("Illegal argument");
        ProblemDetail problemDetail = new GlobalExceptionHandler().handleIllegalArgumentException(e);

        Assertions.assertEquals(400, problemDetail.getStatus());
        Assertions.assertEquals("Illegal argument", problemDetail.getDetail());

    }

    @Test
    void handleConstraintViolationException() {
        ConstraintViolationException e = new ConstraintViolationException("Constraint violation", null);
        ProblemDetail problemDetail = new GlobalExceptionHandler().handleConstraintViolationException(e);
        Assertions.assertEquals(400, problemDetail.getStatus());
        Assertions.assertEquals("Constraint violation", problemDetail.getDetail());

    }
}
