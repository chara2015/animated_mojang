package net.labymod.core.main.user.shop.cosmetic.state;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.client.render.matrix.DefaultStackProvider;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.core.main.user.shop.cosmetic.appearance.PartAppearanceStore;
import net.labymod.laby3d.api.mesh.Mesh;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/state/CosmeticRenderState.class */
public class CosmeticRenderState {
    private long lastGlobalVersion;
    private long outlineLastGlobalVersion;
    private boolean opaqueTexture;
    private boolean opacityAnalyzed;
    private final List<CosmeticsUniformBlockData> groupCosmeticsData = new ArrayList();
    private final List<Mesh> compiledMeshes = new ArrayList();
    private final List<Mesh> outlineMeshes = new ArrayList();
    private final PartAppearanceStore appearanceStore = new PartAppearanceStore();
    private final Stack stack = Stack.create((StackProvider) new DefaultStackProvider());

    public PartAppearanceStore appearanceStore() {
        return this.appearanceStore;
    }

    public List<CosmeticsUniformBlockData> groupCosmeticsData() {
        return this.groupCosmeticsData;
    }

    public Stack stack() {
        return this.stack;
    }

    public List<Mesh> compiledMeshes() {
        return this.compiledMeshes;
    }

    public List<Mesh> outlineMeshes() {
        return this.outlineMeshes;
    }

    public long lastGlobalVersion() {
        return this.lastGlobalVersion;
    }

    public void setLastGlobalVersion(long version) {
        this.lastGlobalVersion = version;
    }

    public long outlineLastGlobalVersion() {
        return this.outlineLastGlobalVersion;
    }

    public void setOutlineLastGlobalVersion(long version) {
        this.outlineLastGlobalVersion = version;
    }

    public boolean isOpaqueTexture() {
        return this.opaqueTexture;
    }

    public void setOpaqueTexture(boolean opaque) {
        this.opaqueTexture = opaque;
    }

    public boolean isOpacityAnalyzed() {
        return this.opacityAnalyzed;
    }

    public void setOpacityAnalyzed(boolean analyzed) {
        this.opacityAnalyzed = analyzed;
    }

    public void close() {
        for (Mesh mesh : this.compiledMeshes) {
            if (mesh != null) {
                mesh.close();
            }
        }
        this.compiledMeshes.clear();
        for (Mesh mesh2 : this.outlineMeshes) {
            if (mesh2 != null) {
                mesh2.close();
            }
        }
        this.outlineMeshes.clear();
    }
}
