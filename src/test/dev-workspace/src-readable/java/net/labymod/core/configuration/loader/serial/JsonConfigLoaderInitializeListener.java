package net.labymod.core.configuration.loader.serial;

import com.google.gson.GsonBuilder;
import java.nio.file.Path;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.ComponentService;
import net.labymod.api.client.component.format.Style;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.widgets.input.TagInputWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.configuration.labymod.chat.config.RootChatTabConfig;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.config.JsonConfigLoaderInitializeEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.models.version.Version;
import net.labymod.api.nbt.NBTTag;
import net.labymod.api.util.Color;
import net.labymod.api.util.gson.ColorTypeAdapter;
import net.labymod.api.util.gson.ItemStackTypeAdapter;
import net.labymod.api.util.gson.KeyTypeAdapter;
import net.labymod.api.util.gson.NBTTagTypeAdapter;
import net.labymod.api.util.gson.PathTypeAdapter;
import net.labymod.api.util.gson.ResourceLocationTypeAdapter;
import net.labymod.api.util.gson.RootChatTabConfigTypeAdapter;
import net.labymod.api.util.gson.TagCollectionTypeAdapter;
import net.labymod.api.util.gson.VersionTypeAdapter;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/configuration/loader/serial/JsonConfigLoaderInitializeListener.class */
public class JsonConfigLoaderInitializeListener {
    @Subscribe
    public void onJsonConfigLoaderInitialize(JsonConfigLoaderInitializeEvent event) {
        GsonBuilder builder = event.getGsonBuilder();
        builder.registerTypeAdapter(Color.class, new ColorTypeAdapter());
        builder.registerTypeAdapter(Key.class, new KeyTypeAdapter());
        builder.registerTypeAdapter(MouseButton.class, new KeyTypeAdapter());
        builder.registerTypeAdapter(TagInputWidget.TagCollection.class, new TagCollectionTypeAdapter());
        builder.registerTypeAdapter(RootChatTabConfig.Type.class, new RootChatTabConfigTypeAdapter());
        builder.registerTypeHierarchyAdapter(ItemStack.class, new ItemStackTypeAdapter());
        builder.registerTypeHierarchyAdapter(Version.class, new VersionTypeAdapter());
        builder.registerTypeHierarchyAdapter(NBTTag.class, new NBTTagTypeAdapter());
        builder.registerTypeHierarchyAdapter(ResourceLocation.class, new ResourceLocationTypeAdapter());
        builder.registerTypeHierarchyAdapter(Path.class, new PathTypeAdapter());
        builder.registerTypeHierarchyAdapter(Component.class, ComponentService.getComponentSerializer());
        if (!MinecraftVersions.V23w40a.orNewer()) {
            builder.registerTypeHierarchyAdapter(Style.class, ComponentService.getStyleSerializer());
        }
    }
}
