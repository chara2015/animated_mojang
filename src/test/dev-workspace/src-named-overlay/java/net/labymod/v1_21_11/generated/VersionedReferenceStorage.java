package net.labymod.v1_21_11.generated;

import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.pipeline.Blaze3DGlStatePipeline;
import net.labymod.api.client.gfx.pipeline.renderer.text.FormattedTextLayout;
import net.labymod.api.client.gfx.pipeline.renderer.text.MinecraftFontRenderer;
import net.labymod.api.client.gfx.pipeline.renderer.text.glyph.VanillaGlyphStorage;
import net.labymod.api.client.gfx.pipeline.texture.atlas.MinecraftAtlases;
import net.labymod.api.client.gui.screen.ScreenWrapper;
import net.labymod.api.client.gui.screen.key.mapper.KeyMapper;
import net.labymod.api.client.network.server.ServerAddressResolver;
import net.labymod.api.client.network.server.ServerController;
import net.labymod.api.client.particle.ParticleController;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.render.RenderConstants;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.draw.hover.HoverBackgroundEffect;
import net.labymod.api.client.render.font.ComponentMapper;
import net.labymod.api.client.render.matrix.StackProviderFactory;
import net.labymod.api.client.resources.ResourceLocationFactory;
import net.labymod.api.client.resources.pack.ResourcePackRepository;
import net.labymod.api.client.resources.pack.ResourcePackScanner;
import net.labymod.api.client.resources.sound.MinecraftSounds;
import net.labymod.api.client.resources.texture.GameImageProvider;
import net.labymod.api.client.resources.texture.TextureRepository;
import net.labymod.api.client.resources.transform.ResourceTransformer;
import net.labymod.api.client.session.MinecraftAuthenticator;
import net.labymod.api.client.session.SessionAccessor;
import net.labymod.api.client.util.SystemInfo;
import net.labymod.api.client.world.BossBarRegistry;
import net.labymod.api.client.world.ClientWorld;
import net.labymod.api.client.world.block.BlockColorProvider;
import net.labymod.api.client.world.block.Blocks;
import net.labymod.api.client.world.block.properties.BlockProperties;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.lighting.LightingLayerMapper;
import net.labymod.api.client.world.phys.hit.HitResultController;
import net.labymod.api.event.client.render.item.RenderFirstPersonItemInHandEvent;
import net.labymod.api.laby3d.GameTransformations;
import net.labymod.api.laby3d.Laby3D;
import net.labymod.api.laby3d.buffer.GameBufferProvider;
import net.labymod.api.laby3d.render.buffer.RenderBufferSource;
import net.labymod.api.laby3d.util.DirtyFramebufferClearer;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.nbt.NBTFactory;
import net.labymod.api.reference.DynamicReference;
import net.labymod.api.reference.NullableSingletonReference;
import net.labymod.api.reference.Reference;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.reference.SingletonReference;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.service.annotation.AutoService;
import net.labymod.api.util.math.GameMathMapper;
import net.labymod.core.client.chat.InternalChatModifier;
import net.labymod.core.client.font.text.FontSetGlyphProvider;
import net.labymod.core.client.gui.background.panorama.PanoramaRenderer;
import net.labymod.core.client.multiplayer.ClientNetworkPacketListener;
import net.labymod.core.client.render.draw.hover.FancyHoverBackgroundEffect;
import net.labymod.core.client.render.draw.hover.VanillaHoverBackgroundEffect;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer;
import net.labymod.core.client.resources.transform.transformer.customhitcolor.DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer;
import net.labymod.core.client.session.UserFactory;
import net.labymod.core.client.util.buffersource.BufferSourceGui;
import net.labymod.gfx_lwjgl3.generated.ReferenceStorage;
import net.labymod.v1_21_11.client.chat.VersionedChatExecutor;
import net.labymod.v1_21_11.client.chat.VersionedChatModifier;
import net.labymod.v1_21_11.client.component.VersionedComponentService;
import net.labymod.v1_21_11.client.component.format.numbers.VersionedNumberFormatMapper;
import net.labymod.v1_21_11.client.crash.VersionedGameCrashReportFactory;
import net.labymod.v1_21_11.client.entity.VersionedEntityPoseMapper;
import net.labymod.v1_21_11.client.entity.VersionedEntityVisualizer;
import net.labymod.v1_21_11.client.font.component.VersionedComponentMapper;
import net.labymod.v1_21_11.client.gfx.VersionedGFXBridge;
import net.labymod.v1_21_11.client.gfx.pipeline.VersionedBlaze3DGlStatePipeline;
import net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.VanillaFontRenderer;
import net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.VanillaFormattedTextLayoutFactory;
import net.labymod.v1_21_11.client.gfx.pipeline.renderer.text.VersionedVanillaGlyphStorage;
import net.labymod.v1_21_11.client.gfx.pipeline.texture.atlas.VersionedMinecraftAtlases;
import net.labymod.v1_21_11.client.gui.background.VersionedPanoramaRenderer;
import net.labymod.v1_21_11.client.gui.screen.VersionedScreenWrapperFactory;
import net.labymod.v1_21_11.client.gui.screen.key.mapper.VersionedKeyMapper;
import net.labymod.v1_21_11.client.multiplayer.server.VersionedClientNetworkPacketListener;
import net.labymod.v1_21_11.client.multiplayer.server.VersionedServerController;
import net.labymod.v1_21_11.client.particle.VersionedParticleController;
import net.labymod.v1_21_11.client.render.VersionedItemStackVisualizer;
import net.labymod.v1_21_11.client.render.VersionedRenderConstants;
import net.labymod.v1_21_11.client.render.VersionedRenderPipeline;
import net.labymod.v1_21_11.client.render.item.VersionedAnimationTypeMapper;
import net.labymod.v1_21_11.client.render.matrix.VersionedStackProviderFactory;
import net.labymod.v1_21_11.client.resources.VersionedResourceLocationFactory;
import net.labymod.v1_21_11.client.resources.pack.VersionedResourcePackRepository;
import net.labymod.v1_21_11.client.resources.pack.VersionedResourcePackScanner;
import net.labymod.v1_21_11.client.resources.sound.VersionedMinecraftSounds;
import net.labymod.v1_21_11.client.resources.texture.NativeGameImageProvider;
import net.labymod.v1_21_11.client.resources.texture.VersionedTextureRepository;
import net.labymod.v1_21_11.client.session.VersionedMinecraftAuthenticator;
import net.labymod.v1_21_11.client.session.VersionedSessionAccessor;
import net.labymod.v1_21_11.client.session.user.VersionedUserFactory;
import net.labymod.v1_21_11.client.util.VersionedSystemInfo;
import net.labymod.v1_21_11.client.util.math.VersionedGameMathMapper;
import net.labymod.v1_21_11.client.world.VersionedBossBarRegistry;
import net.labymod.v1_21_11.client.world.VersionedClientWorld;
import net.labymod.v1_21_11.client.world.block.VersionedBlockColorProvider;
import net.labymod.v1_21_11.client.world.block.properties.VersionedBlockProperties;
import net.labymod.v1_21_11.client.world.item.VersionedItemStackFactory;
import net.labymod.v1_21_11.client.world.lighting.VersionedLightingLayerMapper;
import net.labymod.v1_21_11.client.world.phys.hit.VersionedHitResultController;
import net.labymod.v1_21_11.laby3d.VersionedGameTransformations;
import net.labymod.v1_21_11.laby3d.VersionedLaby3D;
import net.labymod.v1_21_11.laby3d.buffer.VersionedGameBufferProvider;
import net.labymod.v1_21_11.laby3d.render.buffer.VersionedRenderBufferSource;
import net.labymod.v1_21_11.mojang.texture.VersionedMojangTextureService;
import net.labymod.v1_21_11.nbt.VersionedNBTFactory;
import net.labymod.v1_21_11.server.VersionedIntegratedServer;
import net.labymod.v1_21_11.server.VersionedServerAddressResolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/generated/VersionedReferenceStorage.class */
@AutoService(value = ReferenceStorageAccessor.class, versionSpecific = true)
public class VersionedReferenceStorage extends ReferenceStorage implements ReferenceStorageAccessor {
    private final Reference<MojangTextureService> mojangTextureServiceReference = new SingletonReference(MojangTextureService.class, () -> {
        return new VersionedMojangTextureService(labyAPI());
    });
    private final Reference<SessionAccessor> sessionAccessorReference = new SingletonReference(SessionAccessor.class, () -> {
        return new VersionedSessionAccessor(userFactory());
    });
    private final Reference<MinecraftAuthenticator> minecraftAuthenticatorReference = new SingletonReference(MinecraftAuthenticator.class, () -> {
        return new VersionedMinecraftAuthenticator();
    });
    private final Reference<GameCrashReport.Factory> gameCrashReportFactoryReference = new SingletonReference(GameCrashReport.Factory.class, () -> {
        return new VersionedGameCrashReportFactory();
    });
    private final Reference<ComponentService> componentServiceReference = new SingletonReference(ComponentService.class, () -> {
        return new VersionedComponentService();
    });
    private final Reference<NumberFormatMapper> numberFormatMapperReference = new NullableSingletonReference(NumberFormatMapper.class, () -> {
        return new VersionedNumberFormatMapper(componentMapper());
    });
    private final Reference<SystemInfo> systemInfoReference = new SingletonReference(SystemInfo.class, () -> {
        return new VersionedSystemInfo();
    });
    private final Reference<ScreenWrapper.Factory> screenWrapperFactoryReference = new SingletonReference(ScreenWrapper.Factory.class, () -> {
        return new VersionedScreenWrapperFactory();
    });
    private final Reference<KeyMapper> keyMapperReference = new SingletonReference(KeyMapper.class, () -> {
        return new VersionedKeyMapper();
    });
    private final Reference<ResourceLocationFactory> resourceLocationFactoryReference = new SingletonReference(ResourceLocationFactory.class, () -> {
        return new VersionedResourceLocationFactory();
    });
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer();
    });
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer();
    });
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer();
    });
    private final Reference<MinecraftSounds> minecraftSoundsReference = new SingletonReference(MinecraftSounds.class, () -> {
        return new VersionedMinecraftSounds();
    });
    private final Reference<GameImageProvider> gameImageProviderReference = new SingletonReference(GameImageProvider.class, () -> {
        return new NativeGameImageProvider();
    });
    private final Reference<TextureRepository> textureRepositoryReference = new SingletonReference(TextureRepository.class, () -> {
        return new VersionedTextureRepository(labyAPI(), gameImageProvider());
    });
    private final Reference<ResourcePackRepository> resourcePackRepositoryReference = new SingletonReference(ResourcePackRepository.class, () -> {
        return new VersionedResourcePackRepository();
    });
    private final Reference<ResourcePackScanner> resourcePackScannerReference = new SingletonReference(ResourcePackScanner.class, () -> {
        return new VersionedResourcePackScanner();
    });
    private final Reference<ItemStackVisualizer> itemStackVisualizerReference = new SingletonReference(ItemStackVisualizer.class, () -> {
        return new VersionedItemStackVisualizer();
    });
    private final Reference<RenderConstants> renderConstantsReference = new SingletonReference(RenderConstants.class, () -> {
        return new VersionedRenderConstants();
    });
    private final Reference<ComponentMapper> componentMapperReference = new DynamicReference(ComponentMapper.class, () -> {
        return new VersionedComponentMapper();
    });
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencevanilla_hover_effect = new SingletonReference(HoverBackgroundEffect.class, () -> {
        return new VanillaHoverBackgroundEffect();
    });
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencefancy_hover_effect = new SingletonReference(HoverBackgroundEffect.class, () -> {
        return new FancyHoverBackgroundEffect();
    });
    private final Reference<StackProviderFactory> stackProviderFactoryReference = new SingletonReference(StackProviderFactory.class, () -> {
        return new VersionedStackProviderFactory();
    });
    private final Reference<RenderPipeline> renderPipelineReference = new SingletonReference(RenderPipeline.class, () -> {
        return new VersionedRenderPipeline();
    });
    private final Reference<ChatExecutor> chatExecutorReference = new SingletonReference(ChatExecutor.class, () -> {
        return new VersionedChatExecutor(componentMapper());
    });
    private final Reference<MinecraftAtlases> minecraftAtlasesReference = new SingletonReference(MinecraftAtlases.class, () -> {
        return new VersionedMinecraftAtlases();
    });
    private final Reference<Blaze3DGlStatePipeline> blaze3DGlStatePipelineReference = new SingletonReference(Blaze3DGlStatePipeline.class, () -> {
        return new VersionedBlaze3DGlStatePipeline();
    });
    private final Reference<MinecraftFontRenderer> minecraftFontRendererReference = new SingletonReference(MinecraftFontRenderer.class, () -> {
        return new VanillaFontRenderer(componentMapper());
    });
    private final Reference<FormattedTextLayout.Factory> formattedTextLayoutFactoryReference = new SingletonReference(FormattedTextLayout.Factory.class, () -> {
        return new VanillaFormattedTextLayoutFactory(componentMapper());
    });
    private final Reference<VanillaGlyphStorage> vanillaGlyphStorageReference = new SingletonReference(VanillaGlyphStorage.class, () -> {
        return new VersionedVanillaGlyphStorage();
    });
    private final Reference<GFXBridge> gfxBridgeReference = new SingletonReference(GFXBridge.class, () -> {
        return new VersionedGFXBridge(blaze3DGlStatePipeline());
    });
    private final Reference<EntityVisualizer> entityVisualizerReference = new SingletonReference(EntityVisualizer.class, () -> {
        return new VersionedEntityVisualizer();
    });
    private final Reference<EntityPoseMapper> entityPoseMapperReference = new SingletonReference(EntityPoseMapper.class, () -> {
        return new VersionedEntityPoseMapper();
    });
    private final Reference<ServerController> serverControllerReference = new SingletonReference(ServerController.class, () -> {
        return new VersionedServerController(labyAPI(), labyModProtocolService(), permissionRegistry(), resourceLocationFactory(), componentMapper());
    });
    private final Reference<ServerAddressResolver> serverAddressResolverReference = new SingletonReference(ServerAddressResolver.class, () -> {
        return new VersionedServerAddressResolver();
    });
    private final Reference<ParticleController> particleControllerReference = new SingletonReference(ParticleController.class, () -> {
        return new VersionedParticleController();
    });
    private final Reference<LightingLayerMapper> lightingLayerMapperReference = new SingletonReference(LightingLayerMapper.class, () -> {
        return new VersionedLightingLayerMapper();
    });
    private final Reference<ClientWorld> clientWorldReference = new SingletonReference(ClientWorld.class, () -> {
        return new VersionedClientWorld(resourceLocationFactory());
    });
    private final Reference<BlockColorProvider> blockColorProviderReference = new SingletonReference(BlockColorProvider.class, () -> {
        return new VersionedBlockColorProvider();
    });
    private final Reference<BlockProperties> blockPropertiesReference = new SingletonReference(BlockProperties.class, () -> {
        return new VersionedBlockProperties();
    });
    private final Reference<HitResultController> hitResultControllerReference = new SingletonReference(HitResultController.class, () -> {
        return new VersionedHitResultController();
    });
    private final Reference<BossBarRegistry> bossBarRegistryReference = new SingletonReference(BossBarRegistry.class, () -> {
        return new VersionedBossBarRegistry(labyAPI());
    });
    private final Reference<ItemStackFactory> itemStackFactoryReference = new SingletonReference(ItemStackFactory.class, () -> {
        return new VersionedItemStackFactory();
    });
    private final Reference<GameMathMapper> gameMathMapperReference = new SingletonReference(GameMathMapper.class, () -> {
        return new VersionedGameMathMapper();
    });
    private final Reference<IntegratedServer> integratedServerReference = new SingletonReference(IntegratedServer.class, () -> {
        return new VersionedIntegratedServer();
    });
    private final Reference<GameBufferProvider> gameBufferProviderReference = new SingletonReference(GameBufferProvider.class, () -> {
        return new VersionedGameBufferProvider();
    });
    private final Reference<GameTransformations> gameTransformationsReference = new SingletonReference(GameTransformations.class, () -> {
        return new VersionedGameTransformations();
    });
    private final Reference<RenderBufferSource> renderBufferSourceReference = new SingletonReference(RenderBufferSource.class, () -> {
        return new VersionedRenderBufferSource();
    });
    private final Reference<Laby3D> laby3DReference = new SingletonReference(Laby3D.class, () -> {
        return new VersionedLaby3D(gameTransformations());
    });
    private final Reference<RenderFirstPersonItemInHandEvent.AnimationTypeMapper> renderFirstPersonItemInHandEventAnimationTypeMapperReference = new SingletonReference(RenderFirstPersonItemInHandEvent.AnimationTypeMapper.class, () -> {
        return new VersionedAnimationTypeMapper();
    });
    private final Reference<NBTFactory> nbtFactoryReference = new DynamicReference(NBTFactory.class, () -> {
        return new VersionedNBTFactory();
    });
    private final Reference<UserFactory> userFactoryReference = new SingletonReference(UserFactory.class, () -> {
        return new VersionedUserFactory();
    });
    private final Reference<PanoramaRenderer> panoramaRendererReference = new SingletonReference(PanoramaRenderer.class, () -> {
        return new VersionedPanoramaRenderer(labyAPI());
    });
    private final Reference<ClientNetworkPacketListener> clientNetworkPacketListenerReference = new SingletonReference(ClientNetworkPacketListener.class, () -> {
        return new VersionedClientNetworkPacketListener(serverController(), oldAnimationRegistry(), serverFeatureService());
    });
    private final Reference<InternalChatModifier> internalChatModifierReference = new SingletonReference(InternalChatModifier.class, () -> {
        return new VersionedChatModifier(componentMapper());
    });

    @NotNull
    public MojangTextureService mojangTextureService() {
        return (MojangTextureService) this.mojangTextureServiceReference.get();
    }

    @NotNull
    public SessionAccessor sessionAccessor() {
        return (SessionAccessor) this.sessionAccessorReference.get();
    }

    @NotNull
    public MinecraftAuthenticator minecraftAuthenticator() {
        return (MinecraftAuthenticator) this.minecraftAuthenticatorReference.get();
    }

    @NotNull
    public GameCrashReport.Factory gameCrashReportFactory() {
        return (GameCrashReport.Factory) this.gameCrashReportFactoryReference.get();
    }

    @NotNull
    public ComponentService componentService() {
        return (ComponentService) this.componentServiceReference.get();
    }

    @Nullable
    public NumberFormatMapper getNumberFormatMapper() {
        return (NumberFormatMapper) this.numberFormatMapperReference.get();
    }

    @NotNull
    public SystemInfo systemInfo() {
        return (SystemInfo) this.systemInfoReference.get();
    }

    @NotNull
    public ScreenWrapper.Factory screenWrapperFactory() {
        return (ScreenWrapper.Factory) this.screenWrapperFactoryReference.get();
    }

    @NotNull
    public KeyMapper keyMapper() {
        return (KeyMapper) this.keyMapperReference.get();
    }

    @NotNull
    public ResourceLocationFactory resourceLocationFactory() {
        return (ResourceLocationFactory) this.resourceLocationFactoryReference.get();
    }

    @NotNull
    public ResourceTransformer resourceTransformer(String key) {
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_json")) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json.get();
        }
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_fragment_shader")) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader.get();
        }
        if (key.equals("damage_overlay_rendertype_armor_cutout_no_cull_vertex_shader")) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader.get();
        }
        return null;
    }

    @NotNull
    public MinecraftSounds minecraftSounds() {
        return (MinecraftSounds) this.minecraftSoundsReference.get();
    }

    @NotNull
    public GameImageProvider gameImageProvider() {
        return (GameImageProvider) this.gameImageProviderReference.get();
    }

    @NotNull
    public TextureRepository textureRepository() {
        return (TextureRepository) this.textureRepositoryReference.get();
    }

    @NotNull
    public ResourcePackRepository resourcePackRepository() {
        return (ResourcePackRepository) this.resourcePackRepositoryReference.get();
    }

    @NotNull
    public ResourcePackScanner resourcePackScanner() {
        return (ResourcePackScanner) this.resourcePackScannerReference.get();
    }

    @NotNull
    public ItemStackVisualizer itemStackVisualizer() {
        return (ItemStackVisualizer) this.itemStackVisualizerReference.get();
    }

    @NotNull
    public RenderConstants renderConstants() {
        return (RenderConstants) this.renderConstantsReference.get();
    }

    @NotNull
    public ComponentMapper componentMapper() {
        return (ComponentMapper) this.componentMapperReference.get();
    }

    @NotNull
    public HoverBackgroundEffect hoverBackgroundEffect(String key) {
        if (key.equals("vanilla_hover_effect")) {
            return (HoverBackgroundEffect) this.hoverBackgroundEffectReferencevanilla_hover_effect.get();
        }
        if (key.equals("fancy_hover_effect")) {
            return (HoverBackgroundEffect) this.hoverBackgroundEffectReferencefancy_hover_effect.get();
        }
        return null;
    }

    @NotNull
    public StackProviderFactory stackProviderFactory() {
        return (StackProviderFactory) this.stackProviderFactoryReference.get();
    }

    @NotNull
    public RenderPipeline renderPipeline() {
        return (RenderPipeline) this.renderPipelineReference.get();
    }

    @NotNull
    public ChatExecutor chatExecutor() {
        return (ChatExecutor) this.chatExecutorReference.get();
    }

    @NotNull
    public MinecraftAtlases minecraftAtlases() {
        return (MinecraftAtlases) this.minecraftAtlasesReference.get();
    }

    @NotNull
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return (Blaze3DGlStatePipeline) this.blaze3DGlStatePipelineReference.get();
    }

    @NotNull
    public MinecraftFontRenderer minecraftFontRenderer() {
        return (MinecraftFontRenderer) this.minecraftFontRendererReference.get();
    }

    @NotNull
    public FormattedTextLayout.Factory formattedTextLayoutFactory() {
        return (FormattedTextLayout.Factory) this.formattedTextLayoutFactoryReference.get();
    }

    @NotNull
    public VanillaGlyphStorage vanillaGlyphStorage() {
        return (VanillaGlyphStorage) this.vanillaGlyphStorageReference.get();
    }

    @NotNull
    public GFXBridge gfxBridge() {
        return (GFXBridge) this.gfxBridgeReference.get();
    }

    @NotNull
    public EntityVisualizer entityVisualizer() {
        return (EntityVisualizer) this.entityVisualizerReference.get();
    }

    @NotNull
    public EntityPoseMapper entityPoseMapper() {
        return (EntityPoseMapper) this.entityPoseMapperReference.get();
    }

    @NotNull
    public ServerController serverController() {
        return (ServerController) this.serverControllerReference.get();
    }

    @NotNull
    public ServerAddressResolver serverAddressResolver() {
        return (ServerAddressResolver) this.serverAddressResolverReference.get();
    }

    @NotNull
    public ParticleController particleController() {
        return (ParticleController) this.particleControllerReference.get();
    }

    @NotNull
    public LightingLayerMapper lightingLayerMapper() {
        return (LightingLayerMapper) this.lightingLayerMapperReference.get();
    }

    @NotNull
    public ClientWorld clientWorld() {
        return (ClientWorld) this.clientWorldReference.get();
    }

    @NotNull
    public BlockColorProvider blockColorProvider() {
        return (BlockColorProvider) this.blockColorProviderReference.get();
    }

    @NotNull
    public BlockProperties blockProperties() {
        return (BlockProperties) this.blockPropertiesReference.get();
    }

    @NotNull
    public Blocks blocks() {
        return null;
    }

    @NotNull
    public HitResultController hitResultController() {
        return (HitResultController) this.hitResultControllerReference.get();
    }

    @NotNull
    public BossBarRegistry bossBarRegistry() {
        return (BossBarRegistry) this.bossBarRegistryReference.get();
    }

    @NotNull
    public ItemStackFactory itemStackFactory() {
        return (ItemStackFactory) this.itemStackFactoryReference.get();
    }

    @NotNull
    public GameMathMapper gameMathMapper() {
        return (GameMathMapper) this.gameMathMapperReference.get();
    }

    @NotNull
    public IntegratedServer integratedServer() {
        return (IntegratedServer) this.integratedServerReference.get();
    }

    @NotNull
    public GameBufferProvider gameBufferProvider() {
        return (GameBufferProvider) this.gameBufferProviderReference.get();
    }

    @Nullable
    public DirtyFramebufferClearer getDirtyFramebufferClearer() {
        return null;
    }

    @NotNull
    public GameTransformations gameTransformations() {
        return (GameTransformations) this.gameTransformationsReference.get();
    }

    @NotNull
    public RenderBufferSource renderBufferSource() {
        return (RenderBufferSource) this.renderBufferSourceReference.get();
    }

    @NotNull
    public Laby3D laby3D() {
        return (Laby3D) this.laby3DReference.get();
    }

    @NotNull
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return (RenderFirstPersonItemInHandEvent.AnimationTypeMapper) this.renderFirstPersonItemInHandEventAnimationTypeMapperReference.get();
    }

    @NotNull
    public NBTFactory nbtFactory() {
        return (NBTFactory) this.nbtFactoryReference.get();
    }

    @NotNull
    public UserFactory userFactory() {
        return (UserFactory) this.userFactoryReference.get();
    }

    @NotNull
    public BufferSourceGui bufferSourceGui() {
        return null;
    }

    @NotNull
    public FontSetGlyphProvider fontSetGlyphProvider() {
        return null;
    }

    @NotNull
    public PanoramaRenderer panoramaRenderer() {
        return (PanoramaRenderer) this.panoramaRendererReference.get();
    }

    @NotNull
    public ClientNetworkPacketListener clientNetworkPacketListener() {
        return (ClientNetworkPacketListener) this.clientNetworkPacketListenerReference.get();
    }

    @NotNull
    public InternalChatModifier internalChatModifier() {
        return (InternalChatModifier) this.internalChatModifierReference.get();
    }

    public boolean isVersion() {
        return true;
    }
}
