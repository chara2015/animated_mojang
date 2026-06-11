package net.minecraft.network.chat.contents.data;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/data/StorageDataSource.class */
public final class StorageDataSource extends Record implements DataSource {
    private final Identifier id;
    public static final MapCodec<StorageDataSource> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(Identifier.CODEC.fieldOf("storage").forGetter((v0) -> {
            return v0.id();
        })).apply($$0, StorageDataSource::new);
    });

    public StorageDataSource(Identifier $$0) {
        this.id = $$0;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, StorageDataSource.class), StorageDataSource.class, "id", "FIELD:Lnet/minecraft/network/chat/contents/data/StorageDataSource;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, StorageDataSource.class, Object.class), StorageDataSource.class, "id", "FIELD:Lnet/minecraft/network/chat/contents/data/StorageDataSource;->id:Lnet/minecraft/resources/Identifier;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Identifier id() {
        return this.id;
    }

    @Override // net.minecraft.network.chat.contents.data.DataSource
    public Stream<CompoundTag> getData(CommandSourceStack $$0) {
        CompoundTag $$1 = $$0.getServer().getCommandStorage().get(this.id);
        return Stream.of($$1);
    }

    @Override // net.minecraft.network.chat.contents.data.DataSource
    public MapCodec<StorageDataSource> codec() {
        return MAP_CODEC;
    }

    @Override // java.lang.Record
    public String toString() {
        return "storage=" + String.valueOf(this.id);
    }
}
