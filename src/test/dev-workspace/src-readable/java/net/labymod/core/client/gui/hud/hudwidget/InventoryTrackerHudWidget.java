package net.labymod.core.client.gui.hud.hudwidget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.component.serializer.legacy.LegacyComponentSerializer;
import net.labymod.api.client.entity.LivingEntity;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gfx.imgui.flag.ImGuiFlags;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundConfig;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.widget.attributes.animation.CubicBezier;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.render.RenderPipeline;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.Item;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.ItemStackFactory;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Phase;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import net.labymod.api.event.client.lifecycle.GameTickEvent;
import net.labymod.api.event.client.render.RenderTypeAttachmentEvent;
import net.labymod.api.event.client.world.WorldLeaveEvent;
import net.labymod.api.event.client.world.WorldLoadEvent;
import net.labymod.api.laby3d.render.queue.SubmissionOrders;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.nbt.tags.NBTTagCompound;
import net.labymod.api.util.logging.Logging;
import net.labymod.api.util.time.TimeUtil;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/InventoryTrackerHudWidget.class */
@SpriteSlot(x = 7, y = 4)
@IntroducedIn("4.1.0")
public class InventoryTrackerHudWidget extends BackgroundHudWidget<InventoryTrackerHudWidgetConfig> {
    private static final Logging LOGGER = Logging.getLogger();
    private static final long FADE_DURATION = 200;
    private static final int ICON_SIZE = 15;
    private static final float ICON_SCALE = 0.65f;
    private final ItemStack[] slots;
    private final List<InventoryChangeRecord> records;
    private final List<InventoryChangeRecord> dummyRecords;
    private boolean dirty;
    private boolean renderItem;
    private long timeLastWorldLoad;

    public InventoryTrackerHudWidget() {
        super("inventory_tracker", InventoryTrackerHudWidgetConfig.class);
        this.slots = new ItemStack[41];
        this.records = new ArrayList();
        this.dummyRecords = new ArrayList();
        this.dirty = false;
        bindCategory(HudWidgetCategory.INGAME);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(InventoryTrackerHudWidgetConfig config) {
        super.load(config);
        this.dummyRecords.clear();
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.STONE, 46, (Component) null, config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.DIAMOND_SWORD, 1, (Component) null, config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.COOKIE, 1, Component.text("Cookie").color(NamedTextColor.GOLD).append(Component.text(" ❤").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)), config));
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.STICK, -1, (Component) null, config));
        TextComponent displayName = Component.text("My Diamond").color(NamedTextColor.AQUA);
        this.dummyRecords.add(InventoryChangeRecord.createDummy(VanillaItems.DIAMOND, -2, displayName, config));
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget, net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        BackgroundConfig background = ((InventoryTrackerHudWidgetConfig) this.config).background();
        float margin = background.getMargin();
        float padding = background.getPadding();
        float spacing = ((InventoryTrackerHudWidgetConfig) this.config).lineSpacing().get().intValue();
        float x = margin + padding;
        float y = margin + padding;
        float width = 0.0f;
        boolean firstLayer = true;
        boolean rightAligned = anchor().isRight();
        for (RecordRenderLayer layer : RecordRenderLayer.getLayers(rightAligned)) {
            float maxLayerWidth = 0.0f;
            y = margin + padding;
            if (!((InventoryTrackerHudWidgetConfig) this.config).showIcon().get().booleanValue() && layer == RecordRenderLayer.ICON) {
                width += 2.0f;
            } else {
                List<InventoryChangeRecord> records = isEditorContext ? this.dummyRecords : this.records;
                for (InventoryChangeRecord record : records) {
                    if (!record.isExpired()) {
                        RenderableComponent nameComponent = getValidatedComponent(record, (v0) -> {
                            return v0.displayNameComponent();
                        });
                        RenderableComponent countComponent = getValidatedComponent(record, (v0) -> {
                            return v0.countComponent();
                        });
                        float height = (nameComponent.getHeight() + (padding * 2.0f)) * record.getOpacity();
                        if (phase.canRender()) {
                            if (firstLayer) {
                                renderBackgroundSegment(context, x - padding, y - padding, size.getActualWidth() - (margin * 2.0f), height, record.getOpacity());
                            }
                            renderRecord(context, record, x + width, y, layer, isEditorContext);
                        }
                        float recordLayerWidth = padding * 2.0f;
                        switch (layer) {
                            case COUNT:
                                recordLayerWidth += countComponent.getWidth();
                                break;
                            case ICON:
                                recordLayerWidth += 15.0f;
                                break;
                            case NAME:
                                recordLayerWidth += nameComponent.getWidth();
                                break;
                        }
                        y += height + spacing;
                        maxLayerWidth = Math.max(maxLayerWidth, recordLayerWidth);
                    }
                }
                width += maxLayerWidth;
                firstLayer = false;
            }
        }
        size.set(width + 2.0f + (margin * 2.0f), Math.max(((y + margin) - padding) - spacing, spacing));
    }

    private RenderableComponent getValidatedComponent(InventoryChangeRecord record, Function<InventoryChangeRecord, RenderableComponent> componentGetter) {
        RenderableComponent component = componentGetter.apply(record);
        float height = component.getHeight();
        if (height <= 0.0f) {
            component.updateBounds();
        }
        return component;
    }

    private void renderRecord(ScreenContext context, InventoryChangeRecord record, float x, float y, RecordRenderLayer layer, boolean isEditorContext) {
        ScreenCanvas renderState = context.canvas();
        RenderPipeline renderPipeline = this.labyAPI.renderPipeline();
        float currentAlpha = renderPipeline.getAlpha();
        float opacity = currentAlpha * record.getOpacity();
        if (opacity == 0.0f) {
            return;
        }
        renderPipeline.setAlpha(opacity, () -> {
            float maxNameWidth;
            switch (layer) {
                case COUNT:
                    RenderableComponent countComponent = record.countComponent();
                    renderState.submitRenderableComponent(countComponent, x, y + 1.0f, -1, 1);
                    break;
                case ICON:
                    context.pushStack();
                    context.scale(ICON_SCALE, ICON_SCALE, 1.0f);
                    this.renderItem = true;
                    Laby.references().itemStackVisualizer().submitItem(context, record.icon(), ((int) ((x + 7.0f) / ICON_SCALE)) - 8, ((int) ((y + 5.0f) / ICON_SCALE)) - 8, false);
                    this.renderItem = false;
                    context.popStack();
                    break;
                case NAME:
                    RenderableComponent nameComponent = record.displayNameComponent();
                    if (anchor().isRight()) {
                        maxNameWidth = getMaxNameWidth(isEditorContext) - nameComponent.getWidth();
                    } else {
                        maxNameWidth = 0.0f;
                    }
                    float offset = maxNameWidth;
                    renderState.submitRenderableComponent(nameComponent, x + offset, y + 1.0f, -1, 1);
                    break;
            }
        });
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        for (InventoryChangeRecord record : this.records) {
            if (!record.isExpired()) {
                return true;
            }
        }
        return false;
    }

    @Subscribe
    public void onWorldLoad(WorldLoadEvent event) {
        this.timeLastWorldLoad = TimeUtil.getMillis();
    }

    @Subscribe
    public void onGameTick(GameTickEvent event) {
        if (event.phase() == Phase.POST && !this.labyAPI.minecraft().isIngame()) {
            this.timeLastWorldLoad = TimeUtil.getMillis();
        }
    }

    @Subscribe
    public void onWorldLeave(WorldLeaveEvent event) {
        this.records.clear();
        Arrays.fill(this.slots, (Object) null);
    }

    @Subscribe
    public void onInventorySlotChanged(InventorySetSlotEvent event) {
        this.dirty = true;
    }

    @Subscribe
    public void onRenderTypeAttachment(RenderTypeAttachmentEvent event) {
        if (event.phase() != Phase.POST || !this.renderItem) {
            return;
        }
        String name = event.name();
        if (("entity_cutout".equals(name) || "entity_translucent_cull".equals(name)) && event.state() == RenderTypeAttachmentEvent.State.APPLY) {
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void onTick(boolean isEditorContext) {
        super.onTick(isEditorContext);
        ClientPlayer player = this.labyAPI.minecraft().getClientPlayer();
        if (player != null && this.dirty) {
            this.dirty = false;
            purgeInvalidRecords();
            analyzeDifference(player);
        }
    }

    private void analyzeDifference(ClientPlayer player) {
        for (int i = 0; i < this.slots.length; i++) {
            ItemStack newStack = getItemAtIndex(player, i).copy();
            ItemStack oldStack = this.slots[i];
            if (oldStack == null || oldStack.isAir()) {
                if (!newStack.isAir()) {
                    itemStackChanged(newStack, newStack.getSize());
                }
            } else if (newStack.getAsItem().equals(oldStack.getAsItem())) {
                int difference = newStack.getSize() - oldStack.getSize();
                if (difference != 0) {
                    itemStackChanged(newStack, difference);
                }
            } else {
                itemStackChanged(oldStack, -oldStack.getSize());
                if (!newStack.isAir()) {
                    itemStackChanged(newStack, newStack.getSize());
                }
            }
            this.slots[i] = newStack;
        }
    }

    private ItemStack getItemAtIndex(ClientPlayer player, int index) {
        if (index < 36) {
            return player.inventory().itemStackAt(index);
        }
        switch (index) {
            case 36:
                return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.HEAD);
            case ImGuiFlags.StyleColors.TabUnfocusedActive /* 37 */:
                return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.CHEST);
            case ImGuiFlags.StyleColors.DockingPreview /* 38 */:
                return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.LEGS);
            case ImGuiFlags.StyleColors.DockingEmptyBg /* 39 */:
                return player.getEquipmentItemStack(LivingEntity.EquipmentSpot.FEET);
            default:
                return player.getOffHandItemStack();
        }
    }

    private void itemStackChanged(ItemStack itemStack, int difference) {
        long timePassedSinceLastWorldLoad = TimeUtil.getMillis() - this.timeLastWorldLoad;
        boolean showInitialItems = ((InventoryTrackerHudWidgetConfig) this.config).showInitialItems().get().booleanValue() || timePassedSinceLastWorldLoad > 1000;
        if (showInitialItems) {
            computeIfAbsent(itemStack).add(difference);
            this.records.sort((a, b) -> {
                return Long.compare(b.getStartTimestamp(), a.getStartTimestamp());
            });
        } else {
            this.records.clear();
        }
    }

    private InventoryChangeRecord computeIfAbsent(ItemStack itemStack) {
        for (InventoryChangeRecord record : this.records) {
            if (record.isItem(itemStack.getAsItem()) && record.isMutable()) {
                return record;
            }
        }
        InventoryTrackerHudWidgetConfig config = (InventoryTrackerHudWidgetConfig) this.config;
        InventoryChangeRecord record2 = InventoryChangeRecord.fromItemStack(itemStack, config);
        this.records.add(record2);
        return record2;
    }

    private void purgeInvalidRecords() {
        this.records.removeIf((v0) -> {
            return v0.isExpired();
        });
    }

    private float getMaxNameWidth(boolean isEditorContext) {
        float maxWidth = 0.0f;
        for (InventoryChangeRecord record : isEditorContext ? this.dummyRecords : this.records) {
            if (!record.isExpired()) {
                maxWidth = Math.max(maxWidth, record.displayNameComponent().getWidth());
            }
        }
        return maxWidth;
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/InventoryTrackerHudWidget$InventoryChangeRecord.class */
    private static class InventoryChangeRecord {
        private final ItemStack icon;
        private final RenderableComponent displayNameComponent;
        private final boolean colored;
        private final Boolean showItemDrops;
        private long timestamp;
        private long duration;
        private int count;
        private RenderableComponent countComponent;

        private InventoryChangeRecord(ItemStack icon, InventoryTrackerHudWidgetConfig config) {
            this.icon = icon;
            this.duration = config.displayTime().get().intValue() * SubmissionOrders.DEBUG;
            this.colored = config.coloredAmount().get().booleanValue();
            this.showItemDrops = config.showItemDrops().get();
            if (config.customItemName().get().booleanValue()) {
                this.displayNameComponent = RenderableComponent.of(icon.getDisplayName());
                return;
            }
            ResourceLocation identifier = this.icon.getAsItem().getIdentifier();
            ItemStack itemStack = Laby.references().itemStackFactory().create(identifier);
            this.displayNameComponent = RenderableComponent.of(itemStack.getDisplayName());
        }

        public void add(int amount) {
            TextColor textColor;
            this.count += amount;
            boolean positive = this.count >= 0;
            boolean isInitial = this.timestamp <= 1;
            boolean isZero = this.count == 0;
            if (isInitial) {
                this.timestamp = (this.showItemDrops.booleanValue() || positive) ? TimeUtil.getMillis() : -200L;
            } else {
                boolean isFadingIn = getFadeInProgress() < 1.0f;
                if (!isFadingIn) {
                    this.timestamp = TimeUtil.getMillis() - InventoryTrackerHudWidget.FADE_DURATION;
                }
            }
            String sign = positive ? "+" : "";
            if (isZero || !this.colored) {
                textColor = NamedTextColor.WHITE;
            } else {
                textColor = positive ? NamedTextColor.GREEN : NamedTextColor.RED;
            }
            TextColor color = textColor;
            TextComponent amountComponent = Component.text(sign + this.count).color(color);
            this.countComponent = RenderableComponent.of(amountComponent);
            if (isZero || (!this.showItemDrops.booleanValue() && !positive)) {
                finish();
            }
        }

        private void finish() {
            this.duration = 0L;
            if (getTimeAlive() <= 20) {
                this.timestamp = 0L;
            }
        }

        private void showForever() {
            boolean positive = this.count >= 0;
            if (positive || this.showItemDrops.booleanValue()) {
                this.duration = Long.MAX_VALUE;
                this.timestamp -= InventoryTrackerHudWidget.FADE_DURATION;
            }
        }

        public float getFadeInProgress() {
            long timePassed = getTimeAlive();
            if (timePassed > InventoryTrackerHudWidget.FADE_DURATION) {
                return 1.0f;
            }
            return timePassed / 200.0f;
        }

        public float getFadeOutProgress() {
            long timeRemaining = getTimeRemaining();
            if (timeRemaining > InventoryTrackerHudWidget.FADE_DURATION) {
                return 0.0f;
            }
            return 1.0f - (timeRemaining / 200.0f);
        }

        public long getStartTimestamp() {
            return this.timestamp;
        }

        public long getEndTimestamp() {
            if (this.duration == Long.MAX_VALUE) {
                return Long.MAX_VALUE;
            }
            return this.timestamp + this.duration + 400;
        }

        public float getOpacity() {
            float opacity = getFadeInProgress() * (1.0f - getFadeOutProgress());
            return (float) CubicBezier.EASE_IN_OUT.curve(opacity);
        }

        public long getTimeAlive() {
            return TimeUtil.getMillis() - this.timestamp;
        }

        public long getTimeRemaining() {
            return getEndTimestamp() - TimeUtil.getMillis();
        }

        public boolean isMutable() {
            return !isExpired() && getTimeRemaining() > InventoryTrackerHudWidget.FADE_DURATION;
        }

        public boolean isExpired() {
            return getEndTimestamp() < TimeUtil.getMillis();
        }

        public ItemStack icon() {
            return this.icon;
        }

        public RenderableComponent displayNameComponent() {
            return this.displayNameComponent;
        }

        public RenderableComponent countComponent() {
            return this.countComponent;
        }

        public int getCount() {
            return this.count;
        }

        public boolean isItem(Item item) {
            return this.icon.getAsItem().equals(item);
        }

        public static InventoryChangeRecord fromItemStack(ItemStack itemStack, InventoryTrackerHudWidgetConfig config) {
            return new InventoryChangeRecord(itemStack, config);
        }

        public static InventoryChangeRecord createDummy(VanillaItem item, int amount, Component displayName, InventoryTrackerHudWidgetConfig config) {
            return createDummy(item.identifier(), amount, displayName, config);
        }

        public static InventoryChangeRecord createDummy(ResourceLocation identifier, int amount, Component displayName, InventoryTrackerHudWidgetConfig config) {
            ItemStackFactory factory = Laby.references().itemStackFactory();
            ItemStack itemStack = factory.create(identifier);
            if (displayName != null) {
                NBTTagCompound nbtTag = itemStack.getOrCreateNBTTag();
                NBTTagCompound display = Laby.references().nbtFactory().compound();
                if (MinecraftVersions.V1_12_2.orOlder()) {
                    String name = LegacyComponentSerializer.legacySection().serialize(displayName);
                    display.setString("Name", name);
                } else {
                    String json = Laby.references().gsonComponentSerializer().serialize(displayName);
                    display.setString("Name", json);
                }
                nbtTag.set("display", display);
            }
            InventoryChangeRecord record = fromItemStack(itemStack, config);
            record.add(amount);
            record.showForever();
            return record;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/InventoryTrackerHudWidget$RecordRenderLayer.class */
    private enum RecordRenderLayer {
        COUNT,
        ICON,
        NAME;

        public static final RecordRenderLayer[] VALUES = values();
        public static final RecordRenderLayer[] VALUES_REVERSE = (RecordRenderLayer[]) Stream.of((Object[]) VALUES).sorted((a, b) -> {
            return Integer.compare(b.ordinal(), a.ordinal());
        }).toArray(x$0 -> {
            return new RecordRenderLayer[x$0];
        });

        public static RecordRenderLayer[] getLayers(boolean reverse) {
            return reverse ? VALUES_REVERSE : VALUES;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/InventoryTrackerHudWidget$InventoryTrackerHudWidgetConfig.class */
    public static class InventoryTrackerHudWidgetConfig extends BackgroundHudWidget.BackgroundHudWidgetConfig {

        @SliderWidget.SliderSetting(min = 1.0f, max = 10.0f, steps = 1.0f)
        private final ConfigProperty<Integer> displayTime = new ConfigProperty<>(5);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> coloredAmount = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> customItemName = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showIcon = new ConfigProperty<>(true);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showInitialItems = new ConfigProperty<>(false);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> showItemDrops = new ConfigProperty<>(true);

        @SliderWidget.SliderSetting(min = 0.0f, max = 10.0f, steps = 1.0f)
        private final ConfigProperty<Integer> lineSpacing = new ConfigProperty<>(1);

        public ConfigProperty<Integer> displayTime() {
            return this.displayTime;
        }

        public ConfigProperty<Boolean> coloredAmount() {
            return this.coloredAmount;
        }

        public ConfigProperty<Boolean> customItemName() {
            return this.customItemName;
        }

        public ConfigProperty<Boolean> showIcon() {
            return this.showIcon;
        }

        public ConfigProperty<Boolean> showInitialItems() {
            return this.showInitialItems;
        }

        public ConfigProperty<Boolean> showItemDrops() {
            return this.showItemDrops;
        }

        public ConfigProperty<Integer> lineSpacing() {
            return this.lineSpacing;
        }
    }
}
