package de.safespacegerman.core.utils;

/**
 * SpaceCore; de.safespacegerman.core.utils:StringUtils
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 29.03.2023
 */
public class StringUtils {

    private StringUtils() {
    } // prevent instantiation

    public static String redactUrlsAndDomains(String input) {
        String regex = "(https?|ftp)://\\S+\\b|(?<=\\s|^)\\S+\\.(com|net|org|edu|gov|mil|biz|int|info|name|museum|coop|aero|[a-z]{2})\\b(?=\\s|$)";
        String replacement = "`[DOMAIN ENTFERNT]`";
        return input.replaceAll(regex, replacement);
    }

    public static String redactUrls(String input) {
        String regex = "(https?|ftp)://[^\\s/]+/\\S*";
        String replacement = "`[URL/LINK ENTFERNT]`";
        return input.replaceAll(regex, replacement);
    }

    public static String redactDiscordMentions(String input) {
        String regex = "<@[!&]?\\d+>|@everyone|@here|#\\S+";
        String replacement = "`[@MENTION ENTFERNT]`";
        return input.replaceAll(regex, replacement);
    }


}
