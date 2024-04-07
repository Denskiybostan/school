package service;

import exceptions.RecordNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import model.Faculty;
import repository.FacultyRepository;

import java.util.Collection;

public class FacultyService {
    private final FacultyRepository repository;


    public FacultyService(FacultyRepository repository) {
        this.repository = repository;
    }

    public Faculty add(Faculty faculty) {
        return repository.save(faculty);
    }

    public Faculty get(long id) {
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
        return repository.findById(faculty.getId())
                .map(entity -> repository.save(faculty))
                .orElse(null);
    }

    public Collection<Faculty> getByColor(String color) {
        return repository.findAllByColor(color);
    }
}
