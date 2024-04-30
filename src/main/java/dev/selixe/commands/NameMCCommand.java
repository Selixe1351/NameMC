package dev.selixe.commands;

import dev.selixe.NameMC;
import dev.selixe.NameMCAPI;
import dev.selixe.utils.ColorUtils;
import dev.selixe.utils.command.Command;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class NameMCCommand {

    @SneakyThrows
    @Command(names = {"namemc"}, permission = "namemc.vote", playerOnly = true)
    public void execute(Player player) {

        if (NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(player.getUniqueId().toString())) {
            NameMC.getInstance().getConfiguration().getConfig().getStringList("already-claimed-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
            return;
        }

        // Run the canVote check asynchronously
        new BukkitRunnable() {
            @Override
            public void run() {
                boolean canVote = NameMCAPI.canVote(player.getUniqueId());
                // Ensure actions affecting the game state are run on the main thread
                Bukkit.getScheduler().runTask(NameMC.getInstance(), () -> {
                    if (canVote) {
                        NameMCAPI.vote(player);
                    } else {
                        NameMC.getInstance().getConfiguration().getConfig().getStringList("command-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
                    }
                });
            }
        }.runTaskAsynchronously(NameMC.getInstance());
    }
}
