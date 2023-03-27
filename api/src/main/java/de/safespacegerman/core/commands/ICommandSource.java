package de.safespacegerman.core.commands;

import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * SpaceCore; de.safespacegerman.core.commands:ICommandSource
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public interface ICommandSource extends CommandSender {
    String getName();
    Component getDisplayName();
    CommandSender sender();
    Player player();
    ConsoleCommandSender console();
    boolean isPlayer();
    boolean isConsole();
}
