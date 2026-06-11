package net.labymod.v1_20_1.mixins.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_20_1.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_1/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({fpw.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    private eox labyMod$graphics;

    @Shadow
    public abstract void a(cfz cfzVar, cfw cfwVar, boolean z, eij eijVar, fjx fjxVar, int i, int i2, fwr fwrVar);

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new eox(enn.N(), enn.N().aN().b());
        }
        cfz minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        this.labyMod$graphics.setPose((eij) stack.getProvider().getPoseStack());
        float prevRed = RenderSystem.getShaderColor()[0];
        float prevGreen = RenderSystem.getShaderColor()[1];
        float prevBlue = RenderSystem.getShaderColor()[2];
        float prevAlpha = RenderSystem.getShaderColor()[3];
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, alpha);
        this.labyMod$graphics.b(minecraftItemStack, x, y);
        RenderSystem.setShaderColor(prevRed, prevGreen, prevBlue, prevAlpha);
        if (decorate) {
            this.labyMod$graphics.a(enn.N().h, minecraftItemStack, x, y);
        }
    }
}
