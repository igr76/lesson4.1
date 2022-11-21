package org.example.controller;

import org.example.model.Faculty;
import org.example.model.Student;
import org.example.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Stream;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService ;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@PathVariable Long id,@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public Collection<Student> findByAge(@RequestParam int age) {
        return studentService.findByAge(age);
    }
    @GetMapping
    public Collection<Student> findByAgeBetween(@RequestParam("min") int minAge,@RequestParam("max") int maxAge) {
        return studentService.findByAgeBetween(minAge,maxAge);
    }
    @GetMapping("/lastFive")
    public Collection<Student> getStudentsLastFive() {
        return studentService.getStudentsLastFive();
    }
    @GetMapping("/quantity")
    public Integer getStudentsQuantity() {
        return studentService.getStudentsQuantity();
    }
    @GetMapping("/ageAvery")
    public Integer getStudentsByAgeAvery() {
        return studentService.getStudentsByAgeAvery();
    }

    @GetMapping("/{id}/faculty")
    public Faculty findFacultyByStudent(@PathVariable Long id) {
        return studentService.findFacultyByStudent(id);
    }

    @GetMapping("/findStudentNamrThisStartedThisA")
    public Stream<String> findStudentNamrThisStartedThisA() {
        return studentService.findStudentNamrThisStartedThisA();
    }

    @GetMapping("/findStudentAverageAge")
    public double findStudentAverageAge() {
        return studentService.findStudentAverageAge();
    }


}
