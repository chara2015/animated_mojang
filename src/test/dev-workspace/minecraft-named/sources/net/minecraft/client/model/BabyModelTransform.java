package net.minecraft.client.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.model.geom.builders.PartDefinition;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/model/BabyModelTransform.class */
public final class BabyModelTransform extends Record implements MeshTransformer {
    private final boolean scaleHead;
    private final float babyYHeadOffset;
    private final float babyZHeadOffset;
    private final float babyHeadScale;
    private final float babyBodyScale;
    private final float bodyYOffset;
    private final Set<String> headParts;

    public BabyModelTransform(boolean $$0, float $$1, float $$2, float $$3, float $$4, float $$5, Set<String> $$6) {
        this.scaleHead = $$0;
        this.babyYHeadOffset = $$1;
        this.babyZHeadOffset = $$2;
        this.babyHeadScale = $$3;
        this.babyBodyScale = $$4;
        this.bodyYOffset = $$5;
        this.headParts = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BabyModelTransform.class), BabyModelTransform.class, "scaleHead;babyYHeadOffset;babyZHeadOffset;babyHeadScale;babyBodyScale;bodyYOffset;headParts", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->scaleHead:Z", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyYHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyZHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyHeadScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyBodyScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->bodyYOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->headParts:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BabyModelTransform.class), BabyModelTransform.class, "scaleHead;babyYHeadOffset;babyZHeadOffset;babyHeadScale;babyBodyScale;bodyYOffset;headParts", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->scaleHead:Z", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyYHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyZHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyHeadScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyBodyScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->bodyYOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->headParts:Ljava/util/Set;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BabyModelTransform.class, Object.class), BabyModelTransform.class, "scaleHead;babyYHeadOffset;babyZHeadOffset;babyHeadScale;babyBodyScale;bodyYOffset;headParts", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->scaleHead:Z", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyYHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyZHeadOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyHeadScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->babyBodyScale:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->bodyYOffset:F", "FIELD:Lnet/minecraft/client/model/BabyModelTransform;->headParts:Ljava/util/Set;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public boolean scaleHead() {
        return this.scaleHead;
    }

    public float babyYHeadOffset() {
        return this.babyYHeadOffset;
    }

    public float babyZHeadOffset() {
        return this.babyZHeadOffset;
    }

    public float babyHeadScale() {
        return this.babyHeadScale;
    }

    public float babyBodyScale() {
        return this.babyBodyScale;
    }

    public float bodyYOffset() {
        return this.bodyYOffset;
    }

    public Set<String> headParts() {
        return this.headParts;
    }

    public BabyModelTransform(Set<String> $$0) {
        this(false, 5.0f, 2.0f, $$0);
    }

    public BabyModelTransform(boolean $$0, float $$1, float $$2, Set<String> $$3) {
        this($$0, $$1, $$2, 2.0f, 2.0f, 24.0f, $$3);
    }

    @Override // net.minecraft.client.model.geom.builders.MeshTransformer
    public MeshDefinition apply(MeshDefinition $$0) {
        float $$1 = this.scaleHead ? 1.5f / this.babyHeadScale : 1.0f;
        float $$2 = 1.0f / this.babyBodyScale;
        UnaryOperator<PartPose> $$3 = $$12 -> {
            return $$12.translated(0.0f, this.babyYHeadOffset, this.babyZHeadOffset).scaled($$1);
        };
        UnaryOperator<PartPose> $$4 = $$13 -> {
            return $$13.translated(0.0f, this.bodyYOffset, 0.0f).scaled($$2);
        };
        MeshDefinition $$5 = new MeshDefinition();
        for (Map.Entry<String, PartDefinition> $$6 : $$0.getRoot().getChildren()) {
            String $$7 = $$6.getKey();
            PartDefinition $$8 = $$6.getValue();
            $$5.getRoot().addOrReplaceChild($$7, $$8.transformed(this.headParts.contains($$7) ? $$3 : $$4));
        }
        return $$5;
    }
}
