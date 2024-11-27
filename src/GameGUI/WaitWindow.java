package GameGUI;

import javax.swing.*;
import java.awt.*;

public class WaitWindow extends JFrame implements PanelHandler{


    JPanel panelWait;

    public WaitWindow(JPanel panelWait) {
        this.panelWait = panelWait;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        return panelWait;
    }
}
