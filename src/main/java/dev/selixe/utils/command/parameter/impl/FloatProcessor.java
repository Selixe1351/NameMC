package dev.selixe.utils.command.parameter.impl;

import dev.selixe.utils.command.parameter.Processor;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Copyright (c) 2023 Selixe
 * <p>
 * Usage or redistribution of compiled JAR file and or source code is permitted only if given
 * permission from the original author: Selixe
 */

public class FloatProcessor extends Processor<Float> {

    public Float process(CommandSender sender, String supplied) {
        try {
            return Float.parseFloat(supplied);
        } catch(Exception ex) {
            sender.sendMessage(ChatColor.RED + "The value you entered '" + supplied + "' is an invalid float.");
            return 0F;
        }
    }
}
