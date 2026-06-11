package com.mojang.realmsclient.dto;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.logging.LogUtils;
import java.io.IOException;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/ServiceQuality.class */
public enum ServiceQuality {
    GREAT(1, "icon/ping_5"),
    GOOD(2, "icon/ping_4"),
    OKAY(3, "icon/ping_3"),
    POOR(4, "icon/ping_2"),
    UNKNOWN(5, "icon/ping_unknown");

    final int value;
    private final Identifier icon;

    ServiceQuality(int $$0, String $$1) {
        this.value = $$0;
        this.icon = Identifier.withDefaultNamespace($$1);
    }

    public static ServiceQuality byValue(int $$0) {
        for (ServiceQuality $$1 : values()) {
            if ($$1.getValue() == $$0) {
                return $$1;
            }
        }
        return null;
    }

    public int getValue() {
        return this.value;
    }

    public Identifier getIcon() {
        return this.icon;
    }

    /* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:com/mojang/realmsclient/dto/ServiceQuality$RealmsServiceQualityJsonAdapter.class */
    public static class RealmsServiceQualityJsonAdapter extends TypeAdapter<ServiceQuality> {
        private static final Logger LOGGER = LogUtils.getLogger();

        public void write(JsonWriter $$0, ServiceQuality $$1) throws IOException {
            $$0.value($$1.value);
        }

        /* JADX INFO: renamed from: read, reason: merged with bridge method [inline-methods] */
        public ServiceQuality m127read(JsonReader $$0) throws IOException {
            int $$1 = $$0.nextInt();
            ServiceQuality $$2 = ServiceQuality.byValue($$1);
            if ($$2 == null) {
                LOGGER.warn("Unsupported ServiceQuality {}", Integer.valueOf($$1));
                return ServiceQuality.UNKNOWN;
            }
            return $$2;
        }
    }
}
