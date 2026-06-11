package net.labymod.v1_21_11.mixins.client.model.geom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.Transformation;
import net.labymod.v1_21_11.client.render.model.ModelPartAnimationTransformation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/mixins/client/model/geom/MixinModelPart.class */
@Mixin({ModelPart.class})
public abstract class MixinModelPart implements net.labymod.api.client.render.model.ModelPart {
    private final Transformation labyMod$transformation = new Transformation();
    private final Transformation labyMod$animationTransformation = new ModelPartAnimationTransformation((ModelPart) this, this.labyMod$transformation);
    private final List<ModelBox> labyMod$boxes = new ArrayList();

    @Shadow
    public boolean visible;

    @Shadow
    @Final
    private Map<String, ModelPart> children;

    @Shadow
    @Final
    private List<ModelPart.Cube> cubes;

    public int getId() {
        return 0;
    }

    public void setId(int id) {
    }

    @Inject(method = {"loadPose"}, at = {@At("TAIL")})
    public void labyMod$setDefaultValues(PartPose partPose, CallbackInfo callbackInfo) {
        this.labyMod$transformation.setTranslation(partPose.x(), partPose.y(), partPose.z());
        this.labyMod$transformation.setRotation(partPose.xRot(), partPose.yRot(), partPose.zRot());
        this.labyMod$transformation.setScale(partPose.xScale(), partPose.yScale(), partPose.zScale());
    }

    public Transformation getModelPartTransform() {
        return this.labyMod$transformation;
    }

    public Transformation getAnimationTransformation() {
        return this.labyMod$animationTransformation;
    }

    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    public void addBox(ModelBox box) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    public List<ModelBox> getBoxes() {
        if (this.labyMod$boxes.isEmpty()) {
            Iterator<ModelPart.Cube> it = this.cubes.iterator();
            while (it.hasNext()) {
                this.labyMod$boxes.add(((ModelPart.Cube) it.next()).getModelBox());
            }
        }
        return this.labyMod$boxes;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void copyFrom(net.labymod.api.client.render.model.ModelPart modelPart) {
        this.labyMod$transformation.set(modelPart.getModelPartTransform());
        this.labyMod$animationTransformation.set(modelPart.getAnimationTransformation());
    }

    public int getTextureWidth() {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    public int getTextureHeight() {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    public void setTextureSize(int textureWidth, int textureHeight) {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    public int getTextureOffsetX() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    public int getTextureOffsetY() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    public void setTextureOffset(int textureOffsetX, int textureOffsetY) {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    public void addChild(String name, net.labymod.api.client.render.model.ModelPart child) {
        throw new UnsupportedOperationException("Adding children not supported");
    }

    public net.labymod.api.client.render.model.ModelPart getChild(String name) {
        return this.children.get(name);
    }

    public Map<String, net.labymod.api.client.render.model.ModelPart> getChildren() {
        return this.children;
    }

    public void reset() {
        this.labyMod$animationTransformation.reset();
    }
}

