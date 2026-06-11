package net.labymod.core.client.gui.hud.hudwidget;

import java.util.ArrayList;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.Namespaces;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.player.ClientPlayer;
import net.labymod.api.client.gui.hud.binding.category.HudWidgetCategory;
import net.labymod.api.client.gui.hud.binding.dropzone.NamedHudWidgetDropzones;
import net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget;
import net.labymod.api.client.gui.hud.position.HudSize;
import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.client.gui.screen.state.ScreenCanvas;
import net.labymod.api.client.gui.screen.theme.DefaultThemeVariables;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.itemstack.ItemStackPickerWidget;
import net.labymod.api.client.render.ItemStackVisualizer;
import net.labymod.api.client.render.font.ComponentRenderer;
import net.labymod.api.client.render.font.RenderableComponent;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemData;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.VanillaItem;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.configuration.loader.Config;
import net.labymod.api.configuration.loader.annotation.IntroducedIn;
import net.labymod.api.configuration.loader.annotation.SpriteSlot;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.type.list.ListSettingConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.inventory.InventorySetSlotEvent;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ItemCounterHudWidget.class */
@SpriteSlot(x = 1, y = 5)
@IntroducedIn("4.1.0")
public class ItemCounterHudWidget extends BackgroundHudWidget<ItemCounterHudWidgetConfig> {
    private final List<CountingItem> dummyItems;

    public ItemCounterHudWidget() {
        super("item_counter", ItemCounterHudWidgetConfig.class);
        this.dummyItems = new ArrayList();
        bindCategory(HudWidgetCategory.INGAME);
        bindDropzones(NamedHudWidgetDropzones.ITEM_COUNTER_LEFT, NamedHudWidgetDropzones.ITEM_COUNTER_RIGHT);
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public void load(ItemCounterHudWidgetConfig config) {
        super.load(config);
        this.dummyItems.clear();
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.GOLDEN_APPLE, 4));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.MUSHROOM_STEM, 12));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.COOKED_BEEF, 5));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.ARROW, 128));
        this.dummyItems.add(CountingItem.createDummy(VanillaItems.SPLASH_POTION, 0));
        List<CountingItem> items = config.items.get();
        if (items.isEmpty()) {
            for (CountingItem dummyItem : this.dummyItems) {
                CountingItem item = dummyItem.copyWithCount(0);
                item.updateItemCount();
                items.add(item);
            }
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.background.BackgroundHudWidget, net.labymod.api.client.gui.hud.hudwidget.SimpleHudWidget
    public void render(SimpleHudWidget.RenderPhase phase, ScreenContext context, boolean isEditorContext, HudSize size) {
        List<CountingItem> list;
        List<CountingItem> countingItems = ((ItemCounterHudWidgetConfig) this.config).items.get();
        if (countingItems.isEmpty() && !isEditorContext) {
            return;
        }
        ItemStackVisualizer itemStackVisualizer = Laby.references().itemStackVisualizer();
        ComponentRenderer componentRenderer = this.labyAPI.renderPipeline().componentRenderer();
        float margin = ((ItemCounterHudWidgetConfig) this.config).background().getMargin();
        float padding = ((ItemCounterHudWidgetConfig) this.config).background().getPadding();
        float spacingHeight = ((ItemCounterHudWidgetConfig) this.config).spacingHeight().get().floatValue();
        float segmentSpacing = ((ItemCounterHudWidgetConfig) this.config).segmentSpacing().get().floatValue();
        int visibleItems = 0;
        for (CountingItem countingItem : countingItems) {
            if (!countingItem.hideWhenNoItems().get().booleanValue() || countingItem.getItemCount() != 0) {
                visibleItems++;
            }
        }
        ScreenCanvas renderState = context.canvas();
        boolean floatingPointPosition = ((Boolean) Laby.references().themeService().currentTheme().metadata().get(DefaultThemeVariables.HUD_WIDGET_FLOATING_POINT_POSITION, false)).booleanValue();
        float x = 0.0f;
        if (countingItems.isEmpty() || (visibleItems == 0 && isEditorContext)) {
            list = this.dummyItems;
        } else {
            list = countingItems;
        }
        List<CountingItem> list2 = list;
        for (CountingItem countingItem2 : list2) {
            float segmentWidth = Math.max(16.0f, countingItem2.renderableCount().getWidth()) + (padding * 2.0f);
            if (segmentWidth % 2.0f != 0.0f) {
                segmentWidth += 1.0f;
            }
            if (!countingItem2.hideWhenNoItems().get().booleanValue() || countingItem2.getItemCount() != 0) {
                if (phase.canRender()) {
                    super.renderBackgroundSegment(context, x + margin, margin, segmentWidth, 16.0f + spacingHeight + componentRenderer.height() + (padding * 2.0f));
                    ItemStack itemStack = countingItem2.itemStack();
                    itemStackVisualizer.submitItem(context, itemStack, (int) (((x + (segmentWidth / 2.0f)) - 8.0f) + margin), (int) (padding + margin));
                    int flags = 3;
                    if (floatingPointPosition) {
                        flags = 3 | 4;
                    }
                    renderState.submitRenderableComponent(countingItem2.renderableCount(), x + (segmentWidth / 2.0f) + margin + (floatingPointPosition ? 0.5f : 0.0f), 16.0f + spacingHeight + padding + margin, -1, flags);
                }
                x += segmentWidth + segmentSpacing;
            }
        }
        size.set(Math.max(x - segmentSpacing, segmentSpacing) + (margin * 2.0f), 16.0f + spacingHeight + componentRenderer.height() + (padding * 2.0f) + (margin * 2.0f));
    }

    @Subscribe
    public void onInventorySetSlot(InventorySetSlotEvent event) {
        for (CountingItem countingItem : ((ItemCounterHudWidgetConfig) this.config).items.get()) {
            countingItem.updateItemCount();
        }
    }

    @Override // net.labymod.api.client.gui.hud.hudwidget.HudWidget
    public boolean isVisibleInGame() {
        return !((ItemCounterHudWidgetConfig) this.config).items.get().isEmpty();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ItemCounterHudWidget$ItemCounterHudWidgetConfig.class */
    public static class ItemCounterHudWidgetConfig extends BackgroundHudWidget.BackgroundHudWidgetConfig {
        private final ConfigProperty<List<CountingItem>> items = new ConfigProperty<>(new ArrayList());

        @SliderWidget.SliderSetting(min = 0.0f, max = 20.0f, steps = 1.0f)
        private final ConfigProperty<Float> segmentSpacing = new ConfigProperty<>(Float.valueOf(2.0f));

        @SliderWidget.SliderSetting(min = 0.0f, max = 20.0f, steps = 1.0f)
        private final ConfigProperty<Float> spacingHeight = new ConfigProperty<>(Float.valueOf(2.0f));

        public ConfigProperty<Float> segmentSpacing() {
            return this.segmentSpacing;
        }

        public ConfigProperty<Float> spacingHeight() {
            return this.spacingHeight;
        }
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/hud/hudwidget/ItemCounterHudWidget$CountingItem.class */
    public static class CountingItem extends Config implements ListSettingConfig {
        private static final ItemData DEFAULT_ITEM = Laby.references().itemStackFactory().create(Namespaces.MINECRAFT, "apple").toItemData();

        @ItemStackPickerWidget.ItemStackSetting
        private final ConfigProperty<ItemData> item = ConfigProperty.create(DEFAULT_ITEM, this::updateItemStack);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> hideWhenNoItems = new ConfigProperty<>(false);

        @SwitchWidget.SwitchSetting
        private final ConfigProperty<Boolean> ignoreNBT = new ConfigProperty<>(false);
        private transient ItemStack itemStack;
        private transient int itemCount;
        private transient RenderableComponent renderableCount;

        public CountingItem() {
            this.item.addChangeListener((type, oldValue, newValue) -> {
                updateItemStack(newValue);
                updateItemCount();
            });
            this.ignoreNBT.addChangeListener((type2, oldValue2, newValue2) -> {
                updateItemCount();
            });
            updateItemCount();
        }

        private CountingItem(ItemData itemStack, int itemCount) {
            this.item.set(itemStack);
            onItemCountChange(itemCount);
        }

        @Override // net.labymod.api.configuration.settings.type.list.ListSettingConfig
        @NotNull
        public Component newEntryTitle() {
            return this.itemStack.getDisplayName();
        }

        @Override // net.labymod.api.configuration.settings.type.list.ListSettingConfig
        @NotNull
        public Component entryDisplayName() {
            return this.itemStack.getDisplayName();
        }

        public int getItemCount() {
            return this.itemCount;
        }

        public RenderableComponent renderableCount() {
            return this.renderableCount;
        }

        public ConfigProperty<ItemData> item() {
            return this.item;
        }

        public ConfigProperty<Boolean> hideWhenNoItems() {
            return this.hideWhenNoItems;
        }

        public ConfigProperty<Boolean> ignoreNBT() {
            return this.ignoreNBT;
        }

        private void updateItemCount() {
            ClientPlayer player = Laby.labyAPI().minecraft().getClientPlayer();
            if (player == null) {
                onItemCountChange(0);
                return;
            }
            int count = 0;
            for (int slot = 0; slot < 36; slot++) {
                ItemStack stack = player.inventory().itemStackAt(slot);
                if (!stack.isAir() && compare(stack, this.itemStack)) {
                    count += stack.getSize();
                }
            }
            onItemCountChange(count);
        }

        private boolean compare(ItemStack first, ItemStack second) {
            if (ignoreNBT().get().booleanValue()) {
                return ItemStack.isSameItem(first, second);
            }
            return ItemStack.isSameItemSameTags(first, second);
        }

        private void onItemCountChange(int count) {
            this.itemCount = count;
            this.renderableCount = RenderableComponent.of(Component.text(Integer.valueOf(count)));
        }

        public CountingItem copyWithCount(int itemCount) {
            return new CountingItem(this.item.get(), itemCount);
        }

        public ItemStack itemStack() {
            return this.itemStack;
        }

        private void updateItemStack(ItemData data) {
            this.itemStack = data.toItemStack();
        }

        public static CountingItem createDummy(VanillaItem item, int itemCount) {
            return createDummy(item.identifier(), itemCount);
        }

        public static CountingItem createDummy(ResourceLocation identifier, int itemCount) {
            ItemStack itemStack = Laby.references().itemStackFactory().create(identifier);
            return new CountingItem(itemStack.toItemData(), itemCount);
        }
    }
}
