package controller;

import model.Faculty;
import model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import service.FacultyService;

import java.util.Collection;
import java.util.List;
@Service
@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService service;
    public FacultyController(FacultyService service) {
        this.service = service;
    }
    @GetMapping
    public Faculty get (@RequestParam long id) {
        return service.add(faculty);
    }
    @PostMapping
    public Faculty add(@RequestBody Faculty faculty) {
        return service.add(faculty);
    }
    @DeleteMapping
    public boolean delete(@RequestParam long id) {
        return service.delete(id);
    }
    @GetMapping
    public Faculty update(@RequestBody Faculty faculty) {
        return service.update(faculty);
    }
    @GetMapping("byColorAndName")
    public Collection<Faculty> getByColor(@RequestParam(required = false) String color, @RequestParam(required = false) String name) {
        if (color == null && name == null){
            return service.getAll();
        }
        return service.getBycolorOrName(color, name);
    }
    @GetMapping("students")
    public Faculty getStudetFaculty(@RequestParam long facultyId) {
        return (Faculty) service.get(facultyId).getStudents();
    }
    @GetMapping ("students")
    public List<Student> getStudentsFaculty(@RequestParam long facultyId) {
        return service.get(facultyId).getStudents();
    }

}
