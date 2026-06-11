package net.labymod.v26_2_snapshot_8.mixins.client.renderer.entity;

import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.v26_2_snapshot_8.client.renderer.GameRendererAccessor;
import net.labymod.v26_2_snapshot_8.client.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import org.joml.Matrix3x2fStack;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/mixins/client/renderer/entity/MixinItemRenderer.class */
@Mixin({ItemEntityRenderer.class})
@Implements({@Interface(iface = ItemStackRenderer.class, prefix = "itemStackRenderer$", remap = Interface.Remap.NONE)})
public abstract class MixinItemRenderer implements ItemStackRenderer {
    private GuiGraphicsExtractor labyMod$graphics;

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        if (this.labyMod$graphics == null) {
            this.labyMod$graphics = new GuiGraphicsExtractor(Minecraft.getInstance(), GameRendererAccessor.self(Minecraft.getInstance().gameRenderer).labyMod$getGuiRenderState(), 0, 0);
        }
        net.minecraft.world.item.ItemStack minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        this.labyMod$graphics.setPose((Matrix3x2fStack) stack.getProvider().getPoseStack());
        this.labyMod$graphics.fakeItem(minecraftItemStack, x, y);
        if (decorate) {
            this.labyMod$graphics.itemDecorations(Minecraft.getInstance().font, minecraftItemStack, x, y);
        }
    }
}
