package pl.pumbakos.japwebservice.producermodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pumbakos.japwebservice.producermodule.models.Producer;
import pl.pumbakos.japwebservice.producermodule.services.ProducerService;

import javax.validation.Valid;
import java.util.List;

import static pl.pumbakos.japwebservice.japresources.EndPoint.ALL;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PRODUCER;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;

@RestController
@RequestMapping(path = PRODUCER)
public class ProducerController {
    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }

    @GetMapping(path = ALL,
            produces = "application/json")
    public ResponseEntity<List<Producer>> getAll() {
        List<Producer> all = service.getAll();
        return all == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(all);
    }

    @GetMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<Producer> get(@Valid @PathVariable(name = "id") Long id) {
        Producer producer = service.get(id);
        return producer == null ? ResponseEntity.noContent().build() : ResponseEntity.ok(producer);
    }

    @PostMapping(
            consumes = "application/json")
    public ResponseEntity<HttpStatus> save(@Valid @RequestBody Producer producer) {
        service.save(producer);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping(path = ID,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<HttpStatus> update(@RequestBody Producer producer, @PathVariable(name = "id") Long id) {
        return service.update(producer, id) ? ResponseEntity.ok(HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = ID,
            produces = "application/json")
    public ResponseEntity<HttpStatus> delete(@PathVariable(name = "id") Long id) {
        return service.delete(id) ? ResponseEntity.ok(HttpStatus.OK) : ResponseEntity.notFound().build();
    }
}
