package net.minecraft.client.gui.screens.recipebook;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.world.entity.player.StackedItemContents;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.RecipeDisplayEntry;
import net.minecraft.world.item.crafting.display.RecipeDisplayId;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/recipebook/RecipeCollection.class */
public class RecipeCollection {
    public static final RecipeCollection EMPTY = new RecipeCollection(List.of());
    private final List<RecipeDisplayEntry> entries;
    private final Set<RecipeDisplayId> craftable = new HashSet();
    private final Set<RecipeDisplayId> selected = new HashSet();

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/client/gui/screens/recipebook/RecipeCollection$CraftableStatus.class */
    public enum CraftableStatus {
        ANY,
        CRAFTABLE,
        NOT_CRAFTABLE
    }

    public RecipeCollection(List<RecipeDisplayEntry> $$0) {
        this.entries = $$0;
    }

    public void selectRecipes(StackedItemContents $$0, Predicate<RecipeDisplay> $$1) {
        for (RecipeDisplayEntry $$2 : this.entries) {
            boolean $$3 = $$1.test($$2.display());
            if ($$3) {
                this.selected.add($$2.id());
            } else {
                this.selected.remove($$2.id());
            }
            if ($$3 && $$2.canCraft($$0)) {
                this.craftable.add($$2.id());
            } else {
                this.craftable.remove($$2.id());
            }
        }
    }

    public boolean isCraftable(RecipeDisplayId $$0) {
        return this.craftable.contains($$0);
    }

    public boolean hasCraftable() {
        return !this.craftable.isEmpty();
    }

    public boolean hasAnySelected() {
        return !this.selected.isEmpty();
    }

    public List<RecipeDisplayEntry> getRecipes() {
        return this.entries;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: java.lang.MatchException */
    public List<RecipeDisplayEntry> getSelectedRecipes(CraftableStatus $$0) throws MatchException {
        Predicate<RecipeDisplayId> predicate;
        switch ($$0) {
            case ANY:
                Set<RecipeDisplayId> set = this.selected;
                Objects.requireNonNull(set);
                predicate = (v1) -> {
                    return r0.contains(v1);
                };
                break;
            case CRAFTABLE:
                Set<RecipeDisplayId> set2 = this.craftable;
                Objects.requireNonNull(set2);
                predicate = (v1) -> {
                    return r0.contains(v1);
                };
                break;
            case NOT_CRAFTABLE:
                predicate = $$02 -> {
                    return this.selected.contains($$02) && !this.craftable.contains($$02);
                };
                break;
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
        Predicate<RecipeDisplayId> $$1 = predicate;
        List<RecipeDisplayEntry> $$2 = new ArrayList<>();
        for (RecipeDisplayEntry $$3 : this.entries) {
            if ($$1.test($$3.id())) {
                $$2.add($$3);
            }
        }
        return $$2;
    }
}
