package org.example.service;

import org.example.component.RecordMapper;
import org.example.exeption.AvatarNotFoundExeption;
import org.example.exeption.StudentNotFoundExeption;
import org.example.model.Avatar;
import org.example.model.Student;
import org.example.record.AvatarRecord;
import org.example.repository.AvatarRepository;
import org.example.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;


@Service
public class AvatarService {
    private static final Logger LOG = LoggerFactory.getLogger(AvatarService.class);


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
        LOG.debug("Method creat was invoked");
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
        LOG.debug("Method readFromFs was invoked");
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> {
            LOG.error("Avatar with id = {} not found",id);
            return new AvatarNotFoundExeption(id);
        });
        return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())),avatar.getMediaTipe());
    }
    public Pair<byte[], String> readFromDb(long id) {
        LOG.debug("Method readFromDb was invoked");
        Avatar avatar = avatarRepository.findById(id).orElseThrow(() -> {
            LOG.error("Avatar with id = {} not found",id);
            return new AvatarNotFoundExeption(id);
        });
        return Pair.of(avatar.getData(),avatar.getMediaTipe());
    }

    public List<Avatar> getAll() {
        return avatarRepository.findAll();
    }

    public List<Avatar> fingByPagination(int page, int size) {
        LOG.debug("Method fingByPagination was invoked");
        return avatarRepository.findAll(PageRequest.of(page, size)).get()
                .collect(Collectors.toList());
    }
}
