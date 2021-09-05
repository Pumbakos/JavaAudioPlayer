package pl.pumbakos.japwebservice.songmodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.songmodule.models.Song;
import pl.pumbakos.japwebservice.songmodule.services.SongService;

import java.util.List;
import java.util.Objects;

import static pl.pumbakos.japwebservice.japresources.EndPoint.SONG;
import static pl.pumbakos.japwebservice.japresources.EndPoint.ALL;
import static pl.pumbakos.japwebservice.japresources.EndPoint.INFO;
import static pl.pumbakos.japwebservice.japresources.EndPoint.SIZE;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.FILENAME;

@RestController
@RequestMapping(SONG)
public class SongController {
    private final SongService service;

    @Autowired
    public SongController(SongService service) {
        this.service = service;
    }

    @PostMapping(consumes = "multipart/form-data", produces = "text/plain")
    public ResponseEntity<String> upload(@RequestParam("files") List<MultipartFile> multipartFiles) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFiles.get(0).getOriginalFilename()));
        if (multipartFiles.size() == 1 && filename.isBlank()) {
            return ResponseEntity.noContent().build();
        }
        return service.upload(multipartFiles);
    }

    @PutMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public Song update(@RequestBody Song song, @PathVariable(name = "id") Long id){
        return service.update(song, id);
    }

    @GetMapping(path = FILENAME,
            produces = "application/json")
    public ResponseEntity<Object> download(@PathVariable("filename") String filename) {
        return service.download(filename);
    }

    @GetMapping(path = INFO + FILENAME,
            produces = "application/json")
    public ResponseEntity<Song> get(@PathVariable("filename") String filename) {
        return service.info(filename);
    }

    @GetMapping(path = SIZE + FILENAME,
            produces = "text/plain")
    public ResponseEntity<Long> getFileSize(@PathVariable("filename") String filename) {
        return service.getFileSize(filename);
    }

    @GetMapping(path = ALL,
            produces = "application/json")
    public String getTitles() {
        return service.getTitles();
    }

    @GetMapping(path = INFO + ALL,
            produces = "application/json")
    public List<Song> getAll() {
        return service.getAll();
    }
}
