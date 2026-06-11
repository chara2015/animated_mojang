package net.labymod.v1_21_3.mixins.world.level.block.entity;

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

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/world/level/block/entity/MixinSignBlockEntity.class */
@Mixin({dwk.class})
public abstract class MixinSignBlockEntity implements SignBlockEntity {

    @Shadow
    private dwl e;

    @Shadow
    private dwl f;

    @Insert(method = {"loadAdditional"}, at = @At("TAIL"))
    private void labyMod$fireTileEntityPreLoadEvent(ux tag, a provider, InsertInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound) CastUtil.cast(tag)));
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public float getRotationYaw() throws MatchException {
        dys dysVar;
        dyu dyuVar;
        dux entity = (dux) this;
        dxv state = entity.m();
        dkm block = state.b();
        if ((block instanceof dtp) || (block instanceof dto)) {
            if (block instanceof dtp) {
                dysVar = dtp.b;
            } else {
                dysVar = dto.b;
            }
            jm facing = state.c(dysVar);
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
        if ((block instanceof dsk) || (block instanceof dlm)) {
            if (block instanceof dsk) {
                dyuVar = dsk.b;
            } else {
                dyuVar = dlm.b;
            }
            Integer rotation = (Integer) state.c(dyuVar);
            return 180.0f - ((rotation.intValue() * 360) / 16.0f);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: net.labymod.v1_21_3.mixins.world.level.block.entity.MixinSignBlockEntity$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_21_3/mixins/world/level/block/entity/MixinSignBlockEntity$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$core$Direction = new int[jm.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.d.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$core$Direction[jm.e.ordinal()] = 6;
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
        xv[] messages = (side == SignBlockEntity.SignSide.FRONT ? this.e : this.f).b(false);
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
        dux entity = (dux) this;
        dxv state = entity.m();
        dkm block = state.b();
        if (block instanceof dsk) {
            return SignBlockEntity.SignType.STANDING_SIGN;
        }
        if (block instanceof dtp) {
            return SignBlockEntity.SignType.WALL_SIGN;
        }
        if (block instanceof dlm) {
            return SignBlockEntity.SignType.CEILING_HANGING_SIGN;
        }
        if (block instanceof dto) {
            return SignBlockEntity.SignType.WALL_HANGING_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
