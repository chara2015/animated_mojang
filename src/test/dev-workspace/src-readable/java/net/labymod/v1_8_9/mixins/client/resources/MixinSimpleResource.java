package net.labymod.v1_8_9.mixins.client.resources;

import java.io.InputStream;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.transform.ResourceTransformerRegistry;
import net.labymod.core.client.resources.transform.DefaultResourceTransformerRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_8_9/mixins/client/resources/MixinSimpleResource.class */
@Mixin({bno.class})
public class MixinSimpleResource {

    @Mutable
    @Shadow
    @Final
    private InputStream d;
    private ResourceTransformerRegistry labyMod$resourceTransformerRegistry;

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/client/resources/SimpleResource;resourceInputStream:Ljava/io/InputStream;"))
    private void labyMod$transformResourceInputStream(bno resource, InputStream stream) {
        if (this.labyMod$resourceTransformerRegistry == null) {
            this.labyMod$resourceTransformerRegistry = Laby.references().resourceTransformerRegistry();
        }
        this.d = ((DefaultResourceTransformerRegistry) this.labyMod$resourceTransformerRegistry).applyTransformers(resource.a(), stream);
    }
}
