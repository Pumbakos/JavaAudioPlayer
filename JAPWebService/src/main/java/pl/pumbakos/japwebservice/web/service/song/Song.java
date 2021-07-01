package pl.pumbakos.japwebservice.web.service.song;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
public class Song {
    @Id
    @SequenceGenerator(
            name = "song_sequence",
            sequenceName = "song_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String title;
    private String author;
    private String album;
    private LocalDate releaseDate;

    public Song() {
    }

    public Song(Long id, String title, String author, String album, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.album = album;
        this.releaseDate = releaseDate;
    }

    public Song(String title, String author, String album, LocalDate releaseDate) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.releaseDate = releaseDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", album='" + album + '\'' +
                ", releaseDate='" + releaseDate;
    }
}
