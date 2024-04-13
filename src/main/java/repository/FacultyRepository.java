package repository;

import model.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface FacultyRepository  extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColorAndNameIgnoreCase(String color, String name);
    Collection<Faculty> findAllByColorOrNameIgnoreCase(String color, String name);
}
