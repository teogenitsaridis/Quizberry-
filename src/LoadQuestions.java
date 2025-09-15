
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

class Question {
    String question;
    boolean answer;
}

public class LoadQuestions {

    // Reads the "questions.json" file using Gson and converts it into a List of Question objects
    public List<Question> loadQuestions() throws Exception {
        Gson gson = new Gson();
        InputStreamReader reader = new InputStreamReader(
            new FileInputStream("questions.json"), StandardCharsets.UTF_8
        );
        return gson.fromJson(reader, new TypeToken<List<Question>>(){}.getType());
    }
}


