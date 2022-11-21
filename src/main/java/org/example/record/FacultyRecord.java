package org.example.record;

import javax.validation.constraints.NotBlank;

public class FacultyRecord {
    private long id;
    @NotBlank(message = "Имя факультета должно быть заполненно")
    private String name;
    @NotBlank(message = "Цвет факультета должно быть заполненно")
    private String color;

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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
