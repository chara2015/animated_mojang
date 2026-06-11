package net.minecraft.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MaterialMapper;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.MaterialSet;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.joml.Vector3fc;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/special/StandingSignSpecialRenderer.class */
public class StandingSignSpecialRenderer implements NoDataSpecialModelRenderer {
    private final MaterialSet materials;
    private final Model.Simple model;
    private final Material material;

    public StandingSignSpecialRenderer(MaterialSet $$0, Model.Simple $$1, Material $$2) {
        this.materials = $$0;
        this.model = $$1;
        this.material = $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked.class */
    public static final class Unbaked extends Record implements SpecialModelRenderer.Unbaked {
        private final WoodType woodType;
        private final Optional<Identifier> texture;
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(WoodType.CODEC.fieldOf("wood_type").forGetter((v0) -> {
                return v0.woodType();
            }), Identifier.CODEC.optionalFieldOf("texture").forGetter((v0) -> {
                return v0.texture();
            })).apply($$0, Unbaked::new);
        });

        public Unbaked(WoodType $$0, Optional<Identifier> $$1) {
            this.woodType = $$0;
            this.texture = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Unbaked.class), Unbaked.class, "woodType;texture", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->woodType:Lnet/minecraft/world/level/block/state/properties/WoodType;", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->texture:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Unbaked.class), Unbaked.class, "woodType;texture", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->woodType:Lnet/minecraft/world/level/block/state/properties/WoodType;", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->texture:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Unbaked.class, Object.class), Unbaked.class, "woodType;texture", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->woodType:Lnet/minecraft/world/level/block/state/properties/WoodType;", "FIELD:Lnet/minecraft/client/renderer/special/StandingSignSpecialRenderer$Unbaked;->texture:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public WoodType woodType() {
            return this.woodType;
        }

        public Optional<Identifier> texture() {
            return this.texture;
        }

        public Unbaked(WoodType $$0) {
            this($$0, Optional.empty());
        }

        @Override // net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override // net.minecraft.client.renderer.special.SpecialModelRenderer.Unbaked
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext $$0) {
            Model.Simple $$1 = SignRenderer.createSignModel($$0.entityModelSet(), this.woodType, true);
            Optional<Identifier> optional = this.texture;
            MaterialMapper materialMapper = Sheets.SIGN_MAPPER;
            Objects.requireNonNull(materialMapper);
            Material $$2 = (Material) optional.map(materialMapper::apply).orElseGet(() -> {
                return Sheets.getSignMaterial(this.woodType);
            });
            return new StandingSignSpecialRenderer($$0.materials(), $$1, $$2);
        }
    }

    @Override // net.minecraft.client.renderer.special.NoDataSpecialModelRenderer
    public void submit(ItemDisplayContext $$0, PoseStack $$1, SubmitNodeCollector $$2, int $$3, int $$4, boolean $$5, int $$6) {
        SignRenderer.submitSpecial(this.materials, $$1, $$2, $$3, $$4, this.model, this.material);
    }

    @Override // net.minecraft.client.renderer.special.SpecialModelRenderer
    public void getExtents(Consumer<Vector3fc> $$0) {
        PoseStack $$1 = new PoseStack();
        SignRenderer.applyInHandTransforms($$1);
        this.model.root().getExtentsForGui($$1, $$0);
    }
}
