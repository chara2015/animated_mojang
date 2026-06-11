package net.minecraft.client.gui.screens;

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.CommonColors;
import net.minecraft.util.Util;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/ConfirmLinkScreen.class */
public class ConfirmLinkScreen extends ConfirmScreen {
    private static final Component WARNING_TEXT = Component.translatable("chat.link.warning").withColor(CommonColors.COSMOS_PINK);
    private static final int BUTTON_WIDTH = 100;
    private final String url;
    private final boolean showWarning;

    public ConfirmLinkScreen(BooleanConsumer $$0, String $$1, boolean $$2) {
        this($$0, confirmMessage($$2), Component.literal($$1), $$1, $$2 ? CommonComponents.GUI_CANCEL : CommonComponents.GUI_NO, $$2);
    }

    public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, String $$2, boolean $$3) {
        this($$0, $$1, confirmMessage($$3, $$2), $$2, $$3 ? CommonComponents.GUI_CANCEL : CommonComponents.GUI_NO, $$3);
    }

    public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, URI $$2, boolean $$3) {
        this($$0, $$1, $$2.toString(), $$3);
    }

    public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, Component $$2, URI $$3, Component $$4, boolean $$5) {
        this($$0, $$1, $$2, $$3.toString(), $$4, true);
    }

    public ConfirmLinkScreen(BooleanConsumer $$0, Component $$1, Component $$2, String $$3, Component $$4, boolean $$5) {
        super($$0, $$1, $$2);
        this.yesButtonComponent = $$5 ? CommonComponents.GUI_OPEN_IN_BROWSER : CommonComponents.GUI_YES;
        this.noButtonComponent = $$4;
        this.showWarning = !$$5;
        this.url = $$3;
    }

    protected static MutableComponent confirmMessage(boolean $$0, String $$1) {
        return confirmMessage($$0).append(CommonComponents.SPACE).append(Component.literal($$1));
    }

    protected static MutableComponent confirmMessage(boolean $$0) {
        return Component.translatable($$0 ? "chat.link.confirmTrusted" : "chat.link.confirm");
    }

    @Override // net.minecraft.client.gui.screens.ConfirmScreen
    protected void addAdditionalText() {
        if (this.showWarning) {
            this.layout.addChild(new StringWidget(WARNING_TEXT, this.font));
        }
    }

    @Override // net.minecraft.client.gui.screens.ConfirmScreen
    protected void addButtons(LinearLayout $$0) {
        this.yesButton = (Button) $$0.addChild(Button.builder(this.yesButtonComponent, $$02 -> {
            this.callback.accept(true);
        }).width(100).build());
        $$0.addChild(Button.builder(CommonComponents.GUI_COPY_TO_CLIPBOARD, $$03 -> {
            copyToClipboard();
            this.callback.accept(false);
        }).width(100).build());
        this.noButton = (Button) $$0.addChild(Button.builder(this.noButtonComponent, $$04 -> {
            this.callback.accept(false);
        }).width(100).build());
    }

    public void copyToClipboard() {
        this.minecraft.keyboardHandler.setClipboard(this.url);
    }

    public static void confirmLinkNow(Screen $$0, String $$1, boolean $$2) {
        Minecraft $$3 = Minecraft.getInstance();
        $$3.setScreen(new ConfirmLinkScreen($$32 -> {
            if ($$32) {
                Util.getPlatform().openUri($$1);
            }
            $$3.setScreen($$0);
        }, $$1, $$2));
    }

    public static void confirmLinkNow(Screen $$0, URI $$1, boolean $$2) {
        Minecraft $$3 = Minecraft.getInstance();
        $$3.setScreen(new ConfirmLinkScreen($$32 -> {
            if ($$32) {
                Util.getPlatform().openUri($$1);
            }
            $$3.setScreen($$0);
        }, $$1.toString(), $$2));
    }

    public static void confirmLinkNow(Screen $$0, URI $$1) {
        confirmLinkNow($$0, $$1, true);
    }

    public static void confirmLinkNow(Screen $$0, String $$1) {
        confirmLinkNow($$0, $$1, true);
    }

    public static Button.OnPress confirmLink(Screen $$0, String $$1, boolean $$2) {
        return $$3 -> {
            confirmLinkNow($$0, $$1, $$2);
        };
    }

    public static Button.OnPress confirmLink(Screen $$0, URI $$1, boolean $$2) {
        return $$3 -> {
            confirmLinkNow($$0, $$1, $$2);
        };
    }

    public static Button.OnPress confirmLink(Screen $$0, String $$1) {
        return confirmLink($$0, $$1, true);
    }

    public static Button.OnPress confirmLink(Screen $$0, URI $$1) {
        return confirmLink($$0, $$1, true);
    }
}
