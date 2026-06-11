package net.labymod.v1_21_4.client.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
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
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.math.Direction;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.accessor.resource.texture.NativeImageAccessor;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.v1_21_4.client.multiplayer.server.VersionedCookieStorage;
import net.labymod.v1_21_4.client.resources.texture.VersionedLabyTexture;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/util/MinecraftUtil.class */
public final class MinecraftUtil {
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static File lastFileGrab;
    private static fof currentGuiGraphics;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, hee> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof hee) {
            hee textureObject = (hee) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " is not a valid texture.");
    });
    public static final ffv VISITOR_STACK = new ffv();
    private static final ffv VIEW_MATRIX_STACK = new ffv();
    public static LevelRenderContext levelRenderContext = new LevelRenderContext();
    private static Matrix4f vanillaViewMatrix = new Matrix4f().identity();

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
        MathHelper.mapper().fromMatrix4f(vanillaViewMatrix4);
        return vanillaViewMatrix4;
    }

    public static Matrix4f getViewMatrix() {
        return vanillaViewMatrix;
    }

    public static Stack obtainStackFromGraphics(fof graphics) {
        return graphics.c().stack();
    }

    public static void obtainScreenContextFromGraphics(fof graphics, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromGraphics(graphics, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromGraphics(fof graphics, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        Stack stack = obtainStackFromGraphics(graphics);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(bvi entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.a(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(cwq itemStack) {
        return (ItemStack) itemStack;
    }

    public static cwq toMinecraft(ItemStack itemStack) {
        return (cwq) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Direction fromMinecraft(jn direction) throws MatchException {
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
    public static jn toMinecraft(@NotNull Direction direction) throws MatchException {
        switch (direction) {
            case DOWN:
                return jn.a;
            case UP:
                return jn.b;
            case NORTH:
                return jn.c;
            case SOUTH:
                return jn.d;
            case WEST:
                return jn.e;
            case EAST:
                return jn.f;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static buu toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (spot) {
            case HEAD:
                return buu.f;
            case CHEST:
                return buu.e;
            case LEGS:
                return buu.d;
            case FEET:
                return buu.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(cwo context) throws MatchException {
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
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static ServerType fromMinecraft(ggp data) {
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

    public static void updateTextEdgeStrength(float strength) {
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

    public static void exportScreenshotToFile(fev image, File file) throws IOException {
        WriteScreenshotEventCaller.call(((NativeImageAccessor) image).toByteArray(), file);
    }

    public static File getLastFileGrab() {
        return lastFileGrab;
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        gsd dispatcher = flk.Q().aq();
        gxm gxmVarA = dispatcher.a((bum) player);
        if (!(gxmVarA instanceof gxm)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        gxm playerRenderer = gxmVarA;
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

    /* JADX INFO: renamed from: net.labymod.v1_21_4.client.util.MinecraftUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/util/MinecraftUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemDisplayContext;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$gui$Font$DisplayMode;

        static {
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.NORMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.SEE_THROUGH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$render$font$text$TextDrawMode[TextDrawMode.POLYGON_OFFSET.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            $SwitchMap$net$minecraft$client$gui$Font$DisplayMode = new int[a.values().length];
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.a.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.b.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$client$gui$Font$DisplayMode[a.c.ordinal()] = 3;
            } catch (NoSuchFieldError e6) {
            }
            $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type = new int[c.values().length];
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[c.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            $SwitchMap$net$labymod$api$client$network$server$ServerType = new int[ServerType.values().length];
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.LAN.ordinal()] = 1;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.REALM.ordinal()] = 2;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$labymod$api$client$network$server$ServerType[ServerType.THIRD_PARTY.ordinal()] = 3;
            } catch (NoSuchFieldError e12) {
            }
            $SwitchMap$net$minecraft$world$item$ItemDisplayContext = new int[cwo.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.a.ordinal()] = 1;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.b.ordinal()] = 2;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.c.ordinal()] = 3;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.d.ordinal()] = 4;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.e.ordinal()] = 5;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.f.ordinal()] = 6;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.g.ordinal()] = 7;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.h.ordinal()] = 8;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cwo.i.ordinal()] = 9;
            } catch (NoSuchFieldError e21) {
            }
            $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot = new int[LivingEntity.EquipmentSpot.values().length];
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.CHEST.ordinal()] = 2;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.LEGS.ordinal()] = 3;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.FEET.ordinal()] = 4;
            } catch (NoSuchFieldError e25) {
            }
            $SwitchMap$net$labymod$api$util$math$Direction = new int[Direction.values().length];
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError e31) {
            }
            $SwitchMap$net$minecraft$core$Direction = new int[jn.values().length];
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.a.ordinal()] = 1;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.b.ordinal()] = 2;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.c.ordinal()] = 3;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.d.ordinal()] = 4;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.e.ordinal()] = 5;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jn.f.ordinal()] = 6;
            } catch (NoSuchFieldError e37) {
            }
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

    public static CookieStorage fromMinecraft(ggt state) {
        if (state == null) {
            return null;
        }
        return new VersionedCookieStorage(state);
    }

    public static ggt toMinecraft(CookieStorage storage) {
        Map<ResourceLocation, byte[]> apiCookies = storage.cookies();
        Map<akv, byte[]> cookies = new HashMap<>(apiCookies.size());
        for (Map.Entry<ResourceLocation, byte[]> entry : apiCookies.entrySet()) {
            cookies.put((akv) entry.getKey().getMinecraftLocation(), entry.getValue());
        }
        return new ggt(cookies);
    }

    public static ayl fromText(String text, Style style) {
        tl language = tl.a();
        return language.a(wu.a(text, (xm) style));
    }

    public static LevelRenderContext levelRenderContext() {
        return levelRenderContext;
    }

    public static hee toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    public static fof getCurrentGuiGraphics() {
        return currentGuiGraphics;
    }

    public static void setCurrentGuiGraphics(fof currentGuiGraphics2) {
        currentGuiGraphics = currentGuiGraphics2;
    }

    public static bum unwrapEntity(bum entity) {
        if (entity instanceof cjt) {
            cjt part = (cjt) entity;
            return part.a;
        }
        return entity;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_4/client/util/MinecraftUtil$LevelRenderContext.class */
    public static class LevelRenderContext {
        private ffv poseStack;

        public ffv getPoseStack() {
            return this.poseStack;
        }

        public void setPoseStack(ffv poseStack) {
            this.poseStack = poseStack;
        }
    }
}
