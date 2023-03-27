package de.safespacegerman.core;

import de.safespacegerman.core.salami.ComponentSerializer;
import net.kyori.adventure.text.Component;

/**
 * SpaceCore; de.safespacegerman.core:Constants
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class Constants {

    private Constants() {} // prevent instantiation

    public static final String PREFIX = "&r[&9Space&3Core&r]&r";
    public static final String CHAT_PREFIX = PREFIX + " &3»&r ";

    public static final Component CHAT_PREFIX_COMPONENT = ComponentSerializer.etAndHEX.deserialize(CHAT_PREFIX);
    public static final String HEADER = "&r&6============= &r" + PREFIX + " &r&6=============&r";
    public static final String FOOTER = "&r&6============= &r" + PREFIX + " &r&6=============&r";
    public static final Component PREFIX_COMPONENT = ComponentSerializer.etAndHEX.deserialize(PREFIX);

    public static String SERVER_NAME = "";
    public static String VERSION = "";

}
