package net.labymod.core.main.user.shop.cosmetic;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.ModelAnimation;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.item.geometry.effect.GeometryEffect;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/CosmeticAssets.class */
public final class CosmeticAssets {
    private final Model model;
    private final AnimationContainer animationContainer;
    private final List<GeometryEffect> effects;
    private final boolean hasOutlineParts;

    public CosmeticAssets(Model model, AnimationContainer animationContainer, List<GeometryEffect> effects, boolean hasOutlineParts) {
        this.model = model;
        this.animationContainer = animationContainer;
        this.effects = effects;
        this.hasOutlineParts = hasOutlineParts;
    }

    public static CosmeticAssets ofModel(Model model, List<GeometryEffect> effects, boolean hasOutlineParts) {
        return new CosmeticAssets(model, new AnimationContainer(), effects, hasOutlineParts);
    }

    public static CosmeticAssets ofAnimated(Model model, Collection<ModelAnimation> animations, List<GeometryEffect> effects, boolean hasOutlineParts) {
        return new CosmeticAssets(model, new AnimationContainer(animations), effects, hasOutlineParts);
    }

    public Model model() {
        return this.model;
    }

    public AnimationContainer animationContainer() {
        return this.animationContainer;
    }

    public List<GeometryEffect> effects() {
        return this.effects == null ? Collections.emptyList() : this.effects;
    }

    public boolean hasOutlineParts() {
        return this.hasOutlineParts;
    }
}
