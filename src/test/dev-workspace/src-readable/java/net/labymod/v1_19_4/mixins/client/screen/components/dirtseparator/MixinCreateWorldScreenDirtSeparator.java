package net.labymod.v1_19_4.mixins.client.screen.components.dirtseparator;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.theme.Theme;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/mixins/client/screen/components/dirtseparator/MixinCreateWorldScreenDirtSeparator.class */
@Mixin({exn.class})
public class MixinCreateWorldScreenDirtSeparator extends etd {
    protected MixinCreateWorldScreenDirtSeparator(tj $$0) {
        super($$0);
    }

    @Inject(method = {"render"}, at = {@At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V", shift = At.Shift.BEFORE)}, cancellable = true)
    private void labyMod$skipDirtSeparator(ehe stack, int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        Theme currentTheme = Laby.references().themeService().currentTheme();
        if (!((Boolean) currentTheme.metadata().get(DefaultThemeVariables.SHOW_DIRT_SEPARATOR, true)).booleanValue()) {
            ci.cancel();
            super.a(stack, mouseX, mouseY, partialTicks);
        }
    }
}
