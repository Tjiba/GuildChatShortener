package com.guildchat.formatter;

import net.fabricmc.loader.api.FabricLoader;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;

public class UpdateDownloader {

    public static class DownloadResult {
        private final boolean success;
        private final String message;

        public DownloadResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    public static CompletableFuture<DownloadResult> downloadLatestReleaseAsync() {
        return CompletableFuture.supplyAsync(UpdateDownloader::downloadLatestRelease);
    }

    public static DownloadResult downloadLatestRelease() {
        try {
            VersionManager.ReleaseInfo releaseInfo = VersionManager.getLatestReleaseInfo();
            if (releaseInfo == null || releaseInfo.getJarDownloadUrl() == null || releaseInfo.getJarName() == null) {
                return new DownloadResult(false, "No downloadable release asset found.");
            }

            URI downloadUri = URI.create(releaseInfo.getJarDownloadUrl());
            String host = downloadUri.getHost();
            if (host == null || (!host.equals("cdn.modrinth.com") && !host.equals("cdn-raw.modrinth.com"))) {
                return new DownloadResult(false, "Refused update: untrusted download host.");
            }

            Path modsDir = FabricLoader.getInstance().getGameDir().resolve("mods");
            if (!Files.exists(modsDir)) {
                Files.createDirectories(modsDir);
            }

            String cleanName = releaseInfo.getJarName().replace(" ", "-");
            Path finalPath = modsDir.resolve(cleanName);
            Path tmpPath = modsDir.resolve(cleanName + ".download");

            HttpURLConnection connection = (HttpURLConnection) downloadUri.toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestProperty("User-Agent", "GuildZip-Mod-Updater");

            // Ajouter le support des redirects
            connection.setInstanceFollowRedirects(true);
            HttpURLConnection.setFollowRedirects(true);

            int responseCode = connection.getResponseCode();

            // Gérer les redirects manuellement si nécessaire
            if (responseCode == 301 || responseCode == 302) {
                String newLocation = connection.getHeaderField("Location");
                if (newLocation != null) {
                    connection.disconnect();
                    connection = (HttpURLConnection) new URI(newLocation).toURL().openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(15000);
                    connection.setRequestProperty("User-Agent", "GuildZip-Mod-Updater");
                    responseCode = connection.getResponseCode();
                }
            }

            if (responseCode < 200 || responseCode >= 300) {
                connection.disconnect();
                return new DownloadResult(false, "Download failed: HTTP " + responseCode);
            }

            try (InputStream in = connection.getInputStream();
                 OutputStream out = Files.newOutputStream(tmpPath)) {
                byte[] buffer = new byte[8192];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            } finally {
                connection.disconnect();
            }

            Files.move(tmpPath, finalPath, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);

            return new DownloadResult(true, "Update downloaded to: " + finalPath.getFileName());
        } catch (Exception e) {
            GuildChatMod.LOGGER.error("Auto-update download failed", e);
            return new DownloadResult(false, "Download failed: " + e.getMessage());
        }
    }
}

