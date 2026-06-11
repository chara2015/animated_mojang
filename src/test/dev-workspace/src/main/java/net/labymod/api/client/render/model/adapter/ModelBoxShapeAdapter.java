package net.labymod.api.client.render.model.adapter;

import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.geometry.BoundingBox;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeType;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/adapter/ModelBoxShapeAdapter.class */
public class ModelBoxShapeAdapter implements Shape {
    private final ModelBox modelBox;
    private final ShapeQuad[] adaptedQuads;
    private final BoundingBox bounds;

    public ModelBoxShapeAdapter(@NotNull ModelBox modelBox) {
        this.modelBox = modelBox;
        ModelBoxQuad[] boxQuads = modelBox.getQuads();
        this.adaptedQuads = new ShapeQuad[boxQuads.length];
        for (int i = 0; i < boxQuads.length; i++) {
            if (boxQuads[i] != null) {
                this.adaptedQuads[i] = new ModelBoxQuadAdapter(boxQuads[i]);
            }
        }
        this.bounds = new ModelBoxBoundsAdapter(modelBox);
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public ShapeQuad[] getQuads() {
        return this.adaptedQuads;
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public BoundingBox getBounds() {
        return this.bounds;
    }

    @Override // net.labymod.api.client.render.model.geometry.Shape
    @NotNull
    public ShapeType getType() {
        return ShapeType.CUBE;
    }

    @NotNull
    public ModelBox getModelBox() {
        return this.modelBox;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/model/adapter/ModelBoxShapeAdapter$ModelBoxBoundsAdapter.class */
    private static class ModelBoxBoundsAdapter implements BoundingBox {
        private final ModelBox box;

        ModelBoxBoundsAdapter(ModelBox box) {
            this.box = box;
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMinX() {
            return this.box.getMinX();
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMinY() {
            return this.box.getMinY();
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMinZ() {
            return this.box.getMinZ();
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMaxX() {
            return this.box.getMaxX();
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMaxY() {
            return this.box.getMaxY();
        }

        @Override // net.labymod.api.client.render.model.geometry.BoundingBox
        public float getMaxZ() {
            return this.box.getMaxZ();
        }
    }
}
