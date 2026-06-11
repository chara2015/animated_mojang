package net.labymod.api.swing.progress;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;
import net.labymod.api.Constants;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.swing.component.SurfacePanel;
import net.labymod.api.swing.component.TitleBar;
import net.labymod.api.swing.laf.LabyLookAndFeel;
import net.labymod.api.swing.laf.LabyThemeColors;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/progress/ProgressWindow.class */
public final class ProgressWindow {
    private static final Gson GSON = new Gson();
    private static final int WINDOW_WIDTH = 400;
    private static final int BAR_HEIGHT = 6;
    private final JFrame frame;
    private final TitleBar titleBar;
    private final JLabel statusLabel;
    private final JProgressBar progressBar;
    private final JLabel overallLabel;
    private final JProgressBar overallBar;

    private ProgressWindow(String title) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(0);
        this.frame.setResizable(false);
        this.frame.setAlwaysOnTop(true);
        this.frame.setUndecorated(true);
        this.frame.setBackground(LabyThemeColors.BACKGROUND);
        SurfacePanel contentPanel = new SurfacePanel();
        contentPanel.setLayout(new BorderLayout());
        this.titleBar = new TitleBar(title);
        contentPanel.add(this.titleBar, "North");
        JPanel body = new JPanel(new GridBagLayout());
        body.setOpaque(false);
        body.setBorder(BorderFactory.createEmptyBorder(14, 24, 20, 24));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = 2;
        gbc.weightx = 1.0d;
        gbc.gridx = 0;
        this.statusLabel = new JLabel(" ");
        this.statusLabel.setFont(this.statusLabel.getFont().deriveFont(0, 12.0f));
        this.statusLabel.setForeground(LabyThemeColors.TEXT_PRIMARY);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 6, 0);
        body.add(this.statusLabel, gbc);
        this.progressBar = createProgressBar();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 14, 0);
        body.add(this.progressBar, gbc);
        this.overallLabel = new JLabel(" ");
        this.overallLabel.setFont(this.overallLabel.getFont().deriveFont(0, 11.0f));
        this.overallLabel.setForeground(LabyThemeColors.TEXT_SECONDARY);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 6, 0);
        body.add(this.overallLabel, gbc);
        this.overallBar = createProgressBar();
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        body.add(this.overallBar, gbc);
        contentPanel.add(body, "Center");
        this.frame.setContentPane(contentPanel);
        this.frame.setSize(WINDOW_WIDTH, this.frame.getPreferredSize().height);
        this.frame.setLocationRelativeTo((Component) null);
    }

    private JProgressBar createProgressBar() {
        JProgressBar bar = new JProgressBar(0, SubmissionOrders.DEBUG);
        bar.setPreferredSize(new Dimension(0, 6));
        bar.setStringPainted(false);
        bar.setValue(0);
        return bar;
    }

    private void showIfHidden() {
        if (!this.frame.isVisible()) {
            SwingUtilities.invokeLater(() -> {
                this.frame.setVisible(true);
            });
        }
    }

    private boolean handleMessage(JsonObject message) {
        String type = message.get(ParameterType.TYPE).getAsString();
        if (!"close".equals(type)) {
            showIfHidden();
        }
        switch (type) {
            case "title":
                String text = message.get("text").getAsString();
                SwingUtilities.invokeLater(() -> {
                    this.frame.setTitle(text);
                    this.titleBar.setTitle(text);
                });
                return false;
            case "status":
                String text2 = message.get("text").getAsString();
                SwingUtilities.invokeLater(() -> {
                    this.statusLabel.setText(text2);
                });
                return false;
            case "progress":
                SwingUtilities.invokeLater(() -> {
                    applyProgress(this.progressBar, message);
                });
                return false;
            case "indeterminate":
                SwingUtilities.invokeLater(() -> {
                    this.progressBar.setIndeterminate(true);
                });
                return false;
            case "overall_status":
                String text3 = message.get("text").getAsString();
                SwingUtilities.invokeLater(() -> {
                    this.overallLabel.setText(text3);
                });
                return false;
            case "overall_progress":
                SwingUtilities.invokeLater(() -> {
                    applyProgress(this.overallBar, message);
                });
                return false;
            case "overall_indeterminate":
                SwingUtilities.invokeLater(() -> {
                    this.overallBar.setIndeterminate(true);
                });
                return false;
            case "close":
                SwingUtilities.invokeLater(() -> {
                    this.frame.setVisible(false);
                    this.frame.dispose();
                });
                return true;
            default:
                return false;
        }
    }

    private void applyProgress(JProgressBar bar, JsonObject message) {
        float value = message.get("value").getAsFloat();
        int barValue = Math.round(Math.clamp(value, 0.0f, 1.0f) * 1000.0f);
        bar.setIndeterminate(false);
        bar.setValue(barValue);
    }

    public static void main(String[] args) {
        JsonObject message;
        String initialTitle = args.length > 0 ? args[0] : Constants.Branding.NAME;
        LabyLookAndFeel.install();
        ProgressWindow window = new ProgressWindow(initialTitle);
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
            do {
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        break;
                    } else {
                        message = (JsonObject) GSON.fromJson(line, JsonObject.class);
                    }
                } finally {
                }
            } while (!window.handleMessage(message));
            reader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.exit(0);
    }
}
