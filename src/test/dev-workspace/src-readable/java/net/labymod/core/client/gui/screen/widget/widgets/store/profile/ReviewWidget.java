package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import java.text.SimpleDateFormat;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.RatingWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.core.flint.marketplace.FlintModification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/profile/ReviewWidget.class */
@AutoWidget
public class ReviewWidget extends VerticalListWidget<Widget> {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
    private final FlintModification.Review review;

    public ReviewWidget(FlintModification.Review review) {
        this.review = review;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        HorizontalListWidget headerWidget = new HorizontalListWidget();
        headerWidget.addId("review-header");
        IconWidget icon = new IconWidget(this.review.user().icon());
        icon.addId("review-user-head");
        headerWidget.addEntry(icon);
        ComponentWidget name = ComponentWidget.text(this.review.user().getUserName());
        name.addId("review-user-name");
        headerWidget.addEntry(name);
        RatingWidget rating = new RatingWidget(this.review.getRating());
        rating.addId("review-rating");
        headerWidget.addEntry(rating);
        addChild(headerWidget);
        String commentText = this.review.getComment();
        if (commentText.length() != 0) {
            ComponentWidget comment = ComponentWidget.text(commentText);
            comment.addId("review-comment");
            addChild(comment);
        }
        String format = DATE_FORMAT.format(Long.valueOf(this.review.getAddedAt()));
        ComponentWidget date = ComponentWidget.text(format);
        date.addId("review-date");
        addChild(date);
    }
}
