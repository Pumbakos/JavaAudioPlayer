package pl.pumbakos.japwebservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pl.pumbakos.japwebservice.Generators.SongGenerator;
import pl.pumbakos.japwebservice.songmodule.controllers.SongController;
import pl.pumbakos.japwebservice.songmodule.models.Song;

@WebMvcTest(controllers = SongController.class)
public class SongControllerTest {
    private final ObjectMapper mapper = new ObjectMapper();
    private final Gson gson = new Gson();

    @Test
    @DisplayName("POST files into server")
    public void uploadCompleteFiles(){
        Song song = SongGenerator.createCompleteSong();
    }
}
