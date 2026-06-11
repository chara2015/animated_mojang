package net.labymod.api.swing.component;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import net.labymod.api.swing.laf.LabyThemeColors;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/component/TitleBar.class */
public class TitleBar extends JPanel {
    private static final int HEIGHT = 36;
    private final JLabel titleLabel;

    public TitleBar(String title) {
        setOpaque(false);
        setPreferredSize(new Dimension(0, 36));
        setCursor(Cursor.getPredefinedCursor(13));
        this.titleLabel = new JLabel(title);
        this.titleLabel.setFont(this.titleLabel.getFont().deriveFont(1, 12.0f));
        this.titleLabel.setForeground(LabyThemeColors.TEXT_PRIMARY);
        setLayout(null);
        add(this.titleLabel);
        DragListener dragListener = new DragListener();
        addMouseListener(dragListener);
        addMouseMotionListener(dragListener);
    }

    public void setTitle(String title) {
        this.titleLabel.setText(title);
    }

    public void doLayout() {
        Dimension labelSize = this.titleLabel.getPreferredSize();
        this.titleLabel.setBounds(16, (36 - labelSize.height) / 2, labelSize.width, labelSize.height);
    }

    protected void paintComponent(Graphics g) {
        Graphics2D g2 = g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(LabyThemeColors.BORDER);
        g2.drawLine(16, getHeight() - 1, getWidth() - 16, getHeight() - 1);
        g2.dispose();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/component/TitleBar$DragListener.class */
    private class DragListener extends MouseAdapter {
        private Point dragOffset;

        private DragListener() {
        }

        public void mousePressed(MouseEvent e) {
            this.dragOffset = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Window window = SwingUtilities.getWindowAncestor(TitleBar.this);
            if (window == null) {
                return;
            }
            Point screenPoint = e.getLocationOnScreen();
            window.setLocation(screenPoint.x - this.dragOffset.x, screenPoint.y - this.dragOffset.y);
        }
    }
}
