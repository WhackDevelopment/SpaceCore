package de.safespacegerman.core.utils;

import de.safespacegerman.core.Constants;
import de.safespacegerman.core.salami.ComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.Collection;

/**
 * SpaceCore; de.safespacegerman.core.utils:ChatUtil
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class ChatUtil {

    /**
     * @param message
     * @return component
     */
    public static Component toColoredComponent(String message) {
        return ComponentSerializer.etAndHEX.deserialize(message);
    }

    /**
     * Translate placeholders of placeholder api ( if not enabled placeholders will not be translated )
     *
     * @param source
     * @param message
     * @return messageReplacement
     */
    public static String replacePlaceholders(CommandSender source, String message) {
        return message; // TODO: replace placeholders
    }

    /**
     * Reply with a message to a sender
     *
     * @param source
     * @param message
     * @return success
     */
    public static boolean reply(CommandSender source, String message) {
        return replySenderComponent(source, message);
    }

    public static boolean replyError(CommandSender source) {
        return reply(source, "&cError beim ausführen des Commands.");
    }

    /**
     * Reply with a message to a sender
     *
     * @param source
     * @param message
     * @return success
     */
    public static boolean replySenderComponent(CommandSender source, String message) {
        source.sendMessage(toColoredComponent("").append(Constants.CHAT_PREFIX_COMPONENT).append(toColoredComponent(message)));
        return true;
    }

    /**
     * Reply with a message to a sender replyUnPrefixedSenderComponent
     *
     * @param source
     * @param message
     * @return success
     */
    public static boolean replyUnPrefixedSenderComponent(CommandSender source, String message) {
        source.sendMessage(toColoredComponent(message));
        return true;
    }

    /**
     * Reply with a message to a sender replyUnPrefixedSenderComponent
     *
     * @param source
     * @param message
     * @return success
     */
    public static boolean replyUnPrefixed(CommandSender source, String message) {
        return replyUnPrefixedSenderComponent(source, message);
    }

    /**
     * Reply with a component message to a sender
     *
     * @param source
     * @param message
     * @return success
     */
    public static boolean replySender(CommandSender source, Component message) {
        source.sendMessage(message);
        return true;
    }

    public static boolean startsWithIgnoreCase(String string, String prefix) {
        if (string.length() < prefix.length()) {
            return false;
        }
        return string.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static void copyPartialMatches(String input, Collection<String> available, Collection<String> toAppend) {
        for (String string : available) {
            if (startsWithIgnoreCase(string, input)) {
                toAppend.add(string);
            }
        }
    }


    /**
     * Reply with a actionbar popup to a player
     *
     * @param sender
     * @param message
     * @return success
     */
    public static boolean replyHologramComponent(Player sender, String message) {
        sender.sendActionBar(
                ComponentSerializer.etAndHEX.deserialize(message)
        );
        return true;
    }

    public static boolean replyActionBarComponent(Player sender, String message) {
        return replyHologramComponent(sender, message);
    }

    /**
     * reply with a title
     *
     * @param sender
     * @param message ( The smaller shown text of the title )
     * @return success
     */
    public static boolean replyTitleComponent(Player sender, String message) {
        sender.showTitle(
                Title.title(
                        Component.empty(),
                        ComponentSerializer.etAndHEX.deserialize(message)
                )
        );
        return true;
    }

    public static boolean replyTitleComponent(Player sender, String title, String message) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message)
                )
        );
        return true;
    }

    /**
     * reply with a title
     *
     * @param sender
     * @param title   ( The bigger shown text of the title )
     * @param message ( The smaller shown text of the title )
     * @param fadeIn  the time to show the title in seconds
     * @param stay    the time to stay of title in seconds
     * @param fadeOut the time to hide the title in seconds
     * @return success
     */
    public static boolean replyTitleComponent(Player sender, String title, String message, int fadeIn, int stay, int fadeOut) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message),
                        Title.Times.times(
                                Duration.ofSeconds(fadeIn),
                                Duration.ofSeconds(stay),
                                Duration.ofSeconds(fadeOut)
                        )
                )
        );
        return true;
    }

    public static boolean replyTitleComponent(Player sender, String title, String message, int stay) {
        sender.showTitle(
                Title.title(
                        ComponentSerializer.etAndHEX.deserialize(title),
                        ComponentSerializer.etAndHEX.deserialize(message),
                        Title.Times.times(
                                Duration.ofSeconds(1),
                                Duration.ofSeconds(stay),
                                Duration.ofSeconds(1)
                        )
                )
        );
        return true;
    }

    /**
     * Translate placeholders of placeholder api ( if not enabled placeholders will not be translated )
     *
     * @param player
     * @param message
     * @return messageReplacement
     */
    public static String replacePlaceholders(Player player, String message) {
        return message; // TODO: replace placeholders
    }

    /**
     * Check if placeholder api is enabled
     *
     * @return enabled
     */
    public static boolean isPlaceholderAPIEnabled() {
        return Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

}
