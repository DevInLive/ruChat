package ru.cubelife.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

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
		m = m.replaceAll("$player", p.getDisplayName()).replaceAll("$msg", msg);
		
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
