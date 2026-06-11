package net.minecraft.server.dialog;

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.server.dialog.action.Action;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/ButtonListDialog.class */
public interface ButtonListDialog extends Dialog {
    @Override // net.minecraft.server.dialog.Dialog
    MapCodec<? extends ButtonListDialog> codec();

    int columns();

    Optional<ActionButton> exitAction();

    @Override // net.minecraft.server.dialog.Dialog
    default Optional<Action> onCancel() {
        return exitAction().flatMap((v0) -> {
            return v0.action();
        });
    }
}
