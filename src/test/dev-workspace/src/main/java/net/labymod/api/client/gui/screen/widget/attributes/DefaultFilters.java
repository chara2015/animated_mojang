package net.labymod.api.client.gui.screen.widget.attributes;

import java.util.function.Function;
import net.labymod.api.Laby;
import net.labymod.api.client.render.draw.BlurRenderer;
import net.labymod.api.generated.ReferenceStorage;
import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/gui/screen/widget/attributes/DefaultFilters.class */
public final class DefaultFilters {
    private static final Logging LOGGER = Logging.getLogger();
    public static final BlurRenderer BLUR_RENDERER = (BlurRenderer) createFilter("Blur Renderer", (v0) -> {
        return v0.blurRenderer();
    });

    public static <T> T createFilter(String name, Function<ReferenceStorage, T> factory) {
        try {
            return factory.apply(Laby.references());
        } catch (Throwable throwable) {
            LOGGER.error("Unable to create \"{}\"", name, throwable);
            return null;
        }
    }
}
