package net.minecraft.client;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/GuiMessageTag.class */
public final class GuiMessageTag extends Record {
    private final int indicatorColor;
    private final Icon icon;
    private final Component text;
    private final String logTag;
    private static final int CHAT_MODIFIED_INDICATOR_COLOR = 6316128;
    private static final Component SYSTEM_TEXT = Component.translatable("chat.tag.system");
    private static final Component SYSTEM_TEXT_SINGLE_PLAYER = Component.translatable("chat.tag.system_single_player");
    private static final Component CHAT_NOT_SECURE_TEXT = Component.translatable("chat.tag.not_secure");
    private static final Component CHAT_MODIFIED_TEXT = Component.translatable("chat.tag.modified");
    private static final Component CHAT_ERROR_TEXT = Component.translatable("chat.tag.error");
    private static final int CHAT_NOT_SECURE_INDICATOR_COLOR = 13684944;
    private static final GuiMessageTag SYSTEM = new GuiMessageTag(CHAT_NOT_SECURE_INDICATOR_COLOR, null, SYSTEM_TEXT, "System");
    private static final GuiMessageTag SYSTEM_SINGLE_PLAYER = new GuiMessageTag(CHAT_NOT_SECURE_INDICATOR_COLOR, null, SYSTEM_TEXT_SINGLE_PLAYER, "System");
    private static final GuiMessageTag CHAT_NOT_SECURE = new GuiMessageTag(CHAT_NOT_SECURE_INDICATOR_COLOR, null, CHAT_NOT_SECURE_TEXT, "Not Secure");
    private static final GuiMessageTag CHAT_ERROR = new GuiMessageTag(16733525, null, CHAT_ERROR_TEXT, "Chat Error");

    public GuiMessageTag(int $$0, Icon $$1, Component $$2, String $$3) {
        this.indicatorColor = $$0;
        this.icon = $$1;
        this.text = $$2;
        this.logTag = $$3;
    }

    @Override // java.lang.Record
    public final String toString() {
        return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, GuiMessageTag.class), GuiMessageTag.class, "indicatorColor;icon;text;logTag", "FIELD:Lnet/minecraft/client/GuiMessageTag;->indicatorColor:I", "FIELD:Lnet/minecraft/client/GuiMessageTag;->icon:Lnet/minecraft/client/GuiMessageTag$Icon;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->logTag:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final int hashCode() {
        return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, GuiMessageTag.class), GuiMessageTag.class, "indicatorColor;icon;text;logTag", "FIELD:Lnet/minecraft/client/GuiMessageTag;->indicatorColor:I", "FIELD:Lnet/minecraft/client/GuiMessageTag;->icon:Lnet/minecraft/client/GuiMessageTag$Icon;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->logTag:Ljava/lang/String;").dynamicInvoker().invoke(this) /* invoke-custom */;
    }

    @Override // java.lang.Record
    public final boolean equals(Object $$0) {
        return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, GuiMessageTag.class, Object.class), GuiMessageTag.class, "indicatorColor;icon;text;logTag", "FIELD:Lnet/minecraft/client/GuiMessageTag;->indicatorColor:I", "FIELD:Lnet/minecraft/client/GuiMessageTag;->icon:Lnet/minecraft/client/GuiMessageTag$Icon;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->text:Lnet/minecraft/network/chat/Component;", "FIELD:Lnet/minecraft/client/GuiMessageTag;->logTag:Ljava/lang/String;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
    }

    public int indicatorColor() {
        return this.indicatorColor;
    }

    public Icon icon() {
        return this.icon;
    }

    public Component text() {
        return this.text;
    }

    public String logTag() {
        return this.logTag;
    }

    public static GuiMessageTag system() {
        return SYSTEM;
    }

    public static GuiMessageTag systemSinglePlayer() {
        return SYSTEM_SINGLE_PLAYER;
    }

    public static GuiMessageTag chatNotSecure() {
        return CHAT_NOT_SECURE;
    }

    public static GuiMessageTag chatModified(String $$0) {
        Component $$1 = Component.literal($$0).withStyle(ChatFormatting.GRAY);
        Component $$2 = Component.empty().append(CHAT_MODIFIED_TEXT).append(CommonComponents.NEW_LINE).append($$1);
        return new GuiMessageTag(CHAT_MODIFIED_INDICATOR_COLOR, Icon.CHAT_MODIFIED, $$2, "Modified");
    }

    public static GuiMessageTag chatError() {
        return CHAT_ERROR;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/GuiMessageTag$Icon.class */
    public enum Icon {
        CHAT_MODIFIED(Identifier.withDefaultNamespace("icon/chat_modified"), 9, 9);

        public final Identifier sprite;
        public final int width;
        public final int height;

        Icon(Identifier $$0, int $$1, int $$2) {
            this.sprite = $$0;
            this.width = $$1;
            this.height = $$2;
        }

        public void draw(GuiGraphics $$0, int $$1, int $$2) {
            $$0.blitSprite(RenderPipelines.GUI_TEXTURED, this.sprite, $$1, $$2, this.width, this.height);
        }
    }
}
