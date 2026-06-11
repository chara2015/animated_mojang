package net.minecraft.client.sounds;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/sounds/SoundPreviewHandler.class */
public final class SoundPreviewHandler {
    private static SoundInstance activePreview;
    private static SoundSource previousCategory;

    public static void preview(SoundManager $$0, SoundSource $$1, float $$2) {
        SoundEvent soundEventValue;
        stopOtherCategoryPreview($$0, $$1);
        if (canPlaySound($$0)) {
            switch ($$1) {
                case RECORDS:
                    soundEventValue = SoundEvents.NOTE_BLOCK_GUITAR.value();
                    break;
                case WEATHER:
                    soundEventValue = SoundEvents.LIGHTNING_BOLT_THUNDER;
                    break;
                case BLOCKS:
                    soundEventValue = SoundEvents.GRASS_PLACE;
                    break;
                case HOSTILE:
                    soundEventValue = SoundEvents.ZOMBIE_AMBIENT;
                    break;
                case NEUTRAL:
                    soundEventValue = SoundEvents.COW_AMBIENT;
                    break;
                case PLAYERS:
                    soundEventValue = SoundEvents.GENERIC_EAT.value();
                    break;
                case AMBIENT:
                    soundEventValue = SoundEvents.AMBIENT_CAVE.value();
                    break;
                case UI:
                    soundEventValue = SoundEvents.UI_BUTTON_CLICK.value();
                    break;
                default:
                    soundEventValue = SoundEvents.EMPTY;
                    break;
            }
            SoundEvent $$3 = soundEventValue;
            if ($$3 != SoundEvents.EMPTY) {
                activePreview = SimpleSoundInstance.forUI($$3, 1.0f, $$2);
                $$0.play(activePreview);
            }
        }
    }

    private static void stopOtherCategoryPreview(SoundManager $$0, SoundSource $$1) {
        if (previousCategory != $$1) {
            previousCategory = $$1;
            if (activePreview != null) {
                $$0.stop(activePreview);
            }
        }
    }

    private static boolean canPlaySound(SoundManager $$0) {
        return activePreview == null || !$$0.isActive(activePreview);
    }
}
