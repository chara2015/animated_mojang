package net.labymod.core.client.gfx.pipeline.renderer.nametag;

import java.util.Objects;
import java.util.function.Consumer;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.entity.player.tag.event.NameTagBackgroundRenderEvent;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.network.NetworkPlayerInfo;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.EntityExtraKeys;
import net.labymod.api.client.render.state.entity.AvatarSnapshot;
import net.labymod.api.client.render.state.entity.CustomAvatarDataSnapshot;
import net.labymod.api.client.render.state.entity.EntitySnapshot;
import net.labymod.api.event.client.render.PlayerNameTagRenderEvent;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.color.format.ColorFormat;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/renderer/nametag/NameTagRenderer.class */
public class NameTagRenderer {
    private final ComponentMapper mapper = Laby.references().componentMapper();
    private final RenderEnvironmentContext environmentContext = Laby.references().renderEnvironmentContext();
    private final TagRegistry tagRegistry = Laby.labyAPI().tagRegistry();

    public boolean transformNameTagContent(Stack stack, EntitySnapshot snapshot, Object displayName, Consumer<Object> displayNameConsumer) {
        NetworkPlayerInfo networkPlayerInfoPlayerInfo;
        TagType type = RenderUtil.getNameTagType();
        if (type != TagType.MAIN_TAG || !(snapshot instanceof AvatarSnapshot)) {
            return false;
        }
        Component eventComponent = this.mapper.fromMinecraftComponent(displayName);
        PlayerNameTagRenderEvent.Context context = PlayerNameTagRenderEvent.Context.PLAYER_RENDER;
        if (snapshot.has(EntityExtraKeys.CUSTOM_AVATAR_DATA)) {
            networkPlayerInfoPlayerInfo = ((CustomAvatarDataSnapshot) snapshot.get(EntityExtraKeys.CUSTOM_AVATAR_DATA)).playerInfo();
        } else {
            networkPlayerInfoPlayerInfo = null;
        }
        PlayerNameTagRenderEvent event = new PlayerNameTagRenderEvent(context, networkPlayerInfoPlayerInfo, eventComponent, type);
        Laby.fireEvent(event);
        if (event.isCancelled()) {
            resetTag();
            stack.pop();
            return true;
        }
        Component modifiedNameTag = event.nameTag();
        if (!Objects.equals(modifiedNameTag, eventComponent)) {
            displayNameConsumer.accept(this.mapper.toMinecraftComponent(modifiedNameTag.append(PlayerNameTagRenderEvent.EDITED_COMPONENT)));
            return false;
        }
        return false;
    }

    public void renderNameTags(Stack stack, EntitySnapshot snapshot, int nameWidth, int packedLight) {
        this.environmentContext.setPackedLight(packedLight);
        this.tagRegistry.render(stack, snapshot, nameWidth, RenderUtil.getNameTagType());
    }

    public int getNameTagBackgroundColor(float currentOpacity) {
        NameTagBackgroundRenderEvent event = (NameTagBackgroundRenderEvent) Laby.fireEvent(NameTagBackgroundRenderEvent.singleton());
        if (event.isCancelled()) {
            return 0;
        }
        ColorFormat format = ColorFormat.ARGB32;
        return format.pack(event.getColor(), format.normalize(currentOpacity));
    }

    public void resetTag() {
        RenderUtil.setNameTagType(TagType.INVALID);
    }
}
