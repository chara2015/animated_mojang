package net.labymod.core.client.entity.player.abilities;

import net.labymod.api.client.entity.player.abilities.BooleanAbilityOption;
import net.labymod.api.client.entity.player.abilities.FloatAbilityOption;
import net.labymod.api.client.entity.player.abilities.PlayerAbilities;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/entity/player/abilities/DefaultPlayerAbilities.class */
public class DefaultPlayerAbilities implements PlayerAbilities {
    private final BooleanAbilityOption invulnerable;
    private final BooleanAbilityOption flying;
    private final BooleanAbilityOption ableToFly;
    private final BooleanAbilityOption instaBuild;
    private final BooleanAbilityOption canBuild;
    private final FloatAbilityOption flyingSpeed;
    private final FloatAbilityOption walkingSpeed;

    public DefaultPlayerAbilities(BooleanAbilityOption invulnerable, BooleanAbilityOption flying, BooleanAbilityOption ableToFly, BooleanAbilityOption instaBuild, BooleanAbilityOption canBuild, FloatAbilityOption flyingSpeed, FloatAbilityOption walkingSpeed) {
        this.invulnerable = invulnerable;
        this.flying = flying;
        this.ableToFly = ableToFly;
        this.instaBuild = instaBuild;
        this.canBuild = canBuild;
        this.flyingSpeed = flyingSpeed;
        this.walkingSpeed = walkingSpeed;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption invulnerable() {
        return this.invulnerable;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption flying() {
        return this.flying;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption ableToFly() {
        return this.ableToFly;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption instaBuild() {
        return this.instaBuild;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public BooleanAbilityOption canBuild() {
        return this.canBuild;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public FloatAbilityOption flyingSpeed() {
        return this.flyingSpeed;
    }

    @Override // net.labymod.api.client.entity.player.abilities.PlayerAbilities
    public FloatAbilityOption walkingSpeed() {
        return this.walkingSpeed;
    }
}
