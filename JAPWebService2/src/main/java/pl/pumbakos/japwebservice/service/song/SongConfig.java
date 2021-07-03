package pl.pumbakos.japwebservice2.service.song;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;

//@Configuration
public class SongConfig {
    @Bean
    CommandLineRunner commandLineRunner(SongRepository songRepository) {
        return args -> {
            Song song1 = new Song.Builder().
                    title("Bandyta").
                    author("Sobel").
                    album("Pułapka na motyle").
                    releaseDate(LocalDate.now()).
                    build();

            Song song2 = new Song.Builder().
                    title("To ja").author("Sobel").
                    album("Pułapka na motyle").
                    releaseDate(LocalDate.now()).
                    build();

            Song song3 = new Song.Builder().
                    title("Fiołkowe pole").
                    author("Sobel").
                    album("Pułapka na motyle").
                    releaseDate(LocalDate.now()).
                    build();

            Song song4 = new Song.Builder().
                    title("Kapie deszcz").
                    author("Sobel").
                    album("Pułapka na motyle").
                    releaseDate(LocalDate.now()).
                    build();


            songRepository.saveAll(List.of(song1, song2, song3, song4));
        };
    }
}
