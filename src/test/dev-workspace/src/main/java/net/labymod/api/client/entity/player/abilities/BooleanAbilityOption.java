package net.labymod.api.client.entity.player.abilities;

import net.labymod.api.util.function.BooleanGetter;
import net.labymod.api.util.function.BooleanSetter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/entity/player/abilities/BooleanAbilityOption.class */
public class BooleanAbilityOption {
    private final BooleanGetter getter;
    private final BooleanSetter setter;

    public BooleanAbilityOption(BooleanGetter getter, BooleanSetter setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public boolean get() {
        return this.getter.get();
    }

    public void set(boolean value) {
        this.setter.set(value);
    }
}
