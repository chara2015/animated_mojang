package net.labymod.v1_20_6.mixins.client.model.geom;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_20_6.client.render.model.ModelPartAnimationTransformation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_6/mixins/client/model/geom/MixinModelPart.class */
@Mixin({fxc.class})
public abstract class MixinModelPart implements ModelPart {
    private final Transformation labyMod$transformation = new Transformation();
    private final Transformation labyMod$animationTransformation = new ModelPartAnimationTransformation((fxc) this, this.labyMod$transformation);
    private final List<ModelBox> labyMod$boxes = new ArrayList();

    @Shadow
    public float b;

    @Shadow
    public float c;

    @Shadow
    public float d;

    @Shadow
    public float e;

    @Shadow
    public float f;

    @Shadow
    public float g;

    @Shadow
    public boolean k;

    @Shadow
    @Final
    private Map<String, fxc> n;

    @Shadow
    @Final
    private List<a> m;

    @Shadow
    public abstract void a(faa faaVar, fae faeVar, int i, int i2, float f, float f2, float f3, float f4);

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getId() {
        return 0;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setId(int id) {
    }

    @Inject(method = {"loadPose"}, at = {@At("TAIL")})
    public void labyMod$setDefaultValues(fxe partPose, CallbackInfo callbackInfo) {
        this.labyMod$transformation.setTranslation(this.b, this.c, this.d);
        this.labyMod$transformation.setRotation(this.e, this.f, this.g);
        this.labyMod$transformation.setScale(1.0f);
    }

    @Insert(method = {"translateAndRotate(Lcom/mojang/blaze3d/vertex/PoseStack;)V"}, at = @At("TAIL"))
    public void labyMod$applyScale(faa stack, InsertInfo insertInfo) {
        FloatVector3 scale = this.labyMod$animationTransformation.getScale();
        float scaleX = scale.getX();
        float scaleY = scale.getY();
        float scaleZ = scale.getZ();
        if (scaleX != 1.0f || scaleY != 1.0f || scaleZ != 1.0f) {
            stack.b(scaleX, scaleY, scaleZ);
        }
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public Transformation getModelPartTransform() {
        return this.labyMod$transformation;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public Transformation getAnimationTransformation() {
        return this.labyMod$animationTransformation;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(ModelBox box) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public List<ModelBox> getBoxes() {
        if (this.labyMod$boxes.isEmpty()) {
            Iterator<a> it = this.m.iterator();
            while (it.hasNext()) {
                this.labyMod$boxes.add(((a) it.next()).getModelBox());
            }
        }
        return this.labyMod$boxes;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public boolean isVisible() {
        return this.k;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setVisible(boolean visible) {
        this.k = visible;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void copyFrom(ModelPart modelPart) {
        this.labyMod$transformation.set(modelPart.getModelPartTransform());
        this.labyMod$animationTransformation.set(modelPart.getAnimationTransformation());
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureWidth() {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureHeight() {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureSize(int textureWidth, int textureHeight) {
        throw new UnsupportedOperationException("Texture size not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetX() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetY() {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureOffset(int textureOffsetX, int textureOffsetY) {
        throw new UnsupportedOperationException("Texture offset not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        throw new UnsupportedOperationException("Adding children not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        return this.n.get(name);
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        return this.n;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void reset() {
        this.labyMod$animationTransformation.reset();
    }
}
