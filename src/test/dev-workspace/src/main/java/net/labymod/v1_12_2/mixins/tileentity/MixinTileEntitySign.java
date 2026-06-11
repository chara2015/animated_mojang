package net.labymod.v1_12_2.mixins.tileentity;

import net.labymod.api.Laby;
import net.labymod.api.client.blockentity.SignBlockEntity;
import net.labymod.api.client.component.Component;
import net.labymod.api.event.client.blockentity.SignBlockEntityPostLoadEvent;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.ThreadSafe;
import net.labymod.api.volt.annotation.Insert;
import net.labymod.api.volt.callback.InsertInfo;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/tileentity/MixinTileEntitySign.class */
@Mixin({awc.class})
public abstract class MixinTileEntitySign implements SignBlockEntity {

    @Shadow
    @Final
    public hh[] a;

    @Insert(method = {"readFromNBT"}, at = @At("TAIL"))
    private void labyMod$fireTileEntityPostLoadEvent(fy tag, InsertInfo ci) {
        if (ThreadSafe.isRenderThread()) {
            Laby.fireEvent(new SignBlockEntityPostLoadEvent(this, (NBTTagCompound) tag));
        }
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public float getRotationYaw() {
        Integer rotation;
        avj entity = (avj) this;
        aow block = entity.x();
        awt state = entity.D().o(entity.w());
        if (block == aox.ax) {
            fa facing = state.c(auw.b);
            switch (AnonymousClass1.$SwitchMap$net$minecraft$util$EnumFacing[facing.ordinal()]) {
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
            }
        }
        if (block == aox.an && (rotation = (Integer) state.c(aue.b)) != null) {
            return 180.0f - ((rotation.intValue() * 360) / 16.0f);
        }
        return 0.0f;
    }

    /* JADX INFO: renamed from: net.labymod.v1_12_2.mixins.tileentity.MixinTileEntitySign$1, reason: invalid class name */
    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/mixins/tileentity/MixinTileEntitySign$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$net$minecraft$util$EnumFacing = new int[fa.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.c.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.b.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.a.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.f.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.d.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$net$minecraft$util$EnumFacing[fa.e.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public boolean hasSide(@NotNull SignBlockEntity.SignSide side) {
        return side == SignBlockEntity.SignSide.FRONT;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    @NotNull
    public Component[] getLines(@NotNull SignBlockEntity.SignSide side) {
        Component componentEmpty;
        if (side != SignBlockEntity.SignSide.FRONT) {
            throw new IllegalArgumentException("Unsupported side: " + String.valueOf(side));
        }
        Component[] lines = new Component[this.a.length];
        for (int i = 0; i < lines.length; i++) {
            int i2 = i;
            if (this.a[i] != null) {
                componentEmpty = Laby.references().componentMapper().fromMinecraftComponent(this.a[i]);
            } else {
                componentEmpty = Component.empty();
            }
            lines[i2] = componentEmpty;
        }
        return lines;
    }

    @Override // net.labymod.api.client.blockentity.SignBlockEntity
    public SignBlockEntity.SignType getType() {
        avj entity = (avj) this;
        aow block = entity.x();
        if (block == aox.an) {
            return SignBlockEntity.SignType.STANDING_SIGN;
        }
        if (block == aox.ax) {
            return SignBlockEntity.SignType.WALL_SIGN;
        }
        throw new IllegalArgumentException("Unknown sign block: " + String.valueOf(block));
    }
}
