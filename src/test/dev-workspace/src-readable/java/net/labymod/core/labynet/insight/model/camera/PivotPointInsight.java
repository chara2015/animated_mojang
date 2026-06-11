package net.labymod.core.labynet.insight.model.camera;

import com.google.gson.JsonObject;
import net.labymod.core.labynet.insight.Insight;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/model/camera/PivotPointInsight.class */
public class PivotPointInsight implements Insight {
    private double x;
    private double y;
    private double z;

    public PivotPointInsight(JsonObject object) {
        fromJsonObject(object);
    }

    public PivotPointInsight(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", Double.valueOf(this.x));
        jsonObject.addProperty("y", Double.valueOf(this.y));
        jsonObject.addProperty("z", Double.valueOf(this.z));
        return jsonObject;
    }

    @Override // net.labymod.core.labynet.insight.Insight
    public void fromJsonObject(JsonObject object) {
        this.x = object.get("x").getAsDouble();
        this.y = object.get("y").getAsDouble();
        this.z = object.get("z").getAsDouble();
    }
}
