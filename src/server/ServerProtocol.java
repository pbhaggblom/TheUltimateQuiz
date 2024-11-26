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
    private Questions currentQuestion;

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

    public Response getOutput(String input) {
        Response output;

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
                //hämta frågor från vald kategori
                //skicka första frågan
                System.out.println(game.getActivePlayer().getName() + " " + input);
                return new Response("QUESTION", null);
            } else if (input.startsWith("answered")) {
                //kolla om svar är rätt
                //ge poäng
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    //skicka nästa fråga
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
                            game.resetQuestionsAnswered();
                            game.resetNumOfPlayersAnswered();
                            return new Response("CATEGORY", quiz.categories());
                        } else {
                            //skicka resultat till båda spelarna
                            game.resetRoundsPlayed();
                            return new Response("RESULT", null);
                        }
                    } else {
                        //skicka första frågan (samma som innan)
                        game.resetQuestionsAnswered();
                        return new Response("WAIT", null);
                    }
                }
            } else if (input.equals("next player")) {
                return new Response("QUESTION", null);
            }
        }
        return new Response("ERROR", null);
    }


}
