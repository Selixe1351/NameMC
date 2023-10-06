package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.NameMCAPI;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of source code is permitted only if given
 * permission from the original author: Selixe
 */

public class NameMCCommand {

    @SneakyThrows
    @Command(names = {"namemc"}, permission = "namemc.vote", playerOnly = true)
    public void execute(Player player) {

        if (NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(player.getUniqueId().toString())) {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("already-claimed-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
            return;
        }

        if (NameMCAPI.canVote(player.getUniqueId())) {
            NameMCAPI.vote(player);
        } else {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("command-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
        }
    }
}
