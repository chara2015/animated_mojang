package net.minecraft.commands.synchronization.brigadier;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.profiling.jfr.event.ChunkRegionIoEvent;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/StringArgumentSerializer.class */
public class StringArgumentSerializer implements ArgumentTypeInfo<StringArgumentType, Template> {

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/StringArgumentSerializer$Template.class */
    public final class Template implements ArgumentTypeInfo.Template<StringArgumentType> {
        final StringArgumentType.StringType type;

        public Template(StringArgumentType.StringType $$1) {
            this.type = $$1;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        /* JADX INFO: renamed from: instantiate, reason: merged with bridge method [inline-methods] */
        public StringArgumentType mo1326instantiate(CommandBuildContext $$0) throws MatchException {
            switch (AnonymousClass1.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[this.type.ordinal()]) {
                case 1:
                    return StringArgumentType.word();
                case 2:
                    return StringArgumentType.string();
                case 3:
                    return StringArgumentType.greedyString();
                default:
                    throw new MatchException((String) null, (Throwable) null);
            }
        }

        @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo.Template
        public ArgumentTypeInfo<StringArgumentType, ?> type() {
            return StringArgumentSerializer.this;
        }
    }

    /* JADX INFO: renamed from: net.minecraft.commands.synchronization.brigadier.StringArgumentSerializer$1, reason: invalid class name */
    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/commands/synchronization/brigadier/StringArgumentSerializer$1.class */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType = new int[StringArgumentType.StringType.values().length];

        static {
            try {
                $SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[StringArgumentType.StringType.SINGLE_WORD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[StringArgumentType.StringType.QUOTABLE_PHRASE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[StringArgumentType.StringType.GREEDY_PHRASE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
        $$1.writeEnum($$0.type);
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
        StringArgumentType.StringType $$1 = $$0.readEnum(StringArgumentType.StringType.class);
        return new Template($$1);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public void serializeToJson(Template $$0, JsonObject $$1) throws MatchException {
        String str;
        switch (AnonymousClass1.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[$$0.type.ordinal()]) {
            case 1:
                str = "word";
                break;
            case 2:
                str = "phrase";
                break;
            case 3:
                str = "greedy";
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        $$1.addProperty(ChunkRegionIoEvent.Fields.TYPE, str);
    }

    @Override // net.minecraft.commands.synchronization.ArgumentTypeInfo
    public Template unpack(StringArgumentType $$0) {
        return new Template($$0.getType());
    }
}
