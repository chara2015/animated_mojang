package net.labymod.api.event.client.render;

import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/PlayerNameTagRenderEvent.class */
public class PlayerNameTagRenderEvent extends DefaultCancellable implements Event {
    public static final TextComponent EDITED_COMPONENT = Component.text(" ✎", NamedTextColor.YELLOW);
    private final Context context;
    private final NetworkPlayerInfo playerInfo;
    private final TagType tagType;
    private Component nameTagComponent;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/PlayerNameTagRenderEvent$Context.class */
    public enum Context {
        PLAYER_RENDER,
        TAB_LIST
    }

    public PlayerNameTagRenderEvent(@NotNull Context context, @Nullable NetworkPlayerInfo playerInfo, @NotNull Component nameTagComponent, @NotNull TagType tagType) {
        this.context = context;
        this.playerInfo = playerInfo;
        this.nameTagComponent = nameTagComponent;
        this.tagType = tagType;
    }

    @NotNull
    public Context context() {
        return this.context;
    }

    @Nullable
    public NetworkPlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }

    @Deprecated(forRemoval = true, since = "4.2.26")
    @Nullable
    public NetworkPlayerInfo playerInfo() {
        return this.playerInfo;
    }

    @NotNull
    public Component nameTag() {
        return this.nameTagComponent;
    }

    public TagType tagType() {
        return this.tagType;
    }

    public void setNameTag(@NotNull Component nameTagComponent) {
        this.nameTagComponent = nameTagComponent;
    }
}
