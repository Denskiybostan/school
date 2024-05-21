package service;

import exceptions.RecordNotFoundException;
import model.Faculty;
import model.Student;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.StudentRepository;

import java.util.Collection;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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

    public Collection<String> getNameStartsWithA() {
        return studentRepository.findAll().stream()
                .map(Student::getName)
                .map(String::toUpperCase)
                .filter(name -> name.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }

    public double gegMediumAge() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(0);
    }

    public void printParallel() {
        var students = studentRepository.findAll();
        logger.info(students.get(0).toString());
        logger.info(students.get(1).toString());
        new  Thread(() -> {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            logger.info(students.get(2).toString());
            logger.info(students.get(3).toString());
        }).start();
        new  Thread(() -> {
            logger.info(students.get(4).toString());
            logger.info(students.get(5).toString());
        }).start();
    }
    public void printSync() {
        var students = studentRepository.findAll();
        print(students.get(0));
        print(students.get(1));
        new  Thread(() -> {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                throw new RuntimeException(e);
            }
            print(students.get(2));
            print(students.get(3).toString());
        }).start();
        new  Thread(() -> {
            print(students.get(4));
            print(students.get(5));
        }).start();

    }
    private  void print(Object o) {
        synchronized (this) {
            logger.info(o.toString());
        }
    }
}
