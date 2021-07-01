package pl.pumbakos.japwebservice.web.service.song;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

//@Configuration
public class SongConfig {
    @Bean
    CommandLineRunner commandLineRunner(SongRepository songRepository) {
        return args -> {
            Song song1 = new Song(
                    "Bandyta",
                    "Sobel",
                    "Pułapka na motyle",
                    LocalDate.now()
            );
            Song song2 = new Song(
                    "To ja",
                    "Sobel",
                    "Pułapka na motyle",
                    LocalDate.now()
            );
            Song song3 = new Song(
                    "Fiołkowe pole",
                    "Sobel",
                    "Pułapka na motyle",
                    LocalDate.now()
            );
            Song song4 = new Song(
                    "Kapie deszcz",
                    "Sobel",
                    "Pułapka na motyle",
                    LocalDate.now()
            );

            songRepository.saveAll(List.of(song1, song2, song3, song4));
        };
    }
}
