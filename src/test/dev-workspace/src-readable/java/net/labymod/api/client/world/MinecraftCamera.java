package net.labymod.api.client.world;

import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/MinecraftCamera.class */
public interface MinecraftCamera {
    @NotNull
    DoubleVector3 position();

    @NotNull
    Quaternionf cameraRotation();

    @NotNull
    default DoubleVector3 renderPosition() {
        return position();
    }

    default float getYaw() {
        return JomlMath.getYaw(cameraRotation());
    }

    default float getPitch() {
        return JomlMath.getPitch(cameraRotation());
    }
}
