package net.minecraft.network.chat.contents;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.objects.ObjectInfo;
import net.minecraft.network.chat.contents.objects.ObjectInfos;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/contents/ObjectContents.class */
public final class ObjectContents extends Record implements ComponentContents {
    private final ObjectInfo contents;
    private static final String PLACEHOLDER = Character.toString(65532);
    public static final MapCodec<ObjectContents> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ObjectInfos.CODEC.forGetter((v0) -> {
            return v0.contents();
        })).apply($$0, ObjectContents::new);
    });

    public ObjectContents(ObjectInfo $$0) {
        this.contents = $$0;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ObjectContents.class), ObjectContents.class, "contents", "FIELD:Lnet/minecraft/network/chat/contents/ObjectContents;->contents:Lnet/minecraft/network/chat/contents/objects/ObjectInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ObjectContents.class), ObjectContents.class, "contents", "FIELD:Lnet/minecraft/network/chat/contents/ObjectContents;->contents:Lnet/minecraft/network/chat/contents/objects/ObjectInfo;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ObjectContents.class, Object.class), ObjectContents.class, "contents", "FIELD:Lnet/minecraft/network/chat/contents/ObjectContents;->contents:Lnet/minecraft/network/chat/contents/objects/ObjectInfo;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ObjectInfo contents() {
        return this.contents;
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public MapCodec<ObjectContents> codec() {
        return MAP_CODEC;
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
        return $$0.accept(this.contents.description());
    }

    @Override // net.minecraft.network.chat.ComponentContents
    public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
        return $$0.accept($$1.withFont(this.contents.fontDescription()), PLACEHOLDER);
    }
}
