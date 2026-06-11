package net.labymod.v1_8_9.mixins.client.renderer.entity;

import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.render.ItemStackRenderer;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.util.color.format.ColorFormat;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.GlColorAlphaModifier;
import net.labymod.v1_8_9.client.util.MinecraftUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/renderer/entity/MixinRenderItem.class */
@Mixin({bjh.class})
public abstract class MixinRenderItem implements ItemStackRenderer {

    @Shadow
    public float a;

    @Shadow
    public abstract void a(zx zxVar, int i, int i2);

    @Shadow
    public abstract void a(avn avnVar, zx zxVar, int i, int i2, String str);

    @Shadow
    protected abstract void a(boq boqVar, int i, zx zxVar);

    @Inject(method = {"renderModel(Lnet/minecraft/client/resources/model/IBakedModel;Lnet/minecraft/item/ItemStack;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$skipColors(boq model, zx stack, CallbackInfo ci) {
        if (GlColorAlphaModifier.isModifiedAlpha()) {
            a(model, ColorFormat.ARGB32.pack(1.0f, 1.0f, 1.0f, GlColorAlphaModifier.getAlpha()), stack);
            ci.cancel();
        }
    }

    @Override // net.labymod.api.client.render.ItemStackRenderer
    public void renderItemStack(Stack stack, ItemStack itemStack, int x, int y, boolean decorate, float alpha) {
        bfl.B();
        bfl.l();
        bfl.a(GlConst.GL_SRC_ALPHA, GlConst.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        avc.c();
        this.a += 50.0f;
        zx minecraftItemStack = MinecraftUtil.toMinecraft(itemStack);
        bfl.c(1.0f, 1.0f, 1.0f, alpha);
        GlColorAlphaModifier.setAlpha(alpha);
        a(minecraftItemStack, x, y);
        GlColorAlphaModifier.setAlpha(1.0f);
        bfl.c(1.0f, 1.0f, 1.0f, 1.0f);
        if (decorate) {
            a(ave.A().k, minecraftItemStack, x, y, null);
        }
        this.a -= 50.0f;
        avc.a();
        bfl.C();
        bfl.k();
    }
}
