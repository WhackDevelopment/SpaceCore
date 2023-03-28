package de.safespacegerman.core.listener;

import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.naming.NameTag;
import de.safespacegerman.core.salami.ComponentSerializer;
import de.safespacegerman.core.utils.DateUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

/**
 * SpaceCore; de.safespacegerman.core.listener:PlayerListener
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 28.03.2023
 */
public class PlayerListener implements Listener {

    private SpaceCorePlugin plugin;

    private ChatRenderer chatRenderer;

    public PlayerListener(SpaceCorePlugin plugin) {
        this.plugin = plugin;
        this.chatRenderer = new Renderer();
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

        String groupId = plugin.getPerms().getDefaultPlayerGroupId(player.getUniqueId());
        Group playerGroup = plugin.getPerms().loadGroup(groupId);
        int playerGroupWeight = playerGroup.getWeight().orElse(0);

        String teamName = convertToSixDigits(playerGroupWeight) + "-" + groupId;
        Team playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(teamName);
        if (playerTeam == null) {
            playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(teamName);
        }

        playerTeam.addPlayer(player);

        Bukkit.getOnlinePlayers().forEach(current -> {
            try {
                String currentPrefix = plugin.getPerms().getPrefix(current.getUniqueId());
                if (currentPrefix == null) currentPrefix = "&r";
                new NameTag(current).setPrefix(currentPrefix + "&r").build();
            } catch (Exception e) {
            }
        });
    }


    private String convertToSixDigits(int num) {
        if (num > 100000) {
            return "100000";
        } else {
            return String.format("%06d", num);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncChatEvent event) {
        if (event.getPlayer() == null) return;
        if (event.isCancelled()) return;
        event.renderer(this.chatRenderer);
    }

    private class Renderer implements ChatRenderer {
        @Override
        public @NotNull Component render(@NotNull Player sender, @NotNull Component displayName, @NotNull Component message, @NotNull Audience receiver) {
            String messageColor = plugin.getPerms().loadUser(sender.getUniqueId()).getCachedData().getMetaData().getMetaValue("message-color");
            if (messageColor == null) messageColor = plugin.getPerms().loadGroup("default").getCachedData().getMetaData().getMetaValue("message-color");
            if (messageColor == null) messageColor = "&r&f";

            Component finalComponent = Component.join(
                    JoinConfiguration.noSeparators(),
                    displayName.hoverEvent(HoverEvent.showText(
                            ComponentSerializer.etAndHEX.deserialize(
                                    String.join("\n", new String[]{
                                            "&7Username: &9" + sender.getName(),
                                            "&7Prefix: " + plugin.getPerms().getPrefix(sender.getUniqueId()),
                                            "&7Level: &9" + sender.getLevel(),
                                            "&7Gamemode: &9" + sender.getGameMode().toString().toLowerCase(),
                                            "&7Erstmalig Gejoined: &9" + DateUtils.convertDateToString(new Date(sender.getFirstPlayed())),
                                            "&7Zuletzt Gejoined: &9" + DateUtils.convertDateToString(new Date(sender.getLastLogin())),
                                            "&7Aktuelle Welt: &9" + sender.getWorld().getName()
                                    })
                            )
                    )),
                    ComponentSerializer.etAndHEX.deserialize(" &7» "),
                    ComponentSerializer.etAndHEX.deserialize(messageColor),
                    message
            );

            return finalComponent;
        }
    }

}
