package net.minecraft.client.renderer.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.util.RegistryContextSwapper;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ClientItem.class */
public final class ClientItem extends Record {
    private final ItemModel.Unbaked model;
    private final Properties properties;
    private final RegistryContextSwapper registrySwapper;
    public static final Codec<ClientItem> CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ItemModels.CODEC.fieldOf("model").forGetter((v0) -> {
            return v0.model();
        }), Properties.MAP_CODEC.forGetter((v0) -> {
            return v0.properties();
        })).apply($$0, ClientItem::new);
    });

    public ClientItem(ItemModel.Unbaked $$0, Properties $$1, RegistryContextSwapper $$2) {
        this.model = $$0;
        this.properties = $$1;
        this.registrySwapper = $$2;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ClientItem.class), ClientItem.class, "model;properties;registrySwapper", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->model:Lnet/minecraft/client/renderer/item/ItemModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->properties:Lnet/minecraft/client/renderer/item/ClientItem$Properties;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->registrySwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ClientItem.class), ClientItem.class, "model;properties;registrySwapper", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->model:Lnet/minecraft/client/renderer/item/ItemModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->properties:Lnet/minecraft/client/renderer/item/ClientItem$Properties;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->registrySwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ClientItem.class, Object.class), ClientItem.class, "model;properties;registrySwapper", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->model:Lnet/minecraft/client/renderer/item/ItemModel$Unbaked;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->properties:Lnet/minecraft/client/renderer/item/ClientItem$Properties;", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem;->registrySwapper:Lnet/minecraft/util/RegistryContextSwapper;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ItemModel.Unbaked model() {
        return this.model;
    }

    public Properties properties() {
        return this.properties;
    }

    public RegistryContextSwapper registrySwapper() {
        return this.registrySwapper;
    }

    public ClientItem(ItemModel.Unbaked $$0, Properties $$1) {
        this($$0, $$1, null);
    }

    public ClientItem withRegistrySwapper(RegistryContextSwapper $$0) {
        return new ClientItem(this.model, this.properties, $$0);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/item/ClientItem$Properties.class */
    public static final class Properties extends Record {
        private final boolean handAnimationOnSwap;
        private final boolean oversizedInGui;
        private final float swapAnimationScale;
        public static final Properties DEFAULT = new Properties(true, false, 1.0f);
        public static final MapCodec<Properties> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.BOOL.optionalFieldOf("hand_animation_on_swap", true).forGetter((v0) -> {
                return v0.handAnimationOnSwap();
            }), Codec.BOOL.optionalFieldOf("oversized_in_gui", false).forGetter((v0) -> {
                return v0.oversizedInGui();
            }), Codec.FLOAT.optionalFieldOf("swap_animation_scale", Float.valueOf(1.0f)).forGetter((v0) -> {
                return v0.swapAnimationScale();
            })).apply($$0, (v1, v2, v3) -> {
                return new Properties(v1, v2, v3);
            });
        });

        public Properties(boolean $$0, boolean $$1, float $$2) {
            this.handAnimationOnSwap = $$0;
            this.oversizedInGui = $$1;
            this.swapAnimationScale = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Properties.class), Properties.class, "handAnimationOnSwap;oversizedInGui;swapAnimationScale", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->handAnimationOnSwap:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->oversizedInGui:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->swapAnimationScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Properties.class), Properties.class, "handAnimationOnSwap;oversizedInGui;swapAnimationScale", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->handAnimationOnSwap:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->oversizedInGui:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->swapAnimationScale:F").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Properties.class, Object.class), Properties.class, "handAnimationOnSwap;oversizedInGui;swapAnimationScale", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->handAnimationOnSwap:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->oversizedInGui:Z", "FIELD:Lnet/minecraft/client/renderer/item/ClientItem$Properties;->swapAnimationScale:F").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public boolean handAnimationOnSwap() {
            return this.handAnimationOnSwap;
        }

        public boolean oversizedInGui() {
            return this.oversizedInGui;
        }

        public float swapAnimationScale() {
            return this.swapAnimationScale;
        }
    }
}
