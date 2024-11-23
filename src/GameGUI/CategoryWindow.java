package GameGUI;

import javax.swing.*;
import java.awt.*;

public class CategoryWindow extends JFrame implements PanelHandler {
    JPanel panelCategory;
    JButton category1, category2;
    int x, y;
    String text1, text2;

    public CategoryWindow() {}

    public CategoryWindow(JPanel panelCategory, JButton category1, JButton category2, int x, int y, String text1, String text2) {
        this.panelCategory = panelCategory;
        this.category1 = category1;
        this.category2 = category2;
        this.x = x;
        this.y = y;
        this.text1 = text1;
        this.text2 = text2;
    }

    @Override
    public void changePanel(JPanel panel) {
        add(panel);
        panel.setLayout(new GridBagLayout());
    }

    @Override
    public JPanel changePanelContent() {
        GridBagConstraints c = new GridBagConstraints();

        category1.setPreferredSize(new Dimension(150, 70));
        category1.setSize(100, 40);
        category1.setText(text1);
        c.gridx = x;
        c.gridy = y;
        c.insets = new Insets(10, 10, 10, 10);

        panelCategory.add(category1, c);

        category2.setPreferredSize(new Dimension(150, 70));
        category2.setSize(100, 40);
        category2.setText(text2);
        c.gridx = 1;
        c.gridy = y;
        panelCategory.add(category2, c);


        return panelCategory;
    }
}
