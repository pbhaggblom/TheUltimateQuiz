package GameGUI;

import javax.swing.*;
import java.awt.*;

public class ResultWindow extends JFrame implements PanelHandler {
    String player1;
    String player2;
    int scorePlayer1;
    int scorePlayer2;
    JPanel panelResult;

    public ResultWindow(JPanel panelResult, String player1, String player2, int scorePlayer1, int scorePlayer2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
        this.panelResult = panelResult;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        JLabel label1 = new JLabel("Player 1: " + player1 + " number of points: " + scorePlayer1);
        label1.setFont(new Font("Arial", Font.BOLD, 20));
        panelResult.add(label1, c);
        c.gridx = 0;
        c.gridy = 1;
        c.insets = new Insets(20, 10, 10, 10);
        JLabel label2 = new JLabel("Player 2: " + player2 + " number of Points: " + scorePlayer2);
        label2.setFont(new Font("Arial", Font.BOLD, 20));
        panelResult.add(label2, c);

        return panelResult;
    }

}
