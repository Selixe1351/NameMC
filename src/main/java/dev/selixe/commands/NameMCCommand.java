package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.swift.other.ColorUtils;
import dev.swift.util.command.Command;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.net.URL;
import java.util.List;
import java.util.Scanner;

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
            //Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ColorUtils.translate("&d&lNameMC &7\u239c &d" + player.getName() + " &fhas voted on &dNameMC &fand received 5x &dVote &fkeys.")));
            for (String rewards : NameMC.getInstance().getConfig().getStringList("rewards")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards.replace("%player%", player.getName()));
            }
            List<String> claimed = NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed");
            claimed.add(player.getUniqueId().toString());
            NameMC.getInstance().getConfiguration().getConfig().set("claimed", claimed);
            NameMC.getInstance().getConfiguration().updateConfig();
        } else {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("command-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
        }

        scanner.close();
    }
}
