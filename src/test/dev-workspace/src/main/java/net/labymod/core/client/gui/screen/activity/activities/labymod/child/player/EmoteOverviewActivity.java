package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.CheckBoxWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.event.Subscribe;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.DummySegmentWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmoteOverviewWidget;
import net.labymod.core.flint.FlintUrls;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.models.Emote;
import net.labymod.core.labymodnet.models.UserItems;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/EmoteOverviewActivity.class */
@AutoActivity
@Link("activity/player/emotes-overview.lss")
public class EmoteOverviewActivity extends PlayerActivity.Child {
    private final DefaultLabyModNetService labyModNetService;
    private final EmoteService emoteService;
    private final EmotesActivity emotesActivity;
    private String searchQuery;
    private boolean showUnowned;
    private int selectedSlot;
    private int page;
    private EmoteItem selectedEmote;

    public EmoteOverviewActivity(EmotesActivity emotesActivity) {
        super(emotesActivity.playerActivity(), emotesActivity.getTranslationKeyPrefix() + "overview.", emotesActivity.getGroupIdentifier());
        this.searchQuery = "";
        this.showUnowned = false;
        this.selectedSlot = -1;
        this.labyModNetService = (DefaultLabyModNetService) LabyMod.references().labyModNetService();
        this.emoteService = LabyMod.references().emoteService();
        this.emotesActivity = emotesActivity;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        FlexibleContentWidget flexibleContentWidget = new FlexibleContentWidget();
        flexibleContentWidget.addId("content");
        if (this.selectedEmote != null) {
            FlexibleContentWidget sideBar = new FlexibleContentWidget();
            sideBar.addId("sidebar");
            ComponentWidget titleWidget = ComponentWidget.i18n(this.translationKeyPrefix + "swapTitle", this.selectedEmote.getName());
            titleWidget.addId("title");
            sideBar.addContent(titleWidget);
            DivWidget wheelWrapper = new DivWidget();
            wheelWrapper.addId("wheel-wrapper");
            WheelWidget wheel = new WheelWidget();
            wheel.addId("wheel");
            int i = 0;
            while (i < 6) {
                wheel.addSegment((WheelWidget.Segment) new DummySegmentWidget(i == this.selectedSlot).addId("segment-" + i));
                i++;
            }
            wheelWrapper.addChild(wheel);
            if (this.page != -1) {
                ComponentWidget page = ComponentWidget.i18n(this.translationKeyPrefix + "page", Integer.valueOf(this.page + 1));
                page.addId(FlintUrls.QUERY_PAGE_PARAM);
                wheelWrapper.addChild(page);
            }
            sideBar.addContent(wheelWrapper);
            sideBar.addFlexibleContent(new DivWidget());
            ButtonWidget cancelButton = ButtonWidget.i18n("labymod.ui.button.cancel");
            cancelButton.addId("cancel-button");
            cancelButton.setPressable(() -> {
                this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
            });
            sideBar.addContent(cancelButton);
            flexibleContentWidget.addContent(sideBar);
        }
        FlexibleContentWidget flexibleContentWidget2 = new FlexibleContentWidget();
        flexibleContentWidget2.addId("list-wrapper");
        VerticalListWidget<Widget> list = new VerticalListWidget<>();
        list.addId("list");
        fill(list);
        HorizontalListWidget header = new HorizontalListWidget();
        header.addId("header");
        if (this.selectedEmote == null) {
            ButtonWidget cancelButton2 = ButtonWidget.icon(Textures.SpriteCommon.BACK_BUTTON);
            cancelButton2.addId("cancel-button");
            cancelButton2.setPressable(() -> {
                this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
            });
            header.addEntry(cancelButton2);
            ComponentWidget titleWidget2 = ComponentWidget.i18n(this.translationKeyPrefix + "genericTitle");
            titleWidget2.addId("title");
            header.addEntry(titleWidget2);
        }
        TextFieldWidget searchField = new TextFieldWidget();
        searchField.addId("search-field");
        if (this.selectedEmote == null) {
            searchField.addId("full-size");
        }
        searchField.setText(this.searchQuery);
        searchField.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        searchField.updateListener(query -> {
            if (query.trim().isEmpty() && !this.searchQuery.trim().isEmpty()) {
                this.searchQuery = "";
            } else {
                this.searchQuery = query;
            }
            fill(list);
        });
        header.addEntry(searchField);
        CheckBoxWidget showUnownedCheckBox = new CheckBoxWidget();
        showUnownedCheckBox.addId("show-unowned");
        showUnownedCheckBox.setState(this.showUnowned ? CheckBoxWidget.State.CHECKED : CheckBoxWidget.State.UNCHECKED);
        showUnownedCheckBox.setPressable(() -> {
            this.showUnowned = !this.showUnowned;
            fill(list);
        });
        ButtonWidget showUnownedButton = ButtonWidget.i18n(this.translationKeyPrefix + "showUnowned");
        showUnownedButton.addId("show-unowned-button");
        Objects.requireNonNull(showUnownedCheckBox);
        showUnownedButton.setActionListener(showUnownedCheckBox::onPress);
        showUnownedButton.addEntry(showUnownedCheckBox);
        header.addEntry(showUnownedButton);
        flexibleContentWidget2.addContent(header);
        flexibleContentWidget2.addFlexibleContent(new ScrollWidget((VerticalListWidget<?>) list).addId("player-scroll"));
        flexibleContentWidget.addFlexibleContent(flexibleContentWidget2);
        ((Document) this.document).addChild(flexibleContentWidget);
    }

    private void fill(VerticalListWidget<Widget> list) {
        List<Emote> userEmotes;
        list.getChildren().clear();
        UserItems userItems = this.labyModNetService.getUserItems();
        if (userItems == null) {
            userEmotes = new ArrayList();
        } else {
            userEmotes = userItems.getEmotes();
        }
        List<EmoteItem> emotes = new ArrayList<>();
        String query = this.searchQuery.trim().toLowerCase(Locale.ROOT);
        if (query.length() == 0) {
            query = null;
        }
        for (Emote userEmote : userEmotes) {
            EmoteItem emote = this.emoteService.getEmote(userEmote.getItemId());
            if (emote != null && (query == null || emote.getName().toLowerCase(Locale.ROOT).contains(query))) {
                emotes.add(emote);
            }
        }
        emotes.sort((a, b) -> {
            return a.getName().compareToIgnoreCase(b.getName());
        });
        for (EmoteItem ownedEmote : emotes) {
            EmoteOverviewWidget emoteOverviewWidget = new EmoteOverviewWidget(this.emotesActivity, ownedEmote);
            if (this.selectedEmote != null) {
                if (ownedEmote.getId() == this.selectedEmote.getId()) {
                    emoteOverviewWidget.addId("unselectable");
                } else {
                    emoteOverviewWidget.setPressable(() -> {
                        this.emotesActivity.swap(this.selectedEmote, ownedEmote);
                        this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
                    });
                }
            }
            if (((Document) this.document).isInitialized()) {
                list.addChildInitialized(emoteOverviewWidget, false);
            } else {
                list.addChild(emoteOverviewWidget, false);
            }
        }
        if (list.getChildren().isEmpty()) {
            LabyModNetService.State state = this.labyModNetService.getState();
            String translationKey = null;
            if (state == LabyModNetService.State.NOT_CONNECTED) {
                translationKey = "notConnected";
            } else if (state == LabyModNetService.State.LOADING || state == LabyModNetService.State.ACCOUNT_CHANGED) {
                translationKey = "loading";
            } else if (state == LabyModNetService.State.ERROR) {
                translationKey = "error";
            }
            if (translationKey != null) {
                ComponentWidget componentWidget = ComponentWidget.i18n(this.translationKeyPrefix + translationKey);
                componentWidget.addId("no-emotes");
                if (((Document) this.document).isInitialized()) {
                    list.addChildInitialized(componentWidget, false);
                } else {
                    list.addChild(componentWidget, false);
                }
            }
        }
        if (!this.showUnowned) {
            appendNoEmotesComponent(list);
            list.sortChildren();
            list.updateBounds();
            return;
        }
        emotes.clear();
        ObjectIterator it = this.emoteService.getEmotes().int2ObjectEntrySet().iterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<EmoteItem> emoteItemEntry = (Int2ObjectMap.Entry) it.next();
            if (query == null || ((EmoteItem) emoteItemEntry.getValue()).getName().toLowerCase(Locale.ROOT).contains(query)) {
                int id = emoteItemEntry.getIntKey();
                boolean found = false;
                Iterator<Emote> it2 = userEmotes.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    Emote userEmote2 = it2.next();
                    if (userEmote2.getItemId() == id) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    EmoteItem value = (EmoteItem) emoteItemEntry.getValue();
                    if (!value.isDraft()) {
                        emotes.add(value);
                    }
                }
            }
        }
        if (!emotes.isEmpty()) {
            ComponentWidget unownedSubtitle = ComponentWidget.i18n(this.translationKeyPrefix + "unownedSubtitle");
            unownedSubtitle.addId("subtitle");
            if (((Document) this.document).isInitialized()) {
                list.addChildInitialized(unownedSubtitle, false);
            } else {
                list.addChild(unownedSubtitle, false);
            }
        }
        emotes.sort((a2, b2) -> {
            return a2.getName().compareToIgnoreCase(b2.getName());
        });
        Iterator<EmoteItem> it3 = emotes.iterator();
        while (it3.hasNext()) {
            EmoteOverviewWidget emoteOverviewWidget2 = (EmoteOverviewWidget) new EmoteOverviewWidget(this.emotesActivity, it3.next()).addId("unselectable");
            if (((Document) this.document).isInitialized()) {
                list.addChildInitialized(emoteOverviewWidget2, false);
            } else {
                list.addChild(emoteOverviewWidget2, false);
            }
        }
        appendNoEmotesComponent(list);
        list.sortChildren();
        list.updateBounds();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity
    public boolean shouldHandleEscape() {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (key == Key.ESCAPE) {
            this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
            return true;
        }
        return super.keyPressed(key, type);
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    protected void onSessionUpdate(PlayerActivity.UpdateContext context, UUID uniqueId) {
        if (context == PlayerActivity.UpdateContext.EVENT) {
            this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    protected void onLabyModRefresh(PlayerActivity.UpdateContext context) {
        this.playerActivity.displayScreen((PlayerActivity.Child) this.emotesActivity);
    }

    @Subscribe
    public void onLabyModNetUpdate(LabyModNetRefreshEvent event) {
        reload();
    }

    public void display(EmoteItem emoteItem, int slot, int page) {
        this.selectedEmote = emoteItem;
        this.selectedSlot = slot;
        this.page = page;
        this.playerActivity.displayScreen((PlayerActivity.Child) this);
    }

    public void display() {
        this.selectedEmote = null;
        this.selectedSlot = -1;
        this.page = 0;
        this.playerActivity.displayScreen((PlayerActivity.Child) this);
    }

    private void appendNoEmotesComponent(VerticalListWidget<Widget> list) {
        String key;
        if (!list.getChildren().isEmpty()) {
            return;
        }
        if (this.searchQuery.isEmpty()) {
            key = "noEmotes";
        } else {
            key = "noEmotesSearch";
        }
        ComponentWidget componentWidget = ComponentWidget.i18n(this.translationKeyPrefix + key);
        componentWidget.addId("no-emotes");
        if (((Document) this.document).isInitialized()) {
            list.addChildInitialized(componentWidget, false);
        } else {
            list.addChild(componentWidget, false);
        }
    }
}
