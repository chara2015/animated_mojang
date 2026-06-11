package net.labymod.core.client.gui.screen.widget.widgets.store.profile;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.util.concurrent.task.Task;
import net.labymod.api.util.markdown.MarkdownDocument;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.flint.marketplace.FlintModification;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/profile/ProfileInfoWidget.class */
@AutoWidget
@Link("activity/flint/profile-info.lss")
public class ProfileInfoWidget extends VerticalListWidget<Widget> {
    private final FlintModification modification;
    private long loadingStart;
    private long lastLoadingStart;
    private final Map<State, ButtonWidget> buttons = new HashMap();
    private State state = State.DESCRIPTION;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/widget/widgets/store/profile/ProfileInfoWidget$State.class */
    private enum State {
        DESCRIPTION,
        CHANGELOG,
        REVIEWS
    }

    public ProfileInfoWidget(FlintModification modification) {
        this.modification = modification;
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        this.buttons.clear();
        HorizontalListWidget buttons = new HorizontalListWidget();
        buttons.addId("info-button-container");
        buttons.addEntry(createInfoButton(State.DESCRIPTION, "description"));
        buttons.addEntry(createInfoButton(State.CHANGELOG, "changelog"));
        buttons.addEntry(createInfoButton(State.REVIEWS, "reviews"));
        addChild(buttons);
        Widget infoWidget = null;
        switch (this.state) {
            case DESCRIPTION:
                DescriptionWidget descriptionWidget = descriptionWidget();
                if (descriptionWidget != null) {
                    infoWidget = descriptionWidget;
                } else if (this.loadingStart == 0) {
                    this.loadingStart = TimeUtil.getMillis() + 1000;
                }
                break;
            case CHANGELOG:
                VerticalListWidget<ChangelogWidget> changelogWidget = changelogWidget();
                if (changelogWidget != null && !changelogWidget.getChildren().isEmpty()) {
                    infoWidget = changelogWidget;
                } else if (this.loadingStart == 0) {
                    this.loadingStart = TimeUtil.getMillis() + 1000;
                }
                break;
            case REVIEWS:
                VerticalListWidget<ReviewWidget> reviewsWidget = reviewWidget();
                if (reviewsWidget != null) {
                    infoWidget = reviewsWidget.getChildren().isEmpty() ? ComponentWidget.i18n(getTranslationKey(this.state, "noReviews")) : reviewsWidget;
                } else if (this.loadingStart == 0) {
                    this.loadingStart = TimeUtil.getMillis() + 1000;
                }
                break;
        }
        if (infoWidget == null) {
            infoWidget = loadingInfoWidget(ComponentWidget.i18n(getTranslationKey(this.state, "loading")));
            if (infoWidget == null) {
                return;
            }
        }
        if (infoWidget instanceof ComponentWidget) {
            ComponentWidget componentWidget = (ComponentWidget) infoWidget;
            if (componentWidget.component() instanceof TranslatableComponent) {
                infoWidget.addId("info-information");
            }
        }
        infoWidget.addId("info-content");
        addChild(infoWidget);
    }

    private Widget loadingInfoWidget(Widget widget) {
        if (this.loadingStart < TimeUtil.getMillis()) {
            return widget;
        }
        if (this.lastLoadingStart == this.loadingStart) {
            return null;
        }
        this.lastLoadingStart = this.loadingStart;
        Task.builder(() -> {
            if (this.loadingStart == 0) {
                return;
            }
            this.loadingStart = TimeUtil.getMillis();
            this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
        }).delay(500L, TimeUnit.MILLISECONDS).build().execute();
        return null;
    }

    private DescriptionWidget descriptionWidget() {
        MarkdownDocument description = this.modification.getOrLoadDescription(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
        });
        if (description == null) {
            return null;
        }
        return new DescriptionWidget(this.modification, description);
    }

    private VerticalListWidget<ReviewWidget> reviewWidget() {
        Optional<List<FlintModification.Review>> reviews = this.modification.getOrLoadReviews(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
        });
        if (!reviews.isPresent()) {
            return null;
        }
        VerticalListWidget<ReviewWidget> reviewsWidget = new VerticalListWidget<>();
        for (FlintModification.Review review : reviews.get()) {
            reviewsWidget.addChild(new ReviewWidget(review));
        }
        return reviewsWidget;
    }

    private VerticalListWidget<ChangelogWidget> changelogWidget() {
        Optional<List<FlintModification.Changelog>> changelogs = this.modification.getOrLoadChangelog(result -> {
            if (result.isPresent()) {
                this.loadingStart = 0L;
                this.labyAPI.minecraft().executeOnRenderThread(this::reInitialize);
            }
        });
        if (!changelogs.isPresent()) {
            return null;
        }
        VerticalListWidget<ChangelogWidget> changelogWidget = new VerticalListWidget<>();
        for (FlintModification.Changelog changelog : changelogs.get()) {
            changelogWidget.addChild(new ChangelogWidget(this.modification, changelog));
        }
        return changelogWidget;
    }

    private String getTranslationKey(State state, String key) {
        return "labymod.addons.store.profile.info." + state.name().toLowerCase(Locale.ROOT) + "." + key;
    }

    private ButtonWidget createInfoButton(State state, String name) {
        ButtonWidget button = ButtonWidget.i18n(getTranslationKey(state, "name"), () -> {
            if (this.state == state) {
                return;
            }
            this.state = state;
            this.loadingStart = 0L;
            reInitialize();
            for (Map.Entry<State, ButtonWidget> entry : this.buttons.entrySet()) {
                entry.getValue().setEnabled(entry.getKey() != state);
                entry.getValue().setActive(entry.getKey() == state);
            }
        });
        button.addId("info-button");
        button.setEnabled(this.state != state);
        button.setActive(this.state == state);
        this.buttons.put(state, button);
        return button;
    }
}
