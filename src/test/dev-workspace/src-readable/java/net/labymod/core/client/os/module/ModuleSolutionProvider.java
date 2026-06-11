package net.labymod.core.client.os.module;

import net.labymod.api.util.logging.Logging;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/os/module/ModuleSolutionProvider.class */
public abstract class ModuleSolutionProvider {
    protected static final Logging LOGGER = Logging.getLogger();
    private final ModuleInfo moduleInfo;

    public abstract void execute();

    public ModuleSolutionProvider(String... names) {
        this.moduleInfo = ModuleInfo.of(names);
    }

    public boolean contains(String name) {
        return this.moduleInfo.contains(name);
    }

    public String getFileName() {
        return this.moduleInfo.getFileName();
    }
}
