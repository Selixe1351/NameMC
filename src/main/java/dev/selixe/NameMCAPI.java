package dev.selixe;

import dev.selixe.utils.ColorUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@UtilityClass
public class NameMCAPI {

    /**
     * Scan a uuid
     * @param uuid the uuid
     * @return the scanner
     */
    public Scanner scan(UUID uuid) {
        try {
            return new Scanner((new URL("https://api.namemc.com/server/" + NameMC.getInstance().getConfiguration().getConfig().getString("ip") + "/likes?profile=" + uuid).openStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Check if an uuid can vote
     * @param uuid the uuid
     * @return if the uuid can vote
     */
    public boolean canVote(UUID uuid) {
        try (Scanner scanner = scan(uuid)) {
            return Boolean.parseBoolean(scanner.next());
        }
    }

    /**
     * Check if an uuid has voted
     * @param uuid the uuid
     * @return if the uuid has voted
     */
    public boolean hasVoted(UUID uuid) {
        return NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(uuid.toString());
    }

    /**
     * Make a player vote
     * @param player the player
     */
    public void vote(Player player) {
        if (NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(player.getUniqueId().toString())) {
            return;
        }
        List<String> claimed = NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed");
        claimed.add(player.getUniqueId().toString());
        NameMC.getInstance().getConfiguration().getConfig().set("claimed", claimed);
        NameMC.getInstance().getConfiguration().saveConfig();
        NameMC.getInstance().getConfiguration().reloadConfig();
        claim(player);
    }

    /**
     * Remove a uuid's vote
     * @param uuid the uuid
     */
    public void unVote(UUID uuid) {
        if (!NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed").contains(uuid.toString())) {
            return;
        }
        List<String> claimed = NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed");
        claimed.remove(uuid.toString());
        NameMC.getInstance().getConfiguration().getConfig().set("claimed", claimed);
        NameMC.getInstance().getConfiguration().saveConfig();
        NameMC.getInstance().getConfiguration().reloadConfig();
    }

    /**
     * Make a player claim the vote rewards
     * @param player the player
     */
    public void claim(Player player) {
        NameMC.getInstance().getConfiguration().getConfig().getStringList("claimed-message").forEach(s -> player.sendMessage(ColorUtils.translate(s)));
        for (String rewards : NameMC.getInstance().getConfig().getStringList("rewards")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), rewards.replace("%player%", player.getName()));
        }
    }
}
