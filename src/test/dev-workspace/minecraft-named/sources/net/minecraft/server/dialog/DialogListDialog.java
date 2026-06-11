package net.minecraft.server.dialog;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.Optional;
import net.minecraft.core.HolderSet;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/DialogListDialog.class */
public final class DialogListDialog extends Record implements ButtonListDialog {
    private final CommonDialogData common;
    private final HolderSet<Dialog> dialogs;
    private final Optional<ActionButton> exitAction;
    private final int columns;
    private final int buttonWidth;
    public static final MapCodec<DialogListDialog> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(CommonDialogData.MAP_CODEC.forGetter((v0) -> {
            return v0.common();
        }), Dialog.LIST_CODEC.fieldOf("dialogs").forGetter((v0) -> {
            return v0.dialogs();
        }), ActionButton.CODEC.optionalFieldOf("exit_action").forGetter((v0) -> {
            return v0.exitAction();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("columns", 2).forGetter((v0) -> {
            return v0.columns();
        }), WIDTH_CODEC.optionalFieldOf("button_width", 150).forGetter((v0) -> {
            return v0.buttonWidth();
        })).apply($$0, (v1, v2, v3, v4, v5) -> {
            return new DialogListDialog(v1, v2, v3, v4, v5);
        });
    });

    public DialogListDialog(CommonDialogData $$0, HolderSet<Dialog> $$1, Optional<ActionButton> $$2, int $$3, int $$4) {
        this.common = $$0;
        this.dialogs = $$1;
        this.exitAction = $$2;
        this.columns = $$3;
        this.buttonWidth = $$4;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DialogListDialog.class), DialogListDialog.class, "common;dialogs;exitAction;columns;buttonWidth", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->dialogs:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->columns:I", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->buttonWidth:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DialogListDialog.class), DialogListDialog.class, "common;dialogs;exitAction;columns;buttonWidth", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->dialogs:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->columns:I", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->buttonWidth:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DialogListDialog.class, Object.class), DialogListDialog.class, "common;dialogs;exitAction;columns;buttonWidth", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->dialogs:Lnet/minecraft/core/HolderSet;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->columns:I", "FIELD:Lnet/minecraft/server/dialog/DialogListDialog;->buttonWidth:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.server.dialog.Dialog
    public CommonDialogData common() {
        return this.common;
    }

    public HolderSet<Dialog> dialogs() {
        return this.dialogs;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog
    public Optional<ActionButton> exitAction() {
        return this.exitAction;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog
    public int columns() {
        return this.columns;
    }

    public int buttonWidth() {
        return this.buttonWidth;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog, net.minecraft.server.dialog.Dialog
    public MapCodec<DialogListDialog> codec() {
        return MAP_CODEC;
    }
}
