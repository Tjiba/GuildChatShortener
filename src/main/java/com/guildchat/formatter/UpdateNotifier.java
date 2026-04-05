package com.guildchat.formatter;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

public class UpdateNotifier {

    private static boolean hasChecked = false;
    private static boolean updatePendingRestart = false;
    private static boolean hypixelRestartReminderShown = false;
    private static boolean dismissedForSession = false;

    public static void init() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
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

    public static void dismiss() {
        dismissedForSession = true;
    }

    public static void disableNotification() {
        BridgeConfig cfg = BridgeConfig.get();
        cfg.hideUpdateNotification = true;
        cfg.save();
    }

    private static boolean isHypixelServer(MinecraftClient client) {
        if (client == null || client.getCurrentServerEntry() == null || client.getCurrentServerEntry().address == null) {
            return false;
        }
        String address = client.getCurrentServerEntry().address.toLowerCase();
        return address.contains("hypixel.net");
    }

    private static void runUpdateFlowSilentlyWithAutoDownload(MinecraftClient client) {
        VersionManager.resetVersionCache();
        String currentVersion = VersionManager.CURRENT_VERSION != null ? VersionManager.CURRENT_VERSION : "unknown";

        VersionManager.checkVersionUpdateAsyncInternal().thenRun(() -> {
            String latestVersion = VersionManager.getLatestVersionOnline();
            if (latestVersion == null) return;

            int comparison = VersionManager.compareVersions(currentVersion, latestVersion);
            if (comparison < 0) {
                if (!BridgeConfig.get().hideUpdateNotification && !dismissedForSession) {
                    sendUpdateNotification(client, latestVersion);
                }
            }
        });
    }

    private static MutableText buildGuildZipPrefix() {
        return Text.literal("[").styled(s -> s.withColor(0x8F96BE).withBold(true))
            .append(Text.literal("G").styled(s -> s.withColor(0x8886B4).withBold(true)))
            .append(Text.literal("u").styled(s -> s.withColor(0x8175AA).withBold(true)))
            .append(Text.literal("i").styled(s -> s.withColor(0x7A65A1).withBold(true)))
            .append(Text.literal("l").styled(s -> s.withColor(0x735597).withBold(true)))
            .append(Text.literal("d").styled(s -> s.withColor(0x6C448D).withBold(true)))
            .append(Text.literal("Z").styled(s -> s.withColor(0x653483).withBold(true)))
            .append(Text.literal("i").styled(s -> s.withColor(0x5E247A).withBold(true)))
            .append(Text.literal("p").styled(s -> s.withColor(0x571370).withBold(true)))
            .append(Text.literal("]").styled(s -> s.withColor(0x500366).withBold(true)));
    }

    private static void sendUpdateNotification(MinecraftClient client, String latestVersion) {
        MutableText msg = Text.literal("")
            .append(buildGuildZipPrefix())
            .append(Text.literal(" A new update is available for GuildZip §a" + latestVersion + " §f: "));

        MutableText updateBtn = Text.literal("§a§l[Update]")
                .styled(s -> s
                        .withClickEvent(new ClickEvent.RunCommand("/gz update"))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Text.literal("§7Download and install GuildZip §a" + latestVersion))));

        MutableText dismissBtn = Text.literal(" §7[Dismiss]")
                .styled(s -> s
                        .withClickEvent(new ClickEvent.RunCommand("/gz dismiss"))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Text.literal("§7Hide this notification for this session"))));

        MutableText noShowBtn = Text.literal(" §c[Don't show again]")
                .styled(s -> s
                        .withClickEvent(new ClickEvent.RunCommand("/gz noupdate"))
                        .withHoverEvent(new HoverEvent.ShowText(
                                Text.literal("§7Never show update notifications on startup"))));

        msg.append(updateBtn).append(dismissBtn).append(noShowBtn);
        sendClientMessage(client, msg);
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
        sendClientMessage(client, Text.literal(msg));
    }

    private static void sendClientMessage(MinecraftClient client, Text msg) {
        if (client == null) return;
        client.execute(() -> {
            if (client.player != null) {
                client.player.sendMessage(msg, false);
            }
        });
    }

    public static void checkUpdateManually(MinecraftClient client) {
        sendClientMessage(client, Messages.get(Messages.UPDATE_CHECKING));
        runUpdateFlow(client, true);
    }
}
