package client;

import GameGUI.GameWindow;
import GameLogic.Questions;
import server.Response;
import server.ResultResponse;

import javax.swing.*;
import java.awt.*;
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
                    if (fromServer instanceof ResultResponse) {
                        ResultResponse r = (ResultResponse) fromServer;
//                        System.out.println(r.ge);
                    }
                    SwingUtilities.invokeLater(() -> handleResponse(fromServer));
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    public void handleResponse(Object obj) {
        if (obj instanceof Response) {
            Response r = (Response) obj;
            System.out.println("Server:" + r.getType());
            if (r.getType().equals("CATEGORY")) {
                gw.panelQuestions.setVisible(false);
                gw.panelWait.setVisible(false);
                gw.categoryWindow();
                gw.panelCategories.setVisible(true);
                gw.category1.setText(r.getResponseList().get(0));
                gw.category2.setText(r.getResponseList().get(1));
            } else if (r.getType().equals("WAIT")) {
                System.out.println("Waiting for opponent");
                gw.panelCategories.setVisible(false);
                gw.panelQuestions.setVisible(false);
                gw.waitWindow();
                gw.panelWait.setVisible(true);
            }
        } else if (obj instanceof Questions) {
            Questions q = (Questions) obj;
            gw.panelQuestions.setVisible(false);
            gw.panelWait.setVisible(false);
            gw.questionsWindow();
            gw.panelQuestions.setVisible(true);
            gw.question.setText(q.getQuestion());
            gw.answer1.setText(q.getOptions()[0]);
            gw.answer2.setText(q.getOptions()[1]);
            gw.answer3.setText(q.getOptions()[2]);
            gw.answer4.setText(q.getOptions()[3]);
        } else if (obj instanceof ResultResponse) {
            ResultResponse rr = (ResultResponse) obj;
//            if (rr.isFinal()) {
                //visa resultatpanel
                System.out.println(rr.getPlayer());
                System.out.println(rr.getOpponent());
                gw.panelCategories.setVisible(false);
                gw.panelQuestions.setVisible(false);
                gw.panelWait.setVisible(false);
                gw.resultWindow(rr.getPlayer(), rr.getOpponent(), rr.getPlayerResult(), rr.getOpponentResult());
                gw.panelResult.setVisible(true);
                System.out.println(rr.getPlayerResult());
                System.out.println("Game ended hejsan");
//            } else {
//                System.out.println("Waiting for " + rr.getOpponent());
//                gw.panelCategories.setVisible(false);
//                gw.panelQuestions.setVisible(false);
//            }

        }
    }

    public static void main(String[] args) {
        new Client();
    }

}
