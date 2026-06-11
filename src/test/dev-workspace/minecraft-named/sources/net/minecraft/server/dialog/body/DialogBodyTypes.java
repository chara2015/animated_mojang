package net.minecraft.server.dialog.body;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.entity.DecoratedPotBlockEntity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/body/DialogBodyTypes.class */
public class DialogBodyTypes {
    public static MapCodec<? extends DialogBody> bootstrap(Registry<MapCodec<? extends DialogBody>> $$0) {
        Registry.register($$0, Identifier.withDefaultNamespace(DecoratedPotBlockEntity.TAG_ITEM), ItemBody.MAP_CODEC);
        return (MapCodec) Registry.register($$0, Identifier.withDefaultNamespace("plain_message"), PlainMessage.MAP_CODEC);
    }
}
