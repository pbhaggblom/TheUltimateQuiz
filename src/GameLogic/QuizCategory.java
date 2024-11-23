package GameLogic;

public class QuizCategory {

    private String name;
    private Questions[] categories;

    public QuizCategory (String name, Questions[] categories) {
        this.name = name;
        this.categories = categories;
    }


    public String getCategoryName() {
        return name;
    }

    public Questions[] getCategories() {
        return categories;
    }

}