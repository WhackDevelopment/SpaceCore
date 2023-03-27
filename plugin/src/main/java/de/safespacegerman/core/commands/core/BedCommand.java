package de.safespacegerman.core.commands.core;

import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.commands.CommandSource;
import de.safespacegerman.core.commands.CoreCommand;
import de.safespacegerman.core.utils.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * SpaceCore; de.safespacegerman.core.commands.core:BedCommand
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class BedCommand extends CoreCommand implements Listener {

    private SpaceCorePlugin plugin;

    private Map<UUID, TeleportQueuedPlayer> playerQueue;

    public BedCommand(SpaceCorePlugin plugin) {
        super("bed");
        this.plugin = plugin;

        this.setDescription("Default Core Command Bed.");
        this.setPermission("core.command." + this.getName().toLowerCase());
        this.setAliases(List.of("bett"));

        playerQueue = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            try {
                if (playerQueue.isEmpty()) {
                    return;
                }
                playerQueue.values().forEach(queuedPlayer -> {
                    try {
                        if (queuedPlayer != null && queuedPlayer.isShouldTeleport()) {
                            queuedPlayer.timer();
                        }
                    } catch (Exception e) {
                    }
                });
            } catch (Exception e) {
            }
        }, 20, 20);
    }

    @Override
    public void run(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args) {
        if (!sender.isPlayer()) {
            ChatUtil.replySenderComponent(sender, "&cYou can only run this command as a player.");
            return;
        }
        if (playerQueue.containsKey(sender.player().getUniqueId())) {
            ChatUtil.replySenderComponent(sender, "&7Du wirst bereits teleportiert.");
            return;
        }
        if(sender.player().getBedSpawnLocation() == null) {
            ChatUtil.replySenderComponent(sender, "&7Du hast noch kein Bett gesetzt.");
            return;
        }
        playerQueue.put(sender.player().getUniqueId(), new TeleportQueuedPlayer(5, sender.player()));
        ChatUtil.replySenderComponent(sender, "&7Du wurdest zur &3TeleportQueue &7hinzugefügt.");
    }

    @Override
    public List<String> tab(final CommandSource sender, final String commandLabel, final Command cmd, final String[] args) {
        return null;
    }

    @Override
    public void error(final CommandSender sender, final Throwable throwable, final String commandLabel) {
        ChatUtil.replyError(sender);
    }

    @EventHandler
    public void onMovedPlayer(PlayerMoveEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (playerQueue.get(event.getPlayer().getUniqueId()) != null) {
            playerQueue.get(event.getPlayer().getUniqueId()).moveEvent(event);
        }
    }

    @EventHandler
    public void onMovedPlayer(PlayerInteractEvent event) {
        if (event.getPlayer() == null) {
            return;
        }
        if (playerQueue.get(event.getPlayer().getUniqueId()) != null) {
            playerQueue.get(event.getPlayer().getUniqueId()).interactEvent(event);
        }
    }

    class TeleportQueuedPlayer {

        private final int delay;
        private final Player toTeleport;
        private boolean shouldTeleport = true;
        private int second = 0;

        public TeleportQueuedPlayer(int delay, Player toTeleport) {
            this.delay = delay;
            this.toTeleport = toTeleport;
        }

        public void addToSpawn() {
            if (shouldTeleport) {
                toTeleport.playSound(toTeleport.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                if(toTeleport.getBedSpawnLocation() == null) return;
                toTeleport.teleport(toTeleport.getBedSpawnLocation());
            }
            shouldTeleport = false;
            playerQueue.remove(toTeleport.getUniqueId());
        }

        public void remove() {
            shouldTeleport = false;
            playerQueue.remove(toTeleport.getUniqueId());
            ChatUtil.replySenderComponent(toTeleport, "&7Teleportation abgebrochen.");
        }

        public void stat(int s) {
            if (shouldTeleport) {
                ChatUtil.replyTitleComponent(toTeleport, "&6Teleport",
                        "&7Du wirst in &3" + (delay - s) + " &7Sekunden Teleportiert.",
                        1, 1, 1
                );
                toTeleport.playSound(toTeleport.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1f, 1f);
            } else {
                remove();
            }
        }

        public void moveEvent(PlayerMoveEvent event) {
            if (!shouldTeleport) {
                return;
            }
            if (event.getPlayer() == null) {
                return;
            }
            if (!event.getPlayer().getUniqueId().equals(toTeleport.getUniqueId())) {
                return;
            }
            if (event.getFrom().getX() != event.getTo().getX()
                    || event.getFrom().getY() != event.getTo().getY()
                    || event.getFrom().getZ() != event.getTo().getZ()) {
                remove();
            }
        }

        public void interactEvent(PlayerInteractEvent event) {
            if (!shouldTeleport) {
                return;
            }
            if (event.getPlayer() == null) {
                return;
            }
            if (!event.getPlayer().getUniqueId().equals(toTeleport.getUniqueId())) {
                return;
            }
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)
                    || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                remove();
            }
        }

        public void timer() {
            if (second == 0) {
                ChatUtil.replySenderComponent(toTeleport, "&7Du wirst in &3" + delay + " &7Sekunden zum Bett teleportiert.");
            }
            if (second < delay) {
                stat(second);
            } else {
                addToSpawn();
            }
            second++;
        }

        public boolean isShouldTeleport() {
            return shouldTeleport;
        }

    }

}
