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
    Questions currentQuestion;
    private String userName;

    public Client() {

        gw = new GameWindow();

        gw.startButton.addActionListener(e -> {
            if (!gw.textField.getText().isBlank()) {
                userName = gw.textField.getText().trim();
                gw.setTitle(gw.getTitle() + " (" + userName + ")");
            }
            connectToServer();
        });
        gw.category1.addActionListener(e -> out.println("chosen category: 0"));
        gw.category2.addActionListener(e -> out.println("chosen category: 1"));
        gw.answer1.addActionListener(e -> {
            checkAnswer("0");
            disableableAnswerButtons();
        });
        gw.answer2.addActionListener(e -> {
            checkAnswer("1");
            disableableAnswerButtons();
        });
        gw.answer3.addActionListener(e -> {
            checkAnswer("2");
            disableableAnswerButtons();
        });
        gw.answer4.addActionListener(e -> {
            checkAnswer("3");
            disableableAnswerButtons();
        });

    }

    public void connectToServer() {
        try {
            s = new Socket("127.0.0.1", 55555);
            out = new PrintWriter(s.getOutputStream(), true);
            in = new ObjectInputStream(s.getInputStream());

            out.println(userName);
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
    }

    public void handleResponse(Object obj) {
        if (obj instanceof Response) {
            Response r = (Response) obj;
            System.out.println("Server:" + r.getType());
            if (r.getType().equals("CATEGORY")) {
                gw.panelQuestions.setVisible(false);
                gw.panelWait.setVisible(false);
                gw.panelResult.setVisible(false);
                gw.categoryWindow();
                gw.panelCategories.setVisible(true);
                gw.category1.setText(r.getResponseList().get(0));
                gw.category2.setText(r.getResponseList().get(1));
            } else if (r.getType().equals("WAIT")) {
                System.out.println("Waiting for opponent");
                gw.panelCategories.setVisible(false);
                gw.panelQuestions.setVisible(false);
                gw.panelResult.setVisible(false);
                gw.waitWindow();
                gw.panelWait.setVisible(true);
            }
        } else if (obj instanceof Questions) {
            Questions q = (Questions) obj;
            enableAnswerButtons();
            currentQuestion = q;
            gw.panelQuestions.setVisible(false);
            gw.panelWait.setVisible(false);
            gw.panelResult.setVisible(false);
            gw.questionsWindow();
            gw.panelQuestions.setVisible(true);
            gw.question.setText(q.getQuestion());
            gw.answer1.setText(q.getOptions()[0]);
            gw.answer2.setText(q.getOptions()[1]);
            gw.answer3.setText(q.getOptions()[2]);
            gw.answer4.setText(q.getOptions()[3]);
        } else if (obj instanceof ResultResponse) {
            ResultResponse rr = (ResultResponse) obj;
            if (rr.isFinal()) {
                //visa resultatpanel
                System.out.println(rr.getPlayer());
                System.out.println(rr.getOpponent());
                gw.panelCategories.setVisible(false);
                gw.panelQuestions.setVisible(false);
                gw.panelResult.setVisible(false);
                gw.panelWait.setVisible(false);
                gw.panelResult.removeAll();
                gw.resultWindow(rr.getPlayer(), rr.getOpponent(), rr.getPlayerResult(), rr.getOpponentResult());
                gw.panelResult.setVisible(true);
                System.out.println(rr.getPlayerResult());
                System.out.println("Game ended");
            } else {
                System.out.println("round result");
                gw.panelCategories.setVisible(false);
                gw.panelQuestions.setVisible(false);
                gw.panelWait.setVisible(false);
                gw.panelResult.setVisible(false);
                gw.panelResult.removeAll();
                gw.resultWindow(rr.getPlayer(), rr.getOpponent(), rr.getPlayerResult(), rr.getOpponentResult());
                gw.panelResult.setVisible(true);
            }

        }
    }

    public void checkAnswer(String answer) {
        new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                if (answer.equals(currentQuestion.getAnswer())) {
                    getAnswerButton(answer).setBackground(Color.GREEN);
                } else {
                    getAnswerButton(answer).setBackground(Color.RED);
                    getAnswerButton(currentQuestion.getAnswer()).setBackground(Color.GREEN);
                }
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            SwingUtilities.invokeLater(() -> {
                getAnswerButton(answer).setBackground(null);
                getAnswerButton(currentQuestion.getAnswer()).setBackground(null);
                sendResponse(answer);
            });

        }).start();

    }

    public JButton getAnswerButton(String answer) {
        switch (answer) {
            case "0":
                return gw.answer1;
            case "1":
                return gw.answer2;
            case "2":
                return gw.answer3;
            case "3":
                return gw.answer4;
            default:
                return null;
        }
    }

    public void sendResponse(String res) {
        out.println("answered: " + res);
    }

    public void enableAnswerButtons() {
        gw.answer1.setEnabled(true);
        gw.answer2.setEnabled(true);
        gw.answer3.setEnabled(true);
        gw.answer4.setEnabled(true);
    }

    public void disableableAnswerButtons() {
        gw.answer1.setEnabled(false);
        gw.answer2.setEnabled(false);
        gw.answer3.setEnabled(false);
        gw.answer4.setEnabled(false);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static void main(String[] args) {
        new Client();
    }

}
