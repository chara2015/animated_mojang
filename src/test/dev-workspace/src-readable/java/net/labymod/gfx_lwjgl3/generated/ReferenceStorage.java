package net.labymod.gfx_lwjgl3.generated;

import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.format.numbers.NumberFormatMapper;
import net.labymod.api.client.crash.GameCrashReport;
import net.labymod.api.client.entity.EntityPoseMapper;
import net.labymod.api.client.entity.EntityVisualizer;
import net.labymod.api.client.gfx.GFXBridge;
import net.labymod.api.client.gfx.imgui.ImGuiAccessor;
import net.labymod.api.client.gfx.imgui.type.ImGuiTypeProvider;
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
import net.labymod.api.reference.Reference;
import net.labymod.api.reference.ReferenceStorageAccessor;
import net.labymod.api.reference.SingletonReference;
import net.labymod.api.server.IntegratedServer;
import net.labymod.api.util.FileDialogs;
import net.labymod.api.util.InjectionNames;
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
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.backend.lwjgl3.util.LWJGL3FileDialogs;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.DefaultImGuiAccessor;
import net.labymod.gfx_lwjgl3.client.gfx.pipeline.imgui.type.DefaultImGuiTypeProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/gfx_lwjgl3/generated/ReferenceStorage.class */
public class ReferenceStorage extends DefaultReferenceStorage implements ReferenceStorageAccessor {
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullJsonResourceTransformer();
    });
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullFragmentShaderResourceTransformer();
    });
    private final Reference<ResourceTransformer> resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader = new SingletonReference(ResourceTransformer.class, () -> {
        return new DamageOverlayRenderTypeArmorCutoutNoCullVertexShaderResourceTransformer();
    });
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencevanilla_hover_effect = new SingletonReference(HoverBackgroundEffect.class, () -> {
        return new VanillaHoverBackgroundEffect();
    });
    private final Reference<HoverBackgroundEffect> hoverBackgroundEffectReferencefancy_hover_effect = new SingletonReference(HoverBackgroundEffect.class, () -> {
        return new FancyHoverBackgroundEffect();
    });
    private final Reference<ImGuiTypeProvider> imGuiTypeProviderReference = new SingletonReference(ImGuiTypeProvider.class, () -> {
        return new DefaultImGuiTypeProvider();
    });
    private final Reference<ImGuiAccessor> imGuiAccessorReference = new SingletonReference(ImGuiAccessor.class, () -> {
        return new DefaultImGuiAccessor(textureRepository());
    });
    private final Reference<FileDialogs> fileDialogsReference = new SingletonReference(FileDialogs.class, () -> {
        return new LWJGL3FileDialogs();
    });

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public MojangTextureService mojangTextureService() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public SessionAccessor sessionAccessor() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public MinecraftAuthenticator minecraftAuthenticator() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GameCrashReport.Factory gameCrashReportFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ComponentService componentService() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @Nullable
    public NumberFormatMapper getNumberFormatMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public SystemInfo systemInfo() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ScreenWrapper.Factory screenWrapperFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public KeyMapper keyMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ResourceLocationFactory resourceLocationFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ResourceTransformer resourceTransformer(String key) {
        if (key.equals(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_JSON)) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_json.get();
        }
        if (key.equals(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_FRAGMENT_SHADER)) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_fragment_shader.get();
        }
        if (key.equals(InjectionNames.DAMAGE_OVERLAY_RENDERTYPE_ARMOR_CUTOUT_NO_CULL_VERTEX_SHADER)) {
            return (ResourceTransformer) this.resourceTransformerReferencedamage_overlay_rendertype_armor_cutout_no_cull_vertex_shader.get();
        }
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public MinecraftSounds minecraftSounds() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GameImageProvider gameImageProvider() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public TextureRepository textureRepository() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ResourcePackRepository resourcePackRepository() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ResourcePackScanner resourcePackScanner() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ItemStackVisualizer itemStackVisualizer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public RenderConstants renderConstants() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ComponentMapper componentMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public HoverBackgroundEffect hoverBackgroundEffect(String key) {
        if (key.equals(InjectionNames.VANILLA_HOVER_EFFECT)) {
            return (HoverBackgroundEffect) this.hoverBackgroundEffectReferencevanilla_hover_effect.get();
        }
        if (key.equals(InjectionNames.FANCY_HOVER_EFFECT)) {
            return (HoverBackgroundEffect) this.hoverBackgroundEffectReferencefancy_hover_effect.get();
        }
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public StackProviderFactory stackProviderFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public RenderPipeline renderPipeline() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ChatExecutor chatExecutor() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ImGuiTypeProvider imGuiTypeProvider() {
        return (ImGuiTypeProvider) this.imGuiTypeProviderReference.get();
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ImGuiAccessor imGuiAccessor() {
        return (ImGuiAccessor) this.imGuiAccessorReference.get();
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public MinecraftAtlases minecraftAtlases() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public Blaze3DGlStatePipeline blaze3DGlStatePipeline() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public MinecraftFontRenderer minecraftFontRenderer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public FormattedTextLayout.Factory formattedTextLayoutFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public VanillaGlyphStorage vanillaGlyphStorage() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GFXBridge gfxBridge() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public EntityVisualizer entityVisualizer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public EntityPoseMapper entityPoseMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ServerController serverController() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ServerAddressResolver serverAddressResolver() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ParticleController particleController() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public LightingLayerMapper lightingLayerMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ClientWorld clientWorld() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public BlockColorProvider blockColorProvider() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public BlockProperties blockProperties() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public Blocks blocks() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public HitResultController hitResultController() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public BossBarRegistry bossBarRegistry() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public ItemStackFactory itemStackFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public FileDialogs fileDialogs() {
        return (FileDialogs) this.fileDialogsReference.get();
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GameMathMapper gameMathMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public IntegratedServer integratedServer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GameBufferProvider gameBufferProvider() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @Nullable
    public DirtyFramebufferClearer getDirtyFramebufferClearer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public GameTransformations gameTransformations() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public RenderBufferSource renderBufferSource() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public Laby3D laby3D() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public RenderFirstPersonItemInHandEvent.AnimationTypeMapper renderFirstPersonItemInHandEventAnimationTypeMapper() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage, net.labymod.api.generated.ReferenceStorage
    @NotNull
    public NBTFactory nbtFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public UserFactory userFactory() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public BufferSourceGui bufferSourceGui() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public FontSetGlyphProvider fontSetGlyphProvider() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public PanoramaRenderer panoramaRenderer() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public ClientNetworkPacketListener clientNetworkPacketListener() {
        return null;
    }

    @Override // net.labymod.core.generated.DefaultReferenceStorage
    @NotNull
    public InternalChatModifier internalChatModifier() {
        return null;
    }
}
