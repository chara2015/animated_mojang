package net.labymod.api.client.entity.player.abilities;

import net.labymod.api.util.function.FloatGetter;
import net.labymod.api.util.function.FloatSetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/abilities/FloatAbilityOption.class */
public class FloatAbilityOption {
    private final FloatGetter getter;
    private final FloatSetter setter;

    public FloatAbilityOption(FloatGetter getter, FloatSetter setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public float get() {
        return this.getter.get();
    }

    public void set(float value) {
        this.setter.set(value);
    }
}
