package dev.selixe.commands;

import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) 2024 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class VoteHelpCommand {

    @Command(names = {"vote", "vote help"}, permission = "namemc.staff")
    public void execute(CommandSender sender) {
        sender.sendMessage(ColorUtils.translate(" "));
        sender.sendMessage(ColorUtils.translate("&6&lNameMC"));
        sender.sendMessage(ColorUtils.translate(" &e&o/vote check <target>"));
        sender.sendMessage(ColorUtils.translate(" &e&o/vote add <target>"));
        sender.sendMessage(ColorUtils.translate(" &e&o/vote remove <target>"));
        sender.sendMessage(ColorUtils.translate(" "));
    }
}
