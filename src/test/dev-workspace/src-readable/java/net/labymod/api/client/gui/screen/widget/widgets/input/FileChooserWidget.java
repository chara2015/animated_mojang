package net.labymod.api.client.gui.screen.widget.widgets.input;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.gui.mouse.MutableMouse;
import net.labymod.api.client.gui.screen.Parent;
import net.labymod.api.client.gui.screen.key.MouseButton;
import net.labymod.api.client.gui.screen.widget.Widget;
import net.labymod.api.client.gui.screen.widget.action.Selectable;
import net.labymod.api.configuration.settings.Setting;
import net.labymod.api.configuration.settings.SettingInfo;
import net.labymod.api.configuration.settings.accessor.SettingAccessor;
import net.labymod.api.configuration.settings.annotation.SettingElement;
import net.labymod.api.configuration.settings.annotation.SettingFactory;
import net.labymod.api.configuration.settings.annotation.SettingWidget;
import net.labymod.api.configuration.settings.widget.WidgetFactory;
import net.labymod.api.util.I18n;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/FileChooserWidget.class */
@AutoWidget
@SettingWidget
public class FileChooserWidget extends ButtonWidget {
    private final Selectable<Path> selectable;
    private final String[] extensions;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/FileChooserWidget$FileChooserSetting.class */
    @SettingElement
    @Target({ElementType.FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface FileChooserSetting {
        String[] extensions() default {};
    }

    public FileChooserWidget(Selectable<Path> selectable) {
        this(selectable, null);
    }

    public FileChooserWidget(Selectable<Path> selectable, String[] extensions) {
        super(Component.translatable("labymod.ui.fileChooser.select", new Component[0]), null);
        this.selectable = selectable;
        this.extensions = extensions;
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.Interactable
    public void initialize(Parent parent) {
        super.initialize(parent);
        if (FILE_DROP_COMPATIBILITY.isCompatible(this.labyAPI.labyModLoader().version())) {
            setHoverComponent(Component.translatable("labymod.ui.fileChooser.description", new Component[0]));
        }
    }

    @Override // net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean fileDropped(MutableMouse mouse, List<Path> paths) {
        if (!paths.isEmpty()) {
            Path path = paths.get(0);
            if (this.extensions != null && this.extensions.length != 0) {
                String fileName = path.getFileName().toString();
                if (Arrays.stream(this.extensions).noneMatch(ext -> {
                    return fileName.endsWith("." + ext);
                })) {
                    return super.fileDropped(mouse, paths);
                }
            }
            this.selectable.select(path);
            return true;
        }
        return super.fileDropped(mouse, paths);
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.element.CompositeElement, net.labymod.api.client.gui.MouseUser
    public boolean mouseClicked(MutableMouse mouse, MouseButton mouseButton) {
        if (mouseButton == MouseButton.LEFT) {
            openDialog();
        }
        return super.mouseClicked(mouse, mouseButton);
    }

    private void openDialog() {
        Laby.references().fileDialogs().open(I18n.translate("labymod.ui.fileChooser.title", new Object[0]), null, null, this.extensions, false, paths -> {
            this.selectable.select((paths == null || paths.length == 0) ? null : paths[0]);
        });
    }

    @Override // net.labymod.api.client.gui.screen.widget.widgets.input.ButtonWidget, net.labymod.api.client.gui.screen.widget.AbstractWidget
    public boolean isHoverComponentRendered() {
        return hasHoverComponent() ? super.isHoverComponentRendered() : isHovered();
    }

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/input/FileChooserWidget$Factory.class */
    @SettingFactory
    public static class Factory implements WidgetFactory<FileChooserSetting, FileChooserWidget> {
        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public /* bridge */ /* synthetic */ Widget[] create(Setting setting, Annotation annotation, SettingInfo settingInfo, SettingAccessor settingAccessor) {
            return create(setting, (FileChooserSetting) annotation, (SettingInfo<?>) settingInfo, settingAccessor);
        }

        public FileChooserWidget[] create(Setting setting, FileChooserSetting annotation, SettingInfo<?> info, SettingAccessor accessor) {
            return new FileChooserWidget[]{new FileChooserWidget(value -> {
                Laby.labyAPI().minecraft().executeOnRenderThread(() -> {
                    accessor.set(value.toAbsolutePath());
                });
            }, annotation.extensions())};
        }

        @Override // net.labymod.api.configuration.settings.widget.WidgetFactory
        public Class<?>[] types() {
            return new Class[]{Path.class};
        }
    }
}
