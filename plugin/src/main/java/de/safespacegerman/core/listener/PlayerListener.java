package de.safespacegerman.core.listener;

import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.salami.ComponentSerializer;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

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
                                            "&7Username: &a" + sender.getName(),
                                            "&7Prefix: " + plugin.getPerms().getPrefix(sender.getUniqueId()),
                                            "&7Level: &9" + sender.getLevel(),
                                            "&7Gamemode: &6" + sender.getGameMode()
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
