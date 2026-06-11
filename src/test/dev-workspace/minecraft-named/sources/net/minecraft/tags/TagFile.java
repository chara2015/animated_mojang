package net.minecraft.tags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/tags/TagFile.class */
public final class TagFile extends Record {
    private final List<TagEntry> entries;
    private final boolean replace;
    public static final Codec<TagFile> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(TagEntry.CODEC.listOf().fieldOf("values").forGetter((v0) -> {
            return v0.entries();
        }), Codec.BOOL.optionalFieldOf("replace", false).forGetter((v0) -> {
            return v0.replace();
        })).apply($$0, (v1, v2) -> {
            return new TagFile(v1, v2);
        });
    });

    public TagFile(List<TagEntry> $$0, boolean $$1) {
        this.entries = $$0;
        this.replace = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, TagFile.class), TagFile.class, "entries;replace", "FIELD:Lnet/minecraft/tags/TagFile;->entries:Ljava/util/List;", "FIELD:Lnet/minecraft/tags/TagFile;->replace:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, TagFile.class), TagFile.class, "entries;replace", "FIELD:Lnet/minecraft/tags/TagFile;->entries:Ljava/util/List;", "FIELD:Lnet/minecraft/tags/TagFile;->replace:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, TagFile.class, Object.class), TagFile.class, "entries;replace", "FIELD:Lnet/minecraft/tags/TagFile;->entries:Ljava/util/List;", "FIELD:Lnet/minecraft/tags/TagFile;->replace:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public List<TagEntry> entries() {
        return this.entries;
    }

    public boolean replace() {
        return this.replace;
    }
}
