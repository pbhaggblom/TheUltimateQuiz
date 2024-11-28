package GameGUI;

import javax.swing.*;
import java.awt.*;

public class QuestionsWindow extends JFrame implements PanelHandler {

    JPanel panelQuestions;
    JButton answer1, answer2, answer3, answer4;
    int x, y;
    String text1, text2, text3, text4;
    JLabel question;

    public QuestionsWindow(JPanel panelQuestions, JButton answer1,
                           JButton answer2, JButton answer3, JButton question4,
                           int x, int y, String text1, String text2,
                           String text3, String text4, JLabel question) {
        this.panelQuestions = panelQuestions;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = question4;
        this.x = x;
        this.y = y;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
        this.question = question;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        GridBagConstraints c = new GridBagConstraints();
        question.setText("Hejsan hoppsan nu ska vi se om detta kanske går bättre");
        question.setFont(new Font("Serif", Font.BOLD, 15));
        c.insets = new Insets(0, 0, 10, 0);
        c.gridx = 0;
        c.gridy = 0;
        panelQuestions.add(question, c);

        answer1.setPreferredSize(new Dimension(150, 70));
        answer1.setSize(100, 40);
        answer1.setText(text1);
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(answer1, c);

        answer2.setPreferredSize(new Dimension(150, 70));
        answer2.setSize(100, 40);
        answer2.setText(text2);
        c.gridx = 1;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(answer2, c);

        answer3.setPreferredSize(new Dimension(150, 70));
        answer3.setSize(100, 40);
        answer3.setText(text3);
        c.gridx = x;
        c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(answer3, c);

        answer4.setPreferredSize(new Dimension(150, 70));
        answer4.setSize(100, 40);
        answer4.setText(text4);
        c.gridx = 1;
        c.gridy = 3;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(answer4, c);


        return panelQuestions;
    }
}
