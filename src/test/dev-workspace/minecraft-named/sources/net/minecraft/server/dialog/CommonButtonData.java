package net.minecraft.server.dialog;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.world.entity.Display;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/CommonButtonData.class */
public final class CommonButtonData extends Record {
    private final Component label;
    private final Optional<Component> tooltip;
    private final int width;
    public static final int DEFAULT_WIDTH = 150;
    public static final MapCodec<CommonButtonData> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ComponentSerialization.CODEC.fieldOf("label").forGetter((v0) -> {
            return v0.label();
        }), ComponentSerialization.CODEC.optionalFieldOf("tooltip").forGetter((v0) -> {
            return v0.tooltip();
        }), Dialog.WIDTH_CODEC.optionalFieldOf(Display.TAG_WIDTH, 150).forGetter((v0) -> {
            return v0.width();
        })).apply($$0, (v1, v2, v3) -> {
            return new CommonButtonData(v1, v2, v3);
        });
    });

    public CommonButtonData(Component $$0, Optional<Component> $$1, int $$2) {
        this.label = $$0;
        this.tooltip = $$1;
        this.width = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CommonButtonData.class), CommonButtonData.class, "label;tooltip;width", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->tooltip:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CommonButtonData.class), CommonButtonData.class, "label;tooltip;width", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->tooltip:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->width:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CommonButtonData.class, Object.class), CommonButtonData.class, "label;tooltip;width", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->label:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->tooltip:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonButtonData;->width:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Component label() {
        return this.label;
    }

    public Optional<Component> tooltip() {
        return this.tooltip;
    }

    public int width() {
        return this.width;
    }

    public CommonButtonData(Component $$0, int $$1) {
        this($$0, Optional.empty(), $$1);
    }
}
