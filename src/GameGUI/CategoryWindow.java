package GameGUI;

import javax.swing.*;
import java.awt.*;

public class CategoryWindow extends JFrame implements PanelHandler {
    JPanel panelCategory;
    JButton category1, category2, category3;
    int x, y, z;
    String text1, text2, text3;

    public CategoryWindow() {}

    public CategoryWindow(JPanel panelCategory, JButton category1, JButton category2, JButton category3, int x, int y, int z, String text1, String text2, String text3) {
        this.panelCategory = panelCategory;
        this.category1 = category1;
        this.category2 = category2;
        this.category3 = category3;
        this.x = x;
        this.y = y;
        this.z = z;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
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

        category3.setPreferredSize(new Dimension(150, 70));
        category3.setSize(100, 40);
        category3.setText(text3);
        c.gridx = 2;
        c.gridy = y;
        panelCategory.add(category3, c);

        return panelCategory;
    }
}
