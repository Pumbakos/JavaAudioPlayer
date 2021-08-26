package pl.pumbakos.japwebservice.authormodule.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pumbakos.japwebservice.authormodule.models.Author;
import pl.pumbakos.japwebservice.authormodule.services.AuthorService;

import java.util.List;

import static pl.pumbakos.japwebservice.authormodule.resources.EndPoint.*;
import static pl.pumbakos.japwebservice.authormodule.resources.EndPoint.PathVariable.*;

@RestController
@RequestMapping(AUTHOR)
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping(path = ALL, produces = "application/json")
    public List<Author> getAll(){
        return service.getAll();
    }

    @GetMapping(path = ID, consumes = "application/json", produces = "application/json")
    public Author get(@PathVariable(name = "id") Long id){
        return service.get(id);
    }

    @PostMapping(path = ID, consumes = "application/json", produces = "application/json")
    public Author save(@RequestBody Author author){
        return service.save(author);
    }

    @PutMapping(path = ID, consumes = "application/json", produces = "application/json")
    public Author update(@RequestBody Author author, @PathVariable(name = "id") Long id){
        return service.update(author, id);
    }

    @DeleteMapping(path = ID, consumes = "application/json", produces = "application/json")
    public Author delete(@PathVariable(name = "id") Long id){
        return service.delete(id);
    }
}
