package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuizGame extends Thread {

    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private final int numOfRoundsPerGame;
    private final int numOfQuestionsPerRound;
    private int roundsPlayed;
    private int questionsAnswered;
    private int numOfPlayersAnswered;

    public QuizGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        numOfRoundsPerGame = Integer.parseInt(p.getProperty("numOfRoundsPerGame", "3"));
        numOfQuestionsPerRound = Integer.parseInt(p.getProperty("numOfQuestionsPerRound", "3"));
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public int getNumOfRoundsPerGame() {
        return numOfRoundsPerGame;
    }

    public int getNumOfQuestionsPerRound() {
        return numOfQuestionsPerRound;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public int getNumOfPlayersAnswered() {
        return numOfPlayersAnswered;
    }

    public void addQuestionsAnswered() {
        questionsAnswered++;
    }

    public void addRoundsPlayed() {
        roundsPlayed++;
    }

    public void addNumOfPlayersAnswered() {
        numOfPlayersAnswered++;
    }

    public void resetQuestionsAnswered() {
        questionsAnswered = 0;
    }

    public void resetRoundsPlayed() {
        roundsPlayed = 0;
    }

    public void resetNumOfPlayersAnswered() {
        numOfPlayersAnswered = 0;
    }

    @Override
    public void run() {

        ServerProtocol sp = new ServerProtocol(this);

        int rounds = 0;
        int questions = 0;
        int numOfPlayersAnswered = 0;

        System.out.println(player1.getName() + " " + player1.receive());
        System.out.println(player2.getName() + " " + player2.receive());

        activePlayer = player1;
        activePlayer.send(new Response("CATEGORY", sp.getQuiz().categories()));
        String input;

        while (true) {

            try {
                input = activePlayer.receive();
                if (input != null) {
                    activePlayer.send(sp.getOutput(input));
                }

//                if (input.startsWith("chosen category")) {
//                    System.out.println(activePlayer.getName() + " " + input);
//                    activePlayer.send("QUESTION");
//                } else if (input.startsWith("answered")) {
//                    questions++;
//                    if (questions < numOfQuestionsPerRound) {
//                        System.out.println(activePlayer.getName() + " " + input);
//                        System.out.println("Questions: " + questions);
//                        activePlayer.send("QUESTION");
//                    } else {
//                        System.out.println(activePlayer.getName() + " " + input);
//                        System.out.println("Questions: " + questions);
//                        numOfPlayersAnswered++;
//                        if (numOfPlayersAnswered == 2) {
//                            rounds++;
//                            System.out.println("Rounds: " + rounds);
//                            if (rounds < numOfRoundsPerGame) {
//                                questions = 0;
//                                numOfPlayersAnswered = 0;
//                                activePlayer.send("CATEGORY");
//                            } else {
//                                rounds = 0;
//                                player1.send("RESULT");
//                                player2.send("RESULT");
//                                System.out.println("Game ended");
//                            }
//                        } else {
//                            questions = 0;
//                            activePlayer.send("WAIT");
//                            activePlayer = activePlayer.getOpponent();
//                            activePlayer.send("QUESTION");
//                        }
//                    }
//                }
            } catch (RuntimeException e) {
                System.out.println("Active player disconnected");
                break;
            }
        }

    }
}
