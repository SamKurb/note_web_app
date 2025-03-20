package uk.ac.ucl.model;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class JsonWriter {

    public static void note_to_json(Note note, String filePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            objectMapper.writeValue(new File(filePath), note);
            System.out.println("Note saved to JSON file: " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
