package net.minecraft.client.renderer.block.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.vertex.PoseStack;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Type;
import java.lang.runtime.ObjectMethods;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/ItemTransform.class */
public final class ItemTransform extends Record {
    private final Vector3fc rotation;
    private final Vector3fc translation;
    private final Vector3fc scale;
    public static final ItemTransform NO_TRANSFORM = new ItemTransform(new Vector3f(), new Vector3f(), new Vector3f(1.0f, 1.0f, 1.0f));

    public ItemTransform(Vector3fc $$0, Vector3fc $$1, Vector3fc $$2) {
        this.rotation = $$0;
        this.translation = $$1;
        this.scale = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemTransform.class), ItemTransform.class, "rotation;translation;scale", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->rotation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->translation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->scale:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemTransform.class), ItemTransform.class, "rotation;translation;scale", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->rotation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->translation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->scale:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemTransform.class, Object.class), ItemTransform.class, "rotation;translation;scale", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->rotation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->translation:Lorg/joml/Vector3fc;", "FIELD:Lnet/minecraft/client/renderer/block/model/ItemTransform;->scale:Lorg/joml/Vector3fc;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Vector3fc rotation() {
        return this.rotation;
    }

    public Vector3fc translation() {
        return this.translation;
    }

    public Vector3fc scale() {
        return this.scale;
    }

    public void apply(boolean $$0, PoseStack.Pose $$1) {
        float $$5;
        float $$6;
        float $$7;
        if (this == NO_TRANSFORM) {
            $$1.translate(-0.5f, -0.5f, -0.5f);
            return;
        }
        if ($$0) {
            $$5 = -this.translation.x();
            $$6 = -this.rotation.y();
            $$7 = -this.rotation.z();
        } else {
            $$5 = this.translation.x();
            $$6 = this.rotation.y();
            $$7 = this.rotation.z();
        }
        $$1.translate($$5, this.translation.y(), this.translation.z());
        $$1.rotate(new Quaternionf().rotationXYZ(this.rotation.x() * 0.017453292f, $$6 * 0.017453292f, $$7 * 0.017453292f));
        $$1.scale(this.scale.x(), this.scale.y(), this.scale.z());
        $$1.translate(-0.5f, -0.5f, -0.5f);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/block/model/ItemTransform$Deserializer.class */
    protected static class Deserializer implements JsonDeserializer<ItemTransform> {
        private static final Vector3f DEFAULT_ROTATION = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f DEFAULT_TRANSLATION = new Vector3f(0.0f, 0.0f, 0.0f);
        private static final Vector3f DEFAULT_SCALE = new Vector3f(1.0f, 1.0f, 1.0f);
        public static final float MAX_TRANSLATION = 5.0f;
        public static final float MAX_SCALE = 4.0f;

        protected Deserializer() {
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        /* JADX INFO: renamed from: deserialize, reason: merged with bridge method [inline-methods] */
        public ItemTransform m822deserialize(JsonElement $$0, Type $$1, JsonDeserializationContext $$2) throws JsonParseException, JsonSyntaxException {
            JsonObject $$3 = $$0.getAsJsonObject();
            Vector3f $$4 = getVector3f($$3, "rotation", DEFAULT_ROTATION);
            Vector3f $$5 = getVector3f($$3, "translation", DEFAULT_TRANSLATION);
            $$5.mul(0.0625f);
            $$5.set(Mth.clamp($$5.x, -5.0f, 5.0f), Mth.clamp($$5.y, -5.0f, 5.0f), Mth.clamp($$5.z, -5.0f, 5.0f));
            Vector3f $$6 = getVector3f($$3, "scale", DEFAULT_SCALE);
            $$6.set(Mth.clamp($$6.x, -4.0f, 4.0f), Mth.clamp($$6.y, -4.0f, 4.0f), Mth.clamp($$6.z, -4.0f, 4.0f));
            return new ItemTransform($$4, $$5, $$6);
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonParseException */
        /* JADX INFO: Thrown type has an unknown type hierarchy: com.google.gson.JsonSyntaxException */
        private Vector3f getVector3f(JsonObject $$0, String $$1, Vector3f $$2) throws JsonParseException, JsonSyntaxException {
            if (!$$0.has($$1)) {
                return $$2;
            }
            JsonArray $$3 = GsonHelper.getAsJsonArray($$0, $$1);
            if ($$3.size() != 3) {
                throw new JsonParseException("Expected 3 " + $$1 + " values, found: " + $$3.size());
            }
            float[] $$4 = new float[3];
            for (int $$5 = 0; $$5 < $$4.length; $$5++) {
                $$4[$$5] = GsonHelper.convertToFloat($$3.get($$5), $$1 + "[" + $$5 + "]");
            }
            return new Vector3f($$4[0], $$4[1], $$4[2]);
        }
    }
}
