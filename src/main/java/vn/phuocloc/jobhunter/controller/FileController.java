package vn.phuocloc.jobhunter.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import vn.phuocloc.jobhunter.service.FileService;

@RestController
@RequestMapping("/api/v1")
public class FileController {

    private final FileService fileService;

    @Value("${phuocloc.upload-file.base-uri}")
    private String baseURI;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/files")
    public String upload(@RequestParam("file") MultipartFile file, @RequestParam("folder") String folder)
            throws URISyntaxException, IOException {

        // validate

        // crate directory if not exists
        this.fileService.createDirectory(baseURI + folder);

        // store file

        this.fileService.store(file, folder);
        return file.getOriginalFilename() + folder;

    }

}
