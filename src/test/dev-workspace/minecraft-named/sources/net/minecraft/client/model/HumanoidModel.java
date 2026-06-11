package net.minecraft.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.client.model.effects.SpearAnimations;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.ArmorModelSet;
import net.minecraft.client.renderer.entity.state.ArmedEntityRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Ease;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/HumanoidModel.class */
public class HumanoidModel<T extends HumanoidRenderState> extends EntityModel<T> implements ArmedModel<T>, HeadedModel {
    public static final MeshTransformer BABY_TRANSFORMER = new BabyModelTransform(true, 16.0f, 0.0f, 2.0f, 2.0f, 24.0f, Set.of(PartNames.HEAD));
    public static final float OVERLAY_SCALE = 0.25f;
    public static final float HAT_OVERLAY_SCALE = 0.5f;
    public static final float LEGGINGS_OVERLAY_SCALE = -0.1f;
    private static final float DUCK_WALK_ROTATION = 0.005f;
    private static final float SPYGLASS_ARM_ROT_Y = 0.2617994f;
    private static final float SPYGLASS_ARM_ROT_X = 1.9198622f;
    private static final float SPYGLASS_ARM_CROUCH_ROT_X = 0.2617994f;
    private static final float HIGHEST_SHIELD_BLOCKING_ANGLE = -1.3962634f;
    private static final float LOWEST_SHIELD_BLOCKING_ANGLE = 0.43633232f;
    private static final float HORIZONTAL_SHIELD_MOVEMENT_LIMIT = 0.5235988f;
    public static final float TOOT_HORN_XROT_BASE = 1.4835298f;
    public static final float TOOT_HORN_YROT_BASE = 0.5235988f;
    public final ModelPart head;
    public final ModelPart hat;
    public final ModelPart body;
    public final ModelPart rightArm;
    public final ModelPart leftArm;
    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/HumanoidModel$ArmPose.class */
    public enum ArmPose {
        EMPTY(false, false),
        ITEM(false, false),
        BLOCK(false, false),
        BOW_AND_ARROW(true, true),
        THROW_TRIDENT(false, true),
        CROSSBOW_CHARGE(true, true),
        CROSSBOW_HOLD(true, true),
        SPYGLASS(false, false),
        TOOT_HORN(false, false),
        BRUSH(false, false),
        SPEAR(false, true) { // from class: net.minecraft.client.model.HumanoidModel.ArmPose.1
            @Override // net.minecraft.client.model.HumanoidModel.ArmPose
            public <S extends ArmedEntityRenderState> void animateUseItem(S $$0, PoseStack $$1, float $$2, HumanoidArm $$3, ItemStack $$4) {
                SpearAnimations.thirdPersonUseItem($$0, $$1, $$2, $$3, $$4);
            }
        };

        private final boolean twoHanded;
        private final boolean affectsOffhandPose;

        ArmPose(boolean $$0, boolean $$1) {
            this.twoHanded = $$0;
            this.affectsOffhandPose = $$1;
        }

        public boolean isTwoHanded() {
            return this.twoHanded;
        }

        public boolean affectsOffhandPose() {
            return this.affectsOffhandPose;
        }

        public <S extends ArmedEntityRenderState> void animateUseItem(S $$0, PoseStack $$1, float $$2, HumanoidArm $$3, ItemStack $$4) {
        }
    }

    public HumanoidModel(ModelPart $$0) {
        this($$0, RenderTypes::entityCutoutNoCull);
    }

    public HumanoidModel(ModelPart $$0, Function<Identifier, RenderType> $$1) {
        super($$0, $$1);
        this.head = $$0.getChild(PartNames.HEAD);
        this.hat = this.head.getChild(PartNames.HAT);
        this.body = $$0.getChild(PartNames.BODY);
        this.rightArm = $$0.getChild(PartNames.RIGHT_ARM);
        this.leftArm = $$0.getChild(PartNames.LEFT_ARM);
        this.rightLeg = $$0.getChild(PartNames.RIGHT_LEG);
        this.leftLeg = $$0.getChild(PartNames.LEFT_LEG);
    }

    public static MeshDefinition createMesh(CubeDeformation $$0, float $$1) {
        MeshDefinition $$2 = new MeshDefinition();
        PartDefinition $$3 = $$2.getRoot();
        PartDefinition $$4 = $$3.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, $$0), PartPose.offset(0.0f, 0.0f + $$1, 0.0f));
        $$4.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, $$0.extend(0.5f)), PartPose.ZERO);
        $$3.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 16).addBox(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, $$0), PartPose.offset(0.0f, 0.0f + $$1, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 16).addBox(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(-5.0f, 2.0f + $$1, 0.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(5.0f, 2.0f + $$1, 0.0f));
        $$3.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(-1.9f, 12.0f + $$1, 0.0f));
        $$3.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0), PartPose.offset(1.9f, 12.0f + $$1, 0.0f));
        return $$2;
    }

    public static ArmorModelSet<MeshDefinition> createArmorMeshSet(CubeDeformation $$0, CubeDeformation $$1) {
        return createArmorMeshSet(HumanoidModel::createBaseArmorMesh, $$0, $$1);
    }

    protected static ArmorModelSet<MeshDefinition> createArmorMeshSet(Function<CubeDeformation, MeshDefinition> $$0, CubeDeformation $$1, CubeDeformation $$2) {
        MeshDefinition $$3 = $$0.apply($$2);
        $$3.getRoot().retainPartsAndChildren(Set.of(PartNames.HEAD));
        MeshDefinition $$4 = $$0.apply($$2);
        $$4.getRoot().retainExactParts(Set.of(PartNames.BODY, PartNames.LEFT_ARM, PartNames.RIGHT_ARM));
        MeshDefinition $$5 = $$0.apply($$1);
        $$5.getRoot().retainExactParts(Set.of(PartNames.LEFT_LEG, PartNames.RIGHT_LEG, PartNames.BODY));
        MeshDefinition $$6 = $$0.apply($$2);
        $$6.getRoot().retainExactParts(Set.of(PartNames.LEFT_LEG, PartNames.RIGHT_LEG));
        return new ArmorModelSet<>($$3, $$4, $$5, $$6);
    }

    private static MeshDefinition createBaseArmorMesh(CubeDeformation $$0) {
        MeshDefinition $$1 = createMesh($$0, 0.0f);
        PartDefinition $$2 = $$1.getRoot();
        $$2.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0.extend(-0.1f)), PartPose.offset(-1.9f, 12.0f, 0.0f));
        $$2.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, $$0.extend(-0.1f)), PartPose.offset(1.9f, 12.0f, 0.0f));
        return $$1;
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(T $$0) {
        super.setupAnim($$0);
        ArmPose $$1 = $$0.leftArmPose;
        ArmPose $$2 = $$0.rightArmPose;
        float $$3 = $$0.swimAmount;
        boolean $$4 = $$0.isFallFlying;
        this.head.xRot = $$0.xRot * 0.017453292f;
        this.head.yRot = $$0.yRot * 0.017453292f;
        if ($$4) {
            this.head.xRot = -0.7853982f;
        } else if ($$3 > 0.0f) {
            this.head.xRot = Mth.rotLerpRad($$3, this.head.xRot, -0.7853982f);
        }
        float $$5 = $$0.walkAnimationPos;
        float $$6 = $$0.walkAnimationSpeed;
        this.rightArm.xRot = (((Mth.cos(($$5 * 0.6662f) + 3.1415927f) * 2.0f) * $$6) * 0.5f) / $$0.speedValue;
        this.leftArm.xRot = (((Mth.cos($$5 * 0.6662f) * 2.0f) * $$6) * 0.5f) / $$0.speedValue;
        this.rightLeg.xRot = ((Mth.cos($$5 * 0.6662f) * 1.4f) * $$6) / $$0.speedValue;
        this.leftLeg.xRot = ((Mth.cos(($$5 * 0.6662f) + 3.1415927f) * 1.4f) * $$6) / $$0.speedValue;
        this.rightLeg.yRot = 0.005f;
        this.leftLeg.yRot = -0.005f;
        this.rightLeg.zRot = 0.005f;
        this.leftLeg.zRot = -0.005f;
        if ($$0.isPassenger) {
            ModelPart modelPart = this.rightArm;
            modelPart.xRot -= 0.62831855f;
            ModelPart modelPart2 = this.leftArm;
            modelPart2.xRot -= 0.62831855f;
            this.rightLeg.xRot = -1.4137167f;
            this.rightLeg.yRot = 0.31415927f;
            this.rightLeg.zRot = 0.07853982f;
            this.leftLeg.xRot = -1.4137167f;
            this.leftLeg.yRot = -0.31415927f;
            this.leftLeg.zRot = -0.07853982f;
        }
        boolean $$7 = $$0.mainArm == HumanoidArm.RIGHT;
        if ($$0.isUsingItem) {
            boolean $$8 = $$0.useItemHand == InteractionHand.MAIN_HAND;
            if ($$8 == $$7) {
                poseRightArm($$0);
                if (!$$0.rightArmPose.affectsOffhandPose()) {
                    poseLeftArm($$0);
                }
            } else {
                poseLeftArm($$0);
                if (!$$0.leftArmPose.affectsOffhandPose()) {
                    poseRightArm($$0);
                }
            }
        } else {
            boolean $$9 = $$7 ? $$1.isTwoHanded() : $$2.isTwoHanded();
            if ($$7 != $$9) {
                poseLeftArm($$0);
                if (!$$0.leftArmPose.affectsOffhandPose()) {
                    poseRightArm($$0);
                }
            } else {
                poseRightArm($$0);
                if (!$$0.rightArmPose.affectsOffhandPose()) {
                    poseLeftArm($$0);
                }
            }
        }
        setupAttackAnimation($$0);
        if ($$0.isCrouching) {
            this.body.xRot = 0.5f;
            this.rightArm.xRot += 0.4f;
            this.leftArm.xRot += 0.4f;
            this.rightLeg.z += 4.0f;
            this.leftLeg.z += 4.0f;
            this.head.y += 4.2f;
            this.body.y += 3.2f;
            this.leftArm.y += 3.2f;
            this.rightArm.y += 3.2f;
        }
        if ($$2 != ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.rightArm, $$0.ageInTicks, 1.0f);
        }
        if ($$1 != ArmPose.SPYGLASS) {
            AnimationUtils.bobModelPart(this.leftArm, $$0.ageInTicks, -1.0f);
        }
        if ($$3 > 0.0f) {
            float $$10 = $$5 % 26.0f;
            HumanoidArm $$11 = $$0.attackArm;
            float $$12 = ($$0.rightArmPose == ArmPose.SPEAR || ($$11 == HumanoidArm.RIGHT && $$0.attackTime > 0.0f)) ? 0.0f : $$3;
            float $$13 = ($$0.leftArmPose == ArmPose.SPEAR || ($$11 == HumanoidArm.LEFT && $$0.attackTime > 0.0f)) ? 0.0f : $$3;
            if (!$$0.isUsingItem) {
                if ($$10 < 14.0f) {
                    this.leftArm.xRot = Mth.rotLerpRad($$13, this.leftArm.xRot, 0.0f);
                    this.rightArm.xRot = Mth.lerp($$12, this.rightArm.xRot, 0.0f);
                    this.leftArm.yRot = Mth.rotLerpRad($$13, this.leftArm.yRot, 3.1415927f);
                    this.rightArm.yRot = Mth.lerp($$12, this.rightArm.yRot, 3.1415927f);
                    this.leftArm.zRot = Mth.rotLerpRad($$13, this.leftArm.zRot, 3.1415927f + ((1.8707964f * quadraticArmUpdate($$10)) / quadraticArmUpdate(14.0f)));
                    this.rightArm.zRot = Mth.lerp($$12, this.rightArm.zRot, 3.1415927f - ((1.8707964f * quadraticArmUpdate($$10)) / quadraticArmUpdate(14.0f)));
                } else if ($$10 >= 14.0f && $$10 < 22.0f) {
                    float $$14 = ($$10 - 14.0f) / 8.0f;
                    this.leftArm.xRot = Mth.rotLerpRad($$13, this.leftArm.xRot, 1.5707964f * $$14);
                    this.rightArm.xRot = Mth.lerp($$12, this.rightArm.xRot, 1.5707964f * $$14);
                    this.leftArm.yRot = Mth.rotLerpRad($$13, this.leftArm.yRot, 3.1415927f);
                    this.rightArm.yRot = Mth.lerp($$12, this.rightArm.yRot, 3.1415927f);
                    this.leftArm.zRot = Mth.rotLerpRad($$13, this.leftArm.zRot, 5.012389f - (1.8707964f * $$14));
                    this.rightArm.zRot = Mth.lerp($$12, this.rightArm.zRot, 1.2707963f + (1.8707964f * $$14));
                } else if ($$10 >= 22.0f && $$10 < 26.0f) {
                    float $$15 = ($$10 - 22.0f) / 4.0f;
                    this.leftArm.xRot = Mth.rotLerpRad($$13, this.leftArm.xRot, 1.5707964f - (1.5707964f * $$15));
                    this.rightArm.xRot = Mth.lerp($$12, this.rightArm.xRot, 1.5707964f - (1.5707964f * $$15));
                    this.leftArm.yRot = Mth.rotLerpRad($$13, this.leftArm.yRot, 3.1415927f);
                    this.rightArm.yRot = Mth.lerp($$12, this.rightArm.yRot, 3.1415927f);
                    this.leftArm.zRot = Mth.rotLerpRad($$13, this.leftArm.zRot, 3.1415927f);
                    this.rightArm.zRot = Mth.lerp($$12, this.rightArm.zRot, 3.1415927f);
                }
            }
            this.leftLeg.xRot = Mth.lerp($$3, this.leftLeg.xRot, 0.3f * Mth.cos(($$5 * 0.33333334f) + 3.1415927f));
            this.rightLeg.xRot = Mth.lerp($$3, this.rightLeg.xRot, 0.3f * Mth.cos($$5 * 0.33333334f));
        }
    }

    private void poseRightArm(T $$0) {
        switch ($$0.rightArmPose) {
            case EMPTY:
                this.rightArm.yRot = 0.0f;
                break;
            case ITEM:
                this.rightArm.xRot = (this.rightArm.xRot * 0.5f) - 0.31415927f;
                this.rightArm.yRot = 0.0f;
                break;
            case BLOCK:
                poseBlockingArm(this.rightArm, true);
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = (-0.1f) + this.head.yRot;
                this.leftArm.yRot = 0.1f + this.head.yRot + 0.4f;
                this.rightArm.xRot = (-1.5707964f) + this.head.xRot;
                this.leftArm.xRot = (-1.5707964f) + this.head.xRot;
                break;
            case THROW_TRIDENT:
                this.rightArm.xRot = (this.rightArm.xRot * 0.5f) - 3.1415927f;
                this.rightArm.yRot = 0.0f;
                break;
            case CROSSBOW_CHARGE:
                AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, $$0.maxCrossbowChargeDuration, $$0.ticksUsingItem, true);
                break;
            case CROSSBOW_HOLD:
                AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, true);
                break;
            case SPYGLASS:
                this.rightArm.xRot = Mth.clamp((this.head.xRot - SPYGLASS_ARM_ROT_X) - ($$0.isCrouching ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.rightArm.yRot = this.head.yRot - 0.2617994f;
                break;
            case TOOT_HORN:
                this.rightArm.xRot = Mth.clamp(this.head.xRot, -1.2f, 1.2f) - 1.4835298f;
                this.rightArm.yRot = this.head.yRot - 0.5235988f;
                break;
            case BRUSH:
                this.rightArm.xRot = (this.rightArm.xRot * 0.5f) - 0.62831855f;
                this.rightArm.yRot = 0.0f;
                break;
            case SPEAR:
                SpearAnimations.thirdPersonHandUse(this.rightArm, this.head, true, $$0.getUseItemStackForArm(HumanoidArm.RIGHT), $$0);
                break;
        }
    }

    private void poseLeftArm(T $$0) {
        switch ($$0.leftArmPose) {
            case EMPTY:
                this.leftArm.yRot = 0.0f;
                break;
            case ITEM:
                this.leftArm.xRot = (this.leftArm.xRot * 0.5f) - 0.31415927f;
                this.leftArm.yRot = 0.0f;
                break;
            case BLOCK:
                poseBlockingArm(this.leftArm, false);
                break;
            case BOW_AND_ARROW:
                this.rightArm.yRot = ((-0.1f) + this.head.yRot) - 0.4f;
                this.leftArm.yRot = 0.1f + this.head.yRot;
                this.rightArm.xRot = (-1.5707964f) + this.head.xRot;
                this.leftArm.xRot = (-1.5707964f) + this.head.xRot;
                break;
            case THROW_TRIDENT:
                this.leftArm.xRot = (this.leftArm.xRot * 0.5f) - 3.1415927f;
                this.leftArm.yRot = 0.0f;
                break;
            case CROSSBOW_CHARGE:
                AnimationUtils.animateCrossbowCharge(this.rightArm, this.leftArm, $$0.maxCrossbowChargeDuration, $$0.ticksUsingItem, false);
                break;
            case CROSSBOW_HOLD:
                AnimationUtils.animateCrossbowHold(this.rightArm, this.leftArm, this.head, false);
                break;
            case SPYGLASS:
                this.leftArm.xRot = Mth.clamp((this.head.xRot - SPYGLASS_ARM_ROT_X) - ($$0.isCrouching ? 0.2617994f : 0.0f), -2.4f, 3.3f);
                this.leftArm.yRot = this.head.yRot + 0.2617994f;
                break;
            case TOOT_HORN:
                this.leftArm.xRot = Mth.clamp(this.head.xRot, -1.2f, 1.2f) - 1.4835298f;
                this.leftArm.yRot = this.head.yRot + 0.5235988f;
                break;
            case BRUSH:
                this.leftArm.xRot = (this.leftArm.xRot * 0.5f) - 0.62831855f;
                this.leftArm.yRot = 0.0f;
                break;
            case SPEAR:
                SpearAnimations.thirdPersonHandUse(this.leftArm, this.head, false, $$0.getUseItemStackForArm(HumanoidArm.LEFT), $$0);
                break;
        }
    }

    private void poseBlockingArm(ModelPart $$0, boolean $$1) {
        $$0.xRot = (($$0.xRot * 0.5f) - 0.9424779f) + Mth.clamp(this.head.xRot, HIGHEST_SHIELD_BLOCKING_ANGLE, LOWEST_SHIELD_BLOCKING_ANGLE);
        $$0.yRot = (($$1 ? -30.0f : 30.0f) * 0.017453292f) + Mth.clamp(this.head.yRot, -0.5235988f, 0.5235988f);
    }

    protected void setupAttackAnimation(T $$0) {
        float $$1 = $$0.attackTime;
        if ($$1 <= 0.0f) {
        }
        this.body.yRot = Mth.sin(Mth.sqrt($$1) * 6.2831855f) * 0.2f;
        if ($$0.attackArm == HumanoidArm.LEFT) {
            this.body.yRot *= -1.0f;
        }
        float $$2 = $$0.ageScale;
        this.rightArm.z = Mth.sin(this.body.yRot) * 5.0f * $$2;
        this.rightArm.x = (-Mth.cos(this.body.yRot)) * 5.0f * $$2;
        this.leftArm.z = (-Mth.sin(this.body.yRot)) * 5.0f * $$2;
        this.leftArm.x = Mth.cos(this.body.yRot) * 5.0f * $$2;
        this.rightArm.yRot += this.body.yRot;
        this.leftArm.yRot += this.body.yRot;
        this.leftArm.xRot += this.body.yRot;
        switch ($$0.swingAnimationType) {
            case WHACK:
                float $$3 = Ease.outQuart($$1);
                float $$4 = Mth.sin($$3 * 3.1415927f);
                float $$5 = Mth.sin($$1 * 3.1415927f) * (-(this.head.xRot - 0.7f)) * 0.75f;
                ModelPart $$6 = getArm($$0.attackArm);
                $$6.xRot -= ($$4 * 1.2f) + $$5;
                $$6.yRot += this.body.yRot * 2.0f;
                $$6.zRot += Mth.sin($$1 * 3.1415927f) * (-0.4f);
                break;
            case STAB:
                SpearAnimations.thirdPersonAttackHand(this, $$0);
                break;
        }
    }

    private float quadraticArmUpdate(float $$0) {
        return ((-65.0f) * $$0) + ($$0 * $$0);
    }

    public void setAllVisible(boolean $$0) {
        this.head.visible = $$0;
        this.hat.visible = $$0;
        this.body.visible = $$0;
        this.rightArm.visible = $$0;
        this.leftArm.visible = $$0;
        this.rightLeg.visible = $$0;
        this.leftLeg.visible = $$0;
    }

    @Override // net.minecraft.client.model.ArmedModel
    public void translateToHand(HumanoidRenderState $$0, HumanoidArm $$1, PoseStack $$2) {
        this.root.translateAndRotate($$2);
        getArm($$1).translateAndRotate($$2);
    }

    public ModelPart getArm(HumanoidArm $$0) {
        if ($$0 == HumanoidArm.LEFT) {
            return this.leftArm;
        }
        return this.rightArm;
    }

    @Override // net.minecraft.client.model.HeadedModel
    public ModelPart getHead() {
        return this.head;
    }
}
