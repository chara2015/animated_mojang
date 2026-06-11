package net.labymod.api.service.model;

import java.util.HashMap;
import java.util.Map;
import net.labymod.api.models.version.Version;
import net.labymod.api.util.version.serial.VersionDeserializer;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/service/model/ServiceModel.class */
public class ServiceModel {
    private String serviceClass;
    private double classVersion;
    private final Map<String, Object> meta = new HashMap();

    public String getServiceClass() {
        return this.serviceClass;
    }

    public double getClassVersion() {
        return this.classVersion;
    }

    public Map<String, Object> getMeta() {
        return this.meta;
    }

    @Nullable
    public Version getVersion() {
        Object minecraftVersion = this.meta.get("gameVersion");
        if (!(minecraftVersion instanceof String)) {
            return null;
        }
        return VersionDeserializer.from((String) minecraftVersion);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ServiceModel{");
        sb.append("serviceClass='").append(this.serviceClass).append('\'');
        sb.append(", classVersion=").append(this.classVersion);
        sb.append(", meta=").append(this.meta);
        sb.append('}');
        return sb.toString();
    }
}
