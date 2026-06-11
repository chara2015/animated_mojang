package net.labymod.v26_2_snapshot_8.client.gui.screen.widget.converter;

import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer;
import net.labymod.api.client.gui.screen.widget.converter.WidgetConverterRegistry;
import net.labymod.api.client.gui.screen.widget.converter.exclusion.ExclusionStrategy;
import net.labymod.api.service.annotation.AutoService;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.gui.components.AbstractOptionSliderButton;
import net.minecraft.client.gui.components.AbstractSliderButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.components.tabs.TabNavigationBar;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.dialog.DialogScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.MinecartCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.client.gui.screens.inventory.SmokerScreen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.TestBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.TestInstanceBlockEditScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v26_2_snapshot_8/client/gui/screen/widget/converter/VersionedWidgetConverterInitializer.class */
@AutoService(value = WidgetConverterInitializer.class, versionSpecific = true)
public class VersionedWidgetConverterInitializer implements WidgetConverterInitializer {
    @Override // net.labymod.api.client.gui.screen.widget.converter.WidgetConverterInitializer
    public void initialize(WidgetConverterRegistry registry) {
        registry.exclude(SocialInteractionsScreen.class, InventoryScreen.class, CraftingScreen.class, FurnaceScreen.class, BlastFurnaceScreen.class, SmokerScreen.class, ChatScreen.class, AnvilScreen.class, CreativeModeInventoryScreen.class, AdvancementsScreen.class, CommandBlockEditScreen.class, MinecartCommandBlockEditScreen.class, JigsawBlockEditScreen.class, StructureBlockEditScreen.class, TestBlockEditScreen.class, TestInstanceBlockEditScreen.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) InBedChatScreen.class, (Class<?>) EditBox.class));
        registry.exclude(ExclusionStrategy.parent(DialogScreen.class));
        registry.exclude(PageButton.class);
        registry.exclude(ExclusionStrategy.widget((Class<?>) TitleScreen.class, (Class<?>) PlainTextButton.class));
        registry.register(new ButtonConverter(), Button.class, CycleButton.class, ImageButton.class, LockIconButton.class);
        registry.register(new SliderConverter(), AbstractSliderButton.class, AbstractOptionSliderButton.class, OptionInstance.OptionInstanceSliderButton.class);
        registry.register(new TextFieldConverter(), EditBox.class);
        registry.register(new TabLayoutConverter(), TabNavigationBar.class);
        registry.register(new StringConverter(), StringWidget.class);
    }
}
