package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.FacultyController;
import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import repository.FacultyRepository;
import repository.StudentRepository;
import service.FacultyService;
import service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.checkerframework.checker.units.UnitsTools.min;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;



@WebMvcTest
public class StudentControllerTestMvc {
    @Autowired
    MockMvc mvc;
    @MockBean
    StudentRepository studentRepository;
    @SpyBean
    StudentService studentService;
    @InjectMocks
    FacultyController facultyController;

    @Test
    void testGet() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 10)));
        mvc.perform(MockMvcRequestBuilders.get("/faculty?id=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name$").value("test_student_mvc"))
                .andExpect(jsonPath("$.name$").value("test_age_mvc"));
    }

    @Test
    void testUpdate() throws Exception {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student(1L, "test_student_mvc", 10)));
        Student student = new Student(1L, "update_name", 10);
        when(studentRepository.save(any(Student.class))).thenReturn(student);
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(student));
        mvc.perform(MockMvcRequestBuilders.put("/faculty?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name$").value("test_student_mvc"))
                .andExpect(jsonPath("$.name$").value("updated_age"));
    }

    @Test
    void testDelete() throws Exception {
        when(studentRepository.findById(2L)).thenReturn(Optional.of(new Student(1l, "test_student_mvc", 10)));

        mvc.perform(MockMvcRequestBuilders.delete("/student?id=1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
        when(studentRepository.findById(333L)).thenReturn(Optional.empty());
        mvc.perform(MockMvcRequestBuilders.delete("/student?id=333"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testAdd() throws Exception {
        when(studentRepository.save(any(Student.class))).then((invocationOnMock -> {
            Student input = invocationOnMock.getArgument(0, Student.class);
            Student f = new Student();
            f.setId(100L);
            f.setAge(input.getAge());
            f.setName(input.getName());
            return f;

        }));
        Student student = new Student(null, "two", 20);

        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(student));
        mvc.perform(MockMvcRequestBuilders.post("/student?id=1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(100L))
                .andExpect(jsonPath("$.name$").value("two"))
                .andExpect(jsonPath("$.age$").value(10));
    }

    void testAgeAndName() throws Exception {
        when(studentRepository.findAllByAgeBetween(anyInt(), anyInt())
                .iterator(),List.of(
                        new Student(1L, "name1", 10),
                        new Student(2L, "name2", 20)),
                        mvc.perform(MockMvcRequestBuilders.get("/faculty?id/byColorAndName?name=two?color=three"))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("[0].name").value("name1"))
                        .andExpect(jsonPath("[0].age").value("age1"))
                        .andExpect(jsonPath("[1].name").value("name2"))
                        .andExpect(jsonPath("[1].age").value("age2"));
    }
    @Test
    void testGetStudents() throws  Exception {
        Student s = new Student(1L, "s1", 12);
        s.setFaculty((Faculty) List.of(new Faculty(1L, "s1", "white").getStudents()));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(s));

        mvc.perform(MockMvcRequestBuilders.get("/faculty/students?facultyId=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].name").value("s1"))
                .andExpect(jsonPath("[0].age").value(10));
    }
}

