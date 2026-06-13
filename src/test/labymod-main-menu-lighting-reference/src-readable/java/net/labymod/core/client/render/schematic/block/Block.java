package net.labymod.core.client.render.schematic.block;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.labymod.core.client.render.schematic.block.material.MaterialRegistry;
import net.labymod.core.client.render.schematic.block.material.material.Material;
import net.labymod.core.client.render.schematic.lighting.LightSource;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/render/schematic/block/Block.class */
public class Block {

    @NotNull
    private final Material material;
    private final Map<String, Object> parameters;
    private final String namespace;
    private final String rawId;
    private final boolean unknown;
    private LightSource lightSource;

    public Block(String namespace, String rawId) {
        this.parameters = new HashMap();
        this.namespace = namespace;
        this.rawId = rawId;
        String id = rawId;
        if (id.contains("[")) {
            String[] split = id.split("\\[");
            id = split[0];
            String parameters = split[1].replace("]", "");
            for (String parameter : parameters.split(",")) {
                String[] pair = parameter.split("=");
                String key = pair[0];
                Object value = pair[1];
                try {
                    value = Integer.valueOf(Integer.parseInt(pair[1]));
                } catch (NumberFormatException e) {
                }
                if (pair[1].equals("true") || pair[1].equals("false")) {
                    value = Boolean.valueOf(Boolean.parseBoolean(pair[1]));
                }
                this.parameters.put(key, value);
            }
        }
        Material material = MaterialRegistry.getById(id);
        if (material == null) {
            this.material = MaterialRegistry.AIR;
            this.unknown = true;
        } else {
            this.material = material;
            this.unknown = false;
        }
    }

    public Block(String namespace, Material material) {
        this.parameters = new HashMap();
        this.namespace = namespace;
        this.rawId = material.getId();
        this.material = material;
        this.unknown = false;
    }

    public boolean hasParameter(String type) {
        return this.parameters.containsKey(type);
    }

    public <T> T getParameter(String str) {
        return (T) this.parameters.get(str);
    }

    public <T> T getParameter(String str, T t) {
        return hasParameter(str) ? (T) this.parameters.get(str) : t;
    }

    public LightSource getLightSource() {
        return this.lightSource;
    }

    public void setLightSource(LightSource lightSource) {
        this.lightSource = lightSource;
    }

    @NotNull
    public Material material() {
        return this.material;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String toString() {
        if (this.unknown) {
            return String.format(Locale.ROOT, "%s:%s", this.namespace, this.rawId);
        }
        StringBuilder id = new StringBuilder(String.format(Locale.ROOT, "%s:%s", this.namespace, this.material.getId()));
        if (!this.parameters.isEmpty()) {
            id.append("[");
            for (Map.Entry<String, Object> entry : this.parameters.entrySet()) {
                id.append(String.format(Locale.ROOT, "%s=%s,", entry.getKey(), entry.getValue()));
            }
            id = new StringBuilder(id.substring(0, id.length() - 1));
            id.append("]");
        }
        return id.toString();
    }
}
