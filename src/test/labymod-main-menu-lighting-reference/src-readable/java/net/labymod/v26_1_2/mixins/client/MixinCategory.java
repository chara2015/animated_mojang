package net.labymod.v26_1_2.mixins.client;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1_2/mixins/client/MixinCategory.class */
@Mixin({KeyMapping.Category.class})
public class MixinCategory {

    @Shadow
    @Final
    private Identifier id;

    @Inject(method = {"label"}, at = {@At("HEAD")}, cancellable = true)
    private void labyMod$label(CallbackInfoReturnable<Component> cir) {
        if (this.id.getNamespace().equalsIgnoreCase("labymod")) {
            String path = this.id.getPath();
            String displayName = Laby.labyAPI().getNiceNamespace(path);
            cir.setReturnValue(Component.literal(displayName));
        }
    }
}
