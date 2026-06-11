package net.minecraft.network.chat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.net.URI;
import java.nio.file.Path;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent.class */
public interface ClickEvent {
    public static final Codec<ClickEvent> CODEC = Action.CODEC.dispatch("action", (v0) -> {
        return v0.action();
    }, $$0 -> {
        return $$0.codec;
    });

    Action action();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$OpenUrl.class */
    public static final class OpenUrl extends Record implements ClickEvent {
        private final URI uri;
        public static final MapCodec<OpenUrl> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.UNTRUSTED_URI.fieldOf("url").forGetter((v0) -> {
                return v0.uri();
            })).apply($$0, OpenUrl::new);
        });

        public OpenUrl(URI $$0) {
            this.uri = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OpenUrl.class), OpenUrl.class, "uri", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenUrl;->uri:Ljava/net/URI;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OpenUrl.class), OpenUrl.class, "uri", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenUrl;->uri:Ljava/net/URI;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OpenUrl.class, Object.class), OpenUrl.class, "uri", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenUrl;->uri:Ljava/net/URI;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public URI uri() {
            return this.uri;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.OPEN_URL;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$OpenFile.class */
    public static final class OpenFile extends Record implements ClickEvent {
        private final String path;
        public static final MapCodec<OpenFile> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.STRING.fieldOf("path").forGetter((v0) -> {
                return v0.path();
            })).apply($$0, OpenFile::new);
        });

        public OpenFile(String $$0) {
            this.path = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, OpenFile.class), OpenFile.class, "path", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenFile;->path:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, OpenFile.class), OpenFile.class, "path", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenFile;->path:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, OpenFile.class, Object.class), OpenFile.class, "path", "FIELD:Lnet/minecraft/network/chat/ClickEvent$OpenFile;->path:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String path() {
            return this.path;
        }

        public OpenFile(File $$0) {
            this($$0.toString());
        }

        public OpenFile(Path $$0) {
            this($$0.toFile());
        }

        public File file() {
            return new File(this.path);
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.OPEN_FILE;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$RunCommand.class */
    public static final class RunCommand extends Record implements ClickEvent {
        private final String command;
        public static final MapCodec<RunCommand> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.CHAT_STRING.fieldOf("command").forGetter((v0) -> {
                return v0.command();
            })).apply($$0, RunCommand::new);
        });

        public RunCommand(String $$0) {
            this.command = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, RunCommand.class), RunCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$RunCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, RunCommand.class), RunCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$RunCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, RunCommand.class, Object.class), RunCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$RunCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String command() {
            return this.command;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.RUN_COMMAND;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$SuggestCommand.class */
    public static final class SuggestCommand extends Record implements ClickEvent {
        private final String command;
        public static final MapCodec<SuggestCommand> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.CHAT_STRING.fieldOf("command").forGetter((v0) -> {
                return v0.command();
            })).apply($$0, SuggestCommand::new);
        });

        public SuggestCommand(String $$0) {
            this.command = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, SuggestCommand.class), SuggestCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$SuggestCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, SuggestCommand.class), SuggestCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$SuggestCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, SuggestCommand.class, Object.class), SuggestCommand.class, "command", "FIELD:Lnet/minecraft/network/chat/ClickEvent$SuggestCommand;->command:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String command() {
            return this.command;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.SUGGEST_COMMAND;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$ShowDialog.class */
    public static final class ShowDialog extends Record implements ClickEvent {
        private final Holder<Dialog> dialog;
        public static final MapCodec<ShowDialog> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Dialog.CODEC.fieldOf("dialog").forGetter((v0) -> {
                return v0.dialog();
            })).apply($$0, ShowDialog::new);
        });

        public ShowDialog(Holder<Dialog> $$0) {
            this.dialog = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ShowDialog.class), ShowDialog.class, "dialog", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ShowDialog;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ShowDialog.class), ShowDialog.class, "dialog", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ShowDialog;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ShowDialog.class, Object.class), ShowDialog.class, "dialog", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ShowDialog;->dialog:Lnet/minecraft/core/Holder;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Holder<Dialog> dialog() {
            return this.dialog;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.SHOW_DIALOG;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$ChangePage.class */
    public static final class ChangePage extends Record implements ClickEvent {
        private final int page;
        public static final MapCodec<ChangePage> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(ExtraCodecs.POSITIVE_INT.fieldOf("page").forGetter((v0) -> {
                return v0.page();
            })).apply($$0, (v1) -> {
                return new ChangePage(v1);
            });
        });

        public ChangePage(int $$0) {
            this.page = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, ChangePage.class), ChangePage.class, "page", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ChangePage;->page:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, ChangePage.class), ChangePage.class, "page", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ChangePage;->page:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, ChangePage.class, Object.class), ChangePage.class, "page", "FIELD:Lnet/minecraft/network/chat/ClickEvent$ChangePage;->page:I").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public int page() {
            return this.page;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.CHANGE_PAGE;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$CopyToClipboard.class */
    public static final class CopyToClipboard extends Record implements ClickEvent {
        private final String value;
        public static final MapCodec<CopyToClipboard> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Codec.STRING.fieldOf("value").forGetter((v0) -> {
                return v0.value();
            })).apply($$0, CopyToClipboard::new);
        });

        public CopyToClipboard(String $$0) {
            this.value = $$0;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, CopyToClipboard.class), CopyToClipboard.class, "value", "FIELD:Lnet/minecraft/network/chat/ClickEvent$CopyToClipboard;->value:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, CopyToClipboard.class), CopyToClipboard.class, "value", "FIELD:Lnet/minecraft/network/chat/ClickEvent$CopyToClipboard;->value:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, CopyToClipboard.class, Object.class), CopyToClipboard.class, "value", "FIELD:Lnet/minecraft/network/chat/ClickEvent$CopyToClipboard;->value:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public String value() {
            return this.value;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.COPY_TO_CLIPBOARD;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$Custom.class */
    public static final class Custom extends Record implements ClickEvent {
        private final Identifier id;
        private final Optional<Tag> payload;
        public static final MapCodec<Custom> CODEC = RecordCodecBuilder.mapCodec($$0 -> {
            return $$0.group(Identifier.CODEC.fieldOf(Entity.TAG_ID).forGetter((v0) -> {
                return v0.id();
            }), ExtraCodecs.NBT.optionalFieldOf("payload").forGetter((v0) -> {
                return v0.payload();
            })).apply($$0, Custom::new);
        });

        public Custom(Identifier $$0, Optional<Tag> $$1) {
            this.id = $$0;
            this.payload = $$1;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, Custom.class), Custom.class, "id;payload", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->payload:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, Custom.class), Custom.class, "id;payload", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->payload:Ljava/util/Optional;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, Custom.class, Object.class), Custom.class, "id;payload", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->id:Lnet/minecraft/resources/Identifier;", "FIELD:Lnet/minecraft/network/chat/ClickEvent$Custom;->payload:Ljava/util/Optional;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public Identifier id() {
            return this.id;
        }

        public Optional<Tag> payload() {
            return this.payload;
        }

        @Override // net.minecraft.network.chat.ClickEvent
        public Action action() {
            return Action.CUSTOM;
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/network/chat/ClickEvent$Action.class */
    public enum Action implements StringRepresentable {
        OPEN_URL("open_url", true, OpenUrl.CODEC),
        OPEN_FILE("open_file", false, OpenFile.CODEC),
        RUN_COMMAND("run_command", true, RunCommand.CODEC),
        SUGGEST_COMMAND("suggest_command", true, SuggestCommand.CODEC),
        SHOW_DIALOG("show_dialog", true, ShowDialog.CODEC),
        CHANGE_PAGE("change_page", true, ChangePage.CODEC),
        COPY_TO_CLIPBOARD("copy_to_clipboard", true, CopyToClipboard.CODEC),
        CUSTOM("custom", true, Custom.CODEC);

        public static final Codec<Action> UNSAFE_CODEC = StringRepresentable.fromEnum(Action::values);
        public static final Codec<Action> CODEC = UNSAFE_CODEC.validate(Action::filterForSerialization);
        private final boolean allowFromServer;
        private final String name;
        final MapCodec<? extends ClickEvent> codec;

        Action(String $$0, boolean $$1, MapCodec mapCodec) {
            this.name = $$0;
            this.allowFromServer = $$1;
            this.codec = mapCodec;
        }

        public boolean isAllowedFromServer() {
            return this.allowFromServer;
        }

        @Override // net.minecraft.util.StringRepresentable
        public String getSerializedName() {
            return this.name;
        }

        public MapCodec<? extends ClickEvent> valueCodec() {
            return this.codec;
        }

        public static DataResult<Action> filterForSerialization(Action $$0) {
            if (!$$0.isAllowedFromServer()) {
                return DataResult.error(() -> {
                    return "Click event type not allowed: " + String.valueOf($$0);
                });
            }
            return DataResult.success($$0, Lifecycle.stable());
        }
    }
}
