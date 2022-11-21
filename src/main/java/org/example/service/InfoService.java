package org.example.service;

import org.example.model.Student;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Stream;

@Service
public class InfoService {
    private final StudentRepository studentRepository;

    public InfoService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private static final Logger LOG =  LoggerFactory.getLogger(FacultyService.class);


    public void testParallelStream() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("plain stream");
        Stream.iterate(1L, a -> a + 1)
                .limit(10_000L)
                .reduce(0L, (a,b) ->{
                    long s = 0;
                    for (int i = 0; i < a+b; i++) {
                        s+=1;
                    }
                    return s;
                });
        stopWatch.stop();
        stopWatch.start("parallel stream");
        long sum = Stream.iterate(1L, a -> a + 1)
                .limit(10_000L)
                .parallel()
                .reduce(0L, (a,b) ->{
                    long s = 0;
                    for (int i = 0; i < a+b; i++) {
                        s+=1;
                    }
                    return s;
                });
        stopWatch.stop();
        LOG.info("Calculated value is {}; {}", sum, stopWatch.prettyPrint());

    }

    public void printStudents() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0,6)).getContent();

        printStudents(students.subList(0,2));
        new Thread(() -> printStudents(students.subList(2,4))).start();
        new Thread(() -> printStudents(students.subList(4,6))).start();

    }

    private void printStudents(List<Student> students) {
        for (Student student :
                students) {
            LOG.info(student.getName());
        }
    }

    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll(PageRequest.of(0,6)).getContent();

        printStudentsSync(students.subList(0,2));
        new Thread(() -> printStudentsSync(students.subList(2,4))).start();
        new Thread(() -> printStudentsSync(students.subList(4,6))).start();

    }

    private synchronized void printStudentsSync(List<Student> students) {
        for (Student student :
                students) {
            LOG.info(student.getName());
        }
    }
}
