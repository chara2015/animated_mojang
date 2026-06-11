package net.labymod.core.labynet.insight.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;
import javax.inject.Singleton;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.screen.widget.widgets.PopupWidget;
import net.labymod.api.client.screenshot.ScreenshotUtil;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.TokenStorage;
import net.labymod.api.reference.annotation.Referenceable;
import net.labymod.api.util.GsonUtil;
import net.labymod.api.util.io.LabyExecutors;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.client.screenshot.meta.ScreenshotMeta;
import net.labymod.core.labynet.exception.ScreenshotException;
import net.labymod.core.labynet.insight.model.ScreenshotInsight;
import net.labymod.core.labynet.insight.util.ImageCodec;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/labynet/insight/controller/InsightUploader.class */
@Singleton
@Referenceable
public class InsightUploader {
    private static final Logging LOGGER = Logging.create((Class<?>) InsightUploader.class);
    private static final String[] FORMATS = {"avif", "webp", "png", "jpeg"};
    private final ExecutorService executor = LabyExecutors.newSingleThreadExecutor("Insight#%d");

    public void uploadAsync(Path file, Consumer<String> successCallback, Consumer<Exception> errorCallback) {
        PopupWidget.builder().title(Component.translatable("labymod.activity.screenshotBrowser.viewer.upload.warning", new Component[0])).confirmCallback(() -> {
            this.executor.execute(() -> {
                try {
                    String url = upload(file);
                    successCallback.accept(url);
                } catch (Exception e) {
                    errorCallback.accept(e);
                }
            });
        }).cancelCallback(() -> {
            errorCallback.accept(null);
        }).build().displayInOverlay();
    }

    private String upload(Path file) throws Exception {
        InputStream inputStream;
        ScreenshotMeta meta = new ScreenshotMeta(file);
        if (!meta.hasInsight()) {
            throw new ScreenshotException("Not an insight screenshot");
        }
        ScreenshotInsight insight = meta.getInsight();
        if (insight == null) {
            throw new ScreenshotException("Insight is corrupted");
        }
        LabyConnectSession session = Laby.labyAPI().labyConnect().getSession();
        if (session == null) {
            throw new ScreenshotException("Not connected to LabyConnect");
        }
        TokenStorage.Token token = session.tokenStorage().getToken(TokenStorage.Purpose.CLIENT, session.self().getUniqueId());
        if (token == null || token.isExpired()) {
            throw new ScreenshotException("Access token expired");
        }
        HttpURLConnection connection = (HttpURLConnection) new URL(Constants.Urls.LABYNET_SCREENSHOT_UPLOAD).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Authorization", "Client " + token.getToken());
        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        try {
            try {
                ImageCodec codec = new ImageCodec(file);
                codec.setImagePostProcessor(image -> {
                    return ScreenshotUtil.maxSize(image, 1920, 1080);
                });
                String format = ImageCodec.getAvailableFormat(FORMATS);
                byte[] imageBytes = codec.compile(format);
                dos.writeInt(imageBytes.length);
                dos.write(imageBytes);
                JsonObject object = insight.toJsonObject();
                object.addProperty("format", format);
                String json = object.toString();
                int length = json.length();
                dos.writeInt(length);
                for (int i = 0; i < length; i++) {
                    char character = json.charAt(i);
                    dos.writeByte(character > 127 ? (byte) 63 : (byte) character);
                }
                dos.close();
                StringBuilder jsonResponse = new StringBuilder();
                if (connection.getResponseCode() / 100 != 2) {
                    inputStream = connection.getErrorStream();
                    while (true) {
                        try {
                            int read = inputStream.read();
                            if (read == -1) {
                                break;
                            }
                            jsonResponse.append((char) read);
                        } finally {
                        }
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    try {
                        String json2 = jsonResponse.toString();
                        JsonObject response = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(json2, JsonObject.class);
                        if (response.has("error")) {
                            throw new ScreenshotException(response.get("error").getAsString());
                        }
                        throw new ScreenshotException(json2);
                    } catch (JsonSyntaxException e) {
                        LOGGER.info("Failed to parse error response: " + String.valueOf(jsonResponse), new Object[0]);
                        throw new ScreenshotException("Unknown error", connection.getResponseCode());
                    }
                }
                inputStream = connection.getInputStream();
                while (inputStream != null) {
                    try {
                        int i2 = inputStream.read();
                        if (i2 == -1) {
                            break;
                        }
                        jsonResponse.append((char) i2);
                    } finally {
                    }
                }
                if (inputStream != null) {
                    inputStream.close();
                }
                try {
                    JsonObject response2 = (JsonObject) GsonUtil.DEFAULT_GSON.fromJson(jsonResponse.toString(), JsonObject.class);
                    if (!response2.has("url")) {
                        throw new ScreenshotException("No url given in response");
                    }
                    return response2.get("url").getAsString();
                } catch (Exception e2) {
                    throw new ScreenshotException("Invalid response: " + String.valueOf(jsonResponse));
                }
            } catch (Throwable e3) {
                throw new ScreenshotException("Failed to encode image: " + e3.getMessage());
            }
        } catch (Throwable th) {
            try {
                dos.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}
