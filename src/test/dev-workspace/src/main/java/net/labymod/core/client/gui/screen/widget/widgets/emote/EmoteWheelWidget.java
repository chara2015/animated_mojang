package net.labymod.core.client.gui.screen.widget.widgets.emote;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntListIterator;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.util.CharSequences;
import net.labymod.core.configuration.labymod.LabyConfigProvider;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.advertisement.Advertisement;
import net.labymod.core.main.advertisement.model.DailyEmote;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.shop.emote.EmoteService;
import net.labymod.core.main.user.shop.emote.model.DailyEmoteFlat;
import net.labymod.core.main.user.shop.emote.model.EmoteItem;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/emote/EmoteWheelWidget.class */
@AutoWidget
@Link("widget/emote-wheel.lss")
public class EmoteWheelWidget extends WheelWidget {

    @Deprecated(forRemoval = true, since = "4.1.18")
    public static final int SEGMENTS = 6;
    public static final int DAILY_EMOTES_PAGE = -1;
    private static final EmoteService EMOTE_SERVICE = LabyMod.references().emoteService();
    private final IntSupplier pageSupplier;
    private final IntSupplier segmentCountSupplier;
    private final List<EmoteItem> emotes = new ArrayList();
    private Supplier<CharSequence> querySupplier;
    private SegmentSupplier segmentSupplier;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/emote/EmoteWheelWidget$SegmentSupplier.class */
    public interface SegmentSupplier {
        WheelWidget.Segment get(int i, int i2, EmoteItem emoteItem);
    }

    public EmoteWheelWidget(IntSupplier pageSupplier, IntSupplier segmentCountSupplier) {
        this.pageSupplier = pageSupplier;
        this.segmentCountSupplier = segmentCountSupplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        refresh();
        super.initialize(parent);
    }

    public void refresh() {
        int index;
        removeChildIf(widget -> {
            return widget instanceof WheelWidget.Segment;
        });
        if (this.segmentSupplier == null) {
            return;
        }
        CharSequence searchQuery = this.querySupplier == null ? null : this.querySupplier.get();
        int page = this.pageSupplier.getAsInt();
        Storage storage = Storage.INSTANCE;
        boolean dailyEmotesPage = page == -1;
        IntList source = dailyEmotesPage ? storage.getDailyEmotes() : storage.getUserEmotes();
        this.emotes.clear();
        IntListIterator it = source.iterator();
        while (it.hasNext()) {
            int id = ((Integer) it.next()).intValue();
            EmoteItem emote = EMOTE_SERVICE.getEmote(id);
            if (emote != null && (searchQuery == null || CharSequences.contains(emote.getLowercaseName(), searchQuery))) {
                this.emotes.add(emote);
            }
        }
        int size = this.emotes.size();
        boolean clockwise = LabyConfigProvider.INSTANCE.get().ingame().emotes().orderEmotesClockwise().get().booleanValue();
        int segmentCount = this.segmentCountSupplier.getAsInt();
        for (int i = 0; i < segmentCount; i++) {
            if (dailyEmotesPage) {
                index = (segmentCount - i) % segmentCount;
            } else if (clockwise) {
                index = ((segmentCount - i) + (page * segmentCount)) - 1;
            } else {
                index = ((i + 1) % segmentCount) + (page * segmentCount);
            }
            EmoteItem item = (index < 0 || index >= size) ? null : this.emotes.get(index);
            WheelWidget.Segment segment = this.segmentSupplier.get(index, i, item);
            if (this.initialized) {
                addSegmentInitialized(segment);
            } else {
                addSegment(segment);
            }
        }
    }

    public EmoteWheelWidget querySupplier(Supplier<CharSequence> querySupplier) {
        this.querySupplier = querySupplier;
        return this;
    }

    public EmoteWheelWidget segmentSupplier(Function<EmoteItem, WheelWidget.Segment> segmentSupplier) {
        return segmentSupplier((index, wheelIndex, emote) -> {
            return (WheelWidget.Segment) segmentSupplier.apply(emote);
        });
    }

    public EmoteWheelWidget segmentSupplier(SegmentSupplier segmentSupplier) {
        this.segmentSupplier = segmentSupplier;
        return this;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/emote/EmoteWheelWidget$Storage.class */
    public static class Storage {
        public static final Storage INSTANCE = new Storage();
        private final IntList userEmotes = new IntArrayList();
        private final IntList dailyEmotes = new IntArrayList();
        private UUID uniqueId;
        private boolean hasDailyEmotes;

        private Storage() {
            refreshDailyEmotes();
        }

        public void refreshUserData() {
            IntSet emotes;
            this.userEmotes.clear();
            DefaultGameUserService gameUserService = (DefaultGameUserService) LabyMod.references().gameUserService();
            DefaultGameUser gameUser = (DefaultGameUser) gameUserService.clientGameUser();
            GameUserData userData = gameUser.getUserData();
            this.uniqueId = gameUser.getUniqueId();
            boolean emoteDebug = LabyConfigProvider.INSTANCE.get().ingame().emotes().emoteDebug().get().booleanValue();
            IntList intList = this.userEmotes;
            if (emoteDebug) {
                emotes = LabyMod.references().emoteService().getEmotes().keySet();
            } else {
                emotes = userData.getEmotes();
            }
            intList.addAll(emotes);
            DailyEmoteFlat dailyEmoteFlat = userData.getDailyEmoteFlat();
            this.hasDailyEmotes = dailyEmoteFlat != null && dailyEmoteFlat.hasFlat();
        }

        public void refreshDailyEmotes() {
            this.dailyEmotes.clear();
            Advertisement advertisement = LabyMod.references().advertisementService().getAdvertisement();
            if (advertisement != null) {
                for (DailyEmote dailyEmote : advertisement.dailyEmotes()) {
                    this.dailyEmotes.add(dailyEmote.id());
                }
            }
        }

        public IntList getUserEmotes() {
            return this.userEmotes;
        }

        public IntList getDailyEmotes() {
            return this.dailyEmotes;
        }

        public UUID getUniqueId() {
            return this.uniqueId;
        }

        public boolean hasDailyEmotes() {
            return this.hasDailyEmotes;
        }
    }
}
