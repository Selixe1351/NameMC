package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.NameMCAPI;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import dev.selixe.utils.command.parameter.Param;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

public class VoteCheckCommand {

    @Command(names = {"vote check"}, permission = "namemc.staff")
    public void execute(CommandSender sender, @Param(name = "target") OfflinePlayer target) {
        sender.sendMessage(ColorUtils.translate(NameMCAPI.hasVoted(target.getUniqueId()) ?
                NameMC.getInstance().getConfiguration().getConfig().getString("staff.has-voted", "").replace("%target%", target.getName()) :
                NameMC.getInstance().getConfiguration().getConfig().getString("staff.has-not-voted", "").replace("%target%", target.getName())));
    }
}
