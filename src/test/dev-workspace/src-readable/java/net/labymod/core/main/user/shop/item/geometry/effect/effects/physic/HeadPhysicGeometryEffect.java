package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/HeadPhysicGeometryEffect.class */
public class HeadPhysicGeometryEffect extends GeometryEffect {
    private PhysicMapping physicMapping;
    private float strength;
    private boolean mirror;

    public HeadPhysicGeometryEffect(String effectArgument, ModelPart model) {
        super(effectArgument, model, GeometryEffect.Type.PHYSIC, 5);
        this.physicMapping = PhysicMapping.X;
        this.strength = 1.0f;
        this.mirror = false;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        String mirrorParameter;
        try {
            this.strength = Integer.parseInt(getParameter(0)) / 100.0f;
            String mappingParameter = getParameter(1, 1);
            if (mappingParameter == null || mappingParameter.isEmpty() || (mirrorParameter = getParameter(2, 1)) == null || mirrorParameter.isEmpty()) {
                return false;
            }
            this.physicMapping = PhysicMapping.get(mappingParameter.charAt(0));
            this.mirror = mirrorParameter.charAt(0) == 'n';
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        float pitch = effectData.getPitch() - MathHelper.toDegreesFloat(playerModel.getBody().getAnimationTransformation().getRotation().getX());
        float rotation = -MathHelper.toRadiansFloat(pitch * (this.mirror ? -1 : 1) * this.strength);
        FloatVector3 modelPartRotation = this.modelPart.getAnimationTransformation().getRotation();
        switch (this.physicMapping) {
            case X:
                modelPartRotation.setX(rotation);
                break;
            case Y:
                modelPartRotation.setY(rotation);
                break;
            case Z:
                modelPartRotation.setZ(rotation);
                break;
        }
    }

    public PhysicMapping physicMapping() {
        return this.physicMapping;
    }

    public double strength() {
        return this.strength;
    }

    public boolean mirror() {
        return this.mirror;
    }
}
