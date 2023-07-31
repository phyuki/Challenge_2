package br.com.compass.challenge2.integrate.controller;
import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.dto.AssessmentDTO;
import br.com.compass.challenge2.entity.Assessment;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.service.AssessmentService;
import br.com.compass.challenge2.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;



@AutoConfigureMockMvc(addFilters = false)
public class AssessmentControllerTest implements ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AssessmentService assessmentService;

    @Autowired
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = new Student();
        student.setName("John Doe");
        student.setEmail("john@example.com");
        student = studentService.save(student);
    }
    @Order(1)
    @Test
    public void testGetAllAssessments() throws Exception {
        Assessment assessment1 = new Assessment();
        assessment1.setStudent(student);
        assessment1.setActivityName("English Test");
        assessment1.setGrade(9.0f);
        assessment1 = assessmentService.save(assessment1);

        Assessment assessment2 = new Assessment();
        assessment2.setStudent(student);
        assessment2.setActivityName("History Test");
        assessment2.setGrade(7.0f);
        assessment2 = assessmentService.save(assessment2);

        mockMvc.perform(get("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.assessmentList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.assessmentList[0].activityName", is("English Test")))
                .andExpect(jsonPath("$._embedded.assessmentList[1].activityName", is("History Test")));
    }

    @Test
    public void testCreateAssessment() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(student.getId());
        assessmentDTO.setActivityName("Math Test");
        assessmentDTO.setGrade(8.5f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.activityName", is("Math Test")))
                .andExpect(jsonPath("$.grade", is(8.5)));
    }

    @Test
    public void testCreateAssessmentWithInvalidStudent() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(999L);
        assessmentDTO.setActivityName("Math Test");
        assessmentDTO.setGrade(8.5f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateAssessment() throws Exception {
        Assessment existingAssessment = new Assessment();
        existingAssessment.setStudent(student);
        existingAssessment.setActivityName("Physics Test");
        existingAssessment.setGrade(7.5f);
        existingAssessment = assessmentService.save(existingAssessment);

        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(student.getId());
        assessmentDTO.setActivityName("Updated Physics Test");
        assessmentDTO.setGrade(9.0f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(put("/api/assessments/{id}", existingAssessment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName", is("Updated Physics Test")))
                .andExpect(jsonPath("$.grade", is(9.0)));
    }

    @Test
    public void testUpdateAssessmentWithInvalidId() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(student.getId());
        assessmentDTO.setActivityName("Updated Physics Test");
        assessmentDTO.setGrade(9.0f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(put("/api/assessments/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteAssessment() throws Exception {
        Assessment existingAssessment = new Assessment();
        existingAssessment.setStudent(student);
        existingAssessment.setActivityName("Biology Test");
        existingAssessment.setGrade(6.0f);
        existingAssessment = assessmentService.save(existingAssessment);

        mockMvc.perform(delete("/api/assessments/{id}", existingAssessment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteAssessmentWithInvalidId() throws Exception {
        mockMvc.perform(delete("/api/assessments/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAssessmentById() throws Exception {
        Assessment existingAssessment = new Assessment();
        existingAssessment.setStudent(student);
        existingAssessment.setActivityName("Chemistry Test");
        existingAssessment.setGrade(8.0f);
        existingAssessment = assessmentService.save(existingAssessment);

        mockMvc.perform(get("/api/assessments/{id}", existingAssessment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName", is("Chemistry Test")))
                .andExpect(jsonPath("$.grade", is(8.0)));
    }

    @Test
    public void testGetAssessmentByIdWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/assessments/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }


    @Test
    public void testGetAssessmentsByStudentId() throws Exception {
        Assessment assessment1 = new Assessment();
        assessment1.setStudent(student);
        assessment1.setActivityName("Physics Test");
        assessment1.setGrade(8.5f);
        assessment1 = assessmentService.save(assessment1);

        Student newStudent = new Student();
        newStudent.setName("Jane Doe");
        newStudent.setEmail("jane@example.com");
        newStudent = studentService.save(newStudent);

        Assessment assessment2 = new Assessment();
        assessment2.setStudent(newStudent);
        assessment2.setActivityName("Chemistry Test");
        assessment2.setGrade(7.0f);
        assessment2 = assessmentService.save(assessment2);

        mockMvc.perform(get("/api/assessments/students/{id}", student.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.assessmentList", hasSize(1)))
                .andExpect(jsonPath("$._embedded.assessmentList[0].activityName", is("Physics Test")));
    }

    @Test
    public void testUpdatePartialAssessment() throws Exception {
        Assessment existingAssessment = new Assessment();
        existingAssessment.setStudent(student);
        existingAssessment.setActivityName("Chemistry Test");
        existingAssessment.setGrade(8.0f);
        existingAssessment = assessmentService.save(existingAssessment);

        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setGrade(9.0f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(patch("/api/assessments/{id}", existingAssessment.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grade", is(9.0)));

        mockMvc.perform(get("/api/assessments/{id}", existingAssessment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityName", is("Chemistry Test")))
                .andExpect(jsonPath("$.id", is(student.getId().intValue())));
    }

    @Test
    public void testCreateAssessmentWithInvalidGrade() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(student.getId());
        assessmentDTO.setActivityName("Invalid Grade Test");
        assessmentDTO.setGrade(null);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateAssessmentWithInvalidStudentId() throws Exception {
        AssessmentDTO assessmentDTO = new AssessmentDTO();
        assessmentDTO.setStudentId(999L);
        assessmentDTO.setActivityName("Invalid Student Test");
        assessmentDTO.setGrade(8.0f);

        String json = objectMapper.writeValueAsString(assessmentDTO);

        mockMvc.perform(post("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllAssessmentsEmpty() throws Exception {
        mockMvc.perform(get("/api/assessments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

}
