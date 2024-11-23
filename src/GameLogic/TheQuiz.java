package GameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TheQuiz {

    Questions[] christmasQuestions = {
            new Questions("In which of these countries do they celebrate Christmas on the 24th of December?", new String[]{"Sweden", "United Kingdom", "Ireland", "France"}, "0"),
            new Questions("What's Santa Clause called in Sweden?", new String[]{"Julnisse", "Julgubbe", "Jultome", "Julbocken"}, "2")};

    Questions[] animalsQuestions = {
            new Questions("What's the latin name for spider?", new String[]{"Longleggius", "Spididoo", "Araneau", "Zeuzus"}, "2"),
            new Questions("What’s the first thing a caterpillar usually eats after it’s born?", new String[]{"Grass", "Its own eggshell", "Bark", "Leaves"}, "1")};

    Questions[] nutritionQuestions = {
            new Questions("Which vitamin is called thiamine?", new String[]{"B1", "B5", "B12", "B8"}, "0"),
            new Questions("What is the main source to vitamin D?", new String[]{"Peppers", "Cucumber", "Sunlight", "Oats"}, "2")};

    List<Questions[]> allQuestions = List.of(christmasQuestions, animalsQuestions, nutritionQuestions);

    QuizCategory[] categories = {
            new QuizCategory("Christmas", christmasQuestions),
            new QuizCategory("Animals", animalsQuestions),
            new QuizCategory("Nutrition", nutritionQuestions)};

    List<QuizCategory> quizCategories = List.of(categories);

    public List<String> categories() {
        List<String> categoryNames = new ArrayList<>();
        int i = 1;
        for (QuizCategory category : quizCategories) {
            categoryNames.add(i + "." + category.getCategoryName());
            i++;
        }
        return categoryNames;
    }

    public void playQuiz(BufferedReader in, PrintWriter out) throws IOException {

        int score = 0;
        int userChoice;

        out.println("Select a category:");
        List<String> categoryNames = categories();
        for (String categoryName : categoryNames) {
            out.println(categoryName);
        }

        while (true) {
            try {
                out.println("Choose a category");
                out.flush();
                String userInput = in.readLine();
                userChoice = Integer.parseInt(userInput);

                if (userChoice < 1 || userChoice > allQuestions.size()) {
                    out.println("Something went wrong, try again");
                    continue;
                }
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        QuizCategory selectedCategory = quizCategories.get(userChoice - 1);
        Questions[] catergoryQuestions = selectedCategory.getCategories();

        for (Questions question : catergoryQuestions) {
            out.println(question.getQuestion());

            String[] options = question.getOptions();

            for (int i = 0; i < options.length; i++) {
                System.out.println((i + 1) + ". " + options[i]);
                out.println((i + 1) + options[i]);
            }

            out.flush();

            int userAnswer;

            while (true) {
                try {
                    String userInput = in.readLine();
                    userAnswer = Integer.parseInt(userInput);

                    if (userAnswer < 1 || userAnswer > allQuestions.size()) {
                        out.println("Something went wrong, try again");
                        continue;
                    }

                    if (String.valueOf(userAnswer).equals(question.getAnswer())) {
                        out.println("Correct!");
                        score++;
                    } else
                        out.println("Wrong!");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }}