package net.labymod.v1_16_5.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.math.MathHelper;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.v1_16_5.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_16_5/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({efo.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    @Shadow
    public abstract void a(dku dkuVar, bmb bmbVar, int i, int i2);

    @Shadow
    public abstract void c(bmb bmbVar, int i, int i2);

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        bmb minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        RenderSystem.pushMatrix();
        RenderSystem.multMatrix((b) MathHelper.mapper().toMatrix4f(stack.getProvider().getPosition()));
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        c(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            a(djz.C().g, minecraftItemStack, x, y);
        }
        RenderSystem.popMatrix();
    }
}
