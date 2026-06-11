package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import com.mojang.blaze3d.textures.TextureFormat;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalInt;
import net.minecraft.SharedConstants;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.lwjgl.system.MemoryUtil;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/renderer/texture/TextureAtlas.class */
public class TextureAtlas extends AbstractTexture implements Dumpable, TickableTexture {
    private static final Logger LOGGER = LogUtils.getLogger();

    @Deprecated
    public static final Identifier LOCATION_BLOCKS = Identifier.withDefaultNamespace("textures/atlas/blocks.png");

    @Deprecated
    public static final Identifier LOCATION_ITEMS = Identifier.withDefaultNamespace("textures/atlas/items.png");

    @Deprecated
    public static final Identifier LOCATION_PARTICLES = Identifier.withDefaultNamespace("textures/atlas/particles.png");
    private TextureAtlasSprite missingSprite;
    private final Identifier location;
    private int width;
    private int height;
    private int maxMipLevel;
    private int mipLevelCount;
    private GpuBuffer spriteUbos;
    private List<TextureAtlasSprite> sprites = List.of();
    private List<SpriteContents.AnimationState> animatedTexturesStates = List.of();
    private Map<Identifier, TextureAtlasSprite> texturesByName = Map.of();
    private GpuTextureView[] mipViews = new GpuTextureView[0];
    private final int maxSupportedTextureSize = RenderSystem.getDevice().getMaxTextureSize();

    public TextureAtlas(Identifier $$0) {
        this.location = $$0;
    }

    private void createTexture(int $$0, int $$1, int $$2) {
        LOGGER.info("Created: {}x{}x{} {}-atlas", new Object[]{Integer.valueOf($$0), Integer.valueOf($$1), Integer.valueOf($$2), this.location});
        GpuDevice $$3 = RenderSystem.getDevice();
        close();
        Identifier identifier = this.location;
        Objects.requireNonNull(identifier);
        this.texture = $$3.createTexture(identifier::toString, 15, TextureFormat.RGBA8, $$0, $$1, 1, $$2 + 1);
        this.textureView = $$3.createTextureView(this.texture);
        this.width = $$0;
        this.height = $$1;
        this.maxMipLevel = $$2;
        this.mipLevelCount = $$2 + 1;
        this.mipViews = new GpuTextureView[this.mipLevelCount];
        for (int $$4 = 0; $$4 <= this.maxMipLevel; $$4++) {
            this.mipViews[$$4] = $$3.createTextureView(this.texture, $$4, 1);
        }
    }

    public void upload(SpriteLoader.Preparations $$0) {
        createTexture($$0.width(), $$0.height(), $$0.mipLevel());
        clearTextureData();
        this.sampler = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST);
        this.texturesByName = Map.copyOf($$0.regions());
        this.missingSprite = this.texturesByName.get(MissingTextureAtlasSprite.getLocation());
        if (this.missingSprite == null) {
            throw new IllegalStateException("Atlas '" + String.valueOf(this.location) + "' (" + this.texturesByName.size() + " sprites) has no missing texture sprite");
        }
        List<TextureAtlasSprite> $$1 = new ArrayList<>();
        List<SpriteContents.AnimationState> $$2 = new ArrayList<>();
        int $$3 = (int) $$0.regions().values().stream().filter((v0) -> {
            return v0.isAnimated();
        }).count();
        int $$4 = Mth.roundToward(SpriteContents.UBO_SIZE, RenderSystem.getDevice().getUniformOffsetAlignment());
        int $$5 = $$4 * this.mipLevelCount;
        ByteBuffer $$6 = MemoryUtil.memAlloc($$3 * $$5);
        int $$7 = 0;
        for (TextureAtlasSprite $$8 : $$0.regions().values()) {
            if ($$8.isAnimated()) {
                $$8.uploadSpriteUbo($$6, $$7 * $$5, this.maxMipLevel, this.width, this.height, $$4);
                $$7++;
            }
        }
        GpuBuffer $$9 = $$7 > 0 ? RenderSystem.getDevice().createBuffer(() -> {
            return String.valueOf(this.location) + " sprite UBOs";
        }, 128, $$6) : null;
        int $$72 = 0;
        for (TextureAtlasSprite $$10 : $$0.regions().values()) {
            $$1.add($$10);
            if ($$10.isAnimated() && $$9 != null) {
                SpriteContents.AnimationState $$11 = $$10.createAnimationState($$9.slice($$72 * $$5, $$5), $$4);
                $$72++;
                if ($$11 != null) {
                    $$2.add($$11);
                }
            }
        }
        this.spriteUbos = $$9;
        this.sprites = $$1;
        this.animatedTexturesStates = List.copyOf($$2);
        uploadInitialContents();
        if (SharedConstants.DEBUG_DUMP_TEXTURE_ATLAS) {
            Path $$12 = TextureUtil.getDebugTexturePath();
            try {
                Files.createDirectories($$12, new FileAttribute[0]);
                dumpContents(this.location, $$12);
            } catch (Exception e) {
                LOGGER.warn("Failed to dump atlas contents to {}", $$12);
            }
        }
    }

    private void uploadInitialContents() {
        GpuDevice $$0 = RenderSystem.getDevice();
        int $$1 = Mth.roundToward(SpriteContents.UBO_SIZE, RenderSystem.getDevice().getUniformOffsetAlignment());
        int $$2 = $$1 * this.mipLevelCount;
        GpuSampler $$3 = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.NEAREST, true);
        List<TextureAtlasSprite> $$4 = this.sprites.stream().filter($$02 -> {
            return !$$02.isAnimated();
        }).toList();
        List<GpuTextureView[]> $$5 = new ArrayList<>();
        ByteBuffer $$6 = MemoryUtil.memAlloc($$4.size() * $$2);
        for (int $$7 = 0; $$7 < $$4.size(); $$7++) {
            TextureAtlasSprite $$8 = $$4.get($$7);
            $$8.uploadSpriteUbo($$6, $$7 * $$2, this.maxMipLevel, this.width, this.height, $$1);
            GpuTexture $$9 = $$0.createTexture(() -> {
                return $$8.contents().name().toString();
            }, 5, TextureFormat.RGBA8, $$8.contents().width(), $$8.contents().height(), 1, this.mipLevelCount);
            GpuTextureView[] $$10 = new GpuTextureView[this.mipLevelCount];
            for (int $$11 = 0; $$11 <= this.maxMipLevel; $$11++) {
                $$8.uploadFirstFrame($$9, $$11);
                $$10[$$11] = $$0.createTextureView($$9);
            }
            $$5.add($$10);
        }
        GpuBuffer $$12 = $$0.createBuffer(() -> {
            return "SpriteAnimationInfo";
        }, 128, $$6);
        for (int $$13 = 0; $$13 < this.mipLevelCount; $$13++) {
            try {
                RenderPass $$14 = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> {
                    return "Animate " + String.valueOf(this.location);
                }, this.mipViews[$$13], OptionalInt.empty());
                try {
                    $$14.setPipeline(RenderPipelines.ANIMATE_SPRITE_BLIT);
                    for (int $$15 = 0; $$15 < $$4.size(); $$15++) {
                        $$14.bindTexture("Sprite", $$5.get($$15)[$$13], $$3);
                        $$14.setUniform("SpriteAnimationInfo", $$12.slice(($$15 * $$2) + ($$13 * $$1), SpriteContents.UBO_SIZE));
                        $$14.draw(0, 6);
                    }
                    if ($$14 != null) {
                        $$14.close();
                    }
                } finally {
                }
            } catch (Throwable th) {
                if ($$12 != null) {
                    try {
                        $$12.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                }
                throw th;
            }
        }
        if ($$12 != null) {
            $$12.close();
        }
        for (GpuTextureView[] $$16 : $$5) {
            for (GpuTextureView $$17 : $$16) {
                $$17.close();
                $$17.texture().close();
            }
        }
        MemoryUtil.memFree($$6);
        uploadAnimationFrames();
    }

    @Override // net.minecraft.client.renderer.texture.Dumpable
    public void dumpContents(Identifier $$0, Path $$1) throws IOException {
        String $$2 = $$0.toDebugFileName();
        TextureUtil.writeAsPNG($$1, $$2, getTexture(), this.maxMipLevel, $$02 -> {
            return $$02;
        });
        dumpSpriteNames($$1, $$2, this.texturesByName);
    }

    private static void dumpSpriteNames(Path $$0, String $$1, Map<Identifier, TextureAtlasSprite> $$2) {
        Path $$3 = $$0.resolve($$1 + ".txt");
        try {
            Writer $$4 = Files.newBufferedWriter($$3, new OpenOption[0]);
            try {
                for (Map.Entry<Identifier, TextureAtlasSprite> $$5 : $$2.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
                    TextureAtlasSprite $$6 = $$5.getValue();
                    $$4.write(String.format(Locale.ROOT, "%s\tx=%d\ty=%d\tw=%d\th=%d%n", $$5.getKey(), Integer.valueOf($$6.getX()), Integer.valueOf($$6.getY()), Integer.valueOf($$6.contents().width()), Integer.valueOf($$6.contents().height())));
                }
                if ($$4 != null) {
                    $$4.close();
                }
            } finally {
            }
        } catch (IOException $$7) {
            LOGGER.warn("Failed to write file {}", $$3, $$7);
        }
    }

    public void cycleAnimationFrames() {
        if (this.texture == null) {
            return;
        }
        for (SpriteContents.AnimationState $$0 : this.animatedTexturesStates) {
            $$0.tick();
        }
        uploadAnimationFrames();
    }

    private void uploadAnimationFrames() {
        if (this.animatedTexturesStates.stream().anyMatch((v0) -> {
            return v0.needsToDraw();
        })) {
            for (int $$0 = 0; $$0 <= this.maxMipLevel; $$0++) {
                RenderPass $$1 = RenderSystem.getDevice().createCommandEncoder().createRenderPass(() -> {
                    return "Animate " + String.valueOf(this.location);
                }, this.mipViews[$$0], OptionalInt.empty());
                try {
                    for (SpriteContents.AnimationState $$2 : this.animatedTexturesStates) {
                        if ($$2.needsToDraw()) {
                            $$2.drawToAtlas($$1, $$2.getDrawUbo($$0));
                        }
                    }
                    if ($$1 != null) {
                        $$1.close();
                    }
                } catch (Throwable th) {
                    if ($$1 != null) {
                        try {
                            $$1.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            }
        }
    }

    @Override // net.minecraft.client.renderer.texture.TickableTexture
    public void tick() {
        cycleAnimationFrames();
    }

    public TextureAtlasSprite getSprite(Identifier $$0) {
        TextureAtlasSprite $$1 = this.texturesByName.getOrDefault($$0, this.missingSprite);
        if ($$1 == null) {
            throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
        }
        return $$1;
    }

    public TextureAtlasSprite missingSprite() {
        return (TextureAtlasSprite) Objects.requireNonNull(this.missingSprite, "Atlas not initialized");
    }

    public void clearTextureData() {
        this.sprites.forEach((v0) -> {
            v0.close();
        });
        this.sprites = List.of();
        this.animatedTexturesStates = List.of();
        this.texturesByName = Map.of();
        this.missingSprite = null;
    }

    @Override // net.minecraft.client.renderer.texture.AbstractTexture, java.lang.AutoCloseable
    public void close() {
        super.close();
        for (GpuTextureView $$0 : this.mipViews) {
            $$0.close();
        }
        for (SpriteContents.AnimationState $$1 : this.animatedTexturesStates) {
            $$1.close();
        }
        if (this.spriteUbos != null) {
            this.spriteUbos.close();
            this.spriteUbos = null;
        }
    }

    public Identifier location() {
        return this.location;
    }

    public int maxSupportedTextureSize() {
        return this.maxSupportedTextureSize;
    }

    int getWidth() {
        return this.width;
    }

    int getHeight() {
        return this.height;
    }
}
