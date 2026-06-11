package net.labymod.core.client.gfx.pipeline.blaze3d;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gfx/pipeline/blaze3d/Blaze3DVertexProvider.class */
public abstract class Blaze3DVertexProvider {
    private final List<Blaze3DVertex> vertices = new ArrayList();

    protected Blaze3DVertexProvider() {
    }

    public void addVertex(Blaze3DVertex vertex) {
        this.vertices.add(vertex);
    }

    public List<Blaze3DVertex> vertices() {
        return this.vertices;
    }
}
