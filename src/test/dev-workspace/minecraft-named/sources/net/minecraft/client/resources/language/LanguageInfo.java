package net.minecraft.client.resources.language;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.block.entity.JigsawBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/resources/language/LanguageInfo.class */
public final class LanguageInfo extends Record {
    private final String region;
    private final String name;
    private final boolean bidirectional;
    public static final Codec<LanguageInfo> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ExtraCodecs.NON_EMPTY_STRING.fieldOf("region").forGetter((v0) -> {
            return v0.region();
        }), ExtraCodecs.NON_EMPTY_STRING.fieldOf(JigsawBlockEntity.NAME).forGetter((v0) -> {
            return v0.name();
        }), Codec.BOOL.optionalFieldOf("bidirectional", false).forGetter((v0) -> {
            return v0.bidirectional();
        })).apply($$0, (v1, v2, v3) -> {
            return new LanguageInfo(v1, v2, v3);
        });
    });

    public LanguageInfo(String $$0, String $$1, boolean $$2) {
        this.region = $$0;
        this.name = $$1;
        this.bidirectional = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, LanguageInfo.class), LanguageInfo.class, "region;name;bidirectional", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->region:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->bidirectional:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, LanguageInfo.class), LanguageInfo.class, "region;name;bidirectional", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->region:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->bidirectional:Z").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, LanguageInfo.class, Object.class), LanguageInfo.class, "region;name;bidirectional", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->region:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->name:Ljava/lang/String;", "FIELD:Lnet/minecraft/client/resources/language/LanguageInfo;->bidirectional:Z").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String region() {
        return this.region;
    }

    public String name() {
        return this.name;
    }

    public boolean bidirectional() {
        return this.bidirectional;
    }

    public Component toComponent() {
        return Component.literal(this.name + " (" + this.region + ")");
    }
}
