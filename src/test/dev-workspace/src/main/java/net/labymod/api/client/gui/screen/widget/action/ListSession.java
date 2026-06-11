package net.labymod.api.client.gui.screen.widget.action;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.util.math.MathHelper;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/action/ListSession.class */
public class ListSession<T> {
    private final Equalizer<T> comparator;
    private float scrollPositionY;
    private float renderScrollPositionY;
    private float maxScrollPositionY;
    private float contentHeight;
    private T selectedEntry;
    private float previousScrollPositionY;
    private long lastScrollTime;
    private final List<ScrollListener> listeners;
    private EntrySwapper entrySwapper;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/action/ListSession$EntrySwapper.class */
    public interface EntrySwapper {
        void swap(int i, int i2);
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/action/ListSession$ScrollListener.class */
    public interface ScrollListener {
        void onScrolled(float f);
    }

    public ListSession() {
        this(null);
    }

    public ListSession(Equalizer<T> comparator) {
        this.maxScrollPositionY = -1.0f;
        this.previousScrollPositionY = 0.0f;
        this.listeners = new ArrayList();
        this.comparator = comparator;
    }

    public static ListSession<?> create() {
        return new ListSession<>();
    }

    public boolean compareSelectedTo(T widget) {
        return this.selectedEntry != null && (this.comparator != null ? this.comparator.equals(this.selectedEntry, widget) : widget.equals(this.selectedEntry));
    }

    public void scroll(float delta) {
        this.lastScrollTime = TimeUtil.getMillis();
        this.previousScrollPositionY = this.scrollPositionY;
        this.scrollPositionY += delta;
        for (ScrollListener listener : this.listeners) {
            listener.onScrolled(delta);
        }
    }

    public T getSelectedEntry() {
        return this.selectedEntry;
    }

    public void setSelectedEntry(T selectedEntry) {
        this.selectedEntry = selectedEntry;
    }

    public float getScrollPositionY() {
        return this.scrollPositionY;
    }

    public float getPreviousScrollPositionY() {
        return this.previousScrollPositionY;
    }

    public void setScrollPositionY(float scrollPositionY) {
        this.scrollPositionY = scrollPositionY;
    }

    public void setPreviousScrollPositionY(float previousScrollPositionY) {
        this.previousScrollPositionY = previousScrollPositionY;
    }

    public float getRenderScrollPositionY() {
        return this.renderScrollPositionY;
    }

    public void skipAnimation() {
        this.previousScrollPositionY = this.scrollPositionY;
    }

    public void setEntrySwapper(EntrySwapper entrySwapper) {
        this.entrySwapper = entrySwapper;
    }

    public EntrySwapper getEntrySwapper() {
        return this.entrySwapper;
    }

    public boolean hasSelectedEntry() {
        return this.selectedEntry != null;
    }

    public void addListener(ScrollListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(ScrollListener listener) {
        this.listeners.remove(listener);
    }

    public List<ScrollListener> getListeners() {
        return this.listeners;
    }

    public void scrollToBottom() {
        if (!isScrollbarRequired()) {
            return;
        }
        scroll(this.maxScrollPositionY - this.scrollPositionY);
    }

    public boolean isScrolledToBottom() {
        return isScrollbarRequired() && this.scrollPositionY >= this.maxScrollPositionY;
    }

    public boolean isScrollbarRequired() {
        return this.maxScrollPositionY >= 0.0f;
    }

    public float maxScrollPositionY() {
        return this.maxScrollPositionY;
    }

    public float getContentHeight() {
        return this.contentHeight;
    }

    public void updateBounds(float contentHeight, float maxScrollPositionY, VerticalAlignment fixedPosition) {
        this.contentHeight = contentHeight;
        this.maxScrollPositionY = maxScrollPositionY;
        if (this.scrollPositionY > maxScrollPositionY) {
            setScrollPositionY(maxScrollPositionY);
        }
        if (this.scrollPositionY < 0.0f) {
            setScrollPositionY(0.0f);
        }
        if (fixedPosition != null) {
            switch (fixedPosition) {
                case TOP:
                    setScrollPositionY(0.0f);
                    break;
                case CENTER:
                    setScrollPositionY(maxScrollPositionY / 2.0f);
                    break;
                case BOTTOM:
                    setScrollPositionY(maxScrollPositionY);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + String.valueOf(fixedPosition));
            }
        }
        boolean interpolateScroll = Laby.labyAPI().themeService().currentTheme().metadata().getBoolean(DefaultThemeVariables.INTERPOLATE_SCROLL);
        if (interpolateScroll) {
            float timePassed = TimeUtil.getMillis() - this.lastScrollTime;
            float percentage = MathHelper.sigmoid(MathHelper.clamp(0.01f * timePassed, 0.0f, 1.0f));
            float scrollDifference = this.scrollPositionY - this.previousScrollPositionY;
            this.renderScrollPositionY = this.previousScrollPositionY + (scrollDifference * percentage);
            return;
        }
        this.renderScrollPositionY = this.scrollPositionY;
    }

    @Deprecated
    public float getInterpolatedScrollPositionY() {
        return getRenderScrollPositionY();
    }

    @Deprecated
    public void updateBounds(float maxScrollPositionY, VerticalAlignment fixedPosition) {
        updateBounds(0.0f, maxScrollPositionY, fixedPosition);
    }
}
