package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/EffectData.class */
public class EffectData {

    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    @SerializedName(ParameterType.TYPE)
    private Type type = Type.RENDER_TARGET;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/EffectData$Type.class */
    public enum Type {
        RENDER_TARGET,
        TEXTURE
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }
}
