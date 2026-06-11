package net.minecraft.client.renderer.block.model;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelDebugName;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.QuadCollection;
import net.minecraft.client.resources.model.UnbakedGeometry;
import net.minecraft.core.Direction;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/SimpleUnbakedGeometry.class */
public final class SimpleUnbakedGeometry extends Record implements UnbakedGeometry {
    private final List<BlockElement> elements;

    public SimpleUnbakedGeometry(List<BlockElement> $$0) {
        this.elements = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SimpleUnbakedGeometry.class), SimpleUnbakedGeometry.class, "elements", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleUnbakedGeometry;->elements:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SimpleUnbakedGeometry.class), SimpleUnbakedGeometry.class, "elements", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleUnbakedGeometry;->elements:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SimpleUnbakedGeometry.class, Object.class), SimpleUnbakedGeometry.class, "elements", "FIELD:Lnet/minecraft/client/renderer/block/model/SimpleUnbakedGeometry;->elements:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<BlockElement> elements() {
        return this.elements;
    }

    @Override // net.minecraft.client.resources.model.UnbakedGeometry
    public QuadCollection bake(TextureSlots $$0, ModelBaker $$1, ModelState $$2, ModelDebugName $$3) {
        return bake(this.elements, $$0, $$1, $$2, $$3);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static QuadCollection bake(List<BlockElement> $$0, TextureSlots $$1, ModelBaker $$2, ModelState $$3, ModelDebugName $$4) throws MatchException {
        boolean z;
        QuadCollection.Builder $$5 = new QuadCollection.Builder();
        for (BlockElement $$6 : $$0) {
            boolean $$7 = true;
            boolean $$8 = true;
            boolean $$9 = true;
            Vector3fc $$10 = $$6.from();
            Vector3fc $$11 = $$6.to();
            if ($$10.x() == $$11.x()) {
                $$8 = false;
                $$9 = false;
            }
            if ($$10.y() == $$11.y()) {
                $$7 = false;
                $$9 = false;
            }
            if ($$10.z() == $$11.z()) {
                $$7 = false;
                $$8 = false;
            }
            if ($$7 || $$8 || $$9) {
                for (Map.Entry<Direction, BlockElementFace> $$12 : $$6.faces().entrySet()) {
                    Direction $$13 = $$12.getKey();
                    BlockElementFace $$14 = $$12.getValue();
                    switch ($$13.getAxis()) {
                        case X:
                            z = $$7;
                            break;
                        case Y:
                            z = $$8;
                            break;
                        case Z:
                            z = $$9;
                            break;
                        default:
                            throw new MatchException((String) null, (Throwable) null);
                    }
                    boolean $$15 = z;
                    if ($$15) {
                        TextureAtlasSprite $$16 = $$2.sprites().resolveSlot($$1, $$14.texture(), $$4);
                        BakedQuad $$17 = FaceBakery.bakeQuad($$2.parts(), $$10, $$11, $$14, $$16, $$13, $$3, $$6.rotation(), $$6.shade(), $$6.lightEmission());
                        if ($$14.cullForDirection() == null) {
                            $$5.addUnculledFace($$17);
                        } else {
                            $$5.addCulledFace(Direction.rotate($$3.transformation().getMatrix(), $$14.cullForDirection()), $$17);
                        }
                    }
                }
            }
        }
        return $$5.build();
    }
}
