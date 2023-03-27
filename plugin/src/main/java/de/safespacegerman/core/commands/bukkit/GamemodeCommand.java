package de.safespacegerman.core.commands.bukkit;

import com.google.common.collect.Lists;
import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.commands.CommandSource;
import de.safespacegerman.core.commands.CoreCommand;
import de.safespacegerman.core.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * SpaceCore; de.safespacegerman.core.commands.bukkit:GamemodeCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class GamemodeCommand extends CoreCommand {

    private SpaceCorePlugin plugin;

    public GamemodeCommand(SpaceCorePlugin plugin) {
        super("gamemode");
        this.plugin = plugin;
        this.setUsage("/gamemode <mode> [spieler]");
        this.setDescription("Default Core Command Gamemode.");
        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setAliases(Lists.newArrayList("gm"));
    }

    @Override
    public void run(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args) {
        GameMode targetMode = null;
        Player target = null;

        if (args.length == 0) {
            ChatUtil.replySenderComponent(sender, this.getUsage());
            return;
        }

        if (args.length == 1) {
            if (!sender.isPlayer()) {
                ChatUtil.replySenderComponent(sender, "&cYou can only run this command as a player.");
                return;
            }
            Player player = sender.player();
            target = player;
            switch (args[0].toLowerCase()) {
                case "survival":
                case "s":
                case "0":
                    if (!sender.hasPermission(this.getPermission() + ".survival")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".survival");
                        return;
                    }
                    targetMode = GameMode.SURVIVAL;
                    break;
                case "creative":
                case "c":
                case "1":
                    if (!sender.hasPermission(this.getPermission() + ".creative")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".creative");
                        return;
                    }
                    targetMode = GameMode.CREATIVE;
                    break;
                case "adventure":
                case "a":
                case "2":
                    if (!sender.hasPermission(this.getPermission() + ".adventure")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".adventure");
                        return;
                    }
                    targetMode = GameMode.ADVENTURE;
                    break;
                case "spectator":
                case "spec":
                case "3":
                    if (!sender.hasPermission(this.getPermission() + ".spectator")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".spectator");
                        return;
                    }
                    targetMode = GameMode.SPECTATOR;
                    break;
                default:
                    ChatUtil.replySenderComponent(sender, "&7Der angegebene Gamemode ist nicht verfügbar.");
                    return;
            }
            target.setGameMode(targetMode);
            if (targetMode.equals(GameMode.SURVIVAL) || targetMode.equals(GameMode.ADVENTURE)) {
                target.setFlying(false);
            }
            ChatUtil.replySenderComponent(
                    target,
                    "&7Dein Spielmodus wurde auf &3" + targetMode.name().substring(0, 1) + targetMode.name().toLowerCase().substring(1) + " &7gesetzt."
            );
            return;
        }

        if (args.length == 2) {
            if (!sender.hasPermission(this.getPermission() + ".others")
                    && !sender.hasPermission(this.getPermission() + ".*")) {
                return;
            }
            switch (args[0].toLowerCase()) {
                case "survival":
                case "s":
                case "0":
                    if (!sender.hasPermission(this.getPermission() + ".others.survival")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".others.survival");
                        return;
                    }
                    targetMode = GameMode.SURVIVAL;
                    break;
                case "creative":
                case "c":
                case "1":
                    if (!sender.hasPermission(this.getPermission() + ".others.creative")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".others.creative");
                        return;
                    }
                    targetMode = GameMode.CREATIVE;
                    break;
                case "adventure":
                case "a":
                case "2":
                    if (!sender.hasPermission(this.getPermission() + ".others.adventure")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".others.adventure");
                        return;
                    }
                    targetMode = GameMode.ADVENTURE;
                    break;
                case "spectator":
                case "spec":
                case "3":
                    if (!sender.hasPermission(this.getPermission() + ".others.spectator")
                            && !sender.hasPermission(this.getPermission() + ".others.*")
                            && !sender.hasPermission(this.getPermission() + ".*")) {
                        ChatUtil.replySenderComponent(sender, "&7Dir fehlt die Berechtigung: &3" + this.getPermission() + ".others.spectator");
                        return;
                    }
                    targetMode = GameMode.SPECTATOR;
                    break;
                default:
                    ChatUtil.replySenderComponent(sender, "&7Der angegebene Gamemode ist nicht verfügbar.");
                    return;
            }
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                ChatUtil.replySenderComponent(sender, "&7Der angegebene Spieler ist nicht online.");
                return;
            }
            target.setGameMode(targetMode);
            if (targetMode.equals(GameMode.SURVIVAL) || targetMode.equals(GameMode.ADVENTURE)) {
                target.setFlying(false);
            }
            ChatUtil.replySenderComponent(
                    target,
                    "&7Dein Spielmodus wurde auf &3" + targetMode.name().substring(0, 1) + targetMode.name().toLowerCase().substring(1) + " &7gesetzt."
            );
            ChatUtil.replySenderComponent(
                    sender,
                    "&7Du hast den Spielmodus von: &3" + target.getName() + " &7auf &3" + targetMode.name().substring(0, 1) + targetMode
                            .name()
                            .toLowerCase()
                            .substring(1) + " &7gesetzt."
            );
            return;
        }


        ChatUtil.replySenderComponent(sender, "&7Benutzung: &3" + this.getUsage());
    }

    @Override
    public List<String> tab(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args) {
        List<String> completions = new ArrayList<>();
        List<String> available = new ArrayList<>();

        switch (args.length) {
            case 1: {
                if (sender.hasPermission(this.getPermission() + ".survival")
                        || sender.hasPermission(this.getPermission() + ".*")) {
                    available.add("survival");
                    available.add("0");
                    available.add("s");
                }
                if (sender.hasPermission(this.getPermission() + ".creative")
                        || sender.hasPermission(this.getPermission() + ".*")) {
                    available.add("creative");
                    available.add("1");
                    available.add("c");
                }
                if (sender.hasPermission(this.getPermission() + ".adventure")
                        || sender.hasPermission(this.getPermission() + ".*")) {
                    available.add("adventure");
                    available.add("2");
                    available.add("a");
                }
                if (sender.hasPermission(this.getPermission() + ".spectator")
                        || sender.hasPermission(this.getPermission() + ".*")) {
                    available.add("spectator");
                    available.add("3");
                    available.add("spec");
                }

                break;
            }

            case 2: {
                if (!sender.hasPermission(this.getPermission() + ".others")) {
                    break;
                }
                for (Player all : Bukkit.getOnlinePlayers()) {
                    available.add(all.getName());
                }
                break;
            }
            default:
                break;
        }

        StringUtil.copyPartialMatches(args[0], available, completions);
        Collections.sort(completions);
        return completions;
    }

    @Override
    public void error(final CommandSender sender, final Throwable throwable, final String commandLabel) {
        ChatUtil.replyError(sender);
    }

}
