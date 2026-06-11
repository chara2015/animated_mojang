package net.labymod.core.main.user.shop.cosmetic.render;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.EnumMap;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/CosmeticRendererRegistry.class */
public class CosmeticRendererRegistry {
    private final EnumMap<CosmeticType, CosmeticRenderer> renderers = new EnumMap<>(CosmeticType.class);
    private final Int2ObjectOpenHashMap<CosmeticRenderer> overrides = new Int2ObjectOpenHashMap<>();

    public void register(CosmeticType type, CosmeticRenderer renderer) {
        this.renderers.put(type, renderer);
    }

    public void registerOverride(int cosmeticId, CosmeticRenderer renderer) {
        this.overrides.put(cosmeticId, renderer);
    }

    @Nullable
    public CosmeticRenderer getRenderer(CosmeticDefinition definition) {
        CosmeticRenderer override = (CosmeticRenderer) this.overrides.get(definition.id());
        if (override != null) {
            return override;
        }
        return this.renderers.get(definition.cosmeticType());
    }
}
