package org.example.record;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class StudentRecord {
    private long id;
    @NotBlank(message = "Имя студента должно быть заполненно")
    private String name;
    @Min(value = 17, message = "Минимальный возраст 17лет")
    @Max(value = 25,message = "Максимальый возраст 25лет")
    private int age;

    private FacultyRecord faculy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public FacultyRecord getFaculy() {
        return faculy;
    }

    public void setFaculy(FacultyRecord faculy) {
        this.faculy = faculy;
    }
}
