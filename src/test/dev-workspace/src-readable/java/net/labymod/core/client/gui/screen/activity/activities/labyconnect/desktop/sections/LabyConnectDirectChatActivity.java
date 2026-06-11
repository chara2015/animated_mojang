package net.labymod.core.client.gui.screen.activity.activities.labyconnect.desktop.sections;

import java.util.Objects;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.GameImage;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.LabyConnectDisconnectEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatInitializeEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageDeleteEvent;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendNoteUpdateEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendServerEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendStatusEvent;
import net.labymod.api.labyconnect.protocol.model.friend.Friend;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectChatWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.chat.LabyConnectFriendProfileWidget;
import net.labymod.core.client.gui.screen.widget.widgets.labyconnect.friends.entries.LabyConnectFriendWidget;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labyconnect/desktop/sections/LabyConnectDirectChatActivity.class */
@Links({@Link("activity/labyconnect/laby-connect-chat.lss"), @Link("activity/labyconnect/laby-connect-chat-log.lss")})
@AutoActivity
public class LabyConnectDirectChatActivity extends Activity {
    private final Friend friend;
    private final LabyConnectChatWidget widgetChat;
    private LabyConnectFriendProfileWidget profile;
    private final ListSession<Widget> listSession = new ListSession<>();
    private final TextFieldWidget inputField = new TextFieldWidget();

    public LabyConnectDirectChatActivity(Friend friend) {
        this.friend = friend;
        this.inputField.addId("input-field");
        this.inputField.submitHandler(message -> {
            submitMessage();
        });
        this.inputField.imagePasteHandler(gameImage -> {
            GameImage resized = ScreenshotUtil.maxSize(gameImage, 1920, 1080);
            ResourceLocation resourceLocation = Laby.labyAPI().renderPipeline().resources().resourceLocationFactory().createLabyMod("send_image_preview");
            resized.uploadTextureAt(resourceLocation);
            Icon icon = Icon.texture(resourceLocation);
            icon.resolution(resized.getWidth(), resized.getHeight());
            PopupWidget.builder().title(Component.translatable("labymod.activity.labyconnect.chat.lanworld.share.image." + "title", new Component[0])).text(Component.translatable("labymod.activity.labyconnect.chat.lanworld.share.image." + "text", new Component[0]).arguments(Component.text(friend.getName()))).icon(icon).confirmCallback(() -> {
                try {
                    this.friend.chat().sendImage(resized);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).cancelCallback(() -> {
            }).build().displayInOverlay();
        });
        this.inputField.setText(this.friend.chat().getInputMessage());
        this.inputField.setCooldownMillis(1000L);
        this.listSession.scrollToBottom();
        this.widgetChat = new LabyConnectChatWidget(this.friend.chat(), this.listSession);
        this.widgetChat.addId("chat");
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        super.onOpenScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        String hoverTranslatable;
        super.initialize(parent);
        FlexibleContentWidget container = new FlexibleContentWidget();
        container.addId("container");
        DivWidget header = new DivWidget();
        header.addId("header");
        this.profile = new LabyConnectFriendProfileWidget(this.friend);
        this.profile.addId("profile");
        header.addChild(this.profile);
        HorizontalListWidget buttonContainer = (HorizontalListWidget) new HorizontalListWidget().addId("button-container");
        boolean singleplayer = this.labyAPI.minecraft().isSingleplayer();
        if (!this.friend.isOnline()) {
            hoverTranslatable = "notOnline";
        } else if (!singleplayer) {
            hoverTranslatable = "notSingleplayer";
        } else {
            hoverTranslatable = null;
        }
        ButtonWidget createDropdownMenu = ButtonWidget.i18n("labymod.activity.labyconnect.chat.lanworld.invite.title");
        createDropdownMenu.addId("invite-button");
        if (hoverTranslatable != null) {
            createDropdownMenu.setHoverComponent(Component.translatable("labymod.activity.labyconnect.chat.lanworld.invite." + hoverTranslatable, new Component[0]));
        }
        createDropdownMenu.setEnabled(this.friend.isOnline() && singleplayer);
        createDropdownMenu.setPressable(() -> {
            LabyMod.references().worldsharing().netEventHandler().inviteToWorld(this.friend);
        });
        buttonContainer.addEntry(createDropdownMenu);
        Laby.fireEvent(new LabyConnectChatInitializeEvent(this.friend, buttonContainer));
        ButtonWidget dropdownWidget = ButtonWidget.icon(Textures.SpriteCommon.SMALL_BURGER_DOTS);
        dropdownWidget.setContextMenu(LabyConnectFriendWidget.createContextMenu(this.friend));
        Objects.requireNonNull(dropdownWidget);
        dropdownWidget.setPressable(dropdownWidget::openContextMenu);
        buttonContainer.addEntry(dropdownWidget);
        header.addChild(buttonContainer);
        container.addContent(header);
        container.addContent(new DivWidget().addId("split"));
        container.addFlexibleContent(new ScrollWidget(this.widgetChat, this.listSession));
        FlexibleContentWidget chatInputContainer = new FlexibleContentWidget();
        chatInputContainer.addId("chat-input-container");
        this.inputField.setFocused(true);
        chatInputContainer.addFlexibleContent(this.inputField);
        container.addContent(chatInputContainer);
        ((Document) this.document).addChild(container);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        this.friend.chat().setInputMessage(this.inputField.getText());
    }

    private void submitMessage() {
        String message = this.inputField.getText().trim();
        if (message.isEmpty()) {
            return;
        }
        this.labyAPI.minecraft().sounds().playSound(Constants.Resources.SOUND_CHAT_MESSAGE, 0.35f, 1.0f);
        this.friend.chat().sendMessage(message);
        this.inputField.setText("");
        this.labyAPI.minecraft().executeNextTick(() -> {
            this.inputField.setFocused(true);
        });
    }

    @Subscribe
    public void onLabyConnectFriendRemove(LabyConnectFriendRemoveEvent event) {
        if (this.friend.equals(event.friend())) {
            displayScreen(new LabyConnectEmptyActivity());
        }
    }

    @Subscribe
    public void onLabyConnectDisconnect(LabyConnectDisconnectEvent event) {
        displayScreen(new LabyConnectEmptyActivity());
    }

    @Subscribe
    public void onLabyConnectChatMessage(LabyConnectChatMessageEvent event) {
        if (this.friend.chat().equals(event.chat())) {
            this.widgetChat.addMessage(event.message());
        }
    }

    @Subscribe
    public void onLabyConnectChatMessageDelete(LabyConnectChatMessageDeleteEvent event) {
        if (this.friend.chat().equals(event.chat())) {
            reload();
        }
    }

    @Subscribe
    public void onLabyConnectFriendStatus(LabyConnectFriendStatusEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }

    @Subscribe
    public void onLabyConnectFriendServer(LabyConnectFriendServerEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }

    @Subscribe
    public void onLabyConnectFriendNoteUpdate(LabyConnectFriendNoteUpdateEvent event) {
        if (this.friend.equals(event.friend())) {
            this.profile.reInitialize();
        }
    }

    public Friend friend() {
        return this.friend;
    }
}
