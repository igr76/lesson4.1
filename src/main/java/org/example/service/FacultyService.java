package org.example.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.example.exeption.AvatarNotFoundExeption;
import org.example.exeption.FacultyNotFoundExeption;
import org.example.model.Faculty;
import org.example.model.Student;
import org.example.repository.FacultyRepository;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
   private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(nameOrColor,nameOrColor);
    }

    public Collection<Student> findStudentByFaculty(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new
                FacultyNotFoundExeption(id)).getStudents().stream().collect(Collectors.toList());
    }
}
