package org.example.exeption;

public class FacultyNotFoundExeption extends RuntimeException {
    private final  long id;
    public FacultyNotFoundExeption(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
