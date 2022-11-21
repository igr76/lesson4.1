package org.example.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
public class Avatar {

    @javax.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String filePath;
    private long fileSize;
    private byte[] data;
    private String mediaTipe;

    @OneToOne
    private Student student;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getMediaTipe() {
        return mediaTipe;
    }

    public void setMediaTipe(String mediaTipe) {
        this.mediaTipe = mediaTipe;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
