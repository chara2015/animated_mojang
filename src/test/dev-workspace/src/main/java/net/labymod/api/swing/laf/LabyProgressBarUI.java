package net.labymod.api.swing.laf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JComponent;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import net.labymod.core.main.user.shop.spray.SprayConstants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/laf/LabyProgressBarUI.class */
public class LabyProgressBarUI extends BasicProgressBarUI implements ActionListener {
    private static final Color TRANSPARENT = new Color(0, 0, 0, 0);
    private static final Color SHIMMER = new Color(255, 255, 255, 40);
    private static final int SHIMMER_WIDTH = 120;
    private static final int SHIMMER_CYCLE_MS = 2500;
    private Timer animationTimer;
    private float displayedProgress;
    private long shimmerStartTime;

    public static ComponentUI createUI(JComponent c) {
        return new LabyProgressBarUI();
    }

    protected void installDefaults() {
        super.installDefaults();
        this.progressBar.setBorder((Border) null);
        this.progressBar.setOpaque(false);
        this.shimmerStartTime = System.currentTimeMillis();
    }

    protected void installListeners() {
        super.installListeners();
        this.animationTimer = new Timer(16, this);
        this.animationTimer.start();
    }

    protected void uninstallListeners() {
        super.uninstallListeners();
        if (this.animationTimer != null) {
            this.animationTimer.stop();
            this.animationTimer = null;
        }
    }

    public void actionPerformed(ActionEvent e) {
        float target = (float) this.progressBar.getPercentComplete();
        if (target >= this.displayedProgress && target - this.displayedProgress > 0.001f) {
            this.displayedProgress += (target - this.displayedProgress) * 0.12f;
        } else {
            this.displayedProgress = target;
        }
        boolean animating = this.displayedProgress != target || this.progressBar.isIndeterminate() || this.displayedProgress > 0.0f;
        if (animating) {
            this.progressBar.repaint();
        }
    }

    public void paint(Graphics g, JComponent c) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Insets insets = c.getInsets();
        int x = insets.left;
        int y = insets.top;
        int width = (c.getWidth() - insets.left) - insets.right;
        int height = (c.getHeight() - insets.top) - insets.bottom;
        g2.setColor(LabyThemeColors.PROGRESS_TRACK);
        g2.fill(new RoundRectangle2D.Float(x, y, width, height, height, height));
        if (this.progressBar.isIndeterminate()) {
            paintIndeterminate(g2, x, y, width, height, height);
        } else {
            paintDeterminate(g2, x, y, width, height, height);
        }
        g2.dispose();
    }

    private void paintDeterminate(Graphics2D g2, int x, int y, int width, int height, int arc) {
        if (this.displayedProgress <= 0.0f) {
            return;
        }
        int fillWidth = Math.max((int) (width * this.displayedProgress), arc);
        g2.setPaint(new GradientPaint(x, 0.0f, LabyThemeColors.PROGRESS_FILL_START, x + width, 0.0f, LabyThemeColors.PROGRESS_FILL_END));
        g2.fill(new RoundRectangle2D.Float(x, y, fillWidth, height, arc, arc));
        paintShimmer(g2, x, y, fillWidth, height, arc);
    }

    private void paintIndeterminate(Graphics2D g2, int x, int y, int width, int height, int arc) {
        float fPow;
        long time = System.currentTimeMillis();
        float linear = (float) ((time % SprayConstants.LABYMOD_PLUS_NEXT_SPRAY) / 2000.0d);
        if (linear < 0.5f) {
            fPow = 4.0f * linear * linear * linear;
        } else {
            fPow = 1.0f - (((float) Math.pow(((-2.0f) * linear) + 2.0f, 3.0d)) / 2.0f);
        }
        float eased = fPow;
        int pulseWidth = width / 3;
        int travel = width + pulseWidth;
        int pulseX = (x + ((int) (travel * eased))) - pulseWidth;
        g2.setClip(new RoundRectangle2D.Float(x, y, width, height, arc, arc));
        g2.setPaint(new GradientPaint(pulseX, 0.0f, TRANSPARENT, pulseX + (pulseWidth / 4), 0.0f, LabyThemeColors.ACCENT));
        g2.fillRect(pulseX, y, pulseWidth / 4, height);
        g2.setPaint(new GradientPaint(pulseX + (pulseWidth / 4), 0.0f, LabyThemeColors.PROGRESS_FILL_START, pulseX + ((pulseWidth * 3) / 4), 0.0f, LabyThemeColors.PROGRESS_FILL_END));
        g2.fillRect(pulseX + (pulseWidth / 4), y, pulseWidth / 2, height);
        g2.setPaint(new GradientPaint(pulseX + ((pulseWidth * 3) / 4), 0.0f, LabyThemeColors.ACCENT, pulseX + pulseWidth, 0.0f, TRANSPARENT));
        g2.fillRect(pulseX + ((pulseWidth * 3) / 4), y, pulseWidth / 4, height);
        g2.setClip((Shape) null);
    }

    private void paintShimmer(Graphics2D g2, int x, int y, int fillWidth, int height, int arc) {
        long elapsed = System.currentTimeMillis() - this.shimmerStartTime;
        float shimmerT = (float) ((elapsed % SprayConstants.LABYMOD_PLUS_LAST_LIFESPAN) / 2500.0d);
        int totalTravel = fillWidth + SHIMMER_WIDTH;
        int shimmerX = (x + ((int) (totalTravel * shimmerT))) - SHIMMER_WIDTH;
        g2.setClip(new RoundRectangle2D.Float(x, y, fillWidth, height, arc, arc));
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setPaint(new GradientPaint(shimmerX, 0.0f, TRANSPARENT, shimmerX + 40, 0.0f, SHIMMER));
        g2.fillRect(shimmerX, y, 40, height);
        g2.setPaint(new GradientPaint(shimmerX + 40, 0.0f, SHIMMER, shimmerX + 80, 0.0f, SHIMMER));
        g2.fillRect(shimmerX + 40, y, 40, height);
        g2.setPaint(new GradientPaint(shimmerX + 80, 0.0f, SHIMMER, shimmerX + SHIMMER_WIDTH, 0.0f, TRANSPARENT));
        g2.fillRect(shimmerX + 80, y, 40, height);
        g2.setClip((Shape) null);
    }
}
