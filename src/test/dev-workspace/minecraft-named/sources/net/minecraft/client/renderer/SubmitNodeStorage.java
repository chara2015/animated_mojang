package net.minecraft.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.Int2ObjectAVLTreeMap;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage.class */
public class SubmitNodeStorage implements SubmitNodeCollector {
    private final Int2ObjectAVLTreeMap<SubmitNodeCollection> submitsPerOrder = new Int2ObjectAVLTreeMap<>();

    @Override // net.minecraft.client.renderer.SubmitNodeCollector
    public SubmitNodeCollection order(int $$0) {
        return (SubmitNodeCollection) this.submitsPerOrder.computeIfAbsent($$0, $$02 -> {
            return new SubmitNodeCollection(this);
        });
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitShadow(PoseStack $$0, float $$1, List<EntityRenderState.ShadowPiece> $$2) {
        order(0).submitShadow($$0, $$1, $$2);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitNameTag(PoseStack $$0, Vec3 $$1, int $$2, Component $$3, boolean $$4, int $$5, double $$6, CameraRenderState $$7) {
        order(0).submitNameTag($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitText(PoseStack $$0, float $$1, float $$2, FormattedCharSequence $$3, boolean $$4, Font.DisplayMode $$5, int $$6, int $$7, int $$8, int $$9) {
        order(0).submitText($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitFlame(PoseStack $$0, EntityRenderState $$1, Quaternionf $$2) {
        order(0).submitFlame($$0, $$1, $$2);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitLeash(PoseStack $$0, EntityRenderState.LeashState $$1) {
        order(0).submitLeash($$0, $$1);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public <S> void submitModel(Model<? super S> $$0, S $$1, PoseStack $$2, RenderType $$3, int $$4, int $$5, int $$6, TextureAtlasSprite $$7, int $$8, ModelFeatureRenderer.CrumblingOverlay $$9) {
        order(0).submitModel($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitModelPart(ModelPart $$0, PoseStack $$1, RenderType $$2, int $$3, int $$4, TextureAtlasSprite $$5, boolean $$6, boolean $$7, int $$8, ModelFeatureRenderer.CrumblingOverlay $$9, int $$10) {
        order(0).submitModelPart($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitBlock(PoseStack $$0, BlockState $$1, int $$2, int $$3, int $$4) {
        order(0).submitBlock($$0, $$1, $$2, $$3, $$4);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitMovingBlock(PoseStack $$0, MovingBlockRenderState $$1) {
        order(0).submitMovingBlock($$0, $$1);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitBlockModel(PoseStack $$0, RenderType $$1, BlockStateModel $$2, float $$3, float $$4, float $$5, int $$6, int $$7, int $$8) {
        order(0).submitBlockModel($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitItem(PoseStack $$0, ItemDisplayContext $$1, int $$2, int $$3, int $$4, int[] $$5, List<BakedQuad> $$6, RenderType $$7, ItemStackRenderState.FoilType $$8) {
        order(0).submitItem($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitCustomGeometry(PoseStack $$0, RenderType $$1, SubmitNodeCollector.CustomGeometryRenderer $$2) {
        order(0).submitCustomGeometry($$0, $$1, $$2);
    }

    @Override // net.minecraft.client.renderer.OrderedSubmitNodeCollector
    public void submitParticleGroup(SubmitNodeCollector.ParticleGroupRenderer $$0) {
        order(0).submitParticleGroup($$0);
    }

    public void clear() {
        this.submitsPerOrder.values().forEach((v0) -> {
            v0.clear();
        });
    }

    public void endFrame() {
        this.submitsPerOrder.values().removeIf($$0 -> {
            return !$$0.wasUsed();
        });
        this.submitsPerOrder.values().forEach((v0) -> {
            v0.endFrame();
        });
    }

    public Int2ObjectAVLTreeMap<SubmitNodeCollection> getSubmitsPerOrder() {
        return this.submitsPerOrder;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit.class */
    public static final class ShadowSubmit extends Record {
        private final Matrix4f pose;
        private final float radius;
        private final List<EntityRenderState.ShadowPiece> pieces;

        public ShadowSubmit(Matrix4f $$0, float $$1, List<EntityRenderState.ShadowPiece> $$2) {
            this.pose = $$0;
            this.radius = $$1;
            this.pieces = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ShadowSubmit.class), ShadowSubmit.class, "pose;radius;pieces", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->radius:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShadowSubmit.class), ShadowSubmit.class, "pose;radius;pieces", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->radius:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShadowSubmit.class, Object.class), ShadowSubmit.class, "pose;radius;pieces", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->radius:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ShadowSubmit;->pieces:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public float radius() {
            return this.radius;
        }

        public List<EntityRenderState.ShadowPiece> pieces() {
            return this.pieces;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit.class */
    public static final class FlameSubmit extends Record {
        private final PoseStack.Pose pose;
        private final EntityRenderState entityRenderState;
        private final Quaternionf rotation;

        public FlameSubmit(PoseStack.Pose $$0, EntityRenderState $$1, Quaternionf $$2) {
            this.pose = $$0;
            this.entityRenderState = $$1;
            this.rotation = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, FlameSubmit.class), FlameSubmit.class, "pose;entityRenderState;rotation", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->entityRenderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->rotation:Lorg/joml/Quaternionf;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, FlameSubmit.class), FlameSubmit.class, "pose;entityRenderState;rotation", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->entityRenderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->rotation:Lorg/joml/Quaternionf;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, FlameSubmit.class, Object.class), FlameSubmit.class, "pose;entityRenderState;rotation", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->entityRenderState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$FlameSubmit;->rotation:Lorg/joml/Quaternionf;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public EntityRenderState entityRenderState() {
            return this.entityRenderState;
        }

        public Quaternionf rotation() {
            return this.rotation;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit.class */
    public static final class NameTagSubmit extends Record {
        private final Matrix4f pose;
        private final float x;
        private final float y;
        private final Component text;
        private final int lightCoords;
        private final int color;
        private final int backgroundColor;
        private final double distanceToCameraSq;

        public NameTagSubmit(Matrix4f $$0, float $$1, float $$2, Component $$3, int $$4, int $$5, int $$6, double $$7) {
            this.pose = $$0;
            this.x = $$1;
            this.y = $$2;
            this.text = $$3;
            this.lightCoords = $$4;
            this.color = $$5;
            this.backgroundColor = $$6;
            this.distanceToCameraSq = $$7;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NameTagSubmit.class), NameTagSubmit.class, "pose;x;y;text;lightCoords;color;backgroundColor;distanceToCameraSq", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->distanceToCameraSq:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NameTagSubmit.class), NameTagSubmit.class, "pose;x;y;text;lightCoords;color;backgroundColor;distanceToCameraSq", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->distanceToCameraSq:D").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NameTagSubmit.class, Object.class), NameTagSubmit.class, "pose;x;y;text;lightCoords;color;backgroundColor;distanceToCameraSq", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$NameTagSubmit;->distanceToCameraSq:D").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public Component text() {
            return this.text;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int color() {
            return this.color;
        }

        public int backgroundColor() {
            return this.backgroundColor;
        }

        public double distanceToCameraSq() {
            return this.distanceToCameraSq;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$TextSubmit.class */
    public static final class TextSubmit extends Record {
        private final Matrix4f pose;
        private final float x;
        private final float y;
        private final FormattedCharSequence string;
        private final boolean dropShadow;
        private final Font.DisplayMode displayMode;
        private final int lightCoords;
        private final int color;
        private final int backgroundColor;
        private final int outlineColor;

        public TextSubmit(Matrix4f $$0, float $$1, float $$2, FormattedCharSequence $$3, boolean $$4, Font.DisplayMode $$5, int $$6, int $$7, int $$8, int $$9) {
            this.pose = $$0;
            this.x = $$1;
            this.y = $$2;
            this.string = $$3;
            this.dropShadow = $$4;
            this.displayMode = $$5;
            this.lightCoords = $$6;
            this.color = $$7;
            this.backgroundColor = $$8;
            this.outlineColor = $$9;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TextSubmit.class), TextSubmit.class, "pose;x;y;string;dropShadow;displayMode;lightCoords;color;backgroundColor;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->string:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->dropShadow:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->displayMode:Lnet/minecraft/client/gui/Font$DisplayMode;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TextSubmit.class), TextSubmit.class, "pose;x;y;string;dropShadow;displayMode;lightCoords;color;backgroundColor;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->string:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->dropShadow:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->displayMode:Lnet/minecraft/client/gui/Font$DisplayMode;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TextSubmit.class, Object.class), TextSubmit.class, "pose;x;y;string;dropShadow;displayMode;lightCoords;color;backgroundColor;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->x:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->y:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->string:Lnet/minecraft/util/FormattedCharSequence;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->dropShadow:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->displayMode:Lnet/minecraft/client/gui/Font$DisplayMode;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->color:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->backgroundColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TextSubmit;->outlineColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public float x() {
            return this.x;
        }

        public float y() {
            return this.y;
        }

        public FormattedCharSequence string() {
            return this.string;
        }

        public boolean dropShadow() {
            return this.dropShadow;
        }

        public Font.DisplayMode displayMode() {
            return this.displayMode;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int color() {
            return this.color;
        }

        public int backgroundColor() {
            return this.backgroundColor;
        }

        public int outlineColor() {
            return this.outlineColor;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit.class */
    public static final class LeashSubmit extends Record {
        private final Matrix4f pose;
        private final EntityRenderState.LeashState leashState;

        public LeashSubmit(Matrix4f $$0, EntityRenderState.LeashState $$1) {
            this.pose = $$0;
            this.leashState = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LeashSubmit.class), LeashSubmit.class, "pose;leashState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->leashState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState$LeashState;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LeashSubmit.class), LeashSubmit.class, "pose;leashState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->leashState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState$LeashState;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LeashSubmit.class, Object.class), LeashSubmit.class, "pose;leashState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$LeashSubmit;->leashState:Lnet/minecraft/client/renderer/entity/state/EntityRenderState$LeashState;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public EntityRenderState.LeashState leashState() {
            return this.leashState;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit.class */
    public static final class ModelSubmit<S> extends Record {
        private final PoseStack.Pose pose;
        private final Model<? super S> model;
        private final S state;
        private final int lightCoords;
        private final int overlayCoords;
        private final int tintedColor;
        private final TextureAtlasSprite sprite;
        private final int outlineColor;
        private final ModelFeatureRenderer.CrumblingOverlay crumblingOverlay;

        public ModelSubmit(PoseStack.Pose $$0, Model<? super S> $$1, S $$2, int $$3, int $$4, int $$5, TextureAtlasSprite $$6, int $$7, ModelFeatureRenderer.CrumblingOverlay $$8) {
            this.pose = $$0;
            this.model = $$1;
            this.state = $$2;
            this.lightCoords = $$3;
            this.overlayCoords = $$4;
            this.tintedColor = $$5;
            this.sprite = $$6;
            this.outlineColor = $$7;
            this.crumblingOverlay = $$8;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModelSubmit.class), ModelSubmit.class, "pose;model;state;lightCoords;overlayCoords;tintedColor;sprite;outlineColor;crumblingOverlay", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->model:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->state:Ljava/lang/Object;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModelSubmit.class), ModelSubmit.class, "pose;model;state;lightCoords;overlayCoords;tintedColor;sprite;outlineColor;crumblingOverlay", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->model:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->state:Ljava/lang/Object;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModelSubmit.class, Object.class), ModelSubmit.class, "pose;model;state;lightCoords;overlayCoords;tintedColor;sprite;outlineColor;crumblingOverlay", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->model:Lnet/minecraft/client/model/Model;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->state:Ljava/lang/Object;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public Model<? super S> model() {
            return this.model;
        }

        public S state() {
            return this.state;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }

        public int tintedColor() {
            return this.tintedColor;
        }

        public TextureAtlasSprite sprite() {
            return this.sprite;
        }

        public int outlineColor() {
            return this.outlineColor;
        }

        public ModelFeatureRenderer.CrumblingOverlay crumblingOverlay() {
            return this.crumblingOverlay;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit.class */
    public static final class ModelPartSubmit extends Record {
        private final PoseStack.Pose pose;
        private final ModelPart modelPart;
        private final int lightCoords;
        private final int overlayCoords;
        private final TextureAtlasSprite sprite;
        private final boolean sheeted;
        private final boolean hasFoil;
        private final int tintedColor;
        private final ModelFeatureRenderer.CrumblingOverlay crumblingOverlay;
        private final int outlineColor;

        public ModelPartSubmit(PoseStack.Pose $$0, ModelPart $$1, int $$2, int $$3, TextureAtlasSprite $$4, boolean $$5, boolean $$6, int $$7, ModelFeatureRenderer.CrumblingOverlay $$8, int $$9) {
            this.pose = $$0;
            this.modelPart = $$1;
            this.lightCoords = $$2;
            this.overlayCoords = $$3;
            this.sprite = $$4;
            this.sheeted = $$5;
            this.hasFoil = $$6;
            this.tintedColor = $$7;
            this.crumblingOverlay = $$8;
            this.outlineColor = $$9;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ModelPartSubmit.class), ModelPartSubmit.class, "pose;modelPart;lightCoords;overlayCoords;sprite;sheeted;hasFoil;tintedColor;crumblingOverlay;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->modelPart:Lnet/minecraft/client/model/geom/ModelPart;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sheeted:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->hasFoil:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ModelPartSubmit.class), ModelPartSubmit.class, "pose;modelPart;lightCoords;overlayCoords;sprite;sheeted;hasFoil;tintedColor;crumblingOverlay;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->modelPart:Lnet/minecraft/client/model/geom/ModelPart;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sheeted:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->hasFoil:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ModelPartSubmit.class, Object.class), ModelPartSubmit.class, "pose;modelPart;lightCoords;overlayCoords;sprite;sheeted;hasFoil;tintedColor;crumblingOverlay;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->modelPart:Lnet/minecraft/client/model/geom/ModelPart;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sprite:Lnet/minecraft/client/renderer/texture/TextureAtlasSprite;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->sheeted:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->hasFoil:Z", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->tintedColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->crumblingOverlay:Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelPartSubmit;->outlineColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public ModelPart modelPart() {
            return this.modelPart;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }

        public TextureAtlasSprite sprite() {
            return this.sprite;
        }

        public boolean sheeted() {
            return this.sheeted;
        }

        public boolean hasFoil() {
            return this.hasFoil;
        }

        public int tintedColor() {
            return this.tintedColor;
        }

        public ModelFeatureRenderer.CrumblingOverlay crumblingOverlay() {
            return this.crumblingOverlay;
        }

        public int outlineColor() {
            return this.outlineColor;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit.class */
    public static final class TranslucentModelSubmit<S> extends Record {
        private final ModelSubmit<S> modelSubmit;
        private final RenderType renderType;
        private final Vector3f position;

        public TranslucentModelSubmit(ModelSubmit<S> $$0, RenderType $$1, Vector3f $$2) {
            this.modelSubmit = $$0;
            this.renderType = $$1;
            this.position = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TranslucentModelSubmit.class), TranslucentModelSubmit.class, "modelSubmit;renderType;position", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->modelSubmit:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TranslucentModelSubmit.class), TranslucentModelSubmit.class, "modelSubmit;renderType;position", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->modelSubmit:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TranslucentModelSubmit.class, Object.class), TranslucentModelSubmit.class, "modelSubmit;renderType;position", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->modelSubmit:Lnet/minecraft/client/renderer/SubmitNodeStorage$ModelSubmit;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$TranslucentModelSubmit;->position:Lorg/joml/Vector3f;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public ModelSubmit<S> modelSubmit() {
            return this.modelSubmit;
        }

        public RenderType renderType() {
            return this.renderType;
        }

        public Vector3f position() {
            return this.position;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit.class */
    public static final class BlockSubmit extends Record {
        private final PoseStack.Pose pose;
        private final BlockState state;
        private final int lightCoords;
        private final int overlayCoords;
        private final int outlineColor;

        public BlockSubmit(PoseStack.Pose $$0, BlockState $$1, int $$2, int $$3, int $$4) {
            this.pose = $$0;
            this.state = $$1;
            this.lightCoords = $$2;
            this.overlayCoords = $$3;
            this.outlineColor = $$4;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockSubmit.class), BlockSubmit.class, "pose;state;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->state:Lnet/minecraft/world/level/block/state/BlockState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockSubmit.class), BlockSubmit.class, "pose;state;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->state:Lnet/minecraft/world/level/block/state/BlockState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockSubmit.class, Object.class), BlockSubmit.class, "pose;state;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->state:Lnet/minecraft/world/level/block/state/BlockState;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockSubmit;->outlineColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public BlockState state() {
            return this.state;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }

        public int outlineColor() {
            return this.outlineColor;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit.class */
    public static final class MovingBlockSubmit extends Record {
        private final Matrix4f pose;
        private final MovingBlockRenderState movingBlockRenderState;

        public MovingBlockSubmit(Matrix4f $$0, MovingBlockRenderState $$1) {
            this.pose = $$0;
            this.movingBlockRenderState = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MovingBlockSubmit.class), MovingBlockSubmit.class, "pose;movingBlockRenderState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->movingBlockRenderState:Lnet/minecraft/client/renderer/block/MovingBlockRenderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MovingBlockSubmit.class), MovingBlockSubmit.class, "pose;movingBlockRenderState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->movingBlockRenderState:Lnet/minecraft/client/renderer/block/MovingBlockRenderState;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MovingBlockSubmit.class, Object.class), MovingBlockSubmit.class, "pose;movingBlockRenderState", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->pose:Lorg/joml/Matrix4f;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$MovingBlockSubmit;->movingBlockRenderState:Lnet/minecraft/client/renderer/block/MovingBlockRenderState;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Matrix4f pose() {
            return this.pose;
        }

        public MovingBlockRenderState movingBlockRenderState() {
            return this.movingBlockRenderState;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit.class */
    public static final class BlockModelSubmit extends Record {
        private final PoseStack.Pose pose;
        private final RenderType renderType;
        private final BlockStateModel model;
        private final float r;
        private final float g;
        private final float b;
        private final int lightCoords;
        private final int overlayCoords;
        private final int outlineColor;

        public BlockModelSubmit(PoseStack.Pose $$0, RenderType $$1, BlockStateModel $$2, float $$3, float $$4, float $$5, int $$6, int $$7, int $$8) {
            this.pose = $$0;
            this.renderType = $$1;
            this.model = $$2;
            this.r = $$3;
            this.g = $$4;
            this.b = $$5;
            this.lightCoords = $$6;
            this.overlayCoords = $$7;
            this.outlineColor = $$8;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, BlockModelSubmit.class), BlockModelSubmit.class, "pose;renderType;model;r;g;b;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->model:Lnet/minecraft/client/renderer/block/model/BlockStateModel;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->r:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->g:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->b:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, BlockModelSubmit.class), BlockModelSubmit.class, "pose;renderType;model;r;g;b;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->model:Lnet/minecraft/client/renderer/block/model/BlockStateModel;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->r:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->g:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->b:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->outlineColor:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, BlockModelSubmit.class, Object.class), BlockModelSubmit.class, "pose;renderType;model;r;g;b;lightCoords;overlayCoords;outlineColor", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->model:Lnet/minecraft/client/renderer/block/model/BlockStateModel;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->r:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->g:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->b:F", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$BlockModelSubmit;->outlineColor:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public RenderType renderType() {
            return this.renderType;
        }

        public BlockStateModel model() {
            return this.model;
        }

        public float r() {
            return this.r;
        }

        public float g() {
            return this.g;
        }

        public float b() {
            return this.b;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }

        public int outlineColor() {
            return this.outlineColor;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit.class */
    public static final class ItemSubmit extends Record {
        private final PoseStack.Pose pose;
        private final ItemDisplayContext displayContext;
        private final int lightCoords;
        private final int overlayCoords;
        private final int outlineColor;
        private final int[] tintLayers;
        private final List<BakedQuad> quads;
        private final RenderType renderType;
        private final ItemStackRenderState.FoilType foilType;

        public ItemSubmit(PoseStack.Pose $$0, ItemDisplayContext $$1, int $$2, int $$3, int $$4, int[] $$5, List<BakedQuad> $$6, RenderType $$7, ItemStackRenderState.FoilType $$8) {
            this.pose = $$0;
            this.displayContext = $$1;
            this.lightCoords = $$2;
            this.overlayCoords = $$3;
            this.outlineColor = $$4;
            this.tintLayers = $$5;
            this.quads = $$6;
            this.renderType = $$7;
            this.foilType = $$8;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ItemSubmit.class), ItemSubmit.class, "pose;displayContext;lightCoords;overlayCoords;outlineColor;tintLayers;quads;renderType;foilType", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->displayContext:Lnet/minecraft/world/item/ItemDisplayContext;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->tintLayers:[I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->quads:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->foilType:Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ItemSubmit.class), ItemSubmit.class, "pose;displayContext;lightCoords;overlayCoords;outlineColor;tintLayers;quads;renderType;foilType", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->displayContext:Lnet/minecraft/world/item/ItemDisplayContext;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->tintLayers:[I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->quads:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->foilType:Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ItemSubmit.class, Object.class), ItemSubmit.class, "pose;displayContext;lightCoords;overlayCoords;outlineColor;tintLayers;quads;renderType;foilType", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->displayContext:Lnet/minecraft/world/item/ItemDisplayContext;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->lightCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->overlayCoords:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->outlineColor:I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->tintLayers:[I", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->quads:Ljava/util/List;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->renderType:Lnet/minecraft/client/renderer/rendertype/RenderType;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$ItemSubmit;->foilType:Lnet/minecraft/client/renderer/item/ItemStackRenderState$FoilType;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public ItemDisplayContext displayContext() {
            return this.displayContext;
        }

        public int lightCoords() {
            return this.lightCoords;
        }

        public int overlayCoords() {
            return this.overlayCoords;
        }

        public int outlineColor() {
            return this.outlineColor;
        }

        public int[] tintLayers() {
            return this.tintLayers;
        }

        public List<BakedQuad> quads() {
            return this.quads;
        }

        public RenderType renderType() {
            return this.renderType;
        }

        public ItemStackRenderState.FoilType foilType() {
            return this.foilType;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit.class */
    public static final class CustomGeometrySubmit extends Record {
        private final PoseStack.Pose pose;
        private final SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer;

        public CustomGeometrySubmit(PoseStack.Pose $$0, SubmitNodeCollector.CustomGeometryRenderer $$1) {
            this.pose = $$0;
            this.customGeometryRenderer = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CustomGeometrySubmit.class), CustomGeometrySubmit.class, "pose;customGeometryRenderer", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->customGeometryRenderer:Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CustomGeometrySubmit.class), CustomGeometrySubmit.class, "pose;customGeometryRenderer", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->customGeometryRenderer:Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CustomGeometrySubmit.class, Object.class), CustomGeometrySubmit.class, "pose;customGeometryRenderer", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->pose:Lcom/mojang/blaze3d/vertex/PoseStack$Pose;", "FIELD:Lnet/minecraft/client/renderer/SubmitNodeStorage$CustomGeometrySubmit;->customGeometryRenderer:Lnet/minecraft/client/renderer/SubmitNodeCollector$CustomGeometryRenderer;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public PoseStack.Pose pose() {
            return this.pose;
        }

        public SubmitNodeCollector.CustomGeometryRenderer customGeometryRenderer() {
            return this.customGeometryRenderer;
        }
    }
}
