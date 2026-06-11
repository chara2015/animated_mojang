package net.labymod.v26_1.client.player;

import net.labymod.api.util.CastUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_1/client/player/ClientAvatarStateAccessor.class */
public interface ClientAvatarStateAccessor {
    double labyMod$getXCloak();

    void labyMod$setXCloak(double d);

    double labyMod$getYCloak();

    void labyMod$setYCloak(double d);

    double labyMod$getZCloak();

    void labyMod$setZCloak(double d);

    double labyMod$getXCloakO();

    void labyMod$setXCloakO(double d);

    double labyMod$getYCloakO();

    void labyMod$setYCloakO(double d);

    double labyMod$getZCloakO();

    void labyMod$setZCloakO(double d);

    float labyMod$getBob();

    float labyMod$getBobO();

    float labyMod$getWalkDist();

    void labyMod$setWalkDist(float f);

    float labyMod$getWalkDistO();

    static ClientAvatarStateAccessor cast(Object obj) {
        return (ClientAvatarStateAccessor) CastUtil.requireInstanceOf(obj, ClientAvatarStateAccessor.class);
    }
}
