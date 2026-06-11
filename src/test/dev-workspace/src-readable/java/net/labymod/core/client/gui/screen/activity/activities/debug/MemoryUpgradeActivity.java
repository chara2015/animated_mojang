package net.labymod.core.client.gui.screen.activity.activities.debug;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.activity.AutoActivity;
import net.labymod.api.client.gui.screen.activity.Link;
import net.labymod.api.client.gui.screen.activity.types.SimpleActivity;
import net.labymod.api.client.gui.screen.widget.action.SliderInteraction;
import net.labymod.api.client.gui.screen.widget.widgets.ComponentWidget;
import net.labymod.api.client.gui.screen.widget.widgets.DivWidget;
import net.labymod.api.client.gui.screen.widget.widgets.activity.Document;
import net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget;
import net.labymod.api.client.gui.screen.widget.widgets.input.SliderWidget;
import net.labymod.api.client.gui.screen.widget.widgets.layout.list.HorizontalListWidget;
import net.labymod.api.notification.Notification;
import net.labymod.api.platform.launcher.LauncherVendorType;
import net.labymod.api.platform.launcher.MinecraftLauncher;
import net.labymod.api.platform.launcher.MinecraftLauncherFactory;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.math.MathHelper;
import net.labymod.core.main.lagdetector.GarbageCollectorLagDetectionModule;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/gui/screen/activity/activities/debug/MemoryUpgradeActivity.class */
@AutoActivity
@Link("activity/debug/memory-upgrade.lss")
public class MemoryUpgradeActivity extends SimpleActivity implements SliderInteraction {
    private static final String TRANSLATION_UI_BUTTON_KEY = "labymod.ui.button.";
    private static final String TRANSLATION_ACTIVITY_KEY = "labymod.activity.memoryUpgrade.";
    private static final String PROFILES_KEY = "profiles";
    private static final String JAVA_ARGS_KEY = "javaArgs";
    private static final int SLIDER_STEPS = 512;
    private static final long MIN_MEMORY = 2048;
    private static final long MAX_MEMORY = 5120;
    private ComponentWidget memorySliderLabelWidget;
    private float value;

    @Override // net.labymod.api.client.gui.screen.activity.Activity, net.labymod.api.client.gui.screen.activity.ElementActivity, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        MinecraftLauncherFactory factory = Laby.references().minecraftLauncherFactory();
        MinecraftLauncher launcher = factory.create(LauncherVendorType.MOJANG);
        DivWidget containerWidget = new DivWidget();
        containerWidget.addId("container");
        ComponentWidget titleWidget = ComponentWidget.i18n("labymod.activity.memoryUpgrade.title");
        titleWidget.addId("title");
        containerWidget.addChild(titleWidget);
        Runtime runtime = Runtime.getRuntime();
        ComponentWidget descriptionWidget = ComponentWidget.i18n("labymod.activity.memoryUpgrade.description");
        descriptionWidget.addId("description");
        containerWidget.addChild(descriptionWidget);
        HorizontalListWidget horizontalListWidget = new HorizontalListWidget();
        horizontalListWidget.addId("memory-slider-container");
        containerWidget.addChild(horizontalListWidget);
        SliderWidget memorySliderWidget = new SliderWidget(512.0f, this);
        memorySliderWidget.range(2048.0f, 5120.0f);
        this.memorySliderLabelWidget = ComponentWidget.text("");
        this.memorySliderLabelWidget.addId("memory-slider-label");
        memorySliderWidget.setValue(MathHelper.clamp((runtime.maxMemory() / 1024) / 1024, MIN_MEMORY, MAX_MEMORY), true);
        memorySliderWidget.addId("memory-slider");
        horizontalListWidget.addEntry(memorySliderWidget);
        horizontalListWidget.addEntry(this.memorySliderLabelWidget);
        HorizontalListWidget controlsContainerWidget = new HorizontalListWidget();
        controlsContainerWidget.addId("controls-container");
        ButtonWidget cancelButtonWidget = ButtonWidget.i18n("labymod.ui.button.cancel");
        cancelButtonWidget.setPressable(this::displayPreviousScreen);
        controlsContainerWidget.addEntry(cancelButtonWidget);
        ButtonWidget quitGameButtonWidget = ButtonWidget.i18n("labymod.ui.button.quit");
        quitGameButtonWidget.setPressable(() -> {
            killLauncher(launcher);
        });
        controlsContainerWidget.addEntry(quitGameButtonWidget);
        containerWidget.addChild(controlsContainerWidget);
        ((Document) super.document()).addChild(containerWidget);
    }

    @Override // net.labymod.api.client.gui.screen.widget.action.SliderInteraction
    public void updateValue(float value) {
        this.value = value;
        if (this.memorySliderLabelWidget != null) {
            this.memorySliderLabelWidget.setText("= " + (this.value / 1024.0f) + "GB");
        }
    }

    private void killLauncher(MinecraftLauncher launcher) {
        try {
            if (!launcher.kill()) {
                onLauncherProfileUpdateFailed("labymod.activity.memoryUpgrade.launcherCouldNotClosed", true, new Component[0]);
                return;
            }
            if (updateLauncherProfile(launcher.getDirectory(), (long) this.value)) {
                Laby.labyAPI().minecraft().shutdownGame();
            } else {
                String profile = Laby.labyAPI().labyModLoader().getProfile();
                onLauncherProfileUpdateFailed("labymod.activity.memoryUpgrade.profileNotFound", true, Component.text(profile == null ? "unknown" : profile, NamedTextColor.YELLOW));
            }
        } catch (IOException exception) {
            onLauncherProfileUpdateFailed(exception.getMessage(), false, new Component[0]);
        }
    }

    private void onLauncherProfileUpdateFailed(String errorMessage, boolean translatable, Component... args) {
        Notification notification = Notification.builder().title(Component.translatable(GarbageCollectorLagDetectionModule.OUT_OF_MEMORY_TITLE_KEY, new Component[0])).text(translatable ? Component.translatable(errorMessage, args) : Component.text(errorMessage)).type(Notification.Type.SYSTEM).build();
        Laby.references().notificationController().push(notification);
    }

    private boolean updateLauncherProfile(File directory, long mb) {
        return updateLauncherProfile(directory.toPath(), mb);
    }

    private boolean updateLauncherProfile(Path directory, long mb) {
        Path launcherProfilesPath = directory.resolve("launcher_profiles.json");
        if (Files.notExists(launcherProfilesPath, new LinkOption[0])) {
            return false;
        }
        try {
            Reader reader = Files.newBufferedReader(launcherProfilesPath);
            try {
                JsonElement profilesElement = (JsonElement) GsonUtil.DEFAULT_GSON.fromJson(reader, JsonElement.class);
                if (updateLauncherProfile(profilesElement, mb)) {
                    Files.write(launcherProfilesPath, GsonUtil.DEFAULT_GSON.toJson(profilesElement).getBytes(StandardCharsets.UTF_8), new OpenOption[0]);
                    if (reader != null) {
                        reader.close();
                    }
                    return true;
                }
                if (reader != null) {
                    reader.close();
                }
                return false;
            } finally {
            }
        } catch (IOException exception) {
            exception.printStackTrace();
            return false;
        }
        exception.printStackTrace();
        return false;
    }

    private boolean updateLauncherProfile(JsonElement element, long mb) {
        if (!element.isJsonObject()) {
            return false;
        }
        JsonObject object = element.getAsJsonObject();
        if (!object.has(PROFILES_KEY)) {
            return false;
        }
        JsonElement profilesElement = object.get(PROFILES_KEY);
        if (!profilesElement.isJsonObject()) {
            return false;
        }
        JsonObject profilesObject = profilesElement.getAsJsonObject();
        String currentProfile = Laby.labyAPI().labyModLoader().getProfile();
        if (!profilesObject.has(currentProfile)) {
            return false;
        }
        JsonElement profileElement = profilesObject.get(currentProfile);
        if (!profileElement.isJsonObject()) {
            return false;
        }
        JsonObject profileObject = profileElement.getAsJsonObject();
        if (profileObject.has(JAVA_ARGS_KEY)) {
            JsonElement javaArgsElement = profileObject.get(JAVA_ARGS_KEY);
            if (!javaArgsElement.isJsonPrimitive()) {
                return false;
            }
            String arguments = javaArgsElement.getAsString();
            profileObject.addProperty(JAVA_ARGS_KEY, arguments.replaceAll("-[x|X][m|M][x|X][0-9]+\\w+", "-Xmx" + mb + "M"));
            return true;
        }
        profileObject.addProperty(JAVA_ARGS_KEY, "-Xmx" + mb + "M");
        return true;
    }
}
