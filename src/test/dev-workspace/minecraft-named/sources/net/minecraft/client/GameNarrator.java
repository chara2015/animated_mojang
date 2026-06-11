package net.minecraft.client;

import com.mojang.logging.LogUtils;
import com.mojang.text2speech.Narrator;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.components.toasts.SystemToast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.main.SilentInitException;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Crypt;
import net.minecraft.util.profiling.jfr.JfrProfiler;
import org.lwjgl.util.tinyfd.TinyFileDialogs;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/GameNarrator.class */
public class GameNarrator {
    public static final Component NO_TITLE = CommonComponents.EMPTY;
    private static final Logger LOGGER = LogUtils.getLogger();
    private final Minecraft minecraft;
    private final Narrator narrator = Narrator.getNarrator();

    public GameNarrator(Minecraft $$0) {
        this.minecraft = $$0;
    }

    public void sayChatQueued(Component $$0) {
        if (getStatus().shouldNarrateChat()) {
            narrateNotInterruptingMessage($$0);
        }
    }

    public void saySystemChatQueued(Component $$0) {
        if (getStatus().shouldNarrateSystemOrChat()) {
            narrateNotInterruptingMessage($$0);
        }
    }

    public void saySystemQueued(Component $$0) {
        if (getStatus().shouldNarrateSystem()) {
            narrateNotInterruptingMessage($$0);
        }
    }

    private void narrateNotInterruptingMessage(Component $$0) {
        String $$1 = $$0.getString();
        if (!$$1.isEmpty()) {
            logNarratedMessage($$1);
            narrateMessage($$1, false);
        }
    }

    public void saySystemNow(Component $$0) {
        saySystemNow($$0.getString());
    }

    public void saySystemNow(String $$0) {
        if (getStatus().shouldNarrateSystem() && !$$0.isEmpty()) {
            logNarratedMessage($$0);
            if (this.narrator.active()) {
                this.narrator.clear();
                narrateMessage($$0, true);
            }
        }
    }

    private void narrateMessage(String $$0, boolean $$1) {
        this.narrator.say($$0, $$1, this.minecraft.options.getFinalSoundSourceVolume(SoundSource.VOICE));
    }

    private NarratorStatus getStatus() {
        return this.minecraft.options.narrator().get();
    }

    private void logNarratedMessage(String $$0) {
        if (SharedConstants.IS_RUNNING_IN_IDE) {
            LOGGER.debug("Narrating: {}", $$0.replaceAll(Crypt.MIME_LINE_SEPARATOR, "\\\\n"));
        }
    }

    public void updateNarratorStatus(NarratorStatus $$0) {
        clear();
        narrateMessage(Component.translatable("options.narrator").append(" : ").append($$0.getName()).getString(), true);
        ToastManager $$1 = Minecraft.getInstance().getToastManager();
        if (this.narrator.active()) {
            if ($$0 == NarratorStatus.OFF) {
                SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.translatable("narrator.toast.disabled"), null);
                return;
            } else {
                SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.translatable("narrator.toast.enabled"), $$0.getName());
                return;
            }
        }
        SystemToast.addOrUpdate($$1, SystemToast.SystemToastId.NARRATOR_TOGGLE, Component.translatable("narrator.toast.disabled"), Component.translatable("options.narrator.notavailable"));
    }

    public boolean isActive() {
        return this.narrator.active();
    }

    public void clear() {
        if (getStatus() == NarratorStatus.OFF || !this.narrator.active()) {
            return;
        }
        this.narrator.clear();
    }

    public void destroy() {
        this.narrator.destroy();
    }

    public void checkStatus(boolean $$0) {
        if ($$0 && !isActive() && !TinyFileDialogs.tinyfd_messageBox(JfrProfiler.ROOT_CATEGORY, "Failed to initialize text-to-speech library. Do you want to continue?\nIf this problem persists, please report it at bugs.mojang.com", "yesno", "error", true)) {
            throw new NarratorInitException("Narrator library is not active");
        }
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/GameNarrator$NarratorInitException.class */
    public static class NarratorInitException extends SilentInitException {
        public NarratorInitException(String $$0) {
            super($$0);
        }
    }
}
