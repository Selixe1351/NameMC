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
            player.sendMessage(ColorUtils.translate("&d&l" + NameMC.getInstance().getConfiguration().getConfig().getString("name") + " &7\u239c &cYou have already claimed your rewards."));
            return;
        }

        if (status) {
            player.sendMessage(ColorUtils.translate("&d&l" + NameMC.getInstance().getConfiguration().getConfig().getString("name") + " &7\u239c &aYou've successfully voted on NameMC."));
            //Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(ColorUtils.translate("&d&lNameMC &7\u239c &d" + player.getName() + " &fhas voted on &dNameMC &fand received 5x &dVote &fkeys.")));
            for (String rewards : NameMC.getInstance().getConfig().getStringList("rewards")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards.replace("%player%", player.getName()));
            }
            List<String> claimed = NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed");
            claimed.add(player.getUniqueId().toString());
            NameMC.getInstance().getConfiguration().getConfig().set("claimed", claimed);
            NameMC.getInstance().getConfiguration().updateConfig();
        } else {
            player.sendMessage(" ");
            player.sendMessage(ColorUtils.translate("&d&lNameMC"));
            player.sendMessage(ColorUtils.translate("&7Vote on the website below to claim your rewards"));
            player.sendMessage(ColorUtils.translate(" "));
            player.sendMessage(ColorUtils.translate(" &7\u25b6 &fhttps://namemc.com/server/" + NameMC.getInstance().getConfiguration().getConfig().getString("ip")));
            player.sendMessage(" ");
            player.sendMessage(ColorUtils.translate("&7Once you've voted on the website, type /namemc again."));
        }

        scanner.close();
    }
}
