package pl.pumbakos.japwebservice.producermodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pumbakos.japwebservice.producermodule.ProducertRepository;
import pl.pumbakos.japwebservice.producermodule.models.Producer;

import javax.persistence.Basic;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class ProducerService {
    private final ProducertRepository repository;

    @Autowired
    public ProducerService(ProducertRepository repository) {
        this.repository = repository;
    }

    public List<Producer> getAll() {
        return repository.findAll();
    }

    public Producer get(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Producer save(Producer producer) {
        return repository.save(producer);
    }

    public Producer update(Producer producer, Long id) {
        Optional<Producer> optionalProducer = repository.findById(id);

        if (optionalProducer.isPresent()) {
            Producer updatableProducer = optionalProducer.get();

            Field[] fields = Producer.class.getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id"))
                        continue;

                    Field updatableProducerField = updatableProducer.getClass().getDeclaredField(field.getName());
                    Field producerField = producer.getClass().getDeclaredField(field.getName());

                    updatableProducerField.setAccessible(true);
                    producerField.setAccessible(true);

                    if (field.getAnnotation(Basic.class) != null && producerField.get(producer) == null) {
                        continue;
                    }

                    updatableProducerField.set(updatableProducer, producerField.get(producer));

                    updatableProducerField.setAccessible(false);
                    producerField.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return null;
                }
            }

            return repository.save(updatableProducer);
        }
        return null;
    }

    public Producer delete(Long id) {
        Optional<Producer> byId = repository.findById(id);
        if(byId.isPresent()){
            repository.delete(byId.get());
            return byId.get();
        }
        return null;
    }
}
