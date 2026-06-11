package net.labymod.core.client.world.rplace;

import java.net.URI;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.core.client.world.rplace.art.PixelArt;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/world/rplace/RPlaceOverlayCommand.class */
public class RPlaceOverlayCommand extends Command {
    public RPlaceOverlayCommand() {
        super("place-overlay", "placeoverlay", "rplace-overlay", "rplaceoverlay");
        translationKey("labymod.command.command.rplaceoverlay");
    }

    @Override // net.labymod.api.client.chat.command.Command
    public boolean execute(String prefix, String[] arguments) {
        LabyConnect labyConnect = Laby.labyAPI().labyConnect();
        if (!labyConnect.isAuthenticated()) {
            displayMessage(Component.translatable("labymod.activity.screenshotBrowser.viewer.notConnected", NamedTextColor.RED));
            return true;
        }
        RPlaceRegistry registry = LabyMod.references().rPlaceRegistry();
        if (!registry.isOnTargetLobby()) {
            displayTranslatable("wrongServer", NamedTextColor.RED, new Component[0]);
            return true;
        }
        if (!registry.isEnabled()) {
            displayTranslatable("disabled", NamedTextColor.RED, new Component[0]);
            return true;
        }
        if (arguments.length == 1 && arguments[0].equals("clear")) {
            displayTranslatable("cleared", NamedTextColor.RED, new Component[0]);
            registry.clear(false);
            return true;
        }
        if (arguments.length < 3) {
            displaySyntax();
            return true;
        }
        String url = arguments[0];
        ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
        int playerX = player == null ? 0 : (int) player.position().getX();
        int playerZ = player == null ? 0 : (int) player.position().getZ();
        int x = arguments[1].equals("~") ? playerX : Integer.parseInt(arguments[1]);
        int z = arguments[2].equals("~") ? playerZ : Integer.parseInt(arguments[2]);
        int size = -1;
        if (arguments.length > 3) {
            size = Integer.parseInt(arguments[3]);
        }
        try {
            PixelArt art = registry.registerFromUrl(URI.create(url).toURL(), x, z, size, false);
            displayTranslatable("added", NamedTextColor.GREEN, Component.text(url, NamedTextColor.YELLOW), Component.text(Integer.valueOf(art.getWidth()), NamedTextColor.YELLOW), Component.text(Integer.valueOf(art.getHeight()), NamedTextColor.YELLOW), Component.text(Integer.valueOf(art.getCenterX()), NamedTextColor.YELLOW), Component.text(Integer.valueOf(art.getCenterZ()), NamedTextColor.YELLOW), Component.text("/place-overlay clear", NamedTextColor.GRAY));
            return true;
        } catch (Throwable e) {
            displayTranslatable("invalid", NamedTextColor.RED, Component.text(e.getMessage(), NamedTextColor.YELLOW));
            return true;
        }
    }
}
