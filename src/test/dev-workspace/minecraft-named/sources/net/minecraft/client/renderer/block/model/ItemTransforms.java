package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Type;
import java.lang.runtime.ObjectMethods;
import net.minecraft.world.item.ItemDisplayContext;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/ItemTransforms.class */
public final class ItemTransforms extends Record {
    private final ItemTransform thirdPersonLeftHand;
    private final ItemTransform thirdPersonRightHand;
    private final ItemTransform firstPersonLeftHand;
    private final ItemTransform firstPersonRightHand;
    private final ItemTransform head;
    private final ItemTransform gui;
    private final ItemTransform ground;
    private final ItemTransform fixed;
    private final ItemTransform fixedFromBottom;
    public static final ItemTransforms NO_TRANSFORMS = new ItemTransforms(ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM, ItemTransform.NO_TRANSFORM);

    public ItemTransforms(ItemTransform $$0, ItemTransform $$1, ItemTransform $$2, ItemTransform $$3, ItemTransform $$4, ItemTransform $$5, ItemTransform $$6, ItemTransform $$7, ItemTransform $$8) {
        this.thirdPersonLeftHand = $$0;
        this.thirdPersonRightHand = $$1;
        this.firstPersonLeftHand = $$2;
        this.firstPersonRightHand = $$3;
        this.head = $$4;
        this.gui = $$5;
        this.ground = $$6;
        this.fixed = $$7;
        this.fixedFromBottom = $$8;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemTransforms.class), ItemTransforms.class, "thirdPersonLeftHand;thirdPersonRightHand;firstPersonLeftHand;firstPersonRightHand;head;gui;ground;fixed;fixedFromBottom", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->head:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->gui:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->ground:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixed:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixedFromBottom:Lnet/minecraft/client/renderer/block/model/ItemTransform;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemTransforms.class), ItemTransforms.class, "thirdPersonLeftHand;thirdPersonRightHand;firstPersonLeftHand;firstPersonRightHand;head;gui;ground;fixed;fixedFromBottom", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->head:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->gui:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->ground:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixed:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixedFromBottom:Lnet/minecraft/client/renderer/block/model/ItemTransform;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemTransforms.class, Object.class), ItemTransforms.class, "thirdPersonLeftHand;thirdPersonRightHand;firstPersonLeftHand;firstPersonRightHand;head;gui;ground;fixed;fixedFromBottom", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->thirdPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonLeftHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->firstPersonRightHand:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->head:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->gui:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->ground:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixed:Lnet/minecraft/client/renderer/block/model/ItemTransform;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransforms;->fixedFromBottom:Lnet/minecraft/client/renderer/block/model/ItemTransform;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ItemTransform thirdPersonLeftHand() {
        return this.thirdPersonLeftHand;
    }

    public ItemTransform thirdPersonRightHand() {
        return this.thirdPersonRightHand;
    }

    public ItemTransform firstPersonLeftHand() {
        return this.firstPersonLeftHand;
    }

    public ItemTransform firstPersonRightHand() {
        return this.firstPersonRightHand;
    }

    public ItemTransform head() {
        return this.head;
    }

    public ItemTransform gui() {
        return this.gui;
    }

    public ItemTransform ground() {
        return this.ground;
    }

    public ItemTransform fixed() {
        return this.fixed;
    }

    public ItemTransform fixedFromBottom() {
        return this.fixedFromBottom;
    }

    public ItemTransform getTransform(ItemDisplayContext $$0) {
        switch ($$0) {
            case THIRD_PERSON_LEFT_HAND:
                return this.thirdPersonLeftHand;
            case THIRD_PERSON_RIGHT_HAND:
                return this.thirdPersonRightHand;
            case FIRST_PERSON_LEFT_HAND:
                return this.firstPersonLeftHand;
            case FIRST_PERSON_RIGHT_HAND:
                return this.firstPersonRightHand;
            case HEAD:
                return this.head;
            case GUI:
                return this.gui;
            case GROUND:
                return this.ground;
            case FIXED:
                return this.fixed;
            case ON_SHELF:
                return this.fixedFromBottom;
            default:
                return ItemTransform.NO_TRANSFORM;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/ItemTransforms$Deserializer.class */
    protected static class Deserializer implements JsonDeserializer<ItemTransforms> {
        protected Deserializer() {
        }

        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public ItemTransforms m825deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException {
            JsonObject $$3 = $$0.getAsJsonObject();
            ItemTransform $$4 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND);
            ItemTransform $$5 = getTransform($$2, $$3, ItemDisplayContext.THIRD_PERSON_LEFT_HAND);
            if ($$5 == ItemTransform.NO_TRANSFORM) {
                $$5 = $$4;
            }
            ItemTransform $$6 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_RIGHT_HAND);
            ItemTransform $$7 = getTransform($$2, $$3, ItemDisplayContext.FIRST_PERSON_LEFT_HAND);
            if ($$7 == ItemTransform.NO_TRANSFORM) {
                $$7 = $$6;
            }
            ItemTransform $$8 = getTransform($$2, $$3, ItemDisplayContext.HEAD);
            ItemTransform $$9 = getTransform($$2, $$3, ItemDisplayContext.GUI);
            ItemTransform $$10 = getTransform($$2, $$3, ItemDisplayContext.GROUND);
            ItemTransform $$11 = getTransform($$2, $$3, ItemDisplayContext.FIXED);
            ItemTransform $$12 = getTransform($$2, $$3, ItemDisplayContext.ON_SHELF);
            return new ItemTransforms($$5, $$4, $$7, $$6, $$8, $$9, $$10, $$11, $$12);
        }

        private ItemTransform getTransform(JsonDeserializationContext $$0, JsonObject $$1, ItemDisplayContext $$2) {
            String $$3 = $$2.getSerializedName();
            if ($$1.has($$3)) {
                return (ItemTransform) $$0.deserialize($$1.get($$3), ItemTransform.class);
            }
            return ItemTransform.NO_TRANSFORM;
        }
    }
}
