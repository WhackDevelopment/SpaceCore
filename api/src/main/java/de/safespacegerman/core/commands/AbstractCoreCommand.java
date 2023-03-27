package de.safespacegerman.core.commands;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * SpaceCore; de.safespacegerman.core.commands:AbstractCoreCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public abstract class AbstractCoreCommand extends Command implements ICoreCommand {

    protected static final List<String> COMMON_DURATIONS = ImmutableList.of("1", "60", "600", "3600", "86400");
    protected static final List<String> COMMON_DATE_DIFFS = ImmutableList.of("1m", "15m", "1h", "3h", "12h", "1d", "1w", "1mo", "1y");
    private static final Pattern ARGUMENT_PATTERN = Pattern.compile("([ :>])(([\\[<])[A-Za-z |]+[>\\]])");

    private final transient Map<String, String> usageStrings = new LinkedHashMap<>();

    public AbstractCoreCommand(String name) {
        super(name);
    }

    private void addUsageString(final String usage, final String description) {
        final StringBuffer buffer = new StringBuffer();
        final Matcher matcher = ARGUMENT_PATTERN.matcher(usage);
        while (matcher.find()) {
            final String color = matcher.group(3).equals("<") ? "pflicht" : "optional";
            matcher.appendReplacement(buffer, "$1" + color + matcher.group(2).replace("|", "oder" + "|" + color) + "&r");
        }
        matcher.appendTail(buffer);
        usageStrings.put(buffer.toString(), description);
    }

    @Override
    public Map<String, String> getUsageStrings() {
        return usageStrings;
    }

    public static String getFinalArg(final String[] args, final int start) {
        final StringBuilder bldr = new StringBuilder();
        for (int i = start; i < args.length; i++) {
            if (i != start) {
                bldr.append(" ");
            }
            bldr.append(args[i]);
        }
        return bldr.toString();
    }

    @Override
    public boolean execute(@NotNull final CommandSender sender, @NotNull final String commandLabel, final @NotNull String[] args) {
        CommandSource source = new CommandSource(sender);
        try {
            this.run(source, commandLabel, this, args);
        } catch (Exception e) {
            e.printStackTrace();
            this.error(sender, e, commandLabel);
        }
        return false;
    }

    protected List<String> getTabCompleteOptions(final CommandSource sender, final String commandLabel, final String[] args) {

        // No tab completion results
        return getPlayers();
    }

    protected List<String> getPlayers() {
        final List<String> players = Lists.newArrayList();
        for (final Player player : Bukkit.getOnlinePlayers()) {
            players.add(player.getName());

        }
        return players;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull final CommandSender sender, @NotNull final String alias, final @NotNull String[] args) {
        return this.tabComplete(sender, alias, args, Bukkit.getWorld("world").getSpawnLocation());
    }

    @Override
    public @NotNull List<String> tabComplete(
            @NotNull final CommandSender sender,
            @NotNull final String alias,
            final @NotNull String[] args,
            @Nullable final Location location
    ) {
        try {
            if (args.length == 0) {
                // Shouldn't happen, but bail out early if it does so that args[0] can always be used
                return Collections.emptyList();
            }
            CommandSource source = new CommandSource(sender);
            final List<String> options = tab(source, alias, this, args);
            if (options == null) {
                return Collections.emptyList();
            } else {
                return options;
            }
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    @Override
    public abstract void run(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args);

    @Override
    public abstract List<String> tab(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args);

    @Override
    public abstract void error(final CommandSender sender, final Throwable throwable, final String commandLabel);

}
