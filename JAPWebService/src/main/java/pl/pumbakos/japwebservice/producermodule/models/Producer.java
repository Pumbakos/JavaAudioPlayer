package pl.pumbakos.japwebservice.producermodule.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.pumbakos.japwebservice.albummodule.models.Album;
import pl.pumbakos.japwebservice.japresources.DBModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table
@Setter
@Getter
@ToString
public class Producer extends DBModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    @Column(unique = true, updatable = false, insertable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String nickname;

    @OneToMany(mappedBy = "producer")
    @JsonIgnore
    @ToString.Exclude
    private List<Album> albums;
}
