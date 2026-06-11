package net.labymod.v1_12_2.mixins.client.renderer.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.v1_12_2.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/renderer/entity/MixinRenderItem.class */
@Mixin({bzw.class})
public abstract class MixinRenderItem implements ItemStackRenderer {

    @Shadow
    public float a;

    @Shadow
    public abstract void a(aip aipVar, int i, int i2);

    @Shadow
    public abstract void a(bip bipVar, aip aipVar, int i, int i2, String str);

    @Shadow
    protected abstract void a(cfy cfyVar, int i, aip aipVar);

    @Inject(method = {"renderModel(Lnet/minecraft/client/renderer/block/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$skipColors(cfy model, aip stack, CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            a(model, ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, GlColorAlphaModifier.getAlpha()), stack);
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        Laby3D laby3D = Laby.references().laby3D();
        laby3D.storeStates();
        bus.D();
        bus.m();
        bus.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        bhz.c();
        this.a += 50.0f;
        aip minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        bus.c(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        a(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        bus.c(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            a(bib.z().k, minecraftItemStack, x, y, null);
        }
        this.a -= 50.0f;
        bhz.a();
        bus.E();
        bus.l();
        laby3D.restoreStates();
    }
}
