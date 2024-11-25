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
        gw.category1.addActionListener(e -> out.println("chosen category: 0"));
        gw.category2.addActionListener(e -> out.println("chosen category: 1"));
        gw.answer1.addActionListener(e -> out.println("answered: 0"));
        gw.answer2.addActionListener(e -> out.println("answered: 1"));
        gw.answer3.addActionListener(e -> out.println("answered: 2"));
        gw.answer4.addActionListener(e -> out.println("answered: 3"));

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

        new Thread(() -> {
            while (true) {
                try {
                    Object fromServer = in.readObject();
                    SwingUtilities.invokeLater(() -> handleResponse(fromServer));
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

//        SwingUtilities.invokeLater(() -> {
//            try {
//                Object fromServer = in.readObject();
//                System.out.println(fromServer);
//                if (fromServer instanceof Response) {
//                    Response r = (Response) fromServer;
//                    if (r != null) {
//                        System.out.println("Server:" + r.getType());
//                        if (r.getType().equals("WAIT")) {
//                            System.out.println("Waiting for opponent");
//                            gw.panelCategories.setVisible(false);
//                            gw.panelQuestions.setVisible(false);
//                        } else if (r.getType().equals("CATEGORY")) {
//                            System.out.println("inside category");
//                            gw.panelQuestions.setVisible(false);
//                            gw.categoryWindow();
//                            gw.panelCategories.setVisible(true);
//                            gw.category1.setText(r.getResponseList().get(0));
//                            gw.category2.setText(r.getResponseList().get(1));
//                        } else if (r.getType().equals("QUESTION")) {
//                            gw.panelQuestions.setVisible(false);
//                            gw.questionsWindow();
//                            gw.panelQuestions.setVisible(true);
////                        System.out.println("your turn");
//                        } else if (r.getType().equals("RESULT")) {
//                            gw.panelCategories.setVisible(false);
//                            gw.panelQuestions.setVisible(false);
//                            System.out.println("Game finished");
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                System.out.println("Error reading from server");
//                return;
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//            listen();
//        });
    }

    public void handleResponse(Object obj) {
        if (obj instanceof Response) {
            Response r = (Response) obj;
            if (r != null) {
                System.out.println("Server:" + r.getType());
                if (r.getType().equals("WAIT")) {
                    System.out.println("Waiting for opponent");
                    gw.panelCategories.setVisible(false);
                    gw.panelQuestions.setVisible(false);
                } else if (r.getType().equals("CATEGORY")) {
                    System.out.println("inside category");
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
        }

    }

    public static void main(String[] args) {
        new Client();
    }

}
