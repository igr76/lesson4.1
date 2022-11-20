package org.example.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.example.model.Faculty;
import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentService {
   private final StudentRepository studentRepository;
    private static final Logger LOG =  LoggerFactory.getLogger(StudentService.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    public Student addStudent(Student student) {
        LOG.debug("Method addStudent was invoked");
                return studentRepository.save(student);
    }

    public Student findStudent(long id) {
        LOG.debug("Method findStudent was invoked");
        return studentRepository.findById(id).get();
    }

    public Student editStudent( Student student) {
        LOG.debug("Method editStudent was invoked");
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        LOG.debug("Method deleteStudent was invoked");
         studentRepository.deleteById(id);
    }

    public Collection<Student> findByAge(int age) {
        LOG.debug("Method findByAge was invoked");
        return studentRepository.findAllByAge(age);
    }
    public Collection<Student> findByAgeBetween(int minAge,int maxAge) {
        LOG.debug("Method findByAgeBetween was invoked");
        return studentRepository.findByAgeBetween(minAge,maxAge);
    }
    public Faculty findFacultyByStudent(long id) {
        LOG.debug("Method findFacultyByStudent was invoked");
        return findStudent(id).getFaculty();
    }

    public Integer getStudentsQuantity() {
        LOG.debug("Method getStudentsQuantity was invoked");
        return  studentRepository.getStudentsQuantity();
    }
    public Integer getStudentsByAgeAvery() {
        LOG.debug("Method getStudentsByAgeAvery was invoked");
        return  studentRepository.getStudentsByAgeAvery();
    }
    public List<Student> getStudentsLastFive() {
        LOG.debug("Method getStudentsLastFive was invoked");
        int lastFive = studentRepository.getStudentsQuantity() - 5;
        PageRequest pageRequest = PageRequest.of(lastFive,5);
        return  studentRepository.findAll(pageRequest).getContent();
    }

}
