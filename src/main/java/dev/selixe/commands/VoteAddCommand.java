package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.NameMCAPI;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import dev.selixe.utils.command.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 2024 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class VoteAddCommand {

    @Command(names = {"vote add", "forcevote"}, permission = "namemc.staff")
    public void execute(CommandSender sender, @Param(name = "target") Player target) {
        if (NameMCAPI.hasVoted(target.getUniqueId())) {
            sender.sendMessage(ColorUtils.translate(NameMC.getInstance().getConfiguration().getConfig().getString("staff.has-already-voted", "").replace("%target%", target.getName())));
            return;
        }
        NameMCAPI.vote(target);
        sender.sendMessage(ColorUtils.translate(NameMC.getInstance().getConfiguration().getConfig().getString("staff.added-vote", "").replace("%target%", target.getName())));
    }
}
