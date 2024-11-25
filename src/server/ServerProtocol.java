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
            //skicka kategorier
            state = GAMELOOP;
            return "CATEGORY";
        } else if (state == GAMELOOP) {
            if (input.startsWith("chosen category")) {
                //hämta frågor från vald kategori
                //skicka första frågan
                System.out.println(game.getActivePlayer().getName() + " " + input);
                return "QUESTION";
            } else if (input.startsWith("answered")) {
                //kolla om svar är rätt
                //ge poäng
                game.addQuestionsAnswered();
                if (game.getQuestionsAnswered() < game.getNumOfQuestionsPerRound()) {
                    //skicka nästa fråga
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
                            //skicka kategorier
                            game.resetQuestionsAnswered();
                            game.resetNumOfPlayersAnswered();
                            return "CATEGORY";
                        } else {
                            //skicka resultat till båda spelarna
                            game.resetRoundsPlayed();
//                            player1.send("RESULT");
//                            player2.send("RESULT");
                        }
                    } else {
                        //skicka första frågan (samma som innan)
                        game.resetQuestionsAnswered();
//                        activePlayer.send("WAIT");
//                        activePlayer = activePlayer.getOpponent();
//                        activePlayer.send("QUESTION");
                    }
                }
            }
        }
        return "unexpected error";
    }


}
