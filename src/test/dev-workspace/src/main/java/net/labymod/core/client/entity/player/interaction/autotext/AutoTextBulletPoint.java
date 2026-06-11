package net.labymod.core.client.entity.player.interaction.autotext;

import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.interaction.AbstractBulletPoint;
import net.labymod.core.client.entity.player.interaction.DefaultInteractionMenuRegistry;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/interaction/autotext/AutoTextBulletPoint.class */
public class AutoTextBulletPoint extends AbstractBulletPoint {
    private static final DefaultInteractionMenuRegistry REGISTRY = (DefaultInteractionMenuRegistry) LabyMod.references().interactionMenuRegistry();
    private final AutoTextEntry entry;

    public AutoTextBulletPoint(AutoTextEntry entry) {
        super(Component.text(entry.displayName().get()));
        this.entry = entry;
    }

    @Override // net.labymod.api.client.entity.player.interaction.BulletPoint
    public void execute(Player player) {
        LabyAPI labyAPI = Laby.labyAPI();
        String autoTextMessage = REGISTRY.replacePlaceholders(this.entry.message().get(), player);
        if (this.entry.sendImmediately().get().booleanValue()) {
            labyAPI.minecraft().chatExecutor().chat(autoTextMessage, false);
        } else {
            labyAPI.minecraft().openChat(autoTextMessage);
        }
    }
}
