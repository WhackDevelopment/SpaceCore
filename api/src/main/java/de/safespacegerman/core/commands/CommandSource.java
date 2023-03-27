package de.safespacegerman.core.commands;

import de.safespacegerman.core.salami.ComponentSerializer;
import net.kyori.adventure.text.Component;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

/**
 * SpaceCore; de.safespacegerman.core.commands:CommandSource
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class CommandSource implements ICommandSource {

    private CommandSender sender;
    private boolean isPLayer;
    private boolean isConsole;

    private Player player = null;
    private ConsoleCommandSender console = null;

    public CommandSource(CommandSender sender) {
        this.sender = sender;
        this.isPLayer = (sender instanceof Player);
        this.isConsole = (sender instanceof ConsoleCommandSender);

        if (isPlayer()) {
            this.player = (Player) sender;
        } else if (isConsole()) {
            this.console = (ConsoleCommandSender) sender;
        }
    }

    @Override
    public void sendMessage(@NotNull final String message) {
        sender.sendMessage(message);
    }

    @Override
    public void sendMessage(final @NotNull String... messages) {
        sender.sendMessage(messages);
    }

    @Override
    public void sendMessage(@Nullable final UUID uid, @NotNull final String message) {
        sender.sendMessage(uid, message);
    }

    @Override
    public void sendMessage(@Nullable final UUID uid, final @NotNull String... messages) {
        sender.sendMessage(uid, messages);
    }

    @Override
    public @NotNull Server getServer() {
        return sender.getServer();
    }

    @Override
    public String getName() {
        String name = "console";
        if (this.isPLayer) {
            name = sender.getName();
        }
        return null;
    }

    @Override
    public @NotNull Spigot spigot() {
        return sender.spigot();
    }

    @Override
    public @NotNull Component name() {
        return sender.name();
    }

    @Override
    public Component getDisplayName() {
        Component name = ComponentSerializer.etAndHEX.deserialize("Konsole");
        if (this.isPLayer) {
            name = player().displayName();
        }
        return name;
    }

    @Override
    public CommandSender sender() {
        return sender;
    }

    @Override
    public Player player() {
        return player;
    }

    @Override
    public ConsoleCommandSender console() {
        return console;
    }

    @Override
    public boolean isPermissionSet(@NotNull final String name) {
        return sender.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull final Permission perm) {
        return sender.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(final String permission) {
        return sender.hasPermission(permission);
    }

    @Override
    public boolean hasPermission(@NotNull final Permission perm) {
        return sender.hasPermission(perm);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String name, final boolean value) {
        return sender.addAttachment(plugin, name, value);
    }

    @Override
    public @NotNull PermissionAttachment addAttachment(@NotNull final Plugin plugin) {
        return sender.addAttachment(plugin);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String name, final boolean value, final int ticks) {
        return sender.addAttachment(plugin, name, value, ticks);
    }

    @Override
    public @Nullable PermissionAttachment addAttachment(@NotNull final Plugin plugin, final int ticks) {
        return sender.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull final PermissionAttachment attachment) {
        sender.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        sender.recalculatePermissions();
    }

    @Override
    public @NotNull Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return sender.getEffectivePermissions();
    }

    @Override
    public boolean isPlayer() {
        return isPLayer;
    }

    @Override
    public boolean isConsole() {
        return isConsole;
    }

    @Override
    public boolean isOp() {
        return sender.isOp();
    }

    @Override
    public void setOp(final boolean value) {
        sender.setOp(value);
    }

}
