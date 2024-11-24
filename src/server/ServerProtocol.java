package server;

public class ServerProtocol {

    private QuizGame game;
    final protected int INITIAL = 0;
    final protected int GAMELOOP = 1;
    final protected int GAME_ENDED = 2;

    protected int state = INITIAL;

    public ServerProtocol(QuizGame game) {
        this.game = game;
    }

    public String getOutput(String input) {
        String output;

        if (state == INITIAL) {
            state = GAMELOOP;
            return "CATEGORY";
        } else if (state == GAMELOOP) {
            if (input.startsWith("chosen category")) {
                System.out.println(game.getActivePlayer().getName() + " " + input);
                return "QUESTION";
            } else if (input.startsWith("answered")) {
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    System.out.println(game.getActivePlayer().getName() + " " + input);
                    System.out.println("Questions: " + game.getQuestionsAnswered());
                    return "QUESTION";
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
                            return "CATEGORY";
                        } else {
                            game.resetRoundsPlayed();
//                            player1.send("RESULT");
//                            player2.send("RESULT");
                        }
                    } else {
                        game.resetQuestionsAnswered();
//                        activePlayer.send("WAIT");
//                        activePlayer = activePlayer.getOpponent();
//                        activePlayer.send("GAMELOOP");
                    }
                }
            }
        }
        return "unexpected error";
    }


}
