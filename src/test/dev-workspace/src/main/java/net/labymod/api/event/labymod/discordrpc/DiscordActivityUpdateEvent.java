package net.labymod.api.event.labymod.discordrpc;

import java.util.Objects;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.thirdparty.discord.DiscordActivity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/labymod/discordrpc/DiscordActivityUpdateEvent.class */
public class DiscordActivityUpdateEvent extends DefaultCancellable {
    private final DiscordActivity originActivity;
    private DiscordActivity activity;

    @ApiStatus.Internal
    public DiscordActivityUpdateEvent(@NotNull DiscordActivity activity) {
        Objects.requireNonNull(activity, "Discord activity cannot be null!");
        this.originActivity = activity;
        this.activity = activity;
    }

    @NotNull
    public DiscordActivity originActivity() {
        return this.originActivity;
    }

    @NotNull
    public DiscordActivity activity() {
        return this.activity;
    }

    public void setActivity(@NotNull DiscordActivity activity) {
        Objects.requireNonNull(activity, "Activity cannot be null. Cancel the event instead.");
        this.activity = activity;
    }
}
