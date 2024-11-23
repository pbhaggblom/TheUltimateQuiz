package GameGUI;

import javax.swing.*;
import java.awt.*;

public class QuestionsWindow extends JFrame implements PanelHandler {

    JPanel panelQuestions;
    JButton question1, question2, question3, question4;
    int x, y;
    String text1, text2, text3, text4;

    public QuestionsWindow(JPanel panelQuestions, JButton question1,
                           JButton question2, JButton question3, JButton question4,
                           int x, int y, String text1, String text2,
                           String text3, String text4) {
        this.panelQuestions = panelQuestions;
        this.question1 = question1;
        this.question2 = question2;
        this.question3 = question3;
        this.question4 = question4;
        this.x = x;
        this.y = y;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.text4 = text4;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        GridBagConstraints c = new GridBagConstraints();
        question1.setPreferredSize(new Dimension(150, 70));
        question1.setSize(100, 40);
        question1.setText(text1);
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(question1, c);

        question2.setPreferredSize(new Dimension(150, 70));
        question2.setSize(100, 40);
        question2.setText(text2);
        c.gridx = 1;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(question2, c);

        question3.setPreferredSize(new Dimension(150, 70));
        question3.setSize(100, 40);
        question3.setText(text3);
        c.gridx = x;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(question3, c);

        question4.setPreferredSize(new Dimension(150, 70));
        question4.setSize(100, 40);
        question4.setText(text4);
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 10, 10);
        panelQuestions.add(question4, c);

        return panelQuestions;
    }
}
