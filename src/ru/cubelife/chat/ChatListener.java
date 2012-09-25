package ru.cubelife.chat;

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
		boolean g = event.getMessage().startsWith("!");
		boolean s = event.getMessage().startsWith("@");
		if(cm == ChatMode.PRIVATE && !(g || s)) {
			Utils.privateMessage(p, event.getMessage());
		} else if(cm == ChatMode.GLOBAL || g) {
			Utils.globalMessage(p, event.getMessage());
		} else if(cm == ChatMode.SALE || s) {
			Utils.saleMessage(p, event.getMessage());
		} else if(cm == ChatMode.HELP) {
			Utils.helpMessage(p, event.getMessage());
		}
	}

}
