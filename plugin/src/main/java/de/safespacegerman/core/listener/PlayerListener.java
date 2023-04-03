package de.safespacegerman.core.listener;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import de.safespacegerman.core.SpaceCorePlugin;
import de.safespacegerman.core.salami.ComponentSerializer;
import de.safespacegerman.core.utils.DateUtils;
import de.safespacegerman.core.utils.StringUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.JoinConfiguration;
import net.kyori.adventure.text.event.HoverEvent;
import net.luckperms.api.model.group.Group;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

import static de.safespacegerman.core.utils.DiscordWebhook.executeHook;

/**
 * SpaceCore; de.safespacegerman.core.listener:PlayerListener
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 28.03.2023
 */
public class PlayerListener implements Listener {

    private final String webhookURL;
    private SpaceCorePlugin plugin;

    private ChatRenderer chatRenderer;

    public PlayerListener(SpaceCorePlugin plugin) {
        this.plugin = plugin;
        this.chatRenderer = new Renderer();
        this.webhookURL = plugin.getConfig().getString("chatRelay", "");
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


            playerTeam.prefix(
                    ComponentSerializer.etAndHEX.deserialize(
                            playerPrefix + "&r" + nameColor
                    )
            );
            playerTeam.displayName(
                    ComponentSerializer.etAndHEX.deserialize(
                            playerPrefix
                    )
            );
        }

        playerTeam.addPlayer(player);
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
        try {
            handleChatToDiscord(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onQuitDiscord(PlayerQuitEvent event) {
        if (event.getPlayer() == null) return;
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("System");
        webhookMessageBuilder.setAvatarUrl(plugin.getConfig().getString("serverAvatar", "https://cdn.discordapp.com/attachments/1040778980992766054/1090748202854129744/ssg_bot.png"));
        webhookMessageBuilder.setContent(plugin.getConfig().getString("emoji.leave", "") + " **`" + event.getPlayer().getName() + "` hat den Server verlassen.**");
        executeHook(webhookMessageBuilder, webhookURL);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onJoinDiscord(PlayerJoinEvent event) {
        if (event.getPlayer() == null) return;
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("System");
        webhookMessageBuilder.setAvatarUrl(plugin.getConfig().getString("serverAvatar", "https://cdn.discordapp.com/attachments/1040778980992766054/1090748202854129744/ssg_bot.png"));
        webhookMessageBuilder.setContent(plugin.getConfig().getString("emoji.join", "") + " **`" + event.getPlayer().getName() + "` ist dem Server beigetreten.**");
        executeHook(webhookMessageBuilder, webhookURL);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinDiscord(PlayerDeathEvent event) {
        if (event.getPlayer() == null) return;
        if (event.isCancelled()) return;
        if (event.getEntityType() != EntityType.PLAYER) return;
        Player player = event.getPlayer();
        String deathMessage = getDeathMessage(player);
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("System");
        webhookMessageBuilder.setAvatarUrl(plugin.getConfig().getString("deathAvatar", "https://cdn.discordapp.com/attachments/1040778980992766054/1091043857598263386/skull_icon.png"));
        webhookMessageBuilder.setContent(deathMessage);
        executeHook(webhookMessageBuilder, webhookURL);
    }

    private String getDeathMessage(Player player) {
        // TODO: use DefaultDeathMessages.<messageType> and add replacements...
        return "<:ssg_death:1091398575981858887> **`" + player.getName() + "` ist gestorben.**";
    }

    private void sendPlayerMessage(Player player, String message) {
        message = StringUtils.redactUrls(message);
        message = StringUtils.redactUrlsAndDomains(message);
        message = StringUtils.redactDiscordMentions(message);
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername(player.getName());
        webhookMessageBuilder.setAvatarUrl("https://mc-heads.net/head/" + player.getUniqueId().toString() + "/512/right");
        webhookMessageBuilder.setContent(message);
        executeHook(webhookMessageBuilder, webhookURL);
    }


    private void handleChatToDiscord(AsyncChatEvent event) {
        Component originalMessageComponent = event.originalMessage();
        String originalMessage = ComponentSerializer.etAndHEX.serialize(originalMessageComponent);
        final String finalContent = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', originalMessage));
        new Thread(() -> sendPlayerMessage(event.getPlayer(), finalContent), "execute-chat-hook").start();
    }


}
