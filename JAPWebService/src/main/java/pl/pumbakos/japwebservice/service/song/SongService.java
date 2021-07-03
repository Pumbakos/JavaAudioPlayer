package pl.pumbakos.japwebservice.service.song;

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
import pl.pumbakos.japwebservice.service.resource.Status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Service
public class SongService {
    private static final String DIRECTORY = System.getProperty("user.home") + "/Downloads/";
    private static final byte ERROR_STATUS = -1;
    private final SongRepository repository;
    private List<String> filenames = new ArrayList<>();

    @Autowired
    public SongService(SongRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<List<String>> uploadFiles(List<MultipartFile> multipartFiles) {
        try {
            for (MultipartFile file : multipartFiles) {
                String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

                //TODO: create enum with extension
                if (!filename.endsWith(".wav")) {
                    return ResponseEntity.of(Optional.of(Collections.singletonList(Status.Message.BAD_EXTENSION)));
                }

                Path fileStorage = get(DIRECTORY, filename).toAbsolutePath().normalize();
                copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

                long size = Files.size(fileStorage);
                String trimmedFilename = filename.substring(0, filename.length() - 4).replace('_', ' ');
                Song s = new Song.Builder().title(trimmedFilename).size(size).build();
                repository.save(s);

                filenames.add(filename);
            }
            return ResponseEntity.ok().body(filenames);
        } catch (Exception e) {
            return ResponseEntity.of(Optional.of(Collections.singletonList(Status.Message.NO_CONTENT)));
        }
    }

    @Transactional()
    public ResponseEntity<HttpStatus> updateSongInfo(String title, String album, String author, String releaseDate) {
        String trimmedTitle = title.replace('_', ' ');
        Song song = repository.findByTitle(trimmedTitle);
        if (song == null) {
            return ResponseEntity.of(Optional.of(HttpStatus.UNPROCESSABLE_ENTITY));
        }

        try {
            song.setAlbum(album.replace('_', ' '));
            song.setAuthor(author);
            song.setReleaseDate(LocalDate.parse(releaseDate));
        } catch (DateTimeParseException e) {
            return ResponseEntity.of(Optional.of(HttpStatus.PARTIAL_CONTENT));
        }catch (NullPointerException e){
            // DO NOTHING
        }

        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }

    /**
     * @param filename - name of song in DB, it's better to use names w/o extensions
     */
    public ResponseEntity<Object> downloadFiles(String filename) {
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
        try {
            return ResponseEntity.ok().body(repository.findSongSizeByName(trimmedFilename));
        } catch (NullPointerException e) {
            return ResponseEntity.of(Optional.of((long) Status.NO_CONTENT));
        }
    }
}
