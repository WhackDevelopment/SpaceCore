package de.safespacegerman.core.salami;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * SpaceCore; de.safespacegerman.core.salami:ComponentSerializer
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class ComponentSerializer {

    private ComponentSerializer() {} // prevent instantiation

    public static final LegacyComponentSerializer sectionAndHEX = LegacyComponentSerializer.builder().character('§').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer unusualSectionAndHEX = LegacyComponentSerializer
            .builder()
            .character('§')
            .hexCharacter('#')
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();
    public static final LegacyComponentSerializer etAndHEX = LegacyComponentSerializer.builder().character('&').hexCharacter('#').hexColors().build();
    public static final LegacyComponentSerializer etOnly = LegacyComponentSerializer.builder().character('&').build();
    public static final LegacyComponentSerializer sectionOnly = LegacyComponentSerializer.builder().character('§').build();

}
