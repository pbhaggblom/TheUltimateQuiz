package GameGUI;

import GameLogic.Questions;
import GameLogic.TheQuiz;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    JPanel panelStart = new JPanel();
    public JPanel panelCategories = new JPanel();
    public JPanel panelQuestions = new JPanel();
    JLabel label = new JLabel("Quiz Game");
    public JButton startButton = new JButton();
    public JButton category2 = new JButton();
    public JButton answer1 = new JButton();
    public JButton category1 = new JButton();
    public JButton answer2 = new JButton();
    public JButton answer3 = new JButton();
    public JButton answer4 = new JButton();
    public TheQuiz theQuiz;


    public GameWindow() {
        setTitle("Quiz Game");
        panelStart.setLayout(new GridBagLayout());
        startWindow();

        setSize(400, 700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        theQuiz = new TheQuiz();
    }

    public void startWindow() {
        StartWindow startWindow = new StartWindow(panelStart, startButton, 0, 1,
                "Starta nytt spel", label);
        startWindow.changePanel(panelStart);
        panelStart = startWindow.changePanelContent();
        add(panelStart);

        startButton.addActionListener(e -> {
            panelStart.setVisible(false);
//            categoryWindow();
            panelCategories.setVisible(false);
        });
    }

    public void categoryWindow() {
        CategoryWindow categoryWindow = new CategoryWindow(panelCategories, category1, category2, 0, 0, "Kategori 1", "Kategori2");
        categoryWindow.changePanel(panelCategories);
        panelCategories = categoryWindow.changePanelContent();
        add(panelCategories);

        category1.addActionListener(e -> {
            panelCategories.setVisible(false);
//            questionsWindow();
            christmasQuestions();

            {}
        });
        category2.addActionListener(e -> {
            panelCategories.setVisible(false);
//            questionsWindow();
            animalQuestions();

        });
    }

    public void christmasQuestions() {
        Questions[] questions = theQuiz.getCategoryQuestions(1);
    }

    public void animalQuestions() {
        Questions[] questions = theQuiz.getCategoryQuestions(2);
    }

    public void nutritionQuestions() {
        Questions[] questions = theQuiz.getCategoryQuestions(3);
    }

    public void questionsWindow() {
        QuestionsWindow questionsWindow = new QuestionsWindow(panelQuestions, answer1,
                answer2, answer3, answer4, 0, 0,
                "Svar 1", "Svar 2", "Svar 3", "Svar 4");
        questionsWindow.changePanel(panelQuestions);
        panelQuestions = questionsWindow.changePanelContent();
        add(panelQuestions);

        answer1.addActionListener(e -> {
            System.out.println("Du valde svar 1");
//            categoryWindow();
        });
        answer2.addActionListener(e -> {
            System.out.println("Du valde svar 2");
//            categoryWindow();
        });
        answer3.addActionListener(e -> {
            System.out.println("Du valde svar 3");
//            categoryWindow();
        });
        answer4.addActionListener(e -> {
            System.out.println("Du valde svar 4");
//            categoryWindow();
        });

    }
}
