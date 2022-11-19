package org.example.service;

import org.example.component.RecordMapper;
import org.example.exeption.AvatarNotFoundExeption;
import org.example.exeption.StudentNotFoundExeption;
import org.example.model.Avatar;
import org.example.model.Student;
import org.example.record.AvatarRecord;
import org.example.repository.AvatarRepository;
import org.example.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;


@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final RecordMapper recordMapper;

    @Value("${application.avatar.store}")
    private String avatarDir;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         RecordMapper recordMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.recordMapper = recordMapper;
    }

    public AvatarRecord creat(MultipartFile avatar1, long studentId) throws IOException {
        Student student = studentRepository.
                findById(studentId).orElseThrow(() -> new StudentNotFoundExeption(studentId));
        byte[] data = avatar1.getBytes();

        String extension = Optional.ofNullable(avatar1.getOriginalFilename())
                .map(fileName -> fileName.substring(avatar1.getOriginalFilename().lastIndexOf('.')))
                        .orElse("");
        Path path = Paths.get(avatarDir).resolve(studentId+extension);
        Files.write(path,data);
        Avatar avatar = new Avatar();
        avatar.setData(data);
        avatar.setFileSize(data.length);
        avatar.setMediaTipe(avatar1.getContentType());
        avatar.setFilePath(path.toString());
        return recordMapper.toRecord(avatarRepository.save(avatar));
    }

    public Pair<byte[], String> readFromFs(long id) throws IOException {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundExeption(id));
        return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())),avatar.getMediaTipe());
    }
    public Pair<byte[], String> readFromDb(long id) {
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> new AvatarNotFoundExeption(id));
        return Pair.of(avatar.getData(),avatar.getMediaTipe());
    }

    public List<Avatar> getAll() {
        return avatarRepository.findAll();
    }
}
