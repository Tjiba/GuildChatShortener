package com.guildchat.formatter;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class UpdateNotifier {
    
    private static boolean hasChecked = false;
    
    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Afficher le message de mise à jour au premier join du serveur
            if (!hasChecked && client.player != null) {
                hasChecked = true;
                
                // Vérifier la version en ligne de manière asynchrone
                VersionManager.checkVersionUpdateAsync();
                
                // Vérifier après un court délai si une nouvelle version a été trouvée
                scheduleVersionCheckMessage(client);
            }
        });
    }
    
    /**
     * Affiche le message de mise à jour après vérification en ligne
     */
    private static void scheduleVersionCheckMessage(MinecraftClient client) {
        // Attendre un peu pour que la vérification GitHub soit faite
        Thread delayedCheck = new Thread(() -> {
            try {
                // Attendre 2 secondes pour que la requête HTTP se termine
                Thread.sleep(2000);
                
                // Vérifier si une nouvelle version est disponible en ligne
                String latestVersion = VersionManager.getLatestVersionOnline();
                if (latestVersion != null && !latestVersion.equals(VersionManager.CURRENT_VERSION)) {
                    if (client.player != null) {
                        client.player.sendMessage(
                            Text.literal(Messages.get(Messages.UPDATE_AVAILABLE)),
                            false
                        );
                        client.player.sendMessage(
                            Text.literal(Messages.get(Messages.UPDATE_MODRINTH)),
                            false
                        );
                    }
                }
            } catch (InterruptedException e) {
                // Ignorer les interruptions
                Thread.currentThread().interrupt();
            }
        });
        delayedCheck.setDaemon(true);
        delayedCheck.start();
    }
}
