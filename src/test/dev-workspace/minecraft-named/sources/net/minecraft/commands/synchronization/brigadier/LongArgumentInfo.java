package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.LongArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/LongArgumentInfo.class */
public class LongArgumentInfo implements ArgumentTypeInfo<LongArgumentType, Template> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/LongArgumentInfo$Template.class */
    public final class Template implements ArgumentTypeInfo.Template<LongArgumentType> {
        final long min;
        final long max;

        Template(long $$1, long $$2) {
            this.min = $$1;
            this.max = $$2;
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        /* JADX INFO: renamed from: instantiate, reason: merged with bridge method [inline-methods] */
        public LongArgumentType mo1326instantiate(CommandBuildContext $$0) {
            return LongArgumentType.longArg(this.min, this.max);
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        public ArgumentTypeInfo<LongArgumentType, ?> type() {
            return LongArgumentInfo.this;
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
        boolean $$2 = $$0.min != Long.MIN_VALUE;
        boolean $$3 = $$0.max != DynamicGraphMinFixedPoint.SOURCE;
        $$1.m1593writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
        if ($$2) {
            $$1.m1586writeLong($$0.min);
        }
        if ($$3) {
            $$1.m1586writeLong($$0.max);
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
        byte $$1 = $$0.readByte();
        long $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readLong() : Long.MIN_VALUE;
        long $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readLong() : DynamicGraphMinFixedPoint.SOURCE;
        return new Template($$2, $$3);
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToJson(Template $$0, JsonObject $$1) {
        if ($$0.min != Long.MIN_VALUE) {
            $$1.addProperty("min", Long.valueOf($$0.min));
        }
        if ($$0.max != DynamicGraphMinFixedPoint.SOURCE) {
            $$1.addProperty("max", Long.valueOf($$0.max));
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template unpack(LongArgumentType $$0) {
        return new Template($$0.getMinimum(), $$0.getMaximum());
    }
}
