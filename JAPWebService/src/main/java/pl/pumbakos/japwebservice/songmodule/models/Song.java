package pl.pumbakos.japwebservice.songmodule.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Getter@Setter
@Entity
@Table
public class Song implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    @NotBlank(message = "We require title not to be empty, check data you entered")
    @Column(nullable = false)
    private String title;

    @NotNull(message = "We require size not to be empty, check data you entered")
    @Column(nullable = false)
    private Long size;

    @Basic
    private String author;

    @Basic
    private String album;

    @Basic
    private LocalDate releaseDate;
}
