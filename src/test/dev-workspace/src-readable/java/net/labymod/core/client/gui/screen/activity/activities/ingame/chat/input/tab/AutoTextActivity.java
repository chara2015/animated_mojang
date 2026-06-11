package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.chat.autotext.AutoTextEntry;
import net.labymod.api.client.chat.autotext.AutoTextService;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.activities.labymod.child.SettingContentActivity;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabSettingActivity;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.core.client.chat.DefaultChatProvider;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/input/tab/AutoTextActivity.class */
@Link("activity/chat/input/auto-text.lss")
@AutoActivity
public class AutoTextActivity extends ChatInputTabSettingActivity<FlexibleContentWidget> {
    private final AutoTextService autoTextService = Laby.references().autoTextService();
    private AutoTextEntry original;
    private AutoTextEntry editing;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        ((Document) this.document).addChild(createWindow());
    }

    @NotNull
    private AbstractWidget<?> createWindow() {
        this.contentWidget = new FlexibleContentWidget();
        ((FlexibleContentWidget) this.contentWidget).addId("window");
        DivWidget titleBar = new DivWidget();
        titleBar.addId("title-bar");
        ComponentWidget title = ComponentWidget.component(Component.translatable("labymod.chatInput.tab.autotext.name", new Component[0]));
        title.addId("title");
        titleBar.addChild(title);
        ButtonWidget button = ButtonWidget.icon(this.editing == null ? Textures.SpriteCommon.SMALL_ADD : Textures.SpriteCommon.SMALL_CHECKED);
        button.addId(this.editing == null ? "add" : "save");
        button.setPressable(() -> {
            if (this.editing == null) {
                this.original = null;
                this.editing = new AutoTextEntry();
                reload();
                return;
            }
            if (this.editing.isConfigured()) {
                this.autoTextService.removeEntry(this.original == null ? this.editing : this.original);
                this.autoTextService.addEntry(this.editing);
                ((DefaultChatProvider) this.labyAPI.chatProvider()).autoTextConfigProvider().save();
            }
            this.original = null;
            this.editing = null;
            reload();
        });
        titleBar.addChild(button);
        ((FlexibleContentWidget) this.contentWidget).addContent(titleBar);
        DivWidget contentWrapper = new DivWidget();
        contentWrapper.addId("content-wrapper");
        if (this.editing == null) {
            VerticalListWidget<Widget> list = new VerticalListWidget<>();
            list.addId("entries");
            for (AutoTextEntry entry : this.autoTextService.getEntries()) {
                list.addChild(createEntry(entry));
            }
            ScrollWidget scrollWidget = new ScrollWidget((VerticalListWidget<?>) list);
            scrollWidget.addId("scroll-widget");
            contentWrapper.addChild(scrollWidget);
        } else {
            ScreenRendererWidget screen = new ScreenRendererWidget(true);
            screen.addId("settings");
            SettingContentActivity settings = new SettingContentActivity(this.editing.asRegistry("autotext").translationId("chatInput.tab.autotext"), false);
            settings.setHeaderType(SettingContentActivity.HeaderType.FIXED_IN_CHILDREN);
            screen.displayScreen(settings);
            contentWrapper.addChild(screen);
        }
        ((FlexibleContentWidget) this.contentWidget).addFlexibleContent(contentWrapper);
        return (AbstractWidget) this.contentWidget;
    }

    private Widget createEntry(AutoTextEntry entry) {
        DivWidget list = new DivWidget();
        list.addId("entry");
        String displayName = entry.displayName().get();
        if (displayName.isEmpty()) {
            displayName = entry.message().get();
        }
        ComponentWidget keyWidget = ComponentWidget.component(Component.text(displayName, NamedTextColor.GREEN));
        keyWidget.addId("display-name");
        list.addChild(keyWidget);
        IconWidget delete = new IconWidget(Textures.SpriteCommon.SMALL_X);
        list.addChild(delete).addId("delete");
        delete.setPressable(() -> {
            this.autoTextService.removeEntry(entry);
            ((DefaultChatProvider) this.labyAPI.chatProvider()).autoTextConfigProvider().save();
            reload();
        });
        list.setPressable(() -> {
            this.original = entry;
            this.editing = entry.copy();
            if (this.editing.displayName().get().isEmpty()) {
                this.editing.displayName().set(this.editing.message().get());
            }
            reload();
        });
        return list;
    }
}
