package br.com.compass.challenge2.unit;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AssessmentTest {

    private ValidatorFactory factory;
    private Validator validator;

    @BeforeAll
    void setUp(){
        validator = factory.getValidator();
        factory = Validation.buildDefaultValidatorFactory();
    }
    @Test
    public void testAssessmentCreation() {
        // Cenário
        Student student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");

        String activityName = "Math Test";
        Float grade = 9.5F;

        // Ação
        Assessment assessment = new Assessment();
        assessment.setStudent(student);
        assessment.setActivityName(activityName);
        assessment.setGrade(grade);

        // Verificação
        Assertions.assertEquals(student, assessment.getStudent());
        Assertions.assertEquals(activityName, assessment.getActivityName());
        Assertions.assertEquals(grade, assessment.getGrade());
    }

    @Test
    public void testStudentNotNull() {

        String activityName = "English Test";
        Float grade = 8.0F;

        Assessment assessment = new Assessment();
        assessment.setActivityName(activityName);
        assessment.setGrade(grade);

        Assertions.assertFalse(validator.validate(assessment).isEmpty());
    }

    @Test
    public void testActivityNameNotBlank() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Jane Doe");
        student.setEmail("jane.doe@example.com");

        Float grade = 7.5F;


        Assessment assessment = new Assessment();
        assessment.setStudent(student);
        assessment.setGrade(grade);


        Assertions.assertFalse(validator.validate(assessment).isEmpty());
    }

    @Test
    public void testGradeNotNull() {

        Student student = new Student();
        student.setId(1L);
        student.setName("Bob Smith");
        student.setEmail("bob.smith@example.com");

        String activityName = "Science Test";


        Assessment assessment = new Assessment();
        assessment.setStudent(student);
        assessment.setActivityName(activityName);


        Assertions.assertFalse(validator.validate(assessment).isEmpty());
    }
}
