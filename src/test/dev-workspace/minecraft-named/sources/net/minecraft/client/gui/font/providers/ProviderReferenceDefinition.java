package net.minecraft.client.gui.font.providers;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.gui.font.providers.GlyphProviderDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/font/providers/ProviderReferenceDefinition.class */
public final class ProviderReferenceDefinition extends Record implements GlyphProviderDefinition {
    private final Identifier id;
    public static final MapCodec<ProviderReferenceDefinition> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
            return v0.id();
        })).apply($$0, ProviderReferenceDefinition::new);
    });

    public ProviderReferenceDefinition(Identifier $$0) {
        this.id = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ProviderReferenceDefinition.class), ProviderReferenceDefinition.class, "id", "FIELD:Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ProviderReferenceDefinition.class), ProviderReferenceDefinition.class, "id", "FIELD:Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ProviderReferenceDefinition.class, Object.class), ProviderReferenceDefinition.class, "id", "FIELD:Lnet/minecraft/client/gui/font/providers/ProviderReferenceDefinition;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier id() {
        return this.id;
    }

    @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
    public GlyphProviderType type() {
        return GlyphProviderType.REFERENCE;
    }

    @Override // net.minecraft.client.gui.font.providers.GlyphProviderDefinition
    public Either<GlyphProviderDefinition.Loader, GlyphProviderDefinition.Reference> unpack() {
        return Either.right(new GlyphProviderDefinition.Reference(this.id));
    }
}
