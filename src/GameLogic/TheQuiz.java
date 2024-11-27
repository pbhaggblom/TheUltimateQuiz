package GameLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TheQuiz {

    Questions[] christmasQuestions = {
            new Questions("In which of these countries do they celebrate Christmas on the 24th of December?", new String[]{"Sweden", "United Kingdom", "Ireland", "France"}, "0"),
            new Questions("What's Santa Clause called in Sweden?", new String[]{"Julnisse", "Julgubbe", "Jultomte", "Julbocken"}, "2"),
            new Questions("What is the alternative Christmas plant associated with Mexico?", new String[]{"Cactus", "Poinsetta", "Holly", "Mistletoe"}, "1"),
            new Questions("Which of the following is one of Santa’s reindeer?", new String[]{"Comet", "Lancer", "Blixem", "Loner"}, "0")};

    Questions[] animalsQuestions = {
            new Questions("What's the latin name for spider?", new String[]{"Longleggius", "Spididoo", "Araneau", "Zeuzus"}, "2"),
            new Questions("What’s the first thing a caterpillar usually eats after it’s born?", new String[]{"Grass", "Its own eggshell", "Bark", "Leaves"}, "1"),
            new Questions("Which insect holds the record for the fastest flying insect in the world?", new String[]{"Hawk moth", "Dragonfly", "Housefly", "Bee"}, "1"),
            new Questions("What is the only mammal capable of true flight (not gliding)?", new String[]{"Flying fox", "Colugo", "Flying squirrel", " Bat"}, "3")};

    Questions[] nutritionQuestions = {
            new Questions("Which vitamin is called thiamine?", new String[]{"B1", "B5", "B12", "B8"}, "0"),
            new Questions("What is the main source to vitamin D?", new String[]{"Peppers", "Cucumber", "Sunlight", "Oats"}, "2"),
            new Questions("Which food group does cereal belong to?", new String[]{"Vegetable", "Grain", "Cereal", "Wheat"}, "1"),
            new Questions("Which of the following minerals helps build strong teeth and bones?", new String[]{"Folate", "Iron", "Zinc", "Calcium"}, "3")};

    Questions[] geographyQuestions = {
            new Questions("What is the capital city of New Zealand?", new String[]{"Auckland", "Christchurch", "Wellington", "Queenstown"}, "2"),
            new Questions("Which city in India is home to the iconic Taj Mahal?", new String[]{"Delhi", "Mumbai", "Jaipur", "Agra"}, "3"),
            new Questions("What city holds the record for the highest altitude among the world's capital cities?", new String[]{"La Paz", "Kathmandu", "Quito", "Bogotá"}, "0"),
            new Questions("Which country is the largest by land area?", new String[]{"Russia", "Canada", "China", "United States"}, "0")};

    Questions[] musicQuestions = {
            new Questions("Who composed the famous piano piece “Für Elise”?", new String[]{"Beethoven", "Mozart", "Bach", "Chopin"}, "0"),
            new Questions("Which rock band released the album \"The Dark Side of the Moon?\"", new String[]{"The Beatles", "Pink Floyd", "Led Zeppelin", "The Rolling Stones"}, "1"),
            new Questions("Who composed the famous ballet score \"Swan Lake\"?", new String[]{"Stravinsky", "Shostakovich", "Tchaikovsky", "Rachmaninoff"}, "2"),
            new Questions("Who is known as the \"King of Pop\"?", new String[]{"Elvis Presley", "Michael Jackson", "Frank Sinatra", "Prince"}, "1")};


    List<Questions[]> allQuestions = List.of(christmasQuestions, animalsQuestions, nutritionQuestions);

    QuizCategory[] categories = {
            new QuizCategory("Christmas", christmasQuestions),
            new QuizCategory("Animals", animalsQuestions),
            new QuizCategory("Nutrition", nutritionQuestions),
            new QuizCategory("Geography", geographyQuestions),
            new QuizCategory("Music", musicQuestions)};

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

    public List<Questions[]> getQuestions() {
        return allQuestions;
    }

    public Questions[] getCategoryQuestions(int index) {
        if (index >= 0 && index < quizCategories.size()) {
            return quizCategories.get(index).getCategories();
        }
        return null;
    }


    public QuizCategory[] getCategories() {
        return categories;
    }

    public Questions[] getAnimalsQuestions() {
        return animalsQuestions;

    }


    public void setAnimalsQuestions(Questions[] animalsQuestions) {
        this.animalsQuestions = animalsQuestions;
    }
}