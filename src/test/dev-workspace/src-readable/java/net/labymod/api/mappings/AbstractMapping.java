package net.labymod.api.mappings;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/api/mappings/AbstractMapping.class */
public abstract class AbstractMapping implements Mapping {
    private final String original;
    private final String mapped;

    protected AbstractMapping(String original, String mapped) {
        this.original = original;
        this.mapped = mapped;
    }

    @Override // net.labymod.api.mappings.Mapping
    public String getOriginal() {
        return this.original;
    }

    @Override // net.labymod.api.mappings.Mapping
    public String getMapped() {
        return this.mapped;
    }
}
