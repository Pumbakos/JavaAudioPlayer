package pl.pumbakos.japwebservice2.service.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    @Query("SELECT s FROM Song s WHERE s.title = ?1")
    Song findSongByTitle(String title);

    @Query("SELECT s.title FROM Song s")
    List<String> findAllByTitle();
}
