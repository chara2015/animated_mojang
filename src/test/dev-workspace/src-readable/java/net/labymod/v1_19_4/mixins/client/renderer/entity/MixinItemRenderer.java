package net.labymod.v1_19_4.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_19_4.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({foc.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    @Shadow
    public abstract void c(ehe eheVar, cfv cfvVar, int i, int i2);

    @Shadow
    public abstract void a(ehe eheVar, enp enpVar, cfv cfvVar, int i, int i2);

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        cfv minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        ehe poseStack = (ehe) stack.getProvider().getPoseStack();
        float prevRed = RenderSystem.getShaderColor()[0];
        float prevGreen = RenderSystem.getShaderColor()[1];
        float prevBlue = RenderSystem.getShaderColor()[2];
        float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        c(poseStack, minecraftItemStack, x, y);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            a(poseStack, emh.N().h, minecraftItemStack, x, y);
        }
    }
}
