package pl.pumbakos.japwebservice.albummodule.models;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pumbakos.japwebservice.authormodule.models.Author;
import pl.pumbakos.japwebservice.songmodule.models.Song;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Getter@Setter
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotNull
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date releaseDate;

//    @NotNull
//    @Column(nullable = false)
//    @OneToMany //TODO
//    private List<Song> songs;
//
//    @NotNull
//    @Column(nullable = false)
//    @ManyToMany //TODO
//    private List<Author> authors;
}
