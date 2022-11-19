package org.example.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.example.model.Faculty;
import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
   private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
                return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        return studentRepository.findById(id).get();
    }

    public Student editStudent( Student student) {

        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
         studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        return studentRepository.findAllByAge(age);
    }
    public Collection<Student> findByAgeBetween(int minAge,int maxAge) {
        return studentRepository.findByAgeBetween(minAge,maxAge);
    }
    public Faculty findFacultyByStudent(long id) {
        return findStudent(id).getFaculty();
    }

    public Integer getStudentsQuantity() {
        return  studentRepository.getStudentsQuantity();
    }
    public Integer getStudentsByAgeAvery() {
        return  studentRepository.getStudentsByAgeAvery();
    }
    public List<Student> getStudentsLastFive() {
        int lastFive = studentRepository.getStudentsQuantity() - 5;
        PageRequest pageRequest = PageRequest.of(lastFive,5);
        return  studentRepository.findAll(pageRequest).getContent();
    }

}
