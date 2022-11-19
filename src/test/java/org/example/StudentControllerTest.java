package org.example;

import com.github.javafaker.Faker;
import org.example.model.Faculty;
import org.example.model.Student;
import org.example.record.FacultyRecord;
import org.example.record.StudentRecord;
import org.example.repository.FacultyRepository;
import org.example.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest(webEnironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private FacultyRepository facultyRepository;
    private final Faker faker = new Faker();

    @AfterEach
    public void afterEach() {
        studentRepository.deleteAll();
        facultyRepository.deleteAll();
    }

    @Test
    public void greateTest() { addStudent(generateStudent(addFaculty(generateFaculty())));

    }

    private FacultyRecord addFaculty(FacultyRecord facultyRecord) {
        ResponseEntity<FacultyRecord> facultyRecordResponseEntity = testRestTemplate.postForEntity("http:/localhost:"+port+"/faculty",facultyRecord,FacultyRecord.class);
        assertThat(facultyRecordResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(facultyRecordResponseEntity.getBody()).isNotNull();
        assertThat(facultyRecordResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(facultyRecord);
        assertThat(facultyRecordResponseEntity.getBody().getId()).isNotNull();
        return facultyRecordResponseEntity.getBody();
    }
    private StudentRecord addStudent(StudentRecord studentRecord) {
        ResponseEntity<StudentRecord> studentRecordResponseEntity = testRestTemplate.postForEntity("http:/localhost:"+port+"/faculty",studentRecord,StudentRecord.class);
        assertThat(studentRecordResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentRecordResponseEntity.getBody()).isNotNull();
        assertThat(studentRecordResponseEntity.getBody()).usingRecursiveComparison().ignoringFields("id").isEqualTo(studentRecord);
        assertThat(studentRecordResponseEntity.getBody().getId()).isNotNull();
        return studentRecordResponseEntity.getBody();
    }

    @Test
    public void putTest() {
        FacultyRecord facultyRecord1 = addFaculty(generateFaculty());
        FacultyRecord facultyRecord2 = addFaculty(generateFaculty());
        StudentRecord studentRecord = addStudent(generateStudent(facultyRecord1));

        ResponseEntity<StudentRecord> getForEntityResponse = testRestTemplate.getForEntity();
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody()).isNotNull();
        assertThat(getForEntityResponse.getBody()).usingRecursiveComparison().isEqualTo(studentRecord);
        assertThat(getForEntityResponse.getBody().getFaculy()).usingRecursiveComparison().isEqualTo(facultyRecord1);
        studentRecord.setFaculy(facultyRecord2);

        ResponseEntity<StudentRecord> recordResponseEntity = testRestTemplate
                .exchange("http:/localhost:"+port+"/faculty" + studentRecord.getId(),
                       HttpMethod.PUT, new HttpEntity<>(studentRecord), StudentRecord.class);

        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(recordResponseEntity.getBody()).isNotNull();
        assertThat(recordResponseEntity.getBody()).usingRecursiveComparison().isEqualTo(studentRecord);
        assertThat(recordResponseEntity.getBody().getFaculy()).usingRecursiveComparison().isEqualTo(facultyRecord1);

    }

    @Test
    public void findByAgeBetweenTest() {
        List<FacultyRecord> facultyRecords = Stream.generate(this::generateFaculty)
                .limit(5)
                .map(this::addFaculty)
                .toList();
        List<StudentRecord> studentRecords = Stream.generate(() -> generateStudent(facultyRecords.get(faker.random().nextInt(facultyRecords))))
                .limit(50)
                .map(this::addStudent)
                .toList();
        int minAge = 14;
        int maxAge = 17;

        List<StudentRecord>  expectedStudents = studentRecords.stream()
                .filter(studentRecord -> studentRecord.getAge() >= minAge && studentRecord.getAge() <= maxAge)
                .toList();

        ResponseEntity<StudentRecord> getForEntityResponse = testRestTemplate
                .exchange("http:/localhost:" + port + "/students?minAge={minAge}&maxAge={maxAge}", HttpMethod.GET, HttpEntity.EMPTY,
                        new ParameterizedTypeReference<>() {
                        },minAge,maxAge);
        assertThat(getForEntityResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getForEntityResponse.getBody())
                .hasSize(expectedStudents.size())
                .usingResursiveFieldElementComparator()
                .containsExactlyInAnyOrderElementOf(expectedStudents);
    }

    public Student toEntity(StudentRecord studentRecord) {
        Student student = new Student();
        student.setName(studentRecord.getName());
        student.setAge(studentRecord.getAge());
        return student;
    }

    public Faculty toEntity(FacultyRecord facultyRecord) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyRecord.getName());
        faculty.setColor(facultyRecord.getColor());
        return faculty;
    }

    public FacultyRecord generateFaculty(){
        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setName(faker.harryPotter().house());
        facultyRecord.setColor(faker.color().name());
        return facultyRecord;
    }
    public StudentRecord generateStudent(FacultyRecord facultyRecord) {
        StudentRecord studentRecord = new StudentRecord();
        studentRecord.setName(faker.harryPotter().character());
        studentRecord.setAge(faker.random().nextInt(11,18));
        if (facultyRecord != null) {
            studentRecord.setFaculy(facultyRecord);
        }
        return studentRecord;

    }


}
