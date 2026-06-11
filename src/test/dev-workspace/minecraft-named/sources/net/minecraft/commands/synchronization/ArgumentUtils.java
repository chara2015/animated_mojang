package net.minecraft.commands.synchronization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.tree.ArgumentCommandNode;
import com.mojang.brigadier.tree.CommandNode;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mojang.brigadier.tree.RootCommandNode;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.permissions.PermissionCheck;
import net.minecraft.server.permissions.PermissionProviderCheck;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/ArgumentUtils.class */
public class ArgumentUtils {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final byte NUMBER_FLAG_MIN = 1;
    private static final byte NUMBER_FLAG_MAX = 2;

    public static int createNumberFlags(boolean $$0, boolean $$1) {
        int $$2 = 0;
        if ($$0) {
            $$2 = 0 | 1;
        }
        if ($$1) {
            $$2 |= 2;
        }
        return $$2;
    }

    public static boolean numberHasMin(byte $$0) {
        return ($$0 & 1) != 0;
    }

    public static boolean numberHasMax(byte $$0) {
        return ($$0 & 2) != 0;
    }

    private static <A extends ArgumentType<?>, T extends ArgumentTypeInfo.Template<A>> void serializeArgumentCap(JsonObject $$0, ArgumentTypeInfo<A, T> $$1, ArgumentTypeInfo.Template<A> $$2) {
        $$1.serializeToJson($$2, $$0);
    }

    private static <T extends ArgumentType<?>> void serializeArgumentToJson(JsonObject $$0, T $$1) {
        ArgumentTypeInfo.Template<T> $$2 = ArgumentTypeInfos.unpack($$1);
        $$0.addProperty(ChunkRegionIoEvent.Fields.TYPE, "argument");
        $$0.addProperty("parser", String.valueOf(BuiltInRegistries.COMMAND_ARGUMENT_TYPE.getKey($$2.type())));
        JsonObject $$3 = new JsonObject();
        serializeArgumentCap($$3, $$2.type(), $$2);
        if (!$$3.isEmpty()) {
            $$0.add("properties", $$3);
        }
    }

    public static <S> JsonObject serializeNodeToJson(CommandDispatcher<S> $$0, CommandNode<S> $$1) {
        JsonObject $$2 = new JsonObject();
        Objects.requireNonNull($$1);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), RootCommandNode.class, LiteralCommandNode.class, ArgumentCommandNode.class).dynamicInvoker().invoke($$1, 0) /* invoke-custom */) {
            case 0:
                $$2.addProperty(ChunkRegionIoEvent.Fields.TYPE, "root");
                break;
            case 1:
                $$2.addProperty(ChunkRegionIoEvent.Fields.TYPE, "literal");
                break;
            case 2:
                ArgumentCommandNode<S, ?> $$5 = (ArgumentCommandNode) $$1;
                serializeArgumentToJson($$2, $$5.getType());
                break;
            default:
                LOGGER.error("Could not serialize node {} ({})!", $$1, $$1.getClass());
                $$2.addProperty(ChunkRegionIoEvent.Fields.TYPE, "unknown");
                break;
        }
        Collection<CommandNode<S>> $$6 = $$1.getChildren();
        if (!$$6.isEmpty()) {
            JsonObject $$7 = new JsonObject();
            for (CommandNode<S> $$8 : $$6) {
                $$7.add($$8.getName(), serializeNodeToJson($$0, $$8));
            }
            $$2.add("children", $$7);
        }
        if ($$1.getCommand() != null) {
            $$2.addProperty("executable", true);
        }
        Predicate requirement = $$1.getRequirement();
        if (requirement instanceof PermissionProviderCheck) {
            PermissionProviderCheck<?> $$9 = (PermissionProviderCheck) requirement;
            JsonElement $$10 = (JsonElement) PermissionCheck.CODEC.encodeStart(JsonOps.INSTANCE, $$9.test()).getOrThrow($$02 -> {
                return new IllegalStateException("Failed to serialize requirement: " + $$02);
            });
            $$2.add("permissions", $$10);
        }
        if ($$1.getRedirect() != null) {
            Collection<String> $$11 = $$0.getPath($$1.getRedirect());
            if (!$$11.isEmpty()) {
                JsonArray $$12 = new JsonArray();
                for (String $$13 : $$11) {
                    $$12.add($$13);
                }
                $$2.add("redirect", $$12);
            }
        }
        return $$2;
    }

    public static <T> Set<ArgumentType<?>> findUsedArgumentTypes(CommandNode<T> $$0) {
        ReferenceOpenHashSet referenceOpenHashSet = new ReferenceOpenHashSet();
        Set<ArgumentType<?>> $$2 = new HashSet<>();
        findUsedArgumentTypes($$0, $$2, referenceOpenHashSet);
        return $$2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static <T> void findUsedArgumentTypes(CommandNode<T> $$0, Set<ArgumentType<?>> $$1, Set<CommandNode<T>> $$2) {
        if (!$$2.add($$0)) {
            return;
        }
        if ($$0 instanceof ArgumentCommandNode) {
            ArgumentCommandNode<T, ?> $$3 = (ArgumentCommandNode) $$0;
            $$1.add($$3.getType());
        }
        $$0.getChildren().forEach($$22 -> {
            findUsedArgumentTypes($$22, $$1, $$2);
        });
        CommandNode<T> $$4 = $$0.getRedirect();
        if ($$4 != null) {
            findUsedArgumentTypes($$4, $$1, $$2);
        }
    }
}
