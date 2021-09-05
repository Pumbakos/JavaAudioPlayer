package pl.pumbakos.japwebservice.albummodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pumbakos.japwebservice.albummodule.AlbumRepository;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.authormodule.AuthorRepository;
import pl.pumbakos.japwebservice.japresources.DefaultUtils;
import pl.pumbakos.japwebservice.songmodule.SongRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {
    private final AlbumRepository repository;
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;
    private final DefaultUtils<Album> defaultUtils;

    @Autowired
    public AlbumService(AlbumRepository repository, AuthorRepository authorRepository, SongRepository songRepository, DefaultUtils<Album> defaultUtils) {
        this.repository = repository;
        this.authorRepository = authorRepository;
        this.songRepository = songRepository;
        this.defaultUtils = defaultUtils;
    }

    public List<Album> getAll(){
        return repository.findAll();
    }

    public Album get(Long id){
        return repository.findById(id).orElse(null);
    }

    public Album save(Album album){
        return repository.save(album);
    }

    public Album update(Album album, Long id){
        return defaultUtils.update(repository ,album, id);
    }

    public Album delete(Long id){
        Optional<Album> optionalAlbum = repository.findById(id);
        if(optionalAlbum.isPresent()){
            repository.delete(optionalAlbum.get());
        }
        return null;
    }
}
