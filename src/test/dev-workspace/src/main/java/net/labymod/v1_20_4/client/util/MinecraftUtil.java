package net.labymod.v1_20_4.client.util;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.network.server.ServerType;
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.math.Direction;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.v1_20_4.client.resources.texture.VersionedLabyTexture;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/util/MinecraftUtil.class */
public final class MinecraftUtil {
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, gdy> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof gdy) {
            gdy textureObject = (gdy) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " is not a valid texture.");
    });
    private static File lastFileGrab;
    private static ewu currentGuiGraphics;

    public static Stack obtainStackFromGraphics(ewu graphics) {
        return graphics.c().stack();
    }

    public static void obtainScreenContextFromGraphics(ewu graphics, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromGraphics(graphics, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromGraphics(ewu graphics, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        Stack stack = obtainStackFromGraphics(graphics);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(bml entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.c(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(cmy itemStack) {
        return (ItemStack) itemStack;
    }

    public static cmy toMinecraft(ItemStack itemStack) {
        return (cmy) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Direction fromMinecraft(ic direction) throws MatchException {
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
    public static ic toMinecraft(@NotNull Direction direction) throws MatchException {
        switch (direction) {
            case DOWN:
                return ic.a;
            case UP:
                return ic.b;
            case NORTH:
                return ic.c;
            case SOUTH:
                return ic.d;
            case WEST:
                return ic.e;
            case EAST:
                return ic.f;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static bma toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (spot) {
            case HEAD:
                return bma.f;
            case CHEST:
                return bma.e;
            case LEGS:
                return bma.d;
            case FEET:
                return bma.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(cmv context) throws MatchException {
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

    public static ServerType fromMinecraft(fod data) {
        if (data.e()) {
            return ServerType.REALM;
        }
        if (data.d()) {
            return ServerType.LAN;
        }
        return ServerType.THIRD_PARTY;
    }

    public static b toMinecraft(ServerType type) {
        switch (type) {
            case LAN:
                return b.a;
            case REALM:
                return b.b;
            case THIRD_PARTY:
                return b.c;
            default:
                throw new IllegalStateException("Unexpected value: " + String.valueOf(type));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ServerType fromMinecraft(b type) throws MatchException {
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

    public static void exportScreenshotToFile(epc image, File file) throws IOException {
        WriteScreenshotEventCaller.call(image.g(), file);
    }

    public static File getLastFileGrab() {
        return lastFileGrab;
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        fyl dispatcher = evi.O().ao();
        gdq gdqVarA = dispatcher.a((blv) player);
        if (!(gdqVarA instanceof gdq)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        gdq playerRenderer = gdqVarA;
        return playerRenderer.a();
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

    /* JADX INFO: renamed from: net.labymod.v1_20_4.client.util.MinecraftUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/client/util/MinecraftUtil$1.class */
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
            $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type = new int[b.values().length];
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[b.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[b.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$multiplayer$ServerData$Type[b.c.ordinal()] = 3;
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
            $SwitchMap$net$minecraft$world$item$ItemDisplayContext = new int[cmv.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.a.ordinal()] = 1;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.b.ordinal()] = 2;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.c.ordinal()] = 3;
            } catch (NoSuchFieldError e15) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.d.ordinal()] = 4;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.e.ordinal()] = 5;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.f.ordinal()] = 6;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.g.ordinal()] = 7;
            } catch (NoSuchFieldError e19) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.h.ordinal()] = 8;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cmv.i.ordinal()] = 9;
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
            $SwitchMap$net$minecraft$core$Direction = new int[ic.values().length];
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.a.ordinal()] = 1;
            } catch (NoSuchFieldError e32) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.b.ordinal()] = 2;
            } catch (NoSuchFieldError e33) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.c.ordinal()] = 3;
            } catch (NoSuchFieldError e34) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.d.ordinal()] = 4;
            } catch (NoSuchFieldError e35) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.e.ordinal()] = 5;
            } catch (NoSuchFieldError e36) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.f.ordinal()] = 6;
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

    public static aua fromText(String text, Style style) {
        si language = si.a();
        return language.a(vk.a(text, (wc) style));
    }

    public static gdy toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    public static ewu getCurrentGuiGraphics() {
        return currentGuiGraphics;
    }

    public static void setCurrentGuiGraphics(ewu currentGuiGraphics2) {
        currentGuiGraphics = currentGuiGraphics2;
    }

    public static blv unwrapEntity(blv entity) {
        if (entity instanceof cam) {
            cam part = (cam) entity;
            return part.b;
        }
        return entity;
    }
}
