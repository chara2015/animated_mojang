package net.labymod.core.client.sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Supplier;
import net.labymod.api.client.resources.ResourceLocation;
import net.labymod.api.client.sound.SoundType;
import net.labymod.api.util.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/sound/DefaultSoundType.class */
public class DefaultSoundType extends SoundType {
    private List<Pair<String, Supplier<ResourceLocation>>> suppliers;

    public DefaultSoundType(String identifier, ResourceLocation location, SoundType parent) {
        super(identifier, location, parent);
    }

    @Override // net.labymod.api.client.sound.SoundType
    @Nullable
    public ResourceLocation getLocation() {
        ResourceLocation parentLocation;
        ResourceLocation parentLocation2;
        ResourceLocation fromSuppliers = getFromSuppliers();
        if (fromSuppliers != null) {
            return fromSuppliers;
        }
        SoundType parent = this.parent;
        while (true) {
            SoundType parent2 = parent;
            if (parent2 != null) {
                if (parent2 instanceof DefaultSoundType) {
                    parentLocation2 = ((DefaultSoundType) parent2).getFromSuppliers();
                } else {
                    parentLocation2 = parent2.getLocation();
                }
                if (parentLocation2 != null) {
                    return parentLocation2;
                }
                parent = parent2.getParent();
            } else {
                if (this.location != null) {
                    return this.location;
                }
                SoundType parent3 = this.parent;
                while (true) {
                    SoundType parent4 = parent3;
                    if (parent4 != null) {
                        if (parent4 instanceof DefaultSoundType) {
                            parentLocation = ((DefaultSoundType) parent4).location;
                        } else {
                            parentLocation = parent4.getLocation();
                        }
                        if (parentLocation != null) {
                            return parentLocation;
                        }
                        parent3 = parent4.getParent();
                    } else {
                        return null;
                    }
                }
            }
        }
    }

    private ResourceLocation getFromSuppliers() {
        if (this.suppliers != null) {
            for (Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
                ResourceLocation resourceLocation = supplier.getSecond().get();
                if (resourceLocation != null) {
                    return resourceLocation;
                }
            }
            return null;
        }
        return null;
    }

    public void bind(@NotNull String identifier, @NotNull Supplier<ResourceLocation> supplier) {
        Pair<String, Supplier<ResourceLocation>> bound = getBound(identifier);
        if (bound != null) {
            this.suppliers.remove(bound);
        }
        if (this.suppliers == null) {
            this.suppliers = new ArrayList();
        }
        this.suppliers.add(0, Pair.of(identifier.toLowerCase(Locale.ROOT), supplier));
    }

    public Pair<String, Supplier<ResourceLocation>> getBound(String identifier) {
        if (this.suppliers == null) {
            return null;
        }
        String identifier2 = identifier.toLowerCase(Locale.ROOT);
        for (Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
            if (supplier.getFirst().equals(identifier2)) {
                return supplier;
            }
        }
        return null;
    }

    public void setDefault(ResourceLocation defaultResourceLocation) {
        this.location = defaultResourceLocation;
    }

    @Override // net.labymod.api.client.sound.SoundType
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DefaultSoundType) || !super.equals(o)) {
            return false;
        }
        DefaultSoundType that = (DefaultSoundType) o;
        return Objects.equals(this.suppliers, that.suppliers);
    }

    @Override // net.labymod.api.client.sound.SoundType
    public int hashCode() {
        int result = super.hashCode();
        return (31 * result) + (this.suppliers != null ? this.suppliers.hashCode() : 0);
    }

    @Override // net.labymod.api.client.sound.SoundType
    public String toString() {
        String string = "DefaultSoundType{identifier='" + this.identifier + "'";
        if (this.location != null) {
            string = string + ", location=" + String.valueOf(this.location);
        }
        if (this.suppliers != null) {
            StringBuilder builder = new StringBuilder();
            for (Pair<String, Supplier<ResourceLocation>> supplier : this.suppliers) {
                builder.append(';').append(supplier.getFirst());
            }
            string = string + ", suppliers=" + builder.substring(1);
        }
        if (this.parent != null) {
            string = string + ", parent=" + String.valueOf(this.parent);
        }
        return string + "}";
    }
}
