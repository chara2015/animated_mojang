package net.labymod.core.client.gui.screen.activity.activities.labymod.child.player;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import net.labymod.api.Laby;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.user.UserUpdateDataEvent;
import net.labymod.api.user.GameUser;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmoteCustomizationSegmentWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.EmotePlaceholderSegmentWidget;
import net.labymod.core.client.gui.screen.activity.activities.labymod.child.player.widgets.emotes.VariableListWidget;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteWheelWidget;
import net.labymod.core.labymodnet.DefaultLabyModNetService;
import net.labymod.core.labymodnet.LabyModNetService;
import net.labymod.core.labymodnet.event.LabyModNetRefreshEvent;
import net.labymod.core.labymodnet.models.Emote;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/player/EmotesActivity.class */
@AutoActivity
@Link("activity/player/emotes.lss")
public class EmotesActivity extends PlayerActivity.Child {
    private static final int SEGMENT_COUNT = 6;
    private static final String IDENTIFIER = "emotes";
    private final EmoteService emoteService;
    private final EmoteOverviewActivity emoteOverviewActivity;
    private int page;
    private int maxPages;
    private EmoteWheelWidget emoteWheelWidget;
    private ButtonWidget backButton;
    private ComponentWidget pageText;
    private ButtonWidget nextButton;
    private long lastUpdate;
    private EmoteItem highlightedEmote;

    public EmotesActivity(PlayerActivity playerActivity, String translationKeyPrefix) {
        super(playerActivity, translationKeyPrefix + "emotes.", IDENTIFIER);
        this.emoteService = LabyMod.references().emoteService();
        this.emoteOverviewActivity = new EmoteOverviewActivity(this);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        VariableListWidget container = new VariableListWidget();
        container.addId("container");
        ComponentWidget title = ComponentWidget.i18n(this.translationKeyPrefix + "title");
        title.addId("title");
        container.addChild(title);
        this.emoteWheelWidget = new EmoteWheelWidget(() -> {
            return this.page;
        }, () -> {
            return 6;
        });
        this.emoteWheelWidget.addId("emote-wheel");
        this.emoteWheelWidget.segmentSupplier((index, wheelIndex, item) -> {
            if (item == null) {
                EmotePlaceholderSegmentWidget segment = new EmotePlaceholderSegmentWidget(Textures.SpriteCommon.DARK_ADD, Component.text("Shop"));
                segment.setPressable(() -> {
                    Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                    this.labyAPI.minecraft().chatExecutor().openUrl("https://www.labymod.net/shop#emotes");
                });
                segment.addId("placeholder-segment");
                return segment;
            }
            EmoteCustomizationSegmentWidget segment2 = new EmoteCustomizationSegmentWidget(this, index, wheelIndex, item);
            if (item == this.highlightedEmote) {
                segment2.highlightAt(this.lastUpdate);
                if (!segment2.isHighlighted()) {
                    this.highlightedEmote = null;
                    this.lastUpdate = 0L;
                }
            }
            segment2.addId("present-emote-segment");
            segment2.setSelectable(true);
            segment2.setPressable(() -> {
                Laby.references().soundService().play(SoundType.BUTTON_CLICK);
                this.emoteOverviewActivity.display(item, wheelIndex, this.maxPages == 0 ? -1 : this.page);
            });
            return segment2;
        });
        IconWidget listWidget = new IconWidget(Textures.SpriteFlint.DOCUMENT);
        listWidget.addId("emote-list");
        listWidget.setHoverComponent(Component.translatable(this.translationKeyPrefix + "openOverview", new Component[0]));
        listWidget.setPressable(() -> {
            Laby.references().soundService().play(SoundType.BUTTON_CLICK);
            this.emoteOverviewActivity.display();
        });
        this.emoteWheelWidget.addChild(listWidget);
        container.setFlexibleChild(this.emoteWheelWidget);
        HorizontalListWidget buttonContainer = new HorizontalListWidget();
        buttonContainer.addId("pagination-container");
        this.backButton = ButtonWidget.i18n(this.translationKeyPrefix + "previousPage");
        this.backButton.addId("back-button");
        this.backButton.setPressable(() -> {
            navigate(-1);
        });
        buttonContainer.addEntry(this.backButton);
        this.pageText = ComponentWidget.text("");
        this.pageText.addId("current-page");
        container.addChild(this.pageText);
        this.nextButton = ButtonWidget.i18n(this.translationKeyPrefix + "nextPage");
        this.nextButton.addId("next-button");
        this.nextButton.setPressable(() -> {
            navigate(1);
        });
        buttonContainer.addEntry(this.nextButton);
        container.addChild(buttonContainer);
        ((Document) this.document).addChild(container);
        navigate(0);
    }

    private void navigate(int offset) {
        int prevPage = this.page;
        this.page += offset;
        if (this.page < 0) {
            this.page = 0;
        } else if (this.page > this.maxPages) {
            this.page = this.maxPages;
        }
        if (offset != 0 && this.page == prevPage) {
            return;
        }
        if (this.backButton != null) {
            this.backButton.setEnabled(this.page > 0);
        }
        if (this.pageText != null) {
            this.pageText.setComponent(Component.translatable(this.translationKeyPrefix + "pagination", Component.text(Integer.valueOf(this.page + 1)), Component.text(Integer.valueOf(this.maxPages + 1))));
        }
        if (this.nextButton != null) {
            this.nextButton.setEnabled(this.page < this.maxPages);
        }
        if (offset != 0 && this.emoteWheelWidget != null) {
            this.emoteWheelWidget.refresh();
        }
    }

    @Override // net.labymod.core.client.gui.screen.activity.activities.labymod.child.PlayerActivity.Child
    protected void onSessionUpdate(PlayerActivity.UpdateContext context, UUID uniqueId) {
        EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        if (storage.getUniqueId() == null || !storage.getUniqueId().equals(uniqueId)) {
            if (context == PlayerActivity.UpdateContext.INITIALIZE && LabyMod.references().labyModNetService().getState() == LabyModNetService.State.OK) {
                storage.refreshUserData();
            } else {
                storage.getUserEmotes().clear();
            }
        }
        refresh(storage);
    }

    @Subscribe
    public void onLabyModNetUpdate(LabyModNetRefreshEvent event) {
        DefaultLabyModNetService labyModNetService = (DefaultLabyModNetService) LabyMod.references().labyModNetService();
        if (labyModNetService.getState() == LabyModNetService.State.OK) {
            EmoteWheelWidget.Storage.INSTANCE.refreshUserData();
            refresh(EmoteWheelWidget.Storage.INSTANCE);
        }
    }

    @Subscribe
    public void onUserUpdateData(UserUpdateDataEvent event) {
        if (event.phase() != Phase.POST) {
            return;
        }
        GameUser gameUser = event.gameUser();
        if (!(gameUser instanceof DefaultGameUser) || !gameUser.getUniqueId().equals(this.uniqueId)) {
            return;
        }
        EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        refresh(storage);
    }

    private void refresh(EmoteWheelWidget.Storage storage) {
        this.maxPages = (int) Math.ceil(storage.getUserEmotes().size() / 6.0f);
        if (this.maxPages > 0) {
            this.maxPages--;
        }
        navigate(0);
        reload();
    }

    public void playEmote(EmoteItem emoteItem) {
        this.playerActivity.modelWidget().playEmote(emoteItem);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        if (super.keyPressed(key, type)) {
            return true;
        }
        if (key == Key.ARROW_LEFT) {
            navigate(-1);
            return true;
        }
        if (key == Key.ARROW_RIGHT) {
            navigate(1);
            return true;
        }
        return false;
    }

    public void stopEmote(EmoteItem condition) {
        this.playerActivity.modelWidget().stopEmote(condition);
    }

    public void swap(EmoteItem a, EmoteItem b) {
        this.lastUpdate = TimeUtil.getMillis();
        this.highlightedEmote = b;
        Laby.references().soundService().play(SoundType.HUD_ATTACH);
        Emote firstEmote = null;
        Emote secondEmote = null;
        GameUser gameUser = LabyMod.references().gameUserService().clientGameUser();
        if (gameUser instanceof DefaultGameUser) {
            DefaultGameUser defaultGameUser = (DefaultGameUser) gameUser;
            List<Integer> emotes = defaultGameUser.getUserData().getEmotes();
            int firstIndex = -1;
            int secondIndex = -1;
            for (int i = 0; i < emotes.size(); i++) {
                int id = emotes.get(i).intValue();
                if (id == a.getId()) {
                    firstIndex = i;
                } else if (id == b.getId()) {
                    secondIndex = i;
                }
            }
            if (firstIndex != -1) {
                if (secondIndex == -1) {
                    emotes.remove(firstIndex);
                    emotes.add(firstIndex, Integer.valueOf(b.getId()));
                } else {
                    Collections.swap(emotes, firstIndex, secondIndex);
                }
                EmoteWheelWidget.Storage.INSTANCE.refreshUserData();
                if (this.playerActivity.getActiveChild() instanceof EmotesActivity) {
                    this.emoteWheelWidget.refresh();
                }
            }
        }
        for (Emote emote : LabyMod.references().labyModNetService().getUserItems().getEmotes()) {
            if (emote.getItemId() == a.getId()) {
                firstEmote = emote;
            } else if (emote.getItemId() == b.getId()) {
                secondEmote = emote;
            }
        }
        if (firstEmote == null || secondEmote == null) {
            return;
        }
        int order = firstEmote.getOrder();
        firstEmote.setOrder(secondEmote.getOrder());
        secondEmote.setOrder(order);
        LabyMod.references().labyModNetService().updateEmotes(response -> {
        });
    }
}
