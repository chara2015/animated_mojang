package net.labymod.core.main.user.shop.item.items.minecraft;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.core.main.user.shop.item.positionprovider.HideItemPositionProvider;
import net.labymod.core.main.user.shop.item.positionprovider.MinecraftItemPositionProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/item/items/minecraft/MinecraftItemRegistry.class */
public final class MinecraftItemRegistry {
    private static final MinecraftItemPositionProvider FALLBACK_PROVIDER = new HideItemPositionProvider();
    private final Map<ResourceLocation, MinecraftItemPositionProvider> providers = new HashMap();

    private MinecraftItemRegistry() {
    }

    public static MinecraftItemRegistry construct(Consumer<MinecraftItemRegistry> registryConsumer) {
        MinecraftItemRegistry registry = new MinecraftItemRegistry();
        registryConsumer.accept(registry);
        return registry;
    }

    public void register(VanillaItem item, MinecraftItemPositionProvider provider) {
        register(item.identifier(), provider);
    }

    public void register(ResourceLocation id, MinecraftItemPositionProvider provider) {
        this.providers.put(id, provider);
    }

    public MinecraftItemPositionProvider findProvider(VanillaItem item) {
        return findProvider(item.identifier());
    }

    public MinecraftItemPositionProvider findProvider(ResourceLocation id) {
        return this.providers.getOrDefault(id, FALLBACK_PROVIDER);
    }
}
