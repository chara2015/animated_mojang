package net.labymod.api.client.gui.screen.widget.widgets.renderer;

import net.labymod.api.Laby;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.client.gui.lss.property.LssProperty;
import net.labymod.api.client.gui.lss.property.annotation.AutoWidget;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.resources.texture.TextureDetails;
import net.labymod.api.client.resources.texture.TextureRepository;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/VariableIconWidget.class */
@AutoWidget
public class VariableIconWidget extends IconWidget {
    private final LssProperty<Float> iconHeight;
    private final LssProperty<Float> iconWidth;
    private final ResourceLocation fallbackLocation;
    private final VariableUrlFunction urlSupplier;
    private final String baseUrl;
    private boolean recalculate;
    private int appliedHeight;
    private int appliedWidth;

    /* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/widgets/renderer/VariableIconWidget$VariableUrlFunction.class */
    @FunctionalInterface
    public interface VariableUrlFunction {
        @Nullable
        String get(String str, int i, int i2);
    }

    public VariableIconWidget(ResourceLocation fallbackLocation, String baseUrl, VariableUrlFunction urlSupplier) {
        super(fallbackLocation == null ? null : Icon.texture(fallbackLocation));
        this.iconHeight = new LssProperty<>(Float.valueOf(0.0f));
        this.iconWidth = new LssProperty<>(Float.valueOf(0.0f));
        this.appliedHeight = -1;
        this.appliedWidth = -1;
        this.fallbackLocation = fallbackLocation;
        this.urlSupplier = urlSupplier;
        this.baseUrl = baseUrl;
        this.iconWidth.addChangeListener((p, o, n) -> {
            this.recalculate = true;
        });
        this.iconHeight.addChangeListener((p2, o2, n2) -> {
            this.recalculate = true;
        });
    }

    public VariableIconWidget(String baseUrl, VariableUrlFunction urlSupplier) {
        this(null, baseUrl, urlSupplier);
    }

    @Override // net.labymod.api.client.gui.screen.widget.AbstractWidget, net.labymod.api.client.gui.screen.widget.Widget
    public void postStyleSheetLoad() {
        String url;
        super.postStyleSheetLoad();
        if (isVisible() && this.urlSupplier != null && this.baseUrl != null) {
            this.recalculate = false;
            float scale = this.labyAPI.minecraft().minecraftWindow().getScale();
            int height = (int) (this.iconHeight.get().floatValue() * scale);
            int width = (int) (this.iconWidth.get().floatValue() * scale);
            boolean sizeChanged = false;
            if (height != this.appliedHeight) {
                this.appliedHeight = height;
                sizeChanged = true;
            }
            if (width != this.appliedWidth) {
                this.appliedWidth = width;
                sizeChanged = true;
            }
            if (sizeChanged && (url = this.urlSupplier.get(this.baseUrl, width, height)) != null) {
                Icon icon = icon().get();
                if (icon != null) {
                    TextureRepository repository = Laby.references().textureRepository();
                    ResourceLocation iconLocation = ResourceLocation.create("labymod", "icon/" + url.hashCode());
                    TextureDetails details = TextureDetails.builder(iconLocation).withFallbackLocation(this.fallbackLocation).withUrl(url).build();
                    icon.setResourceLocationSupplier(() -> {
                        return repository.getOrRegisterTexture(details).getCompleted();
                    });
                    return;
                }
                icon().set(Icon.url(url, this.fallbackLocation));
            }
        }
    }

    public LssProperty<Float> iconWidth() {
        return this.iconWidth;
    }

    public LssProperty<Float> iconHeight() {
        return this.iconHeight;
    }
}
