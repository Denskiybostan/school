package service;

import exceptions.RecordNotFoundException;
import model.Faculty;
import model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import repository.StudentRepository;

import java.util.Collection;
@Service
public class StudentService {

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    private final StudentRepository studentRepository;
    public Student add (Student student) {
        return studentRepository.save(student);
    }
    public Student get (long id) {
        return studentRepository.findById(id).orElseThrow(RecordNotFoundException::new);
    }
    public boolean delete (long id){
        return studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
    public Student update (Student student) {
            return studentRepository.findById(student.getId())
                    .map(entity -> studentRepository.save(student))
                    .orElse(null);
        }
    public Collection<Student> getByAgeBetween(int max, int min) {
        return studentRepository.findAllByAgeBetween(min, max);
    }
    public Collection<Student> getByAgeStudent(int age) {
        return studentRepository.findByAge(age);
    }

    public Collection<Student> getAll() {
        return studentRepository.findAll();
    }
    public Faculty getFacultyByStudent(Long id) {
        return studentRepository(id).getFaculty();
    }

    public int getStudentCount() {
        return studentRepository.countStudents();
    }
    public double getAvgAge() {
        return studentRepository.avgAge();
    }
    public Collection<Student> getLastFive() {
        return  studentRepository.getLastFive();
    }


}
