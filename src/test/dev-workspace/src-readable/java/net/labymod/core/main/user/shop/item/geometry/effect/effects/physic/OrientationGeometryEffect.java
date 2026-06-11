package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.world.MinecraftCamera;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/OrientationGeometryEffect.class */
public class OrientationGeometryEffect extends GeometryEffect {
    private OrientationTarget target;

    public OrientationGeometryEffect(String effectArgument, ModelPart model) {
        super(effectArgument, model, GeometryEffect.Type.PHYSIC, 1);
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        this.target = OrientationTarget.getById(getParameter(0));
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata metadata, ItemEffect.EffectData data) {
        MinecraftCamera camera = Laby.labyAPI().minecraft().getCamera();
        if (camera == null) {
            return;
        }
        camera.cameraRotation();
        float x = 0.0f;
        float y = 0.0f;
        float z = 0.0f;
        if (this.target == OrientationTarget.CAMERA) {
            x = camera.getPitch();
            y = camera.getYaw() - data.getRenderYawOffset();
        }
        if (this.target == OrientationTarget.NORTH) {
            y = -data.getRenderYawOffset();
        }
        ModelPart parent = this.modelPart.getParent();
        while (true) {
            ModelPart target = parent;
            if (target != null) {
                FloatVector3 rotation = target.getModelPartTransform().getRotation();
                x -= MathHelper.toDegreesFloat(rotation.getX());
                y -= MathHelper.toDegreesFloat(rotation.getY());
                z -= MathHelper.toDegreesFloat(rotation.getZ());
                parent = target.getParent();
            } else {
                Transformation transformation = this.modelPart.getAnimationTransformation();
                transformation.setRotation(MathHelper.toRadiansFloat(x + 180.0f), MathHelper.toRadiansFloat(y), MathHelper.toRadiansFloat(z));
                return;
            }
        }
    }
}
