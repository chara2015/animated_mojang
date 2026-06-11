package net.labymod.core.main.user.shop.item.geometry.effect.effects.layer;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.box.ModelBox;
import net.labymod.api.client.render.model.box.ModelBoxQuad;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.render.model.geometry.Shape;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.Direction;
import net.labymod.core.main.user.shop.item.geometry.DepthMap;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import org.joml.Vector3f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/effect/effects/layer/ExtrudeGeometryEffect.class */
public class ExtrudeGeometryEffect extends GeometryEffect {
    private static final float DELTA = 0.5f;
    private static final float DEPTH = 0.01f;
    private static final Set<Direction> NO_NORTH_SOUTH = EnumSet.of(Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST);
    private int extraGeometry;
    private Rectangle extrudedRectangle;
    private DepthMap depthMap;

    public ExtrudeGeometryEffect(String effectArgument, ModelPart modelPart) {
        super(effectArgument, modelPart, GeometryEffect.Type.BUFFER_CREATION, 0);
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    protected boolean processParameters() {
        return true;
    }

    @Override // net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect
    public void apply(Player player, PlayerModel playerModel, ItemMetadata itemMetadata, ItemEffect.EffectData effectData) {
        if (this.extrudedRectangle == null) {
            ModelPart modelPart = this.modelPart;
            if (modelPart instanceof EnhancedModelPart) {
                EnhancedModelPart enhancedPart = (EnhancedModelPart) modelPart;
                if (enhancedPart.getShapes().isEmpty()) {
                    return;
                } else {
                    transformCubeEnhanced(enhancedPart);
                }
            } else if (this.modelPart.getBoxes().isEmpty()) {
                return;
            } else {
                transformCubeLegacy();
            }
            this.appearanceStore.updateExtrude(this.modelPart);
        }
        if (!Objects.equals(this.depthMap, itemMetadata.getDepthMap())) {
            this.depthMap = itemMetadata.getDepthMap();
            applyDepthMap();
            this.appearanceStore.updateExtrude(this.modelPart);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void transformCubeEnhanced(net.labymod.api.client.render.model.EnhancedModelPart r15) {
        /*
            Method dump skipped, instruction units count: 590
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.main.user.shop.item.geometry.effect.effects.layer.ExtrudeGeometryEffect.transformCubeEnhanced(net.labymod.api.client.render.model.EnhancedModelPart):void");
    }

    private CubeShape buildBoundaryCube(float x, float y, float z, float width, float height, float depth, boolean mirror, int texOffsetX, int texOffsetY, int texWidth, int texHeight) {
        return CubeShape.builder().textureOffset(texOffsetX, texOffsetY).textureSize(texWidth, texHeight).origin(x, y, z).size(width, height, depth).grow(0.0f).mirror(mirror).removeVisibleFace(Direction.SOUTH).build();
    }

    private void transformCubeLegacy() {
        ModelBox modelBox = (ModelBox) this.modelPart.getBoxes().removeFirst();
        float width = modelBox.getWidth();
        float height = modelBox.getHeight();
        float depth = modelBox.getDepth();
        float minX = modelBox.getMinX();
        float minY = modelBox.getMinY();
        float minZ = modelBox.getMinZ();
        boolean mirror = modelBox.isMirror();
        this.extrudedRectangle = Rectangle.relative(this.modelPart.getTextureOffsetX(), this.modelPart.getTextureOffsetY(), width, height);
        if (depth == 0.0f) {
            hideSouthSide(this.modelPart.createAndAddBox(minX, minY, minZ - 0.5f, width, height, 0.0f, 0.0f, mirror));
            hideSouthSide(this.modelPart.createAndAddBox(minX, minY, minZ + 0.5f, width, height, 0.0f, 0.0f, mirror));
            this.extraGeometry += 2;
        }
        if (width == 0.0f) {
            hideSouthSide(this.modelPart.createAndAddBox(minX - 0.5f, minY, minZ, 0.0f, height, depth, 0.0f, mirror));
            hideSouthSide(this.modelPart.createAndAddBox(minX + 0.5f, minY, minZ, 0.0f, height, depth, 0.0f, mirror));
            this.extraGeometry += 2;
        }
        if (height == 0.0f) {
            hideSouthSide(this.modelPart.createAndAddBox(minX, minY - 0.5f, minZ, width, 0.0f, depth, 0.0f, mirror));
            hideSouthSide(this.modelPart.createAndAddBox(minX, minY + 0.5f, minZ, width, 0.0f, depth, 0.0f, mirror));
            this.extraGeometry += 2;
        }
        for (int pixelY = 0; pixelY < this.extrudedRectangle.getHeight(); pixelY++) {
            for (int pixelX = 0; pixelX < this.extrudedRectangle.getWidth(); pixelX++) {
                this.modelPart.setTextureOffset(((int) this.extrudedRectangle.getX()) + pixelX, ((int) this.extrudedRectangle.getY()) + pixelY);
                this.modelPart.addBox(minX + pixelX + 0.5f, minY + pixelY + 0.5f, minZ, DEPTH, DEPTH, DEPTH, 0.5f, mirror);
            }
        }
    }

    private void applyDepthMap() {
        ModelPart modelPart = this.modelPart;
        if (modelPart instanceof EnhancedModelPart) {
            EnhancedModelPart enhancedPart = (EnhancedModelPart) modelPart;
            applyDepthMapEnhanced(enhancedPart);
        } else {
            applyDepthMapLegacy();
        }
    }

    private void applyDepthMapEnhanced(EnhancedModelPart enhancedPart) {
        List<Shape> shapes = enhancedPart.getShapes();
        int size = shapes.size();
        int shapeIndex = 0;
        float width = this.extrudedRectangle.getWidth();
        for (int index = this.extraGeometry; index < size; index++) {
            Shape shape = shapes.get(index);
            for (ShapeQuad quad : shape.getQuads()) {
                if (quad != null) {
                    int faceIndex = normalToFaceIndex(quad.getNormal());
                    if (faceIndex < 0) {
                        quad.setVisible(false);
                    } else {
                        int x = (int) ((shapeIndex % width) + this.extrudedRectangle.getX());
                        int y = (int) ((shapeIndex / width) + this.extrudedRectangle.getY());
                        quad.setVisible(this.depthMap == null || this.depthMap.shouldRenderFace(this.extrudedRectangle, x, y, faceIndex));
                    }
                }
            }
            shapeIndex++;
        }
    }

    private static int normalToFaceIndex(Vector3f normal) {
        if (normal.x > 0.5f) {
            return 0;
        }
        if (normal.x < -0.5f) {
            return 1;
        }
        if (normal.y > 0.5f) {
            return 2;
        }
        if (normal.y < -0.5f) {
            return 3;
        }
        return -1;
    }

    private void applyDepthMapLegacy() {
        int size = this.modelPart.getBoxes().size();
        int boxIndex = 0;
        float width = this.extrudedRectangle.getWidth();
        for (int index = this.extraGeometry; index < size; index++) {
            ModelBox modelBox = this.modelPart.getBoxes().get(index);
            for (int quadIndex = 0; quadIndex < modelBox.getQuads().length; quadIndex++) {
                ModelBoxQuad quad = modelBox.getQuads()[quadIndex];
                Direction direction = quad.getDirection();
                if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    quad.setVisible(false);
                } else {
                    int x = (int) ((boxIndex % width) + this.extrudedRectangle.getX());
                    int y = (int) ((boxIndex / width) + this.extrudedRectangle.getY());
                    quad.setVisible(this.depthMap == null || this.depthMap.shouldRenderFace(this.extrudedRectangle, x, y, quadIndex));
                }
            }
            boxIndex++;
        }
    }

    private void hideSouthSide(ModelBox box) {
        ModelBoxQuad[] quads = box.getQuads();
        for (ModelBoxQuad quad : quads) {
            if (quad.getDirection() == Direction.SOUTH) {
                quad.setVisible(false);
                return;
            }
        }
    }
}
