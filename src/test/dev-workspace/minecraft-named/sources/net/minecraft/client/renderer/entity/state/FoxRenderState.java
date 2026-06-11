package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.animal.fox.Fox;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/FoxRenderState.class */
public class FoxRenderState extends HoldingEntityRenderState {
    public float headRollAngle;
    public float crouchAmount;
    public boolean isCrouching;
    public boolean isSleeping;
    public boolean isSitting;
    public boolean isFaceplanted;
    public boolean isPouncing;
    public Fox.Variant variant = Fox.Variant.DEFAULT;
}
