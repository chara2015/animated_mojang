package net.labymod.api.client.gfx.pipeline.post.data;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.resources.ResourceLocation;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gfx/pipeline/post/data/PostProcessorConfig.class */
public class PostProcessorConfig {

    @SerializedName("vertex")
    private ResourceLocation vertex;

    @SerializedName("fragment")
    private ResourceLocation fragment;

    @SerializedName("keywords")
    private final List<String> keywords = new ArrayList();

    @SerializedName("targets")
    private List<PostProcessorTarget> targets = new ArrayList();

    @SerializedName("passes")
    private List<PostEffectPassData> passes = new ArrayList();

    @SerializedName("samplers")
    private List<PostProcessorSampler> samplers = new ArrayList();

    @SerializedName("uniformBlocks")
    private List<PostProcessorUniformBlock> uniformBlocks = new ArrayList();

    public ResourceLocation getVertex() {
        return this.vertex;
    }

    public ResourceLocation getFragment() {
        return this.fragment;
    }

    public List<String> getKeywords() {
        return this.keywords;
    }

    public List<PostProcessorTarget> getTargets() {
        return this.targets;
    }

    public List<PostEffectPassData> getPasses() {
        return this.passes;
    }

    public List<PostProcessorUniformBlock> getUniformBlocks() {
        return this.uniformBlocks;
    }

    public List<PostProcessorSampler> getSamplers() {
        return this.samplers;
    }
}
