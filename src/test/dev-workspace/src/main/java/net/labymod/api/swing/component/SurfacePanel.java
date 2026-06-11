package net.labymod.api.swing.component;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import net.labymod.api.swing.laf.LabyThemeColors;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/component/SurfacePanel.class */
public class SurfacePanel extends JPanel {
    public SurfacePanel() {
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = g.create();
        int width = getWidth();
        int height = getHeight();
        g2.setColor(LabyThemeColors.SURFACE);
        g2.fillRect(0, 0, width, height);
        g2.setColor(LabyThemeColors.BORDER);
        g2.drawRect(0, 0, width - 1, height - 1);
        g2.dispose();
    }
}
