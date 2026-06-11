package net.labymod.core.client.waila.tooltip.entity;

import java.util.Locale;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.entity.Entity;
import net.labymod.api.client.entity.passive.villager.Villager;
import net.labymod.api.client.entity.passive.villager.VillagerJob;
import net.labymod.api.loader.MinecraftVersions;
import net.labymod.core.client.gui.hud.hudwidget.survival.WailaHudWidget;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/waila/tooltip/entity/VillagerProfessionWailaTooltip.class */
public class VillagerProfessionWailaTooltip extends ComponentEntityWailaTooltip {
    private static final boolean LEGACY_VERSION = MinecraftVersions.V1_12_2.orOlder();
    private static final String MODERN_TRANSLATION_KEY = "entity.minecraft.villager.%s";
    private static final String LEGACY_TRANSLATION_KEY = "entity.Villager.%s";
    private final WailaHudWidget widget;

    public VillagerProfessionWailaTooltip(WailaHudWidget widget) {
        super("villager/profession");
        this.widget = widget;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.core.client.waila.tooltip.entity.ComponentEntityWailaTooltip
    @Nullable
    protected Component createComponent(Entity entity) {
        if (!((WailaHudWidget.WailaConfiguration) this.widget.getConfig()).entityVillagerProfession().get().booleanValue() || !(entity instanceof Villager)) {
            return null;
        }
        Villager villager = (Villager) entity;
        VillagerJob job = villager.job();
        String jobName = job.name();
        return Component.translatable(getTranslationId(jobName), new Component[0]).append(Component.space()).append(Component.text("(")).append(Component.translatable(job.levelDescriptionId(), new Component[0])).append(Component.text(")"));
    }

    private String getTranslationId(String name) {
        String str;
        Locale locale = Locale.ROOT;
        if (LEGACY_VERSION) {
            str = LEGACY_TRANSLATION_KEY;
        } else {
            str = MODERN_TRANSLATION_KEY;
        }
        return String.format(locale, str, name);
    }
}
