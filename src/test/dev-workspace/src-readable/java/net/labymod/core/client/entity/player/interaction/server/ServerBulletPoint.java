package net.labymod.core.client.entity.player.interaction.server;

import java.nio.charset.StandardCharsets;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.BulletPoint;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.util.HashUtil;
import net.labymod.core.client.entity.player.interaction.DefaultInteractionMenuRegistry;
import net.labymod.core.main.LabyMod;
import net.labymod.serverapi.core.model.feature.InteractionMenuEntry;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/server/ServerBulletPoint.class */
public class ServerBulletPoint implements BulletPoint {
    private static final DefaultInteractionMenuRegistry REGISTRY = (DefaultInteractionMenuRegistry) LabyMod.references().interactionMenuRegistry();
    private final InteractionMenuEntry entry;
    private final Component title;
    private final Icon icon;

    public ServerBulletPoint(InteractionMenuEntry entry) {
        this.entry = entry;
        this.title = Laby.references().labyModProtocolService().mapComponent(entry.displayName());
        String iconUrl = entry.getIconUrl();
        this.icon = iconUrl == null ? null : Icon.completable(Laby.references().textureRepository().loadCacheResourceAsync("labymod", HashUtil.md5Hex(iconUrl.getBytes(StandardCharsets.UTF_8)), iconUrl, Textures.EMPTY));
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public Component getTitle() {
        return this.title;
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public Icon getIcon() {
        return this.icon;
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        LabyAPI labyAPI = Laby.labyAPI();
        String value = REGISTRY.replacePlaceholders(this.entry.getValue(), player);
        switch (AnonymousClass1.$SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType[this.entry.type().ordinal()]) {
            case 1:
                labyAPI.minecraft().chatExecutor().copyToClipboard(value);
                break;
            case 2:
                labyAPI.minecraft().chatExecutor().chat(value.startsWith("/") ? value : "/" + value, false);
                break;
            case 3:
                labyAPI.minecraft().openChat(value.startsWith("/") ? value : "/" + value);
                break;
            case 4:
                labyAPI.minecraft().chatExecutor().openUrl(value, true);
                break;
        }
    }

    /* JADX INFO: renamed from: net.labymod.core.client.entity.player.interaction.server.ServerBulletPoint$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/server/ServerBulletPoint$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType = new int[InteractionMenuEntry.InteractionMenuType.values().length];

        static {
            try {
                $SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType[InteractionMenuEntry.InteractionMenuType.CLIPBOARD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType[InteractionMenuEntry.InteractionMenuType.RUN_COMMAND.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType[InteractionMenuEntry.InteractionMenuType.SUGGEST_COMMAND.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$serverapi$core$model$feature$InteractionMenuEntry$InteractionMenuType[InteractionMenuEntry.InteractionMenuType.OPEN_BROWSER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
