package de.safespacegerman.core.perms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.query.QueryMode;
import net.luckperms.api.query.QueryOptions;

import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.UUID;

/**
 * SpaceCore; de.safespacegerman.core.perms:PermissionManager
 *
 * @author <a href="https://github.com/LuciferMorningstarDev">LuciferMorningstarDev</a>
 * @since 27.03.2023
 */
public class PermissionManager {

    private final LuckPerms luckPerms;

    public PermissionManager(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    public String getDefaultPlayerGroupId(UUID uuid) {
        try {
            return luckPerms.getUserManager().getUser(uuid).getPrimaryGroup();
        } catch (Exception except) {
            return "default";
        }
    }

    public List<String> getPlayerGroups(UUID uuid) {
        try {
            return loadUser(uuid).getInheritedGroups(QueryOptions.builder(QueryMode.NON_CONTEXTUAL).build())
                    .stream().map(g -> g.getName()).toList();
        } catch (Exception except) {
            return Collections.emptyList();
        }
    }

    public String resolveGroupPrefix(String groupId) {
        // TODO
        return "";
    }

    public String resolvePlayerGroupPrefix(UUID uuid) {
        return resolveGroupPrefix(getDefaultPlayerGroupId(uuid));
    }

    public boolean isHigherGroup(String group_should_be_higher, String group_should_be_lower) {
        int higher = luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_higher).getWeight().getAsInt() : 0;
        int lower = luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight() != null
                ? luckPerms.getGroupManager().getGroup(group_should_be_lower).getWeight().getAsInt() : 0;
        if (higher <= lower) {
            return false;
        }
        return true;
    }

    public boolean isHigherPlayer(UUID player_should_be_higher, UUID player_should_be_lower) {
        return isHigherGroup(resolvePlayerGroupPrefix(player_should_be_higher), resolvePlayerGroupPrefix(player_should_be_lower));
    }

    public CachedMetaData playerMeta(UUID player) {
        return loadUser(player).getCachedData().getMetaData();
    }

    public CachedMetaData groupMeta(String group) {
        return getLuckPerms().getGroupManager().getGroup(group).getCachedData().getMetaData();
    }

    public User loadUser(UUID player) {
        return getLuckPerms().getUserManager().getUser(player);
    }

    public Group loadGroup(String group) {
        return getLuckPerms().getGroupManager().getGroup(group);
    }

    public String getPrefix(UUID player) {
        String prefix = playerMeta(player).getPrefix();
        return (prefix != null) ? prefix : "";
    }

    public String getSuffix(UUID player) {
        String suffix = playerMeta(player).getSuffix();
        return (suffix != null) ? suffix : "";
    }

    public String getPrefixes(UUID player) {
        SortedMap<Integer, String> map = playerMeta(player).getPrefixes();
        StringBuilder prefixes = new StringBuilder();
        for (String prefix : map.values()) {
            prefixes.append(prefix);
        }
        return prefixes.toString();
    }

    public String getSuffixes(UUID player) {
        SortedMap<Integer, String> map = playerMeta(player).getSuffixes();
        StringBuilder suffixes = new StringBuilder();
        for (String prefix : map.values()) {
            suffixes.append(prefix);
        }
        return suffixes.toString();
    }

    public LuckPerms getLuckPerms() {
        return luckPerms;
    }
}
