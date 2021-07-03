package pl.pumbakos.japwebservice.service.song;

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

    public static class Builder {
        private Long id = null;
        private String title = null;
        private String author = null;
        private String album = null;
        private LocalDate releaseDate = null;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder author(String author) {
            this.author = author;
            return this;
        }

        public Builder album(String album) {
            this.album = album;
            return this;
        }

        public Builder releaseDate(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Song build(){
            return new Song(this);
        }
    }

    private Song(Builder builder){
        this.id = builder.id;
        this.title = builder.title;
        this.author = builder.author;
        this.album = builder.album;
        this.releaseDate = builder.releaseDate;
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
