package net.minecraft.client.model.monster.guardian;

import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.util.Unit;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/monster/guardian/GuardianParticleModel.class */
public class GuardianParticleModel extends Model<Unit> {
    public GuardianParticleModel(ModelPart $$0) {
        super($$0, RenderTypes::entityCutoutNoCull);
    }
}
