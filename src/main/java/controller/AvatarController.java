package controller;

import jakarta.servlet.http.HttpServletResponse;

import model.Avatar;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/avatar/student")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }


    @PostMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public  ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException {
        if (avatar.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().body("File is so big");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/{id}/preview")
    public ResponseEntity<byte[]> downLoadAvatar(@PathVariable Long id) {
        Avatar avatar = avatarService.findAvatar(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping("{id}")
    public void donwLoadAvatar(@PathVariable Long id, HttpServletResponse response) throws  IOException {
        Avatar avatar = avatarService.findAvatar(id);
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
            OutputStream os = response.getOutputStream();){
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength(int) avatar.getFileSize());
            is.transferTo(os);
        }
    }


    @GetMapping()
    public List<Avatar> getByPage(@RequestParam("page") int page,
                                  @RequestParam("size") int size) {
        return avatarService.getPage(page,size);
    }
}

