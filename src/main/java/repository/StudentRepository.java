package repository;

import model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAge(int age);
    Collection<Student> findAllByAgeBetween(int min, int max);

    @Query(value = "select count(*) from Student", nativeQuery = true)
    int countStudents();


    @Query(value = "select avg(age) from Student", nativeQuery = true)
    double avgAge();
    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFive();

}
