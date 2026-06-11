package net.labymod.core.main.serverapi.protocol.neo.handler.game.feature;

import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.user.GameUserService;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import net.labymod.serverapi.api.packet.PacketHandler;
import net.labymod.serverapi.core.model.feature.Emote;
import net.labymod.serverapi.core.packet.clientbound.game.feature.EmotePacket;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/serverapi/protocol/neo/handler/game/feature/EmotePacketHandler.class */
public class EmotePacketHandler implements PacketHandler<EmotePacket> {
    private final GameUserService gameUserService = Laby.references().gameUserService();

    public void handle(@NotNull UUID uuid, @NotNull EmotePacket packet) {
        EmoteService emoteService = ((DefaultGameUserService) this.gameUserService).emoteService();
        for (Emote emote : packet.getEmotes()) {
            EmoteItem targetEmote = emoteService.getEmote(emote.getEmoteId());
            if (targetEmote != null && (!targetEmote.isDraft() || this.gameUserService.clientGameUser().visibleGroup().isStaffOrCosmeticCreator())) {
                emoteService.getOrCreateAnimationStorage(emote.getUniqueId()).playEmote(targetEmote);
            }
        }
    }
}
