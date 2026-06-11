package net.labymod.api.addon.entrypoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.labymod.api.swing.progress.ProgressWindow;
import net.labymod.core.client.render.schematic.block.ParameterType;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/addon/entrypoint/EntrypointProgress.class */
public final class EntrypointProgress implements AutoCloseable {
    private static final Logger LOGGER = Logger.getLogger(EntrypointProgress.class.getName());
    private static final Gson GSON = new Gson();
    private final Process process;
    private final BufferedWriter writer;
    private boolean processAlive = true;

    private EntrypointProgress(Process process) {
        this.process = process;
        this.writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8));
    }

    public static EntrypointProgress open(String title) throws IOException {
        Process process = EntrypointProcessBuilder.create((Class<?>) ProgressWindow.class).classpathFromClass(Gson.class).programArgument(title).start();
        return new EntrypointProgress(process);
    }

    public void title(String title) {
        sendText("title", title);
    }

    public void status(String text) {
        sendText("status", text);
    }

    public void progress(float value) {
        sendValue("progress", value);
    }

    public void indeterminate() {
        sendSignal("indeterminate");
    }

    public void overallStatus(String text) {
        sendText("overall_status", text);
    }

    public void overallProgress(float value) {
        sendValue("overall_progress", value);
    }

    public void overallIndeterminate() {
        sendSignal("overall_indeterminate");
    }

    public Process process() {
        return this.process;
    }

    public boolean isAlive() {
        return this.process.isAlive();
    }

    @Override // java.lang.AutoCloseable
    public void close() {
        if (!this.process.isAlive()) {
            return;
        }
        try {
            sendSignal("close");
            this.writer.close();
            this.process.waitFor();
        } catch (IOException e) {
            this.process.destroyForcibly();
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            this.process.destroyForcibly();
        }
    }

    private void sendSignal(String type) {
        JsonObject message = new JsonObject();
        message.addProperty(ParameterType.TYPE, type);
        send(message);
    }

    private void sendText(String type, String text) {
        JsonObject message = new JsonObject();
        message.addProperty(ParameterType.TYPE, type);
        message.addProperty("text", text);
        send(message);
    }

    private void sendValue(String type, float value) {
        JsonObject message = new JsonObject();
        message.addProperty(ParameterType.TYPE, type);
        message.addProperty("value", Float.valueOf(value));
        send(message);
    }

    private void send(JsonObject message) {
        if (!this.processAlive) {
            return;
        }
        if (!this.process.isAlive()) {
            this.processAlive = false;
            LOGGER.log(Level.WARNING, "Progress window process exited unexpectedly (exit code: {0})", Integer.valueOf(this.process.exitValue()));
            return;
        }
        try {
            GSON.toJson(message, this.writer);
            this.writer.newLine();
            this.writer.flush();
        } catch (IOException exception) {
            this.processAlive = false;
            LOGGER.log(Level.WARNING, "Failed to send message to progress window", (Throwable) exception);
        }
    }
}
