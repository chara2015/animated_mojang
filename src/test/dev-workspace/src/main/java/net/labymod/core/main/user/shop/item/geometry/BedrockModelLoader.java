package net.labymod.core.main.user.shop.item.geometry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.laby.lib.bedrock.geometry.Bone;
import net.laby.lib.bedrock.geometry.Cube;
import net.laby.lib.bedrock.geometry.CubeUv;
import net.laby.lib.bedrock.geometry.FaceUv;
import net.laby.lib.bedrock.geometry.Geometry;
import net.laby.lib.bedrock.geometry.GeometryFile;
import net.laby.lib.bedrock.geometry.Vec3;
import net.labymod.api.Laby;
import net.labymod.api.client.render.model.EnhancedModelPart;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.ModelPart;
import net.labymod.api.client.render.model.ModelPartHolder;
import net.labymod.api.client.render.model.ModelService;
import net.labymod.api.client.render.model.geometry.ShapeQuad;
import net.labymod.api.client.render.model.geometry.ShapeVertex;
import net.labymod.api.client.render.model.geometry.shapes.CubeShape;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.math.Transformation;
import net.labymod.core.client.render.model.DefaultEnhancedModelPart;
import net.labymod.core.client.render.model.DefaultModel;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffectRegistry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/geometry/BedrockModelLoader.class */
public class BedrockModelLoader {
    private static final Logging LOGGER = Logging.getLogger();
    private final Model model = new DefaultModel();
    private final List<GeometryEffect> effects = new ArrayList();
    private int textureWidth;
    private int textureHeight;
    private boolean hasOutlineParts;

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private BedrockModelLoader(GeometryFile geometryFile, boolean generateBoneIds) throws MatchException {
        if (geometryFile.geometries() == null || geometryFile.geometries().isEmpty()) {
            return;
        }
        Geometry geometry = (Geometry) geometryFile.geometries().get(0);
        this.textureWidth = geometry.description().textureWidth();
        this.textureHeight = geometry.description().textureHeight();
        List<Bone> bones = geometry.bones();
        if (bones == null) {
            return;
        }
        Map<String, List<Bone>> childrenByParent = new HashMap<>();
        for (Bone bone : bones) {
            if (bone.parent() != null) {
                childrenByParent.computeIfAbsent(bone.parent(), k -> {
                    return new ArrayList();
                }).add(bone);
            }
        }
        for (Bone bone2 : bones) {
            if (bone2.parent() == null) {
                processBone(childrenByParent, bone2, this.model, null);
            }
        }
        if (generateBoneIds) {
            ModelService modelService = Laby.references().modelService();
            modelService.generateBoneIds(this.model);
        }
    }

    @NotNull
    public static BedrockModelLoader create(@NotNull GeometryFile geometryFile, boolean generateBoneIds) {
        return new BedrockModelLoader(geometryFile, generateBoneIds);
    }

    @NotNull
    public static BedrockModelLoader create(@NotNull GeometryFile geometryFile) {
        return new BedrockModelLoader(geometryFile, false);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void processBone(Map<String, List<Bone>> childrenByParent, Bone bone, ModelPartHolder parent, @Nullable Bone parentBone) throws MatchException {
        EnhancedModelPart part = new DefaultEnhancedModelPart();
        applyBoneTransform(part, bone, parentBone);
        parent.addChild(bone.name(), part);
        this.model.addPart(bone.name(), part);
        registerEffect(bone.name(), part);
        List<Cube> cubes = bone.cubes();
        if (cubes != null) {
            for (int i = 0; i < cubes.size(); i++) {
                addCube(part, cubes.get(i), bone, i);
            }
        }
        List<Bone> children = childrenByParent.get(bone.name());
        if (children != null) {
            for (Bone child : children) {
                processBone(childrenByParent, child, part, bone);
            }
        }
    }

    private void applyBoneTransform(ModelPart part, Bone bone, @Nullable Bone parentBone) {
        Transformation transformation = part.getModelPartTransform();
        Vec3 pivot = bone.pivot();
        float px = (float) (-pivot.x());
        float py = (float) pivot.y();
        float pz = (float) pivot.z();
        if (parentBone != null) {
            Vec3 parentPivot = parentBone.pivot();
            px -= (float) (-parentPivot.x());
            py -= (float) parentPivot.y();
            pz -= (float) parentPivot.z();
        }
        transformation.setTranslation(-px, -py, pz);
        Vec3 rotation = bone.rotation();
        if (rotation != null) {
            transformation.setRotation((float) Math.toRadians(rotation.x()), (float) Math.toRadians(rotation.y()), (float) Math.toRadians(rotation.z()));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0261  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void addCube(net.labymod.api.client.render.model.EnhancedModelPart r17, net.laby.lib.bedrock.geometry.Cube r18, net.laby.lib.bedrock.geometry.Bone r19, int r20) throws java.lang.MatchException {
        /*
            Method dump skipped, instruction units count: 765
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.labymod.core.main.user.shop.item.geometry.BedrockModelLoader.addCube(net.labymod.api.client.render.model.EnhancedModelPart, net.laby.lib.bedrock.geometry.Cube, net.laby.lib.bedrock.geometry.Bone, int):void");
    }

    private void applyCubeRotationTransform(ModelPart wrapper, Cube cube, Bone bone) {
        Transformation transformation = wrapper.getModelPartTransform();
        Vec3 bonePivot = bone.pivot();
        float bpx = (float) bonePivot.x();
        float bpy = (float) bonePivot.y();
        float bpz = (float) bonePivot.z();
        Vec3 cubePivot = cube.pivot();
        float cpx = cubePivot != null ? (float) cubePivot.x() : 0.0f;
        float cpy = cubePivot != null ? (float) cubePivot.y() : 0.0f;
        float cpz = cubePivot != null ? (float) cubePivot.z() : 0.0f;
        float relX = (-cpx) - (-bpx);
        float relY = cpy - bpy;
        float relZ = cpz - bpz;
        transformation.setTranslation(-relX, -relY, relZ);
        Vec3 rotation = cube.rotation();
        if (rotation != null) {
            transformation.setRotation((float) Math.toRadians(rotation.x()), (float) Math.toRadians(rotation.y()), (float) Math.toRadians(rotation.z()));
        }
    }

    private CubeShape buildBoxUVCubeWithOriginalSizes(float x, float y, float z, float sx, float sy, float sz, float inflate, boolean mirror, int texU, int texV, float origSx, float origSy, float origSz, boolean negY) {
        float minU;
        float maxU;
        float minV;
        float maxV;
        CubeShape cubeShape = CubeShape.builder().origin(x, y, z).size(sx, sy, sz).grow(inflate).textureOffset(0, 0).textureSize(this.textureWidth, this.textureHeight).mirror(mirror).build();
        float origSx2 = MathHelper.floor(origSx);
        float origSy2 = MathHelper.floor(origSy);
        float origSz2 = MathHelper.floor(origSz);
        float u0 = texU;
        float u1 = texU + origSz2;
        float u2 = texU + origSz2 + origSx2;
        float u4 = texU + origSz2 + origSx2 + origSz2;
        float u5 = texU + origSz2 + origSx2 + origSz2 + origSx2;
        float v0 = texV;
        float v1 = texV + origSz2;
        float v2 = texV + origSz2 + origSy2;
        float uScale = 1.0f / this.textureWidth;
        float vScale = 1.0f / this.textureHeight;
        for (ShapeQuad quad : cubeShape.getQuads()) {
            if (quad != null) {
                Direction direction = getQuadDirection(quad);
                if (direction != null) {
                    if (!negY) {
                        direction = swapUpDown(direction);
                    }
                    switch (direction) {
                        case DOWN:
                            minU = u1;
                            maxU = u2;
                            minV = v0;
                            maxV = v1;
                            ShapeVertex[] vertices = quad.getVertices();
                            vertices[0] = vertices[0].remap(maxU * uScale, minV * vScale);
                            vertices[1] = vertices[1].remap(minU * uScale, minV * vScale);
                            vertices[2] = vertices[2].remap(minU * uScale, maxV * vScale);
                            vertices[3] = vertices[3].remap(maxU * uScale, maxV * vScale);
                            break;
                        case UP:
                            minU = u2;
                            maxU = u2 + origSx2;
                            minV = v1;
                            maxV = v0;
                            ShapeVertex[] vertices2 = quad.getVertices();
                            vertices2[0] = vertices2[0].remap(maxU * uScale, minV * vScale);
                            vertices2[1] = vertices2[1].remap(minU * uScale, minV * vScale);
                            vertices2[2] = vertices2[2].remap(minU * uScale, maxV * vScale);
                            vertices2[3] = vertices2[3].remap(maxU * uScale, maxV * vScale);
                            break;
                        case WEST:
                            minU = u0;
                            maxU = u1;
                            minV = v1;
                            maxV = v2;
                            ShapeVertex[] vertices22 = quad.getVertices();
                            vertices22[0] = vertices22[0].remap(maxU * uScale, minV * vScale);
                            vertices22[1] = vertices22[1].remap(minU * uScale, minV * vScale);
                            vertices22[2] = vertices22[2].remap(minU * uScale, maxV * vScale);
                            vertices22[3] = vertices22[3].remap(maxU * uScale, maxV * vScale);
                            break;
                        case NORTH:
                            minU = u1;
                            maxU = u2;
                            minV = v1;
                            maxV = v2;
                            ShapeVertex[] vertices222 = quad.getVertices();
                            vertices222[0] = vertices222[0].remap(maxU * uScale, minV * vScale);
                            vertices222[1] = vertices222[1].remap(minU * uScale, minV * vScale);
                            vertices222[2] = vertices222[2].remap(minU * uScale, maxV * vScale);
                            vertices222[3] = vertices222[3].remap(maxU * uScale, maxV * vScale);
                            break;
                        case EAST:
                            minU = u2;
                            maxU = u4;
                            minV = v1;
                            maxV = v2;
                            ShapeVertex[] vertices2222 = quad.getVertices();
                            vertices2222[0] = vertices2222[0].remap(maxU * uScale, minV * vScale);
                            vertices2222[1] = vertices2222[1].remap(minU * uScale, minV * vScale);
                            vertices2222[2] = vertices2222[2].remap(minU * uScale, maxV * vScale);
                            vertices2222[3] = vertices2222[3].remap(maxU * uScale, maxV * vScale);
                            break;
                        case SOUTH:
                            minU = u4;
                            maxU = u5;
                            minV = v1;
                            maxV = v2;
                            ShapeVertex[] vertices22222 = quad.getVertices();
                            vertices22222[0] = vertices22222[0].remap(maxU * uScale, minV * vScale);
                            vertices22222[1] = vertices22222[1].remap(minU * uScale, minV * vScale);
                            vertices22222[2] = vertices22222[2].remap(minU * uScale, maxV * vScale);
                            vertices22222[3] = vertices22222[3].remap(maxU * uScale, maxV * vScale);
                            break;
                    }
                }
            }
        }
        return cubeShape;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private CubeShape buildPerFaceUVCube(float x, float y, float z, float sx, float sy, float sz, float inflate, boolean mirror, CubeUv.PerFaceUv perFaceUv, boolean negY) throws MatchException {
        CubeShape cubeShape = CubeShape.builder().origin(x, y, z).size(sx, sy, sz).grow(inflate).textureOffset(0, 0).textureSize(this.textureWidth, this.textureHeight).mirror(mirror).build();
        float uScale = 1.0f / this.textureWidth;
        float vScale = 1.0f / this.textureHeight;
        for (ShapeQuad quad : cubeShape.getQuads()) {
            if (quad != null) {
                Direction direction = getQuadDirection(quad);
                if (direction != null) {
                    if (!negY) {
                        direction = swapUpDown(direction);
                    }
                    FaceUv face = getFaceUv(perFaceUv, direction);
                    if (face != null) {
                        ShapeVertex[] vertices = quad.getVertices();
                        float uOffset = ((float) face.uv().u()) * uScale;
                        float vOffset = ((float) face.uv().v()) * vScale;
                        float uSize = ((float) face.uvSize().u()) * uScale;
                        float vSize = ((float) face.uvSize().v()) * vScale;
                        vertices[3] = vertices[3].remap(uOffset, vOffset);
                        vertices[0] = vertices[0].remap(uOffset, vOffset + vSize);
                        vertices[1] = vertices[1].remap(uOffset + uSize, vOffset + vSize);
                        vertices[2] = vertices[2].remap(uOffset + uSize, vOffset);
                    }
                }
            }
        }
        return cubeShape;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Nullable
    private FaceUv getFaceUv(CubeUv.PerFaceUv perFaceUv, Direction direction) throws MatchException {
        switch (direction) {
            case DOWN:
                return perFaceUv.down();
            case UP:
                return perFaceUv.up();
            case WEST:
                return perFaceUv.west();
            case NORTH:
                return perFaceUv.north();
            case EAST:
                return perFaceUv.east();
            case SOUTH:
                return perFaceUv.south();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private void flipQuads(CubeShape cubeShape) {
        for (ShapeQuad quad : cubeShape.getQuads()) {
            if (quad != null) {
                ShapeVertex[] vertices = quad.getVertices();
                ShapeVertex temp = vertices[0];
                vertices[0] = vertices[3];
                vertices[3] = temp;
                ShapeVertex temp2 = vertices[1];
                vertices[1] = vertices[2];
                vertices[2] = temp2;
                quad.getNormal().negate();
            }
        }
    }

    private void registerEffect(String name, ModelPart part) {
        try {
            GeometryEffectRegistry effectService = ((DefaultGameUserService) Laby.references().gameUserService()).cosmeticIndexService().effectService();
            GeometryEffect geometryEffect = effectService.create(name, part);
            if (geometryEffect != null) {
                this.effects.add(geometryEffect);
            }
        } catch (Exception exception) {
            LOGGER.error("Failed to create geometry effect for part '{}'", name, exception);
        }
    }

    private Direction swapUpDown(Direction direction) {
        switch (direction) {
            case DOWN:
                return Direction.UP;
            case UP:
                return Direction.DOWN;
            default:
                return direction;
        }
    }

    private Direction getQuadDirection(ShapeQuad quad) {
        float nx = quad.getNormal().x();
        float ny = quad.getNormal().y();
        float nz = quad.getNormal().z();
        float absX = Math.abs(nx);
        float absY = Math.abs(ny);
        float absZ = Math.abs(nz);
        return (absY < absX || absY < absZ) ? (absX < absY || absX < absZ) ? nz > 0.0f ? Direction.NORTH : Direction.SOUTH : nx > 0.0f ? Direction.WEST : Direction.EAST : ny > 0.0f ? Direction.DOWN : Direction.UP;
    }

    @NotNull
    public Model getModel() {
        return this.model;
    }

    @NotNull
    public List<GeometryEffect> getEffects() {
        return this.effects;
    }

    public boolean hasOutlineParts() {
        return this.hasOutlineParts;
    }
}
