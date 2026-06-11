package net.labymod.v26_1_1.mixins.client.screen;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_1/mixins/client/screen/MixinCreateWorldScreen.class */
@Mixin({CreateWorldScreen.class})
public class MixinCreateWorldScreen extends Screen {

    @Shadow
    private TabNavigationBar tabNavigationBar;

    protected MixinCreateWorldScreen(Component $$0) {
        super($$0);
    }

    @Inject(method = {"extractRenderState"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/Screen;extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", shift = At.Shift.AFTER)}, cancellable = true)
    public void labyMod$cancelFooterRender(GuiGraphicsExtractor $$0, int $$1, int $$2, float $$3, CallbackInfo ci) {
        super.extractRenderState($$0, $$1, $$2, $$3);
        ci.cancel();
    }
}
