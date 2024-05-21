package controller;

import model.Faculty;
import model.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import service.FacultyService;

import java.util.Collection;
import java.util.List;
@Service
@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;
    public FacultyController(FacultyService service) {
        this.facultyService = service;
    }
    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return facultyService.add(faculty);
    }
    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return facultyService.delete(id);
    }
    @GetMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return facultyService.update(faculty);
    }
    @GetMapping("byColorAndName")
    public Collection<Faculty> getByColor(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        if (color == null && name == null){
            return facultyService.getAll();
        }
        return facultyService.getByColorAndName(color, name);
    }
    @GetMapping("students")
    public Faculty getStudetFaculty(@RequestParam long facultyId) {
        return (Faculty)facultyService.get(facultyId).getStudents();
    }
    @GetMapping ("students")
    public List<Faculty> getStudentsFaculty(@RequestParam long facultyId) {
        return facultyService.get(facultyId).getStudents();
    }
    @GetMapping("/longerName")
    public String getLongerName() {
        return facultyService.getLongerName();
    }


}
