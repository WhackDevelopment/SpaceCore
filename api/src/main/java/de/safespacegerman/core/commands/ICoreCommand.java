package de.safespacegerman.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Map;

/**
 * SpaceCore; de.safespacegerman.core.commands:ICoreCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public interface ICoreCommand {

    Map<String, String> getUsageStrings();

    void run(CommandSource sender, String commandLabel, Command cmd, String[] args) throws Exception;

    List<String> tab(CommandSource sender, String commandLabel, Command cmd, String[] args);

    void error(CommandSender sender, Throwable throwable, String commandLabel);

}
