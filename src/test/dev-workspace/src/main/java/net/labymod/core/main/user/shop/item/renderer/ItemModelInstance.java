package net.labymod.core.main.user.shop.item.renderer;

import java.util.List;
import java.util.Objects;
import net.labymod.api.Laby;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.BoneGroup;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.math.JomlMath;
import net.labymod.api.laby3d.model.ModelInstance;
import net.labymod.api.laby3d.pipeline.ItemRenderStates;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlock;
import net.labymod.api.laby3d.shaders.block.CosmeticsUniformBlockData;
import net.labymod.api.laby3d.shaders.block.DynamicTransformsUniformBlockData;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.core.main.user.shop.cosmetic.appearance.PartAppearanceStore;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticRenderState;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;
import net.labymod.core.main.user.shop.item.geometry.effect.ItemEffect;
import net.labymod.core.main.user.shop.item.renderer.ItemModelCompiler;
import net.labymod.core.main.user.shop.item.state.ItemState;
import net.labymod.laby3d.api.RenderDevice;
import net.labymod.laby3d.api.buffers.ByteBufferBuilder;
import net.labymod.laby3d.api.mesh.GeometryData;
import net.labymod.laby3d.api.mesh.Mesh;
import net.labymod.laby3d.api.pipeline.RenderState;
import net.labymod.laby3d.api.pipeline.pass.CommandBuffer;
import net.labymod.laby3d.api.textures.DeviceTextureView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/renderer/ItemModelInstance.class */
public class ItemModelInstance extends ModelInstance<ItemState> {
    private static final boolean USE_VIEW_MATRIX = MinecraftVersions.V1_20_6.orNewer();
    private static final int DEFAULT_BUFFER_SIZE = 16384;
    private static final boolean OLD_UI_RENDERING;
    private final RenderEnvironmentContext renderEnvironmentContext;
    private final ItemModel itemModel;

    @Nullable
    private final CosmeticRenderState cosmeticRenderState;
    private final Laby3D laby3D;
    private final ItemModelCompiler compiler;

    static {
        OLD_UI_RENDERING = MinecraftVersions.V1_16_5.orNewer() && MinecraftVersions.V1_21_5.orOlder();
    }

    public ItemModelInstance(@NotNull ItemModel itemModel) {
        this(itemModel, null);
    }

    public ItemModelInstance(@NotNull ItemModel itemModel, @Nullable CosmeticRenderState cosmeticRenderState) {
        super(itemModel.getModel());
        this.renderEnvironmentContext = Laby.references().renderEnvironmentContext();
        this.itemModel = itemModel;
        this.cosmeticRenderState = cosmeticRenderState;
        this.compiler = new ItemModelCompiler(ItemModelCompiler.CompilerMode.RETAINED);
        this.laby3D = Laby.references().laby3D();
    }

    @Override // net.labymod.api.laby3d.model.ModelInstance
    public void update(@NotNull ItemState itemState) {
        super.update(itemState);
        resetPose();
        applyEffects(itemState);
        AnimationController animationController = itemState.animationController;
        if (animationController != null) {
            animationController.applyAnimation(model(), itemState.animationRenderState, new String[0]);
        }
    }

    private void applyEffects(@NotNull ItemState itemState) {
        if (itemState.player == null || itemState.playerModel == null || itemState.metadata == null) {
            return;
        }
        if (this.cosmeticRenderState != null) {
            this.itemModel.bindAppearanceStore(this.cosmeticRenderState.appearanceStore());
        }
        ItemEffect.EffectData effectData = new ItemEffect.EffectData().setForward(itemState.physicForward).setStrafe(itemState.physicStrafe).setGravity(itemState.physicGravity).setRenderYawOffset(itemState.physicRenderYawOffset).setPitch(itemState.physicPitch).setSlim(itemState.player.isSlim()).setRightSide(itemState.rightSide);
        this.itemModel.apply(itemState.player, itemState.playerModel, effectData, itemState.metadata, GeometryEffect.Type.BUFFER_CREATION);
        this.itemModel.apply(itemState.player, itemState.playerModel, effectData, itemState.metadata, GeometryEffect.Type.PHYSIC);
    }

    @NotNull
    public ItemModel getItemModel() {
        return this.itemModel;
    }

    @NotNull
    public PartAppearanceStore appearanceStore() {
        if (this.cosmeticRenderState != null) {
            return this.cosmeticRenderState.appearanceStore();
        }
        return this.itemModel.appearanceStore();
    }

    public boolean isRetained() {
        return Laby.labyAPI().config().ingame().cosmetics().renderMode().get() == RenderMode.RETAINED;
    }

    public void renderRetained(@NotNull CommandBuffer cmd, @NotNull RenderState renderState, @NotNull Matrix4f pose, @NotNull TextureBindingSet textureBindingSet, int lightCoords) {
        PartAppearanceStore partAppearanceStoreAppearanceStore;
        Stack stack;
        List<CosmeticsUniformBlockData> listGroupCosmeticsData;
        List<Mesh> listCompiledMeshes;
        long lastGlobalVersion;
        List<Mesh> listOutlineMeshes;
        long outlineLastGlobalVersion;
        RenderState renderState2;
        Model model = this.itemModel.getModel();
        if (model == null) {
            return;
        }
        List<BoneGroup> groups = model.getBoneGroups();
        if (groups.isEmpty()) {
            return;
        }
        short u = (short) (lightCoords & 65535);
        short v = (short) ((lightCoords >> 16) & 65535);
        boolean useRenderState = this.cosmeticRenderState != null;
        if (useRenderState) {
            partAppearanceStoreAppearanceStore = this.cosmeticRenderState.appearanceStore();
        } else {
            partAppearanceStoreAppearanceStore = this.itemModel.appearanceStore;
        }
        PartAppearanceStore appearanceStore = partAppearanceStoreAppearanceStore;
        if (useRenderState) {
            stack = this.cosmeticRenderState.stack();
        } else {
            stack = this.itemModel.stack;
        }
        Stack renderStack = stack;
        if (useRenderState) {
            listGroupCosmeticsData = this.cosmeticRenderState.groupCosmeticsData();
        } else {
            listGroupCosmeticsData = this.itemModel.groupCosmeticsData;
        }
        List<CosmeticsUniformBlockData> groupCosmeticsData = listGroupCosmeticsData;
        while (groupCosmeticsData.size() < groups.size()) {
            groupCosmeticsData.add(new CosmeticsUniformBlockData());
        }
        if (useRenderState) {
            listCompiledMeshes = this.cosmeticRenderState.compiledMeshes();
        } else {
            listCompiledMeshes = this.itemModel.compiledMeshes;
        }
        List<Mesh> compiledMeshes = listCompiledMeshes;
        long globalVersion = appearanceStore.globalVersion();
        if (useRenderState) {
            lastGlobalVersion = this.cosmeticRenderState.lastGlobalVersion();
        } else {
            lastGlobalVersion = this.itemModel.getLastGlobalVersion();
        }
        long lastVersion = lastGlobalVersion;
        if (globalVersion != lastVersion || needsRecompile(compiledMeshes, groups.size())) {
            if (useRenderState) {
                this.cosmeticRenderState.setLastGlobalVersion(globalVersion);
            } else {
                this.itemModel.setLastGlobalVersion(globalVersion);
            }
            Boolean filter = this.itemModel.hasOutlineParts() ? false : null;
            recompileGroups(compiledMeshes, groups, renderState, lightCoords, filter);
        }
        if (compiledMeshes.isEmpty()) {
            return;
        }
        cmd.bindPipeline(renderState);
        for (int index = 0; index < textureBindingSet.textures().length; index++) {
            DeviceTextureView textureView = textureBindingSet.textures()[index];
            if (textureView != null) {
                cmd.bindTexture(index, textureView);
            }
        }
        this.laby3D.setupOverlayAndLightingTextures(cmd);
        this.laby3D.setupDefaultUniforms(cmd);
        DynamicTransformsUniformBlockData data = new DynamicTransformsUniformBlockData();
        Matrix4f modelViewMatrix = pose;
        cmd.bindUniformBlock(CosmeticsUniformBlock.NAME, this.laby3D.cosmetics());
        boolean isInFirstPersonView = this.itemModel.isFirstPerson() || !this.renderEnvironmentContext.isScreenContext();
        if (USE_VIEW_MATRIX && isInFirstPersonView) {
            Matrix4f viewMatrix = JomlMath.extractMatrix(Laby.references().gameTransformations().viewMatrix());
            viewMatrix.mul(modelViewMatrix);
            modelViewMatrix = viewMatrix;
        }
        if (OLD_UI_RENDERING && this.renderEnvironmentContext.isScreenContext()) {
            Matrix4f m = JomlMath.extractMatrix(Laby.references().gameTransformations().modelViewMatrix());
            m.mul(modelViewMatrix);
            modelViewMatrix = m;
        }
        data.modelViewMatrix().set(modelViewMatrix);
        cmd.bindUniformBlock("DynamicTransforms", this.laby3D.dynamicTransforms());
        cmd.bindUniformBlockData("DynamicTransforms", data);
        drawGroups(cmd, renderStack, groupCosmeticsData, pose, u, v, groups, compiledMeshes);
        if (this.itemModel.hasOutlineParts()) {
            if (useRenderState) {
                listOutlineMeshes = this.cosmeticRenderState.outlineMeshes();
            } else {
                listOutlineMeshes = this.itemModel.outlineMeshes;
            }
            List<Mesh> outlineMeshes = listOutlineMeshes;
            long outlineVersion = appearanceStore.globalVersion();
            if (useRenderState) {
                outlineLastGlobalVersion = this.cosmeticRenderState.outlineLastGlobalVersion();
            } else {
                outlineLastGlobalVersion = this.itemModel.getOutlineLastGlobalVersion();
            }
            long outlineLastVersion = outlineLastGlobalVersion;
            if (outlineVersion != outlineLastVersion || needsRecompile(outlineMeshes, groups.size())) {
                if (useRenderState) {
                    this.cosmeticRenderState.setOutlineLastGlobalVersion(outlineVersion);
                } else {
                    this.itemModel.setOutlineLastGlobalVersion(outlineVersion);
                }
                recompileGroups(outlineMeshes, groups, renderState, lightCoords, true);
            }
            if (!outlineMeshes.isEmpty()) {
                if (renderState == ItemRenderStates.RETAINED_TRANSLUCENT_COSMETICS) {
                    renderState2 = ItemRenderStates.RETAINED_OUTLINE_COSMETICS;
                } else {
                    renderState2 = ItemRenderStates.OUTLINE_COSMETICS;
                }
                RenderState outlineState = renderState2;
                cmd.bindPipeline(outlineState);
                drawGroups(cmd, renderStack, groupCosmeticsData, pose, u, v, groups, outlineMeshes);
            }
        }
    }

    private void drawGroups(CommandBuffer cmd, Stack stack, List<CosmeticsUniformBlockData> groupCosmeticsData, Matrix4f modelMatrix, short u, short v, List<BoneGroup> groups, List<Mesh> meshes) {
        int count = Math.min(groups.size(), meshes.size());
        for (int i = 0; i < count; i++) {
            Mesh mesh = meshes.get(i);
            if (mesh != null) {
                CosmeticsUniformBlockData groupData = groupCosmeticsData.get(i);
                groupData.modelMatrix().set(modelMatrix);
                groupData.lightCoords().set(u, v);
                this.compiler.transformBones(stack, groupData, groups.get(i));
                cmd.bindUniformBlockData(CosmeticsUniformBlock.NAME, groupData);
                cmd.draw(mesh);
            }
        }
    }

    private boolean needsRecompile(List<Mesh> meshes, int expectedCount) {
        if (meshes.size() != expectedCount) {
            return true;
        }
        for (Mesh mesh : meshes) {
            if (mesh != null && mesh.bufferResource().indexBuffer().isClosed()) {
                return true;
            }
        }
        return false;
    }

    private void recompileGroups(List<Mesh> target, List<BoneGroup> groups, RenderState renderState, int lightCoords, @Nullable Boolean invertedFilter) {
        for (Mesh mesh : target) {
            if (mesh != null) {
                mesh.close();
            }
        }
        target.clear();
        for (BoneGroup group : groups) {
            target.add(compileMesh(renderState, group, lightCoords, invertedFilter));
        }
    }

    @Nullable
    private Mesh compileMesh(RenderState renderState, BoneGroup group, int lightCoords, @Nullable Boolean invertedFilter) {
        Stack stack;
        PartAppearanceStore partAppearanceStoreAppearanceStore;
        Mesh meshCreate;
        this.compiler.setInvertedFilter(invertedFilter);
        try {
            ByteBufferBuilder buffer = new ByteBufferBuilder(16384);
            try {
                if (this.cosmeticRenderState != null) {
                    stack = this.cosmeticRenderState.stack();
                } else {
                    stack = this.itemModel.stack;
                }
                Stack compileStack = stack;
                if (this.cosmeticRenderState != null) {
                    partAppearanceStoreAppearanceStore = this.cosmeticRenderState.appearanceStore();
                } else {
                    partAppearanceStoreAppearanceStore = this.itemModel.appearanceStore;
                }
                PartAppearanceStore compileAppearance = partAppearanceStoreAppearanceStore;
                GeometryData geometryData = this.compiler.compile(buffer, compileStack, group, compileAppearance, renderState, lightCoords);
                if (geometryData != null) {
                    RenderDevice renderDevice = this.laby3D.renderDevice();
                    ItemModel itemModel = this.itemModel;
                    Objects.requireNonNull(itemModel);
                    meshCreate = Mesh.create(renderDevice, itemModel::getName, geometryData);
                } else {
                    meshCreate = null;
                }
                Mesh mesh = meshCreate;
                buffer.close();
                this.compiler.setInvertedFilter(null);
                return mesh;
            } finally {
            }
        } catch (Throwable th) {
            this.compiler.setInvertedFilter(null);
            throw th;
        }
    }
}
