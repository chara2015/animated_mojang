package net.minecraft.client.gui.screens.advancements;

import net.minecraft.advancements.AdvancementType;
import net.minecraft.resources.Identifier;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/advancements/AdvancementWidgetType.class */
public enum AdvancementWidgetType {
    OBTAINED(Identifier.withDefaultNamespace("advancements/box_obtained"), Identifier.withDefaultNamespace("advancements/task_frame_obtained"), Identifier.withDefaultNamespace("advancements/challenge_frame_obtained"), Identifier.withDefaultNamespace("advancements/goal_frame_obtained")),
    UNOBTAINED(Identifier.withDefaultNamespace("advancements/box_unobtained"), Identifier.withDefaultNamespace("advancements/task_frame_unobtained"), Identifier.withDefaultNamespace("advancements/challenge_frame_unobtained"), Identifier.withDefaultNamespace("advancements/goal_frame_unobtained"));

    private final Identifier boxSprite;
    private final Identifier taskFrameSprite;
    private final Identifier challengeFrameSprite;
    private final Identifier goalFrameSprite;

    AdvancementWidgetType(Identifier $$0, Identifier $$1, Identifier $$2, Identifier $$3) {
        this.boxSprite = $$0;
        this.taskFrameSprite = $$1;
        this.challengeFrameSprite = $$2;
        this.goalFrameSprite = $$3;
    }

    public Identifier boxSprite() {
        return this.boxSprite;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public Identifier frameSprite(AdvancementType $$0) throws MatchException {
        switch ($$0) {
            case TASK:
                return this.taskFrameSprite;
            case CHALLENGE:
                return this.challengeFrameSprite;
            case GOAL:
                return this.goalFrameSprite;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }
}
