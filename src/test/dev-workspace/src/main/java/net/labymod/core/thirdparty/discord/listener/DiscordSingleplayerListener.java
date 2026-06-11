package net.labymod.core.thirdparty.discord.listener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.entity.player.GameMode;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import net.labymod.api.util.function.Functional;
import net.labymod.core.labyconnect.lanworld.SharedLanWorldService;
import net.labymod.core.main.LabyMod;
import net.labymod.core.thirdparty.discord.DefaultDiscordApp;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/thirdparty/discord/listener/DiscordSingleplayerListener.class */
public final class DiscordSingleplayerListener {
    private static final DecimalFormat HEALTH_FORMAT = (DecimalFormat) Functional.of(new DecimalFormat("#.#"), format -> {
        format.setRoundingMode(RoundingMode.CEILING);
    });
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final SharedLanWorldService lanWorldService = LabyMod.references().sharedLanWorldService();
    private final DefaultDiscordApp richPresence = (DefaultDiscordApp) Laby.references().discordApp();
    private String lastState;
    private boolean lastLanHost;
    private float lastHealth;
    private String lastBiome;
    private GameMode lastGameMode;

    @Subscribe
    public void onPostTick(GameTickEvent event) {
        Minecraft minecraft;
        if (event.phase() != Phase.POST || (minecraft = this.labyAPI.minecraft()) == null || !minecraft.isIngame() || !minecraft.isSingleplayer()) {
            return;
        }
        if (this.lastLanHost != this.lanWorldService.isHost()) {
            this.lastLanHost = this.lanWorldService.isHost();
            if (this.lastLanHost) {
                updateState("Sharing LAN world", true);
                return;
            } else {
                updateState("Exploring " + this.lastBiome + " - " + getHealth(), true);
                return;
            }
        }
        ClientPlayer clientPlayer = minecraft.getClientPlayer();
        if (clientPlayer == null) {
            return;
        }
        float health = clientPlayer.getHealth();
        GameMode gameMode = clientPlayer.gameMode();
        ClientWorld clientWorld = minecraft.clientWorld();
        String biome = clientWorld.getReadableBiomeName();
        if (this.lastHealth == health && this.lastGameMode == gameMode && Objects.equals(this.lastBiome, biome)) {
            return;
        }
        boolean forceUpdate = false;
        this.lastHealth = health;
        if (this.lastGameMode != gameMode) {
            this.lastGameMode = gameMode;
            forceUpdate = true;
        }
        this.lastBiome = biome;
        updateState("Exploring " + this.lastBiome + " - " + getHealth(), forceUpdate);
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        Minecraft minecraft = this.labyAPI.minecraft();
        if (minecraft == null || !minecraft.isIngame() || !minecraft.isSingleplayer()) {
            return;
        }
        this.richPresence.displayDefaultActivity();
    }

    private void updateState(String state, boolean forceUpdate) {
        if (!forceUpdate && Objects.equals(this.lastState, state)) {
            return;
        }
        this.lastState = state;
        DiscordActivity displayedActivity = this.richPresence.getDisplayedActivity();
        if (displayedActivity == null || displayedActivity.isCustom()) {
            return;
        }
        DiscordActivity activity = DiscordActivity.builder(this, displayedActivity).state(state).details(this.labyAPI.minecraft().getClientPlayer().gameMode().getName()).build();
        this.richPresence.displayActivity(activity);
    }

    @NotNull
    private String getHealth() {
        float health = this.lastHealth / 2.0f;
        if (health > 0.0f) {
            return HEALTH_FORMAT.format(health) + " heart" + (health == 1.0f ? "" : "s");
        }
        return "Dead";
    }
}
