package net.labymod.v1_12_2.client.resources.pack.rich;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.labymod.api.client.resources.pack.rich.PackSelectionModel;
import net.labymod.api.client.resources.pack.rich.ResourcePackDetails;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/v1_12_2/client/resources/pack/rich/VersionedPackSelectionModel.class */
public class VersionedPackSelectionModel implements PackSelectionModel {
    private final ceu repository;
    private final VanillaPackDetails vanillaPack;
    private boolean changed = false;
    private List<ResourcePackDetails> unselected = new ArrayList();
    private List<ResourcePackDetails> selected = new ArrayList();

    public VersionedPackSelectionModel(ceu repository) {
        this.repository = repository;
        this.vanillaPack = new VanillaPackDetails(this.repository.a);
        List<a> unselectedEntries = new ArrayList<>(this.repository.d());
        unselectedEntries.removeAll(this.repository.e());
        for (a entry : unselectedEntries) {
            this.unselected.add(new VersionedPackDetails(entry));
        }
        for (a entry2 : this.repository.e()) {
            this.selected.add(new VersionedPackDetails(entry2));
        }
        this.selected.add(this.vanillaPack);
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public List<ResourcePackDetails> getUnselected() {
        return this.unselected;
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public List<ResourcePackDetails> getSelected() {
        return this.selected;
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public void findNewPacks() {
        this.repository.b();
        List<a> unselectedEntries = this.repository.d();
        unselectedEntries.removeAll(this.repository.e());
        List<ResourcePackDetails> newUnselected = new ArrayList<>();
        for (a entry : unselectedEntries) {
            newUnselected.add(new VersionedPackDetails(entry));
        }
        this.unselected = newUnselected;
        List<ResourcePackDetails> newSelected = new ArrayList<>();
        for (a entry2 : this.repository.e()) {
            newSelected.add(new VersionedPackDetails(entry2));
        }
        newSelected.add(this.vanillaPack);
        this.selected = newSelected;
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public void updatePacks(@NotNull List<ResourcePackDetails> selected, @NotNull List<ResourcePackDetails> unselected) {
        if (!this.changed) {
            if (selected.size() != this.selected.size() || unselected.size() != this.unselected.size()) {
                this.changed = true;
            } else {
                return;
            }
        }
        this.repository.a(parseToEntries(selected));
        this.selected = selected;
        this.unselected = unselected;
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public void selectPack(ResourcePackDetails pack) {
        this.selected.add(pack);
        this.unselected.remove(pack);
        updatePacks(this.selected, this.unselected);
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public void deselectPack(ResourcePackDetails pack) {
        this.selected.remove(pack);
        this.unselected.add(pack);
        updatePacks(this.selected, this.unselected);
    }

    @Override // net.labymod.api.client.resources.pack.rich.PackSelectionModel
    public void commitPacks() {
        if (!this.changed) {
            return;
        }
        bib instance = bib.z();
        List<a> entries = parseToEntries(this.selected);
        Collections.reverse(entries);
        instance.P().a(entries);
        instance.t.m.clear();
        instance.t.n.clear();
        for (a entry : entries) {
            instance.t.m.add(entry.d());
            if (entry.f() != 1) {
                instance.t.n.add(entry.d());
            }
        }
        instance.t.b();
        instance.f();
        this.changed = false;
    }

    private List<a> parseToEntries(@NotNull List<ResourcePackDetails> packs) {
        List<a> entries = new ArrayList<>();
        for (ResourcePackDetails pack : packs) {
            if (pack instanceof VersionedPackDetails) {
                VersionedPackDetails versionedPack = (VersionedPackDetails) pack;
                entries.add(versionedPack.getEntry());
            }
        }
        return entries;
    }
}
