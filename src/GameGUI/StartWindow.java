package GameGUI;

import javax.swing.*;
import java.awt.*;

public class StartWindow extends JFrame implements PanelHandler  {
    JPanel panelStart;
    JButton startButton;
    int x, y;
    String text;
    JLabel labelStart;

    public StartWindow(JPanel panelStart, JButton startButton, int x,
                       int y, String text, JLabel labelStart) {
        this.panelStart = panelStart;
        this.startButton = startButton;
        this.x = x;
        this.y = y;
        this.text = text;
        this.labelStart = labelStart;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        GridBagConstraints c = new GridBagConstraints();

        startButton.setPreferredSize(new Dimension(150, 70));
        startButton.setSize(100, 40);
        startButton.setText(text);
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);
        panelStart.add(startButton, c);

        labelStart.setAlignmentY(Component.TOP_ALIGNMENT);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 100, 0);
        panelStart.add(labelStart, c);

        return panelStart;
    }
}
