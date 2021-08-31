package pl.pumbakos.japwebservice.producermodule.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import pl.pumbakos.japwebservice.producermodule.models.Producer;
import pl.pumbakos.japwebservice.producermodule.services.ProducerService;

import java.util.List;

import static pl.pumbakos.japwebservice.japresources.EndPoint.*;
import static pl.pumbakos.japwebservice.japresources.EndPoint.PathVariable.ID;

@RestController
@RequestMapping(path = PRODUCER)
public class ProducerController {
    private final ProducerService service;

    @Autowired
    public ProducerController(ProducerService service) {
        this.service = service;
    }

    @GetMapping(path = ALL)
    public List<Producer> getAll(){
        return service.getAll();
    }

    @GetMapping(path = ID)
    public Producer get(@PathVariable(name = "id") Long id){
        return service.get(id);
    }

    @PostMapping()
    public Producer save(@RequestBody Producer producer){
        return service.save(producer);
    }

    @PutMapping(path = ID)
    public Producer update(@RequestBody Producer producer, @PathVariable(name = "id") Long id){
        return service.update(producer, id);
    }

    @DeleteMapping(path = ID)
    public Producer delete(@PathVariable(name = "id") Long id){
        return service.delete(id);
    }
}
