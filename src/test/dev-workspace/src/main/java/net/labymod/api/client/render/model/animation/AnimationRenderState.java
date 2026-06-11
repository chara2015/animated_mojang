package net.labymod.api.client.render.model.animation;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.labymod.api.client.entity.player.Player;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/animation/AnimationRenderState.class */
public final class AnimationRenderState extends Record {

    @Nullable
    private final Player player;
    private static final AnimationRenderState EMPTY = new AnimationRenderState(null);

    public AnimationRenderState(@Nullable Player player) {
        this.player = player;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, AnimationRenderState.class), AnimationRenderState.class, "player", "FIELD:Lnet/labymod/api/client/render/model/animation/AnimationRenderState;->player:Lnet/labymod/api/client/entity/player/Player;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, AnimationRenderState.class), AnimationRenderState.class, "player", "FIELD:Lnet/labymod/api/client/render/model/animation/AnimationRenderState;->player:Lnet/labymod/api/client/entity/player/Player;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object o) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, AnimationRenderState.class, Object.class), AnimationRenderState.class, "player", "FIELD:Lnet/labymod/api/client/render/model/animation/AnimationRenderState;->player:Lnet/labymod/api/client/entity/player/Player;").dynamicInvoker().invoke(this, o) /* invoke-custom */;
    }

    @Nullable
    public Player player() {
        return this.player;
    }

    public static AnimationRenderState empty() {
        return EMPTY;
    }

    public static AnimationRenderState forPlayer(@Nullable Player player) {
        return player == null ? EMPTY : new AnimationRenderState(player);
    }
}
