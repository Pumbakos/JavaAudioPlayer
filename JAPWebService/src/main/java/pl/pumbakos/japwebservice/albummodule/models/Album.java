package pl.pumbakos.japwebservice.albummodule.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pumbakos.japwebservice.songmodule.models.Song;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Column(name = "album_id", columnDefinition = "INT")
    private Long id;

    @NotBlank
    @Column(nullable = false, name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @NotBlank
    @Column(nullable = false, name = "description", columnDefinition = "VARCHAR(200)")
    private String description;

    @NotNull
    @Column(nullable = false, name = "release_date", columnDefinition = "DATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date releaseDate;

    @Column(nullable = false)
    @OneToMany(mappedBy = "album")
    @JsonIgnore
    private List<Song> songs;
//
//    @NotNull
//    @Column
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(
//            name = "Author_Album",
//            joinColumns = {@JoinColumn(name = "author.id")},
//            inverseJoinColumns = {@JoinColumn(name = "album.id")}
//    )
//    private List<Author> authors;
}
