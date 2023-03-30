package de.safespacegerman.core.types;

/**
 * SpaceCore; de.safespacegerman.core.types:DefaultDeathMessages
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 30.03.2023
 */
public enum DefaultDeathMessages {
    NATURAL_CONTACT("%player% wurde von einem %block% zusammengeschlagen"),
    NATURAL_ENTITY_SWEEP_ATTACK("%player% wurde weggefegt"),
    NATURAL_PROJECTILE("%player% wurde von einem %projectile% getroffen"),
    NATURAL_SUFFOCATION("%player% wurde erdrückt"),
    NATURAL_FALL("%player% ist gestürzt"),
    NATURAL_FIRE("%player% wurde zu Asche verbrannt"),
    NATURAL_FIRE_TICK("%player% konnte die Hitze nicht ertragen"),
    NATURAL_MELTING("%player% ist geschmolzen"),
    NATURAL_LAVA("%player% war nicht aus Netherrit gemacht"),
    NATURAL_DROWNING("%player% braucht Luft"),
    NATURAL_BLOCK_EXPLOSION("%player% wurde von %block% in die Luft gesprengt"),
    NATURAL_ENTITY_EXPLOSION("%player% wurde von %killer% in die Luft gesprengt"),
    NATURAL_VOID("%player% ist ins Nichts gefallen"),
    NATURAL_LIGHTNING("%player% konnte die Spannung nicht ertragen"),
    NATURAL_SUICIDE("%player% hat sich selbst das Leben genommen"),
    NATURAL_STARVATION("%player% brauchte etwas zu essen"),
    NATURAL_POISON("%player% hat das Gegenmittel nicht rechtzeitig gefunden"),
    NATURAL_MAGIC("%player% wurde von einem Schadenszauber getroffen"),
    NATURAL_WITHER("%player% ist dahingegammelt"),
    NATURAL_FALLING_BLOCK("%player% wurde von einem herabfallenden %block% zerquetscht"),
    NATURAL_THORNS("%player% hat sich selbst verletzt"),
    NATURAL_DRAGON_BREATH("%player% konnte den stinkenden Atem des Drachen nicht ertragen"),
    NATURAL_CUSTOM("%player% starb aus unbekannten Gründen"),
    NATURAL_FLY_INTO_WALL("%player% ist zerschellt"),
    NATURAL_HOT_FLOOR("%player% hat sich die Füße verbrannt"),
    NATURAL_CRAMMING("%player% wurde zerquetscht"),
    NATURAL_DRYOUT("%player% hätte im Wasser bleiben sollen"),
    NATURAL_FREEZE("%player% hat vergessen, eine Jacke mitzunehmen"),
    NATURAL_SONIC_BOOM("%player% Trommelfelle sind explodiert"),

    MOB_ZOMBIE_SOLO("%player% wurde von einem %killer% gefressen"),
    MOB_ZOMBIE_GANG("%player% wurde von einer Horde %killer% gefressen"),

    MOB_UNDEFINED("%player% wurde von %killer% getötet"),
    MAGIC("%player% wurde auf magische Weise getötet");

    private final String message;

    DefaultDeathMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return getMessage();
    }
}



