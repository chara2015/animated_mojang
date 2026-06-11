package net.minecraft.world.level.gamerules;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.Objects;
import java.util.function.ToIntFunction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.flag.FeatureElement;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.level.gamerules.GameRules;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/world/level/gamerules/GameRule.class */
public final class GameRule<T> implements FeatureElement {
    private final GameRuleCategory category;
    private final GameRuleType gameRuleType;
    private final ArgumentType<T> argument;
    private final GameRules.VisitorCaller<T> visitorCaller;
    private final Codec<T> valueCodec;
    private final ToIntFunction<T> commandResultFunction;
    private final T defaultValue;
    private final FeatureFlagSet requiredFeatures;

    public GameRule(GameRuleCategory $$0, GameRuleType $$1, ArgumentType<T> $$2, GameRules.VisitorCaller<T> $$3, Codec<T> $$4, ToIntFunction<T> $$5, T $$6, FeatureFlagSet $$7) {
        this.category = $$0;
        this.gameRuleType = $$1;
        this.argument = $$2;
        this.visitorCaller = $$3;
        this.valueCodec = $$4;
        this.commandResultFunction = $$5;
        this.defaultValue = $$6;
        this.requiredFeatures = $$7;
    }

    public String toString() {
        return id();
    }

    public String id() {
        return getIdentifier().toShortString();
    }

    public Identifier getIdentifier() {
        return (Identifier) Objects.requireNonNull(BuiltInRegistries.GAME_RULE.getKey(this));
    }

    public String getDescriptionId() {
        return Util.makeDescriptionId("gamerule", getIdentifier());
    }

    public String serialize(T $$0) {
        return $$0.toString();
    }

    public DataResult<T> deserialize(String $$0) {
        try {
            StringReader $$1 = new StringReader($$0);
            Object obj = this.argument.parse($$1);
            if ($$1.canRead()) {
                return DataResult.error(() -> {
                    return "Failed to deserialize; trailing characters";
                }, obj);
            }
            return DataResult.success(obj);
        } catch (CommandSyntaxException e) {
            return DataResult.error(() -> {
                return "Failed to deserialize";
            });
        }
    }

    public Class<T> valueClass() {
        return (Class<T>) this.defaultValue.getClass();
    }

    public void callVisitor(GameRuleTypeVisitor $$0) {
        this.visitorCaller.call($$0, this);
    }

    public int getCommandResult(T $$0) {
        return this.commandResultFunction.applyAsInt($$0);
    }

    public GameRuleCategory category() {
        return this.category;
    }

    public GameRuleType gameRuleType() {
        return this.gameRuleType;
    }

    public ArgumentType<T> argument() {
        return this.argument;
    }

    public Codec<T> valueCodec() {
        return this.valueCodec;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

    @Override // net.minecraft.world.flag.FeatureElement
    public FeatureFlagSet requiredFeatures() {
        return this.requiredFeatures;
    }
}
