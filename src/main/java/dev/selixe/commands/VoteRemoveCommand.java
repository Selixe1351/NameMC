package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.NameMCAPI;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import dev.selixe.utils.command.parameter.Param;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) 2024 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class VoteRemoveCommand {

    @Command(names = {"vote remove"}, permission = "namemc.staff")
    public void execute(CommandSender sender, @Param(name = "target") OfflinePlayer target) {
        if (!NameMCAPI.hasVoted(target.getUniqueId())) {
            sender.sendMessage(ColorUtils.translate(NameMC.getInstance().getConfiguration().getConfig().getString("staff.has-not-voted", "").replace("%target%", target.getName())));
            return;
        }
        NameMCAPI.unVote(target.getUniqueId());
        sender.sendMessage(ColorUtils.translate(NameMC.getInstance().getConfiguration().getConfig().getString("staff.removed-vote", "").replace("%target%", target.getName())));
    }
}