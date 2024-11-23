package server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public Server() {
        try (ServerSocket ss = new ServerSocket(55555)) {
            while (true) {
                Player player1 = new Player(ss.accept(), "Player 1");
                player1.send("WAIT");
                Player player2 = new Player(ss.accept(), "Player 2");
                player2.send("WAIT");
                player1.setOpponent(player2);
                player2.setOpponent(player1);
                QuizGame newGame = new QuizGame(player1, player2);
                newGame.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
