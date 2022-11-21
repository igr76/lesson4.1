package org.example.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import org.example.exeption.AvatarNotFoundExeption;
import org.example.exeption.FacultyNotFoundExeption;
import org.example.model.Faculty;
import org.example.model.Student;
import org.example.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class FacultyService {
    private static final Logger LOG =  LoggerFactory.getLogger(FacultyService.class);
   private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        LOG.debug("Method findFaculty was invoked");
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        LOG.debug("Method editFaculty was invoked");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        LOG.debug("Method deleteFaculty was invoked");
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        LOG.debug("Method findByColor was invoked");
        return facultyRepository.findAllByColor(color);
    }

    public Collection<Faculty> findByNameOrColor(String nameOrColor) {
        LOG.debug("Method findByNameOrColor was invoked");
        return facultyRepository.findByColorIgnoreCaseOrNameIgnoreCase(nameOrColor,nameOrColor);
    }

    public Collection<Student> findStudentByFaculty(Long id) {
        LOG.debug("Method findStudentByFaculty was invoked");
        return facultyRepository.findById(id).orElseThrow(() -> new
                FacultyNotFoundExeption(id)).getStudents().stream().collect(Collectors.toList());
    }

    public String findTheLongestFacultyName() {
        LOG.debug("Method findTheLongestFacultyName was invoked");
        return facultyRepository.findAll().stream()
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length))
                .orElseThrow();
    }
}
