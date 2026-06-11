package net.labymod.api.configuration.labymod.chat.config;

import java.util.Objects;
import java.util.UUID;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.property.NotNullPropertyConvention;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/config/RootChatTabConfig.class */
public class RootChatTabConfig extends Config {
    private final ConfigProperty<UUID> uniqueId;
    private final GeneralChatTabConfig config;
    private final ConfigProperty<Type> type;
    private final ConfigProperty<Integer> index;

    public RootChatTabConfig() {
        this.uniqueId = ConfigProperty.create(UUID.randomUUID());
        this.type = new ConfigProperty<>(Type.SERVER, new NotNullPropertyConvention(Type.SERVER));
        this.index = ConfigProperty.create(-1);
        this.config = new GeneralChatTabConfig("");
    }

    public RootChatTabConfig(int index, Type type, GeneralChatTabConfig config) {
        this.uniqueId = ConfigProperty.create(UUID.randomUUID());
        this.type = new ConfigProperty<>(Type.SERVER, new NotNullPropertyConvention(Type.SERVER));
        this.index = ConfigProperty.create(-1);
        this.config = config;
        this.type.set(type);
        this.index.set(Integer.valueOf(index));
    }

    public RootChatTabConfig(Type type, String name) {
        this(0, type, new GeneralChatTabConfig(name));
    }

    public ConfigProperty<Integer> index() {
        return this.index;
    }

    public ConfigProperty<Type> type() {
        return this.type;
    }

    public GeneralChatTabConfig config() {
        return this.config;
    }

    public UUID getUniqueID() {
        return this.uniqueId.get();
    }

    @Override // net.labymod.api.configuration.loader.Config
    public int getConfigVersion() {
        return 2;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/config/RootChatTabConfig$Type.class */
    public static class Type {
        public static final Type SERVER = of("SERVER");
        public static final Type CUSTOM = of("CUSTOM");
        private final String identifier;

        private Type(@NotNull String identifier) {
            Objects.requireNonNull(identifier, "Identifier cannot be null!");
            this.identifier = identifier;
        }

        public static Type of(@NotNull String identifier) {
            return new Type(identifier);
        }

        @NotNull
        public String getIdentifier() {
            return this.identifier;
        }

        public String toString() {
            return "Type{identifier='" + this.identifier + "'}";
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Type)) {
                return false;
            }
            Type type = (Type) o;
            return this.identifier.equals(type.identifier);
        }

        public int hashCode() {
            return this.identifier.hashCode();
        }
    }
}
