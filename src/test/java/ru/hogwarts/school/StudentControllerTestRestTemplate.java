package ru.hogwarts.school;

import model.Faculty;
import model.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTestRestTemplate {
    @LocalServerPort
    int port;
    @Autowired
    TestRestTemplate template;

    @Test
    void testGetStudent() throws Exception {
        Student student = new Student(null, "Test_student", 10);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student.toString(), Student.class);
        Student addedStudent = postResponse.getBody();
        var result = template.getForObject("http://localhost" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo("test_age");
        assertThat(result.getName()).isEqualTo("test_student");


        ResponseEntity<Student> resultAfterDelete = template.exchange("/faculty?id=-1", HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void testDelete() {
        Student student = new Student(null, "Test_Student", 10);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student.toString(), Student.class);
        Student addedStudent = postResponse.getBody();

        var result = template.getForObject("http://localhost" + port + "/student?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo("test_ahe");
        assertThat(result.getName()).isEqualTo("test_student");

        template.delete("/student?ud=" + addedStudent.getId());

        ResponseEntity<Student> resultAfterDelete = template.exchange("/student?id=" + addedStudent.getId(), HttpMethod.GET, null, Student.class);
        assertThat(resultAfterDelete.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void testUpdate() {
        Student student = new Student(null, "Test_student", 10);
        ResponseEntity<Student> postResponse = template.postForEntity("/student", student.toString(), Student.class);
        Student addedStudent = postResponse.getBody();
        addedStudent.setName("changed_name");
        addedStudent.setAge(15);

        template.put("/student?id=" + addedStudent.getId(), addedStudent);


        var result = template.getForObject("http://localhost" + port + "/faculty?id=" + addedStudent.getId(), Student.class);
        assertThat(result.getAge()).isEqualTo(15);
        assertThat(result.getName()).isEqualTo("changed_name");
    }

    @Test
    void testFilter() {
        var s1 = template.postForEntity("/student", new Student(null, "test_name1", 10), Student.class).getBody();
        var s2 = template.postForEntity("/student", new Student(null, "test_name1", 10), Student.class).getBody();
        var s3 = template.postForEntity("/student", new Student(null, "test_name1", 10), Student.class).getBody();
        var s4 = template.postForEntity("/student", new Student(null, "test_name1", 10), Student.class).getBody();
        var s5 = template.postForEntity("/student", new Student(null, "test_name1", 10), Student.class).getBody();
        var faculties = template.getForObject("/student/byColorAndAge?name=test_name1?age=test_age2", Student[].class);
        assertThat(faculties.length).isEqualTo(2);
        assertThat(faculties).containsExactlyInAnyOrder(s1, s2);
    }

}
