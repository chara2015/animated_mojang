package net.labymod.v1_21_11.client.util;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
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
import net.labymod.api.client.gfx.GlConst;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.server.CookieStorage;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.StackProvider;
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
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/util/MinecraftUtil.class */
public final class MinecraftUtil {
    private static File lastFileGrab;
    private static idf nameTagRenderState;
    public static PlayerModelRenderHandEvent prePlayerModelRenderHandEvent;
    public static PlayerModelRenderHandEvent postPlayerModelRenderHandEvent;
    private static gir currentGuiGraphics;
    public static final hnt ORTHO = new hnt("Test", 0.5f, 11000.0f, false);
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, ikz> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof ikz) {
            ikz textureObject = (ikz) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " is not a valid texture.");
    });
    private static final fzm VIEW_MATRIX_STACK = new fzm();
    public static LevelRenderContext levelRenderContext = new LevelRenderContext();
    private static Matrix4f vanillaViewMatrix = new Matrix4f().identity();

    public static idf getNameTagRenderState() {
        return nameTagRenderState;
    }

    public static void setNameTagRenderState(idf nameTagRenderState2) {
        nameTagRenderState = nameTagRenderState2;
    }

    public static void resetNameTagRenderState() {
        nameTagRenderState = null;
        RenderUtil.setNameTagType(TagType.INVALID);
    }

    public static Matrix4f setViewMatrix(Matrix4f vanillaViewMatrix2) {
        VIEW_MATRIX_STACK.a();
        VIEW_MATRIX_STACK.e();
        VIEW_MATRIX_STACK.a(vanillaViewMatrix2);
        Stack viewStack = VIEW_MATRIX_STACK.stack();
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.PRE));
        Matrix4f vanillaViewMatrix3 = VIEW_MATRIX_STACK.c().a();
        vanillaViewMatrix = vanillaViewMatrix3;
        Laby.fireEvent(new CameraSetupEvent(viewStack, Phase.POST));
        Matrix4f vanillaViewMatrix4 = VIEW_MATRIX_STACK.c().a();
        VIEW_MATRIX_STACK.b();
        return vanillaViewMatrix4;
    }

    public static Matrix4f getViewMatrix() {
        return vanillaViewMatrix;
    }

    public static Stack obtainStackFromGraphics(gir graphics) {
        return Stack.create((StackProvider) JomlMatrix3x2fStackProvider.fromGuiGraphics(graphics));
    }

    public static void obtainScreenContextFromGraphics(gir graphics, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromGraphics(graphics, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromGraphics(gir graphics, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        Stack stack = obtainStackFromGraphics(graphics);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(chl entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.a(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(dlt itemStack) {
        return (ItemStack) itemStack;
    }

    public static dlt toMinecraft(ItemStack itemStack) {
        return (dlt) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Direction fromMinecraft(iz direction) throws MatchException {
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
    public static iz toMinecraft(@NotNull Direction direction) throws MatchException {
        switch (direction) {
            case DOWN:
                return iz.a;
            case UP:
                return iz.b;
            case NORTH:
                return iz.c;
            case SOUTH:
                return iz.d;
            case WEST:
                return iz.e;
            case EAST:
                return iz.f;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static cgv toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (spot) {
            case HEAD:
                return cgv.f;
            case CHEST:
                return cgv.e;
            case LEGS:
                return cgv.d;
            case FEET:
                return cgv.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(dlr context) throws MatchException {
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

    public static ServerType fromMinecraft(hit data) {
        if (data.e()) {
            return ServerType.REALM;
        }
        if (data.d()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }

    public static c toMinecraft(ServerType type) {
        switch (type) {
            case LAN:
                return c.a;
            case REALM:
                return c.b;
            case THIRD_PARTY:
                return c.c;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ServerType fromMinecraft(c type) throws MatchException {
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

    public static void exportScreenshotToFile(fyh image, File file) throws IOException {
        WriteScreenshotEventCaller.call(((NativeImageAccessor) image).toByteArray(), file);
    }

    public static File getLastFileGrab() {
        return lastFileGrab;
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        hwo dispatcher = gfj.V().ay();
        icd<?> icdVarA = dispatcher.a((cgk) player);
        if (!(icdVarA instanceof icd)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        icd<?> playerRenderer = icdVarA;
        return playerRenderer.c();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static TextDrawMode fromMinecraft(a displayMode) throws MatchException {
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
    public static a toMinecraft(TextDrawMode textDrawMode) throws MatchException {
        switch (textDrawMode) {
            case NORMAL:
                return a.a;
            case SEE_THROUGH:
                return a.b;
            case POLYGON_OFFSET:
                return a.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static CookieStorage fromMinecraft(hix state) {
        if (state == null) {
            return null;
        }
        return new VersionedCookieStorage(state);
    }

    public static hix toMinecraft(CookieStorage storage) {
        Map<ResourceLocation, byte[]> apiCookies = storage.cookies();
        Map<amo, byte[]> cookies = new HashMap<>(apiCookies.size());
        for (Map.Entry<ResourceLocation, byte[]> entry : apiCookies.entrySet()) {
            cookies.put((amo) entry.getKey().getMinecraftLocation(), entry.getValue());
        }
        hix transferState = ((VersionedCookieStorage) storage).getTransferState();
        return new hix(cookies, transferState.b(), transferState.c());
    }

    public static LevelRenderContext levelRenderContext() {
        return levelRenderContext;
    }

    public static ikz toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ym toMinecraft(GlyphSourceDescription glyphSourceDescription) throws MatchException {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), GlyphSourceDescription.Resource.class, GlyphSourceDescription.AtlasSprite.class, GlyphSourceDescription.PlayerSprite.class).dynamicInvoker().invoke(glyphSourceDescription, 0) /* invoke-custom */) {
            case -1:
                return null;
            case 0:
                GlyphSourceDescription.Resource resource = (GlyphSourceDescription.Resource) glyphSourceDescription;
                return new c((amo) resource.id().getMinecraftLocation());
            case 1:
                GlyphSourceDescription.AtlasSprite atlasSprite = (GlyphSourceDescription.AtlasSprite) glyphSourceDescription;
                return new a((amo) atlasSprite.atlasId().getMinecraftLocation(), (amo) atlasSprite.spriteId().getMinecraftLocation());
            case 2:
                GlyphSourceDescription.PlayerSprite playerSprite = (GlyphSourceDescription.PlayerSprite) glyphSourceDescription;
                GlyphSourceDescription.PlayerSprite.Profile profile = playerSprite.profile();
                if (profile instanceof GlyphSourceDescription.PlayerSprite.Profile.Dynamic) {
                    try {
                        Either<String, UUID> nameOrId = ((GlyphSourceDescription.PlayerSprite.Profile.Dynamic) profile).nameOrId();
                        doy resolvableProfile = (doy) nameOrId.map(doy::a, doy::a);
                        return new b(resolvableProfile, playerSprite.hat());
                    } catch (Throwable th) {
                        throw new MatchException(th.toString(), th);
                    }
                }
                GlyphSourceDescription.PlayerSprite.Profile.Static staticProfile = (GlyphSourceDescription.PlayerSprite.Profile.Static) profile;
                return new b(doy.a((GameProfile) CastUtil.cast(staticProfile.profile())), playerSprite.hat());
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(glyphSourceDescription));
        }
    }

    public static GlyphSourceDescription fromMinecraft(ym fontDescription) {
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), c.class, a.class, b.class).dynamicInvoker().invoke(fontDescription, 0) /* invoke-custom */) {
            case -1:
                return null;
            case 0:
                c resource = (c) fontDescription;
                return GlyphSourceDescription.resource((ResourceLocation) CastUtil.cast(resource.a()));
            case 1:
                a atlasSprite = (a) fontDescription;
                return GlyphSourceDescription.atlasSprite((ResourceLocation) CastUtil.cast(atlasSprite.a()), (ResourceLocation) CastUtil.cast(atlasSprite.b()));
            case 2:
                b playerSprite = (b) fontDescription;
                return GlyphSourceDescription.playerSprite(fromMinecraft(playerSprite.a()), playerSprite.b());
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(fontDescription));
        }
    }

    public static GlyphSourceDescription.PlayerSprite.Profile fromMinecraft(doy resolvableProfile) {
        Either<String, UUID> nameOrId;
        GameProfile partialProfile = resolvableProfile.b();
        if (resolvableProfile instanceof a) {
            if (!partialProfile.name().isBlank()) {
                nameOrId = Either.left(partialProfile.name());
            } else if (partialProfile.id() != bhs.e) {
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
        GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, lightMap ? GlConst.GL_LINEAR : GlConst.GL_NEAREST);
        GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, lightMap ? GlConst.GL_LINEAR : GlConst.GL_NEAREST);
    }

    public static SamplerDescription fromMinecraft(fzf sampler) {
        SamplerDescription.Builder builder = SamplerDescription.builder();
        return builder.setAddressU(fromMinecraft(sampler.b())).setAddressV(fromMinecraft(sampler.c())).setMinFilter(fromMinecraft(sampler.d())).setMagFilter(fromMinecraft(sampler.e())).build();
    }

    public static fzf getSampler(SamplerDescription description) {
        return RenderSystem.getSamplerCache().a(toMinecraft(description.addressU()), toMinecraft(description.addressV()), toMinecraft(description.minFilter()), toMinecraft(description.magFilter()), description.mipLevels() > 1);
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
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/util/MinecraftUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemDisplayContext;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$gui$Font$DisplayMode;
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
            $SwitchMap$net$minecraft$client$gui$Font$DisplayMode = new int[a.values().length];
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e14) {
            }
            $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type = new int[c.values().length];
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.a.ordinal()] = 1;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.b.ordinal()] = 2;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.c.ordinal()] = 3;
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
            $SwitchMap$net$minecraft$world$item$ItemDisplayContext = new int[dlr.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.a.ordinal()] = 1;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.b.ordinal()] = 2;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.c.ordinal()] = 3;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.d.ordinal()] = 4;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.e.ordinal()] = 5;
            } catch (NoSuchFieldError e25) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.f.ordinal()] = 6;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.g.ordinal()] = 7;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.h.ordinal()] = 8;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.i.ordinal()] = 9;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[dlr.j.ordinal()] = 10;
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
            $SwitchMap$net$minecraft$core$Direction = new int[iz.values().length];
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.a.ordinal()] = 1;
            } catch (NoSuchFieldError e41) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.b.ordinal()] = 2;
            } catch (NoSuchFieldError e42) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.c.ordinal()] = 3;
            } catch (NoSuchFieldError e43) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.d.ordinal()] = 4;
            } catch (NoSuchFieldError e44) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.e.ordinal()] = 5;
            } catch (NoSuchFieldError e45) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[iz.f.ordinal()] = 6;
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

    public static gir getCurrentGuiGraphics() {
        return currentGuiGraphics;
    }

    public static void setCurrentGuiGraphics(gir currentGuiGraphics2) {
        currentGuiGraphics = currentGuiGraphics2;
    }

    public static cgk unwrapEntity(cgk entity) {
        if (entity instanceof cyd) {
            cyd part = (cyd) entity;
            return part.a;
        }
        return entity;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_11/client/util/MinecraftUtil$LevelRenderContext.class */
    public static class LevelRenderContext {
        private fzm poseStack;

        public fzm getPoseStack() {
            return this.poseStack;
        }

        public void setPoseStack(fzm poseStack) {
            this.poseStack = poseStack;
        }
    }
}
