package service;

import model.Avatar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import repository.AvatarRepository;
import repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.List;

@Service
public class AvatarService {
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path avatarsDir;
    public  AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, @Value("${avatars.dir}") Path avatarsDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
    }
    public void save(Long studentId, MultipartFile file) throws IOException {
        Files.createDirectories(avatarsDir);
        var index = file.getName().lastIndexOf('.');
        var extension = file.getName().substring(index);
        Path filePath = avatarsDir.resolve(studentId + '.' + extension);
        try (var in = file.getInputStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        Avatar avatar = avatarRepository.findAllByStudentId(studentId).orElse(new Avatar());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData();
        avatar.setStudent(studentRepository.getReferenceById(studentId));
        avatar.setFilePath(filePath.toString());
        avatarRepository.save(avatar);
    }

    public Avatar getFromDB(Long id) {
        return avatarRepository.findById(id).orElse(new Avatar());
    }
    public List<Avatar> getPage(int pageNumber, int pageSize) {
        return avatarRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }
}

