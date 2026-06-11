package net.minecraft.client.gui.screens;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.logging.LogUtils;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.lang.runtime.SwitchBootstraps;
import java.net.URI;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.NarratorStatus;
import net.minecraft.client.gui.ComponentPath;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.narration.ScreenNarrationCollector;
import net.minecraft.client.gui.navigation.FocusNavigationEvent;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.ServerboundCustomClickActionPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.Music;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.lighting.DynamicGraphMinFixedPoint;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/Screen.class */
public abstract class Screen extends AbstractContainerEventHandler implements Renderable {
    protected static final float FADE_IN_TIME = 2000.0f;
    protected final Component title;
    private final List<GuiEventListener> children;
    private final List<NarratableEntry> narratables;
    protected final Minecraft minecraft;
    private boolean initialized;
    public int width;
    public int height;
    private final List<Renderable> renderables;
    protected final Font font;
    private static final long NARRATE_DELAY_MOUSE_MOVE = 750;
    private static final long NARRATE_DELAY_MOUSE_ACTION = 200;
    private static final long NARRATE_DELAY_KEYBOARD_ACTION = 200;
    private final ScreenNarrationCollector narrationState;
    private long narrationSuppressTime;
    private long nextNarrationTime;
    protected CycleButton<NarratorStatus> narratorButton;
    private NarratableEntry lastNarratable;
    protected final Executor screenExecutor;
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Component USAGE_NARRATION = Component.translatable("narrator.screen.usage");
    public static final Identifier MENU_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/menu_background.png");
    public static final Identifier HEADER_SEPARATOR = Identifier.withDefaultNamespace("textures/gui/header_separator.png");
    public static final Identifier FOOTER_SEPARATOR = Identifier.withDefaultNamespace("textures/gui/footer_separator.png");
    private static final Identifier INWORLD_MENU_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/inworld_menu_background.png");
    public static final Identifier INWORLD_HEADER_SEPARATOR = Identifier.withDefaultNamespace("textures/gui/inworld_header_separator.png");
    public static final Identifier INWORLD_FOOTER_SEPARATOR = Identifier.withDefaultNamespace("textures/gui/inworld_footer_separator.png");
    private static final long NARRATE_SUPPRESS_AFTER_INIT_TIME = TimeUnit.SECONDS.toMillis(2);
    private static final long NARRATE_DELAY_NARRATOR_ENABLED = NARRATE_SUPPRESS_AFTER_INIT_TIME;

    protected Screen(Component $$0) {
        this(Minecraft.getInstance(), Minecraft.getInstance().font, $$0);
    }

    protected Screen(Minecraft $$0, Font $$1, Component $$2) {
        this.children = Lists.newArrayList();
        this.narratables = Lists.newArrayList();
        this.renderables = Lists.newArrayList();
        this.narrationState = new ScreenNarrationCollector();
        this.narrationSuppressTime = Long.MIN_VALUE;
        this.nextNarrationTime = DynamicGraphMinFixedPoint.SOURCE;
        this.minecraft = $$0;
        this.font = $$1;
        this.title = $$2;
        this.screenExecutor = $$12 -> {
            $$0.execute(() -> {
                if ($$0.screen == this) {
                    $$12.run();
                }
            });
        };
    }

    public Component getTitle() {
        return this.title;
    }

    public Component getNarrationMessage() {
        return getTitle();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public final void renderWithTooltipAndSubtitles(GuiGraphics $$0, int $$1, int $$2, float $$3) throws MatchException {
        $$0.nextStratum();
        renderBackground($$0, $$1, $$2, $$3);
        $$0.nextStratum();
        render($$0, $$1, $$2, $$3);
        $$0.renderDeferredElements();
    }

    public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        for (Renderable $$4 : this.renderables) {
            $$4.render($$0, $$1, $$2, $$3);
        }
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler, net.minecraft.client.gui.components.events.GuiEventListener
    public boolean keyPressed(KeyEvent $$0) {
        FocusNavigationEvent focusNavigationEventCreateTabEvent;
        if ($$0.isEscape() && shouldCloseOnEsc()) {
            onClose();
            return true;
        }
        if (super.keyPressed($$0)) {
            return true;
        }
        switch ($$0.key()) {
            case InputConstants.KEY_TAB /* 258 */:
                focusNavigationEventCreateTabEvent = createTabEvent(!$$0.hasShiftDown());
                break;
            case InputConstants.KEY_BACKSPACE /* 259 */:
            case 260:
            case InputConstants.KEY_DELETE /* 261 */:
            default:
                focusNavigationEventCreateTabEvent = null;
                break;
            case InputConstants.KEY_RIGHT /* 262 */:
                focusNavigationEventCreateTabEvent = createArrowEvent(ScreenDirection.RIGHT);
                break;
            case InputConstants.KEY_LEFT /* 263 */:
                focusNavigationEventCreateTabEvent = createArrowEvent(ScreenDirection.LEFT);
                break;
            case InputConstants.KEY_DOWN /* 264 */:
                focusNavigationEventCreateTabEvent = createArrowEvent(ScreenDirection.DOWN);
                break;
            case InputConstants.KEY_UP /* 265 */:
                focusNavigationEventCreateTabEvent = createArrowEvent(ScreenDirection.UP);
                break;
        }
        FocusNavigationEvent $$1 = focusNavigationEventCreateTabEvent;
        if ($$1 != null) {
            ComponentPath $$2 = super.nextFocusPath($$1);
            if ($$2 == null && ($$1 instanceof FocusNavigationEvent.TabNavigation)) {
                clearFocus();
                $$2 = super.nextFocusPath($$1);
            }
            if ($$2 != null) {
                changeFocus($$2);
                return false;
            }
            return false;
        }
        return false;
    }

    private FocusNavigationEvent.TabNavigation createTabEvent(boolean $$0) {
        return new FocusNavigationEvent.TabNavigation($$0);
    }

    private FocusNavigationEvent.ArrowNavigation createArrowEvent(ScreenDirection $$0) {
        return new FocusNavigationEvent.ArrowNavigation($$0);
    }

    protected void setInitialFocus() {
        if (this.minecraft.getLastInputType().isKeyboard()) {
            FocusNavigationEvent.TabNavigation $$0 = new FocusNavigationEvent.TabNavigation(true);
            ComponentPath $$1 = super.nextFocusPath($$0);
            if ($$1 != null) {
                changeFocus($$1);
            }
        }
    }

    protected void setInitialFocus(GuiEventListener $$0) {
        ComponentPath $$1 = ComponentPath.path(this, $$0.nextFocusPath(new FocusNavigationEvent.InitialFocus()));
        if ($$1 != null) {
            changeFocus($$1);
        }
    }

    public void clearFocus() {
        ComponentPath $$0 = getCurrentFocusPath();
        if ($$0 != null) {
            $$0.applyFocus(false);
        }
    }

    @VisibleForTesting
    protected void changeFocus(ComponentPath $$0) {
        clearFocus();
        $$0.applyFocus(true);
    }

    public boolean shouldCloseOnEsc() {
        return true;
    }

    public void onClose() {
        this.minecraft.setScreen(null);
    }

    protected <T extends GuiEventListener & Renderable & NarratableEntry> T addRenderableWidget(T t) {
        this.renderables.add(t);
        return (T) addWidget(t);
    }

    protected <T extends Renderable> T addRenderableOnly(T $$0) {
        this.renderables.add($$0);
        return $$0;
    }

    protected <T extends GuiEventListener & NarratableEntry> T addWidget(T $$0) {
        this.children.add($$0);
        this.narratables.add($$0);
        return $$0;
    }

    protected void removeWidget(GuiEventListener $$0) {
        if ($$0 instanceof Renderable) {
            this.renderables.remove((Renderable) $$0);
        }
        if ($$0 instanceof NarratableEntry) {
            this.narratables.remove((NarratableEntry) $$0);
        }
        if (getFocused() == $$0) {
            clearFocus();
        }
        this.children.remove($$0);
    }

    protected void clearWidgets() {
        this.renderables.clear();
        this.children.clear();
        this.narratables.clear();
    }

    public static List<Component> getTooltipFromItem(Minecraft $$0, ItemStack $$1) {
        return $$1.getTooltipLines(Item.TooltipContext.of($$0.level), $$0.player, $$0.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL);
    }

    protected void insertText(String $$0, boolean $$1) {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    protected static void defaultHandleGameClickEvent(ClickEvent $$0, Minecraft $$1, Screen $$2) throws MatchException {
        LocalPlayer $$3 = (LocalPlayer) Objects.requireNonNull($$1.player, "Player not available");
        Objects.requireNonNull($$0);
        switch ((int) SwitchBootstraps.typeSwitch(MethodHandles.lookup(), "typeSwitch", MethodType.methodType(Integer.TYPE, Object.class, Integer.TYPE), ClickEvent.RunCommand.class, ClickEvent.ShowDialog.class, ClickEvent.Custom.class).dynamicInvoker().invoke($$0, 0) /* invoke-custom */) {
            case 0:
                try {
                    String $$4 = ((ClickEvent.RunCommand) $$0).command();
                    clickCommandAction($$3, $$4, $$2);
                    return;
                } catch (Throwable th) {
                    throw new MatchException(th.toString(), th);
                }
            case 1:
                ClickEvent.ShowDialog $$5 = (ClickEvent.ShowDialog) $$0;
                $$3.connection.showDialog($$5.dialog(), $$2);
                return;
            case 2:
                ClickEvent.Custom $$6 = (ClickEvent.Custom) $$0;
                $$3.connection.send(new ServerboundCustomClickActionPacket($$6.id(), $$6.payload()));
                if ($$1.screen != $$2) {
                    $$1.setScreen($$2);
                    return;
                }
                return;
            default:
                defaultHandleClickEvent($$0, $$1, $$2);
                return;
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00db A[ADDED_TO_REGION, ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected static void defaultHandleClickEvent(net.minecraft.network.chat.ClickEvent r5, net.minecraft.client.Minecraft r6, net.minecraft.client.gui.screens.Screen r7) throws java.lang.MatchException {
        /*
            Method dump skipped, instruction units count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: net.minecraft.client.gui.screens.Screen.defaultHandleClickEvent(net.minecraft.network.chat.ClickEvent, net.minecraft.client.Minecraft, net.minecraft.client.gui.screens.Screen):void");
    }

    protected static boolean clickUrlAction(Minecraft $$0, Screen $$1, URI $$2) {
        if (!$$0.options.chatLinks().get().booleanValue()) {
            return false;
        }
        if ($$0.options.chatLinksPrompt().get().booleanValue()) {
            $$0.setScreen(new ConfirmLinkScreen($$3 -> {
                if ($$3) {
                    Util.getPlatform().openUri($$2);
                }
                $$0.setScreen($$1);
            }, $$2.toString(), false));
            return true;
        }
        Util.getPlatform().openUri($$2);
        return true;
    }

    protected static void clickCommandAction(LocalPlayer $$0, String $$1, Screen $$2) {
        $$0.connection.sendUnattendedCommand(Commands.trimOptionalPrefix($$1), $$2);
    }

    public final void init(int $$0, int $$1) {
        this.width = $$0;
        this.height = $$1;
        if (!this.initialized) {
            init();
            setInitialFocus();
        } else {
            repositionElements();
        }
        this.initialized = true;
        triggerImmediateNarration(false);
        if (this.minecraft.getLastInputType().isKeyboard()) {
            setNarrationSuppressTime(DynamicGraphMinFixedPoint.SOURCE);
        } else {
            suppressNarration(NARRATE_SUPPRESS_AFTER_INIT_TIME);
        }
    }

    protected void rebuildWidgets() {
        clearWidgets();
        clearFocus();
        init();
        setInitialFocus();
    }

    protected void fadeWidgets(float $$0) {
        for (GuiEventListener $$1 : children()) {
            if ($$1 instanceof AbstractWidget) {
                AbstractWidget $$2 = (AbstractWidget) $$1;
                $$2.setAlpha($$0);
            }
        }
    }

    @Override // net.minecraft.client.gui.components.events.ContainerEventHandler
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    protected void init() {
    }

    public void tick() {
    }

    public void removed() {
    }

    public void added() {
    }

    public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
        if (isInGameUi()) {
            renderTransparentBackground($$0);
        } else {
            if (this.minecraft.level == null) {
                renderPanorama($$0, $$3);
            }
            renderBlurredBackground($$0);
            renderMenuBackground($$0);
        }
        this.minecraft.gui.renderDeferredSubtitles();
    }

    protected void renderBlurredBackground(GuiGraphics $$0) {
        float $$1 = this.minecraft.options.getMenuBackgroundBlurriness();
        if ($$1 >= 1.0f) {
            $$0.blurBeforeThisStratum();
        }
    }

    protected void renderPanorama(GuiGraphics $$0, float $$1) {
        this.minecraft.gameRenderer.getPanorama().render($$0, this.width, this.height, panoramaShouldSpin());
    }

    protected void renderMenuBackground(GuiGraphics $$0) {
        renderMenuBackground($$0, 0, 0, this.width, this.height);
    }

    protected void renderMenuBackground(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4) {
        renderMenuBackgroundTexture($$0, this.minecraft.level == null ? MENU_BACKGROUND : INWORLD_MENU_BACKGROUND, $$1, $$2, 0.0f, 0.0f, $$3, $$4);
    }

    public static void renderMenuBackgroundTexture(GuiGraphics $$0, Identifier $$1, int $$2, int $$3, float $$4, float $$5, int $$6, int $$7) {
        $$0.blit(RenderPipelines.GUI_TEXTURED, $$1, $$2, $$3, $$4, $$5, $$6, $$7, 32, 32);
    }

    public void renderTransparentBackground(GuiGraphics $$0) {
        $$0.fillGradient(0, 0, this.width, this.height, -1072689136, -804253680);
    }

    public boolean isPauseScreen() {
        return true;
    }

    public boolean isInGameUi() {
        return false;
    }

    protected boolean panoramaShouldSpin() {
        return true;
    }

    public boolean isAllowedInPortal() {
        return isPauseScreen();
    }

    protected void repositionElements() {
        rebuildWidgets();
    }

    public void resize(int $$0, int $$1) {
        this.width = $$0;
        this.height = $$1;
        repositionElements();
    }

    public void fillCrashDetails(CrashReport $$0) {
        CrashReportCategory $$1 = $$0.addCategory("Affected screen", 1);
        $$1.setDetail("Screen name", () -> {
            return getClass().getCanonicalName();
        });
    }

    protected boolean isValidCharacterForName(String $$0, int $$1, int $$2) {
        int $$3 = $$0.indexOf(58);
        int $$4 = $$0.indexOf(47);
        return $$1 == 58 ? ($$4 == -1 || $$2 <= $$4) && $$3 == -1 : $$1 == 47 ? $$2 > $$3 : $$1 == 95 || $$1 == 45 || ($$1 >= 97 && $$1 <= 122) || (($$1 >= 48 && $$1 <= 57) || $$1 == 46);
    }

    @Override // net.minecraft.client.gui.components.events.GuiEventListener
    public boolean isMouseOver(double $$0, double $$1) {
        return true;
    }

    public void onFilesDrop(List<Path> $$0) {
    }

    private void scheduleNarration(long $$0, boolean $$1) {
        this.nextNarrationTime = Util.getMillis() + $$0;
        if ($$1) {
            this.narrationSuppressTime = Long.MIN_VALUE;
        }
    }

    private void suppressNarration(long $$0) {
        setNarrationSuppressTime(Util.getMillis() + $$0);
    }

    private void setNarrationSuppressTime(long $$0) {
        this.narrationSuppressTime = $$0;
    }

    public void afterMouseMove() {
        scheduleNarration(NARRATE_DELAY_MOUSE_MOVE, false);
    }

    public void afterMouseAction() {
        scheduleNarration(200L, true);
    }

    public void afterKeyboardAction() {
        scheduleNarration(200L, true);
    }

    private boolean shouldRunNarration() {
        return SharedConstants.DEBUG_UI_NARRATION || this.minecraft.getNarrator().isActive();
    }

    public void handleDelayedNarration() {
        if (shouldRunNarration()) {
            long $$0 = Util.getMillis();
            if ($$0 > this.nextNarrationTime && $$0 > this.narrationSuppressTime) {
                runNarration(true);
                this.nextNarrationTime = DynamicGraphMinFixedPoint.SOURCE;
            }
        }
    }

    public void triggerImmediateNarration(boolean $$0) {
        if (shouldRunNarration()) {
            runNarration($$0);
        }
    }

    private void runNarration(boolean $$0) {
        this.narrationState.update(this::updateNarrationState);
        String $$1 = this.narrationState.collectNarrationText(!$$0);
        if (!$$1.isEmpty()) {
            this.minecraft.getNarrator().saySystemNow($$1);
        }
    }

    protected boolean shouldNarrateNavigation() {
        return true;
    }

    protected void updateNarrationState(NarrationElementOutput $$0) {
        $$0.add(NarratedElementType.TITLE, getNarrationMessage());
        if (shouldNarrateNavigation()) {
            $$0.add(NarratedElementType.USAGE, USAGE_NARRATION);
        }
        updateNarratedWidget($$0);
    }

    protected void updateNarratedWidget(NarrationElementOutput $$0) {
        List<? extends NarratableEntry> $$1 = this.narratables.stream().flatMap($$02 -> {
            return $$02.getNarratables().stream();
        }).filter((v0) -> {
            return v0.isActive();
        }).sorted(Comparator.comparingInt((v0) -> {
            return v0.getTabOrderGroup();
        })).toList();
        NarratableSearchResult $$2 = findNarratableWidget($$1, this.lastNarratable);
        if ($$2 != null) {
            if ($$2.priority.isTerminal()) {
                this.lastNarratable = $$2.entry;
            }
            if ($$1.size() > 1) {
                $$0.add(NarratedElementType.POSITION, Component.translatable("narrator.position.screen", Integer.valueOf($$2.index + 1), Integer.valueOf($$1.size())));
                if ($$2.priority == NarratableEntry.NarrationPriority.FOCUSED) {
                    $$0.add(NarratedElementType.USAGE, getUsageNarration());
                }
            }
            $$2.entry.updateNarration($$0.nest());
        }
    }

    protected Component getUsageNarration() {
        return Component.translatable("narration.component_list.usage");
    }

    public static NarratableSearchResult findNarratableWidget(List<? extends NarratableEntry> $$0, NarratableEntry $$1) {
        NarratableSearchResult $$2 = null;
        NarratableSearchResult $$3 = null;
        int $$5 = $$0.size();
        for (int $$4 = 0; $$4 < $$5; $$4++) {
            NarratableEntry $$6 = $$0.get($$4);
            NarratableEntry.NarrationPriority $$7 = $$6.narrationPriority();
            if ($$7.isTerminal()) {
                if ($$6 == $$1) {
                    $$3 = new NarratableSearchResult($$6, $$4, $$7);
                } else {
                    return new NarratableSearchResult($$6, $$4, $$7);
                }
            } else if ($$7.compareTo($$2 != null ? $$2.priority : NarratableEntry.NarrationPriority.NONE) > 0) {
                $$2 = new NarratableSearchResult($$6, $$4, $$7);
            }
        }
        return $$2 != null ? $$2 : $$3;
    }

    public void updateNarratorStatus(boolean $$0) {
        if ($$0) {
            scheduleNarration(NARRATE_DELAY_NARRATOR_ENABLED, false);
        }
        if (this.narratorButton != null) {
            this.narratorButton.setValue(this.minecraft.options.narrator().get());
        }
    }

    public Font getFont() {
        return this.font;
    }

    public boolean showsActiveEffects() {
        return false;
    }

    public boolean canInterruptWithAnotherScreen() {
        return shouldCloseOnEsc();
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/Screen$NarratableSearchResult.class */
    public static final class NarratableSearchResult extends Record {
        private final NarratableEntry entry;
        private final int index;
        private final NarratableEntry.NarrationPriority priority;

        public NarratableSearchResult(NarratableEntry $$0, int $$1, NarratableEntry.NarrationPriority $$2) {
            this.entry = $$0;
            this.index = $$1;
            this.priority = $$2;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, NarratableSearchResult.class), NarratableSearchResult.class, "entry;index;priority", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->entry:Lnet/minecraft/client/gui/narration/NarratableEntry;", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->index:I", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->priority:Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, NarratableSearchResult.class), NarratableSearchResult.class, "entry;index;priority", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->entry:Lnet/minecraft/client/gui/narration/NarratableEntry;", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->index:I", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->priority:Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object $$0) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, NarratableSearchResult.class, Object.class), NarratableSearchResult.class, "entry;index;priority", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->entry:Lnet/minecraft/client/gui/narration/NarratableEntry;", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->index:I", "FIELD:Lnet/minecraft/client/gui/screens/Screen$NarratableSearchResult;->priority:Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;").dynamicInvoker().invoke(this, $$0) /* invoke-custom */;
        }

        public NarratableEntry entry() {
            return this.entry;
        }

        public int index() {
            return this.index;
        }

        public NarratableEntry.NarrationPriority priority() {
            return this.priority;
        }
    }

    @Override // net.minecraft.client.gui.components.events.GuiEventListener, net.minecraft.client.gui.layouts.LayoutElement
    public ScreenRectangle getRectangle() {
        return new ScreenRectangle(0, 0, this.width, this.height);
    }

    public Music getBackgroundMusic() {
        return null;
    }
}
