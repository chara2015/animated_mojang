package net.minecraft.client.gui.screens.dialog;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.SwitchBootstraps;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.ScrollableLayout;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.dialog.body.DialogBodyHandlers;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.Dialog;
import net.minecraft.server.dialog.DialogAction;
import net.minecraft.server.dialog.Input;
import net.minecraft.server.dialog.body.DialogBody;
import org.apache.commons.lang3.mutable.MutableObject;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/DialogScreen.class */
public abstract class DialogScreen<T extends Dialog> extends Screen {
    private static final int WARNING_BUTTON_SIZE = 20;
    private final T dialog;
    private final HeaderAndFooterLayout layout;
    private final Screen previousScreen;
    private ScrollableLayout bodyScroll;
    private Button warningButton;
    private final DialogConnectionAccess connectionAccess;
    private Supplier<Optional<ClickEvent>> onClose;
    public static final Component DISCONNECT = Component.translatable("menu.custom_screen_info.disconnect");
    private static final WidgetSprites WARNING_BUTTON_SPRITES = new WidgetSprites(Identifier.withDefaultNamespace("dialog/warning_button"), Identifier.withDefaultNamespace("dialog/warning_button_disabled"), Identifier.withDefaultNamespace("dialog/warning_button_highlighted"));

    public DialogScreen(Screen $$0, T $$1, DialogConnectionAccess $$2) {
        super($$1.common().title());
        this.layout = new HeaderAndFooterLayout(this);
        this.onClose = DialogControlSet.EMPTY_ACTION;
        this.dialog = $$1;
        this.previousScreen = $$0;
        this.connectionAccess = $$2;
    }

    @Override // net.minecraft.client.gui.screens.Screen
    protected final void init() {
        super.init();
        this.warningButton = createWarningButton();
        this.warningButton.setTabOrderGroup(-10);
        DialogControlSet $$0 = new DialogControlSet(this);
        LinearLayout $$1 = LinearLayout.vertical().spacing(10);
        $$1.defaultCellSetting().alignHorizontallyCenter();
        this.layout.addToHeader(createTitleWithWarningButton());
        for (DialogBody $$2 : this.dialog.common().body()) {
            LayoutElement $$3 = DialogBodyHandlers.createBodyElement(this, $$2);
            if ($$3 != null) {
                $$1.addChild($$3);
            }
        }
        for (Input $$4 : this.dialog.common().inputs()) {
            Objects.requireNonNull($$1);
            $$0.addInput($$4, $$1::addChild);
        }
        populateBodyElements($$1, $$0, this.dialog, this.connectionAccess);
        this.bodyScroll = new ScrollableLayout(this.minecraft, $$1, this.layout.getContentHeight());
        this.layout.addToContents(this.bodyScroll);
        updateHeaderAndFooter(this.layout, $$0, this.dialog, this.connectionAccess);
        this.onClose = $$0.bindAction(this.dialog.onCancel());
        this.layout.visitWidgets($$02 -> {
            if ($$02 != this.warningButton) {
                addRenderableWidget($$02);
            }
        });
        addRenderableWidget(this.warningButton);
        repositionElements();
    }

    protected void populateBodyElements(LinearLayout $$0, DialogControlSet $$1, T $$2, DialogConnectionAccess $$3) {
    }

    protected void updateHeaderAndFooter(HeaderAndFooterLayout $$0, DialogControlSet $$1, T $$2, DialogConnectionAccess $$3) {
    }

    @Override // net.minecraft.client.gui.screens.Screen
    protected void repositionElements() {
        this.bodyScroll.setMaxHeight(this.layout.getContentHeight());
        this.layout.arrangeElements();
        makeSureWarningButtonIsInBounds();
    }

    protected LayoutElement createTitleWithWarningButton() {
        LinearLayout $$0 = LinearLayout.horizontal().spacing(10);
        $$0.defaultCellSetting().alignHorizontallyCenter().alignVerticallyMiddle();
        $$0.addChild(new StringWidget(this.title, this.font));
        $$0.addChild(this.warningButton);
        return $$0;
    }

    protected void makeSureWarningButtonIsInBounds() {
        int $$0 = this.warningButton.getX();
        int $$1 = this.warningButton.getY();
        if ($$0 < 0 || $$1 < 0 || $$0 > this.width - 20 || $$1 > this.height - 20) {
            this.warningButton.setX(Math.max(0, this.width - 40));
            this.warningButton.setY(Math.min(5, this.height));
        }
    }

    private Button createWarningButton() {
        ImageButton $$0 = new ImageButton(0, 0, 20, 20, WARNING_BUTTON_SPRITES, $$02 -> {
            this.minecraft.setScreen(WarningScreen.create(this.minecraft, this.connectionAccess, this));
        }, Component.translatable("menu.custom_screen_info.button_narration"));
        $$0.setTooltip(Tooltip.create(Component.translatable("menu.custom_screen_info.tooltip")));
        return $$0;
    }

    @Override // net.minecraft.client.gui.screens.Screen
    public boolean isPauseScreen() {
        return this.dialog.common().pause();
    }

    @Override // net.minecraft.client.gui.screens.Screen
    public boolean shouldCloseOnEsc() {
        return this.dialog.common().canCloseWithEscape();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    @Override // net.minecraft.client.gui.screens.Screen
    public void onClose() throws MatchException {
        runAction(this.onClose.get(), DialogAction.CLOSE);
    }

    public void runAction(Optional<ClickEvent> $$0) {
        runAction($$0, this.dialog.common().afterAction());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public void runAction(Optional<ClickEvent> $$0, DialogAction $$1) throws MatchException {
        Screen waitingForResponseScreen;
        switch ($$1) {
            case NONE:
                waitingForResponseScreen = this;
                break;
            case CLOSE:
                waitingForResponseScreen = this.previousScreen;
                break;
            case WAIT_FOR_RESPONSE:
                waitingForResponseScreen = new WaitingForResponseScreen(this.previousScreen);
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        Screen $$2 = waitingForResponseScreen;
        if ($$0.isPresent()) {
            handleDialogClickEvent($$0.get(), $$2);
        } else {
            this.minecraft.setScreen($$2);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    private void handleDialogClickEvent(ClickEvent $$0, Screen $$1) throws MatchException {
        Objects.requireNonNull($$0);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), ClickEvent.RunCommand.class, ClickEvent.ShowDialog.class, ClickEvent.Custom.class).dynamicInvoker().invoke($$0, 0) /* invoke-custom */) {
            case 0:
                try {
                    String $$2 = ((ClickEvent.RunCommand) $$0).command();
                    this.connectionAccess.runCommand(Commands.trimOptionalPrefix($$2), $$1);
                    return;
                } catch (Throwable th) {
                    throw new MatchException(th.toString(), th);
                }
            case 1:
                ClickEvent.ShowDialog $$3 = (ClickEvent.ShowDialog) $$0;
                this.connectionAccess.openDialog($$3.dialog(), $$1);
                return;
            case 2:
                ClickEvent.Custom $$4 = (ClickEvent.Custom) $$0;
                this.connectionAccess.sendCustomAction($$4.id(), $$4.payload());
                this.minecraft.setScreen($$1);
                return;
            default:
                defaultHandleClickEvent($$0, this.minecraft, $$1);
                return;
        }
    }

    public Screen previousScreen() {
        return this.previousScreen;
    }

    protected static LayoutElement packControlsIntoColumns(List<? extends LayoutElement> $$0, int $$1) {
        GridLayout $$2 = new GridLayout();
        $$2.defaultCellSetting().alignHorizontallyCenter();
        $$2.columnSpacing(2).rowSpacing(2);
        int $$3 = $$0.size();
        int $$4 = $$3 / $$1;
        int $$5 = $$4 * $$1;
        for (int $$6 = 0; $$6 < $$5; $$6++) {
            $$2.addChild($$0.get($$6), $$6 / $$1, $$6 % $$1);
        }
        if ($$3 != $$5) {
            LinearLayout $$7 = LinearLayout.horizontal().spacing(2);
            $$7.defaultCellSetting().alignHorizontallyCenter();
            for (int $$8 = $$5; $$8 < $$3; $$8++) {
                $$7.addChild($$0.get($$8));
            }
            $$2.addChild($$7, $$4, 0, 1, $$1);
        }
        return $$2;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/dialog/DialogScreen$WarningScreen.class */
    public static class WarningScreen extends ConfirmScreen {
        private final MutableObject<Screen> returnScreen;

        public static Screen create(Minecraft $$0, DialogConnectionAccess $$1, Screen $$2) {
            return new WarningScreen($$0, $$1, new MutableObject($$2));
        }

        private WarningScreen(Minecraft $$0, DialogConnectionAccess $$1, MutableObject<Screen> $$2) {
            super($$3 -> {
                if ($$3) {
                    $$1.disconnect(DialogScreen.DISCONNECT);
                } else {
                    $$0.setScreen((Screen) $$2.get());
                }
            }, Component.translatable("menu.custom_screen_info.title"), Component.translatable("menu.custom_screen_info.contents"), CommonComponents.disconnectButtonLabel($$0.isLocalServer()), CommonComponents.GUI_BACK);
            this.returnScreen = $$2;
        }

        public Screen returnScreen() {
            return (Screen) this.returnScreen.get();
        }

        public void updateReturnScreen(Screen $$0) {
            this.returnScreen.setValue($$0);
        }
    }
}
