package net.minecraft.world.scores;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import java.util.Collection;
import java.util.function.IntFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Team.class */
public abstract class Team {
    public abstract String getName();

    public abstract MutableComponent getFormattedName(Component component);

    public abstract boolean canSeeFriendlyInvisibles();

    public abstract boolean isAllowFriendlyFire();

    public abstract Visibility getNameTagVisibility();

    public abstract ChatFormatting getColor();

    public abstract Collection<String> getPlayers();

    public abstract Visibility getDeathMessageVisibility();

    public abstract CollisionRule getCollisionRule();

    public boolean isAlliedTo(Team $$0) {
        if ($$0 != null && this == $$0) {
            return true;
        }
        return false;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Team$Visibility.class */
    public enum Visibility implements StringRepresentable {
        ALWAYS("always", 0),
        NEVER("never", 1),
        HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
        HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);

        public static final Codec<Visibility> CODEC = StringRepresentable.fromEnum(Visibility::values);
        private static final IntFunction<Visibility> BY_ID = ByIdMap.continuous($$0 -> {
            return $$0.id;
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, Visibility> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, $$0 -> {
            return $$0.id;
        });
        public final String name;
        public final int id;

        Visibility(String $$0, int $$1) {
            this.name = $$0;
            this.id = $$1;
        }

        public Component getDisplayName() {
            return Component.translatable("team.visibility." + this.name);
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/scores/Team$CollisionRule.class */
    public enum CollisionRule implements StringRepresentable {
        ALWAYS("always", 0),
        NEVER("never", 1),
        PUSH_OTHER_TEAMS("pushOtherTeams", 2),
        PUSH_OWN_TEAM("pushOwnTeam", 3);

        public static final Codec<CollisionRule> CODEC = StringRepresentable.fromEnum(CollisionRule::values);
        private static final IntFunction<CollisionRule> BY_ID = ByIdMap.continuous($$0 -> {
            return $$0.id;
        }, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
        public static final StreamCodec<ByteBuf, CollisionRule> STREAM_CODEC = ByteBufCodecs.idMapper(BY_ID, $$0 -> {
            return $$0.id;
        });
        public final String name;
        public final int id;

        CollisionRule(String $$0, int $$1) {
            this.name = $$0;
            this.id = $$1;
        }

        public Component getDisplayName() {
            return Component.translatable("team.collision." + this.name);
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }
    }
}
