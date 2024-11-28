package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

public class Server {

    public Server() {
        try (ServerSocket ss = new ServerSocket(55555)) {
            while (true) {
                Player player1 = new Player(ss.accept(), "Player 1");
                player1.send(new Response("WAIT", List.of(player1.getName())));
                Player player2 = new Player(ss.accept(), "Player 2");
                player2.send(new Response("WAIT", List.of(player2.getName())));
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
