package net.minecraft.client.model.animal.nautilus;

import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.animation.definitions.NautilusAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.NautilusRenderState;
import net.minecraft.util.Mth;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/animal/nautilus/NautilusModel.class */
public class NautilusModel extends EntityModel<NautilusRenderState> {
    private static final float SWIM_ANIMATION_SPEED_MAX = 2.0f;
    private static final float SWIM_ANIMATION_SCALE_FACTOR = 3.0f;
    private static final float IDLE_SWIM_ANIMATION_SPEED = 0.2f;
    private static final float IDLE_SWIM_ANIMATION_SCALE = 5.0f;
    protected final ModelPart body;
    protected final ModelPart nautilus;
    private final KeyframeAnimation swimAnimation;

    public NautilusModel(ModelPart $$0) {
        super($$0);
        this.nautilus = $$0.getChild("root");
        this.body = this.nautilus.getChild(PartNames.BODY);
        this.swimAnimation = NautilusAnimation.SWIMMING.bake($$0);
    }

    public static LayerDefinition createBodyLayer() {
        return LayerDefinition.create(createBodyMesh(), 128, 128);
    }

    public static MeshDefinition createBodyMesh() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0f, 29.0f, -6.0f));
        $$2.addOrReplaceChild(PartNames.SHELL, CubeListBuilder.create().texOffs(0, 0).addBox(-7.0f, -10.0f, -7.0f, 14.0f, 10.0f, 16.0f, new CubeDeformation(0.0f)).texOffs(0, 26).addBox(-7.0f, 0.0f, -7.0f, 14.0f, 8.0f, 20.0f, new CubeDeformation(0.0f)).texOffs(48, 26).addBox(-7.0f, 0.0f, 6.0f, 14.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -13.0f, 5.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 54).addBox(-5.0f, -4.51f, -3.0f, 10.0f, 8.0f, 14.0f, new CubeDeformation(0.0f)).texOffs(0, 76).addBox(-5.0f, -4.51f, 7.0f, 10.0f, 8.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -8.5f, 12.3f));
        $$3.addOrReplaceChild(PartNames.UPPER_MOUTH, CubeListBuilder.create().texOffs(54, 54).addBox(-5.0f, -2.0f, 0.0f, 10.0f, 4.0f, 4.0f, new CubeDeformation(-0.001f)), PartPose.offset(0.0f, -2.51f, 7.0f));
        $$3.addOrReplaceChild(PartNames.INNER_MOUTH, CubeListBuilder.create().texOffs(54, 70).addBox(-3.0f, -2.0f, -0.5f, 6.0f, 4.0f, 4.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -0.51f, 7.5f));
        $$3.addOrReplaceChild(PartNames.LOWER_MOUTH, CubeListBuilder.create().texOffs(54, 62).addBox(-5.0f, -1.98f, 0.0f, 10.0f, 4.0f, 4.0f, new CubeDeformation(-0.001f)), PartPose.offset(0.0f, 1.49f, 7.0f));
        return $$0;
    }

    public static LayerDefinition createBabyBodyLayer() {
        MeshDefinition $$0 = new MeshDefinition();
        PartDefinition $$1 = $$0.getRoot();
        PartDefinition $$2 = $$1.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(-0.5f, 28.0f, -0.5f));
        $$2.addOrReplaceChild(PartNames.SHELL, CubeListBuilder.create().texOffs(0, 0).addBox(-6.0f, -4.0f, -1.0f, 7.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 11).addBox(-6.0f, 0.0f, -1.0f, 7.0f, 4.0f, 9.0f, new CubeDeformation(0.0f)).texOffs(23, 11).addBox(-6.0f, 0.0f, 5.0f, 7.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(3.0f, -8.0f, -2.0f));
        PartDefinition $$3 = $$2.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(0, 24).addBox(-2.5f, -3.01f, -1.0f, 5.0f, 4.0f, 7.0f, new CubeDeformation(0.0f)).texOffs(0, 35).addBox(-2.5f, -3.01f, 4.1f, 5.0f, 4.0f, 0.0f, new CubeDeformation(0.0f)), PartPose.offset(0.5f, -5.0f, 3.0f));
        $$3.addOrReplaceChild(PartNames.UPPER_MOUTH, CubeListBuilder.create().texOffs(24, 24).addBox(-2.5f, -1.0f, 0.0f, 5.0f, 2.0f, 2.0f, new CubeDeformation(-0.001f)), PartPose.offset(0.0f, -2.01f, 3.9f));
        $$3.addOrReplaceChild(PartNames.INNER_MOUTH, CubeListBuilder.create().texOffs(24, 32).addBox(-1.5f, -1.0f, -1.0f, 3.0f, 2.0f, 2.0f, new CubeDeformation(0.0f)), PartPose.offset(0.0f, -1.01f, 4.9f));
        $$3.addOrReplaceChild(PartNames.LOWER_MOUTH, CubeListBuilder.create().texOffs(24, 28).addBox(-2.5f, -1.0f, 0.0f, 5.0f, 2.0f, 2.0f, new CubeDeformation(-0.001f)), PartPose.offset(0.0f, -0.01f, 3.9f));
        return LayerDefinition.create($$0, 64, 64);
    }

    @Override // net.minecraft.client.model.Model
    public void setupAnim(NautilusRenderState $$0) {
        super.setupAnim($$0);
        applyBodyRotation($$0.yRot, $$0.xRot);
        this.swimAnimation.applyWalk($$0.walkAnimationPos + ($$0.ageInTicks / 5.0f), $$0.walkAnimationSpeed + 0.2f, 2.0f, 3.0f);
    }

    private void applyBodyRotation(float $$0, float $$1) {
        float $$02 = Mth.clamp($$0, -10.0f, 10.0f);
        float $$12 = Mth.clamp($$1, -10.0f, 10.0f);
        this.body.yRot = $$02 * 0.017453292f;
        this.body.xRot = $$12 * 0.017453292f;
    }
}
