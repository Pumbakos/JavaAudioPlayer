package pl.pumbakos.japwebservice.authormodule.models;

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
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Column(name = "author_id", columnDefinition = "INT")
    private Long id;

    @NotBlank
    @Column(nullable = false, name = "name", columnDefinition = "VARCHAR(50)")
    private String name;

    @NotBlank
    @Column(nullable = false, name = "surname", columnDefinition = "VARCHAR(50)")
    private String surname;

    @Basic
    @Column(name = "nickname", columnDefinition = "VARCHAR(50)")
    private String nickname;

    @ManyToMany(mappedBy = "authors")
    @JsonIgnore
    private List<Song> songs;

//    @JsonIgnore
//    @NotNull
//    @Column(nullable = false)
//    @ManyToMany(mappedBy = "authors")
//    private List<Album> albums;

    @NotNull
    @Column(nullable = false, name = "date_of_birth", columnDefinition = "DATETIME")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private Date dateOfBirth;
}
