package org.example.controller;

import org.example.model.Avatar;
import org.example.record.AvatarRecord;
import org.example.service.AvatarService;
import org.springframework.data.util.Pair;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatars")
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping
    public AvatarRecord create(@RequestParam MultipartFile avatar,
                               @RequestParam long studentId) throws IOException {
        return avatarService.creat(avatar,studentId);

    }

    @GetMapping("{id}/from-fs")
    public ResponseEntity<byte[]> readFromFs(@PathVariable long id) throws IOException {
        Pair<byte[],String> pair = avatarService.readFromFs(id);
        return read(pair);
    }
    @GetMapping("{id}/from-db")
    public ResponseEntity<byte[]> readFromDb(@PathVariable long id) {
        Pair<byte[],String> pair = avatarService.readFromDb(id);
        return read(pair);
    }

    @GetMapping("/listPair")
    public ResponseEntity<List<Avatar>> listPair() {
       List<Avatar>  avatars= avatarService.getAll();
       return ResponseEntity.ok(avatars);
    }

    private ResponseEntity<byte[]> read(Pair<byte[], String> pair) {
        return ResponseEntity.ok()
                .contentLength(pair.getFirst().length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(pair.getFirst());
    }
}
