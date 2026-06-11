package net.labymod.core.client.screenshot.command;

import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import net.labymod.api.Constants;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.screenshots.ScreenshotBrowserActivity;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/screenshot/command/ScreenshotViewerCommand.class */
public class ScreenshotViewerCommand extends Command {
    public ScreenshotViewerCommand() {
        super("screenshotViewer", "screenshot-viewer");
        translationKey("labymod.command.command.labynetScreenshotUpload");
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        if (arguments.length == 0) {
            displaySyntax();
            return true;
        }
        try {
            Path path = Paths.get(arguments[0], new String[0]).toAbsolutePath().normalize();
            Path screenshotDirectory = Constants.Files.SCREENSHOT_DIRECTORY.toAbsolutePath().normalize();
            if (!path.startsWith(screenshotDirectory) || !path.toFile().exists()) {
                displayTranslatable("notFound", NamedTextColor.RED, path);
                return true;
            }
            ScreenshotBrowserActivity.openScreenshot(path);
            return true;
        } catch (InvalidPathException e) {
            displayTranslatable("notFound", NamedTextColor.RED, arguments[0]);
            return true;
        }
    }
}
