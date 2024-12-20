package GameGUI;

import server.Player;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    JPanel panelStart = new JPanel();
    public JPanel panelCategories = new JPanel();
    public JPanel panelQuestions = new JPanel();
    public JPanel panelWait = new JPanel();
    JLabel label = new JLabel("Quiz Game");
    public JButton startButton = new JButton();
    public JButton category2 = new JButton();
    public JButton answer1 = new JButton();
    public JButton category1 = new JButton();
    public JButton answer2 = new JButton();
    public JButton answer3 = new JButton();
    public JButton answer4 = new JButton();
    public JLabel question = new JLabel();
    public JPanel panelResult = new JPanel();
    public JTextField textField = new JTextField(20);

    public GameWindow() {
        setTitle("Quiz Game");
        panelStart.setLayout(new GridBagLayout());
        startWindow();

        setSize(700, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    public void startWindow() {
        StartWindow startWindow = new StartWindow(panelStart, startButton,textField, 0, 1,
                 "Starta nytt spel", label);
        startWindow.changePanel(panelStart);
        panelStart = startWindow.changePanelContent();
        add(panelStart);

        startButton.addActionListener(e -> {
            panelStart.setVisible(false);
            panelCategories.setVisible(false);
        });
    }

    public void categoryWindow() {
        CategoryWindow categoryWindow = new CategoryWindow(panelCategories, category1,
                category2, 0, 0, "Kategori 1", "Kategori 2");
        categoryWindow.changePanel(panelCategories);
        panelCategories = categoryWindow.changePanelContent();
        add(panelCategories);

        category1.addActionListener(e -> {
            panelCategories.setVisible(false);
        });
        category2.addActionListener(e -> {
            panelCategories.setVisible(false);
        });
    }

    public void questionsWindow() {
        QuestionsWindow questionsWindow = new QuestionsWindow(panelQuestions, answer1,
                answer2, answer3, answer4, 0, 2,
                "Svar 1", "Svar 2", "Svar 3", "Svar 4", question);
        questionsWindow.changePanel(panelQuestions);
        panelQuestions = questionsWindow.changePanelContent();
        add(panelQuestions);

    }

    public void resultWindow(String player1, String player2, int scorePlayer1, int scorePlayer2) {
        ResultWindow resultWindow = new ResultWindow(panelResult, player1, player2, scorePlayer1, scorePlayer2);
        resultWindow.changePanel(panelResult);
        panelResult = resultWindow.changePanelContent();
        add(panelResult);
    }

    public void waitWindow() {
        WaitWindow waitWindow = new WaitWindow(panelWait);
        waitWindow.changePanel(panelWait);
        panelWait = waitWindow.changePanelContent();
        add(panelWait);
    }

}
