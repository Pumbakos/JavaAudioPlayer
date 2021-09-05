package pl.pumbakos.japwebservice.albummodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.albummodule.services.AlbumService;

import javax.validation.Valid;
import java.util.List;

import static pl.pumbakos.japwebservice.japresources.EndPoint.ALBUM;
import static pl.pumbakos.japwebservice.japresources.EndPoint.ALL;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;

@RestController
@RequestMapping(path = ALBUM)
public class AlbumController {
    private final AlbumService service;

    @Autowired
    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping(path = ALL,
            produces = "application/json")
    public List<Album> getAll() {
        return service.getAll();
    }

    @GetMapping(path = ID,
            produces = "application/json")
    public Album get(@PathVariable(name = "id") Long id) {
        return service.get(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Album save(@Valid @RequestBody Album album) {
        return service.save(album);
    }

    @PutMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public Album update(@Valid @RequestBody Album album, @PathVariable(name = "id") Long id) {
        return service.update(album, id);
    }

    @DeleteMapping(path = ID,
            produces = "application/json")
    public Album delete(@PathVariable(name = "id") Long id) {
        return service.delete(id);
    }
}
