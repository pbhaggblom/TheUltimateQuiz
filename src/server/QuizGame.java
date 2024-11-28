package server;

import GameGUI.ResultWindow;

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
        String p1 = player1.receive();
        String p2 = player2.receive();

        player1.setName(p1);
        player2.setName(p2);

        System.out.println(player1.getName() + " connected");
        System.out.println(player2.getName() + " connected");
        activePlayer = player1;

        activePlayer.send(sp.getOutput(null));
        String input;

        while (true) {

            try {
                input = activePlayer.receive();
                if (input != null) {
                    Object obj = sp.getOutput(input);
                    if (obj instanceof ResultResponse) {
                        ResultResponse res = (ResultResponse) obj;
                        if (res.isFinal()) {
                            activePlayer.send(new ResultResponse(getActivePlayer(), getActivePlayer().getOpponent(), true));
                            activePlayer = activePlayer.getOpponent();
                            activePlayer.send(new ResultResponse(getActivePlayer(), getActivePlayer().getOpponent(), true));
                            System.out.println("Game ended");
                        } else {
                            activePlayer.send(new Response("WAIT", null));
                            activePlayer = activePlayer.getOpponent();
                            activePlayer.send(sp.getOutput("next player"));
                        }
                    } else {
                        if (obj instanceof Response) {
                            Response res = (Response) obj;
                            if (res.getType().equals("CATEGORY")) {
                                showRoundResult(new ResultResponse(getActivePlayer(), getActivePlayer().getOpponent(), false));
                                activePlayer.send(res);
                                activePlayer.getOpponent().send(new Response("WAIT", null));
                            }
                        }
                        activePlayer.send(obj);
                    }
                    activePlayer.out.reset();
                }
            } catch (RuntimeException e) {
                e.printStackTrace();
                break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void showRoundResult(ResultResponse res) {
            player1.send(res);
            player2.send(res);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
    }

}
