package net.labymod.core.client.resources.transform.transformer.customhitcolor;

import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.resources.transform.RegisterResourceTransformerEvent;
import net.labymod.api.util.InjectionNames;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/resources/transform/transformer/customhitcolor/CustomHitColorShaderListener.class */
@Singleton
public final class CustomHitColorShaderListener {
    private final ResourceLocationFactory locationFactory = Laby.references().resourceLocationFactory();

    @Inject
    public CustomHitColorShaderListener() {
    }

    @Subscribe
    public void registerResourceTransformer(RegisterResourceTransformerEvent event) {
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.vsh"), Laby.references().resourceTransformer(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_VERTEX_SHADER));
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.fsh"), Laby.references().resourceTransformer(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_FRAGMENT_SHADER));
        event.register(this.locationFactory.createMinecraft("shaders/core/rendertype_armor_cutout_no_cull.json"), Laby.references().resourceTransformer(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_JSON));
    }
}
