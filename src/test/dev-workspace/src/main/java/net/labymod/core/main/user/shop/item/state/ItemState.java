package net.labymod.core.main.user.shop.item.state;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.pool.Poolable;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/state/ItemState.class */
public class ItemState implements Poolable {

    @Nullable
    public AnimationController animationController;
    public AnimationRenderState animationRenderState = AnimationRenderState.empty();

    @Nullable
    public Player player;

    @Nullable
    public PlayerModel playerModel;

    @Nullable
    public ItemMetadata metadata;
    public boolean rightSide;
    public float physicForward;
    public float physicStrafe;
    public float physicGravity;
    public float physicRenderYawOffset;
    public float physicPitch;

    @Override // net.labymod.api.util.pool.Poolable
    public void reset() {
        this.animationController = null;
        this.animationRenderState = AnimationRenderState.empty();
        this.player = null;
        this.playerModel = null;
        this.metadata = null;
        this.rightSide = false;
        this.physicForward = 0.0f;
        this.physicStrafe = 0.0f;
        this.physicGravity = 0.0f;
        this.physicRenderYawOffset = 0.0f;
        this.physicPitch = 0.0f;
    }
}
