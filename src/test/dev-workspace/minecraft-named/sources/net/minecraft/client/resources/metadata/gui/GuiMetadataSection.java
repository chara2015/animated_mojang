package net.minecraft.client.resources.metadata.gui;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.server.packs.metadata.MetadataSectionType;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/metadata/gui/GuiMetadataSection.class */
public final class GuiMetadataSection extends Record {
    private final GuiSpriteScaling scaling;
    public static final GuiMetadataSection DEFAULT = new GuiMetadataSection(GuiSpriteScaling.DEFAULT);
    public static final Codec<GuiMetadataSection> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(GuiSpriteScaling.CODEC.optionalFieldOf("scaling", GuiSpriteScaling.DEFAULT).forGetter((v0) -> {
            return v0.scaling();
        })).apply($$0, GuiMetadataSection::new);
    });
    public static final MetadataSectionType<GuiMetadataSection> TYPE = new MetadataSectionType<>("gui", CODEC);

    public GuiMetadataSection(GuiSpriteScaling $$0) {
        this.scaling = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GuiMetadataSection.class), GuiMetadataSection.class, "scaling", "FIELD:Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;->scaling:Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GuiMetadataSection.class), GuiMetadataSection.class, "scaling", "FIELD:Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;->scaling:Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GuiMetadataSection.class, Object.class), GuiMetadataSection.class, "scaling", "FIELD:Lnet/minecraft/client/resources/metadata/gui/GuiMetadataSection;->scaling:Lnet/minecraft/client/resources/metadata/gui/GuiSpriteScaling;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public GuiSpriteScaling scaling() {
        return this.scaling;
    }
}
