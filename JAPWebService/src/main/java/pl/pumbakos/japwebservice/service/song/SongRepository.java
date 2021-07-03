package pl.pumbakos.japwebservice.service.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query(value = "SELECT title FROM Song", nativeQuery = true)
    List<String> findAllByTitle();

    @Query(value = "SELECT size FROM Song song WHERE song.title = :songTitle", nativeQuery = true)
    Long getSongSizeByName(@Param("songTitle") String songTitle);
}
