package net.labymod.core.client.chat.command;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Singleton;
import net.labymod.api.LabyAPI;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.chat.command.CommandService;
import net.labymod.api.client.chat.command.InjectedSubCommand;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TextComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.key.Key;
import net.labymod.api.event.EventBus;
import net.labymod.api.models.Implements;
import net.labymod.api.util.I18n;
import net.labymod.api.util.logging.Logging;
import net.labymod.core.addon.DefaultAddonService;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/chat/command/DefaultCommandService.class */
@Singleton
@Implements(CommandService.class)
public class DefaultCommandService implements CommandService {
    private static final DefaultAddonService ADDON_SERVICE = DefaultAddonService.getInstance();
    private final LabyAPI labyAPI;
    private final Logging logging = Logging.create((Class<?>) CommandService.class);
    private final Map<Class<? extends Command>, Command> commands = new HashMap();
    private final Set<InjectedSubCommand> injectedCommands = new HashSet();

    @Inject
    public DefaultCommandService(LabyAPI labyAPI, EventBus eventBus) {
        this.labyAPI = labyAPI;
        eventBus.registerListener(new CommandSendListener(this));
    }

    @Override // net.labymod.api.client.chat.command.CommandService
    public <T extends Command> T register(T t) {
        if (t instanceof InjectedSubCommand) {
            this.injectedCommands.add((InjectedSubCommand) t);
            return t;
        }
        T t2 = (T) this.commands.putIfAbsent((Class<? extends Command>) t.getClass(), t);
        return t2 == null ? t : t2;
    }

    @Override // net.labymod.api.client.chat.command.CommandService
    public void unregister(Class<? extends Command> commandClass) {
        for (Class<? extends Command> command : this.commands.keySet()) {
            if (command == commandClass) {
                this.commands.remove(command);
                return;
            }
        }
        this.injectedCommands.removeIf(command2 -> {
            return command2.getClass() == commandClass;
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // net.labymod.api.client.chat.command.CommandService
    public boolean fireCommand(String prefix, String[] arguments) {
        String lowercasePrefix = prefix.toLowerCase(Locale.ROOT);
        for (Command command : this.commands.values()) {
            if (commandMatches(command, lowercasePrefix) && fireCommandInternal(command, prefix, arguments)) {
                return true;
            }
        }
        if (this.injectedCommands.isEmpty()) {
            return false;
        }
        String message = prefix + " " + String.join(" ", arguments);
        String lowerCaseMessage = message.toLowerCase(Locale.ROOT);
        for (InjectedSubCommand command2 : this.injectedCommands) {
            if (lowerCaseMessage.startsWith(command2.getInjectionPrefix() + " ") && ADDON_SERVICE.isEnabled(this.labyAPI.getNamespace(command2))) {
                String subMessage = message.substring(command2.getInjectionPrefix().length() + 1);
                if (subMessage.length() == 0) {
                    continue;
                } else {
                    String[] messageSplit = subMessage.split(" ");
                    String subPrefix = messageSplit[0];
                    String lowercaseSubPrefix = subPrefix.toLowerCase(Locale.ROOT);
                    if (!command2.getPrefix().equals(lowercaseSubPrefix)) {
                        boolean found = false;
                        String[] aliases = command2.getAliases();
                        int length = aliases.length;
                        int i = 0;
                        while (true) {
                            if (i >= length) {
                                break;
                            }
                            String alias = aliases[i];
                            if (!alias.equals(lowercaseSubPrefix)) {
                                i++;
                            } else {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            continue;
                        }
                    }
                    String[] subArguments = new String[messageSplit.length - 1];
                    System.arraycopy(messageSplit, 1, subArguments, 0, subArguments.length);
                    if (executeCommand(command2.getClass(), command2, subPrefix, subArguments)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override // net.labymod.api.client.chat.command.CommandService
    public boolean fireCommand(Class<? extends Command> commandClass, String usedPrefix, String[] arguments) {
        Command command = this.commands.get(commandClass);
        if (command == null) {
            throw new UnsupportedOperationException("The Command \"" + commandClass.getSimpleName() + "\" was not registered as an command.");
        }
        return fireCommandInternal(commandClass, command, usedPrefix, arguments);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private boolean fireCommandInternal(Command command, String usedPrefix, String[] arguments) {
        return fireCommandInternal(command.getClass(), command, usedPrefix, arguments);
    }

    private boolean fireCommandInternal(Class<? extends Command> commandClass, Command command, String usedPrefix, String[] arguments) {
        String namespace = this.labyAPI.getNamespace((Class<?>) commandClass);
        if (!ADDON_SERVICE.isEnabled(namespace)) {
            String translatedNamespace = I18n.getTranslation(namespace + ".settings.name", new Object[0]);
            if (translatedNamespace == null) {
                translatedNamespace = namespace;
            }
            TextComponent component = Component.text(I18n.translate("labymod.command.disabled", usedPrefix, translatedNamespace, Key.L_SHIFT.getName()), NamedTextColor.RED);
            this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
            return true;
        }
        return executeCommand(commandClass, command, usedPrefix, arguments);
    }

    private boolean executeCommand(Class<? extends Command> commandClass, Command command, String prefix, String[] arguments) {
        if (arguments.length > 0) {
            String subCommandPrefix = arguments[0];
            String[] subCommandArguments = null;
            for (SubCommand subCommand : command.getSubCommands()) {
                if (commandMatches(subCommand, subCommandPrefix)) {
                    if (subCommandArguments == null) {
                        subCommandArguments = new String[arguments.length - 1];
                        System.arraycopy(arguments, 1, subCommandArguments, 0, subCommandArguments.length);
                    }
                    if (executeCommand(subCommand.getClass(), subCommand, subCommandPrefix, subCommandArguments)) {
                        return true;
                    }
                }
            }
        }
        try {
            return command.execute(prefix, arguments);
        } catch (Exception e) {
            TextComponent component = Component.text(I18n.translate("labymod.command.exception", commandClass.getSimpleName(), e.getClass().getSimpleName()), NamedTextColor.RED);
            this.labyAPI.minecraft().chatExecutor().displayClientMessage(component);
            this.logging.error("An exception occurred while executing the command " + commandClass.getSimpleName() + ".", e);
            return true;
        }
    }

    private boolean commandMatches(Command command, String prefix) {
        if (command.getPrefix().equals(prefix)) {
            return true;
        }
        for (String alias : command.getAliases()) {
            if (alias.equals(prefix)) {
                return true;
            }
        }
        return false;
    }
}
