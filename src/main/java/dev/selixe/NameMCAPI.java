package dev.selixe;

import dev.selixe.utils.ColorUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@UtilityClass
public class NameMCAPI {

    /**
     * Check if an uuid can vote
     * @param uuid the uuid
     * @return if the uuid can vote
     */
    public boolean canVote(UUID uuid) {
        String urlString = "https://api.namemc.com/server/" + NameMC.getInstance().getConfiguration().getConfig().getString("ip") + "/likes?profile=" + uuid;
        URL url;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Bukkit.getLogger().warning("Malformed URL: " + urlString);
            return false;
        }

        try (Scanner scanner = new Scanner(url.openStream())) {
            return Boolean.parseBoolean(scanner.next());
        } catch (IOException e) {
            Bukkit.getLogger().warning("Failed to open stream for URL: " + urlString);
            return false;
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
