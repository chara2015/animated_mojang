package net.labymod.api.swing.laf;

import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.Map;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import net.labymod.api.Constants;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/swing/laf/LabyLookAndFeel.class */
public class LabyLookAndFeel extends MetalLookAndFeel {
    private static final ColorUIResource BG = new ColorUIResource(LabyThemeColors.BACKGROUND);
    private static final ColorUIResource SURFACE = new ColorUIResource(LabyThemeColors.SURFACE);
    private static final ColorUIResource TEXT = new ColorUIResource(LabyThemeColors.TEXT_PRIMARY);
    private static final ColorUIResource TEXT_DIM = new ColorUIResource(LabyThemeColors.TEXT_SECONDARY);
    private static final ColorUIResource ACCENT = new ColorUIResource(LabyThemeColors.ACCENT);

    public static void install() {
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");
        try {
            UIManager.setLookAndFeel(new LabyLookAndFeel());
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to install LabyLookAndFeel", exception);
        }
    }

    private static FontUIResource resolveDefaultFont() {
        Font systemFont = (Font) Toolkit.getDefaultToolkit().getDesktopProperty("win.messagebox.font");
        if (systemFont != null) {
            return new FontUIResource(systemFont.deriveFont(0, 13.0f));
        }
        return new FontUIResource("Dialog", 0, 13);
    }

    public String getName() {
        return Constants.Branding.NAME;
    }

    public String getID() {
        return Constants.Branding.NAME;
    }

    public String getDescription() {
        return "LabyMod flat dark theme";
    }

    public boolean isNativeLookAndFeel() {
        return false;
    }

    public boolean isSupportedLookAndFeel() {
        return true;
    }

    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        table.put("ProgressBarUI", LabyProgressBarUI.class.getName());
    }

    protected void initSystemColorDefaults(UIDefaults table) {
        super.initSystemColorDefaults(table);
        table.put("desktop", BG);
        table.put("activeCaption", SURFACE);
        table.put("activeCaptionText", TEXT);
        table.put("activeCaptionBorder", SURFACE);
        table.put("inactiveCaption", BG);
        table.put("inactiveCaptionText", TEXT_DIM);
        table.put("inactiveCaptionBorder", BG);
        table.put("window", BG);
        table.put("windowBorder", SURFACE);
        table.put("windowText", TEXT);
        table.put("text", SURFACE);
        table.put("textText", TEXT);
        table.put("textHighlight", ACCENT);
        table.put("textHighlightText", TEXT);
        table.put("control", BG);
        table.put("controlText", TEXT);
        table.put("controlHighlight", SURFACE);
        table.put("controlLtHighlight", SURFACE);
        table.put("controlShadow", BG);
        table.put("controlDkShadow", BG);
        table.put("info", SURFACE);
        table.put("infoText", TEXT);
        table.put("menu", BG);
        table.put("menuText", TEXT);
        table.put("scrollbar", SURFACE);
    }

    public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();
        Map<RenderingHints.Key, Object> desktopHints = (Map) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
        if (desktopHints != null) {
            defaults.put(RenderingHints.KEY_TEXT_ANTIALIASING, desktopHints.get(RenderingHints.KEY_TEXT_ANTIALIASING));
            defaults.put("awt.font.desktophints", desktopHints);
        }
        return defaults;
    }

    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);
        FontUIResource font = resolveDefaultFont();
        table.put("Panel.background", BG);
        table.put("Panel.foreground", TEXT);
        table.put("Panel.font", font);
        table.put("Label.foreground", TEXT);
        table.put("Label.font", font);
        table.put("RootPane.background", BG);
        table.put("ProgressBar.background", BG);
        table.put("ProgressBar.foreground", ACCENT);
        table.put("ProgressBar.font", font);
        table.put("Button.background", SURFACE);
        table.put("Button.foreground", TEXT);
        table.put("Button.font", font);
        table.put("Button.select", new ColorUIResource(LabyThemeColors.ACCENT_HOVER));
        table.put("ScrollBar.background", BG);
        table.put("ScrollBar.thumb", SURFACE);
        table.put("ScrollBar.track", BG);
        table.put("TextField.background", SURFACE);
        table.put("TextField.foreground", TEXT);
        table.put("TextField.caretForeground", TEXT);
        table.put("TextField.font", font);
        table.put("TextArea.background", SURFACE);
        table.put("TextArea.foreground", TEXT);
        table.put("TextArea.font", font);
        table.put("ToolTip.background", SURFACE);
        table.put("ToolTip.foreground", TEXT_DIM);
        table.put("ToolTip.font", font);
        table.put("OptionPane.background", BG);
        table.put("OptionPane.foreground", TEXT);
        table.put("OptionPane.messageForeground", TEXT);
        table.put("OptionPane.font", font);
    }
}
