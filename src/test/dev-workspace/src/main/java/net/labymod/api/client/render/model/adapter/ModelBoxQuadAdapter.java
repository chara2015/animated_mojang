package net.labymod.api.client.render.model.adapter;

import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.box.ModelBoxVertex;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.UVBounds;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/adapter/ModelBoxQuadAdapter.class */
public class ModelBoxQuadAdapter implements ShapeQuad {
    private final ModelBoxQuad modelBoxQuad;
    private final ShapeVertex[] adaptedVertices;
    private final UVBounds uvBounds;

    public ModelBoxQuadAdapter(@NotNull ModelBoxQuad modelBoxQuad) {
        this.modelBoxQuad = modelBoxQuad;
        ModelBoxVertex[] boxVertices = modelBoxQuad.getVertices();
        this.adaptedVertices = new ShapeVertex[boxVertices.length];
        for (int i = 0; i < boxVertices.length; i++) {
            this.adaptedVertices[i] = new ModelBoxVertexAdapter(boxVertices[i]);
        }
        this.uvBounds = new QuadUVBounds(modelBoxQuad);
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public ShapeVertex[] getVertices() {
        return this.adaptedVertices;
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public Vector3f getNormal() {
        return this.modelBoxQuad.getNormal();
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    public boolean isVisible() {
        return this.modelBoxQuad.isVisible();
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    public void setVisible(boolean visible) {
        this.modelBoxQuad.setVisible(visible);
    }

    @Override // net.labymod.api.client.render.model.geometry.ShapeQuad
    @NotNull
    public UVBounds getUVBounds() {
        return this.uvBounds;
    }

    @NotNull
    public ModelBoxQuad getModelBoxQuad() {
        return this.modelBoxQuad;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/adapter/ModelBoxQuadAdapter$QuadUVBounds.class */
    private static class QuadUVBounds implements UVBounds {
        private final ModelBoxQuad quad;

        QuadUVBounds(ModelBoxQuad quad) {
            this.quad = quad;
        }

        @Override // net.labymod.api.client.render.model.geometry.UVBounds
        public float getMinU() {
            return this.quad.getMinU();
        }

        @Override // net.labymod.api.client.render.model.geometry.UVBounds
        public float getMinV() {
            return this.quad.getMinV();
        }

        @Override // net.labymod.api.client.render.model.geometry.UVBounds
        public float getMaxU() {
            return this.quad.getMaxU();
        }

        @Override // net.labymod.api.client.render.model.geometry.UVBounds
        public float getMaxV() {
            return this.quad.getMaxV();
        }
    }
}
