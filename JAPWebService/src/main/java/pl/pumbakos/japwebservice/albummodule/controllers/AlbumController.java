package pl.pumbakos.japwebservice.albummodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.albummodule.services.AlbumService;

import java.util.List;

@RestController
@RequestMapping(path = "/album")
public class AlbumController {
    private final AlbumService service;

    @Autowired
    public AlbumController(AlbumService service) {
        this.service = service;
    }

    @GetMapping(path = "/all",
            consumes = "application/json", produces = "application/json")
    public List<Album> getAll(){
        return service.getAll();
    }

    @GetMapping(path = "/{id}",
            consumes = "application/json", produces = "application/json")
    public Album get(@PathVariable(name = "id") Long id){
        return service.get(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Album save(@RequestBody Album album){
        return service.save(album);
    }

    @PutMapping(path = "/{id}",
            consumes = "application/json", produces = "application/json")
    public Album update(@RequestBody Album album,@PathVariable(name = "id") Long id){
        return service.update(album, id);
    }

    @DeleteMapping(path = "/{id}",
            consumes = "application/json", produces = "application/json")
    public Album delete(@PathVariable(name = "id") Long id){
        return service.delete(id);
    }
}
