package server;

import java.io.*;
import java.net.Socket;

public class Player {

    Socket socket;
    String name;
    BufferedReader in;
    ObjectOutputStream out;
    Player opponent;

    public Player(Socket socket, String name) {
        this.socket = socket;
        this.name = name;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String receive() {

        try {
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void send(Response r) {
        try {
            out.writeObject(r);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public String getName() {
        return name;
    }
}
