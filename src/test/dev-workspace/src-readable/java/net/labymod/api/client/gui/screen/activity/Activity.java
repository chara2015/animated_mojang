package net.labymod.api.client.gui.screen.activity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.labymod.api.Laby;
import net.labymod.api.client.gui.lss.StyleSheetLoader;
import net.labymod.api.client.gui.lss.meta.LinkMetaLoader;
import net.labymod.api.client.gui.lss.style.StyleSheet;
import net.labymod.api.client.gui.lss.variable.LssVariable;
import net.labymod.api.client.gui.lss.variable.LssVariableHolder;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.LabyScreen;
import net.labymod.api.client.gui.screen.LabyScreenAccessor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.ParentScreen;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.ScreenInstance;
import net.labymod.api.client.gui.screen.activity.activities.OldOverlayWidgetActivity;
import net.labymod.api.client.gui.screen.activity.activities.WorkInProgressActivity;
import net.labymod.api.client.gui.screen.activity.types.AbstractLayerActivity;
import net.labymod.api.client.gui.screen.key.InputType;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.theme.Theme;
import net.labymod.api.client.gui.screen.theme.ThemeFile;
import net.labymod.api.client.gui.screen.theme.controller.ActivityAnimationController;
import net.labymod.api.client.gui.screen.util.StatefulRenderer;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.attributes.bounds.BoundsType;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.DocumentIdentifier;
import net.labymod.api.client.gui.screen.widget.attributes.rules.media.MediaRule;
import net.labymod.api.client.gui.screen.widget.overlay.ScreenOverlay;
import net.labymod.api.client.gui.screen.widget.overlay.WidgetReference;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.renderer.ScreenRendererWidget;
import net.labymod.api.client.gui.window.Window;
import net.labymod.api.client.resources.ResourcesReloadWatcher;
import net.labymod.api.event.client.gui.screen.ActivityInitializeEvent;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.event.method.ListenerRegistry;
import net.labymod.api.util.ThreadSafe;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/activity/Activity.class */
@Link(value = "style.lss", priority = -127)
public abstract class Activity extends ElementActivity<Document> implements LssVariableHolder {
    private final ResourcesReloadWatcher reloadWatcher;
    private final LinkMetaLoader metaLoader;
    private final StyleSheetLoader styleSheetLoader;
    private final Map<String, LssVariable> lssVariables;
    private final List<StyleSheet> stylesheets;
    private final List<Runnable> initializeRunnables;
    private final List<Runnable> closeRunnables;
    private final ListenerRegistry listenerRegistry;
    private String boundBranch;
    private Theme theme;
    protected boolean open;
    protected ActivityAnimationController.ActivityTransformer transformer;

    public Activity() {
        this(true);
    }

    public Activity(boolean handleStyleSheet) {
        this.reloadWatcher = Laby.references().resourcesReloadWatcher();
        this.lssVariables = new HashMap();
        this.stylesheets = new ArrayList();
        this.initializeRunnables = new ArrayList();
        this.closeRunnables = new ArrayList();
        this.listenerRegistry = Laby.references().subscribeMethodResolver().resolve(this);
        this.boundBranch = null;
        this.metaLoader = Laby.references().linkMetaLoader();
        this.styleSheetLoader = Laby.references().styleSheetLoader();
        ((Document) this.document).handleStyleSheet = handleStyleSheet;
        internalLoadMarkup();
        StatefulRenderer.registerActivityAnalyzer(getClass());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity
    public Document createDocument() {
        return new Document(this);
    }

    public void loadMeta() {
        this.theme = this.labyAPI.themeService().currentTheme();
        this.stylesheets.clear();
        this.metaLoader.injectMeta(this);
    }

    public void reload() {
        ThreadSafe.ensureRenderThread();
        super.load(this.parent);
    }

    public void preload() {
        Window window = this.labyAPI.minecraft().minecraftWindow();
        resize(window.getScaledWidth(), window.getScaledHeight());
        load(new ScreenRendererWidget());
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        if (this.boundBranch != null && !Laby.labyAPI().getBranchName().equals(this.boundBranch)) {
            redirectScreen(new WorkInProgressActivity(this.boundBranch));
            return;
        }
        loadMeta();
        ((Document) this.document).reset();
        updateMediaIdentifiers();
        super.initialize(parent);
        for (Runnable initializeRunnable : this.initializeRunnables) {
            if (initializeRunnable != null) {
                initializeRunnable.run();
            }
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen
    protected void postInitialize() {
        super.postInitialize();
        Laby.fireEvent(new ActivityInitializeEvent(this));
        for (StyleSheet stylesheet : this.stylesheets) {
            ((Document) this.document).applyStyleSheet(stylesheet);
        }
        postStyleSheetLoad();
    }

    protected void postStyleSheetLoad() {
        ((Document) this.document).postStyleSheetLoad();
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void render(ScreenContext context) {
        if (this.transformer != null) {
            this.transformer.render(context.getTickDelta());
        }
        Laby.references().documentHandler().collectActivity(this);
        Laby.references().screenWindowManagement().preRenderActivity(context, this);
        context.pushStack();
        if (!(this instanceof AbstractLayerActivity)) {
            transformActivity(context);
        }
        super.render(context);
        context.popStack();
    }

    protected void transformActivity(ScreenContext environment) {
        if (this.transformer == null || !this.transformer.isActive()) {
            return;
        }
        this.transformer.controller().transform(environment.stack(), environment.mouse(), environment.getTickDelta(), this, this.transformer);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public boolean renderBackground(ScreenContext context) {
        return true;
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void renderOverlay(ScreenContext context) {
        super.renderOverlay(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void renderHoverComponent(ScreenContext context) {
        super.renderHoverComponent(context);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.ScreenInstance
    public void resize(int width, int height) {
        super.resize(width, height);
        updateMediaIdentifiers();
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void onOpenScreen() {
        Laby.references().activityController().addOpenActivity(this);
        this.open = true;
        super.onOpenScreen();
        Laby.fireEvent(new ActivityOpenEvent(this));
        if (this.transformer != null) {
            this.transformer.initialize();
        }
    }

    @Override // net.labymod.api.client.gui.screen.ScreenInstance
    public void onCloseScreen() {
        super.onCloseScreen();
        Laby.references().activityController().removeOpenActivity(this);
        ((Document) this.document).dispose();
        for (Runnable closeAction : this.closeRunnables) {
            closeAction.run();
        }
        this.closeRunnables.clear();
        this.open = false;
        if (this.transformer != null) {
            this.transformer.dispose();
            this.transformer = null;
        }
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.screen.LabyScreen, net.labymod.api.client.gui.Interactable
    public void tick() {
        super.tick();
    }

    protected Optional<Widget> getFirstWidget(Predicate<Widget> predicate) {
        for (Widget widget : ((Document) this.document).getChildren()) {
            if (predicate.test(widget)) {
                return Optional.of(widget);
            }
        }
        return Optional.empty();
    }

    protected <T extends Widget> Optional<T> getFirstWidget(Class<T> cls) {
        Objects.requireNonNull(cls);
        Optional<Widget> firstWidget = getFirstWidget((v1) -> {
            return r1.isInstance(v1);
        });
        Objects.requireNonNull(cls);
        return (Optional<T>) firstWidget.map((v1) -> {
            return r1.cast(v1);
        });
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.KeyboardUser
    public boolean keyPressed(Key key, InputType type) {
        boolean result = super.keyPressed(key, type);
        if (!result && key == Key.TAB) {
            Widget focused = getFocused((Widget) this.document);
            ((Document) this.document).unfocus();
            focusNext(focused, (Widget) this.document, false);
        }
        return result;
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean displayPreviousScreen() {
        if ((this.previousScreen == null || this.previousScreen == this) && (this.parent instanceof ScreenRendererWidget)) {
            ParentScreen outerScreen = (ParentScreen) this.parent.getRoot();
            if (outerScreen instanceof Activity) {
                return ((Activity) outerScreen).displayPreviousScreen();
            }
            return true;
        }
        if ((this.previousScreen instanceof LabyScreenAccessor) && ((LabyScreenAccessor) this.previousScreen).screen() == this) {
            this.labyAPI.minecraft().minecraftWindow().displayScreenRaw(null);
            return true;
        }
        return super.displayPreviousScreen();
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.ScreenUser
    public void doScreenAction(String action, Map<String, Object> parameters) {
        if (action.equals("getActivityTree") && parameters.containsKey("consumer")) {
            Consumer<Activity> consumer = (Consumer) parameters.get("consumer");
            consumer.accept(this);
        }
        ((Document) this.document).doScreenAction(action, parameters);
    }

    public void addInitializeRunnable(Runnable initializeRunnable) {
        Objects.requireNonNull(initializeRunnable, "initializeRunnable must not be null");
        this.initializeRunnables.add(initializeRunnable);
    }

    public void addCloseRunnable(Runnable closeRunnable) {
        Objects.requireNonNull(closeRunnable, "closeRunnable must not be null");
        this.closeRunnables.add(closeRunnable);
    }

    public void setTransformer(ActivityAnimationController.ActivityTransformer transformer) {
        this.transformer = transformer;
    }

    public void addStyle(String path) {
        String namespace = this.labyAPI.getNamespace(this);
        addStyle(namespace, path);
    }

    public void addStyle(String namespace, String path) {
        ThemeFile file = this.labyAPI.themeService().currentTheme().file(namespace, "lss/" + path).normalize();
        StyleSheet style = this.styleSheetLoader.load(file);
        if (style != null) {
            addStyle(style);
        }
    }

    public void addStyle(StyleSheet style) {
        if (style != null && !this.stylesheets.contains(style)) {
            this.stylesheets.add(style);
        }
    }

    public void reloadMarkup() {
        this.stylesheets.clear();
        if (((Document) this.document).handleStyleSheet) {
            loadMeta();
        }
    }

    public void reloadStyles() {
        List<StyleSheet> styleSheets = new ArrayList<>(this.stylesheets);
        this.stylesheets.clear();
        Theme theme = this.labyAPI.themeService().currentTheme();
        for (StyleSheet styleSheet : styleSheets) {
            ThemeFile file = styleSheet.file().forTheme(theme).normalize();
            StyleSheet reloadedStyleSheet = this.styleSheetLoader.load(file);
            if (reloadedStyleSheet != null) {
                addStyle(reloadedStyleSheet);
            }
        }
    }

    private Widget getFocused(Widget widget) {
        if (widget.isFocused() && widget.hasTabFocus()) {
            return widget;
        }
        for (Widget child : widget.getChildren()) {
            Widget childFocused = getFocused(child);
            if (childFocused != null) {
                return childFocused;
            }
        }
        return null;
    }

    private Boolean focusNext(Widget focused, Widget widget, Boolean canFocus) {
        if ((focused == null && widget.hasTabFocus()) || (canFocus.booleanValue() && widget.hasTabFocus())) {
            widget.setFocused(true);
            return null;
        }
        if (widget.hasTabFocus() && widget == focused) {
            canFocus = true;
        }
        for (Widget child : widget.getChildren()) {
            canFocus = focusNext(focused, child, canFocus);
            if (canFocus == null) {
                break;
            }
        }
        return canFocus;
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseReleased(MutableMouse mouse, MouseButton mouseButton) {
        return super.mouseReleased(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        ((Document) this.document).unfocus();
        return super.mouseClicked(mouse, mouseButton);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseScrolled(MutableMouse mouse, double scrollDelta) {
        return super.mouseScrolled(mouse, scrollDelta);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.MouseUser
    public boolean mouseDragged(MutableMouse mouse, MouseButton button, double deltaX, double deltaY) {
        return super.mouseDragged(mouse, button, deltaX, deltaY);
    }

    public List<StyleSheet> getStylesheets() {
        return this.stylesheets;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void redirectScreen(ScreenInstance screen) {
        if (screen instanceof Activity) {
            ((Activity) screen).previousScreen = this.previousScreen;
        }
        displayScreen(screen);
    }

    public void overlayClosed(OldOverlayWidgetActivity overlay) {
        overlayUpdated(null);
    }

    public Collection<OldOverlayWidgetActivity> getOverlays() {
        return (Collection) this.labyAPI.screenOverlayHandler().overlays().stream().filter(new Predicate<ScreenOverlay>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.3
            @Override // java.util.function.Predicate
            public boolean test(ScreenOverlay overlay) {
                return overlay instanceof OldOverlayWidgetActivity;
            }
        }).map(new Function<ScreenOverlay, OldOverlayWidgetActivity>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.2
            @Override // java.util.function.Function
            public OldOverlayWidgetActivity apply(ScreenOverlay overlay) {
                return (OldOverlayWidgetActivity) overlay;
            }
        }).filter(new Predicate<OldOverlayWidgetActivity>() { // from class: net.labymod.api.client.gui.screen.activity.Activity.1
            @Override // java.util.function.Predicate
            public boolean test(OldOverlayWidgetActivity overlay) {
                return overlay.getOrigin() == Activity.this;
            }
        }).collect(Collectors.toList());
    }

    public boolean hasOverlay(final OldOverlayWidgetActivity overlay) {
        return this.labyAPI.screenOverlayHandler().overlays().stream().filter(new Predicate<ScreenOverlay>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.5
            @Override // java.util.function.Predicate
            public boolean test(ScreenOverlay o) {
                return !o.isClosing() && o.isActive();
            }
        }).anyMatch(new Predicate<ScreenOverlay>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.4
            @Override // java.util.function.Predicate
            public boolean test(ScreenOverlay o) {
                return o == overlay;
            }
        });
    }

    public boolean hasOverlay(final Class<? extends OldOverlayWidgetActivity> overlayClass) {
        return this.labyAPI.screenOverlayHandler().overlays().stream().filter(new Predicate<ScreenOverlay>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.7
            @Override // java.util.function.Predicate
            public boolean test(ScreenOverlay o) {
                return !o.isClosing() && o.isActive();
            }
        }).anyMatch(new Predicate<ScreenOverlay>(this) { // from class: net.labymod.api.client.gui.screen.activity.Activity.6
            @Override // java.util.function.Predicate
            public boolean test(ScreenOverlay overlay) {
                return overlay.getClass() == overlayClass;
            }
        });
    }

    public void overlayUpdated(OldOverlayWidgetActivity overlay) {
    }

    private void internalLoadMarkup() {
        this.reloadWatcher.addInitializeListener(new Runnable() { // from class: net.labymod.api.client.gui.screen.activity.Activity.8
            @Override // java.lang.Runnable
            public void run() {
                if (((Document) Activity.super.document()).handleStyleSheet) {
                    Activity.this.loadMeta();
                }
            }
        }, true);
    }

    public ListenerRegistry listenerRegistry() {
        return this.listenerRegistry;
    }

    private void updateMediaIdentifiers() {
        for (StyleSheet stylesheet : ((Document) this.document).getStyleSheets()) {
            for (MediaRule mediaRule : stylesheet.getMediaRules()) {
                if (mediaRule.getIdentifier() instanceof DocumentIdentifier) {
                    mediaRule.getIdentifier().updateRectangle(((Document) this.document).bounds().rectangle(BoundsType.MIDDLE));
                }
            }
        }
    }

    public WidgetReference displayInOverlay(List<StyleSheet> styles, Widget widget) {
        return this.labyAPI.screenOverlayHandler().displayInOverlay(this, styles, widget);
    }

    protected Theme theme() {
        return this.theme;
    }

    protected <T extends LabyScreen> T generic() {
        return this;
    }

    protected void bindToBranch(String branchName) {
        this.boundBranch = branchName;
    }

    public boolean listenForEvents() {
        return true;
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public LssVariableHolder getParentVariableHolder() {
        if (this.parent instanceof LssVariableHolder) {
            return (LssVariableHolder) this.parent;
        }
        return null;
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public Map<String, LssVariable> getLssVariables() {
        return this.lssVariables;
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void updateLssVariable(LssVariable variable) {
        ((Document) this.document).updateLssVariable(variable);
    }

    @Override // net.labymod.api.client.gui.lss.variable.LssVariableHolder
    public void forceUpdateLssVariable(LssVariable variable) {
        ((Document) this.document).forceUpdateLssVariable(variable);
    }

    @Override // net.labymod.api.client.gui.screen.activity.ElementActivity
    public boolean shouldDocumentHandleKey(Key key, InputType type) {
        if (key != Key.ESCAPE) {
            return true;
        }
        return shouldHandleEscape();
    }

    public boolean shouldHandleEscape() {
        for (Widget child : ((Document) this.document).getChildren()) {
            if (child.shouldHandleEscape()) {
                return true;
            }
        }
        return false;
    }
}
