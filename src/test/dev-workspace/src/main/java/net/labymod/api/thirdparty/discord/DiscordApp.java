package net.labymod.api.thirdparty.discord;

import net.labymod.api.reference.annotation.Referenceable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/thirdparty/discord/DiscordApp.class */
@Referenceable
public interface DiscordApp {
    void displayDefaultActivity(boolean z);

    void displayActivity(@NotNull DiscordActivity discordActivity);

    @Nullable
    DiscordActivity getDisplayedActivity();

    boolean isRunning();

    default void displayDefaultActivity() {
        displayDefaultActivity(true);
    }
}
