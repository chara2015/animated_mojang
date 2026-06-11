package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.GuardianRenderState;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/entity/ElderGuardianRenderer.class */
public class ElderGuardianRenderer extends GuardianRenderer {
    public static final Identifier GUARDIAN_ELDER_LOCATION = Identifier.withDefaultNamespace("textures/entity/guardian_elder.png");

    public ElderGuardianRenderer(EntityRendererProvider.Context $$0) {
        super($$0, 1.2f, ModelLayers.ELDER_GUARDIAN);
    }

    @Override // net.minecraft.client.renderer.entity.GuardianRenderer, net.minecraft.client.renderer.entity.LivingEntityRenderer
    public Identifier getTextureLocation(GuardianRenderState $$0) {
        return GUARDIAN_ELDER_LOCATION;
    }
}
