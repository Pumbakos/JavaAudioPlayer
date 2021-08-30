package pl.pumbakos.japwebservice.albummodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pumbakos.japwebservice.albummodule.AlbumRepository;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.authormodule.AuthorRepository;
import pl.pumbakos.japwebservice.songmodule.SongRepository;

import javax.persistence.Basic;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository repository;
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;

    @Autowired
    public AlbumService(AlbumRepository repository, AuthorRepository authorRepository, SongRepository songRepository) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.songRepository = songRepository;
    }

    public List<Album> getAll(){
        return repository.findAll();
    }

    public Album get(Long id){
        return repository.findById(id).orElse(null);
    }

    public Album save(Album album){
//        songRepository.saveAll(album.getSongs());
//        authorRepository.saveAll(album.getAuthors());
        return repository.save(album);
    }

    public Album update(Album album, Long id){
        Optional<Album> optionalAlbum = repository.findById(id);
        if (optionalAlbum.isPresent()) {
            Album updatableAlbum = optionalAlbum.get();

            Field[] fields = Album.class.getDeclaredFields();

            //TODO
            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id"))
                        continue;

                    Field updatableAlbumField = updatableAlbum.getClass().getDeclaredField(field.getName());
                    Field albumField = album.getClass().getDeclaredField(field.getName());

                    updatableAlbumField.setAccessible(true);
                    albumField.setAccessible(true);

                    if (field.getAnnotation(Basic.class) != null && albumField.get(album) == null){
                        continue;
                    }

                    updatableAlbumField.set(updatableAlbum, albumField.get(album));

                    updatableAlbumField.setAccessible(false);
                    albumField.setAccessible(false);
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return null;
                }
            }

            return repository.save(updatableAlbum);
        }
        return null;
    }

    public Album delete(Long id){
        Optional<Album> optionalAlbum = repository.findById(id);
        if(optionalAlbum.isPresent()){
            repository.delete(optionalAlbum.get());
        }
        return null;
    }
}
