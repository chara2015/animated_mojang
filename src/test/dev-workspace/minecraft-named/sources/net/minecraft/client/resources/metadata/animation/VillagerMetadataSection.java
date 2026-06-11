package net.minecraft.client.resources.metadata.animation;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/animation/VillagerMetadataSection.class */
public final class VillagerMetadataSection extends Record {
    private final Hat hat;
    public static final Codec<VillagerMetadataSection> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(Hat.CODEC.optionalFieldOf(PartNames.HAT, Hat.NONE).forGetter((v0) -> {
            return v0.hat();
        })).apply($$0, VillagerMetadataSection::new);
    });
    public static final MetadataSectionType<VillagerMetadataSection> TYPE = new MetadataSectionType<>("villager", CODEC);

    public VillagerMetadataSection(Hat $$0) {
        this.hat = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, VillagerMetadataSection.class), VillagerMetadataSection.class, "hat", "FIELD:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection;->hat:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection$Hat;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, VillagerMetadataSection.class), VillagerMetadataSection.class, "hat", "FIELD:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection;->hat:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection$Hat;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, VillagerMetadataSection.class, Object.class), VillagerMetadataSection.class, "hat", "FIELD:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection;->hat:Lnet/minecraft/client/resources/metadata/animation/VillagerMetadataSection$Hat;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Hat hat() {
        return this.hat;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/animation/VillagerMetadataSection$Hat.class */
    public enum Hat implements StringRepresentable {
        NONE("none"),
        PARTIAL("partial"),
        FULL("full");

        public static final Codec<Hat> CODEC = StringRepresentable.fromEnum(Hat::values);
        private final String name;

        Hat(String $$0) {
            this.name = $$0;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}
