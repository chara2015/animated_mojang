package net.labymod.api.configuration.labymod.chat.config;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import net.labymod.api.client.gui.HorizontalAlignment;
import net.labymod.api.client.gui.VerticalAlignment;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.configuration.labymod.chat.category.GeneralChatTabConfig;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.bounds.Rectangle;
import net.labymod.api.util.math.MathHelper;
import org.spongepowered.include.com.google.common.collect.Lists;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/config/ChatWindowConfig.class */
public class ChatWindowConfig extends Config {
    private float width = 300.0f;
    private float height = 150.0f;
    private VerticalAlignment verticalAnchor = VerticalAlignment.BOTTOM;
    private HorizontalAlignment horizontalAnchor = HorizontalAlignment.LEFT;
    private BoundsPosition boundsPosition = BoundsPosition.INSIDE;
    private float x = 0.0f;
    private float y = 0.0f;
    private int focusedTab = 0;
    private List<RootChatTabConfig> tabs = Lists.newArrayList();

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/configuration/labymod/chat/config/ChatWindowConfig$BoundsPosition.class */
    public enum BoundsPosition {
        OUTSIDE,
        INSIDE
    }

    public ChatWindowConfig(RootChatTabConfig... chatTabs) {
        Collections.addAll(this.tabs, chatTabs);
    }

    public ChatWindowConfig() {
        this.tabs.add(createDefaultTab());
    }

    public MutableRectangle getPosition(Bounds documentBounds) {
        float maxY;
        float bottom;
        float top;
        float maxX;
        float right;
        float left;
        float x;
        float y;
        boolean inside = this.boundsPosition != BoundsPosition.OUTSIDE;
        Rectangle document = inside ? documentBounds : documentBounds.rectangle(BoundsType.MIDDLE);
        if (this.verticalAnchor == VerticalAlignment.TOP) {
            if (inside) {
                y = MathHelper.clamp((document.getMaxY() / 100.0f) * this.y, document.getY(), document.getMaxY());
            } else {
                y = document.getY() + this.y;
            }
            top = y;
            bottom = top + this.height;
        } else if (this.verticalAnchor == VerticalAlignment.CENTER) {
            top = MathHelper.clamp(document.getCenterY() - (this.height / 2.0f), document.getY(), document.getMaxY());
            bottom = MathHelper.clamp(document.getCenterY() + (this.height / 2.0f), document.getY(), document.getMaxY());
        } else {
            if (inside) {
                maxY = MathHelper.clamp((document.getMaxY() / 100.0f) * (100.0f - this.y), document.getY(), document.getMaxY());
            } else {
                maxY = document.getMaxY() - this.y;
            }
            bottom = maxY;
            top = bottom - this.height;
        }
        if (this.horizontalAnchor == HorizontalAlignment.LEFT) {
            if (inside) {
                x = MathHelper.clamp((document.getMaxX() / 100.0f) * this.x, document.getX(), document.getMaxX());
            } else {
                x = document.getX() + this.x;
            }
            left = x;
            right = left + this.width;
        } else if (this.horizontalAnchor == HorizontalAlignment.CENTER) {
            left = MathHelper.clamp(document.getCenterX() - (this.width / 2.0f), document.getX(), document.getMaxX());
            right = MathHelper.clamp(document.getCenterX() + (this.width / 2.0f), document.getX(), document.getMaxX());
        } else {
            if (inside) {
                maxX = MathHelper.clamp((document.getMaxX() / 100.0f) * (100.0f - this.x), document.getX(), document.getMaxX());
            } else {
                maxX = document.getMaxX() - this.x;
            }
            right = maxX;
            left = right - this.width;
        }
        if (left < document.getLeft()) {
            left = document.getLeft();
        }
        if (right > document.getRight()) {
            right = document.getRight();
        }
        if (top < document.getTop()) {
            top = document.getTop();
        }
        if (bottom > document.getBottom()) {
            bottom = document.getBottom();
        }
        MutableRectangle rectangle = new DefaultRectangle();
        rectangle.setBounds((int) left, (int) top, (int) right, (int) bottom);
        return rectangle;
    }

    public void setPosition(Bounds documentBounds, Rectangle rectangle) {
        if (documentBounds.isInRectangle(rectangle)) {
            this.boundsPosition = BoundsPosition.INSIDE;
        } else {
            this.boundsPosition = BoundsPosition.OUTSIDE;
        }
        boolean inside = this.boundsPosition != BoundsPosition.OUTSIDE;
        Rectangle document = inside ? documentBounds : documentBounds.rectangle(BoundsType.MIDDLE);
        this.horizontalAnchor = horizontalAnchor(document, rectangle);
        this.verticalAnchor = verticalAnchor(document, rectangle);
        this.width = rectangle.getWidth();
        this.height = rectangle.getHeight();
        if (inside) {
            this.x = getHorizontalPercentage(this.horizontalAnchor, document, rectangle);
            this.y = getVerticalPercentage(this.verticalAnchor, document, rectangle);
        } else {
            this.x = this.horizontalAnchor == HorizontalAlignment.LEFT ? rectangle.getX() - document.getX() : document.getMaxX() - rectangle.getMaxX();
            this.y = this.verticalAnchor == VerticalAlignment.TOP ? rectangle.getY() - document.getY() : document.getMaxY() - rectangle.getMaxY();
        }
    }

    public void setPosition(float x, float y, HorizontalAlignment horizontalAnchor, VerticalAlignment verticalAnchor) {
        this.horizontalAnchor = horizontalAnchor;
        this.verticalAnchor = verticalAnchor;
        this.x = x;
        this.y = y;
    }

    public HorizontalAlignment horizontalAnchor(Rectangle document, Rectangle bounds) {
        if (document.getWidth() / 2.0f == bounds.getCenterX()) {
            return HorizontalAlignment.CENTER;
        }
        if (document.getWidth() / 2.0f > bounds.getCenterX()) {
            return HorizontalAlignment.LEFT;
        }
        return HorizontalAlignment.RIGHT;
    }

    public VerticalAlignment verticalAnchor(Rectangle document, Rectangle bounds) {
        if (document.getHeight() / 2.0f == bounds.getCenterY()) {
            return VerticalAlignment.CENTER;
        }
        if (document.getHeight() / 2.0f > bounds.getCenterY()) {
            return VerticalAlignment.TOP;
        }
        return VerticalAlignment.BOTTOM;
    }

    public float getHorizontalPercentage(HorizontalAlignment anchor, Rectangle document, Rectangle bounds) {
        float documentX = document.getX() + document.getWidth();
        float boundsX = anchor == HorizontalAlignment.LEFT ? bounds.getX() : bounds.getMaxX();
        float percentage = Math.min(100.0f, Math.max(0.0f, (100.0f / documentX) * boundsX));
        return anchor == HorizontalAlignment.RIGHT ? 100.0f - percentage : percentage;
    }

    public float getVerticalPercentage(VerticalAlignment anchor, Rectangle document, Rectangle bounds) {
        float documentY = document.getY() + document.getHeight();
        float boundsY = anchor == VerticalAlignment.TOP ? bounds.getY() : bounds.getMaxY();
        float percentage = Math.min(100.0f, Math.max(0.0f, (100.0f / documentY) * boundsY));
        return anchor == VerticalAlignment.BOTTOM ? 100.0f - percentage : percentage;
    }

    public List<RootChatTabConfig> getTabs() {
        return this.tabs;
    }

    public void setTabs(List<RootChatTabConfig> tabs) {
        this.tabs = tabs;
    }

    public int getFocusedTabIndex() {
        return this.focusedTab;
    }

    public RootChatTabConfig getFocusedTab() {
        return this.tabs.get(this.focusedTab);
    }

    public void setFocusedTab(int index) {
        this.focusedTab = index;
    }

    public boolean tabExists(int index) {
        boolean exists = false;
        Iterator<RootChatTabConfig> it = this.tabs.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            RootChatTabConfig tab = it.next();
            if (tab.index().get().intValue() == index) {
                exists = true;
                break;
            }
        }
        return exists;
    }

    public void checkForFocusedTab() {
        if (!tabExists(this.focusedTab)) {
            if (this.tabs.isEmpty()) {
                this.tabs.add(createDefaultTab());
                this.focusedTab = 0;
            } else {
                this.focusedTab = this.tabs.get(0).index().get().intValue();
            }
        }
    }

    public float getHeight() {
        return this.height;
    }

    private RootChatTabConfig createDefaultTab() {
        return new RootChatTabConfig(0, RootChatTabConfig.Type.SERVER, new GeneralChatTabConfig(""));
    }
}
