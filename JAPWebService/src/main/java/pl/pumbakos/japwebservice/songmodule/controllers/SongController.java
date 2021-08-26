package pl.pumbakos.japwebservice.songmodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.songmodule.models.Song;
import pl.pumbakos.japwebservice.songmodule.resource.EndPoint;
import pl.pumbakos.japwebservice.songmodule.services.SongService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(EndPoint.SONG)
public class SongController {
    private final SongService service;

    @Autowired
    public SongController(SongService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> upload(@RequestParam("files") List<MultipartFile> multipartFiles) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(multipartFiles.get(0).getOriginalFilename()));
        if (multipartFiles.size() == 1 && filename.isBlank()) {
            return ResponseEntity.noContent().build();
        }
        return service.upload(multipartFiles);
    }

    @PutMapping(path = EndPoint.PathVariable.ID)
    public ResponseEntity<String> update(@RequestBody Song song, @PathVariable(name = "id") Long id){
        return service.update(song, id);
    }

    @GetMapping(path = EndPoint.PathVariable.FILENAME)
    public ResponseEntity<Object> download(@PathVariable("filename") String filename) {
        return service.download(filename);
    }

    @GetMapping(path = EndPoint.INFO + EndPoint.PathVariable.FILENAME)
    public ResponseEntity<Song> get(@PathVariable("filename") String filename) {
        return service.info(filename);
    }

    @GetMapping(path = EndPoint.PathVariable.FILENAME + EndPoint.SIZE)
    public ResponseEntity<Long> getFileSize(@PathVariable("filename") String filename) {
        return service.getFileSize(filename);
    }

    @GetMapping(path = EndPoint.ALL)
    public List<String> getTitles() {
        return service.getTitles();
    }
}
