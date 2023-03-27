package de.safespacegerman.core.listener;

import de.safespacegerman.core.SpaceCorePlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * SpaceCore; de.safespacegerman.core.listener:PortalEnterListener
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class PortalEnterListener implements Listener {

    private Map<UUID, Long> lastNotify = new HashMap<>();

    private SpaceCorePlugin plugin;

    public PortalEnterListener(SpaceCorePlugin plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onEvent(PlayerPortalEvent event) {
        long now = System.currentTimeMillis();

        if (event.getCause() == PlayerPortalEvent.TeleportCause.END_PORTAL) {
            if (!event.getPlayer().hasPermission("core.portal.enter.end")) {
                Player target = event.getPlayer();
                try {
                    int factor = 2;
                    int offsetX = target.getLocation().getX() > target.getLocation().getX() ? factor : -factor;
                    int offsetZ = target.getLocation().getZ() > target.getLocation().getZ() ? factor : -factor;
                    target.setVelocity(new Vector(offsetX, 1, offsetZ));
                } catch (Exception x) {
                }
                event.setCancelled(true);
                if (now - lastNotify.getOrDefault(target.getUniqueId(), 0L) > 5000) { // notify max each 5 sec
                    lastNotify.put(target.getUniqueId(), now);
                    target.sendMessage("§cDu kannst das End noch nicht betreten.");
                }
            }
        }

        if (event.getCause() == PlayerPortalEvent.TeleportCause.NETHER_PORTAL) {
            if (!event.getPlayer().hasPermission("core.portal.enter.nether")) {
                Player target = event.getPlayer();
                try {
                    int factor = 2;
                    int offsetX = target.getLocation().getX() > target.getLocation().getX() ? factor : -factor;
                    int offsetZ = target.getLocation().getZ() > target.getLocation().getZ() ? factor : -factor;
                    target.setVelocity(new Vector(offsetX, 1, offsetZ));
                } catch (Exception x) {
                }
                event.setCancelled(true);
                if (now - lastNotify.getOrDefault(target.getUniqueId(), 0L) > 5000) { // notify max each 5 sec
                    lastNotify.put(target.getUniqueId(), now);
                    target.sendMessage("§cDu kannst den Nether noch nicht betreten.");
                }
            }
        }


    }

}
