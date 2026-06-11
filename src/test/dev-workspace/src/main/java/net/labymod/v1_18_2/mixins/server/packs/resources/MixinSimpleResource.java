package net.labymod.v1_18_2.mixins.server.packs.resources;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_18_2/mixins/server/packs/resources/MixinSimpleResource.class */
@Mixin({agg.class})
public class MixinSimpleResource {
    private ResourceTransformerRegistry labyMod$resourceTransformerRegistry;

    @Mutable
    @Shadow
    @Final
    private InputStream c;

    @Redirect(method = {"<init>"}, at = @At(value = "FIELD", target = "Lnet/minecraft/server/packs/resources/SimpleResource;resourceStream:Ljava/io/InputStream;"))
    private void labyMod$transformResourceInputStream(agg resource, InputStream stream) {
        if (this.labyMod$resourceTransformerRegistry == null) {
            this.labyMod$resourceTransformerRegistry = Laby.references().resourceTransformerRegistry();
        }
        this.c = ((DefaultResourceTransformerRegistry) this.labyMod$resourceTransformerRegistry).applyTransformers(resource.a(), stream);
    }
}
