package net.minecraft.server.dialog.body;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/body/DialogBody.class */
public interface DialogBody {
    public static final Codec<DialogBody> DIALOG_BODY_CODEC = BuiltInRegistries.DIALOG_BODY_TYPE.byNameCodec().dispatch((v0) -> {
        return v0.mapCodec();
    }, $$0 -> {
        return $$0;
    });
    public static final Codec<List<DialogBody>> COMPACT_LIST_CODEC = ExtraCodecs.compactListCodec(DIALOG_BODY_CODEC);

    MapCodec<? extends DialogBody> mapCodec();
}
