package net.minecraft.server.dialog.action;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/action/ActionTypes.class */
public class ActionTypes {
    public static MapCodec<? extends Action> bootstrap(Registry<MapCodec<? extends Action>> $$0) {
        StaticAction.WRAPPED_CODECS.forEach(($$1, $$2) -> {
            Registry.register($$0, Identifier.withDefaultNamespace($$1.getSerializedName()), $$2);
        });
        Registry.register($$0, Identifier.withDefaultNamespace("dynamic/run_command"), CommandTemplate.MAP_CODEC);
        return (MapCodec) Registry.register($$0, Identifier.withDefaultNamespace("dynamic/custom"), CustomAll.MAP_CODEC);
    }
}
