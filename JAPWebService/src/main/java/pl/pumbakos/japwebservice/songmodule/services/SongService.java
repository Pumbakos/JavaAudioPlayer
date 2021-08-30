package pl.pumbakos.japwebservice.songmodule.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.albummodule.AlbumRepository;
import pl.pumbakos.japwebservice.songmodule.SongRepository;
import pl.pumbakos.japwebservice.songmodule.models.Song;
import pl.pumbakos.japwebservice.japresources.Extension;
import pl.pumbakos.japwebservice.japresources.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Service
public class SongService {
    private static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/";
    private final SongRepository repository;
    private final AlbumRepository albumRepository;
    private final Gson gson;

    @Autowired
    public SongService(SongRepository repository, AlbumRepository albumRepository, Gson gson) {
        this.repository = repository;
        this.albumRepository = albumRepository;
        this.gson = gson;
    }

    public ResponseEntity<String> upload(List<MultipartFile> multipartFiles) {
        try {
            for (MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                if (!filename.endsWith(Extension.WAV)) {
                    return ResponseEntity.badRequest().body(Status.Message.BAD_EXTENSION);
                }
                Song song = new Song();
                song.setPath(get(DIRECTORY, filename).toAbsolutePath().normalize().toString());

                Path fileStorage = Path.of(song.getPath());
                copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

                long size = file.getSize();
                String title = filename.substring(0, filename.length() - 4);

                song.setSize(size);
                song.setTitle(title);

                repository.save(song);
            }
            return ResponseEntity.ok().body(Status.Message.ACCEPTED);
        } catch (IOException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Transactional
    public ResponseEntity<String> update(Song song, Long id) {
        albumRepository.save(song.getAlbum());
        Optional<Song> optionalSong = repository.findById(id);
        if (optionalSong.isPresent()) {
            Song updatableSong = optionalSong.get();

            Field[] fields = Song.class.getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id")) {
                        continue;
                    }

                    Field updatableSongField = updatableSong.getClass().getDeclaredField(field.getName());
                    Field songField = song.getClass().getDeclaredField(field.getName());

                    updatableSongField.setAccessible(true);
                    songField.setAccessible(true);

                    updatableSongField.set(updatableSong, songField.get(song));

                    updatableSongField.setAccessible(false);
                    songField.setAccessible(false);
                } catch (NullPointerException | NoSuchFieldException | IllegalAccessException | DateTimeParseException e) {
                    return ResponseEntity.badRequest().body(Status.Message.BAD_REQUEST);
                }
            }
            repository.save(updatableSong);

            return ResponseEntity.accepted().body(Status.Message.ACCEPTED);
        }
        return ResponseEntity.unprocessableEntity().body(Status.Message.NOT_FOUND);
    }

    /**
     * @param filename - name of song in DB, it's better to use names w/o extensions
     */
    public ResponseEntity<Object> download(String filename) {
        Optional<Song> optionalSong = repository.findByTitle(filename);

        Resource resource;
        Path filePath;
        try {
            if (optionalSong.isPresent())
                filePath = Path.of(optionalSong.get().getPath());
            else
                return ResponseEntity.notFound().build();

            if (!Files.exists(filePath)) {
                throw new FileNotFoundException(filename + " was not found on the server");
            }

            resource = new UrlResource(filePath.toUri());
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("File-Name", filename);
            httpHeaders.add(CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());

            return ResponseEntity.ok().contentType(MediaType.parseMediaType(Files.probeContentType(filePath)))
                    .headers(httpHeaders).body(resource);
        } catch (InvalidPathException | MalformedURLException e) {
            return ResponseEntity.of(Optional.of(Status.Message.INVALID_TITLE));
        } catch (IOException e) {
            return ResponseEntity.of(Optional.of(HttpStatus.INTERNAL_SERVER_ERROR));
        }
    }

    public ResponseEntity<Long> getFileSize(String filename) {
        String trimmedFilename = filename.replace('_', ' ');
        return ResponseEntity.ok().body(repository.findSongSizeByName(trimmedFilename).orElse((long) Status.NO_CONTENT));
    }

    public String getTitles() {
        return gson.toJson(repository.findAllByTitle());
    }

    public ResponseEntity<Song> info(String filename) {
        Optional<Song> optionalSong = repository.findByTitle(filename);
        return optionalSong.map(song -> ResponseEntity.accepted().body(song)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    public List<Song> getAll() {
        return repository.findAll();
    }
}
