package pl.pumbakos.japwebservice.web.service.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.web.service.resource.EndPoint;

import java.io.IOException;
import java.util.List;

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
        return songService.uploadFiles(multipartFiles);
    }

    @GetMapping(EndPoint.DOWNLOAD + EndPoint.FILENAME)
    public ResponseEntity<Resource> downloadFiles(@PathVariable("filename") String filename) throws IOException {
        return songService.downloadFiles(filename);
    }


    @GetMapping(EndPoint.DOWNLOAD + EndPoint.FILENAME + EndPoint.SIZE)
    public long getFileSize(@PathVariable("filename") String filename) {
        return songService.getFileSize(filename);
    }

    @GetMapping(EndPoint.DOWNLOAD + EndPoint.TITLES)
    public List<String> downloadTitles(){
        return repository.findAllByTitle();
    }
}
