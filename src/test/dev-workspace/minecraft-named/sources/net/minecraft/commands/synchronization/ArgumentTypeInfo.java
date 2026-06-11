package net.minecraft.commands.synchronization;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo.Template;
import net.minecraft.network.FriendlyByteBuf;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/ArgumentTypeInfo.class */
public interface ArgumentTypeInfo<A extends ArgumentType<?>, T extends Template<A>> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/ArgumentTypeInfo$Template.class */
    public interface Template<A extends ArgumentType<?>> {
        /* JADX INFO: renamed from: instantiate */
        A mo1326instantiate(CommandBuildContext commandBuildContext);

        ArgumentTypeInfo<A, ?> type();
    }

    void serializeToNetwork(T t, FriendlyByteBuf friendlyByteBuf);

    T deserializeFromNetwork(FriendlyByteBuf friendlyByteBuf);

    void serializeToJson(T t, JsonObject jsonObject);

    T unpack(A a);
}
