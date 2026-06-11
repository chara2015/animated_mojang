package net.minecraft.server.network.config;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.configuration.ClientboundCodeOfConductPacket;
import net.minecraft.server.network.ConfigurationTask;

/* JADX INFO: loaded from: minecraft-client-1.21.11-named.jar:net/minecraft/server/network/config/ServerCodeOfConductConfigurationTask.class */
public class ServerCodeOfConductConfigurationTask implements ConfigurationTask {
    public static final ConfigurationTask.Type TYPE = new ConfigurationTask.Type("server_code_of_conduct");
    private final Supplier<String> codeOfConduct;

    public ServerCodeOfConductConfigurationTask(Supplier<String> $$0) {
        this.codeOfConduct = $$0;
    }

    @Override // net.minecraft.server.network.ConfigurationTask
    public void start(Consumer<Packet<?>> $$0) {
        $$0.accept(new ClientboundCodeOfConductPacket(this.codeOfConduct.get()));
    }

    @Override // net.minecraft.server.network.ConfigurationTask
    public ConfigurationTask.Type type() {
        return TYPE;
    }
}
