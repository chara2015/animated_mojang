package net.minecraft.client.renderer.entity.state;

import net.minecraft.world.entity.animal.axolotl.Axolotl;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/state/AxolotlRenderState.class */
public class AxolotlRenderState extends LivingEntityRenderState {
    public float playingDeadFactor;
    public float movingFactor;
    public float onGroundFactor;
    public Axolotl.Variant variant = Axolotl.Variant.DEFAULT;
    public float inWaterFactor = 1.0f;
}
