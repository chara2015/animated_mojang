package net.labymod.v1_20_4.mixins.world.level.block.entity;

import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.CastUtil;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/world/level/block/entity/MixinSignBlockEntity.class */
@Mixin({die.class})
public abstract class MixinSignBlockEntity implements SignBlockEntity {

    @Shadow
    private dif e;

    @Shadow
    private dif f;

    @Insert(method = {"load"}, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(sn tag, InsertInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public float getRotationYaw() throws MatchException {
        dkb dkbVar;
        dkh dkhVar;
        dgv entity = (dgv) this;
        djh state = entity.r();
        cwq block = state.b();
        if ((block instanceof dfo) || (block instanceof dfn)) {
            if (block instanceof dfo) {
                dkbVar = dfo.b;
            } else {
                dkbVar = dfn.b;
            }
            ic facing = state.c(dkbVar);
            switch (AnonymousClass1.$SwitchMap$net$minecraft$core$Direction[facing.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return 0.0f;
                case 4:
                    return -90.0f;
                case 5:
                    return 180.0f;
                case 6:
                    return 90.0f;
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }
        if ((block instanceof dek) || (block instanceof cxp)) {
            if (block instanceof dek) {
                dkhVar = dek.b;
            } else {
                dkhVar = cxp.b;
            }
            Integer rotation = (Integer) state.c(dkhVar);
            return 180.0f - ((rotation.intValue() * 360) / 16.0f);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: net.labymod.v1_20_4.mixins.world.level.block.entity.MixinSignBlockEntity$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_20_4/mixins/world/level/block/entity/MixinSignBlockEntity$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction = new int[ic.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.d.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[ic.e.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public boolean hasSide(@NotNull SignBlockEntity.SignSide side) {
        return side == SignBlockEntity.SignSide.FRONT || side == SignBlockEntity.SignSide.BACK;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    @NotNull
    public Component[] getLines(@NotNull SignBlockEntity.SignSide side) {
        Component componentEmpty;
        if (side != SignBlockEntity.SignSide.FRONT && side != SignBlockEntity.SignSide.BACK) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        vf[] messages = (side == SignBlockEntity.SignSide.FRONT ? this.e : this.f).b(false);
        Component[] lines = new Component[messages.length];
        for (int i = 0; i < lines.length; i++) {
            int i2 = i;
            if (messages[i] != null) {
                componentEmpty = Laby.references().componentMapper().fromMinecraftComponent(messages[i]);
            } else {
                componentEmpty = Component.empty();
            }
            lines[i2] = componentEmpty;
        }
        return lines;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public SignBlockEntity.SignType getType() {
        dgv entity = (dgv) this;
        djh state = entity.r();
        cwq block = state.b();
        if (block instanceof dek) {
            return SignBlockEntity.SignType.STANDING_SIGN;
        }
        if (block instanceof dfo) {
            return SignBlockEntity.SignType.WALL_SIGN;
        }
        if (block instanceof cxp) {
            return SignBlockEntity.SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof dfn) {
            return SignBlockEntity.SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
