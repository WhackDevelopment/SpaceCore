package de.safespacegerman.core.naming;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

/**
 * SpaceCore; de.safespacegerman.core.naming:NameTag
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 28.03.2023
 */
public class NameTag {

    PacketContainer packet;

    public NameTag(Player player) {
        this.packet = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);
        String name = UUID.randomUUID().toString().replace("-", "").substring(0, 12);
        this.packet.getIntegers().write(1, 0);
        this.packet.getStrings().write(0, name);
        this.packet.getChatComponents().write(0, WrappedChatComponent.fromText(player.toString()));
        this.packet.getSpecificModifier(Collection.class).write(0, Collections.singletonList(player.getName()));
    }

    public NameTag setPrefix(String prefix) {
        this.packet.getChatComponents().write(1, WrappedChatComponent.fromText(ChatColor.translateAlternateColorCodes('&', prefix)));
        return this;
    }

    public NameTag setSuffix(String suffix) {
        this.packet.getChatComponents().write(2, WrappedChatComponent.fromText(" " + ChatColor.translateAlternateColorCodes('&', suffix)));
        return this;
    }

    public void build() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            try {
                ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
            } catch (InvocationTargetException e) {
                throw new RuntimeException("Cannot send packet " + packet, e);
            }
        }
    }
}
