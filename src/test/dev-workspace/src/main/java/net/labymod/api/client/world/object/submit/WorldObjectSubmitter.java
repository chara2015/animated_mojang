package net.labymod.api.client.world.object.submit;

import net.labymod.api.Laby;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.state.world.CameraSnapshot;
import net.labymod.api.client.world.object.WorldObject;
import net.labymod.api.client.world.object.snapshot.WorldObjectSnapshot;
import net.labymod.api.laby3d.render.queue.SubmissionCollector;
import net.labymod.api.util.math.vector.DoubleVector3;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/world/object/submit/WorldObjectSubmitter.class */
public abstract class WorldObjectSubmitter<WO extends WorldObject, S extends WorldObjectSnapshot> {
    private static final float GROUND_OFFSET = 1.0E-4f;

    public abstract void submit(@NotNull Stack stack, @NotNull SubmissionCollector submissionCollector, @NotNull S s);

    @NotNull
    public abstract S createSnapshot(@NotNull WO wo, double d, double d2, double d3, int i, @NotNull CameraSnapshot cameraSnapshot);

    @NotNull
    public final S createSnapshot(@NotNull WO wo, @NotNull CameraSnapshot cameraSnapshot, float f) {
        DoubleVector3 doubleVector3Position = wo.position();
        DoubleVector3 doubleVector3PreviousPosition = wo.previousPosition();
        DoubleVector3 doubleVector3RenderPosition = cameraSnapshot.renderPosition();
        S s = (S) createSnapshot(wo, doubleVector3Position.lerpX(doubleVector3PreviousPosition, f) - doubleVector3RenderPosition.getX(), (doubleVector3Position.lerpY(doubleVector3PreviousPosition, f) - doubleVector3RenderPosition.getY()) + 9.999999747378752E-5d, doubleVector3Position.lerpZ(doubleVector3PreviousPosition, f) - doubleVector3RenderPosition.getZ(), Laby.references().clientWorld().getPackedLight(doubleVector3Position), cameraSnapshot);
        extractAdditionalData(wo, s, f);
        return s;
    }

    public void extractAdditionalData(@NotNull WO object, @NotNull S snapshot, float partialTicks) {
    }
}
