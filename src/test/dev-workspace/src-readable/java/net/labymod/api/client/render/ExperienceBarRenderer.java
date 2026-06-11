package net.labymod.api.client.render;

import net.labymod.api.client.gui.screen.ScreenContext;
import net.labymod.api.reference.annotation.Referenceable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/client/render/ExperienceBarRenderer.class */
@Referenceable
public interface ExperienceBarRenderer {
    ExperienceBarRenderer mode(RenderMode renderMode);

    ExperienceBarRenderer pos(float f, float f2);

    ExperienceBarRenderer experienceNeededForNextLevel(int i);

    ExperienceBarRenderer experienceProgress(float f);

    ExperienceBarRenderer experienceLevel(int i);

    void render(ScreenContext screenContext);

    float getWidth();

    float getHeight();
}
