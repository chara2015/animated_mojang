package net.labymod.v1_21_11.mixins.client.renderer.entity;

import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v1_21_11.client.renderer.GameRendererAccessor;
import net.labymod.v1_21_11.client.util.MinecraftUtil;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({hxp.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    private gir labyMod$graphics;

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new gir(gfj.V(), GameRendererAccessor.self(gfj.V().i).labyMod$getGuiRenderState(), 0, 0);
        }
        dlt minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        this.labyMod$graphics.setPose((Matrix3x2fStack) stack.getProvider().getPoseStack());
        this.labyMod$graphics.b(minecraftItemStack, x, y);
        if (decorate) {
            this.labyMod$graphics.a(gfj.V().g, minecraftItemStack, x, y);
        }
    }
}
