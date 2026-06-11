package net.minecraft.server.dialog;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.server.dialog.action.Action;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/NoticeDialog.class */
public final class NoticeDialog extends Record implements SimpleDialog {
    private final CommonDialogData common;
    private final ActionButton action;
    public static final ActionButton DEFAULT_ACTION = new ActionButton(new CommonButtonData(CommonComponents.GUI_OK, 150), Optional.empty());
    public static final MapCodec<NoticeDialog> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(CommonDialogData.MAP_CODEC.forGetter((v0) -> {
            return v0.common();
        }), ActionButton.CODEC.optionalFieldOf("action", DEFAULT_ACTION).forGetter((v0) -> {
            return v0.action();
        })).apply($$0, NoticeDialog::new);
    });

    public NoticeDialog(CommonDialogData $$0, ActionButton $$1) {
        this.common = $$0;
        this.action = $$1;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NoticeDialog.class), NoticeDialog.class, "common;action", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->action:Lnet/minecraft/server/dialog/ActionButton;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NoticeDialog.class), NoticeDialog.class, "common;action", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->action:Lnet/minecraft/server/dialog/ActionButton;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NoticeDialog.class, Object.class), NoticeDialog.class, "common;action", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->common:Lnet/minecraft/server/dialog/CommonDialogData;", "FIELD:Lnet/minecraft/server/dialog/NoticeDialog;->action:Lnet/minecraft/server/dialog/ActionButton;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    @Override // net.minecraft.server.dialog.Dialog
    public CommonDialogData common() {
        return this.common;
    }

    public ActionButton action() {
        return this.action;
    }

    @Override // net.minecraft.server.dialog.SimpleDialog, net.minecraft.server.dialog.Dialog
    public MapCodec<NoticeDialog> codec() {
        return MAP_CODEC;
    }

    @Override // net.minecraft.server.dialog.Dialog
    public Optional<Action> onCancel() {
        return this.action.action();
    }

    @Override // net.minecraft.server.dialog.SimpleDialog
    public List<ActionButton> mainActions() {
        return List.of(this.action);
    }
}
