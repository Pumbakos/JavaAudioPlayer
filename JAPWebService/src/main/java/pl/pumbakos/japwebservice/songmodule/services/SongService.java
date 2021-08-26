package pl.pumbakos.japwebservice.songmodule.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.pumbakos.japwebservice.songmodule.SongRepository;
import pl.pumbakos.japwebservice.songmodule.models.Song;
import pl.pumbakos.japwebservice.songmodule.resource.Extension;
import pl.pumbakos.japwebservice.songmodule.resource.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Service
public class SongService {
    private static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/";
    private final SongRepository repository;

    @Autowired
    public SongService(SongRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<String> upload(List<MultipartFile> multipartFiles) {
        try {
            for (MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                if (!filename.endsWith(Extension.WAV)) {
                    return ResponseEntity.badRequest().body(Status.Message.BAD_EXTENSION);
                }

                Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
                copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

                long size = file.getSize();
                String title = filename.substring(0, filename.length() - 4).replace('_', ' ');

                Song song = new Song();
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
        Optional<Song> optionalSong = repository.findById(id);
        if (optionalSong.isPresent()) {
            Song updatableSong = optionalSong.get();

            Field[] fields = Song.class.getDeclaredFields();

            for (Field field : fields) {
                try {
                    if (field.getName().equalsIgnoreCase("id")) {
                        continue;
                    }

                    Field taskToUpdateField = updatableSong.getClass().getDeclaredField(field.getName());
                    Field taskField = song.getClass().getDeclaredField(field.getName());

                    taskToUpdateField.setAccessible(true);
                    taskField.setAccessible(true);

                    taskToUpdateField.set(updatableSong, taskField.get(song));

                    taskToUpdateField.setAccessible(false);
                    taskField.setAccessible(false);
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
        if (!filename.endsWith(".wav")) {
            filename = filename.concat(".wav");
        }

        Resource resource;
        try {
            Path filePath = get(DIRECTORY).toAbsolutePath().normalize().resolve(filename.replace(" ", "_"));

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

    public List<String> getTitles() {
        return repository.findAllByTitle();
    }

    public ResponseEntity<Song> info(String filename) {
        Optional<Song> optionalSong = repository.findByTitle(filename.replace("_", " "));
        return optionalSong.map(song -> ResponseEntity.accepted().body(song)).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
