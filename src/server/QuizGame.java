package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class QuizGame extends Thread {

    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private int numOfRounds;
    private int numOfQuestionsPerRound;

    public QuizGame(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        Properties p = new Properties();
        try {
            p.load(new FileInputStream("src/settings.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        numOfRounds = Integer.parseInt(p.getProperty("numOfRounds", "3"));
        numOfQuestionsPerRound = Integer.parseInt(p.getProperty("numOfQuestionsPerRound", "3"));
    }

    @Override
    public void run() {

        System.out.println(player1.getName() + " " + player1.receive());
        System.out.println(player2.getName() + " " + player2.receive());

        activePlayer = player1;
        activePlayer.send("CATEGORY");
        String input;
        while (true) {
            //välj kategori
            //svara på frågor
            //spara resultat

            try {
                input = activePlayer.receive();
                if (input.startsWith("category chosen")) {
                    System.out.println(activePlayer.getName() + " " + input);
                    activePlayer.send("QUESTION");
                } else if (input.startsWith("question answered")) {
                    System.out.println(activePlayer.getName() + " " + input);
                    changeActivePlayer();
//                    activePlayer.send("CATEGORY");
                }
            } catch (RuntimeException e) {
                System.out.println("Active player disconnected");
                break;
            }
        }

    }

    public void changeActivePlayer() {
        activePlayer.send("WAIT");
        activePlayer = activePlayer.getOpponent();
        activePlayer.send("QUESTION");
    }
}
