package dev.selixe;

import dev.selixe.commands.NameMCCommand;
import dev.swift.config.Config;
import dev.swift.util.command.CommandHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

public class NameMC extends JavaPlugin {

    public static NameMC getInstance() {
        return NameMC.getPlugin(NameMC.class);
    }

    @Getter private Config configuration;

    @Override
    public void onEnable() {
        configuration = new Config("config.yml", this);

        CommandHandler.registerCommands(NameMCCommand.class, this);
    }

    @Override
    public void onDisable() {

    }
}
