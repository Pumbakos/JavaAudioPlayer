package pl.pumbakos.japwebservice.service.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.service.resource.EndPoint;
import pl.pumbakos.japwebservice.service.resource.Status;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(EndPoint.FILE)
public class SongController {
    private final SongService songService;
    private final SongRepository repository;

    @Autowired
    public SongController(SongService songService, SongRepository repository) {
        this.songService = songService;
        this.repository = repository;
    }

    @PostMapping(EndPoint.UPLOAD)
    public ResponseEntity uploadFiles(@RequestParam("files") List<MultipartFile> multipartFiles) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFiles.get(0).getOriginalFilename()));
        if (multipartFiles.size() == 1 && filename.isBlank()) {
            return ResponseEntity.of(Optional.of(Collections.singletonList(Status.Message.NO_CONTENT)));
        }
        return songService.uploadFiles(multipartFiles);
    }

    @GetMapping(EndPoint.DOWNLOAD + EndPoint.FILENAME)
    public ResponseEntity<Object> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        return songService.downloadFiles(filename);
    }


    @GetMapping(EndPoint.DOWNLOAD + EndPoint.FILENAME + EndPoint.SIZE)
    public ResponseEntity<Long> getFileSize(@PathVariable("filename") String filename) {
        return songService.getFileSize(filename);
    }

    @GetMapping(EndPoint.DOWNLOAD + EndPoint.TITLES)
    public List<String> downloadTitles() {
        return repository.findAllByTitle();
    }
}
