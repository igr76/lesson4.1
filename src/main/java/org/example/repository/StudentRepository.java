package org.example.repository;

import org.example.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findAllByAge(int age);
    Collection<Student> findByAgeBetween(int min,int max);
    @Query(value = "SELECT COUNT(*) FROM students ", nativeQuery = true)
    Integer getStudentsQuantity();
    @Query(value = "SELECT AVG(age) as age FROM students ", nativeQuery = true)
    Integer getStudentsByAgeAvery();

    List<Student> findAll();

    @Query(value = "SELECT * FROM students ORDER BY id DESC", nativeQuery = true)
    public void desc();
    @Query(value = "SELECT * FROM students ORDER BY id LIMIT 5", nativeQuery = true)
    List<Student> getStudentsLastFive();
}
