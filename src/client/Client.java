package client;

import GameGUI.GameWindow;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    PrintWriter out;
    BufferedReader in;
    Socket s;
    GameWindow gw;

    public Client() {

        gw = new GameWindow();

        gw.startButton.addActionListener(e -> {
            connectToServer();
        });
        gw.category1.addActionListener(e -> out.println("chosen category: 1"));
        gw.category2.addActionListener(e -> out.println("chosen category: 2"));
        gw.answer1.addActionListener(e -> out.println("answered: 1"));
        gw.answer2.addActionListener(e -> out.println("answered: 2"));
        gw.answer3.addActionListener(e -> out.println("answered: 3"));
        gw.answer4.addActionListener(e -> out.println("answered: 4"));

    }

    public void connectToServer() {
        try {
            s = new Socket("127.0.0.1", 55555);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            out.println("connected");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listen();
    }

    public void listen() {
        SwingUtilities.invokeLater(() -> {
            try {
                if (in.ready()) {
                    String fromServer = in.readLine();
                    System.out.println("Server:" + fromServer);
                    if (fromServer.equals("WAIT")) {
                        System.out.println("Waiting for opponent");
                        gw.panelCategories.setVisible(false);
                        gw.panelQuestions.setVisible(false);
                    } else if (fromServer.equals("CATEGORY")) {
                        gw.panelQuestions.setVisible(false);
                        gw.categoryWindow();
                        gw.panelCategories.setVisible(true);
                    } else if (fromServer.equals("QUESTION")) {
                        gw.panelQuestions.setVisible(false);
                        gw.questionsWindow();
                        gw.panelQuestions.setVisible(true);
//                        System.out.println("your turn");
                    } else if (fromServer.equals("RESULT")) {
                        gw.panelCategories.setVisible(false);
                        gw.panelQuestions.setVisible(false);
                        System.out.println("Game finished");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from server");
                return;
            }
            listen();
        });
    }

    public static void main(String[] args) {
        new Client();
    }

}
