package net.labymod.v1_12_2.client.util;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.gui.mouse.Mouse;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.render.matrix.Stack;
import net.labymod.api.client.render.model.ModelTransformType;
import net.labymod.api.client.render.model.entity.player.PlayerModel;
import net.labymod.api.client.resources.texture.LabyTexture;
import net.labymod.api.client.world.block.BlockState;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.event.Event;
import net.labymod.api.util.function.FunctionMemoizeStorage;
import net.labymod.v1_12_2.client.resources.texture.VersionedLabyTexture;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/util/MinecraftUtil.class */
public class MinecraftUtil {
    public static final aip AIR = new aip(aox.a);
    private static final FunctionMemoizeStorage MEMOIZE = Laby.references().functionMemoizeStorage();
    private static final Function<Object, cds> TEXTURES = MEMOIZE.memoize(texture -> {
        if (texture instanceof LabyTexture) {
            LabyTexture labyTexture = (LabyTexture) texture;
            return new VersionedLabyTexture(labyTexture);
        }
        if (texture instanceof cds) {
            cds textureObject = (cds) texture;
            return textureObject;
        }
        throw new IllegalArgumentException("The given object " + texture.getClass().getName() + " is not a valid texture.");
    });

    public static void obtainScreenContextFromStack(Stack stack, Consumer<ScreenContext> callback) {
        Minecraft minecraft = Laby.labyAPI().minecraft();
        Mouse mouse = minecraft.absoluteMouse();
        obtainScreenContextFromStack(stack, mouse.getX(), mouse.getY(), minecraft.getTickDelta(), callback);
    }

    public static void obtainScreenContextFromStack(Stack stack, int mouseX, int mouseY, float tickDelta, Consumer<ScreenContext> callback) {
        ScreenContext screenContext = Laby.references().renderEnvironmentContext().screenContext();
        screenContext.runInContext(stack, mouseX, mouseY, tickDelta, callback);
    }

    public static ItemStack fromMinecraftSlot(vp entity, LivingEntity.EquipmentSpot spot) {
        return fromMinecraft(entity.b(toMinecraft(spot)));
    }

    public static ItemStack fromMinecraft(aip itemStack) {
        if (itemStack == null) {
            itemStack = AIR;
        }
        return (ItemStack) itemStack;
    }

    public static aip toMinecraft(ItemStack itemStack) {
        return (aip) itemStack;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static ModelTransformType fromMinecraft(b type) throws MatchException {
        switch (AnonymousClass1.$SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[type.ordinal()]) {
            case 1:
                return ModelTransformType.NONE;
            case 2:
                return ModelTransformType.FIRST_PERSON_LEFT_HAND;
            case 3:
                return ModelTransformType.FIRST_PERSON_RIGHT_HAND;
            case 4:
                return ModelTransformType.THIRD_PERSON_LEFT_HAND;
            case 5:
                return ModelTransformType.THIRD_PERSON_RIGHT_HAND;
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

    /* JADX INFO: renamed from: net.labymod.v1_12_2.client.util.MinecraftUtil$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/util/MinecraftUtil$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType;

        static {
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.CHEST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.LEGS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$labymod$api$client$entity$LivingEntity$EquipmentSpot[LivingEntity.EquipmentSpot.FEET.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType = new int[b.values().length];
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.a.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.d.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.e.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.b.ordinal()] = 4;
            } catch (NoSuchFieldError e8) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.c.ordinal()] = 5;
            } catch (NoSuchFieldError e9) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.f.ordinal()] = 6;
            } catch (NoSuchFieldError e10) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.g.ordinal()] = 7;
            } catch (NoSuchFieldError e11) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.h.ordinal()] = 8;
            } catch (NoSuchFieldError e12) {
            }
            try {
                $SwitchMap$net$minecraft$client$renderer$block$model$ItemCameraTransforms$TransformType[b.i.ordinal()] = 9;
            } catch (NoSuchFieldError e13) {
            }
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public static vl toMinecraft(LivingEntity.EquipmentSpot spot) throws MatchException {
        switch (spot) {
            case HEAD:
                return vl.f;
            case CHEST:
                return vl.e;
            case LEGS:
                return vl.d;
            case FEET:
                return vl.c;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    public static <T extends vg> int getPackedLight(T entity) {
        return getPackedLight(entity, Laby.labyAPI().minecraft().getPartialTicks());
    }

    public static <T extends vg> int getPackedLight(T entity, float delta) {
        return entity.av();
    }

    public static int getPackedLight(bsb level, et blockPos) {
        if (level.e(blockPos)) {
            return level.b(blockPos, 0);
        }
        return 0;
    }

    public static void drawTexturedModalRect(int x, int y, int spriteX, int spriteY, int spriteWidth, int spriteHeight) {
        bve var9 = bve.a();
        buk var10 = var9.c();
        var10.a(7, cdy.g);
        var10.b(x, y + spriteHeight, 0.0d).a(spriteX * 0.00390625f, (spriteY + spriteHeight) * 0.00390625f).d();
        var10.b(x + spriteWidth, y + spriteHeight, 0.0d).a((spriteX + spriteWidth) * 0.00390625f, (spriteY + spriteHeight) * 0.00390625f).d();
        var10.b(x + spriteWidth, y, 0.0d).a((spriteX + spriteWidth) * 0.00390625f, spriteY * 0.00390625f).d();
        var10.b(x, y, 0.0d).a(spriteX * 0.00390625f, spriteY * 0.00390625f).d();
        var9.b();
    }

    public static PlayerModel obtainPlayerModel(Player player) {
        bzf dispatcher = bib.z().ac();
        cct cctVarA = dispatcher.a((vg) player);
        if (!(cctVarA instanceof cct)) {
            throw new IllegalStateException("Renderer for the player could not be found");
        }
        cct renderPlayer = cctVarA;
        return renderPlayer.h();
    }

    public static cds toMinecraft(Object texture) {
        return TEXTURES.apply(texture);
    }

    public static void fireDelayedEvent(Supplier<Event> eventSupplier) {
        bib.z().a(() -> {
            return Laby.fireEvent((Event) eventSupplier.get());
        });
    }

    public static BlockState fromMinecraft(awt blockState, int x, int y, int z) {
        BlockState state = (BlockState) blockState;
        state.setCoordinates(x, y, z);
        return state;
    }

    public static vg unwrapEntity(vg entity) {
        if (entity instanceof abb) {
            abb part = (abb) entity;
            vg vgVar = part.a;
            if (vgVar instanceof vg) {
                return vgVar;
            }
        }
        return entity;
    }
}
