package net.minecraft.client.renderer.blockentity.state;

import net.minecraft.world.level.block.entity.SignText;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/blockentity/state/SignRenderState.class */
public class SignRenderState extends BlockEntityRenderState {
    public SignText frontText;
    public SignText backText;
    public int textLineHeight;
    public int maxTextLineWidth;
    public boolean isTextFilteringEnabled;
    public boolean drawOutline;
}
