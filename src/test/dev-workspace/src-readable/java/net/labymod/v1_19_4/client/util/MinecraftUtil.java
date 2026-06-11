package net.labymod.v1_19_4.client.util;

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
import net.labymod.api.client.render.font.text.TextDrawMode;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.matrix.VanillaStackAccessor;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.client.misc.CaptureScreenshotEvent;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.api.util.math.Direction;
import net.labymod.core.event.client.misc.WriteScreenshotEventCaller;
import net.labymod.v1_19_4.client.resources.texture.VersionedLabyTexture;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/util/MinecraftUtil.class */
public final class MinecraftUtil {
    public static final float NO_TEXT_EDGE_STRENGTH = 0.0f;
    public static final float DEFAULT_TEXT_EDGE_STRENGTH = 0.5f;
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, fsn> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof fsn) {
            fsn textureObject = (fsn) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " is not a valid texture.");
    });
    private static File lastFileGrab;

    public static Stack obtainStackFromGraphics(ehe graphics) {
        return ((VanillaStackAccessor) graphics).stack();
    }

    public static void obtainScreenContextFromPoseStack(ehe graphics, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromPoseStack(graphics, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromPoseStack(ehe graphics, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        Stack stack = obtainStackFromGraphics(graphics);
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(bfx entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.c(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(cfv itemStack) {
        return (ItemStack) itemStack;
    }

    public static cfv toMinecraft(ItemStack itemStack) {
        return (cfv) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static Direction fromMinecraft(gz direction) throws MatchException {
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
    public static gz toMinecraft(@NotNull Direction direction) throws MatchException {
        switch (direction) {
            case DOWN:
                return gz.a;
            case UP:
                return gz.b;
            case NORTH:
                return gz.c;
            case SOUTH:
                return gz.d;
            case WEST:
                return gz.e;
            case EAST:
                return gz.f;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static bfm toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (spot) {
            case HEAD:
                return bfm.f;
            case CHEST:
                return bfm.e;
            case LEGS:
                return bfm.d;
            case FEET:
                return bfm.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(cfs context) throws MatchException {
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

    public static void exportScreenshotToFile(egf image, File file) throws IOException {
        WriteScreenshotEventCaller.call(image.g(), file);
    }

    public static File getLastFileGrab() {
        return lastFileGrab;
    }

    public static dds getHighestGeneratedStatus(ddn access) {
        dds currentStatus = access.j();
        dgn belowZeroRetrogen = access.x();
        if (belowZeroRetrogen == null) {
            return currentStatus;
        }
        dds targetStatus = belowZeroRetrogen.a();
        return targetStatus.b(currentStatus) ? targetStatus : currentStatus;
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        fnc dispatcher = emh.N().an();
        fsf fsfVarA = dispatcher.a((bfh) player);
        if (!(fsfVarA instanceof fsf)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        fsf playerRenderer = fsfVarA;
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

    /* JADX INFO: renamed from: net.labymod.v1_19_4.client.util.MinecraftUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_19_4/client/util/MinecraftUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction;
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$world$item$ItemDisplayContext;
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
            $SwitchMap$net$minecraft$world$item$ItemDisplayContext = new int[cfs.values().length];
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.a.ordinal()] = 1;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.b.ordinal()] = 2;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.c.ordinal()] = 3;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.d.ordinal()] = 4;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.e.ordinal()] = 5;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.f.ordinal()] = 6;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.g.ordinal()] = 7;
            } catch (NoSuchFieldError e13) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.h.ordinal()] = 8;
            } catch (NoSuchFieldError e14) {
            }
            try {
                $SwitchMap$net$minecraft$world$item$ItemDisplayContext[cfs.i.ordinal()] = 9;
            } catch (NoSuchFieldError e15) {
            }
            $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot = new int[LivingEntity.EquipmentSpot.values().length];
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError e16) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.CHEST.ordinal()] = 2;
            } catch (NoSuchFieldError e17) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.LEGS.ordinal()] = 3;
            } catch (NoSuchFieldError e18) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.FEET.ordinal()] = 4;
            } catch (NoSuchFieldError e19) {
            }
            $SwitchMap$net$labymod$api$util$math$Direction = new int[Direction.values().length];
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.DOWN.ordinal()] = 1;
            } catch (NoSuchFieldError e20) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.UP.ordinal()] = 2;
            } catch (NoSuchFieldError e21) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.NORTH.ordinal()] = 3;
            } catch (NoSuchFieldError e22) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.SOUTH.ordinal()] = 4;
            } catch (NoSuchFieldError e23) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.WEST.ordinal()] = 5;
            } catch (NoSuchFieldError e24) {
            }
            try {
                $SwitchMap$net$labymod$api$util$math$Direction[Direction.EAST.ordinal()] = 6;
            } catch (NoSuchFieldError e25) {
            }
            $SwitchMap$net$minecraft$core$Direction = new int[gz.values().length];
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.a.ordinal()] = 1;
            } catch (NoSuchFieldError e26) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.b.ordinal()] = 2;
            } catch (NoSuchFieldError e27) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.c.ordinal()] = 3;
            } catch (NoSuchFieldError e28) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.d.ordinal()] = 4;
            } catch (NoSuchFieldError e29) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.e.ordinal()] = 5;
            } catch (NoSuchFieldError e30) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[gz.f.ordinal()] = 6;
            } catch (NoSuchFieldError e31) {
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

    public static aov fromText(String text, Style style) {
        qz language = qz.a();
        return language.a(tn.a(text, (uf) style));
    }

    public static fsn toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    public static bfh unwrapEntity(bfh entity) {
        if (entity instanceof btx) {
            btx part = (btx) entity;
            return part.b;
        }
        return entity;
    }
}
