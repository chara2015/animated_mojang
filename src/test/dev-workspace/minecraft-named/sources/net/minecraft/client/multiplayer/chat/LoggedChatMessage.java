package net.minecraft.client.multiplayer.chat;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.client.multiplayer.chat.LoggedChatEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/LoggedChatMessage.class */
public interface LoggedChatMessage extends LoggedChatEvent {
    Component toContentComponent();

    boolean canReport(UUID uuid);

    static Player player(GameProfile $$0, PlayerChatMessage $$1, ChatTrustLevel $$2) {
        return new Player($$0, $$1, $$2);
    }

    static System system(Component $$0, Instant $$1) {
        return new System($$0, $$1);
    }

    default Component toNarrationComponent() {
        return toContentComponent();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/LoggedChatMessage$Player.class */
    public static final class Player extends Record implements LoggedChatMessage {
        private final GameProfile profile;
        private final PlayerChatMessage message;
        private final ChatTrustLevel trustLevel;
        public static final MapCodec<Player> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.AUTHLIB_GAME_PROFILE.fieldOf("profile").forGetter((v0) -> {
                return v0.profile();
            }), PlayerChatMessage.MAP_CODEC.forGetter((v0) -> {
                return v0.message();
            }), ChatTrustLevel.CODEC.optionalFieldOf("trust_level", ChatTrustLevel.SECURE).forGetter((v0) -> {
                return v0.trustLevel();
            })).apply($$0, Player::new);
        });
        private static final DateTimeFormatter TIME_FORMATTER = Util.localizedDateFormatter(FormatStyle.SHORT);

        public Player(GameProfile $$0, PlayerChatMessage $$1, ChatTrustLevel $$2) {
            this.profile = $$0;
            this.message = $$1;
            this.trustLevel = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Player.class), Player.class, "profile;message;trustLevel", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->profile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->message:Lnet/minecraft/network/chat/PlayerChatMessage;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->trustLevel:Lnet/minecraft/client/multiplayer/chat/ChatTrustLevel;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Player.class), Player.class, "profile;message;trustLevel", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->profile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->message:Lnet/minecraft/network/chat/PlayerChatMessage;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->trustLevel:Lnet/minecraft/client/multiplayer/chat/ChatTrustLevel;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Player.class, Object.class), Player.class, "profile;message;trustLevel", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->profile:Lcom/mojang/authlib/GameProfile;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->message:Lnet/minecraft/network/chat/PlayerChatMessage;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$Player;->trustLevel:Lnet/minecraft/client/multiplayer/chat/ChatTrustLevel;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public GameProfile profile() {
            return this.profile;
        }

        public PlayerChatMessage message() {
            return this.message;
        }

        public ChatTrustLevel trustLevel() {
            return this.trustLevel;
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        @Override // net.minecraft.client.multiplayer.chat.LoggedChatMessage
        public Component toContentComponent() throws MatchException {
            if (!this.message.filterMask().isEmpty()) {
                Component $$0 = this.message.filterMask().applyWithFormatting(this.message.signedContent());
                return $$0 != null ? $$0 : Component.empty();
            }
            return this.message.decoratedContent();
        }

        /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
        @Override // net.minecraft.client.multiplayer.chat.LoggedChatMessage
        public Component toNarrationComponent() throws MatchException {
            Component $$0 = toContentComponent();
            Component $$1 = getTimeComponent();
            return Component.translatable("gui.chatSelection.message.narrate", this.profile.name(), $$0, $$1);
        }

        public Component toHeadingComponent() {
            Component $$0 = getTimeComponent();
            return Component.translatable("gui.chatSelection.heading", this.profile.name(), $$0);
        }

        private Component getTimeComponent() {
            ZonedDateTime $$0 = ZonedDateTime.ofInstant(this.message.timeStamp(), ZoneId.systemDefault());
            return Component.literal($$0.format(TIME_FORMATTER)).withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY);
        }

        @Override // net.minecraft.client.multiplayer.chat.LoggedChatMessage
        public boolean canReport(UUID $$0) {
            return this.message.hasSignatureFrom($$0);
        }

        public UUID profileId() {
            return this.profile.id();
        }

        @Override // net.minecraft.client.multiplayer.chat.LoggedChatEvent
        public LoggedChatEvent.Type type() {
            return LoggedChatEvent.Type.PLAYER;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/LoggedChatMessage$System.class */
    public static final class System extends Record implements LoggedChatMessage {
        private final Component message;
        private final Instant timeStamp;
        public static final MapCodec<System> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ComponentSerialization.CODEC.fieldOf("message").forGetter((v0) -> {
                return v0.message();
            }), ExtraCodecs.INSTANT_ISO8601.fieldOf("time_stamp").forGetter((v0) -> {
                return v0.timeStamp();
            })).apply($$0, System::new);
        });

        public System(Component $$0, Instant $$1) {
            this.message = $$0;
            this.timeStamp = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, System.class), System.class, "message;timeStamp", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->message:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->timeStamp:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, System.class), System.class, "message;timeStamp", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->message:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->timeStamp:Ljava/time/Instant;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, System.class, Object.class), System.class, "message;timeStamp", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->message:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/multiplayer/chat/LoggedChatMessage$System;->timeStamp:Ljava/time/Instant;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Component message() {
            return this.message;
        }

        public Instant timeStamp() {
            return this.timeStamp;
        }

        @Override // net.minecraft.client.multiplayer.chat.LoggedChatMessage
        public Component toContentComponent() {
            return this.message;
        }

        @Override // net.minecraft.client.multiplayer.chat.LoggedChatMessage
        public boolean canReport(UUID $$0) {
            return false;
        }

        @Override // net.minecraft.client.multiplayer.chat.LoggedChatEvent
        public LoggedChatEvent.Type type() {
            return LoggedChatEvent.Type.SYSTEM;
        }
    }
}
