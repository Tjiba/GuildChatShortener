package com.guildchat.formatter;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UpdateNotifier {
    
    private static boolean hasChecked = false;
    private static boolean updatePendingRestart = false;
    private static boolean hypixelRestartReminderShown = false;
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Auto-check for updates on first join
            if (!hasChecked && client.player != null) {
                hasChecked = true;

                runUpdateFlowSilentlyWithAutoDownload(client);
            }
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> {
            if (updatePendingRestart && !hypixelRestartReminderShown && isHypixelServer(client)) {
                hypixelRestartReminderShown = true;
                sendClientMessage(client, Messages.get(Messages.UPDATE_RESTART_ON_HYPIXEL_JOIN));
            }
        });
    }

    private static boolean isHypixelServer(MinecraftClient client) {
        if (client == null || client.getCurrentServerEntry() == null || client.getCurrentServerEntry().address == null) {
            return false;
        }
        String address = client.getCurrentServerEntry().address.toLowerCase();
        return address.contains("hypixel.net");
    }

    private static void runUpdateFlowSilently() {
        // Effectue la vérification de mise à jour en arrière-plan sans afficher les messages
        VersionManager.resetVersionCache();
        String currentVersion = VersionManager.CURRENT_VERSION != null ? VersionManager.CURRENT_VERSION : "unknown";

        VersionManager.checkVersionUpdateAsyncInternal().thenRun(() -> {
            String latestVersion = VersionManager.getLatestVersionOnline();
            if (latestVersion == null) {
                return;
            }

            int comparison = VersionManager.compareVersions(currentVersion, latestVersion);

            if (comparison < 0) {
                // Mise à jour disponible - ne pas afficher de message automatiquement
                // Les mises à jour se verront seulement via /gz update
            }
        });
    }

    private static void runUpdateFlowSilentlyWithAutoDownload(MinecraftClient client) {
        // Auto-check and download updates silently on startup
        VersionManager.resetVersionCache();
        String currentVersion = VersionManager.CURRENT_VERSION != null ? VersionManager.CURRENT_VERSION : "unknown";

        VersionManager.checkVersionUpdateAsyncInternal().thenRun(() -> {
            String latestVersion = VersionManager.getLatestVersionOnline();
            if (latestVersion == null) {
                return;
            }

            int comparison = VersionManager.compareVersions(currentVersion, latestVersion);

            if (comparison < 0) {
                // Update available - start auto-download silently
                if (BridgeConfig.get().autoUpdaterEnabled) {
                    startAutoDownloadSilently(client, latestVersion);
                }
            }
        });
    }

    private static void startAutoDownloadSilently(MinecraftClient client, String latestVersion) {
        VersionManager.ReleaseInfo releaseInfo = VersionManager.getLatestReleaseInfo();
        if (releaseInfo == null || releaseInfo.getJarDownloadUrl() == null || releaseInfo.getJarName() == null) {
            return;
        }

        UpdateDownloader.downloadLatestReleaseAsync().thenAccept(result -> {
            if (result.isSuccess()) {
                updatePendingRestart = true;
                hypixelRestartReminderShown = false;
                // Notify user that update is downloaded and will be active on restart
                sendClientMessage(client, Messages.format(Messages.UPDATE_AUTO_SUCCESS, "Update will be active when you restart Minecraft"));
            }
        });
    }

    private static void runUpdateFlow(MinecraftClient client, boolean manual) {
        VersionManager.resetVersionCache();
        String currentVersion = VersionManager.CURRENT_VERSION != null ? VersionManager.CURRENT_VERSION : "unknown";

        VersionManager.checkVersionUpdateAsyncInternal().thenRun(() -> {
            String latestVersion = VersionManager.getLatestVersionOnline();
            if (latestVersion == null) {
                if (manual) {
                    sendClientMessage(client, Messages.get(Messages.UPDATE_CHECK_FAILED));
                }
                return;
            }

            int comparison = VersionManager.compareVersions(currentVersion, latestVersion);

            if (comparison < 0) {
                sendClientMessage(client,
                    Messages.format(Messages.UPDATE_AVAILABLE, latestVersion, currentVersion));
                sendClientMessage(client, Messages.get(Messages.UPDATE_MODRINTH));

                if (BridgeConfig.get().autoUpdaterEnabled) {
                    startAutoDownload(client, latestVersion);
                }
                return;
            }

            if (comparison > 0) {
                sendClientMessage(client,
                    Messages.format(Messages.UPDATE_DEV_VERSION, currentVersion, latestVersion));
                return;
            }

            if (manual) {
                sendClientMessage(client,
                    Messages.format(Messages.UPDATE_UP_TO_DATE, currentVersion));
            }
        });
    }

    private static void startAutoDownload(MinecraftClient client, String latestVersion) {
        VersionManager.ReleaseInfo releaseInfo = VersionManager.getLatestReleaseInfo();
        if (releaseInfo == null || releaseInfo.getJarDownloadUrl() == null || releaseInfo.getJarName() == null) {
            sendClientMessage(client, Messages.get(Messages.UPDATE_AUTO_NOT_AVAILABLE));
            return;
        }

        sendClientMessage(client, Messages.format(Messages.UPDATE_AUTO_START, latestVersion));

        UpdateDownloader.downloadLatestReleaseAsync().thenAccept(result -> {
            if (result.isSuccess()) {
                updatePendingRestart = true;
                hypixelRestartReminderShown = false;
                sendClientMessage(client, Messages.format(Messages.UPDATE_AUTO_SUCCESS, result.getMessage()));
                sendClientMessage(client, Messages.get(Messages.UPDATE_RESTART_REQUIRED));
            } else {
                sendClientMessage(client, Messages.format(Messages.UPDATE_AUTO_FAILED, result.getMessage()));
            }
        });
    }

    private static void sendClientMessage(MinecraftClient client, String msg) {
        if (client == null) return;
        client.execute(() -> {
            if (client.player != null) {
                client.player.sendMessage(Text.literal(msg), false);
            }
        });
    }
    
    /**
     * Vérifie manuellement les mises à jour (pour commande)
     */
    public static void checkUpdateManually(MinecraftClient client) {
        sendClientMessage(client, Messages.get(Messages.UPDATE_CHECKING));
        runUpdateFlow(client, true);
    }
}
