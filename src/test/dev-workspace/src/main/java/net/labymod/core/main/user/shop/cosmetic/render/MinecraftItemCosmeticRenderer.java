package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.meta.AnimationTrigger;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.user.GameUser;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.AnimationContainer;
import net.labymod.core.main.user.shop.bridge.ItemTags;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.MinecraftItemTypeData;
import net.labymod.core.main.user.shop.item.geometry.AnimationStorage;
import net.labymod.core.main.user.shop.item.items.minecraft.MinecraftItemRegistry;
import net.labymod.core.main.user.shop.item.positionprovider.MinecraftItemPositionProvider;
import net.labymod.core.main.user.shop.item.positionprovider.ShieldItemPositionProvider;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/MinecraftItemCosmeticRenderer.class */
public class MinecraftItemCosmeticRenderer extends AbstractCosmeticRenderer {
    private static final MinecraftItemRegistry REGISTRY = MinecraftItemRegistry.construct(registry -> {
        registry.register(VanillaItems.SHIELD, new ShieldItemPositionProvider());
    });

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public void render(CosmeticDefinition definition, RenderContext context, Stack stack) {
        AnimationStorage storage = getAnimationStorage(context, definition);
        if (storage == null) {
            return;
        }
        AnimationController controller = storage.getController();
        boolean isActivity = definition.hasTag(ItemTags.ACTIVITY);
        MainHand hand = isActivity ? MainHand.RIGHT : context.hand();
        boolean leftSide = hand == MainHand.LEFT;
        definition.setPosition(leftSide ? CosmeticDefinition.Position.LEFT : CosmeticDefinition.Position.RIGHT);
        float offset = leftSide ? 1.0f : -1.0f;
        AnimationContainer animations = getAnimationContainer(definition);
        if (context.player().getItemUseDurationTicks() > 0) {
            animations.handleAnimationTrigger(AnimationTrigger.BLOCKING, controller, context.player());
        } else {
            animations.handleAnimationTrigger(AnimationTrigger.IDLE, controller, context.player());
        }
        MinecraftItemTypeData typeData = (MinecraftItemTypeData) definition.typeData(MinecraftItemTypeData.class);
        stack.push();
        if (typeData != null && (!typeData.isAvailable() || isActivity)) {
            MinecraftItemPositionProvider provider = REGISTRY.findProvider(typeData.itemIdentifier());
            provider.apply(stack, context.playerModel());
        }
        if (!context.firstPerson()) {
            stack.translate(0.0f, 0.0f, (-(PlatformEnvironment.isAncientOpenGL() ? 2.2f : 1.25f)) * 0.0625f);
        }
        stack.rotate(offset * 180.0f, 0.0f, 1.0f, 0.0f);
        if (definition.itemModel() != null) {
            controller.applyAnimation(definition.itemModel().getModel(), new String[0]);
        }
        ProfilerScope ignored = ProfilerScope.of("render_model");
        try {
            renderModel(definition, context, stack, definition.details().getScale(), null);
            if (ignored != null) {
                ignored.close();
            }
            stack.pop();
        } catch (Throwable th) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean isVisibleInFirstPerson(CosmeticDefinition definition) {
        return false;
    }

    private AnimationContainer getAnimationContainer(CosmeticDefinition definition) {
        CosmeticAssets assets = definition.assets();
        if (assets != null) {
            return assets.animationContainer();
        }
        return definition.animationContainer();
    }

    private AnimationStorage getAnimationStorage(RenderContext context, CosmeticDefinition definition) {
        GameUser gameUserUser = context.user();
        if (gameUserUser instanceof DefaultGameUser) {
            DefaultGameUser defaultUser = (DefaultGameUser) gameUserUser;
            return defaultUser.getUserItemStorage().getAnimationStorage(definition.id());
        }
        return null;
    }
}
