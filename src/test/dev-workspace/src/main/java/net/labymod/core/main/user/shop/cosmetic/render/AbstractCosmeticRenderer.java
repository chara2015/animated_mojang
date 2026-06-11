package net.labymod.core.main.user.shop.cosmetic.render;

import net.labymod.api.Laby;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.PlayerClothes;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.animation.AnimationController;
import net.labymod.api.client.render.model.animation.AnimationRenderState;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.laby3d.pipeline.ItemRenderStates;
import net.labymod.api.laby3d.textures.TextureBindingSet;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.user.group.Group;
import net.labymod.api.user.shop.CloakPriority;
import net.labymod.api.user.shop.RenderMode;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.pool.FrameObjectPools;
import net.labymod.api.util.pool.ObjectPool;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.cosmetic.CosmeticAssets;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticRenderState;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticStateStorage;
import net.labymod.core.main.user.shop.cosmetic.texture.TextureOpaqueAnalyzer;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.user.shop.item.model.OffsetVector;
import net.labymod.core.main.user.shop.item.renderer.ItemModel;
import net.labymod.core.main.user.shop.item.renderer.ItemModelInstance;
import net.labymod.core.main.user.shop.item.state.ItemState;
import net.labymod.laby3d.api.pipeline.RenderState;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/cosmetic/render/AbstractCosmeticRenderer.class */
public abstract class AbstractCosmeticRenderer implements CosmeticRenderer {
    private static final int MAX_COSMETIC_DISTANCE = 5;
    private static final int PRIORITY_MULTIPLIER = 3;
    private static final ObjectPool<ItemState> ITEM_STATE_POOL = FrameObjectPools.instance().register(ItemState::new);

    @Override // net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer
    public boolean shouldRender(CosmeticDefinition definition, RenderContext context) {
        if (context.firstPerson() && !isVisibleInFirstPerson(definition)) {
            return false;
        }
        Group group = Laby.references().gameUserService().clientGameUser().visibleGroup();
        CosmeticDetails details = definition.details();
        if (details.isDraft() && !group.isStaffOrCosmeticCreator()) {
            return false;
        }
        boolean hideCape = details.isHideCape();
        Player player = context.player();
        if (hideCape && !player.isShownModelPart(PlayerClothes.CAPE)) {
            return false;
        }
        if (details.isHiddenWhileWearingElytra() && player.isWearingElytra()) {
            return false;
        }
        CosmeticsConfig cosmetics = Laby.labyAPI().config().ingame().cosmetics();
        if (hideCape && cosmetics.cloakPriority().get() != CloakPriority.LABYMOD && player.getCloakTexture() != null) {
            return false;
        }
        if ((hideCape && !player.isWearingElytra()) || Laby.labyAPI().minecraft().getClientPlayer() == null || Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext().isScreenContext()) {
            return true;
        }
        if (cosmetics.hideCosmeticsInDistance().get().booleanValue() && definition.getPriorityLevel() != 0) {
            int distanceExt = 5 - Math.min(5, definition.getPriorityLevel());
            double dist = Math.pow(cosmetics.hideAfterBlocks().get().intValue() + (distanceExt * 3), 2.0d);
            return MathHelper.distanceSquared(player, Laby.labyAPI().minecraft().getClientPlayer()) <= dist;
        }
        return isInRenderDistance(definition);
    }

    protected boolean isInRenderDistance(CosmeticDefinition definition) {
        return true;
    }

    protected void renderWithEffects(CosmeticDefinition definition, RenderContext context, Stack stack, boolean rightSide, @Nullable AnimationController animationController) {
        ItemModel itemModel = definition.itemModel();
        if (itemModel == null) {
            return;
        }
        renderModel(definition, context, stack, definition.details().getScale(), animationController, rightSide);
    }

    protected void renderModel(CosmeticDefinition definition, RenderContext context, Stack stack, double scale, @Nullable AnimationController animationController) {
        renderModel(definition, context, stack, scale, animationController, false);
    }

    protected void renderModel(CosmeticDefinition definition, RenderContext context, Stack stack, double scale, @Nullable AnimationController animationController, boolean rightSide) {
        RenderState renderState;
        RenderState selectedRenderState;
        RenderState renderState2;
        ItemModel itemModel = definition.itemModel();
        if (itemModel == null || itemModel.getModel() == null) {
            return;
        }
        itemModel.setFirstPerson(context.firstPerson());
        boolean applyScale = scale != 1.0d;
        if (applyScale) {
            stack.push();
            stack.scale((float) scale);
        }
        try {
            ProfilerScope ignored = ProfilerScope.of("submit_model");
            try {
                ItemState state = ITEM_STATE_POOL.acquire();
                state.animationController = animationController;
                state.animationRenderState = AnimationRenderState.forPlayer(context.player());
                state.player = context.player();
                state.playerModel = context.playerModel();
                state.metadata = context.metadata();
                state.rightSide = rightSide;
                PhysicData physicData = context.physicData();
                if (physicData != null) {
                    state.physicForward = physicData.getForward();
                    state.physicStrafe = physicData.getStrafe();
                    state.physicGravity = physicData.getGravity();
                    state.physicRenderYawOffset = physicData.getRenderYawOffset();
                    state.physicPitch = physicData.getPitch();
                }
                CosmeticRenderState cosmeticRenderState = getCosmeticRenderState(definition, context);
                if (cosmeticRenderState != null && !cosmeticRenderState.isOpacityAnalyzed()) {
                    analyzeOpacity(definition, cosmeticRenderState, context);
                }
                boolean retained = Laby.labyAPI().config().ingame().cosmetics().renderMode().get() == RenderMode.RETAINED;
                boolean opaque = cosmeticRenderState != null && cosmeticRenderState.isOpaqueTexture();
                if (opaque) {
                    if (retained) {
                        renderState2 = ItemRenderStates.RETAINED_OPAQUE_COSMETICS;
                    } else {
                        renderState2 = ItemRenderStates.OPAQUE_COSMETICS;
                    }
                    selectedRenderState = renderState2;
                } else {
                    if (retained) {
                        renderState = ItemRenderStates.RETAINED_TRANSLUCENT_COSMETICS;
                    } else {
                        renderState = ItemRenderStates.TRANSLUCENT_COSMETICS;
                    }
                    selectedRenderState = renderState;
                }
                Laby.references().geometrySubmitter().submitModel(state, new ItemModelInstance(itemModel, cosmeticRenderState), stack, selectedRenderState, TextureBindingSet.builder().setTexture(0, context.textureLocation()).build(), context.lightCoords(), context.overlayCoords());
                if (ignored != null) {
                    ignored.close();
                }
            } finally {
            }
        } finally {
            if (applyScale) {
                stack.pop();
            }
        }
    }

    @Nullable
    protected CosmeticRenderState getCosmeticRenderState(CosmeticDefinition definition, RenderContext context) {
        CosmeticStateStorage storage = ((DefaultGameUser) context.user()).getCosmeticStateStorage();
        CosmeticState cosmeticState = storage.getState(definition.id());
        if (cosmeticState == null) {
            return null;
        }
        return cosmeticState.renderState();
    }

    protected static void translateOffset(Stack stack, ItemMetadata metadata, boolean translateShoulder) {
        OffsetVector offset = metadata.getOffset();
        if (offset == null) {
            return;
        }
        float modelScale = Laby.labyAPI().renderPipeline().renderConstants().modelScale();
        int i = (translateShoulder && metadata.isRightSide()) ? -1 : 1;
        int side = i;
        stack.translate(((float) (offset.getX() * ((double) side))) / modelScale, ((float) (-offset.getY())) / modelScale, ((float) offset.getZ()) / modelScale);
    }

    private void analyzeOpacity(CosmeticDefinition definition, CosmeticRenderState renderState, RenderContext context) {
        ResourceLocation textureLocation;
        GameImage image;
        CosmeticAssets assets = definition.assets();
        if (assets == null || assets.model() == null || (textureLocation = context.textureLocation()) == null || (image = Laby.references().textureRepository().getImageFromTexture(textureLocation)) == null || image.isFreed()) {
            return;
        }
        renderState.setOpacityAnalyzed(true);
        Model model = assets.model();
        LabyExecutors.submitBackgroundTask(() -> {
            boolean opaque = TextureOpaqueAnalyzer.isOpaqueInUVRegions(model, image);
            renderState.setOpaqueTexture(opaque);
        });
    }
}
