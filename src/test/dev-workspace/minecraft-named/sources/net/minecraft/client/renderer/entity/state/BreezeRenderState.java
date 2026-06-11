package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/BreezeRenderState.class */
public class BreezeRenderState extends LivingEntityRenderState {
    public final AnimationState idle = new AnimationState();
    public final AnimationState shoot = new AnimationState();
    public final AnimationState slide = new AnimationState();
    public final AnimationState slideBack = new AnimationState();
    public final AnimationState inhale = new AnimationState();
    public final AnimationState longJump = new AnimationState();
}
