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
    private Questions[] questions;
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

    public Object getOutput(String input) {

        if (state == INITIAL) {

            for(QuizCategory category : quiz.getCategories()) {
                categories.add(category);
            }
            shuffledCategories = quiz.categories();
            state = GAMELOOP;
            return new Response("CATEGORY", shuffledCategories);
        } else if (state == GAMELOOP) {
            if (input.startsWith("chosen category")) {
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

            } else if (input.startsWith("answered")) {

                if (input.substring(input.length() - 1).equals(shuffledQuestions.get(currentQuestionIndex).getAnswer())) {
                    game.getActivePlayer().setPoints(game.getActivePlayer().getPoints() + 1);
                }
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    currentQuestionIndex++;
                    System.out.println("i:" + currentQuestionIndex);
                    return shuffledQuestions.get(currentQuestionIndex);
                } else {
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    game.addNumOfPlayersAnswered();
                    if (game.getNumOfPlayersAnswered() == 2) {
                        game.addRoundsPlayed();
                        System.out.println("Rounds: " + game.getRoundsPlayed());
                        if (game.getRoundsPlayed() < game.getNumOfRoundsPerGame()) {
                            game.resetQuestionsAnswered();
                            game.resetNumOfPlayersAnswered();
                            shuffledCategories = quiz.categories();
                            return new Response("CATEGORY", shuffledCategories);
                        } else {
                            game.resetRoundsPlayed();
                            return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), true);
                        }
                    } else {
                        game.resetQuestionsAnswered();
                        currentQuestionIndex = 0;
                        return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), false);
                    }
                }
            } else if (input.equals("next player")) {
                System.out.println("i:" + currentQuestionIndex);
                return shuffledQuestions.get(currentQuestionIndex);

            }
        }
        return new Response("ERROR", null);
    }


}
