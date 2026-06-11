package net.labymod.core.labynet.insight.model.camera;

import com.google.gson.JsonObject;
import net.labymod.api.util.math.vector.DoubleVector3;
import net.labymod.core.labynet.insight.Insight;
import org.joml.Quaternionf;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/camera/CameraInsight.class */
public class CameraInsight implements Insight {
    private DoubleVector3 position;
    private Quaternionf rotation;
    private double fov;
    private PivotPointInsight pivotPoint;

    public CameraInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public CameraInsight(DoubleVector3 position, Quaternionf rotation, double fov) {
        this.position = position;
        this.rotation = rotation;
        this.fov = fov;
    }

    public void setPivotPoint(PivotPointInsight pivotPoint) {
        this.pivotPoint = pivotPoint;
    }

    public Quaternionf getRotation() {
        return this.rotation;
    }

    public DoubleVector3 getPosition() {
        return this.position;
    }

    public double getFov() {
        return this.fov;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject camera = new JsonObject();
        JsonObject rotation = new JsonObject();
        rotation.addProperty("x", Float.valueOf(this.rotation.x()));
        rotation.addProperty("y", Float.valueOf(this.rotation.y()));
        rotation.addProperty("z", Float.valueOf(this.rotation.z()));
        rotation.addProperty("w", Float.valueOf(this.rotation.w()));
        camera.add("rotation", rotation);
        JsonObject position = new JsonObject();
        position.addProperty("x", Double.valueOf(this.position.getX()));
        position.addProperty("y", Double.valueOf(this.position.getY()));
        position.addProperty("z", Double.valueOf(this.position.getZ()));
        camera.add("position", position);
        camera.addProperty("fov", Double.valueOf(this.fov));
        if (this.pivotPoint != null) {
            camera.add("pivot_point", this.pivotPoint.toJsonObject());
        }
        return camera;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        JsonObject rotation = object.get("rotation").getAsJsonObject();
        this.rotation = new Quaternionf(rotation.get("x").getAsFloat(), rotation.get("y").getAsFloat(), rotation.get("z").getAsFloat(), rotation.get("w").getAsFloat());
        JsonObject position = object.get("position").getAsJsonObject();
        this.position = new DoubleVector3(position.get("x").getAsDouble(), position.get("y").getAsDouble(), position.get("z").getAsDouble());
        this.fov = object.get("fov").getAsDouble();
        if (object.has("pivot_point")) {
            this.pivotPoint = new PivotPointInsight(object.get("pivot_point").getAsJsonObject());
        }
    }
}
