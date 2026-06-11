package net.labymod.v1_21_11.mixins.client;

import net.labymod.api.Laby;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/MixinCategory.class */
@Mixin({KeyMapping.Category.class})
public class MixinCategory {

    @Shadow
    @Final
    private Identifier i;

    @Inject(method = {"label"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$label(CallbackInfoReturnable<Component> cir) {
        if (this.i.getNamespace().equalsIgnoreCase("labymod")) {
            String path = this.i.getPath();
            String displayName = Laby.labyAPI().getNiceNamespace(path);
            cir.setReturnValue(Component.literal(displayName));
        }
    }
}
