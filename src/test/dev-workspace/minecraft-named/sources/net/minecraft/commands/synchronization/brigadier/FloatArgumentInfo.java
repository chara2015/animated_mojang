package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/FloatArgumentInfo.class */
public class FloatArgumentInfo implements ArgumentTypeInfo<FloatArgumentType, Template> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/FloatArgumentInfo$Template.class */
    public final class Template implements ArgumentTypeInfo.Template<FloatArgumentType> {
        final float min;
        final float max;

        Template(float $$1, float $$2) {
            this.min = $$1;
            this.max = $$2;
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        /* JADX INFO: renamed from: instantiate, reason: merged with bridge method [inline-methods] */
        public FloatArgumentType mo1326instantiate(CommandBuildContext $$0) {
            return FloatArgumentType.floatArg(this.min, this.max);
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        public ArgumentTypeInfo<FloatArgumentType, ?> type() {
            return FloatArgumentInfo.this;
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
        boolean $$2 = $$0.min != -3.4028235E38f;
        boolean $$3 = $$0.max != Float.MAX_VALUE;
        $$1.m1593writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
        if ($$2) {
            $$1.m1583writeFloat($$0.min);
        }
        if ($$3) {
            $$1.m1583writeFloat($$0.max);
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
        byte $$1 = $$0.readByte();
        float $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readFloat() : -3.4028235E38f;
        float $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readFloat() : Float.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToJson(Template $$0, JsonObject $$1) {
        if ($$0.min != -3.4028235E38f) {
            $$1.addProperty("min", Float.valueOf($$0.min));
        }
        if ($$0.max != Float.MAX_VALUE) {
            $$1.addProperty("max", Float.valueOf($$0.max));
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template unpack(FloatArgumentType $$0) {
        return new Template($$0.getMinimum(), $$0.getMaximum());
    }
}
