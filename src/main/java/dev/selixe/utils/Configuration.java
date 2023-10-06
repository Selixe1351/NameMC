package dev.selixe.utils;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class Configuration {

    private final File file;
    private final String name;
    @Getter
    private YamlConfiguration config;
    private final JavaPlugin plugin;

    public Configuration(String name, JavaPlugin plugin) {
        this.plugin = plugin;
        this.name = name;
        plugin.getDataFolder().mkdir();
        this.file = new File(plugin.getDataFolder(), this.name);
        saveDefault();
        reloadConfig();
    }

    public void saveDefault() {
        if (!this.file.exists()) {
            InputStream defConfigStream = this.plugin.getResource(this.name);
            if (defConfigStream != null) {
                try {
                    this.plugin.saveResource(this.name, false);
                } catch (IllegalArgumentException e) {
                    plugin.getLogger().warning("Failed to save default configuration '" + this.name + "': " + e.getMessage());
                }
            } else {
                plugin.getLogger().warning("Default configuration '" + this.name + "' not found in resources.");
            }
        }
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void saveConfig() {
        try {
            this.config.save(this.file);
        } catch (IOException e) {
            plugin.getLogger().warning("Error while saving configuration '" + this.name + "': " + e.getMessage());
        }
    }

}