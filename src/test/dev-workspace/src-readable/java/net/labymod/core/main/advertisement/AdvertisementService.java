package net.labymod.core.main.advertisement;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.service.FetchService;
import net.labymod.api.util.io.web.exception.WebRequestException;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.resources.SplashLoader;
import net.labymod.core.main.advertisement.model.SplashDate;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/advertisement/AdvertisementService.class */
@Singleton
@Referenceable
public class AdvertisementService extends FetchService<Advertisement> {
    private static final Logging LOGGER = Logging.getLogger();
    private final Map<String, Runnable> resolveListeners;
    private Advertisement advertisement;

    public AdvertisementService() {
        super(Constants.LegacyUrls.ADVERTISEMENT_DATA, Advertisement.class);
        this.resolveListeners = new ConcurrentHashMap();
    }

    @Override // net.labymod.api.service.Service
    public void onServiceCompleted() {
        super.onServiceCompleted();
        registerSplashes();
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void success(Advertisement result) throws Exception {
        this.advertisement = result;
        executeResolveListeners();
    }

    @Override // net.labymod.api.util.io.web.WebResponse
    public void failed(WebRequestException exception) {
        LOGGER.error("Failed to fetch the advertisement data.", exception);
    }

    @Nullable
    public Advertisement getAdvertisement() {
        return this.advertisement;
    }

    public void subscribeToAdvertisement(String key, Runnable resolveListener) {
        this.resolveListeners.put(key, resolveListener);
    }

    private void executeResolveListeners() {
        for (Map.Entry<String, Runnable> entry : this.resolveListeners.entrySet()) {
            String key = entry.getKey();
            Runnable resolveListener = entry.getValue();
            if (resolveListener != null) {
                try {
                    resolveListener.run();
                } catch (Throwable throwable) {
                    LOGGER.error("Failed to execute the resolve listener for '" + key + "'.", throwable);
                }
            }
        }
    }

    private void registerSplashes() {
        Advertisement advertisement = getAdvertisement();
        if (advertisement == null) {
            return;
        }
        for (SplashDate splashDate : advertisement.splashDates()) {
            registerSplash(splashDate);
        }
    }

    private void registerSplash(SplashDate date) {
        String text = date.displayString();
        if (date.birthday()) {
            text = "Happy Birthday, " + text + "!";
        }
        SplashLoader.INSTANCE.registerSplashDate(date.month(), date.day(), text);
    }
}
