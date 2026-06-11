package net.minecraft.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransform;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemStackRenderState.class */
public class ItemStackRenderState {
    private int activeLayerCount;
    private boolean animated;
    private boolean oversizedInGui;
    private AABB cachedModelBoundingBox;
    ItemDisplayContext displayContext = ItemDisplayContext.NONE;
    private LayerRenderState[] layers = {new LayerRenderState()};

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemStackRenderState$FoilType.class */
    public enum FoilType {
        NONE,
        STANDARD,
        SPECIAL
    }

    public void ensureCapacity(int $$0) {
        int $$1 = this.layers.length;
        int $$2 = this.activeLayerCount + $$0;
        if ($$2 > $$1) {
            this.layers = (LayerRenderState[]) Arrays.copyOf(this.layers, $$2);
            for (int $$3 = $$1; $$3 < $$2; $$3++) {
                this.layers[$$3] = new LayerRenderState();
            }
        }
    }

    public LayerRenderState newLayer() {
        ensureCapacity(1);
        LayerRenderState[] layerRenderStateArr = this.layers;
        int i = this.activeLayerCount;
        this.activeLayerCount = i + 1;
        return layerRenderStateArr[i];
    }

    public void clear() {
        this.displayContext = ItemDisplayContext.NONE;
        for (int $$0 = 0; $$0 < this.activeLayerCount; $$0++) {
            this.layers[$$0].clear();
        }
        this.activeLayerCount = 0;
        this.animated = false;
        this.oversizedInGui = false;
        this.cachedModelBoundingBox = null;
    }

    public void setAnimated() {
        this.animated = true;
    }

    public boolean isAnimated() {
        return this.animated;
    }

    public void appendModelIdentityElement(Object $$0) {
    }

    private LayerRenderState firstLayer() {
        return this.layers[0];
    }

    public boolean isEmpty() {
        return this.activeLayerCount == 0;
    }

    public boolean usesBlockLight() {
        return firstLayer().usesBlockLight;
    }

    public TextureAtlasSprite pickParticleIcon(RandomSource $$0) {
        if (this.activeLayerCount == 0) {
            return null;
        }
        return this.layers[$$0.nextInt(this.activeLayerCount)].particleIcon;
    }

    public void visitExtents(Consumer<Vector3fc> $$0) {
        Vector3f $$1 = new Vector3f();
        PoseStack.Pose $$2 = new PoseStack.Pose();
        for (int $$3 = 0; $$3 < this.activeLayerCount; $$3++) {
            LayerRenderState $$4 = this.layers[$$3];
            $$4.transform.apply(this.displayContext.leftHand(), $$2);
            Matrix4f $$5 = $$2.pose();
            Vector3fc[] $$6 = $$4.extents.get();
            for (Vector3fc $$7 : $$6) {
                $$0.accept($$1.set($$7).mulPosition($$5));
            }
            $$2.setIdentity();
        }
    }

    public void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, int $$3, int $$4) {
        for (int $$5 = 0; $$5 < this.activeLayerCount; $$5++) {
            this.layers[$$5].submit($$0, $$1, $$2, $$3, $$4);
        }
    }

    public AABB getModelBoundingBox() {
        if (this.cachedModelBoundingBox != null) {
            return this.cachedModelBoundingBox;
        }
        AABB.Builder $$0 = new AABB.Builder();
        Objects.requireNonNull($$0);
        visitExtents($$0::include);
        AABB $$1 = $$0.build();
        this.cachedModelBoundingBox = $$1;
        return $$1;
    }

    public void setOversizedInGui(boolean $$0) {
        this.oversizedInGui = $$0;
    }

    public boolean isOversizedInGui() {
        return this.oversizedInGui;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ItemStackRenderState$LayerRenderState.class */
    public class LayerRenderState {
        private static final Vector3fc[] NO_EXTENTS = new Vector3fc[0];
        public static final Supplier<Vector3fc[]> NO_EXTENTS_SUPPLIER = () -> {
            return NO_EXTENTS;
        };
        boolean usesBlockLight;
        TextureAtlasSprite particleIcon;
        private RenderType renderType;
        private SpecialModelRenderer<Object> specialRenderer;
        private Object argumentForSpecialRendering;
        private final List<BakedQuad> quads = new ArrayList();
        ItemTransform transform = ItemTransform.NO_TRANSFORM;
        private FoilType foilType = FoilType.NONE;
        private int[] tintLayers = new int[0];
        Supplier<Vector3fc[]> extents = NO_EXTENTS_SUPPLIER;

        public LayerRenderState() {
        }

        public void clear() {
            this.quads.clear();
            this.renderType = null;
            this.foilType = FoilType.NONE;
            this.specialRenderer = null;
            this.argumentForSpecialRendering = null;
            Arrays.fill(this.tintLayers, -1);
            this.usesBlockLight = false;
            this.particleIcon = null;
            this.transform = ItemTransform.NO_TRANSFORM;
            this.extents = NO_EXTENTS_SUPPLIER;
        }

        public List<BakedQuad> prepareQuadList() {
            return this.quads;
        }

        public void setRenderType(RenderType $$0) {
            this.renderType = $$0;
        }

        public void setUsesBlockLight(boolean $$0) {
            this.usesBlockLight = $$0;
        }

        public void setExtents(Supplier<Vector3fc[]> $$0) {
            this.extents = $$0;
        }

        public void setParticleIcon(TextureAtlasSprite $$0) {
            this.particleIcon = $$0;
        }

        public void setTransform(ItemTransform $$0) {
            this.transform = $$0;
        }

        public <T> void setupSpecialModel(SpecialModelRenderer<T> $$0, T $$1) {
            this.specialRenderer = eraseSpecialRenderer($$0);
            this.argumentForSpecialRendering = $$1;
        }

        private static SpecialModelRenderer<Object> eraseSpecialRenderer(SpecialModelRenderer<?> $$0) {
            return $$0;
        }

        public void setFoilType(FoilType $$0) {
            this.foilType = $$0;
        }

        public int[] prepareTintLayers(int $$0) {
            if ($$0 > this.tintLayers.length) {
                this.tintLayers = new int[$$0];
                Arrays.fill(this.tintLayers, -1);
            }
            return this.tintLayers;
        }

        void submit(PoseStack $$0, SubmitNodeCollector $$1, int $$2, int $$3, int $$4) {
            $$0.pushPose();
            this.transform.apply(ItemStackRenderState.this.displayContext.leftHand(), $$0.last());
            if (this.specialRenderer != null) {
                this.specialRenderer.submit(this.argumentForSpecialRendering, ItemStackRenderState.this.displayContext, $$0, $$1, $$2, $$3, this.foilType != FoilType.NONE, $$4);
            } else if (this.renderType != null) {
                $$1.submitItem($$0, ItemStackRenderState.this.displayContext, $$2, $$3, $$4, this.tintLayers, this.quads, this.renderType, this.foilType);
            }
            $$0.popPose();
        }
    }
}
