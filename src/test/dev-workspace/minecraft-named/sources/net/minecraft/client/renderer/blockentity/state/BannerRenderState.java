package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/BannerRenderState.class */
public class BannerRenderState extends BlockEntityRenderState {
    public DyeColor baseColor;
    public BannerPatternLayers patterns;
    public float phase;
    public float angle;
    public boolean standing;
}
