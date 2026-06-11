package net.minecraft.server.packs;

import com.mojang.brigadier.arguments.StringArgumentType;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.Crypt;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/packs/PackLocationInfo.class */
public final class PackLocationInfo extends Record {
    private final String id;
    private final Component title;
    private final PackSource source;
    private final Optional<KnownPack> knownPackInfo;

    public PackLocationInfo(String $$0, Component $$1, PackSource $$2, Optional<KnownPack> $$3) {
        this.id = $$0;
        this.title = $$1;
        this.source = $$2;
        this.knownPackInfo = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, PackLocationInfo.class), PackLocationInfo.class, "id;title;source;knownPackInfo", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->source:Lnet/minecraft/server/packs/repository/PackSource;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->knownPackInfo:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, PackLocationInfo.class), PackLocationInfo.class, "id;title;source;knownPackInfo", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->source:Lnet/minecraft/server/packs/repository/PackSource;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->knownPackInfo:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, PackLocationInfo.class, Object.class), PackLocationInfo.class, "id;title;source;knownPackInfo", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->id:Ljava/lang/String;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->source:Lnet/minecraft/server/packs/repository/PackSource;", "FIELD:Lnet/minecraft/server/packs/PackLocationInfo;->knownPackInfo:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public String id() {
        return this.id;
    }

    public Component title() {
        return this.title;
    }

    public PackSource source() {
        return this.source;
    }

    public Optional<KnownPack> knownPackInfo() {
        return this.knownPackInfo;
    }

    public Component createChatLink(boolean $$0, Component $$1) {
        return ComponentUtils.wrapInSquareBrackets(this.source.decorate(Component.literal(this.id))).withStyle($$2 -> {
            return $$2.withColor($$0 ? ChatFormatting.GREEN : ChatFormatting.RED).withInsertion(StringArgumentType.escapeIfRequired(this.id)).withHoverEvent(new HoverEvent.ShowText(Component.empty().append(this.title).append(Crypt.MIME_LINE_SEPARATOR).append($$1)));
        });
    }
}
