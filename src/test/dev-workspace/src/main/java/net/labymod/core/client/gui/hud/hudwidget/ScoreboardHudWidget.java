package net.labymod.core.client.gui.hud.hudwidget;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.runtime.ObjectMethods;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.numbers.NumberFormat;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.HudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.color.ColorPickerWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.dropdown.DropdownWidget;
import net.labymod.api.client.scoreboard.DisplaySlot;
import net.labymod.api.client.scoreboard.ObjectiveRenderType;
import net.labymod.api.client.scoreboard.Scoreboard;
import net.labymod.api.client.scoreboard.ScoreboardObjective;
import net.labymod.api.client.scoreboard.ScoreboardScore;
import net.labymod.api.client.scoreboard.ScoreboardTeam;
import net.labymod.api.client.util.parity.VanillaParityUtil;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.render.overlay.IngameOverlayElementRenderEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.Color;
import net.labymod.api.util.bounds.DefaultRectangle;
import net.labymod.api.util.bounds.MutableRectangle;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget.class */
@SpriteSlot(x = 2, y = 1)
public class ScoreboardHudWidget extends SimpleHudWidget<ScoreboardHudWidgetConfig> {
    private static final boolean SORTED_SCORES = MinecraftVersions.V23w46a.orNewer();
    private static final Comparator<ScoreboardScore> SCORE_DISPLAY_ORDER = Comparator.comparing((v0) -> {
        return v0.getValue();
    }).reversed().thenComparing((v0) -> {
        return v0.getName();
    }, String.CASE_INSENSITIVE_ORDER);
    private static final float DEFAULT_FONT_HEIGHT = 9.0f;
    private static final int MAX_SCORES = 15;
    private static final float BODY_PADDING = 2.0f;
    private final Scoreboard dummyScoreboard;
    private final ScoreboardObjective dummyObjective;
    private final MutableRectangle bodyArea;

    public ScoreboardHudWidget() {
        super("scoreboard", ScoreboardHudWidgetConfig.class);
        this.dummyScoreboard = new DummyScoreboard();
        this.dummyObjective = new DummyObjective();
        this.bodyArea = new DefaultRectangle();
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.SCOREBOARD_LEFT, NamedHudWidgetDropzones.SCOREBOARD_RIGHT);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void initializePreConfigured(ScoreboardHudWidgetConfig config) {
        super.initializePreConfigured(config);
        config.setEnabled(true);
        config.setDropzoneId(NamedHudWidgetDropzones.SCOREBOARD_RIGHT.getId());
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        Scoreboard scoreboard = this.labyAPI.minecraft().getScoreboard();
        ScoreboardObjective objective = getObjective(scoreboard);
        if (scoreboard == null || objective == null || isEditorContext) {
            scoreboard = this.dummyScoreboard;
            objective = this.dummyObjective;
        }
        NumberFormat numberFormat = objective.getNumberFormatOrDefault(NumberFormat.noStyle());
        List<DisplayEntry> entries = getVisibleScores(scoreboard, objective, numberFormat);
        boolean showScores = showScores(entries);
        Component title = objective.getTitle();
        ScreenCanvas canvas = context.canvas();
        int titleWidth = MathHelper.ceil(canvas.getTextWidth(title));
        int maxWidth = titleWidth;
        float colonWidth = canvas.getTextWidth(": ");
        for (DisplayEntry entry : entries) {
            float scoreWidth = canvas.getTextWidth(entry.score());
            boolean scoresVisible = showScores && scoreWidth > 0.0f;
            maxWidth = MathHelper.ceil(Math.max(maxWidth, canvas.getTextWidth(entry.name()) + (scoresVisible ? colonWidth + scoreWidth : 0.0f)));
        }
        this.bodyArea.setPosition(0.0f, 0.0f);
        this.bodyArea.setSize(maxWidth + BODY_PADDING, (entries.size() * DEFAULT_FONT_HEIGHT) + BODY_PADDING);
        if (phase.canRender()) {
            context.pushStack();
            context.translate(0.0f, 0.0f, VanillaParityUtil.getScoreboardSidebarZLevel());
            renderSidebarBody(context, numberFormat, entries, showScores);
            renderSidebarHeader(context, title, titleWidth);
            context.popStack();
        }
        size.set((int) this.bodyArea.getWidth(), (int) (this.bodyArea.getHeight() + canvas.getLineHeight()));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        ScoreboardObjective objective;
        Scoreboard scoreboard = this.labyAPI.minecraft().getScoreboard();
        if (scoreboard == null || (objective = scoreboard.getObjective(DisplaySlot.SIDEBAR)) == null) {
            return false;
        }
        NumberFormat numberFormat = objective.getNumberFormatOrDefault(NumberFormat.noStyle());
        List<DisplayEntry> entries = getVisibleScores(scoreboard, objective, numberFormat);
        return !entries.isEmpty();
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean renderInDebug() {
        return true;
    }

    @Subscribe
    public void onScoreboardRender(IngameOverlayElementRenderEvent event) {
        if (isEnabled() && event.elementType() == IngameOverlayElementRenderEvent.OverlayElementType.SCOREBOARD && isVisibleInGame() && event.phase() == Phase.PRE) {
            event.setCancelled(true);
        }
    }

    private boolean showScores(Collection<DisplayEntry> entries) {
        ScoreboardHudWidgetConfig.ScoreVisibility scoreVisibility = ((ScoreboardHudWidgetConfig) this.config).scoreVisibility.get();
        if (scoreVisibility == ScoreboardHudWidgetConfig.ScoreVisibility.AUTO) {
            if (entries.size() <= 1) {
                return true;
            }
            int lastValue = -1;
            for (DisplayEntry entry : entries) {
                int currentValue = entry.scoreValue();
                if (lastValue != -1 && currentValue - lastValue != 1) {
                    return true;
                }
                lastValue = currentValue;
            }
        }
        return scoreVisibility == ScoreboardHudWidgetConfig.ScoreVisibility.VISIBLE;
    }

    private ScoreboardObjective getObjective(Scoreboard scoreboard) {
        if (scoreboard == null) {
            return null;
        }
        return scoreboard.getObjective(DisplaySlot.SIDEBAR);
    }

    private List<DisplayEntry> getVisibleScores(Scoreboard scoreboard, ScoreboardObjective objective, NumberFormat numberFormat) {
        Component displayName;
        Collection<ScoreboardScore> scores = scoreboard.getScores(objective);
        List<ScoreboardScore> toSort = new ArrayList<>();
        for (ScoreboardScore s : scores) {
            if (!s.isHidden()) {
                toSort.add(s);
            }
        }
        if (SORTED_SCORES) {
            toSort.sort(SCORE_DISPLAY_ORDER);
        }
        List<DisplayEntry> list = new ArrayList<>();
        long limit = 15;
        for (ScoreboardScore score : toSort) {
            long j = limit;
            limit = j - 1;
            if (j == 0) {
                break;
            }
            ScoreboardTeam scoreboardTeam = scoreboard.teamByEntry(score.getName());
            Component ownerName = score.getOwnerName();
            if (scoreboardTeam == null) {
                displayName = ownerName.copy();
            } else {
                displayName = scoreboardTeam.formatDisplayName(ownerName);
            }
            Component scoreName = displayName;
            DisplayEntry apply = new DisplayEntry(scoreName, score.formatValue(numberFormat), score.getValue());
            list.add(apply);
        }
        return list;
    }

    private void renderSidebarHeader(ScreenContext context, Component title, int titleWidth) {
        float y = this.bodyArea.getY();
        ScreenCanvas renderState = context.canvas();
        renderState.submitRelativeRect(this.bodyArea.getX(), y, this.bodyArea.getWidth(), DEFAULT_FONT_HEIGHT, ((ScoreboardHudWidgetConfig) this.config).headerColor.get().get());
        int flags = 0;
        if (((ScoreboardHudWidgetConfig) this.config).headerTextShadow.get().booleanValue()) {
            flags = 0 | 1;
        }
        renderState.submitComponent(title, this.bodyArea.getX() + ((this.bodyArea.getWidth() - titleWidth) / BODY_PADDING), y + 1.0f, -1, flags);
    }

    private void renderSidebarBody(ScreenContext context, NumberFormat numberFormat, List<DisplayEntry> entries, boolean showScores) {
        float width;
        float x;
        boolean sortedScores = SORTED_SCORES;
        float yOffset = 9.0f;
        int size = entries.size();
        int lineIndex = size;
        ScreenCanvas canvas = context.canvas();
        int lastEntry = sortedScores ? size - 1 : 0;
        int i = 0;
        while (i < size) {
            DisplayEntry line = entries.get(i);
            Component scoreComponent = line.score();
            Component nameComponent = line.name();
            if (!sortedScores) {
                yOffset = lineIndex * DEFAULT_FONT_HEIGHT;
            }
            canvas.submitRelativeRect(this.bodyArea.getX(), yOffset, this.bodyArea.getWidth(), DEFAULT_FONT_HEIGHT + (i == lastEntry ? BODY_PADDING : 0.0f), ((ScoreboardHudWidgetConfig) this.config).bodyColor().get().get());
            int flags = 0;
            if (((ScoreboardHudWidgetConfig) this.config).bodyTextShadow.get().booleanValue()) {
                flags = 0 | 1;
            }
            if (this.anchor.isRight()) {
                width = BODY_PADDING + this.bodyArea.getX();
            } else {
                width = this.bodyArea.getWidth() - canvas.getTextWidth(nameComponent);
            }
            canvas.submitComponent(nameComponent, width, BODY_PADDING + yOffset, -1, 1.0f, flags);
            if (showScores) {
                if (this.anchor.isRight()) {
                    x = this.bodyArea.getWidth() - canvas.getTextWidth(scoreComponent);
                } else {
                    x = this.bodyArea.getX() + BODY_PADDING;
                }
                canvas.submitComponent(scoreComponent, x, BODY_PADDING + yOffset, numberFormat == NumberFormat.noStyle() ? ((ScoreboardHudWidgetConfig) this.config).scoreColor.get().intValue() : -1, 1.0f, flags);
            }
            if (sortedScores) {
                yOffset += DEFAULT_FONT_HEIGHT;
            } else {
                lineIndex--;
            }
            i++;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DummyScore.class */
    private static class DummyScore implements ScoreboardScore {
        private final int index;

        private DummyScore(int index) {
            this.index = index;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardScore
        public String getName() {
            return "Test" + this.index;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardScore
        public int getValue() {
            return this.index;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardScore
        public Component getOwnerName() {
            return Component.text(getName());
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardScore
        public Component formatValue(NumberFormat format) {
            return format.format(getValue());
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DummyTeam.class */
    private static class DummyTeam implements ScoreboardTeam {
        private DummyTeam() {
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        @NotNull
        public String getTeamName() {
            return "Test";
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        @NotNull
        public Collection<String> getEntries() {
            return Collections.emptyList();
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        public boolean hasEntry(@NotNull String name) {
            return false;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        @NotNull
        public Component getPrefix() {
            return Component.empty();
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        @NotNull
        public Component getSuffix() {
            return Component.empty();
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardTeam
        @NotNull
        public Component formatDisplayName(@NotNull Component component) {
            return component;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DummyObjective.class */
    private static class DummyObjective implements ScoreboardObjective {
        private final ScoreboardScore score = new DummyScore(0);

        private DummyObjective() {
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @NotNull
        public String getName() {
            return "test";
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @NotNull
        public Component getTitle() {
            return Component.text("Title");
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @NotNull
        public ObjectiveRenderType getRenderType() {
            return ObjectiveRenderType.INTEGER;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @NotNull
        public ScoreboardScore getOrCreateScore(@NotNull String entry) {
            return this.score;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @Nullable
        public ScoreboardScore getScore(@NotNull String entry) {
            return this.score;
        }

        @Override // net.labymod.api.client.scoreboard.ScoreboardObjective
        @Nullable
        public NumberFormat getNumberFormat() {
            return NumberFormat.noStyle();
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DummyScoreboard.class */
    public static class DummyScoreboard implements Scoreboard {
        private final List<ScoreboardTeam> teams = new ArrayList();
        private final List<ScoreboardScore> scores = new ArrayList(2);
        private final ScoreboardObjective objective = new DummyObjective();

        public DummyScoreboard() {
            this.scores.add(new DummyScore(0));
            this.scores.add(new DummyScore(1));
            this.teams.add(new DummyTeam());
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        @NotNull
        public Collection<ScoreboardTeam> getTeams() {
            return this.teams;
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        public ScoreboardTeam teamByEntry(@NotNull String entry) {
            return (ScoreboardTeam) this.teams.getFirst();
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        @Nullable
        public ScoreboardObjective getObjective(@NotNull DisplaySlot slot) {
            return this.objective;
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        @Nullable
        public ScoreboardObjective getObjective(@NotNull String objective) {
            return this.objective;
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        @NotNull
        public Collection<ScoreboardScore> getScores(ScoreboardObjective objective) {
            return this.scores;
        }

        @Override // net.labymod.api.client.scoreboard.Scoreboard
        @NotNull
        public Collection<String> getEntries(ScoreboardObjective objective) {
            List<String> objects = new ArrayList<>();
            for (ScoreboardScore score : this.scores) {
                objects.add(score.getName());
            }
            return objects;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$ScoreboardHudWidgetConfig.class */
    public static class ScoreboardHudWidgetConfig extends HudWidgetConfig {

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> bodyTextShadow = new ConfigProperty<>(false);

        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> bodyColor = new ConfigProperty<>(Color.of(1275068416));

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> headerTextShadow = new ConfigProperty<>(false);

        @ColorPickerWidget.ColorPickerSetting(alpha = true, chroma = true)
        private final ConfigProperty<Color> headerColor = new ConfigProperty<>(Color.of(1711276032));

        @ColorPickerWidget.ColorPickerSetting(alpha = true)
        private final ConfigProperty<Integer> scoreColor = new ConfigProperty<>(-43691);

        @DropdownWidget.DropdownSetting
        private final ConfigProperty<ScoreVisibility> scoreVisibility = new ConfigProperty<>(ScoreVisibility.VISIBLE);

        /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$ScoreboardHudWidgetConfig$ScoreVisibility.class */
        public enum ScoreVisibility {
            VISIBLE,
            HIDDEN,
            AUTO
        }

        public ConfigProperty<Boolean> bodyTextShadow() {
            return this.bodyTextShadow;
        }

        public ConfigProperty<Color> bodyColor() {
            return this.bodyColor;
        }

        public ConfigProperty<Boolean> headerTextShadow() {
            return this.headerTextShadow;
        }

        public ConfigProperty<Color> headerColor() {
            return this.headerColor;
        }

        public ConfigProperty<Integer> scoreColor() {
            return this.scoreColor;
        }

        public ConfigProperty<ScoreVisibility> scoreVisibility() {
            return this.scoreVisibility;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry.class */
    private static final class DisplayEntry extends Record {
        private final Component name;
        private final Component score;
        private final int scoreValue;

        private DisplayEntry(Component name, Component score, int scoreValue) {
            this.name = name;
            this.score = score;
            this.scoreValue = scoreValue;
        }

        @Override // java.lang.Record
        public final String toString() {
            return (String) ObjectMethods.bootstrap(MethodHandles.lookup(), "toString", MethodType.methodType(String.class, DisplayEntry.class), DisplayEntry.class, "name;score;scoreValue", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->name:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->score:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->scoreValue:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final int hashCode() {
            return (int) ObjectMethods.bootstrap(MethodHandles.lookup(), "hashCode", MethodType.methodType(Integer.TYPE, DisplayEntry.class), DisplayEntry.class, "name;score;scoreValue", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->name:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->score:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->scoreValue:I").dynamicInvoker().invoke(this) /* invoke-custom */;
        }

        @Override // java.lang.Record
        public final boolean equals(Object o) {
            return (boolean) ObjectMethods.bootstrap(MethodHandles.lookup(), "equals", MethodType.methodType(Boolean.TYPE, DisplayEntry.class, Object.class), DisplayEntry.class, "name;score;scoreValue", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->name:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->score:Lnet/labymod/api/client/component/Component;", "FIELD:Lnet/labymod/core/client/gui/hud/hudwidget/ScoreboardHudWidget$DisplayEntry;->scoreValue:I").dynamicInvoker().invoke(this, o) /* invoke-custom */;
        }

        public Component name() {
            return this.name;
        }

        public Component score() {
            return this.score;
        }

        public int scoreValue() {
            return this.scoreValue;
        }
    }
}
