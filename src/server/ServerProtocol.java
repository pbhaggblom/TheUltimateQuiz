package server;

import GameLogic.Questions;
import GameLogic.QuizCategory;
import GameLogic.TheQuiz;

import java.util.ArrayList;
import java.util.List;

public class ServerProtocol {

    private QuizGame game;
    private TheQuiz quiz;

    private List<QuizCategory> categories;
    private List<String> questions;
    private QuizCategory currentCategory;
    private Questions[] currentQuestion;
    private int currentQuestionIndex;
    private int currentCategoryIndex;

    final protected int INITIAL = 0;
    final protected int GAMELOOP = 1;
    final protected int GAME_ENDED = 2;

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

            List<QuizCategory> list = new ArrayList<>();
            for (QuizCategory category : quiz.getCategories()) {
                list.add(category);
            }
            state = GAMELOOP;
            return new Response("CATEGORY", quiz.categories());
        } else if (state == GAMELOOP) {
            if (input.startsWith("chosen category")) {
                currentCategoryIndex = Integer.parseInt(input.split(" ")[1]);
                currentQuestion = quiz.getCategoryQuestions(currentCategoryIndex+1);
                currentQuestionIndex = 0;
                //hämta frågor från vald kategori
                //skicka första frågan
                Questions firstQuestion = currentQuestion[currentQuestionIndex];
                System.out.println(game.getActivePlayer().getName() + " " + input);
                return new Response("QUESTION", null);
            } else if (input.startsWith("answered")) {
                //kolla om svar är rätt
                int selectedAnswer = Integer.parseInt(input.split(" ")[1]);
                Questions currentQuestionANDRA =currentQuestion[currentQuestionIndex];
                //ge poäng
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    //skicka nästa fråga
                    Questions nextQuestion = currentQuestion[currentQuestionIndex];
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    return new Response("QUESTION", null);
                } else {
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    game.addNumOfPlayersAnswered();
                    if (game.getNumOfPlayersAnswered() == 2) {
                        game.addRoundsPlayed();
                        System.out.println("Rounds: " + game.getRoundsPlayed());
                        if (game.getRoundsPlayed() < game.getNumOfRoundsPerGame()) {
                            //skicka kategorier
                            //extra rad
                            game.resetQuestionsAnswered();
                            game.resetNumOfPlayersAnswered();
                            return new Response("CATEGORY", quiz.categories());
                        } else {
                            //skicka resultat till båda spelarna
                            game.resetRoundsPlayed();
                            return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), true);
                        }
                    } else {
                        //skicka första frågan (samma som innan)
                        game.resetQuestionsAnswered();
                        return new ResultResponse(game.getActivePlayer(), game.getActivePlayer().getOpponent(), false);
                    }
                }
            } else if (input.equals("next player")) {
                return new Response("QUESTION", null);
            }
        }
        return new Response("ERROR", null);
    }


}
