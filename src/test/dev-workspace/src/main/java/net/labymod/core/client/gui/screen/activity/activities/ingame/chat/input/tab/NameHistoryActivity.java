package net.labymod.core.client.gui.screen.activity.activities.ingame.chat.input.tab;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Textures;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.chatinput.ChatInputTabActivity;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.TextFieldWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.FlexibleContentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.VerticalListWidget;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.IconWidget;
import net.labymod.api.labynet.LabyNetController;
import net.labymod.api.labynet.models.NameHistory;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.I18n;
import net.labymod.api.util.TimeUnit;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.api.util.time.TimeUtil;
import net.labymod.core.flint.FlintUrls;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/ingame/chat/input/tab/NameHistoryActivity.class */
@Link("activity/chat/input/name-history.lss")
@AutoActivity
@Singleton
@Referenceable
public class NameHistoryActivity extends ChatInputTabActivity<FlexibleContentWidget> {
    private static final String HIDDEN = "－";
    private static final String PREFIX = "labymod.chatInput.tab.namehistory.";
    private final LabyNetController labyNetController;
    private String lastQuery;
    private Result<List<NameHistory>> lastResult = Result.empty();
    private final TextFieldWidget searchWidget = new TextFieldWidget();

    @Inject
    public NameHistoryActivity(LabyNetController labyNetController) {
        this.labyNetController = labyNetController;
        this.searchWidget.addId(FlintUrls.QUERY_SEARCH_PARAM);
        this.searchWidget.placeholder(Component.translatable("labymod.ui.textfield.search", new Component[0]));
        this.searchWidget.validator(searchValidator());
        this.searchWidget.submitHandler(searchSubmitHandler());
        this.searchWidget.maximalLength(19);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        Component text;
        String format;
        super.initialize(parent);
        this.searchWidget.setFocused(true);
        this.contentWidget = new FlexibleContentWidget();
        ((FlexibleContentWidget) this.contentWidget).addId("content");
        VerticalListWidget<Widget> header = new VerticalListWidget<>();
        header.addId("header");
        DivWidget titleWrapper = new DivWidget();
        titleWrapper.addId("title-wrapper");
        ComponentWidget title = ComponentWidget.i18n("labymod.chatInput.tab.namehistory.name");
        title.addId("title");
        titleWrapper.addChild(title);
        header.addChild(titleWrapper);
        FlexibleContentWidget searchWrapper = new FlexibleContentWidget();
        searchWrapper.addId("search-wrapper");
        searchWrapper.addFlexibleContent(this.searchWidget);
        ButtonWidget searchButton = ButtonWidget.i18n("labymod.ui.button.search");
        searchButton.addId("search-submit");
        searchButton.setPressable(() -> {
            searchSubmitHandler().accept(this.searchWidget.getText());
        });
        searchWrapper.addContent(searchButton);
        header.addChild(searchWrapper);
        ((FlexibleContentWidget) this.contentWidget).addContent(header);
        if (this.lastQuery == null) {
            ((Document) this.document).addChild(this.contentWidget);
            return;
        }
        ComponentWidget statusWidget = null;
        if (this.lastResult.isEmpty()) {
            statusWidget = ComponentWidget.i18n("labymod.misc.loading");
        } else if (this.lastResult.hasException()) {
            if (this.lastResult.exception().getCause() instanceof NullPointerException) {
                statusWidget = ComponentWidget.i18n("labymod.chatInput.tab.namehistory.search.noResult");
            } else {
                statusWidget = ComponentWidget.i18n("labymod.misc.errorWithArgs", this.lastResult.exception().getClass().getSimpleName());
            }
        }
        if (statusWidget != null) {
            statusWidget.addId("status");
            DivWidget statusWrapper = new DivWidget();
            statusWrapper.addId("status-wrapper");
            statusWrapper.addChild(statusWidget);
            ((FlexibleContentWidget) this.contentWidget).addContent(statusWrapper);
            ((Document) this.document).addChild(this.contentWidget);
            return;
        }
        VerticalListWidget<Widget> results = new VerticalListWidget<>();
        results.addId("results");
        List<NameHistory> value = this.lastResult.get();
        int i = 0;
        while (i < value.size()) {
            NameHistory nameHistory = value.get(i);
            HorizontalListWidget result = new HorizontalListWidget();
            result.addId("result");
            String name = nameHistory.getName();
            if (name.equals(HIDDEN)) {
                text = Component.translatable("labymod.chatInput.tab.namehistory.result.hidden", new Component[0]);
            } else {
                text = Component.text(name);
            }
            ComponentWidget nameWidget = ComponentWidget.component(text);
            nameWidget.addId("name");
            result.addEntry(nameWidget);
            String date = null;
            if (nameHistory.getChangedAtString() != null) {
                if (nameHistory.getChangedAt() == 0 || (format = I18n.getTranslation("labymod.chatInput.tab.namehistory.result.timeFormat", new Object[0])) == null) {
                    date = nameHistory.getChangedAtString();
                } else {
                    date = new SimpleDateFormat(format).format(Long.valueOf(nameHistory.getChangedAt()));
                    if (i != value.size() - 1) {
                        long nextTime = i == 0 ? TimeUtil.getCurrentTimeMillis() : value.get(i - 1).getChangedAt();
                        String duration = (String) TimeUnit.parseToList(nextTime - nameHistory.getChangedAt()).getFirst();
                        ComponentWidget durationWidget = ComponentWidget.text(duration);
                        durationWidget.addId("duration");
                        result.addEntry(durationWidget);
                    }
                }
            }
            if (i == 0) {
                nameWidget.addId("current-name");
            } else if (i == value.size() - 1) {
                nameWidget.addId("original-name");
                String originalName = I18n.translate("labymod.chatInput.tab.namehistory.result.original", new Object[0]);
                if (date == null) {
                    date = originalName;
                } else {
                    date = date + " (" + originalName + ")";
                }
            } else {
                nameWidget.addId("previous-name");
            }
            if (date == null) {
                date = I18n.translate("labymod.chatInput.tab.namehistory.result.unknown", new Object[0]);
            }
            ComponentWidget dateWidget = ComponentWidget.text(date);
            dateWidget.addId("date");
            result.addEntry(dateWidget);
            if (!nameHistory.isAccurate()) {
                IconWidget inaccurate = new IconWidget(Textures.SpriteCommon.CIRCLE_WARNING);
                inaccurate.addId("inaccurate");
                inaccurate.setHoverComponent(Component.translatable("labymod.chatInput.tab.namehistory.result.inaccurate", new Component[0]));
                result.addEntry(inaccurate);
            }
            results.addChild(result);
            i++;
        }
        ((FlexibleContentWidget) this.contentWidget).addFlexibleContent(new ScrollWidget((VerticalListWidget<?>) results));
        ((Document) this.document).addChild(this.contentWidget);
    }

    public void scheduleQuery(String query) {
        this.searchWidget.setText(query);
        this.searchWidget.setFocused(true);
        searchSubmitHandler().accept(query);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        this.searchWidget.setFocused(false);
        super.onCloseScreen();
    }

    private Predicate<String> searchValidator() {
        return name -> {
            return true;
        };
    }

    private Consumer<String> searchSubmitHandler() {
        return name -> {
            if (this.lastQuery != null && this.lastQuery.equals(name)) {
                return;
            }
            String search = name.trim();
            if (search.isEmpty()) {
                this.lastQuery = null;
                this.lastResult = Result.empty();
                this.searchWidget.setFocused(false);
                this.searchWidget.setText("");
                reload();
                return;
            }
            this.lastQuery = search;
            this.lastResult = Result.empty();
            this.searchWidget.setFocused(false);
            reload();
            this.labyNetController.loadNameHistory(search, result -> {
                if (this.lastQuery != null && !this.lastQuery.equals(search)) {
                    return;
                }
                this.lastResult = result;
                this.labyAPI.minecraft().executeOnRenderThread(this::reload);
            });
        };
    }
}
