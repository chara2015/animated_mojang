package net.labymod.v1_21_10.mixins.client.gui.components;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.core.client.accessor.gui.StringWidgetAccessor;
import net.labymod.v1_21_10.client.render.matrix.JomlMatrix3x2fStackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_10/mixins/client/gui/components/MixinStringWidget.class */
@Mixin({gey.class})
public abstract class MixinStringWidget extends MixinAbstractStringWidget implements StringWidgetAccessor {

    @Shadow
    private int a;

    @Shadow
    private a d;
    private boolean labyMod$basedOnTextWidth;

    @Inject(method = {"<init>(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/Font;)V"}, at = {@At("TAIL")})
    private void labyMod$onInit(xx $$0, gda $$1, CallbackInfo ci) {
        this.labyMod$basedOnTextWidth = true;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public boolean isBasedOnTextWidth() {
        return this.labyMod$basedOnTextWidth;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public int getTextColor() {
        return c();
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public boolean isClipped() {
        return this.d == a.a;
    }

    @Override // net.labymod.core.client.accessor.gui.StringWidgetAccessor
    public float getMaxWidth() {
        return this.a;
    }

    @Inject(method = {"renderWidget"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$renderWidget(gdd graphics, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        getWatcher().update(this, ((gdn) this).A());
        Laby.labyAPI().minecraft().updateMouse(mouseX, mouseY, mouse -> {
            boolean rendered = getWatcher().render(Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics)), mouse, partialTicks);
            if (rendered) {
                ci.cancel();
            }
        });
    }
}
