package pl.pumbakos.japwebservice.authormodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pumbakos.japwebservice.albummodule.AlbumRepository;
import pl.pumbakos.japwebservice.authormodule.AuthorRepository;
import pl.pumbakos.japwebservice.authormodule.models.Author;
import pl.pumbakos.japwebservice.songmodule.SongRepository;

import javax.persistence.Basic;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository repository;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Autowired
    public AuthorService(AuthorRepository repository, AlbumRepository albumRepository, SongRepository songRepository) {
        this.repository = repository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
    }

    public List<Author>getAll(){
        return repository.findAll();
    }

    public Author get(Long id){
        Optional<Author> optionalAuthor = repository.findById(id);
        return optionalAuthor.orElse(null);
    }

    public Author save(Author author){
        return repository.save(author);
    }

    public Author update(Author author, Long id){
        Optional<Author> optionalAuthor = repository.findById(id);

        if (optionalAuthor.isPresent()) {
            Author updatableAuthor = optionalAuthor.get();

            Field[] fields = Author.class.getDeclaredFields();

            //TODO
            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id"))
                        continue;

                    Field updatableAuthorField = updatableAuthor.getClass().getDeclaredField(field.getName());
                    Field authorField = author.getClass().getDeclaredField(field.getName());

                    updatableAuthorField.setAccessible(true);
                    authorField.setAccessible(true);

                    if (field.getAnnotation(Basic.class) != null && authorField.get(author) == null){
                        continue;
                    }

                    updatableAuthorField.set(updatableAuthor, authorField.get(author));

                    updatableAuthorField.setAccessible(false);
                    authorField.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return null;
                }
            }

            return repository.save(updatableAuthor);
        }
        return null;
    }

    public Author delete(Long id){
        Optional<Author> optionalAuthor = repository.findById(id);
        if(optionalAuthor.isPresent()){
            repository.delete(optionalAuthor.get());
        }
        return null;
    }
}
