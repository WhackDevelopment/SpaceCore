package de.safespacegerman.core;

import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import de.safespacegerman.core.commands.bukkit.GamemodeCommand;
import de.safespacegerman.core.commands.core.BedCommand;
import de.safespacegerman.core.commands.core.SpawnCommand;
import de.safespacegerman.core.listener.PlayerListener;
import de.safespacegerman.core.listener.PortalEnterListener;
import de.safespacegerman.core.perms.PermissionManager;
import de.safespacegerman.core.utils.ChatUtil;
import de.safespacegerman.core.utils.DiscordWebhook;
import de.safespacegerman.core.utils.Resources;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * SpaceCore; de.safespacegerman.core:SpaceCorePlugin
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class SpaceCorePlugin extends JavaPlugin {
    private static SpaceCorePlugin instance;

    public static SpaceCorePlugin getInstance() {
        return instance;
    }

    private LuckPerms luckPerms;
    private PermissionManager perms;

    @Override
    public void onLoad() {
        if (instance != null) {
            throw new RuntimeException("This plugin can only be loaded once.");
        }
        instance = this;
    }

    @Override
    public void onEnable() {
        Constants.VERSION = Resources.readToString("version.txt").trim();

        this.loadLuckPerms();
        this.saveDefaultConfig();
        this.reloadConfig();

        this.perms = new PermissionManager(this.luckPerms);

        CommandMap cmds = Bukkit.getCommandMap();
        cmds.register("core", new BedCommand(this));
        cmds.register("core", new SpawnCommand(this));

        cmds.register("core-bukkit", new GamemodeCommand(this));

        PluginManager mng = Bukkit.getPluginManager();
        mng.registerEvents(new PlayerListener(this), this);
        mng.registerEvents(new PortalEnterListener(this), this);

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
            webhookMessageBuilder.setUsername("System");
            webhookMessageBuilder.setAvatarUrl(getConfig().getString("serverAvatar", "https://cdn.discordapp.com/attachments/1040778980992766054/1090748202854129744/ssg_bot.png"));
            webhookMessageBuilder.setContent(getConfig().getString("emoji.success", "") + " Der Server wurde neu gestartet.");
            DiscordWebhook.executeHook(webhookMessageBuilder, this.getConfig().getString("chatRelay", ""));
        }, 20 * 5);

        ChatUtil.replySenderComponent(this.getServer().getConsoleSender(), "&a" + this.getName() + " Enabled");
    }

    @Override
    public void onDisable() {
        WebhookMessageBuilder webhookMessageBuilder = new WebhookMessageBuilder();
        webhookMessageBuilder.setUsername("System");
        webhookMessageBuilder.setAvatarUrl(getConfig().getString("serverAvatar", "https://cdn.discordapp.com/attachments/1040778980992766054/1090748202854129744/ssg_bot.png"));
        webhookMessageBuilder.setContent(getConfig().getString("emoji.error", "") + " Der Server wurde gestoppt.");
        DiscordWebhook.executeHook(webhookMessageBuilder, this.getConfig().getString("chatRelay", ""));
        ChatUtil.replySenderComponent(this.getServer().getConsoleSender(), "&c" + this.getName() + " Disabled");
    }

    private void loadLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider.getProvider() != null) {
            luckPerms = provider.getProvider();
            Constants.SERVER_NAME = luckPerms.getServerName();
        } else {
            ChatUtil.replySenderComponent(this.getServer().getConsoleSender(), "&cCould not find LuckPerms. Disabling Plugin");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }

    public PermissionManager getPerms() {
        return perms;
    }
}
