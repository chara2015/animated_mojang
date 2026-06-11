package net.labymod.api.client.entity.player.badge;

import net.labymod.api.client.entity.player.badge.renderer.BadgeRenderer;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/badge/BadgeRegistry.class */
@Referenceable
public interface BadgeRegistry {
    int render(ScreenContext screenContext, PositionType positionType, float f, float f2, NetworkPlayerInfo networkPlayerInfo);

    int getWidth(PositionType positionType, NetworkPlayerInfo networkPlayerInfo);

    void beginRender(ScreenContext screenContext, PositionType positionType);

    void endRender(ScreenContext screenContext, PositionType positionType);

    void register(String str, PositionType positionType, BadgeRenderer badgeRenderer);

    void registerAfter(String str, String str2, PositionType positionType, BadgeRenderer badgeRenderer);

    void registerBefore(String str, String str2, PositionType positionType, BadgeRenderer badgeRenderer);

    void unregister(String str);
}
