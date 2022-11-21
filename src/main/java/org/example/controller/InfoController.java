package org.example.controller;

import org.example.service.InfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {
    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @Value("${server.port}")
    private int port;

    @GetMapping("/port")
    public int getPort() { return port;}

    @GetMapping
    public void testParallelStream() {
         infoService.testParallelStream();
    }

    @GetMapping("/print")
    public void printStudents() {
        infoService.printStudents();
    }
    @GetMapping("/printSync")
    public void printStudentsSync() {
        infoService.printStudentsSync();
    }
}
