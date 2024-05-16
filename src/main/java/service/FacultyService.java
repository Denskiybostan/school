package service;

import exceptions.RecordNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import model.Faculty;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import repository.FacultyRepository;

import java.util.Collection;
import java.util.logging.Logger;

@Service
public class FacultyService {
    private final static Logger logger = (Logger) LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository repository;


    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        logger.info("Was invoked method create faculty");
        return repository.save(faculty);
    }

    public Faculty get(long id) {
        logger.info("faculty is getting");
        return repository.findById(id).orElseThrow(RecordNotFoundException::new);

    }

    public boolean delete(long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return true;
                })
                .orElse(false);
    }
    public Faculty update(Faculty faculty) {
        logger.info("faculty is updating");
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public Collection<Faculty> getByColorAndName(String color, String name) {
        logger.info("color and name is getting");
        return repository.findAllByColorOrNameIgnoreCase(color, name);
    }

    public Collection<Faculty> getAll() {
        return repository.findAll();
    }
}
