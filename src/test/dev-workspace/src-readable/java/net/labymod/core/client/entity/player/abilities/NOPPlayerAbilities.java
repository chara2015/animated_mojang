package net.labymod.core.client.entity.player.abilities;

import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/abilities/NOPPlayerAbilities.class */
public final class NOPPlayerAbilities implements PlayerAbilities {
    public static final PlayerAbilities INSTANCE = new NOPPlayerAbilities();
    private static final BooleanAbilityOption NOP_BOOLEAN_ABILITY_OPTION = new BooleanAbilityOption(() -> {
        return false;
    }, value -> {
    });
    private static final FloatAbilityOption NOP_FLOAT_ABILITY_OPTION = new FloatAbilityOption(() -> {
        return 0.0f;
    }, value -> {
    });

    private NOPPlayerAbilities() {
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption invulnerable() {
        return NOP_BOOLEAN_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption flying() {
        return NOP_BOOLEAN_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption ableToFly() {
        return NOP_BOOLEAN_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption instaBuild() {
        return NOP_BOOLEAN_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption canBuild() {
        return NOP_BOOLEAN_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public FloatAbilityOption flyingSpeed() {
        return NOP_FLOAT_ABILITY_OPTION;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public FloatAbilityOption walkingSpeed() {
        return NOP_FLOAT_ABILITY_OPTION;
    }
}
