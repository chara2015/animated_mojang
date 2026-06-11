package net.labymod.api.client.gui.screen.widget.widgets;

import java.awt.Color;
import java.util.Locale;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.SimpleWidget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.Bounds;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.util.bounds.ModifyReason;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/RatingWidget.class */
@AutoWidget
public class RatingWidget extends SimpleWidget {
    private static final ModifyReason RATING_RELATIVE_WIDTH = ModifyReason.of("ratingRelativeWidth");
    private final Icon fullRatingIcon;
    private final ResourceLocation flintresourceLocation;
    private LssProperty<Boolean> displayExactRating;
    private LssProperty<Boolean> displayEmptyRating;
    private LssProperty<Float> spaceBetweenRatings;
    private LssProperty<Integer> fillColor;
    private LssProperty<Integer> emptyColor;
    private LssProperty<Integer> hoverColor;
    private final int count;
    private final double rating;
    private final int flooredRating;
    private final Component component;
    private Icon decimalIcon;
    private Icon emptyDecimalIcon;

    public RatingWidget(double rating, int count) {
        String beautifiedRating;
        this.displayExactRating = new LssProperty<>(false);
        this.displayEmptyRating = new LssProperty<>(false);
        this.spaceBetweenRatings = new LssProperty<>(Float.valueOf(1.0f));
        this.fillColor = new LssProperty<>(Integer.valueOf(Color.ORANGE.getRGB()));
        this.emptyColor = new LssProperty<>(Integer.valueOf(Color.GRAY.getRGB()));
        this.hoverColor = new LssProperty<>(Integer.valueOf(Color.ORANGE.getRGB()));
        this.rating = rating;
        this.count = count;
        this.flooredRating = (int) Math.floor(rating);
        this.flintresourceLocation = Textures.SpriteFlint.TEXTURE;
        this.fullRatingIcon = Icon.sprite16(this.flintresourceLocation, 0, 0);
        if (rating % 1.0d == 0.0d) {
            beautifiedRating = String.valueOf((int) rating);
        } else {
            beautifiedRating = String.format(Locale.ROOT, "%.1f", Double.valueOf(rating));
        }
        if (count == -1) {
            this.component = Component.translatable("labymod.addons.store.rating.hover", Component.text(beautifiedRating));
            return;
        }
        if (count == 0) {
            this.component = Component.translatable("labymod.addons.store.rating.hoverNoRating", new Component[0]);
        } else if (count == 1) {
            this.component = Component.translatable("labymod.addons.store.rating.hoverOneRating", Component.text(beautifiedRating));
        } else {
            this.component = Component.translatable("labymod.addons.store.rating.hoverWithCount", Component.text(beautifiedRating), Component.text(Integer.valueOf(this.count)));
        }
    }

    public RatingWidget(double rating) {
        this(rating, -1);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        int width;
        super.initialize(parent);
        if (this.rating % 1.0d != 0.0d) {
            if (this.displayExactRating.get().booleanValue()) {
                width = (int) (16.0d * (this.rating - ((double) this.flooredRating)));
            } else {
                width = 8;
            }
        } else {
            width = 0;
        }
        if (width != 0) {
            this.decimalIcon = Icon.sprite16(this.flintresourceLocation, 1, 0);
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        super.postStyleSheetLoad();
        setHoverComponent(this.component.color(TextColor.color(hoverColor().get().intValue())));
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.StyledWidget, net.labymod.api.client.gui.element.Element
    public void render(ScreenContext context) {
        super.render(context);
        Bounds bounds = bounds();
        float x = bounds.getX();
        float y = bounds.getY();
        float ratingSize = bounds.getHeight();
        ScreenCanvas renderState = context.canvas();
        int fillColor = this.fillColor.get().intValue();
        int emptyColor = this.emptyColor.get().intValue();
        boolean displayEmptyRating = this.displayEmptyRating.get().booleanValue();
        float spaceBetweenRatings = this.spaceBetweenRatings.get().floatValue();
        for (int i = 0; i < 5; i++) {
            if (i < this.flooredRating) {
                renderState.submitIcon(this.fullRatingIcon, x, y, ratingSize, ratingSize, false, fillColor);
            } else if (i == this.flooredRating && this.decimalIcon != null) {
                renderState.submitIcon(this.decimalIcon, x, y, ratingSize, ratingSize, false, fillColor);
                if (displayEmptyRating) {
                    if (this.emptyDecimalIcon == null) {
                        this.emptyDecimalIcon = Icon.sprite16(this.flintresourceLocation, 2, 0);
                    }
                    renderState.submitIcon(this.emptyDecimalIcon, x, y, ratingSize, ratingSize, false, emptyColor);
                }
            } else if (displayEmptyRating) {
                renderState.submitIcon(this.fullRatingIcon, x, y, ratingSize, ratingSize, false, emptyColor);
            }
            x += ratingSize + spaceBetweenRatings;
        }
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void updateBounds() {
        Bounds bounds = bounds();
        bounds.setOuterWidth((bounds.getHeight() * 5.0f) + (this.spaceBetweenRatings.get().floatValue() * 4.0f), RATING_RELATIVE_WIDTH);
        super.updateBounds();
    }

    public LssProperty<Boolean> displayExactRating() {
        return this.displayExactRating;
    }

    public LssProperty<Boolean> displayEmptyRating() {
        return this.displayEmptyRating;
    }

    public LssProperty<Float> spaceBetweenRatings() {
        return this.spaceBetweenRatings;
    }

    public LssProperty<Integer> fillColor() {
        return this.fillColor;
    }

    public LssProperty<Integer> emptyColor() {
        return this.emptyColor;
    }

    public LssProperty<Integer> hoverColor() {
        return this.hoverColor;
    }
}
