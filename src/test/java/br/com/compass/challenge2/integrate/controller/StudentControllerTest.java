package br.com.compass.challenge2.integrate.controller;

import br.com.compass.challenge2.config.ConfigTest;
import br.com.compass.challenge2.entity.Student;
import br.com.compass.challenge2.repository.StudentRepository;
import br.com.compass.challenge2.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc(addFilters = false)
public class StudentControllerTest implements ConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    public void setUp() {
        studentRepository.deleteAll();
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student();
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@guilherme.com"));
    }

    @Test
    void testCreateStudentWithId() throws Exception {
        Student student = new Student();
        student.setId(1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testFindAllStudents() throws Exception {
        Student student1 = new Student();
        student1.setName("Gabriel");
        student1.setEmail("gabriel@guilherme.com");
        studentService.save(student1);

        Student student2 = new Student();
        student2.setName("Guilherme");
        student2.setEmail("guilherme@gabriel.com");
        studentService.save(student2);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Gabriel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("gabriel@guilherme.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Guilherme"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email").value("guilherme@gabriel.com"));
    }

    @Test
    void testFindStudentById() throws Exception {
        Student student = new Student();
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");
        studentService.save(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@guilherme.com"));
    }

    @Test
    void testFindNonexistentStudentById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student student = new Student();
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");
        student = studentService.save(student);
        student.setEmail("gabriel@email.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/students/1")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@email.com"));
    }

    @Test
    void testUpdateNonexistentStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/students/1")
                        .content(objectMapper.writeValueAsString(student))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testDeleteStudent() throws Exception {
        Student student = new Student();
        student.setName("Gabriel");
        student.setEmail("gabriel@guilherme.com");
        studentService.save(student);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Gabriel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("gabriel@guilherme.com"));
    }

    @Test
    void testDeleteNonexistentStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
