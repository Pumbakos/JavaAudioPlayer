package pl.pumbakos.japwebservice.songmodule.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.authormodule.models.Author;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Song implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Column(name = "song_id", columnDefinition = "INT")
    private Long id;

    @NotBlank(message = "We require title not to be empty, check data you entered")
    @Column(nullable = false, name = "title", columnDefinition = "VARCHAR(255)")
    private String title;

    @NotNull(message = "We require size not to be empty, check data you entered")
    @Column(nullable = false, name = "size", columnDefinition = "INT")
    private Long size;

    @NotNull
    @Column(nullable = false, name = "path", columnDefinition = "VARCHAR(MAX)")
    private String path;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "Song_Author",
            joinColumns = {@JoinColumn(name = "author_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")}
    )
    private List<Author> authors;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;
}
