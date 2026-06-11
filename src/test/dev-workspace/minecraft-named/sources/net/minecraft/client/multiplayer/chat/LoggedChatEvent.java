package net.minecraft.client.multiplayer.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.chat.LoggedChatMessage;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/LoggedChatEvent.class */
public interface LoggedChatEvent {
    public static final Codec<LoggedChatEvent> CODEC = StringRepresentable.fromEnum(Type::values).dispatch((v0) -> {
        return v0.type();
    }, (v0) -> {
        return v0.codec();
    });

    Type type();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/multiplayer/chat/LoggedChatEvent$Type.class */
    public enum Type implements StringRepresentable {
        PLAYER("player", () -> {
            return LoggedChatMessage.Player.CODEC;
        }),
        SYSTEM("system", () -> {
            return LoggedChatMessage.System.CODEC;
        });

        private final String serializedName;
        private final Supplier<MapCodec<? extends LoggedChatEvent>> codec;

        Type(String $$0, Supplier supplier) {
            this.serializedName = $$0;
            this.codec = supplier;
        }

        private MapCodec<? extends LoggedChatEvent> codec() {
            return this.codec.get();
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.serializedName;
        }
    }
}
