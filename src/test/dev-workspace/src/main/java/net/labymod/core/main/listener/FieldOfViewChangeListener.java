package net.labymod.core.main.listener;

import java.util.function.Predicate;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.player.Player;
import net.labymod.api.client.options.MinecraftOptions;
import net.labymod.api.client.options.Perspective;
import net.labymod.api.client.world.effect.PotionEffect;
import net.labymod.api.client.world.item.ItemStack;
import net.labymod.api.client.world.item.VanillaItems;
import net.labymod.api.configuration.labymod.main.laby.ingame.FieldOfViewConfig;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.entity.player.FieldOfViewEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewModifierEvent;
import net.labymod.api.event.client.entity.player.FieldOfViewTickEvent;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.api.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/main/listener/FieldOfViewChangeListener.class */
@Singleton
public class FieldOfViewChangeListener {
    private static final String SLOWNESS_TRANSLATION_KEY = "effect.minecraft.slowness";
    private static final String SPEED_TRANSLATION_KEY = "effect.minecraft.speed";
    private static final float NORMAL_WALKING_SPEED = 0.1f;
    private static final float DEFAULT_SPRINTING_AMOUNT = 0.3f;
    private static final boolean OLD_FLUID_HANDLING = MinecraftVersions.V1_16_5.orOlder();
    private static final float DEFAULT_SLOWNESS_AMOUNT = -0.15f;
    private static final float DEFAULT_SPEED_AMOUNT = 0.2f;
    private static final float DEFAULT_FLYING_MULTIPLIER = 1.1f;
    private static final float DEFAULT_BOW_MULTIPLIER = 0.15f;
    private static final float DEFAULT_FLUID_VALUE = 1.0f;
    private static final float DEFAULT_FLUID_PRE_VALUE = 0.85714287f;
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final FieldOfViewConfig config = this.labyAPI.config().ingame().fieldOfView();
    private float oldModifiedFov;
    private float modifiedFov;

    @Inject
    public FieldOfViewChangeListener() {
    }

    @Subscribe(-64)
    public void onFieldOfView(FieldOfViewEvent event) {
        if (!event.isUseFieldOfViewSetting() || !this.config.enabled().get().booleanValue()) {
            return;
        }
        float fov = this.config.vanilla().get().floatValue();
        boolean overwritten = false;
        FieldOfViewEvent.FogType fogType = event.getFogType();
        switch (fogType) {
            case LAVA:
                if (this.config.overwriteLavaFov().get().booleanValue()) {
                    fov = this.config.lavaFov().get().floatValue();
                    overwritten = true;
                }
                break;
            case POWDER_SNOW:
                if (this.config.overwriteSnowPowderFov().get().booleanValue()) {
                    fov = this.config.snowPowderFov().get().floatValue();
                    overwritten = true;
                }
                break;
            case WATER:
                if (this.config.overwriteWaterFov().get().booleanValue()) {
                    fov = this.config.waterFov().get().floatValue();
                    overwritten = true;
                }
                break;
        }
        float fov2 = fov * MathHelper.lerp(this.modifiedFov, this.oldModifiedFov, event.getPartialTicks());
        if (!overwritten && fogType.isFluid()) {
            if (OLD_FLUID_HANDLING) {
                fov2 = (fov2 * 60.0f) / 70.0f;
            } else {
                float fovEffectScale = (float) this.labyAPI.minecraft().options().getFovEffectScale();
                fov2 *= MathHelper.lerp(DEFAULT_FLUID_VALUE, DEFAULT_FLUID_PRE_VALUE, fovEffectScale);
            }
        }
        event.setModifiedFieldOfView(fov2);
    }

    @Subscribe(-64)
    public void onFieldOfViewTick(FieldOfViewTickEvent event) {
        if (!this.config.enabled().get().booleanValue()) {
            return;
        }
        this.oldModifiedFov = event.getOldFov();
        this.modifiedFov = event.getFov();
    }

    @Subscribe(-64)
    public void onFieldOfViewModifier(FieldOfViewModifierEvent event) {
        if (!this.config.enabled().get().booleanValue()) {
            return;
        }
        Entity cameraEntity = this.labyAPI.minecraft().getCameraEntity();
        if (!(cameraEntity instanceof Player)) {
            return;
        }
        Player player = (Player) cameraEntity;
        float fov = calculateDefaultFieldOfView(player);
        event.setFieldOfView(calculateItemFieldOfView(player, fov));
    }

    private float calculateDefaultFieldOfView(Player player) {
        float fov = 1.0f;
        if (player.abilities().flying().get()) {
            fov = DEFAULT_FLUID_VALUE * getValue(this.config.flyingOverwrite(), this.config.flyingMultiplier(), DEFAULT_FLYING_MULTIPLIER);
        }
        float walkingSpeed = player.abilities().walkingSpeed().get();
        float movementSpeed = 0.1f;
        boolean overwriteMovementSpeed = false;
        boolean dynamicSprint = this.config.dynamicSprint().get().booleanValue();
        if (!dynamicSprint && player.isSprinting()) {
            overwriteMovementSpeed = true;
        }
        String str = SLOWNESS_TRANSLATION_KEY;
        Float slownessPotionEffectAmount = getPotionEffectAmount(DEFAULT_SLOWNESS_AMOUNT, player, (v1) -> {
            return r3.equals(v1);
        });
        String str2 = SPEED_TRANSLATION_KEY;
        Float speedPotionEffectAmount = getPotionEffectAmount(0.2f, player, (v1) -> {
            return r3.equals(v1);
        });
        boolean isAffectedBySpeedOrSlowness = (slownessPotionEffectAmount == null && speedPotionEffectAmount == null) ? false : true;
        boolean dynamicStatusEffect = this.config.dynamicStatusEffect().get().booleanValue();
        if (!dynamicStatusEffect && isAffectedBySpeedOrSlowness) {
            overwriteMovementSpeed = true;
        }
        float sprintingAmount = getValue(this.config.sprintingOverwrite(), this.config.sprintingMultiplier(), DEFAULT_SPRINTING_AMOUNT);
        if (overwriteMovementSpeed) {
            if (dynamicSprint && player.isSprinting()) {
                movementSpeed = applyEffectModifier(NORMAL_WALKING_SPEED, sprintingAmount);
            }
            if (dynamicStatusEffect && isAffectedBySpeedOrSlowness) {
                if (slownessPotionEffectAmount != null) {
                    movementSpeed = applyEffectModifier(movementSpeed, slownessPotionEffectAmount.floatValue());
                }
                if (speedPotionEffectAmount != null) {
                    movementSpeed = applyEffectModifier(movementSpeed, speedPotionEffectAmount.floatValue());
                }
                if (dynamicSprint && player.isSprinting()) {
                    movementSpeed = applyEffectModifier(movementSpeed, sprintingAmount);
                }
            }
        } else {
            if (isAffectedBySpeedOrSlowness) {
                if (slownessPotionEffectAmount != null) {
                    movementSpeed = applyEffectModifier(NORMAL_WALKING_SPEED, getValue(this.config.slownessOverwrite(), this.config.slownessMultiplier(), slownessPotionEffectAmount.floatValue()));
                }
                if (speedPotionEffectAmount != null) {
                    movementSpeed = applyEffectModifier(movementSpeed, getValue(this.config.speedOverwrite(), this.config.speedMultiplier(), speedPotionEffectAmount.floatValue()));
                }
            }
            if (player.isSprinting()) {
                movementSpeed = applyEffectModifier(movementSpeed, sprintingAmount);
            }
        }
        float fov2 = fov * (((movementSpeed / walkingSpeed) + DEFAULT_FLUID_VALUE) / 2.0f);
        if (isFieldOfViewValid(walkingSpeed, fov2)) {
            fov2 = 1.0f;
        }
        return fov2;
    }

    private float calculateItemFieldOfView(Player player, float fov) {
        float power;
        MinecraftOptions options = this.labyAPI.minecraft().options();
        ItemStack usedItemHand = player.getUsedItemStack();
        boolean usingItem = options.useItemInput().isDown();
        if (!usingItem) {
            return fov;
        }
        if (usedItemHand.isItem(VanillaItems.BOW)) {
            if (this.config.dynamicBow().get().booleanValue()) {
                int ticks = player.getItemUseDurationTicks();
                float power2 = ticks / 20.0f;
                if (power2 > DEFAULT_FLUID_VALUE) {
                    power = 1.0f;
                } else {
                    power = power2 * power2;
                }
                fov *= DEFAULT_FLUID_VALUE - (power * getValue(this.config.bowPowerOverwrite(), this.config.bowPowerMultiplier(), DEFAULT_BOW_MULTIPLIER));
            }
        } else if (options.perspective() == Perspective.FIRST_PERSON && usedItemHand.isItem(VanillaItems.SPYGLASS)) {
            fov = 0.1f;
        }
        return fov;
    }

    private boolean isFieldOfViewValid(float walking, float fov) {
        return walking == 0.0f || Float.isNaN(fov) || Float.isInfinite(fov);
    }

    private float getValue(ConfigProperty<Boolean> overwrite, ConfigProperty<Float> value, float defValue) {
        return getValue(overwrite.get().booleanValue(), value.get().floatValue(), defValue);
    }

    private float getValue(boolean overwrite, float value, float defValue) {
        return overwrite ? value : defValue;
    }

    @Nullable
    private Float getPotionEffectAmount(float baseAmount, Player player, Predicate<String> filter) {
        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            String translationKey = activePotionEffect.getTranslationKey();
            if (filter.test(translationKey)) {
                return Float.valueOf(calculateAmount(baseAmount, activePotionEffect.getAmplifier() + 1));
            }
        }
        return null;
    }

    private float calculateAmount(float baseAmount, float multiplier) {
        return baseAmount * multiplier;
    }

    public float applyEffectModifier(float value, float amount) {
        return value * (DEFAULT_FLUID_VALUE + amount);
    }
}
