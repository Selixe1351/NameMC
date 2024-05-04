package dev.selixe;

import dev.selixe.commands.NameMCCommand;
import dev.selixe.commands.VoteAddCommand;
import dev.selixe.commands.VoteCheckCommand;
import dev.selixe.commands.VoteRemoveCommand;
import dev.selixe.utils.Configuration;
import dev.selixe.utils.command.CommandHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

@Getter
public class NameMC extends JavaPlugin {

    public static NameMC getInstance() {
        return NameMC.getPlugin(NameMC.class);
    }

    private Configuration configuration;

    @Override
    public void onEnable() {
        configuration = new Configuration("config.yml", this);

        CommandHandler.registerCommands("dev.selixe.commands", this);
    }

}
