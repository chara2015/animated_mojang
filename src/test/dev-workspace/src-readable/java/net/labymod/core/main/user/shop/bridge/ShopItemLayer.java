package net.labymod.core.main.user.shop.bridge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.laby.lib.cosmetics.AttachmentPoint;
import net.laby.lib.cosmetics.TextureService;
import net.laby.lib.http.validation.ValidatedHttpClient;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.Textures;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.PositionType;
import net.labymod.api.client.entity.player.tag.TagRegistry;
import net.labymod.api.client.gfx.pipeline.RenderEnvironmentContext;
import net.labymod.api.client.options.MainHand;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.configuration.labymod.main.laby.ingame.CosmeticsConfig;
import net.labymod.api.event.EventBus;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.resources.ReleaseTextureEvent;
import net.labymod.api.labyconnect.LabyConnect;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.api.loader.platform.PlatformEnvironment;
import net.labymod.api.profiler.frame.FrameProfiler;
import net.labymod.api.profiler.frame.ProfilerScope;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.tag.Tag;
import net.labymod.api.tag.Taggable;
import net.labymod.api.tag.TaggedObject;
import net.labymod.api.user.GameUser;
import net.labymod.api.user.GameUserService;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.entity.player.DummyPlayer;
import net.labymod.core.main.profiler.RenderProfiler;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.GameUserItem;
import net.labymod.core.main.user.shop.JsonBodyDeserializer;
import net.labymod.core.main.user.shop.cosmetic.CosmeticDefinition;
import net.labymod.core.main.user.shop.cosmetic.CosmeticType;
import net.labymod.core.main.user.shop.cosmetic.loader.CosmeticLifecycleManager;
import net.labymod.core.main.user.shop.cosmetic.render.AttachmentCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.CosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.CosmeticRendererRegistry;
import net.labymod.core.main.user.shop.cosmetic.render.EyelidsCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.MinecraftItemCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.PetCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.RenderContext;
import net.labymod.core.main.user.shop.cosmetic.render.SnoxhCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.render.WalkingPetCosmeticRenderer;
import net.labymod.core.main.user.shop.cosmetic.state.CosmeticState;
import net.labymod.core.main.user.shop.cosmetic.texture.CosmeticTextureService;
import net.labymod.core.main.user.shop.cosmetic.texture.IndividualTextureProvider;
import net.labymod.core.main.user.shop.cosmetic.texture.TextureProvider;
import net.labymod.core.main.user.shop.cosmetic.texture.TextureUploadThrottler;
import net.labymod.core.main.user.shop.item.CosmeticDetails;
import net.labymod.core.main.user.shop.item.geometry.effect.PhysicData;
import net.labymod.core.main.user.shop.item.metadata.ItemMetadata;
import net.labymod.core.main.util.LabyLibHttpClients;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ShopItemLayer.class */
@Singleton
@Referenceable
public class ShopItemLayer implements Taggable {
    private static final Logging LOGGER = Logging.getLogger();
    private static final float SNEAK_OFFSET = 0.2f;
    private static final float LEG_SNEAK_OFFSET = 0.1875f;
    private final LabyAPI labyAPI;
    private final CosmeticsConfig cosmeticsConfig;
    private final TextureProvider textureProvider;
    private final CosmeticLifecycleManager lifecycleManager;
    private boolean renderedCustomMinecraftItem;
    private MainHand hand;
    private final TaggedObject taggedObject = new TaggedObject();
    private final Set<ResourceLocation> releasedTextures = new HashSet();
    private ItemFilter itemFilter = ItemFilters.defaultFilter();
    private final GameUserService gameUserService = Laby.references().gameUserService();
    private final LabyConnect labyConnect = Laby.references().labyConnect();
    private final PhysicData physicData = new PhysicData();
    private final CosmeticRendererRegistry rendererRegistry = new CosmeticRendererRegistry();

    @Inject
    public ShopItemLayer(TagRegistry tagRegistry, LabyAPI labyAPI, EventBus eventBus, LabyLibHttpClients httpClients) {
        this.labyAPI = labyAPI;
        this.cosmeticsConfig = this.labyAPI.config().ingame().cosmetics();
        PetCosmeticRenderer petRenderer = new PetCosmeticRenderer();
        this.rendererRegistry.register(CosmeticType.COSMETIC, new AttachmentCosmeticRenderer());
        this.rendererRegistry.register(CosmeticType.FLYING_PET, petRenderer);
        this.rendererRegistry.register(CosmeticType.SHOULDER_PET, petRenderer);
        this.rendererRegistry.register(CosmeticType.WALKING_PET, new WalkingPetCosmeticRenderer());
        this.rendererRegistry.register(CosmeticType.MINECRAFT_ITEM, new MinecraftItemCosmeticRenderer());
        this.rendererRegistry.registerOverride(36, new EyelidsCosmeticRenderer());
        this.rendererRegistry.registerOverride(32, new SnoxhCosmeticRenderer());
        ValidatedHttpClient validatedClient = httpClients.createValidatedHttpClient(JsonBodyDeserializer.defaultGson());
        TextureUploadThrottler uploadThrottler = new TextureUploadThrottler();
        CosmeticTextureService cosmeticTextureService = new CosmeticTextureService(new TextureService(validatedClient), labyAPI.renderPipeline().resources().textureRepository(), labyAPI.renderPipeline().resources(), Laby.references().mojangTextureService(), uploadThrottler);
        this.textureProvider = new IndividualTextureProvider(cosmeticTextureService);
        this.lifecycleManager = new CosmeticLifecycleManager(uploadThrottler);
        tagRegistry.register("item-offset", PositionType.BELOW_NAME, new ItemOffsetTag());
        eventBus.registerListener(this);
    }

    @Subscribe
    public void onTextureRelease(ReleaseTextureEvent event) {
        this.releasedTextures.add(event.location());
    }

    @Subscribe
    public void onTick(GameTickEvent event) {
        if (event.phase() != Phase.POST || !this.cosmeticsConfig.renderCosmetics().get().booleanValue()) {
            return;
        }
        this.lifecycleManager.processLoadQueue();
        ProfilerScope ignored = ProfilerScope.of("cosmetics_tick");
        try {
            ClientWorld level = this.labyAPI.minecraft().clientWorld();
            for (Player player : level.getPlayers()) {
                tick(player);
            }
            if (ignored != null) {
                ignored.close();
            }
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

    private void tick(Player player) {
        DefaultGameUser user = (DefaultGameUser) player.gameUser();
        GameUserData userData = user.getUserData();
        if (userData == null || userData.getItems().isEmpty()) {
            return;
        }
        boolean walkingPetsVisible = this.cosmeticsConfig.walkingPets().get().booleanValue();
        this.physicData.resetWalkingPetIndex();
        List<GameUserItem> items = userData.getItems();
        for (GameUserItem userItem : items) {
            CosmeticDefinition definition = userItem.definition();
            if (definition.cosmeticType() != CosmeticType.WALKING_PET || walkingPetsVisible) {
                CosmeticRenderer renderer = this.rendererRegistry.getRenderer(definition);
                if (renderer != null) {
                    ProfilerScope ignored = ProfilerScope.of("tick/" + definition.getName());
                    try {
                        renderer.tick(definition, player, user, this.physicData);
                        if (ignored != null) {
                            ignored.close();
                        }
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
                } else {
                    continue;
                }
            }
        }
    }

    public ShopItemLayer applyTag(Tag tag) {
        setTag(tag);
        return this;
    }

    public ShopItemLayer applyTag(Tag... tags) {
        setTag(tags);
        return this;
    }

    public ShopItemLayer filter(ItemFilter itemFilter) {
        this.itemFilter = itemFilter;
        return this;
    }

    public ShopItemLayer hand(MainHand hand) {
        this.hand = hand;
        return this;
    }

    public void render(Stack stack, Player player, PlayerModel playerModel, int packedLight, float partialTicks) {
        int iIntValue;
        boolean rendered;
        this.lifecycleManager.processUploads();
        this.renderedCustomMinecraftItem = false;
        boolean firstPerson = hasTag(ItemTags.FIRST_PERSON);
        if (player.isInvisible() && !firstPerson) {
            resetValues();
            return;
        }
        if (!(player instanceof DummyPlayer) && !isSelfOrFriend(player)) {
            resetValues();
            return;
        }
        if (player instanceof DummyPlayer) {
            iIntValue = Integer.MAX_VALUE;
        } else {
            iIntValue = this.cosmeticsConfig.maxCosmeticsPerPlayer().get().intValue();
        }
        int maxItems = iIntValue;
        DefaultGameUser user = (DefaultGameUser) player.gameUser();
        GameUserData userData = user.getUserData();
        if (userData == null || userData.getItems().isEmpty()) {
            resetValues();
            return;
        }
        FrameProfiler.push((Supplier<String>) () -> {
            return "cosmetics(" + player.getName() + ")";
        });
        ProfilerScope ignored = ProfilerScope.of("physic_update");
        try {
            this.physicData.update(player, playerModel, partialTicks);
            if (ignored != null) {
                ignored.close();
            }
            List<GameUserItem> items = userData.getItems();
            int renderedCosmetics = 0;
            for (GameUserItem gameUserItem : items) {
                if (renderedCosmetics >= maxItems) {
                    break;
                }
                CosmeticDefinition definition = gameUserItem.definition();
                if (!this.itemFilter.filter(definition)) {
                    definition.clearTags();
                    if (hasTag(ItemTags.WORLD)) {
                        definition.setTag(ItemTags.WORLD);
                    }
                    try {
                        ProfilerScope scope = ProfilerScope.of(definition.getName());
                        try {
                            rendered = renderItem(stack, player, playerModel, definition, gameUserItem, user, firstPerson, packedLight, partialTicks);
                            if (scope != null) {
                                scope.close();
                            }
                        } catch (Throwable th) {
                            if (scope != null) {
                                try {
                                    scope.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable throwable) {
                        LOGGER.error("Item Rendering (" + definition.getName() + ")", throwable);
                        rendered = false;
                    }
                    if (rendered) {
                        renderedCosmetics++;
                        if (definition.cosmeticType() == CosmeticType.MINECRAFT_ITEM) {
                            this.renderedCustomMinecraftItem = true;
                        }
                    }
                }
            }
            FrameProfiler.pop();
            resetValues();
        } catch (Throwable th3) {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th4) {
                    th3.addSuppressed(th4);
                }
            }
            throw th3;
        }
    }

    public boolean isRenderedCustomMinecraftItem() {
        return this.renderedCustomMinecraftItem;
    }

    private void resetValues() {
        this.hand = null;
        this.itemFilter = ItemFilters.defaultFilter();
        clearTags();
    }

    private boolean renderItem(Stack stack, Player player, PlayerModel playerModel, CosmeticDefinition definition, GameUserItem gameUserItem, DefaultGameUser user, boolean firstPerson, int lightCoords, float partialTicks) {
        ProfilerScope ignored;
        ResourceLocation resourceLocation;
        ItemMetadata metadata = gameUserItem.itemMetadata();
        CosmeticRenderer cosmeticRenderer = this.rendererRegistry.getRenderer(definition);
        if (cosmeticRenderer != null) {
            this.lifecycleManager.queueLoad(definition, player);
        }
        CosmeticDetails details = metadata.getDetails();
        if (firstPerson && cosmeticRenderer != null && !cosmeticRenderer.isVisibleInFirstPerson(definition)) {
            return false;
        }
        if (definition.cosmeticType() == CosmeticType.LEGACY) {
            resourceLocation = Textures.WHITE;
        } else {
            CosmeticState cosmeticState = user.getCosmeticStateStorage().getState(definition.id());
            if (cosmeticState == null) {
                return false;
            }
            ignored = ProfilerScope.of("texture_resolve");
            try {
                resourceLocation = this.textureProvider.resolveTexture(definition, cosmeticState, player, metadata);
                if (ignored != null) {
                    ignored.close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        if (resourceLocation == null) {
            return false;
        }
        Model model = definition.itemModel() != null ? definition.itemModel().getModel() : null;
        if (model == null && definition.cosmeticType() != CosmeticType.LEGACY) {
            return false;
        }
        stack.push();
        boolean sneaking = player.isCrouching();
        if (sneaking && !firstPerson && PlatformEnvironment.isAncientOpenGL()) {
            switch (AnonymousClass1.$SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[details.getAttachmentPoint().ordinal()]) {
                case 2:
                case 3:
                case 4:
                case 5:
                    stack.translate(0.0f, 0.2f, 0.0f);
                    break;
                case 6:
                    stack.translate(0.0f, LEG_SNEAK_OFFSET, 0.0f);
                    break;
            }
        }
        TaggedObject layerTags = taggedObject();
        if (layerTags.hasTag(ItemTags.ACTIVITY)) {
            definition.setTag(ItemTags.ACTIVITY);
        }
        if (firstPerson) {
            definition.setTag(ItemTags.FIRST_PERSON);
        }
        ignored = ProfilerScope.of("render_call");
        try {
            RenderContext renderContext = new RenderContext(player, playerModel, user, metadata, firstPerson, this.hand, this.physicData, resourceLocation, partialTicks, lightCoords, RenderEnvironmentContext.NO_OVERLAY);
            if (cosmeticRenderer != null) {
                if (!cosmeticRenderer.shouldRender(definition, renderContext)) {
                    stack.pop();
                    if (ignored != null) {
                        ignored.close();
                    }
                    return false;
                }
                cosmeticRenderer.render(definition, renderContext, stack);
                if (ignored != null) {
                    ignored.close();
                }
                RenderProfiler.increaseRenderedCosmeticCount();
                stack.pop();
                return true;
            }
            stack.pop();
            if (ignored != null) {
                ignored.close();
            }
            return false;
        } finally {
            if (ignored != null) {
                try {
                    ignored.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
        }
    }

    /* JADX INFO: renamed from: net.labymod.core.main.user.shop.bridge.ShopItemLayer$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/user/shop/bridge/ShopItemLayer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint = new int[AttachmentPoint.values().length];

        static {
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.BODY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.HEAD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.ARM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$laby$lib$cosmetics$AttachmentPoint[AttachmentPoint.LEG.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    public void resetAnimations(GameUser user, boolean screenContext) {
        RenderEnvironmentContext context = Laby.labyAPI().gfxRenderPipeline().renderEnvironmentContext();
        boolean prevScreenContext = context.isScreenContext();
        context.setScreenContext(screenContext);
        DefaultGameUser gameUser = (DefaultGameUser) user;
        for (GameUserItem userItem : gameUser.getUserData().getItems()) {
            CosmeticDefinition definition = userItem.definition();
            CosmeticState state = gameUser.getCosmeticStateStorage().getState(definition.id());
            if (state != null) {
                state.animationStorage().getController().stop();
            }
        }
        context.setScreenContext(prevScreenContext);
    }

    private boolean isSelfOrFriend(Player player) {
        boolean isSelf = player.gameUser() == this.gameUserService.clientGameUser();
        if (isSelf) {
            return true;
        }
        boolean onlyFriends = this.cosmeticsConfig.onlyFriends().get().booleanValue();
        if (onlyFriends) {
            LabyConnectSession session = this.labyConnect.getSession();
            if (session == null) {
                return false;
            }
            Friend friend = session.getFriend(player.getUniqueId());
            return friend != null;
        }
        return true;
    }

    @Override // net.labymod.api.tag.Taggable
    public TaggedObject taggedObject() {
        return this.taggedObject;
    }
}
