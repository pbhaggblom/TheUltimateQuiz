package GameGUI;

import server.Player;

import javax.swing.*;

public class ResultWindow extends JFrame implements PanelHandler {
    String player1;
    String player2;
    int scorePlayer1;
    int scorePlayer2;
    JPanel panel;

    public ResultWindow(String player1, String player2, int scorePlayer1, int scorePlayer2) {
        this.player1 = player1;
        this.player2 = player2;
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(null);
    }

    @Override
    public JPanel changePanelContent() {
        add(panel);
        panel.setVisible(true);
        JLabel label1 = new JLabel("Player 1: " + player1 + " " + scorePlayer1);
        JLabel label2 = new JLabel("Player 2: " + player2 + " " + scorePlayer2);
        panel.add(label1);
        panel.add(label2);

        return panel;
    }

}
