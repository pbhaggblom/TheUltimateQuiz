package server;

import java.io.*;
import java.net.Socket;

public class Player {

    Socket socket;
    String name;
    BufferedReader in;
    ObjectOutputStream out;
    Player opponent;
    int points;

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

    public void send(Object obj) {
        try {
            out.writeObject(obj);
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

    public void setPoints(int points) {this.points = points;}

    public int getPoints() {return points;}
}
