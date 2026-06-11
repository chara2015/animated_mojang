package net.labymod.core.client.gui.screen.activity.activities.ingame.emote;

import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import java.util.UUID;
import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.Minecraft;
import net.labymod.api.client.chat.ChatExecutor;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity;
import net.labymod.api.client.gui.screen.activity.util.PageNavigator;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.AbstractWidget;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.client.render.model.Model;
import net.labymod.api.client.resources.CompletableResourceLocation;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.session.MinecraftServices;
import net.labymod.api.event.client.input.KeyEvent;
import net.labymod.api.mojang.model.MojangModelService;
import net.labymod.api.mojang.texture.MojangTextureService;
import net.labymod.api.mojang.texture.MojangTextureType;
import net.labymod.api.util.CharSequences;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteSegmentWidget;
import net.labymod.core.client.gui.screen.widget.widgets.emote.EmoteWheelWidget;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.animation.EmoteAnimationStorage;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/emote/EmoteWheelOverlay.class */
@Link("activity/emote-wheel.lss")
@AutoActivity
public class EmoteWheelOverlay extends AbstractWheelInteractionOverlayActivity {
    private static final Function<String, String> TRANSLATION_KEY_FACTORY = s -> {
        return "labymod.activity.emoteWheel." + s;
    };
    private static final String NO_EMOTES_TRANSLATION_KEY = TRANSLATION_KEY_FACTORY.apply("noEmotes");
    private static final String ALREADY_PLAYING_TRANSLATION_KEY = TRANSLATION_KEY_FACTORY.apply("alreadyPlaying");
    private static final String SELECT_TRANSLATION_KEY = TRANSLATION_KEY_FACTORY.apply("select");
    private static final String NOT_CONNECTED_TRANSLATION_KEY = TRANSLATION_KEY_FACTORY.apply("notConnected");
    private static final String PAGE_DAILY_EMOTES_TRANSLATION_KEY = TRANSLATION_KEY_FACTORY.apply("page.dailyEmotes");
    private final EmoteService emoteService = LabyMod.references().emoteService();
    private final Minecraft minecraft = this.labyAPI.minecraft();
    private boolean emotePlaying;

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractInteractionOverlayActivity
    protected void renderInteractionOverlay(ScreenContext context) {
        EmoteAnimationStorage animationStorage = this.emoteService.getAnimationStorage(this.minecraft.getClientPlayer());
        boolean emotePlaying = animationStorage != null && animationStorage.isPlaying();
        if (emotePlaying != this.emotePlaying) {
            this.emotePlaying = emotePlaying;
            pageNavigator().setPreviousPageToCurrentPage();
            super.reload();
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity, net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        refreshUserData();
        super.initialize(parent);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    public void closeInteractionOverlay() {
        playEmote(null, false);
        super.closeInteractionOverlay();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected Component createTitleComponent() {
        if (!hasEntries()) {
            return Component.translatable(NO_EMOTES_TRANSLATION_KEY, new Component[0]);
        }
        if (this.emotePlaying) {
            return Component.translatable(ALREADY_PLAYING_TRANSLATION_KEY, NamedTextColor.RED);
        }
        return Component.translatable(SELECT_TRANSLATION_KEY, new Component[0]);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected boolean hasEntries() {
        return !EmoteWheelWidget.Storage.INSTANCE.getUserEmotes().isEmpty();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected WheelWidget createWheelWidget() {
        EmoteWheelWidget wheel = new EmoteWheelWidget(() -> {
            return pageNavigator().getCurrentPage();
        }, () -> {
            return this.getSegmentCount();
        });
        wheel.querySupplier(() -> {
            CharSequence searchText = getSearchText();
            if (CharSequences.isEmpty(searchText)) {
                return null;
            }
            return searchText;
        });
        wheel.segmentSupplier(item -> {
            TextColor textColor;
            if (item == null) {
                WheelWidget.Segment segment = new WheelWidget.Segment();
                segment.setSelectable(false);
                return segment;
            }
            MojangModelService modelService = Laby.references().mojangModelService();
            MojangTextureService textureService = Laby.references().mojangTextureService();
            UUID uuid = this.labyAPI.getUniqueId();
            CompletableResourceLocation completable = textureService.getTexture(uuid, MojangTextureType.SKIN);
            MinecraftServices.SkinVariant variant = textureService.getVariant(completable.getCompleted());
            Model model = modelService.getPlayerModel(variant);
            boolean z = this.emotePlaying;
            boolean zBooleanValue = this.labyAPI.config().ingame().emotes().showCosmeticsInWheel().get().booleanValue();
            Style.Builder builder = Style.builder();
            if (pageNavigator().getCurrentPage() == -1) {
                textColor = NamedTextColor.GOLD;
            } else {
                textColor = NamedTextColor.WHITE;
            }
            EmoteSegmentWidget segment2 = new EmoteSegmentWidget(item, model, z, zBooleanValue, builder.color(textColor).build());
            completable.addCompletableListener(() -> {
                ResourceLocation location = completable.getCompleted();
                MinecraftServices.SkinVariant loadedVariant = textureService.getVariant(location);
                Model loadedModel = modelService.getPlayerModel(loadedVariant);
                segment2.updateModelAndTexture(loadedModel, location);
            });
            segment2.addId("emote-wrapper");
            segment2.setSelectable(true);
            return segment2;
        });
        return wheel;
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected Key getKeyToOpen() {
        return this.labyAPI.config().hotkeys().emoteWheelKey().get();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void onInitializeMappedKeys(Object2IntMap<Key> mappedKeys) {
        mappedKeys.put(Key.NUM1, 0);
        mappedKeys.put(Key.NUM2, 1);
        mappedKeys.put(Key.NUM3, 2);
        mappedKeys.put(Key.NUM4, 3);
        mappedKeys.put(Key.NUM5, 4);
        mappedKeys.put(Key.NUM6, 5);
        mappedKeys.put(Key.NUMPAD1, 0);
        mappedKeys.put(Key.NUMPAD2, 1);
        mappedKeys.put(Key.NUMPAD3, 2);
        mappedKeys.put(Key.NUMPAD4, 3);
        mappedKeys.put(Key.NUMPAD5, 4);
        mappedKeys.put(Key.NUMPAD6, 5);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected void onKey(Key key, KeyEvent.State state) {
        EmoteItem emoteItem;
        int mappedPosition = getMappedPosition(key);
        if (mappedPosition == Integer.MIN_VALUE || (emoteItem = findEmoteByPosition(mappedPosition)) == null) {
            return;
        }
        playEmote(emoteItem, true);
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected boolean shouldOpenInteractionMenu() {
        return this.labyAPI.config().ingame().emotes().emotes().get().booleanValue();
    }

    @Override // net.labymod.api.client.gui.screen.activity.types.AbstractWheelInteractionOverlayActivity
    protected Widget createPageIndicator() {
        int currentPage = pageNavigator().getCurrentPage();
        addOrRemoveDailyId(currentPage - 1 == -1);
        if (currentPage == -1) {
            return ComponentWidget.component(Component.translatable(PAGE_DAILY_EMOTES_TRANSLATION_KEY, NamedTextColor.GOLD));
        }
        return super.createPageIndicator();
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (isSearchActivityOpen()) {
            playEmote(null, true);
            closeScreen();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private void addOrRemoveDailyId(boolean add) {
        for (Widget leftMouseWidget : this.leftMouseWidgets) {
            if (leftMouseWidget != null) {
                if (add) {
                    leftMouseWidget.addId("daily");
                } else {
                    leftMouseWidget.removeId("daily");
                }
            }
        }
    }

    @Nullable
    private EmoteItem findEmoteByPosition(int position) {
        int currentPage = pageNavigator().getCurrentPage();
        if (currentPage == -1) {
            return findEmote(EmoteWheelWidget.Storage.INSTANCE.getDailyEmotes(), position);
        }
        int pagePosition = (currentPage * getSegmentCount()) + position;
        return findEmote(EmoteWheelWidget.Storage.INSTANCE.getUserEmotes(), pagePosition);
    }

    @Nullable
    private EmoteItem findEmote(IntList list, int position) {
        if (position >= list.size()) {
            return null;
        }
        return this.emoteService.getEmote(list.getInt(position));
    }

    private void playEmote(EmoteItem forcedEmote, boolean closeMenu) {
        EmoteItem emote = forcedEmote;
        if (emote == null) {
            emote = findSelectedEmote();
        }
        if (emote != null) {
            if (this.emotePlaying) {
                this.emoteService.stopClientEmote();
            } else if (!this.emoteService.playClientEmote(emote)) {
                ChatExecutor chatExecutor = this.labyAPI.minecraft().chatExecutor();
                chatExecutor.displayClientMessage(Component.translatable(NOT_CONNECTED_TRANSLATION_KEY, NamedTextColor.RED));
            }
        }
        if (closeMenu) {
            closeInteraction();
        }
    }

    @Nullable
    private EmoteItem findSelectedEmote() {
        for (AbstractWidget<?> child : wheelWidget().getChildren()) {
            if (child instanceof EmoteSegmentWidget) {
                EmoteSegmentWidget emoteSegmentWidget = (EmoteSegmentWidget) child;
                if (emoteSegmentWidget.isSelectable() && emoteSegmentWidget.isSegmentSelected()) {
                    return emoteSegmentWidget.getEmote();
                }
            }
        }
        return null;
    }

    private void refreshUserData() {
        EmoteWheelWidget.Storage storage = EmoteWheelWidget.Storage.INSTANCE;
        storage.refreshUserData();
        int maxPages = MathHelper.ceil(storage.getUserEmotes().size() / getSegmentCount()) - 1;
        PageNavigator pageNavigator = pageNavigator();
        pageNavigator.setMaximumPage(maxPages);
        pageNavigator.setMinimumPage(storage.hasDailyEmotes() ? -1 : 0);
    }
}
