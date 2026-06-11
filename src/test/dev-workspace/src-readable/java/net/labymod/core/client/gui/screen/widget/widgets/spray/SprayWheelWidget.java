package net.labymod.core.client.gui.screen.widget.widgets.spray;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.laby.lib.sprays.Spray;
import net.laby.lib.sprays.SprayPack;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.widgets.WheelWidget;
import net.labymod.api.util.CharSequences;
import net.labymod.core.generated.DefaultReferenceStorage;
import net.labymod.core.main.LabyMod;
import net.labymod.core.main.user.DefaultGameUser;
import net.labymod.core.main.user.DefaultGameUserService;
import net.labymod.core.main.user.GameUserData;
import net.labymod.core.main.user.shop.spray.SprayService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/spray/SprayWheelWidget.class */
@AutoWidget
@Link("widget/spray-wheel.lss")
public class SprayWheelWidget extends WheelWidget {
    private final IntSupplier pageSupplier;
    private final IntSupplier segmentCountSupplier;
    private final List<Spray> sprays = new ArrayList();
    private Supplier<CharSequence> querySupplier;
    private SegmentSupplier segmentSupplier;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/spray/SprayWheelWidget$SegmentSupplier.class */
    public interface SegmentSupplier {
        WheelWidget.Segment get(int i, int i2, Spray spray);
    }

    public SprayWheelWidget(IntSupplier pageSupplier, IntSupplier segmentCountSupplier) {
        this.pageSupplier = pageSupplier;
        this.segmentCountSupplier = segmentCountSupplier;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Storage.INSTANCE.refreshUserData();
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
        List<Spray> source = storage.getSprays();
        this.sprays.clear();
        filterSpraysBySearchQuery(searchQuery, source);
        int size = this.sprays.size();
        boolean clockwise = this.labyAPI.config().ingame().spray().orderSprayClockwise().get().booleanValue();
        int segmentCount = this.segmentCountSupplier.getAsInt();
        for (int i = 0; i < segmentCount; i++) {
            if (clockwise) {
                index = ((segmentCount - i) + (page * segmentCount)) - 1;
            } else {
                index = ((i + 1) % segmentCount) + (page * segmentCount);
            }
            Spray spray = (index < 0 || index >= size) ? null : this.sprays.get(index);
            WheelWidget.Segment segment = this.segmentSupplier.get(index, i, spray);
            if (this.initialized) {
                addSegmentInitialized(segment);
            } else {
                addSegment(segment);
            }
        }
    }

    private void filterSpraysBySearchQuery(CharSequence searchQuery, List<Spray> source) {
        for (Spray spray : source) {
            if (searchQuery == null) {
                this.sprays.add(spray);
            } else if (containsIgnoreCase(searchQuery, spray.name())) {
                this.sprays.add(spray);
            } else {
                Iterator it = spray.tags().iterator();
                while (true) {
                    if (it.hasNext()) {
                        String tag = (String) it.next();
                        if (containsIgnoreCase(searchQuery, tag)) {
                            this.sprays.add(spray);
                            break;
                        }
                    }
                }
            }
        }
    }

    private boolean containsIgnoreCase(CharSequence searchQuery, CharSequence value) {
        return CharSequences.contains(CharSequences.toLowerCase(value), searchQuery);
    }

    public SprayWheelWidget querySupplier(Supplier<CharSequence> querySupplier) {
        this.querySupplier = querySupplier;
        return this;
    }

    public SprayWheelWidget segmentSupplier(Function<Spray, WheelWidget.Segment> segmentSupplier) {
        return segmentSupplier((index, wheelIndex, sprayPack) -> {
            return (WheelWidget.Segment) segmentSupplier.apply(sprayPack);
        });
    }

    public SprayWheelWidget segmentSupplier(SegmentSupplier segmentSupplier) {
        this.segmentSupplier = segmentSupplier;
        return this;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/spray/SprayWheelWidget$Storage.class */
    public static class Storage {
        public static final Storage INSTANCE = new Storage();
        private final List<Spray> sprays = new ArrayList();
        private UUID uniqueId;

        private Storage() {
        }

        public void refreshUserData() {
            this.sprays.clear();
            DefaultReferenceStorage references = LabyMod.references();
            DefaultGameUserService gameUserService = (DefaultGameUserService) references.gameUserService();
            DefaultGameUser gameUser = (DefaultGameUser) gameUserService.clientGameUser();
            GameUserData userData = gameUser.getUserData();
            this.uniqueId = gameUser.getUniqueId();
            SprayService sprayService = references.sprayService();
            for (Short packId : userData.getSprayPacks().getShorts()) {
                SprayPack pack = sprayService.findPack(packId.shortValue());
                if (pack != null) {
                    this.sprays.addAll(pack.sprays());
                }
            }
        }

        public List<Spray> getSprays() {
            return this.sprays;
        }

        public UUID getUniqueId() {
            return this.uniqueId;
        }
    }
}
