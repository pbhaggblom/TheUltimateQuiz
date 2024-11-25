package client;

import GameGUI.GameWindow;
import server.Response;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class Client {

    PrintWriter out;
    ObjectInputStream in;
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
            in = new ObjectInputStream(s.getInputStream());
            out.println("connected");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        listen();
    }

    public void listen() {
        SwingUtilities.invokeLater(() -> {
            try {
                Object fromServer = in.readObject();
                Response r = (Response) fromServer;
                if (r != null) {
                    System.out.println("Server:" + fromServer);
                    if (r.getType().equals("WAIT")) {
                        System.out.println("Waiting for opponent");
                        gw.panelCategories.setVisible(false);
                        gw.panelQuestions.setVisible(false);
                    } else if (r.getType().equals("CATEGORY")) {

                        gw.panelQuestions.setVisible(false);
                        gw.categoryWindow();
                        gw.panelCategories.setVisible(true);
                        gw.category1.setText(r.getResponseList().get(0));
                        gw.category2.setText(r.getResponseList().get(1));
                    } else if (r.getType().equals("QUESTION")) {
                        gw.panelQuestions.setVisible(false);
                        gw.questionsWindow();
                        gw.panelQuestions.setVisible(true);
//                        System.out.println("your turn");
                    } else if (r.getType().equals("RESULT")) {
                        gw.panelCategories.setVisible(false);
                        gw.panelQuestions.setVisible(false);
                        System.out.println("Game finished");
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading from server");
                return;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            listen();
        });
    }

    public static void main(String[] args) {
        new Client();
    }

}
