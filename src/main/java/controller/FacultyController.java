package controller;

import model.Faculty;
import model.Student;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
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
        if (StringUtils.isEmpty(color) && !StringUtils.isEmpty(name)){
            return service.getByColorAndName(color, name);
        }
        return service.getAll();
    }
    @GetMapping("students")
    public Faculty getStudetFaculty(@RequestParam long facultyId) {
        return service.get(facultyId).getStudents();
    }
    @GetMapping ("students")
    public List<Student> getStudentsFaculty(@RequestParam long facultyId) {
        return service.get(facultyId).getStudents();
    }

}
