package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ChatType.class */
public final class ChatType extends Record {
    private final ChatTypeDecoration chat;
    private final ChatTypeDecoration narration;
    public static final Codec<ChatType> DIRECT_CODEC = RecordCodecBuilder.create($$0 -> {
        return $$0.group(ChatTypeDecoration.CODEC.fieldOf("chat").forGetter((v0) -> {
            return v0.chat();
        }), ChatTypeDecoration.CODEC.fieldOf("narration").forGetter((v0) -> {
            return v0.narration();
        })).apply($$0, ChatType::new);
    });
    public static final StreamCodec<RegistryFriendlyByteBuf, ChatType> DIRECT_STREAM_CODEC = StreamCodec.composite(ChatTypeDecoration.STREAM_CODEC, (v0) -> {
        return v0.chat();
    }, ChatTypeDecoration.STREAM_CODEC, (v0) -> {
        return v0.narration();
    }, ChatType::new);
    public static final StreamCodec<RegistryFriendlyByteBuf, Holder<ChatType>> STREAM_CODEC = ByteBufCodecs.holder(Registries.CHAT_TYPE, DIRECT_STREAM_CODEC);
    public static final ChatTypeDecoration DEFAULT_CHAT_DECORATION = ChatTypeDecoration.withSender("chat.type.text");
    public static final ResourceKey<ChatType> CHAT = create("chat");
    public static final ResourceKey<ChatType> SAY_COMMAND = create("say_command");
    public static final ResourceKey<ChatType> MSG_COMMAND_INCOMING = create("msg_command_incoming");
    public static final ResourceKey<ChatType> MSG_COMMAND_OUTGOING = create("msg_command_outgoing");
    public static final ResourceKey<ChatType> TEAM_MSG_COMMAND_INCOMING = create("team_msg_command_incoming");
    public static final ResourceKey<ChatType> TEAM_MSG_COMMAND_OUTGOING = create("team_msg_command_outgoing");
    public static final ResourceKey<ChatType> EMOTE_COMMAND = create("emote_command");

    public ChatType(ChatTypeDecoration $$0, ChatTypeDecoration $$1) {
        this.chat = $$0;
        this.narration = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChatType.class), ChatType.class, "chat;narration", "FIELD:Lnet/minecraft/network/chat/ChatType;->chat:Lnet/minecraft/network/chat/ChatTypeDecoration;", "FIELD:Lnet/minecraft/network/chat/ChatType;->narration:Lnet/minecraft/network/chat/ChatTypeDecoration;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChatType.class), ChatType.class, "chat;narration", "FIELD:Lnet/minecraft/network/chat/ChatType;->chat:Lnet/minecraft/network/chat/ChatTypeDecoration;", "FIELD:Lnet/minecraft/network/chat/ChatType;->narration:Lnet/minecraft/network/chat/ChatTypeDecoration;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChatType.class, Object.class), ChatType.class, "chat;narration", "FIELD:Lnet/minecraft/network/chat/ChatType;->chat:Lnet/minecraft/network/chat/ChatTypeDecoration;", "FIELD:Lnet/minecraft/network/chat/ChatType;->narration:Lnet/minecraft/network/chat/ChatTypeDecoration;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public ChatTypeDecoration chat() {
        return this.chat;
    }

    public ChatTypeDecoration narration() {
        return this.narration;
    }

    private static ResourceKey<ChatType> create(String $$0) {
        return ResourceKey.create(Registries.CHAT_TYPE, Identifier.withDefaultNamespace($$0));
    }

    public static void bootstrap(BootstrapContext<ChatType> $$0) {
        $$0.register(CHAT, new ChatType(DEFAULT_CHAT_DECORATION, ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(SAY_COMMAND, new ChatType(ChatTypeDecoration.withSender("chat.type.announcement"), ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(MSG_COMMAND_INCOMING, new ChatType(ChatTypeDecoration.incomingDirectMessage("commands.message.display.incoming"), ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(MSG_COMMAND_OUTGOING, new ChatType(ChatTypeDecoration.outgoingDirectMessage("commands.message.display.outgoing"), ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(TEAM_MSG_COMMAND_INCOMING, new ChatType(ChatTypeDecoration.teamMessage("chat.type.team.text"), ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(TEAM_MSG_COMMAND_OUTGOING, new ChatType(ChatTypeDecoration.teamMessage("chat.type.team.sent"), ChatTypeDecoration.withSender("chat.type.text.narrate")));
        $$0.register(EMOTE_COMMAND, new ChatType(ChatTypeDecoration.withSender("chat.type.emote"), ChatTypeDecoration.withSender("chat.type.emote")));
    }

    public static Bound bind(ResourceKey<ChatType> $$0, Entity $$1) {
        return bind($$0, $$1.level().registryAccess(), $$1.getDisplayName());
    }

    public static Bound bind(ResourceKey<ChatType> $$0, CommandSourceStack $$1) {
        return bind($$0, $$1.registryAccess(), $$1.getDisplayName());
    }

    public static Bound bind(ResourceKey<ChatType> $$0, RegistryAccess $$1, Component $$2) {
        Registry<ChatType> $$3 = $$1.lookupOrThrow((ResourceKey) Registries.CHAT_TYPE);
        return new Bound($$3.getOrThrow($$0), $$2);
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ChatType$Bound.class */
    public static final class Bound extends Record {
        private final Holder<ChatType> chatType;
        private final Component name;
        private final Optional<Component> targetName;
        public static final StreamCodec<RegistryFriendlyByteBuf, Bound> STREAM_CODEC = StreamCodec.composite(ChatType.STREAM_CODEC, (v0) -> {
            return v0.chatType();
        }, ComponentSerialization.TRUSTED_STREAM_CODEC, (v0) -> {
            return v0.name();
        }, ComponentSerialization.TRUSTED_OPTIONAL_STREAM_CODEC, (v0) -> {
            return v0.targetName();
        }, Bound::new);

        public Bound(Holder<ChatType> $$0, Component $$1, Optional<Component> $$2) {
            this.chatType = $$0;
            this.name = $$1;
            this.targetName = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Bound.class), Bound.class, "chatType;name;targetName", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->chatType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->name:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->targetName:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Bound.class), Bound.class, "chatType;name;targetName", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->chatType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->name:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->targetName:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Bound.class, Object.class), Bound.class, "chatType;name;targetName", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->chatType:Lnet/minecraft/core/Holder;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->name:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/network/chat/ChatType$Bound;->targetName:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<ChatType> chatType() {
            return this.chatType;
        }

        public Component name() {
            return this.name;
        }

        public Optional<Component> targetName() {
            return this.targetName;
        }

        Bound(Holder<ChatType> $$0, Component $$1) {
            this($$0, $$1, Optional.empty());
        }

        public Component decorate(Component $$0) {
            return this.chatType.value().chat().decorate($$0, this);
        }

        public Component decorateNarration(Component $$0) {
            return this.chatType.value().narration().decorate($$0, this);
        }

        public Bound withTargetName(Component $$0) {
            return new Bound(this.chatType, this.name, Optional.of($$0));
        }
    }
}
