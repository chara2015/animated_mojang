package net.minecraft.server.dialog;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.util.ExtraCodecs;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/MultiActionDialog.class */
public final class MultiActionDialog extends Record implements ButtonListDialog {
    private final CommonDialogData common;
    private final List<ActionButton> actions;
    private final Optional<ActionButton> exitAction;
    private final int columns;
    public static final MapCodec<MultiActionDialog> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(CommonDialogData.MAP_CODEC.forGetter((v0) -> {
            return v0.common();
        }), ExtraCodecs.nonEmptyList(ActionButton.CODEC.listOf()).fieldOf("actions").forGetter((v0) -> {
            return v0.actions();
        }), ActionButton.CODEC.optionalFieldOf("exit_action").forGetter((v0) -> {
            return v0.exitAction();
        }), ExtraCodecs.POSITIVE_INT.optionalFieldOf("columns", 2).forGetter((v0) -> {
            return v0.columns();
        })).apply($$0, (v1, v2, v3, v4) -> {
            return new MultiActionDialog(v1, v2, v3, v4);
        });
    });

    public MultiActionDialog(CommonDialogData $$0, List<ActionButton> $$1, Optional<ActionButton> $$2, int $$3) {
        this.common = $$0;
        this.actions = $$1;
        this.exitAction = $$2;
        this.columns = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, MultiActionDialog.class), MultiActionDialog.class, "common;actions;exitAction;columns", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->actions:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->columns:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, MultiActionDialog.class), MultiActionDialog.class, "common;actions;exitAction;columns", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->actions:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->columns:I").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, MultiActionDialog.class, Object.class), MultiActionDialog.class, "common;actions;exitAction;columns", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->actions:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->exitAction:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/MultiActionDialog;->columns:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.server.dialog.Dialog
    public CommonDialogData common() {
        return this.common;
    }

    public List<ActionButton> actions() {
        return this.actions;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog
    public Optional<ActionButton> exitAction() {
        return this.exitAction;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog
    public int columns() {
        return this.columns;
    }

    @Override // net.minecraft.server.dialog.ButtonListDialog, net.minecraft.server.dialog.Dialog
    public MapCodec<MultiActionDialog> codec() {
        return MAP_CODEC;
    }
}
