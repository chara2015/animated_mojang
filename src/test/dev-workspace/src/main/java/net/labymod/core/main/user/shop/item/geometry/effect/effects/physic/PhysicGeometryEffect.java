package net.labymod.core.main.user.shop.item.geometry.effect.effects.physic;

import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectApplier;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/PhysicGeometryEffect.class */
public class PhysicGeometryEffect extends GeometryEffect {
    private int version;
    private final GeometryEffectApplier effectApplierV1;
    private final GeometryEffectApplier effectApplierV2;
    private PhysicMapping mappingX;
    private PhysicMapping mappingY;
    private PhysicMapping mappingZ;
    private float strength;
    private boolean mirrorX;
    private boolean mirrorY;
    private boolean mirrorZ;

    public PhysicGeometryEffect(String effectArgument, ModelPart model) {
        super(effectArgument, model, GeometryEffect.Type.PHYSIC, 5);
        this.mappingX = PhysicMapping.F;
        this.mappingY = PhysicMapping.G;
        this.mappingZ = PhysicMapping.S;
        this.strength = -1.0f;
        this.mirrorX = false;
        this.mirrorY = false;
        this.mirrorZ = false;
        this.effectApplierV1 = new PhysicGeometryEffectV1(this);
        this.effectApplierV2 = new PhysicGeometryEffectV2(this);
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        try {
            this.strength = Integer.parseInt(getParameter(0)) / 50.0f;
            String mappingV2 = getParameter(1, 3);
            if (mappingV2 != null) {
                this.version = 2;
                if (mappingV2.isEmpty()) {
                    return false;
                }
                this.mappingX = PhysicMapping.get(mappingV2.charAt(0));
                this.mappingY = PhysicMapping.get(mappingV2.charAt(1));
                this.mappingZ = PhysicMapping.get(mappingV2.charAt(2));
                String mirror = getParameter(2, 3);
                if (mirror == null || mirror.isEmpty()) {
                    return false;
                }
                this.mirrorX = mirror.charAt(0) == 'n';
                this.mirrorY = mirror.charAt(1) == 'n';
                this.mirrorZ = mirror.charAt(2) == 'n';
                return true;
            }
            this.version = 1;
            return parseDeprecated();
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean parseDeprecated() {
        String mappingParameter = getParameter(1, 2);
        if (mappingParameter == null || mappingParameter.isEmpty()) {
            return false;
        }
        this.mappingX = PhysicMapping.get(mappingParameter.charAt(0));
        this.mappingZ = PhysicMapping.get(mappingParameter.charAt(1));
        String mirrorParameter = getParameter(2, 2);
        if (mirrorParameter == null || mirrorParameter.isEmpty()) {
            return false;
        }
        this.mirrorX = mirrorParameter.charAt(0) == 'n';
        this.mirrorZ = mirrorParameter.charAt(1) == 'n';
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        switch (this.version) {
            case 1:
                applyEffect(this.effectApplierV1, player, itemMetadata, effectData);
                break;
            case 2:
                applyEffect(this.effectApplierV2, player, itemMetadata, effectData);
                break;
        }
    }

    public PhysicMapping mappingX() {
        return this.mappingX;
    }

    public PhysicMapping mappingY() {
        return this.mappingY;
    }

    public PhysicMapping mappingZ() {
        return this.mappingZ;
    }

    public float strength() {
        return this.strength;
    }

    public boolean mirrorX() {
        return this.mirrorX;
    }

    public boolean mirrorY() {
        return this.mirrorY;
    }

    public boolean mirrorZ() {
        return this.mirrorZ;
    }

    private void applyEffect(GeometryEffectApplier effectApplier, Player player, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        if (effectApplier == null) {
            return;
        }
        effectApplier.apply(player, itemMetadata, effectData);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/PhysicGeometryEffect$PhysicGeometryEffectV1.class */
    private static class PhysicGeometryEffectV1 implements GeometryEffectApplier {
        private final PhysicGeometryEffect effect;

        private PhysicGeometryEffectV1(PhysicGeometryEffect effect) {
            this.effect = effect;
        }

        @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectApplier
        public void apply(Player player, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
            float strength = this.effect.strength();
            float forward = effectData.getForward() * (this.effect.mirrorX() ? -1 : 1) * strength;
            float gravity = effectData.getGravity() * (this.effect.mirrorY() ? -1 : 1) * strength;
            float strafe = effectData.getStrafe() * (this.effect.mirrorZ() ? -1 : 1) * strength;
            Transformation transformation = this.effect.getModelPart().getAnimationTransformation();
            FloatVector3 rotation = transformation.getRotation();
            switch (this.effect.mappingX) {
                case X:
                    rotation.setX(forward);
                    break;
                case Y:
                    rotation.setY(forward);
                    break;
                case Z:
                    rotation.setZ(forward);
                    break;
            }
            switch (this.effect.mappingZ) {
                case X:
                    rotation.setX(strafe);
                    break;
                case Y:
                    rotation.setY(strafe);
                    break;
                case Z:
                    rotation.setZ(strafe);
                    break;
            }
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/physic/PhysicGeometryEffect$PhysicGeometryEffectV2.class */
    private static class PhysicGeometryEffectV2 implements GeometryEffectApplier {
        private final PhysicGeometryEffect effect;

        public PhysicGeometryEffectV2(PhysicGeometryEffect effect) {
            this.effect = effect;
        }

        @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectApplier
        public void apply(Player player, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
            ModelPart modelPart = this.effect.getModelPart();
            float strength = this.effect.strength();
            float forward = effectData.getForward() * (this.effect.mirrorX ? -1 : 1) * strength;
            float gravity = effectData.getGravity() * (this.effect.mirrorY ? -1 : 1) * strength;
            float strafe = effectData.getStrafe() * (this.effect.mirrorZ ? -1 : 1) * strength;
            modelPart.getAnimationTransformation().setRotation(physicDirection(this.effect.mappingX, forward, gravity, strafe), physicDirection(this.effect.mappingY, forward, gravity, strafe), physicDirection(this.effect.mappingZ, forward, gravity, strafe));
        }

        float physicDirection(PhysicMapping mapping, float forward, float gravity, float strafe) {
            switch (mapping) {
                case F:
                    return forward;
                case G:
                    return gravity;
                case S:
                    return strafe;
                default:
                    return 0.0f;
            }
        }
    }
}
