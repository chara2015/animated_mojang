package net.labymod.api.event.client.render.item;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.DefaultCancellable;
import net.labymod.api.event.Event;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/event/client/render/item/PlayerItemRenderContextEvent.class */
@ApiStatus.Internal
public class PlayerItemRenderContextEvent extends DefaultCancellable implements Event {
    private final Stack stack;
    private final ItemStack itemStack;
    private final ModelTransformType type;
    private final Player player;
    private final PlayerModel playerModel;
    private final int packedLight;
    private final float partialTicks;

    public PlayerItemRenderContextEvent(@NotNull Stack stack, @NotNull ItemStack itemStack, @NotNull ModelTransformType type, @NotNull Player player, @NotNull PlayerModel playerModel, int packedLight, float partialTicks) {
        this.stack = stack;
        this.itemStack = itemStack;
        this.type = type;
        this.player = player;
        this.playerModel = playerModel;
        this.packedLight = packedLight;
        this.partialTicks = partialTicks;
    }

    public Stack stack() {
        return this.stack;
    }

    public ItemStack itemStack() {
        return this.itemStack;
    }

    public ModelTransformType transformType() {
        return this.type;
    }

    public Player player() {
        return this.player;
    }

    public PlayerModel playerModel() {
        return this.playerModel;
    }

    public int getPackedLight() {
        return this.packedLight;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}
