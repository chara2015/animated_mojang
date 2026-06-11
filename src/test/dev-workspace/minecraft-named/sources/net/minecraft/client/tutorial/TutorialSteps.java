package net.minecraft.client.tutorial;

import java.util.function.Function;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/tutorial/TutorialSteps.class */
public enum TutorialSteps {
    MOVEMENT("movement", MovementTutorialStepInstance::new),
    FIND_TREE("find_tree", FindTreeTutorialStepInstance::new),
    PUNCH_TREE("punch_tree", PunchTreeTutorialStepInstance::new),
    OPEN_INVENTORY("open_inventory", OpenInventoryTutorialStep::new),
    CRAFT_PLANKS("craft_planks", CraftPlanksTutorialStep::new),
    NONE("none", CompletedTutorialStepInstance::new);

    private final String name;
    private final Function<Tutorial, ? extends TutorialStepInstance> constructor;

    TutorialSteps(String $$0, Function function) {
        this.name = $$0;
        this.constructor = function;
    }

    public TutorialStepInstance create(Tutorial $$0) {
        return this.constructor.apply($$0);
    }

    public String getName() {
        return this.name;
    }

    public static TutorialSteps getByName(String $$0) {
        for (TutorialSteps $$1 : values()) {
            if ($$1.name.equals($$0)) {
                return $$1;
            }
        }
        return NONE;
    }
}
