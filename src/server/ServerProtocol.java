package server;

import GameLogic.Questions;
import GameLogic.QuizCategory;
import GameLogic.TheQuiz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServerProtocol {

    private QuizGame game;
    private TheQuiz quiz;

    private List<QuizCategory> categories = new ArrayList<>();
    private List<String> shuffleQuestions;
    private Questions[] questions;
    private QuizCategory currentCategory;
    private Questions[] selectedQuestion;
    private List<Questions[]> currentCategoryQuestions;
    private int categoryIndex;
    private int currentQuestionIndex = 0;
    private List<String> shuffledCategories;
    List<Questions> shuffledQuestions;

    final protected int INITIAL = 0;
    final protected int GAMELOOP = 1;

    protected int state = INITIAL;

    public ServerProtocol(QuizGame game) {
        this.game = game;
        quiz = new TheQuiz();
    }

    public TheQuiz getQuiz() {
        return quiz;
    }

    public Object getOutput(String input) {
        Object output;

        if (state == INITIAL) {
            //skicka kategorier

            for(QuizCategory category : quiz.getCategories()) {
                categories.add(category);
            }
            shuffledCategories = quiz.categories();
            state = GAMELOOP;
            return new Response("CATEGORY", shuffledCategories);
          //return new Response("CATEGORY", quiz.categories());
        } else if (state == GAMELOOP) {
            if (input.startsWith("chosen category")) {
                //hämta frågor från vald kategori
                //skicka första frågan
                System.out.println(game.getActivePlayer().getName() + " " + input);
                categoryIndex = Integer.parseInt((input.split(": ")[1]));
                String selectedCategory = shuffledCategories.get(categoryIndex);
                questions = quiz.getCategoryQuestions(selectedCategory);

                shuffledQuestions = new ArrayList<>();
                shuffledQuestions.addAll(Arrays.asList(questions));
                Collections.shuffle(shuffledQuestions);

                currentQuestionIndex = 0;
                System.out.println("i:" + currentQuestionIndex);
                return shuffledQuestions.get(currentQuestionIndex);
//                return questions[currentQuestionIndex];

            } else if (input.startsWith("answered")) {

                if (input.substring(input.length() - 1).equals(shuffledQuestions.get(currentQuestionIndex).getAnswer())) {
                    game.getActivePlayer().setPoints(game.getActivePlayer().getPoints() + 1);
                }

                //kolla om svar är rätt
                //ge poäng
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    //skicka nästa fråga
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
//                    int categoryIndex = Integer.parseInt(input.split(": ")[1]);
//                    Questions[] questions = quiz.getCategoryQuestions(categoryIndex);
                    currentQuestionIndex++;
                    System.out.println("i:" + currentQuestionIndex);
                    return shuffledQuestions.get(currentQuestionIndex);
//                    return questions[currentQuestionIndex];
                } else {
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    game.addNumOfPlayersAnswered();
                    if (game.getNumOfPlayersAnswered() == 2) {
                        game.addRoundsPlayed();
                        System.out.println("Rounds: " + game.getRoundsPlayed());
                        if (game.getRoundsPlayed() < game.getNumOfRoundsPerGame()) {
                            //skicka kategorier
                            game.resetQuestionsAnswered();
                            game.resetNumOfPlayersAnswered();
                            shuffledCategories = quiz.categories();
                            return new Response("CATEGORY", shuffledCategories);
                        } else {
                            //skicka resultat till båda spelarna
                            game.resetRoundsPlayed();
                            return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), true);
                        }
                    } else {
                        //skicka första frågan (samma som innan)
                        game.resetQuestionsAnswered();
                        currentQuestionIndex = 0;
                        return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), false);
                    }
                }
            } else if (input.equals("next player")) {
//                Questions[] questions = quiz.getCategoryQuestions(categoryIndex);
//                currentQuestionIndex = 0;
                System.out.println("i:" + currentQuestionIndex);
                return shuffledQuestions.get(currentQuestionIndex);
//                return questions[currentQuestionIndex];

            }
        }
        return new Response("ERROR", null);
    }


}
