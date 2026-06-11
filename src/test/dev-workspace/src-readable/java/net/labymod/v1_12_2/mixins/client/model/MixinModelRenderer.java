package net.labymod.v1_12_2.mixins.client.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.util.math.Transformation;
import net.labymod.api.util.math.vector.FloatVector3;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import net.labymod.v1_12_2.client.render.model.ModelRendererAnimationTransformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/client/model/MixinModelRenderer.class */
@Mixin({brs.class})
public abstract class MixinModelRenderer implements ModelPart {
    private final Transformation labyMod$transformation = new Transformation();
    private final Transformation labyMod$originalTransformation = new Transformation();
    private final Transformation labyMod$animationTransformation = new ModelRendererAnimationTransformation((brs) this, this.labyMod$transformation);

    @Shadow
    public boolean j;

    @Shadow
    public float a;

    @Shadow
    public float b;

    @Shadow
    public List<brq> l;

    @Shadow
    public List<brs> m;

    @Shadow
    private int r;

    @Shadow
    private int s;
    private boolean labyMod$defaultsSet;

    @Shadow
    public abstract void a(float f, float f2, float f3, int i, int i2, int i3, float f4);

    @Insert(method = {"setRotationPoint(FFF)V"}, at = @At("HEAD"))
    public void labyMod$setDefaultValues(float x, float y, float z, InsertInfo callbackInfo) {
        if (!this.labyMod$defaultsSet) {
            this.labyMod$transformation.setTranslation(x, y, z);
            this.labyMod$transformation.setRotation(0.0f, 0.0f, 0.0f);
            this.labyMod$transformation.setScale(1.0f);
            this.labyMod$originalTransformation.set(this.labyMod$transformation);
            this.labyMod$defaultsSet = true;
        }
    }

    @Inject(method = {"render(F)V"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GlStateManager;callList(I)V", shift = At.Shift.BEFORE)})
    public void labyMod$applyScale(float modelScale, CallbackInfo callbackInfo) {
        FloatVector3 currentScale = this.labyMod$animationTransformation.getScale();
        if (currentScale.getX() != 1.0f || currentScale.getY() != 1.0f || currentScale.getZ() != 1.0f) {
            bus.b(currentScale.getX(), currentScale.getY(), currentScale.getZ());
        }
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(float x, float y, float z, float width, float height, float depth, float delta, boolean mirror) {
        this.l.add(new brq((brs) this, this.r, this.s, x, y, z, (int) width, (int) height, (int) delta, delta, mirror));
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void addBox(ModelBox box) {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public List<ModelBox> getBoxes() {
        throw new UnsupportedOperationException("Boxes not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public boolean isVisible() {
        return this.j;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setVisible(boolean visible) {
        this.j = visible;
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
    public void copyFrom(ModelPart modelPart) {
        this.labyMod$transformation.set(modelPart.getModelPartTransform());
        this.labyMod$animationTransformation.set(modelPart.getAnimationTransformation());
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureWidth() {
        return (int) this.a;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureHeight() {
        return (int) this.b;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureSize(int textureWidth, int textureHeight) {
        this.a = textureWidth;
        this.b = textureHeight;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetX() {
        return this.r;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public int getTextureOffsetY() {
        return this.s;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void setTextureOffset(int textureOffsetX, int textureOffsetY) {
        this.r = textureOffsetX;
        this.s = textureOffsetY;
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public void addChild(String name, ModelPart child) {
        throw new UnsupportedOperationException("Adding children not supported");
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public ModelPart getChild(String name) {
        Iterator<brs> it = this.m.iterator();
        while (it.hasNext()) {
            ModelPart modelPart = (brs) it.next();
            if (((brs) modelPart).n.equals(name)) {
                return modelPart;
            }
        }
        return null;
    }

    @Override // net.labymod.api.client.render.model.ModelPartHolder
    public Map<String, ModelPart> getChildren() {
        Map<String, ModelPart> children = new HashMap<>();
        Iterator<brs> it = this.m.iterator();
        while (it.hasNext()) {
            ModelPart modelPart = (brs) it.next();
            children.put(((brs) modelPart).n, modelPart);
        }
        return children;
    }

    @Override // net.labymod.api.client.render.model.ModelPart
    public void reset() {
        this.labyMod$transformation.set(this.labyMod$originalTransformation);
        this.labyMod$animationTransformation.reset();
    }
}
