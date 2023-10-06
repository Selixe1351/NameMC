package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

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
        Scanner scanner = new Scanner((new URL("https://api.namemc.com/server/" + NameMC.getInstance().getConfiguration().getConfig().getString("ip") + "/likes?profile=" + player.getUniqueId())).openStream());
        boolean status = Boolean.parseBoolean(scanner.next());

        if (NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(player.getUniqueId().toString())) {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("already-claimed-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
            return;
        }

        if (status) {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
            for (String rewards : NameMC.getInstance().getConfig().getStringList("rewards")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards.replace("%player%", player.getName()));
            }
            List<String> claimed = NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed");
            claimed.add(player.getUniqueId().toString());
            NameMC.getInstance().getConfiguration().getConfig().set("claimed", claimed);
            NameMC.getInstance().getConfiguration().saveConfig();
            NameMC.getInstance().getConfiguration().reloadConfig();
        } else {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("command-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
        }

        scanner.close();
    }
}
