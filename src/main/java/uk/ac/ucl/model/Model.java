package uk.ac.ucl.model;

import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Model
{
  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a suitable data structure.

  Category main = new Category("main"); // Main here is like the master directory of notes
  static int highest_note_id = 0;

  public Note new_note()
  {
    Note note = new Note(highest_note_id);
    highest_note_id += 1;
    main.add_note(note);
    return note;
  }

  public Note get_note(int ID)
  {
    return main.get_note(ID);
  }


  public List<String> getPatientNames()
  {
    return readFile("data/patients100.csv");
  }

  // This method illustrates how to read csv data from a file.
  // The data files are stored in the root directory of the project (the directory your project is in),
  // in the directory named data.
  public List<String> readFile(String fileName)
  {
    List<String> data = new ArrayList<>();

    try (Reader reader = new FileReader(fileName);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
    {
      for (CSVRecord csvRecord : csvParser)
      {
        // The first row of the file contains the column headers, so is not actual data.
        data.add(csvRecord.get(0));
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return data;
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}
