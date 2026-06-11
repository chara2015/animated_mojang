package net.labymod.v1_21_11.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.vertex.PoseStack;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.GlyphSourceDescription;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.entity.player.tag.TagType;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Phase;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import net.labymod.api.event.client.render.camera.CameraSetupEvent;
import net.labymod.api.event.client.render.model.entity.player.PlayerModelRenderHandEvent;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.Either;
import net.labymod.api.util.RenderUtil;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.math.Direction;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.laby3d.api.textures.SamplerDescription;
import net.labymod.v1_21_11.client.multiplayer.server.VersionedCookieStorage;
import net.labymod.v1_21_11.client.render.matrix.JomlMatrix3x2fStackProvider;
import net.labymod.v1_21_11.client.resources.texture.VersionedLabyTexture;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.TransferState;
import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragonPart;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.component.ResolvableProfile;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/MinecraftUtil.class */
public final class MinecraftUtil {
    private static File lastFileGrab;
    private static EntityRenderState nameTagRenderState;
    public static PlayerModelRenderHandEvent prePlayerModelRenderHandEvent;
    public static PlayerModelRenderHandEvent postPlayerModelRenderHandEvent;
    private static GuiGraphics currentGuiGraphics;
    public static final CachedOrthoProjectionMatrixBuffer ORTHO = new CachedOrthoProjectionMatrixBuffer("Test", 0.5f, 11000.0f, false);
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, AbstractTexture> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof AbstractTexture) {
            AbstractTexture textureObject = (AbstractTexture) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " net.minecraft.core.BlockPos not a valid texture.");
    });
    private static final PoseStack VIEW_MATRIX_STACK = new PoseStack();
    public static LevelRenderContext levelRenderContext = new LevelRenderContext();
    private static Matrix4f vanillaViewMatrix = new Matrix4f().identity();

    public static EntityRenderState getNameTagRenderState() {
        return nameTagRenderState;
    }

    public static void setNameTagRenderState(EntityRenderState nameTagRenderState2) {
        nameTagRenderState = nameTagRenderState2;
    }

    public static void resetNameTagRenderState() {
        nameTagRenderState = null;
        RenderUtil.setNameTagType(TagType.INVALID);
    }

    public static Matrix4f setViewMatrix(Matrix4f vanillaViewMatrix2) {
        VIEW_MATRIX_STACK.pushPose();
        VIEW_MATRIX_STACK.setIdentity();
        VIEW_MATRIX_STACK.mulPose(vanillaViewMatrix2);
        Stack viewStack = VIEW_MATRIX_STACK.stack();
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.PRE));
        Matrix4f vanillaViewMatrix3 = VIEW_MATRIX_STACK.last().pose();
        vanillaViewMatrix = vanillaViewMatrix3;
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.POST));
        Matrix4f vanillaViewMatrix4 = VIEW_MATRIX_STACK.last().pose();
        VIEW_MATRIX_STACK.popPose();
        return vanillaViewMatrix4;
    }

    public static Matrix4f getViewMatrix() {
        return vanillaViewMatrix;
    }

    public static Stack obtainStackFromGraphics(GuiGraphics graphics) {
        return Stack.create(JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
    }

    public static void obtainScreenContextFromGraphics(GuiGraphics graphics, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromGraphics(graphics, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromGraphics(GuiGraphics graphics, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        Stack stack = obtainStackFromGraphics(graphics);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(LivingEntity entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.getItemBySlot(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(net.minecraft.world.item.ItemStack itemStack) {
        return (ItemStack) itemStack;
    }

    public static net.minecraft.world.item.ItemStack toMinecraft(ItemStack itemStack) {
        return (net.minecraft.world.item.ItemStack) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Direction fromMinecraft(net.minecraft.core.Direction direction) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$core$Direction[direction.ordinal()]) {
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.UP;
            case 3:
                return Direction.NORTH;
            case 4:
                return Direction.SOUTH;
            case 5:
                return Direction.WEST;
            case 6:
                return Direction.EAST;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static net.minecraft.core.Direction toMinecraft(@NotNull Direction direction) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$util$math$Direction[direction.ordinal()]) {
            case 1:
                return net.minecraft.core.Direction.DOWN;
            case 2:
                return net.minecraft.core.Direction.UP;
            case 3:
                return net.minecraft.core.Direction.NORTH;
            case 4:
                return net.minecraft.core.Direction.SOUTH;
            case 5:
                return net.minecraft.core.Direction.WEST;
            case 6:
                return net.minecraft.core.Direction.EAST;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static EquipmentSlot toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[spot.ordinal()]) {
            case 1:
                return EquipmentSlot.HEAD;
            case 2:
                return EquipmentSlot.CHEST;
            case 3:
                return EquipmentSlot.LEGS;
            case 4:
                return EquipmentSlot.FEET;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(ItemDisplayContext context) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$world$item$ItemDisplayContext[context.ordinal()]) {
            case 1:
                return ModelTransformType.NONE;
            case 2:
                return ModelTransformType.THIRD_PERSON_LEFT_HAND;
            case 3:
                return ModelTransformType.THIRD_PERSON_RIGHT_HAND;
            case 4:
                return ModelTransformType.FIRST_PERSON_LEFT_HAND;
            case 5:
                return ModelTransformType.FIRST_PERSON_RIGHT_HAND;
            case 6:
                return ModelTransformType.HEAD;
            case 7:
                return ModelTransformType.GUI;
            case 8:
                return ModelTransformType.GROUND;
            case 9:
                return ModelTransformType.FIXED;
            case 10:
                return ModelTransformType.ON_SHELF;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static ServerType fromMinecraft(ServerData data) {
        if (data.isRealm()) {
            return ServerType.REALM;
        }
        if (data.isLan()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }

    public static ServerData.Type toMinecraft(ServerType type) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$network$server$ServerType[type.ordinal()]) {
            case 1:
                return ServerData.Type.LAN;
            case 2:
                return ServerData.Type.REALM;
            case 3:
                return ServerData.Type.OTHER;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ServerType fromMinecraft(ServerData.Type type) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[type.ordinal()]) {
            case 1:
                return ServerType.LAN;
            case 2:
                return ServerType.REALM;
            case 3:
                return ServerType.THIRD_PARTY;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static void grabFile(File gameDirectory, String customName, Function<File, File> fileMapper) {
        File destination;
        File screenshotsDirectory = new File(gameDirectory, "screenshots");
        screenshotsDirectory.mkdir();
        if (customName == null) {
            destination = fileMapper.apply(screenshotsDirectory);
        } else {
            destination = new File(screenshotsDirectory, customName);
        }
        Laby.fireEvent(new CaptureScreenshotEvent(destination));
        lastFileGrab = destination;
    }

    public static void exportScreenshotToFile(NativeImage image, File file) throws IOException {
        WriteScreenshotEventCaller.call(((NativeImageAccessor) image).toByteArray(), file);
    }

    public static File getLastFileGrab() {
        return lastFileGrab;
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        EntityRenderDispatcher dispatcher = net.minecraft.client.Minecraft.getInstance().getEntityRenderDispatcher();
        AvatarRenderer<?> renderer = dispatcher.getRenderer((Entity) player);
        if (!(renderer instanceof AvatarRenderer)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        AvatarRenderer<?> playerRenderer = renderer;
        return playerRenderer.getModel();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static TextDrawMode fromMinecraft(Font.DisplayMode displayMode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$gui$Font$DisplayMode[displayMode.ordinal()]) {
            case 1:
                return TextDrawMode.NORMAL;
            case 2:
                return TextDrawMode.SEE_THROUGH;
            case 3:
                return TextDrawMode.POLYGON_OFFSET;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Font.DisplayMode toMinecraft(TextDrawMode textDrawMode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[textDrawMode.ordinal()]) {
            case 1:
                return Font.DisplayMode.NORMAL;
            case 2:
                return Font.DisplayMode.SEE_THROUGH;
            case 3:
                return Font.DisplayMode.POLYGON_OFFSET;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static CookieStorage fromMinecraft(TransferState state) {
        if (state == null) {
            return null;
        }
        return new VersionedCookieStorage(state);
    }

    public static TransferState toMinecraft(CookieStorage storage) {
        Map<ResourceLocation, byte[]> apiCookies = storage.cookies();
        Map<Identifier, byte[]> cookies = new HashMap<>(apiCookies.size());
        for (Map.Entry<ResourceLocation, byte[]> entry : apiCookies.entrySet()) {
            cookies.put((Identifier) entry.getKey().getMinecraftLocation(), entry.getValue());
        }
        TransferState transferState = ((VersionedCookieStorage) storage).getTransferState();
        return new TransferState(cookies, transferState.seenPlayers(), transferState.seenInsecureChatWarning());
    }

    public static LevelRenderContext levelRenderContext() {
        return levelRenderContext;
    }

    public static AbstractTexture toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static FontDescription toMinecraft(GlyphSourceDescription glyphSourceDescription) throws MatchException {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), GlyphSourceDescription.Resource.class, GlyphSourceDescription.AtlasSprite.class, GlyphSourceDescription.PlayerSprite.class).dynamicInvoker().invoke(glyphSourceDescription, 0) /* invoke-custom */) {
            case -1:
                return null;
            case 0:
                GlyphSourceDescription.Resource resource = (GlyphSourceDescription.Resource) glyphSourceDescription;
                return new FontDescription.Resource((Identifier) resource.id().getMinecraftLocation());
            case 1:
                GlyphSourceDescription.AtlasSprite atlasSprite = (GlyphSourceDescription.AtlasSprite) glyphSourceDescription;
                return new FontDescription.AtlasSprite((Identifier) atlasSprite.atlasId().getMinecraftLocation(), (Identifier) atlasSprite.spriteId().getMinecraftLocation());
            case 2:
                GlyphSourceDescription.PlayerSprite playerSprite = (GlyphSourceDescription.PlayerSprite) glyphSourceDescription;
                GlyphSourceDescription.PlayerSprite.Profile.Dynamic dynamicProfile = playerSprite.profile();
                if (dynamicProfile instanceof GlyphSourceDescription.PlayerSprite.Profile.Dynamic) {
                    try {
                        Either<String, UUID> nameOrId = dynamicProfile.nameOrId();
                        ResolvableProfile resolvableProfile = (ResolvableProfile) nameOrId.map(ResolvableProfile::createUnresolved, ResolvableProfile::createUnresolved);
                        return new FontDescription.PlayerSprite(resolvableProfile, playerSprite.hat());
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                }
                GlyphSourceDescription.PlayerSprite.Profile.Static staticProfile = (GlyphSourceDescription.PlayerSprite.Profile.Static) dynamicProfile;
                return new FontDescription.PlayerSprite(ResolvableProfile.createResolved((GameProfile) CastUtil.cast(staticProfile.profile())), playerSprite.hat());
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(glyphSourceDescription));
        }
    }

    public static GlyphSourceDescription fromMinecraft(FontDescription fontDescription) {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), FontDescription.Resource.class, FontDescription.AtlasSprite.class, FontDescription.PlayerSprite.class).dynamicInvoker().invoke(fontDescription, 0) /* invoke-custom */) {
            case -1:
                return null;
            case 0:
                FontDescription.Resource resource = (FontDescription.Resource) fontDescription;
                return GlyphSourceDescription.resource((ResourceLocation) CastUtil.cast(resource.id()));
            case 1:
                FontDescription.AtlasSprite atlasSprite = (FontDescription.AtlasSprite) fontDescription;
                return GlyphSourceDescription.atlasSprite((ResourceLocation) CastUtil.cast(atlasSprite.atlasId()), (ResourceLocation) CastUtil.cast(atlasSprite.spriteId()));
            case 2:
                FontDescription.PlayerSprite playerSprite = (FontDescription.PlayerSprite) fontDescription;
                return GlyphSourceDescription.playerSprite(fromMinecraft(playerSprite.profile()), playerSprite.hat());
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(fontDescription));
        }
    }

    public static GlyphSourceDescription.PlayerSprite.Profile fromMinecraft(ResolvableProfile resolvableProfile) {
        Either<String, UUID> nameOrId;
        GameProfile partialProfile = resolvableProfile.partialProfile();
        if (resolvableProfile instanceof ResolvableProfile.Dynamic) {
            if (!partialProfile.name().isBlank()) {
                nameOrId = Either.left(partialProfile.name());
            } else if (partialProfile.id() != Util.NIL_UUID) {
                nameOrId = Either.right(partialProfile.id());
            } else {
                throw new IllegalStateException("The partial profile does not contain a name or an id.");
            }
            return new GlyphSourceDescription.PlayerSprite.Profile.Dynamic(nameOrId);
        }
        return new GlyphSourceDescription.PlayerSprite.Profile.Static((net.labymod.api.mojang.GameProfile) CastUtil.cast(partialProfile));
    }

    public static void applySamplerObjectWorkaround() {
        int activeTexture = GlStateManager.activeTexture;
        boolean lightMap = activeTexture == 2;
        GlStateManager._texParameter(3553, 10241, lightMap ? 9729 : 9728);
        GlStateManager._texParameter(3553, 10240, lightMap ? 9729 : 9728);
    }

    public static SamplerDescription fromMinecraft(GpuSampler sampler) {
        SamplerDescription.Builder builder = SamplerDescription.builder();
        return builder.setAddressU(fromMinecraft(sampler.getAddressModeU())).setAddressV(fromMinecraft(sampler.getAddressModeV())).setMinFilter(fromMinecraft(sampler.getMinFilter())).setMagFilter(fromMinecraft(sampler.getMagFilter())).build();
    }

    public static GpuSampler getSampler(SamplerDescription description) {
        return RenderSystem.getSamplerCache().getSampler(toMinecraft(description.addressU()), toMinecraft(description.addressV()), toMinecraft(description.minFilter()), toMinecraft(description.magFilter()), description.mipLevels() > 1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static SamplerDescription.AddressMode fromMinecraft(AddressMode mode) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$com$mojang$blaze3d$textures$AddressMode[mode.ordinal()]) {
            case 1:
                return SamplerDescription.AddressMode.WRAP;
            case 2:
                return SamplerDescription.AddressMode.CLAMP;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static AddressMode toMinecraft(SamplerDescription.AddressMode mode) {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[mode.ordinal()]) {
            case 1:
                return AddressMode.REPEAT;
            case 2:
                return AddressMode.CLAMP_TO_EDGE;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(mode));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static SamplerDescription.Filter fromMinecraft(FilterMode filter) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$com$mojang$blaze3d$textures$FilterMode[filter.ordinal()]) {
            case 1:
                return SamplerDescription.Filter.NEAREST;
            case 2:
                return SamplerDescription.Filter.LINEAR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_11.client.util.MinecraftUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/MinecraftUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$util$math$Direction;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemDisplayContext;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$network$server$ServerType;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$gui$Font$DisplayMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode;
        static final /* synthetic */ int[] $SwitchMap$com$mojang$blaze3d$textures$AddressMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode;
        static final /* synthetic */ int[] $SwitchMap$com$mojang$blaze3d$textures$FilterMode;
        static final /* synthetic */ int[] $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter = new int[SamplerDescription.Filter.values().length];

        static {
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[SamplerDescription.Filter.NEAREST.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[SamplerDescription.Filter.LINEAR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SwitchMap$com$mojang$blaze3d$textures$FilterMode = new int[FilterMode.values().length];
            try {
                $SwitchMap$com$mojang$blaze3d$textures$FilterMode[FilterMode.NEAREST.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$textures$FilterMode[FilterMode.LINEAR.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode = new int[SamplerDescription.AddressMode.values().length];
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[SamplerDescription.AddressMode.WRAP.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$AddressMode[SamplerDescription.AddressMode.CLAMP.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$com$mojang$blaze3d$textures$AddressMode = new int[AddressMode.values().length];
            try {
                $SwitchMap$com$mojang$blaze3d$textures$AddressMode[AddressMode.REPEAT.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$com$mojang$blaze3d$textures$AddressMode[AddressMode.CLAMP_TO_EDGE.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode = new int[TextDrawMode.values().length];
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.SEE_THROUGH.ordinal()] = 2;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.POLYGON_OFFSET.ordinal()] = 3;
            } catch (NoSuchFieldError e11) {
            }
            $SwitchMap$net$minecraft$client$gui$Font$DisplayMode = new int[Font.DisplayMode.values().length];
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.SEE_THROUGH.ordinal()] = 2;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[Font.DisplayMode.POLYGON_OFFSET.ordinal()] = 3;
            } catch (NoSuchFieldError e14) {
            }
            $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type = new int[ServerData.Type.values().length];
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[ServerData.Type.LAN.ordinal()] = 1;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[ServerData.Type.REALM.ordinal()] = 2;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[ServerData.Type.OTHER.ordinal()] = 3;
            } catch (NoSuchFieldError e17) {
            }
            $SwitchMap$net$labymod$api$client$network$server$ServerType = new int[ServerType.values().length];
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.LAN.ordinal()] = 1;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.REALM.ordinal()] = 2;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.THIRD_PARTY.ordinal()] = 3;
            } catch (NoSuchFieldError e20) {
            }
            $SwitchMap$net$minecraft$world$item$ItemDisplayContext = new int[ItemDisplayContext.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.THIRD_PERSON_LEFT_HAND.ordinal()] = 2;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.THIRD_PERSON_RIGHT_HAND.ordinal()] = 3;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.FIRST_PERSON_LEFT_HAND.ordinal()] = 4;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.FIRST_PERSON_RIGHT_HAND.ordinal()] = 5;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.HEAD.ordinal()] = 6;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.GUI.ordinal()] = 7;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.GROUND.ordinal()] = 8;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.FIXED.ordinal()] = 9;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[ItemDisplayContext.ON_SHELF.ordinal()] = 10;
            } catch (NoSuchFieldError e30) {
            }
            $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot = new int[LivingEntity.EquipmentSpot.values().length];
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError e31) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.CHEST.ordinal()] = 2;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.LEGS.ordinal()] = 3;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.FEET.ordinal()] = 4;
            } catch (NoSuchFieldError e34) {
            }
            $SwitchMap$net$labymod$api$util$math$Direction = new int[Direction.values().length];
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError e37) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError e38) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError e39) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError e40) {
            }
            $SwitchMap$net$minecraft$core$Direction = new int[net.minecraft.core.Direction.values().length];
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e41) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e42) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError e43) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError e44) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError e45) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[net.minecraft.core.Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError e46) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static FilterMode toMinecraft(SamplerDescription.Filter filter) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$labymod$laby3d$api$textures$SamplerDescription$Filter[filter.ordinal()]) {
            case 1:
                return FilterMode.NEAREST;
            case 2:
                return FilterMode.LINEAR;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static GuiGraphics getCurrentGuiGraphics() {
        return currentGuiGraphics;
    }

    public static void setCurrentGuiGraphics(GuiGraphics currentGuiGraphics2) {
        currentGuiGraphics = currentGuiGraphics2;
    }

    public static Entity unwrapEntity(Entity entity) {
        if (entity instanceof EnderDragonPart) {
            EnderDragonPart part = (EnderDragonPart) entity;
            return part.parentMob;
        }
        return entity;
    }

    /* JADX INFO: loaded from: LabyMod-4-v1_21_11-named.jar:net/labymod/v1_21_11/client/util/MinecraftUtil$LevelRenderContext.class */
    public static class LevelRenderContext {
        private PoseStack poseStack;

        public PoseStack getPoseStack() {
            return this.poseStack;
        }

        public void setPoseStack(PoseStack poseStack) {
            this.poseStack = poseStack;
        }
    }
}

