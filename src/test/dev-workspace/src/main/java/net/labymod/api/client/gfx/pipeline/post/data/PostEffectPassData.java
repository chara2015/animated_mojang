package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostEffectPassData.class */
public class PostEffectPassData {

    @SerializedName("name")
    private String name;

    @SerializedName("sourceTarget")
    private String sourceTarget;

    @SerializedName("destinationTarget")
    private String destinationTarget;

    @SerializedName("clearSourceTarget")
    private boolean clearSourceTarget = false;

    @SerializedName("clearDestinationTarget")
    private boolean clearDestinationTarget = true;

    @SerializedName("otherTargets")
    private List<EffectData> effects = new ArrayList();

    public String getName() {
        return this.name;
    }

    public String getSourceTarget() {
        return this.sourceTarget;
    }

    public boolean isClearSourceTarget() {
        return this.clearSourceTarget;
    }

    public String getDestinationTarget() {
        return this.destinationTarget;
    }

    public boolean isClearDestinationTarget() {
        return this.clearDestinationTarget;
    }

    public List<EffectData> getEffects() {
        return this.effects;
    }
}
