package net.minecraft.client.resources.model;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.resources.Identifier;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelBaker.class */
public interface ModelBaker {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelBaker$SharedOperationKey.class */
    @FunctionalInterface
    public interface SharedOperationKey<T> {
        T compute(ModelBaker modelBaker);
    }

    ResolvedModel getModel(Identifier identifier);

    BlockModelPart missingBlockModelPart();

    SpriteGetter sprites();

    PartCache parts();

    <T> T compute(SharedOperationKey<T> sharedOperationKey);

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelBaker$PartCache.class */
    public interface PartCache {
        Vector3fc vector(Vector3fc vector3fc);

        default Vector3fc vector(float $$0, float $$1, float $$2) {
            return vector(new Vector3f($$0, $$1, $$2));
        }
    }
}
