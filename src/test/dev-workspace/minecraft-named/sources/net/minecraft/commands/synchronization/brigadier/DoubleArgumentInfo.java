package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentUtils;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/DoubleArgumentInfo.class */
public class DoubleArgumentInfo implements ArgumentTypeInfo<DoubleArgumentType, Template> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/DoubleArgumentInfo$Template.class */
    public final class Template implements ArgumentTypeInfo.Template<DoubleArgumentType> {
        final double min;
        final double max;

        Template(double $$1, double $$2) {
            this.min = $$1;
            this.max = $$2;
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        /* JADX INFO: renamed from: instantiate, reason: merged with bridge method [inline-methods] */
        public DoubleArgumentType mo1326instantiate(CommandBuildContext $$0) {
            return DoubleArgumentType.doubleArg(this.min, this.max);
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        public ArgumentTypeInfo<DoubleArgumentType, ?> type() {
            return DoubleArgumentInfo.this;
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
        boolean $$2 = $$0.min != -1.7976931348623157E308d;
        boolean $$3 = $$0.max != Double.MAX_VALUE;
        $$1.m1593writeByte(ArgumentUtils.createNumberFlags($$2, $$3));
        if ($$2) {
            $$1.m1582writeDouble($$0.min);
        }
        if ($$3) {
            $$1.m1582writeDouble($$0.max);
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
        byte $$1 = $$0.readByte();
        double $$2 = ArgumentUtils.numberHasMin($$1) ? $$0.readDouble() : -1.7976931348623157E308d;
        double $$3 = ArgumentUtils.numberHasMax($$1) ? $$0.readDouble() : Double.MAX_VALUE;
        return new Template($$2, $$3);
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToJson(Template $$0, JsonObject $$1) {
        if ($$0.min != -1.7976931348623157E308d) {
            $$1.addProperty("min", Double.valueOf($$0.min));
        }
        if ($$0.max != Double.MAX_VALUE) {
            $$1.addProperty("max", Double.valueOf($$0.max));
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template unpack(DoubleArgumentType $$0) {
        return new Template($$0.getMinimum(), $$0.getMaximum());
    }
}
