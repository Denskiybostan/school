package service;

import exceptions.RecordNotFoundException;
import model.Faculty;
import model.Student;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.StudentRepository;

import java.util.Collection;
import java.util.logging.Logger;

@Service
public class StudentService {
    private final static Logger logger = (Logger) LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    private final StudentRepository studentRepository;
    public Student add (Student student) {
        logger.info("Was invoked method create student");

        return studentRepository.save(student);
    }
    public Student get (long id) {
        logger.info("getting student" + id);
        return studentRepository.findById(id).orElseThrow(RecordNotFoundException::new);}

    public boolean delete (long id){
        return studentRepository.findById(id)
                .map(entity -> {
                    studentRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
    public Student update (Student student) {
        logger.info("student updating");
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
