package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) 2024 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class ReloadCommand {

    @Command(names = {"namemc reload"}, permission = "namemc.staff")
    public void execute(CommandSender sender) {
        NameMC.getInstance().getConfiguration().reloadConfig();
        sender.sendMessage(ColorUtils.translate("&aThe configuration file has been reloaded"));
    }
}
