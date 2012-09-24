package ru.cubelife.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

@SuppressWarnings("deprecation")
public class ChatListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player p = event.getPlayer();
		ruChat.modes.put(p, ChatMode.PRIVATE);
	}
	
	@EventHandler
	public void onChat(PlayerChatEvent event) {
		if(event.isCancelled()) {
			return;
		}
		
		Player p = event.getPlayer();
		
		ChatMode cm = ruChat.modes.get(p);
		if(cm == ChatMode.PRIVATE) {
			privateMsg(p, event.getMessage());
		}
	}
	
	private void privateMsg(Player p, String msg) {
		Location pLoc = p.getLocation();
		
		String m = ruChat.cfg.getString("private-chat-format");
		PermissionUser user = PermissionsEx.getPermissionManager().getUser(p);
                String worldName = p.getWorld().getName();
                m = m.replaceAll("$player", p.getDisplayName()).replaceAll("$msg", msg).replaceAll("$prefix", user.getPrefix(worldName).replaceAll("$suffix", user.getSuffix(worldName)));
		
		double range = Math.pow(ruChat.cfg.getInt("private-range"), 2);
		
		int i = 0;
		for(Player r : Bukkit.getOnlinePlayers()) {
			if(pLoc.distanceSquared(r.getLocation()) < range) {
				r.sendMessage(m);
				i++;
			}
		}
		
		p.sendMessage(ChatColor.BLUE + "(" + i + ") " + ChatColor.WHITE + m);
	}

}
