package net.labymod.core.client.gui.screen.activity.activities.labymod.child.addons;

import com.google.gson.JsonArray;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.Activity;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.Links;
import net.labymod.api.client.gui.screen.widget.action.ListSession;
import net.labymod.api.client.gui.screen.widget.action.Pressable;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.ScrollWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.TilesGridWidget;
import net.labymod.api.localization.Internationalization;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.util.io.web.result.Result;
import net.labymod.core.client.gui.screen.widget.widgets.store.StoreItemWidget;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.FlintSortBy;
import net.labymod.core.flint.index.FlintIndex;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.flint.marketplace.FlintTag;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/MarketplaceActivity.class */
@Links({@Link("activity/flint/store.lss"), @Link("activity/flint/addon-item.lss")})
@AutoActivity
public class MarketplaceActivity extends Activity {
    private final FlintController flintController;
    private final ListSession<StoreItemWidget> session = new ListSession<>();
    private final Internationalization internationalization = Laby.references().internationalization();
    private final Map<Context, Object> contexts = new HashMap();
    private FlintSortBy sortBy = FlintSortBy.TRENDING;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/labymod/child/addons/MarketplaceActivity$Context.class */
    public enum Context {
        SEARCH,
        TRENDING,
        CATEGORY,
        SUBCATEGORY
    }

    public MarketplaceActivity(FlintController flintController) {
        this.flintController = flintController;
        this.contexts.put(Context.TRENDING, Context.TRENDING);
    }

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        Result<List<FlintModification>> indexResult = getModifications();
        String errorString = null;
        if (this.flintController.getTags() == null) {
            errorString = this.internationalization.translate("labymod.addons.store.marketplace.noResponse", new Object[0]);
        }
        if (indexResult.isEmpty()) {
            if (this.flintController.getFlintIndex().getIndex().isEmpty()) {
                errorString = this.internationalization.translate("labymod.addons.store.marketplace.noIndex", new Object[0]);
            } else if (this.contexts.containsKey(Context.SEARCH)) {
                errorString = this.internationalization.translate("labymod.addons.store.marketplace.noSearch", new Object[0]);
            }
        }
        if (indexResult.hasException()) {
            errorString = this.internationalization.translate("labymod.misc.errorWithArgs", indexResult.exception().getMessage());
        }
        if (errorString != null) {
            ComponentWidget error = ComponentWidget.text(errorString);
            error.addId("error");
            document().addChild(error);
            return;
        }
        if (!indexResult.isPresent()) {
            ComponentWidget error2 = ComponentWidget.i18n("labymod.addons.store.marketplace.noFilter");
            error2.addId("error");
            document().addChild(error2);
        }
        TilesGridWidget<StoreItemWidget> grid = new TilesGridWidget<>();
        List<FlintModification> modifications = indexResult.get();
        for (FlintModification modification : modifications) {
            if (!modification.hasMeta(AddonMeta.HIDDEN)) {
                StoreItemWidget widget = (StoreItemWidget) new StoreItemWidget(modification, openProfile(modification)).addId("addon-item");
                grid.addTile(widget);
            }
        }
        document().addChild(new ScrollWidget(grid, this.session).addId("scroll"));
    }

    public String getSearchQuery() {
        Object o = this.contexts.get(Context.SEARCH);
        if (o == null) {
            return "";
        }
        return (String) o;
    }

    public void search(String search) {
        setContext(Context.SEARCH, search);
    }

    public boolean isSearch(String search) {
        return this.contexts.containsKey(Context.SEARCH) && (search == null || this.contexts.containsValue(search));
    }

    public void category(FlintTag flintTag) {
        setContext(Context.CATEGORY, flintTag);
    }

    public boolean isCategory(FlintTag flintTag) {
        return this.contexts.containsKey(Context.CATEGORY) && this.contexts.containsValue(flintTag);
    }

    public FlintTag getCategory() {
        return (FlintTag) this.contexts.get(Context.CATEGORY);
    }

    public void trending() {
        setContext(Context.TRENDING, null);
    }

    public boolean isTrending() {
        return this.contexts.containsKey(Context.TRENDING) && !this.contexts.containsKey(Context.SEARCH);
    }

    public FlintSortBy getSortBy() {
        return this.sortBy;
    }

    public void setSortBy(FlintSortBy sortBy) {
        this.sortBy = sortBy;
    }

    private void setContext(Context context, Object value) {
        if (context != Context.SUBCATEGORY) {
            this.contexts.clear();
        }
        if (context != Context.SEARCH || (value != null && String.valueOf(value).length() != 0)) {
            this.contexts.put(context, value);
        }
        if (this.contexts.isEmpty()) {
            this.contexts.put(Context.TRENDING, Context.TRENDING);
        }
    }

    private Result<List<FlintModification>> getModifications() {
        List<FlintModification> modifications;
        FlintIndex flintIndex = this.flintController.getFlintIndex();
        Result<JsonArray> index = flintIndex.getIndex();
        if (index == null) {
            return Result.empty();
        }
        if (index.hasException()) {
            return Result.ofException(index.exception());
        }
        if (index.isEmpty()) {
            return Result.empty();
        }
        FlintIndex.IndexFilter filter = this.flintController.getFlintIndex().filter();
        if (this.contexts.containsKey(Context.SEARCH)) {
            modifications = filter.search(this.sortBy, String.valueOf(this.contexts.get(Context.SEARCH)));
        } else {
            modifications = filter.sortBy(this.sortBy);
        }
        if (modifications == null || modifications.isEmpty()) {
            return Result.empty();
        }
        return Result.of(modifications);
    }

    private Pressable openProfile(FlintModification modification) {
        FlintModification mod;
        if ((modification instanceof FlintIndex.FlintIndexModification) && (mod = this.flintController.loadModification(modification.getNamespace(), null)) != null) {
            modification = mod;
        }
        FlintModification finalModification = modification;
        return () -> {
            displayScreen(new AddonProfileActivity(this, this.flintController, finalModification));
        };
    }

    private String getContextTitle() {
        String title;
        Object searchValue = this.contexts.get(Context.SEARCH);
        String search = searchValue == null ? null : String.valueOf(searchValue);
        if (search != null) {
            title = "Search ";
        } else {
            title = "";
        }
        for (Map.Entry<Context, Object> entry : this.contexts.entrySet()) {
            Object value = entry.getValue();
            if (entry.getKey() == Context.CATEGORY) {
                title = title + ((FlintTag) value).getName() + "  ";
            } else if (entry.getKey() == Context.TRENDING) {
                title = title + "Trending Addons ";
            }
        }
        if (search != null) {
            title = title + "for: " + search;
        }
        return title;
    }
}
