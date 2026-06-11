package net.labymod.core.client.options;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.labymod.api.Constants;
import net.labymod.api.Laby;
import net.labymod.api.LabyAPI;
import net.labymod.api.addon.AddonService;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.TranslatableComponent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.gui.screen.activity.activities.ConfirmActivity;
import net.labymod.api.configuration.converter.LegacyConfigConverter;
import net.labymod.api.configuration.converter.addon.LegacyAddon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.client.gui.screen.ActivityOpenEvent;
import net.labymod.api.models.addon.info.AddonMeta;
import net.labymod.api.util.ComponentUtil;
import net.labymod.core.flint.FlintController;
import net.labymod.core.flint.downloader.DownloadState;
import net.labymod.core.flint.marketplace.FlintModification;
import net.labymod.core.main.LabyMod;

/* JADX INFO: loaded from: LabyMod-4.jar:net/labymod/core/client/options/LegacyConfigConvertListener.class */
public class LegacyConfigConvertListener {
    private final LabyAPI labyAPI = Laby.labyAPI();
    private final LegacyConfigConverter converter = Laby.references().legacyConfigConverter();
    private boolean conversionAsked;

    @Subscribe
    public void onActivityOpen(ActivityOpenEvent event) {
        if (this.conversionAsked) {
            return;
        }
        this.conversionAsked = true;
        this.labyAPI.minecraft().executeNextTick(() -> {
            boolean labyModConverted = this.converter.wasConversionAsked("labymod");
            convertLabyModAndAddons(this.converter, confirmed -> {
                if (!labyModConverted) {
                    this.labyAPI.minecraft().executeNextTick(() -> {
                        convertLegacyAddons(this.converter, addonsConfirmed -> {
                            this.converter.setConversionAsked("labymod");
                        });
                    });
                }
            });
        });
    }

    private void convertLabyModAndAddons(LegacyConfigConverter converter, Consumer<Boolean> callback) {
        Stream streamFilter = converter.getConverters().stream().map((v0) -> {
            return v0.getNamespace();
        }).distinct().filter(namespace -> {
            return !converter.wasConversionAsked(namespace);
        });
        Objects.requireNonNull(converter);
        List<String> namespaces = (List) streamFilter.filter(converter::hasStuffToConvert).sorted().collect(Collectors.toList());
        if (namespaces.isEmpty()) {
            callback.accept(null);
            return;
        }
        Component joined = ComponentUtil.join((List) namespaces.stream().map(namespace2 -> {
            return Component.text((String) Laby.labyAPI().addonService().getAddon(namespace2).map(addon -> {
                return addon.info().getDisplayName();
            }).orElse(namespace2.equals("labymod") ? Constants.Branding.NAME : namespace2));
        }).collect(Collectors.toList()));
        TranslatableComponent title = Component.translatable("labymod.legacyconverter.convertSettings", joined, Component.translatable("labymod.legacyconverter.convertWarning", NamedTextColor.RED, joined.copy()));
        ConfirmActivity.confirm(title, Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), (Consumer<Boolean>) confirmed -> {
            if (confirmed != null && confirmed.booleanValue()) {
                Iterator i$ = namespaces.iterator();
                while (i$.hasNext()) {
                    String namespace3 = (String) i$.next();
                    converter.convert(namespace3);
                }
            }
            Iterator i$2 = namespaces.iterator();
            while (i$2.hasNext()) {
                String addonNamespace = (String) i$2.next();
                converter.setConversionAsked(addonNamespace);
            }
            callback.accept(confirmed);
        });
    }

    private void convertLegacyAddons(LegacyConfigConverter converter, Consumer<Boolean> callback) {
        Map<String, LegacyAddon> addons = new HashMap<>();
        AddonService addonService = this.labyAPI.addonService();
        for (LegacyAddon addon : converter.discoverLegacyAddons()) {
            String namespace = addon.getNamespace();
            if (namespace != null && !addonService.getAddon(namespace).isPresent()) {
                addons.put(namespace, addon);
            }
        }
        if (addons.isEmpty()) {
            callback.accept(null);
        } else {
            Component addonsComponent = ComponentUtil.join((List) addons.values().stream().sorted(Comparator.comparing((v0) -> {
                return v0.getName();
            })).map(addon2 -> {
                return Component.text(addon2.getName() + " (" + addon2.getVersion().getVersion() + ")");
            }).collect(Collectors.toList()));
            ConfirmActivity.confirm(Component.translatable("labymod.legacyconverter.convertAddons", addonsComponent), Component.translatable("labymod.legacyconverter.yes", new Component[0]), Component.translatable("labymod.legacyconverter.no", new Component[0]), (Consumer<Boolean>) confirmed -> {
                callback.accept(confirmed);
                if (confirmed == null || !confirmed.booleanValue()) {
                    return;
                }
                FlintController flintController = LabyMod.references().flintController();
                AtomicInteger downloadCount = new AtomicInteger();
                Collection<FlintModification> installed = ConcurrentHashMap.newKeySet();
                Runnable completed = () -> {
                    for (Map.Entry<String, LegacyAddon> addon3 : addons.entrySet()) {
                        converter.useVersion(addon3.getKey(), addon3.getValue().getVersion());
                    }
                    boolean restart = installed.stream().anyMatch(mod -> {
                        return mod.hasMeta(AddonMeta.RESTART_REQUIRED);
                    });
                    convertLabyModAndAddons(converter, c -> {
                        if (restart) {
                            ConfirmActivity.confirm(Component.translatable("labymod.legacyconverter.restartRequired", new Component[0]), restartNow -> {
                                if (restartNow != null && restartNow.booleanValue()) {
                                    Laby.labyAPI().minecraft().shutdownGame();
                                }
                            });
                        }
                    });
                };
                for (String addonNamespace : addons.keySet()) {
                    flintController.getModification(addonNamespace, result -> {
                        if (!result.isPresent()) {
                            if (downloadCount.incrementAndGet() == addons.size()) {
                                completed.run();
                            }
                        } else {
                            FlintModification mod = (FlintModification) result.get();
                            flintController.downloadModification(mod, task -> {
                                if (task.state() != DownloadState.FAILED) {
                                    installed.add(mod);
                                }
                                if (downloadCount.incrementAndGet() == addons.size()) {
                                    completed.run();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
