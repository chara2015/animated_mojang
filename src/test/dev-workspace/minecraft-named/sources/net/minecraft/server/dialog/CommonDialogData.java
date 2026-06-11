package net.minecraft.server.dialog;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.server.dialog.body.DialogBody;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/dialog/CommonDialogData.class */
public final class CommonDialogData extends Record {
    private final Component title;
    private final Optional<Component> externalTitle;
    private final boolean canCloseWithEscape;
    private final boolean pause;
    private final DialogAction afterAction;
    private final List<DialogBody> body;
    private final List<Input> inputs;
    public static final MapCodec<CommonDialogData> MAP_CODEC = RecordCodecBuilder.mapCodec($$0 -> {
        return $$0.group(ComponentSerialization.CODEC.fieldOf("title").forGetter((v0) -> {
            return v0.title();
        }), ComponentSerialization.CODEC.optionalFieldOf("external_title").forGetter((v0) -> {
            return v0.externalTitle();
        }), Codec.BOOL.optionalFieldOf("can_close_with_escape", true).forGetter((v0) -> {
            return v0.canCloseWithEscape();
        }), Codec.BOOL.optionalFieldOf("pause", true).forGetter((v0) -> {
            return v0.pause();
        }), DialogAction.CODEC.optionalFieldOf("after_action", DialogAction.CLOSE).forGetter((v0) -> {
            return v0.afterAction();
        }), DialogBody.COMPACT_LIST_CODEC.optionalFieldOf(PartNames.BODY, List.of()).forGetter((v0) -> {
            return v0.body();
        }), Input.CODEC.listOf().optionalFieldOf("inputs", List.of()).forGetter((v0) -> {
            return v0.inputs();
        })).apply($$0, (v1, v2, v3, v4, v5, v6, v7) -> {
            return new CommonDialogData(v1, v2, v3, v4, v5, v6, v7);
        });
    }).validate($$02 -> {
        if ($$02.pause && !$$02.afterAction.willUnpause()) {
            return DataResult.error(() -> {
                return "Dialogs that pause the game must use after_action values that unpause it after user action!";
            });
        }
        return DataResult.success($$02);
    });

    public CommonDialogData(Component $$0, Optional<Component> $$1, boolean $$2, boolean $$3, DialogAction $$4, List<DialogBody> $$5, List<Input> $$6) {
        this.title = $$0;
        this.externalTitle = $$1;
        this.canCloseWithEscape = $$2;
        this.pause = $$3;
        this.afterAction = $$4;
        this.body = $$5;
        this.inputs = $$6;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CommonDialogData.class), CommonDialogData.class, "title;externalTitle;canCloseWithEscape;pause;afterAction;body;inputs", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->externalTitle:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->canCloseWithEscape:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->pause:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->afterAction:Lnet/minecraft/server/dialog/DialogAction;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->body:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->inputs:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CommonDialogData.class), CommonDialogData.class, "title;externalTitle;canCloseWithEscape;pause;afterAction;body;inputs", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->externalTitle:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->canCloseWithEscape:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->pause:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->afterAction:Lnet/minecraft/server/dialog/DialogAction;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->body:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->inputs:Ljava/util/List;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CommonDialogData.class, Object.class), CommonDialogData.class, "title;externalTitle;canCloseWithEscape;pause;afterAction;body;inputs", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->title:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->externalTitle:Ljava/util/Optional;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->canCloseWithEscape:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->pause:Z", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->afterAction:Lnet/minecraft/server/dialog/DialogAction;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->body:Ljava/util/List;", "FIELD:Lnet/minecraft/server/dialog/CommonDialogData;->inputs:Ljava/util/List;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public Component title() {
        return this.title;
    }

    public Optional<Component> externalTitle() {
        return this.externalTitle;
    }

    public boolean canCloseWithEscape() {
        return this.canCloseWithEscape;
    }

    public boolean pause() {
        return this.pause;
    }

    public DialogAction afterAction() {
        return this.afterAction;
    }

    public List<DialogBody> body() {
        return this.body;
    }

    public List<Input> inputs() {
        return this.inputs;
    }

    public Component computeExternalTitle() {
        return this.externalTitle.orElse(this.title);
    }
}
