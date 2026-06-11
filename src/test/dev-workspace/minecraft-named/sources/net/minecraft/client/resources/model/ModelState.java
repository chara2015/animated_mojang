package net.minecraft.client.resources.model;

import com.mojang.math.Transformation;
import net.minecraft.core.Direction;
import org.joml.Matrix4f;
import org.joml.Matrix4fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/model/ModelState.class */
public interface ModelState {
    public static final Matrix4fc NO_TRANSFORM = new Matrix4f();

    default Transformation transformation() {
        return Transformation.identity();
    }

    default Matrix4fc faceTransformation(Direction $$0) {
        return NO_TRANSFORM;
    }

    default Matrix4fc inverseFaceTransformation(Direction $$0) {
        return NO_TRANSFORM;
    }
}
