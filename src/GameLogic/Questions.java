package GameLogic;

import java.io.Serializable;

public class Questions implements Serializable {

    private String Question;
    private String[] options;
    private String correctAnswer;

    public Questions(String Question, String[] options,  String Answer) {
        this.Question = Question;
        this.options = options;
        this.correctAnswer = Answer;

    }

    public String getQuestion() {
        return Question;
    }

    public String[] getOptions() {
        return options;
    }

    public String getAnswer() {
        return correctAnswer;
    }


}