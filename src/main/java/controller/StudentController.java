package controller;

import jakarta.persistence.Id;
import model.Faculty;
import model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.StudentService;

import java.text.CollationKey;
import java.util.Collection;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping
    public Student get(@RequestParam long id) {
        return service.get(id);
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }

    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return service.delete(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @GetMapping("/byAge")
    public Collection<Student> getByAgeBetween(@RequestParam(required = false) Integer min, @RequestParam(required = false) Integer max) {
        if (min != null && max != null) {
            return service.getByAgeBetween(min, max);
        }
        return service.getAll();
    }
    @GetMapping("faculty")
    public Faculty getStudetFaculty(@RequestParam long studentId) {
        return service.get(studentId).getFaculty();
    }

    @GetMapping("/count")
    public int getCountStudent(){
        return service.getStudentCount();
    }
    @GetMapping("/avg-age")
    public double getAvgAge(){
        return service.getAvgAge();
    }
    @GetMapping("/last")
    public Collection<Student> getLastStudent(){
        return service.getLastFive();
    }

    @GetMapping("/medium-age-stream")
    public double getMediumAgeStream() {
        return service.gegMediumAge();
    }
    @GetMapping("/nameStartsA")
    public Collection<String> getNameStudentStartsA(){
        return service.getNameStartsWithA();
    }

}
