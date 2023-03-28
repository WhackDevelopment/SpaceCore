package de.safespacegerman.core.listener;

import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.salami.ComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * SpaceCore; de.safespacegerman.core.listener:PlayerListener
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 28.03.2023
 */
public class PlayerListener implements Listener {

    private SpaceCorePlugin plugin;

    public PlayerListener(SpaceCorePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEvent(PlayerJoinEvent event) {
        if (event.getPlayer() == null) return;

        Player player = event.getPlayer();

        String nameColor = plugin.getPerms().loadUser(player.getUniqueId()).getCachedData().getMetaData().getMetaValue("username-color");
        if (nameColor == null) nameColor = plugin.getPerms().loadGroup("default").getCachedData().getMetaData().getMetaValue("username-color");
        if (nameColor == null) nameColor = "&7";
        String playerPrefix = plugin.getPerms().getPrefix(player.getUniqueId());
        if (playerPrefix == null) playerPrefix = "&r";

        player.displayName(
                ComponentSerializer.etAndHEX.deserialize(
                        playerPrefix + "&r" + nameColor + player.getName() + "&r"
                )
        );
        player.customName(
                ComponentSerializer.etAndHEX.deserialize(
                        playerPrefix + "&r" + nameColor + player.getName() + "&r"
                )
        );
        player.playerListName(
                ComponentSerializer.etAndHEX.deserialize(
                        playerPrefix + "&r" + nameColor + player.getName() + "&r"
                )
        );

    }

}
