package net.labymod.v1_18_2.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.v1_18_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({ewh.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    @Shadow
    public abstract void a(dzp dzpVar, buw buwVar, int i, int i2);

    @Shadow
    public abstract void c(buw buwVar, int i, int i2);

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        buw minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        Stack modelViewStack = RenderSystem.getModelViewStack().stack();
        modelViewStack.push();
        modelViewStack.mul(stack.getProvider().getPose());
        RenderSystem.applyModelViewMatrix();
        float prevRed = RenderSystem.getShaderColor()[0];
        float prevGreen = RenderSystem.getShaderColor()[1];
        float prevBlue = RenderSystem.getShaderColor()[2];
        float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        c(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            a(dyr.D().h, minecraftItemStack, x, y);
        }
        modelViewStack.pop();
        RenderSystem.applyModelViewMatrix();
    }
}
